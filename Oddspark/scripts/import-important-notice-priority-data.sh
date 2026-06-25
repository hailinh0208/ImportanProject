#!/bin/bash
#
# Import seed data for OP_OBJ_IMPORTANT_NOTICE_PRIORITY via custom object batch API.
# Run this AFTER import-notice-master-data.sh (requires notice masters to exist).
# Idempotent — checks each ERC before importing, skips entries that already exist.
#
# Prerequisite: notice masters 008, 011, 013, 017, 022 must have displayPageMulti set.
# Run import-notice-master-data.sh first (fresh install only; existing entries are skipped).
# If those notice masters already exist without displayPageMulti, update them manually via UI.
#
# Usage:
#   bash scripts/import-important-notice-priority-data.sh
#   bash scripts/import-important-notice-priority-data.sh http://localhost:8080 test@liferay.com:test
#
# Constraint: displayPageSingle of each priority record must be one of the values
# in displayPageMulti of its linked notice master.
#
# Test scenario matrix (pagecode → expected top-3 results, sorted by priority 1→2→3→NULL):
#
#  pagecode=1  (Overall TOP)         → 013[p=1], 011[p=2], 017[p=3]  (022[NULL] is 4th, excluded)
#  pagecode=2  (App)                 → 022[p=1], 017[p=2]             (2 results)
#  pagecode=3  (Horse Racing TOP)    → 011[p=1], 022[p=2], 017[p=3]  (013[NULL] is 4th, excluded)
#  pagecode=4  (Horse Racing Race List) → 022[p=1], 011[p=3]         (2 results)
#  pagecode=5  (Horse Racing Race Info) → 011[NULL]                  (1 result, NULL sorted by date)
#  pagecode=10 (Keirin TOP)          → 022[p=1], 013[p=2], 017[p=3]
#  pagecode=11 (Keirin Race List)    → 022[p=2]                      (1 result)
#  pagecode=17 (Auto Race TOP)       → 013[p=1], 017[p=2], 022[p=3]
#  pagecode=18 (Auto Race Race List) → 022[NULL]                     (1 result)
#  pagecode=24 (LOTO TOP)            → 017[p=1], 022[p=2]            (2 results)
#  pagecode=25 (LOTO Race List)      → 022[p=3]                      (1 result)
#  pagecode=6..9,12..16,19..23,26..28 → []                           (empty, no notices configured)
#  pagecode=1 with notice 008        → 008 must NOT appear (publishStatus=false)

HOST="${1:-http://localhost:8080}"
CREDENTIALS="${2:-test@liferay.com:1}"
NOTICE_MASTER_URL="$HOST/o/c/noticemasters"
PRIORITY_URL="$HOST/o/c/importantnoticepriorities"

CREATED=0
SKIPPED=0
FAILED=0

get_notice_master_id() {
  local ERC="$1"
  local RESPONSE
  RESPONSE=$(curl -s "$NOTICE_MASTER_URL/by-external-reference-code/$ERC" \
    -u "$CREDENTIALS")
  echo "$RESPONSE" | grep -o '"id" *: *[0-9]*' | head -1 | grep -o '[0-9]*'
}

import_priority() {
  local ERC="$1"
  local NOTICE_MASTER_ID="$2"
  local DATA="$3"

  STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
    "$PRIORITY_URL/by-external-reference-code/$ERC" -u "$CREDENTIALS")

  if [ "$STATUS" = "200" ]; then
    echo "  SKIP $ERC (already exists)"
    SKIPPED=$((SKIPPED + 1))
    return
  fi

  RESPONSE=$(curl -s -X POST \
    "$NOTICE_MASTER_URL/$NOTICE_MASTER_ID/importantNoticePriority" \
    -H "Content-Type: application/json" \
    -u "$CREDENTIALS" \
    -d "$DATA")

  if echo "$RESPONSE" | grep -q '"externalReferenceCode"'; then
    echo "  OK   $ERC"
    CREATED=$((CREATED + 1))
  else
    ERROR=$(echo "$RESPONSE" | grep -o '"title" *: *"[^"]*"' | grep -o '"[^"]*"$' | tr -d '"')
    echo "  FAIL $ERC — $ERROR"
    FAILED=$((FAILED + 1))
  fi
}

echo "Importing important notice priority seed data to $HOST..."
echo ""

# ---------------------------------------------------------------------------
# Notice 008 — Scheduled Server Upgrade
# importantNotice=true, publishStatus=false → must NOT appear in any API result
# displayPageMulti: [1, 3, 10, 17, 24]
# All priorities set to 1 to ensure they would be first IF publishStatus were true
# ---------------------------------------------------------------------------
echo "=== Notice 008: Scheduled Server Upgrade (unpublished — verify excluded from results) ==="
NM_ID_008=$(get_notice_master_id "OP_DATA_NOTICE_008")
if [ -z "$NM_ID_008" ]; then
  echo "  ERROR: Could not find notice master OP_DATA_NOTICE_008. Run import-notice-master-data.sh first."
else
  import_priority "OP_DATA_PRIORITY_008_P1" "$NM_ID_008" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_008_P1","displayPageSingle":"1","priority":1}'
  import_priority "OP_DATA_PRIORITY_008_P3" "$NM_ID_008" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_008_P3","displayPageSingle":"3","priority":1}'
  import_priority "OP_DATA_PRIORITY_008_P10" "$NM_ID_008" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_008_P10","displayPageSingle":"10","priority":1}'
  import_priority "OP_DATA_PRIORITY_008_P17" "$NM_ID_008" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_008_P17","displayPageSingle":"17","priority":1}'
  import_priority "OP_DATA_PRIORITY_008_P24" "$NM_ID_008" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_008_P24","displayPageSingle":"24","priority":1}'
fi
echo ""

# ---------------------------------------------------------------------------
# Notice 011 — Horse Racing Result Correction
# importantNotice=true, publishStatus=true
# importantNoticeStart=2026-06-20, importantNoticeEnd=2026-07-31 (active on 2026-06-25)
# displayPageMulti: [1, 3, 4, 5]
#
# Priority assignments:
#   page 1 (Overall TOP)         → priority=2  (2nd after 013)
#   page 3 (Horse Racing TOP)    → priority=1  (1st on this page)
#   page 4 (Horse Racing Race List) → priority=3
#   page 5 (Horse Racing Race Info) → NULL     (sorted by importantNoticeStart date)
# ---------------------------------------------------------------------------
echo "=== Notice 011: Horse Racing Result Correction (published, active) ==="
NM_ID_011=$(get_notice_master_id "OP_DATA_NOTICE_011")
if [ -z "$NM_ID_011" ]; then
  echo "  ERROR: Could not find notice master OP_DATA_NOTICE_011. Run import-notice-master-data.sh first."
else
  import_priority "OP_DATA_PRIORITY_011_P1" "$NM_ID_011" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_011_P1","displayPageSingle":"1","priority":2}'
  import_priority "OP_DATA_PRIORITY_011_P3" "$NM_ID_011" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_011_P3","displayPageSingle":"3","priority":1}'
  import_priority "OP_DATA_PRIORITY_011_P4" "$NM_ID_011" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_011_P4","displayPageSingle":"4","priority":3}'
  import_priority "OP_DATA_PRIORITY_011_P5" "$NM_ID_011" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_011_P5","displayPageSingle":"5"}'
fi
echo ""

# ---------------------------------------------------------------------------
# Notice 013 — Boat Racing Weather Cancellation
# importantNotice=true, publishStatus=true
# importantNoticeStart=2026-06-24 (active), importantNoticeEnd=NULL (indefinite)
# displayPageMulti: [1, 3, 10, 17]
#
# Priority assignments:
#   page 1  (Overall TOP)    → priority=1  (1st on this page)
#   page 3  (Horse Racing TOP) → NULL      (sorted by date: importantNoticeStart=2026-06-24)
#   page 10 (Keirin TOP)     → priority=2
#   page 17 (Auto Race TOP)  → priority=1  (1st on this page)
# ---------------------------------------------------------------------------
echo "=== Notice 013: Boat Racing Weather Cancellation (published, active) ==="
NM_ID_013=$(get_notice_master_id "OP_DATA_NOTICE_013")
if [ -z "$NM_ID_013" ]; then
  echo "  ERROR: Could not find notice master OP_DATA_NOTICE_013. Run import-notice-master-data.sh first."
else
  import_priority "OP_DATA_PRIORITY_013_P1" "$NM_ID_013" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_013_P1","displayPageSingle":"1","priority":1}'
  import_priority "OP_DATA_PRIORITY_013_P3" "$NM_ID_013" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_013_P3","displayPageSingle":"3"}'
  import_priority "OP_DATA_PRIORITY_013_P10" "$NM_ID_013" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_013_P10","displayPageSingle":"10","priority":2}'
  import_priority "OP_DATA_PRIORITY_013_P17" "$NM_ID_013" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_013_P17","displayPageSingle":"17","priority":1}'
fi
echo ""

# ---------------------------------------------------------------------------
# Notice 017 — Account Security Enhancement
# importantNotice=true, publishStatus=true
# importantNoticeStart=2026-06-20 (active), importantNoticeEnd=NULL (indefinite)
# displayPageMulti: [1, 2, 3, 10, 17, 24]
#
# Priority assignments:
#   page 1  (Overall TOP)    → priority=3
#   page 2  (App)            → priority=2
#   page 3  (Horse Racing TOP) → priority=3
#   page 10 (Keirin TOP)     → priority=3
#   page 17 (Auto Race TOP)  → priority=2
#   page 24 (LOTO TOP)       → priority=1  (1st on this page)
# ---------------------------------------------------------------------------
echo "=== Notice 017: Account Security Enhancement (published, active) ==="
NM_ID_017=$(get_notice_master_id "OP_DATA_NOTICE_017")
if [ -z "$NM_ID_017" ]; then
  echo "  ERROR: Could not find notice master OP_DATA_NOTICE_017. Run import-notice-master-data.sh first."
else
  import_priority "OP_DATA_PRIORITY_017_P1" "$NM_ID_017" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_017_P1","displayPageSingle":"1","priority":3}'
  import_priority "OP_DATA_PRIORITY_017_P2" "$NM_ID_017" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_017_P2","displayPageSingle":"2","priority":2}'
  import_priority "OP_DATA_PRIORITY_017_P3" "$NM_ID_017" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_017_P3","displayPageSingle":"3","priority":3}'
  import_priority "OP_DATA_PRIORITY_017_P10" "$NM_ID_017" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_017_P10","displayPageSingle":"10","priority":3}'
  import_priority "OP_DATA_PRIORITY_017_P17" "$NM_ID_017" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_017_P17","displayPageSingle":"17","priority":2}'
  import_priority "OP_DATA_PRIORITY_017_P24" "$NM_ID_017" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_017_P24","displayPageSingle":"24","priority":1}'
fi
echo ""

# ---------------------------------------------------------------------------
# Notice 022 — Emergency Database Maintenance
# importantNotice=true, publishStatus=true
# importantNoticeStart=2026-06-23 (active), importantNoticeEnd=2026-07-20
# displayPageMulti: [1, 2, 3, 4, 10, 11, 17, 18, 24, 25]
#
# Priority assignments:
#   page 1  (Overall TOP)         → NULL     (4th on this page, excluded from top-3)
#   page 2  (App)                 → priority=1
#   page 3  (Horse Racing TOP)    → priority=2
#   page 4  (Horse Racing Race List) → priority=1
#   page 10 (Keirin TOP)          → priority=1
#   page 11 (Keirin Race List)    → priority=2
#   page 17 (Auto Race TOP)       → priority=3
#   page 18 (Auto Race Race List) → NULL     (only notice on this page, sorted by date)
#   page 24 (LOTO TOP)            → priority=2
#   page 25 (LOTO Race List)      → priority=3
# ---------------------------------------------------------------------------
echo "=== Notice 022: Emergency Database Maintenance (published, active) ==="
NM_ID_022=$(get_notice_master_id "OP_DATA_NOTICE_022")
if [ -z "$NM_ID_022" ]; then
  echo "  ERROR: Could not find notice master OP_DATA_NOTICE_022. Run import-notice-master-data.sh first."
else
  import_priority "OP_DATA_PRIORITY_022_P1" "$NM_ID_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P1","displayPageSingle":"1"}'
  import_priority "OP_DATA_PRIORITY_022_P2" "$NM_ID_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P2","displayPageSingle":"2","priority":1}'
  import_priority "OP_DATA_PRIORITY_022_P3" "$NM_ID_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P3","displayPageSingle":"3","priority":2}'
  import_priority "OP_DATA_PRIORITY_022_P4" "$NM_ID_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P4","displayPageSingle":"4","priority":1}'
  import_priority "OP_DATA_PRIORITY_022_P10" "$NM_ID_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P10","displayPageSingle":"10","priority":1}'
  import_priority "OP_DATA_PRIORITY_022_P11" "$NM_ID_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P11","displayPageSingle":"11","priority":2}'
  import_priority "OP_DATA_PRIORITY_022_P17" "$NM_ID_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P17","displayPageSingle":"17","priority":3}'
  import_priority "OP_DATA_PRIORITY_022_P18" "$NM_ID_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P18","displayPageSingle":"18"}'
  import_priority "OP_DATA_PRIORITY_022_P24" "$NM_ID_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P24","displayPageSingle":"24","priority":2}'
  import_priority "OP_DATA_PRIORITY_022_P25" "$NM_ID_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P25","displayPageSingle":"25","priority":3}'
fi
echo ""

echo "Result: $CREATED imported, $SKIPPED skipped, $FAILED failed."
[ "$FAILED" -gt 0 ] && exit 1 || exit 0
