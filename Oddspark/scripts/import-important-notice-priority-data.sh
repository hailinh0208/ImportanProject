#!/bin/bash
#
# Import seed data for OP_OBJ_IMPORTANT_NOTICE_PRIORITY via custom object REST API.
# Run this AFTER import-notice-master-data.sh (requires notice masters to exist).
# Idempotent — checks each ERC before importing, skips entries that already exist.
#
# Constraint: displayPageSingle of each priority record must be one of the values
# in displayPageMulti of its linked notice master.
#
# Usage:
#   bash scripts/import-important-notice-priority-data.sh
#   bash scripts/import-important-notice-priority-data.sh http://localhost:8080 test@liferay.com:test
#
# Test scenario matrix (pagecode → expected top-3 results, sorted by priority 1→2→3→NULL):
#
#  pagecode=1  (Overall TOP)            → 013[p=1], 011[p=2], 017[p=3]  (022[p=0] 4th, excluded)
#  pagecode=2  (App)                    → 022[p=1], 017[p=2]
#  pagecode=3  (Horse Racing TOP)       → 011[p=1], 022[p=2], 017[p=3]  (013[p=0] 4th, excluded)
#  pagecode=4  (Horse Racing Race List) → 022[p=1], 011[p=0]
#  pagecode=5  (Horse Racing Race Info) → 011[p=0]
#  pagecode=10 (Keirin TOP)             → 022[p=1], 013[p=2], 017[p=3]
#  pagecode=11 (Keirin Race List)       → 022[p=2]
#  pagecode=17 (Auto Race TOP)          → 013[p=1], 017[p=2], 022[p=3]
#  pagecode=18 (Auto Race Race List)    → 022[p=0]
#  pagecode=24 (LOTO TOP)               → 017[p=1], 022[p=2]
#  pagecode=25 (LOTO Race List)         → 022[p=3]
#  pagecode=6..9,12..16,19..23,26..28   → [] (empty)
#
# Constraint: priority must be unique per displayPageSingle among published notices.

HOST="${1:-http://localhost:8080}"
CREDENTIALS="${2:-test@liferay.com:1}"
NOTICE_MASTER_URL="$HOST/o/c/noticemasters"
PRIORITY_URL="$HOST/o/c/importantnoticepriorities"

CREATED=0
SKIPPED=0
FAILED=0

check_notice_master() {
  local ERC="$1"
  local STATUS
  STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
    "$NOTICE_MASTER_URL/by-external-reference-code/$ERC" -u "$CREDENTIALS")
  [ "$STATUS" = "200" ]
}

import_priority() {
  local ERC="$1"
  local NOTICE_MASTER_ERC="$2"
  local DATA="$3"

  # Link to notice master via ERC (relationship name is importantNoticePriority — singular)
  PAYLOAD=$(echo "$DATA" | sed "s/}$/,\"r_importantNoticePriority_c_noticeMasterERC\":\"$NOTICE_MASTER_ERC\"}/")

  STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
    "$PRIORITY_URL/by-external-reference-code/$ERC" -u "$CREDENTIALS")

  if [ "$STATUS" = "200" ]; then
    # Record exists — PATCH to update the FK
    HTTP_CODE=$(curl -s -o /tmp/_op_priority_resp.json -w "%{http_code}" \
      -X PATCH "$PRIORITY_URL/by-external-reference-code/$ERC" \
      -H "Content-Type: application/json" \
      -u "$CREDENTIALS" \
      -d "{\"r_importantNoticePriority_c_noticeMasterERC\":\"$NOTICE_MASTER_ERC\"}")
    BODY=$(cat /tmp/_op_priority_resp.json)
    if echo "$BODY" | grep -q '"externalReferenceCode"'; then
      echo "  PATCH $ERC"
      CREATED=$((CREATED + 1))
    else
      echo "  FAIL $ERC (patch)"
      echo "       HTTP : $HTTP_CODE"
      echo "       Body : $(echo "$BODY" | head -c 400)"
      FAILED=$((FAILED + 1))
    fi
    return
  fi

  HTTP_CODE=$(curl -s -o /tmp/_op_priority_resp.json -w "%{http_code}" \
    -X POST "$PRIORITY_URL" \
    -H "Content-Type: application/json" \
    -u "$CREDENTIALS" \
    -d "$PAYLOAD")
  BODY=$(cat /tmp/_op_priority_resp.json)

  if echo "$BODY" | grep -q '"externalReferenceCode"'; then
    echo "  OK   $ERC"
    CREATED=$((CREATED + 1))
  else
    echo "  FAIL $ERC"
    echo "       HTTP : $HTTP_CODE"
    echo "       Body : $(echo "$BODY" | head -c 400)"
    FAILED=$((FAILED + 1))
  fi
}

echo "Importing important notice priority seed data to $HOST..."
echo ""

# ---------------------------------------------------------------------------
# Notice 011 — Horse Racing Result Correction
# importantNotice=true, publishStatus=true
# importantNoticeStart=2026-06-20, importantNoticeEnd=2026-07-31 (active)
# displayPageMulti: [1, 3, 4, 5]
#
# Priority assignments:
#   page 1 (Overall TOP)            → priority=2
#   page 3 (Horse Racing TOP)       → priority=1  (1st on this page)
#   page 4 (Horse Racing Race List) → priority=0  (NULL, sorted last)
#   page 5 (Horse Racing Race Info) → priority=0  (NULL, sorted last)
# ---------------------------------------------------------------------------
echo "=== Notice 011: Horse Racing Result Correction (published, active) ==="
if ! check_notice_master "OP_DATA_NOTICE_011"; then
  echo "  ERROR: OP_DATA_NOTICE_011 not found. Run import-notice-master-data.sh first."
else
  import_priority "OP_DATA_PRIORITY_011_P1" "OP_DATA_NOTICE_011" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_011_P1","displayPageSingle":"1","priority":2}'
  import_priority "OP_DATA_PRIORITY_011_P3" "OP_DATA_NOTICE_011" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_011_P3","displayPageSingle":"3","priority":1}'
  import_priority "OP_DATA_PRIORITY_011_P4" "OP_DATA_NOTICE_011" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_011_P4","displayPageSingle":"4","priority":0}'
  import_priority "OP_DATA_PRIORITY_011_P5" "OP_DATA_NOTICE_011" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_011_P5","displayPageSingle":"5","priority":0}'
fi
echo ""

# ---------------------------------------------------------------------------
# Notice 013 — Boat Racing Weather Cancellation
# importantNotice=true, publishStatus=true
# importantNoticeStart=2026-06-24 (active), importantNoticeEnd=NULL (indefinite)
# displayPageMulti: [1, 3, 10, 17]
#
# Priority assignments:
#   page 1  (Overall TOP)      → priority=1  (1st on this page)
#   page 3  (Horse Racing TOP) → priority=0  (NULL, sorted last — 4th, excluded from top-3)
#   page 10 (Keirin TOP)       → priority=2
#   page 17 (Auto Race TOP)    → priority=1  (1st on this page)
# ---------------------------------------------------------------------------
echo "=== Notice 013: Boat Racing Weather Cancellation (published, active) ==="
if ! check_notice_master "OP_DATA_NOTICE_013"; then
  echo "  ERROR: OP_DATA_NOTICE_013 not found. Run import-notice-master-data.sh first."
else
  import_priority "OP_DATA_PRIORITY_013_P1"  "OP_DATA_NOTICE_013" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_013_P1","displayPageSingle":"1","priority":1}'
  import_priority "OP_DATA_PRIORITY_013_P3"  "OP_DATA_NOTICE_013" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_013_P3","displayPageSingle":"3","priority":0}'
  import_priority "OP_DATA_PRIORITY_013_P10" "OP_DATA_NOTICE_013" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_013_P10","displayPageSingle":"10","priority":2}'
  import_priority "OP_DATA_PRIORITY_013_P17" "OP_DATA_NOTICE_013" \
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
#   page 1  (Overall TOP)      → priority=3
#   page 2  (App)              → priority=2
#   page 3  (Horse Racing TOP) → priority=3
#   page 10 (Keirin TOP)       → priority=3
#   page 17 (Auto Race TOP)    → priority=2
#   page 24 (LOTO TOP)         → priority=1  (1st on this page)
# ---------------------------------------------------------------------------
echo "=== Notice 017: Account Security Enhancement (published, active) ==="
if ! check_notice_master "OP_DATA_NOTICE_017"; then
  echo "  ERROR: OP_DATA_NOTICE_017 not found. Run import-notice-master-data.sh first."
else
  import_priority "OP_DATA_PRIORITY_017_P1"  "OP_DATA_NOTICE_017" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_017_P1","displayPageSingle":"1","priority":3}'
  import_priority "OP_DATA_PRIORITY_017_P2"  "OP_DATA_NOTICE_017" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_017_P2","displayPageSingle":"2","priority":2}'
  import_priority "OP_DATA_PRIORITY_017_P3"  "OP_DATA_NOTICE_017" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_017_P3","displayPageSingle":"3","priority":3}'
  import_priority "OP_DATA_PRIORITY_017_P10" "OP_DATA_NOTICE_017" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_017_P10","displayPageSingle":"10","priority":3}'
  import_priority "OP_DATA_PRIORITY_017_P17" "OP_DATA_NOTICE_017" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_017_P17","displayPageSingle":"17","priority":2}'
  import_priority "OP_DATA_PRIORITY_017_P24" "OP_DATA_NOTICE_017" \
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
#   page 1  (Overall TOP)            → priority=0  (NULL, sorted last — 4th, excluded from top-3)
#   page 2  (App)                    → priority=1
#   page 3  (Horse Racing TOP)       → priority=2
#   page 4  (Horse Racing Race List) → priority=1
#   page 10 (Keirin TOP)             → priority=1
#   page 11 (Keirin Race List)       → priority=2
#   page 17 (Auto Race TOP)          → priority=3
#   page 18 (Auto Race Race List)    → priority=0  (NULL, sorted last)
#   page 24 (LOTO TOP)               → priority=2
#   page 25 (LOTO Race List)         → priority=3
# ---------------------------------------------------------------------------
echo "=== Notice 022: Emergency Database Maintenance (published, active) ==="
if ! check_notice_master "OP_DATA_NOTICE_022"; then
  echo "  ERROR: OP_DATA_NOTICE_022 not found. Run import-notice-master-data.sh first."
else
  import_priority "OP_DATA_PRIORITY_022_P1"  "OP_DATA_NOTICE_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P1","displayPageSingle":"1","priority":0}'
  import_priority "OP_DATA_PRIORITY_022_P2"  "OP_DATA_NOTICE_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P2","displayPageSingle":"2","priority":1}'
  import_priority "OP_DATA_PRIORITY_022_P3"  "OP_DATA_NOTICE_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P3","displayPageSingle":"3","priority":2}'
  import_priority "OP_DATA_PRIORITY_022_P4"  "OP_DATA_NOTICE_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P4","displayPageSingle":"4","priority":1}'
  import_priority "OP_DATA_PRIORITY_022_P10" "OP_DATA_NOTICE_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P10","displayPageSingle":"10","priority":1}'
  import_priority "OP_DATA_PRIORITY_022_P11" "OP_DATA_NOTICE_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P11","displayPageSingle":"11","priority":2}'
  import_priority "OP_DATA_PRIORITY_022_P17" "OP_DATA_NOTICE_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P17","displayPageSingle":"17","priority":3}'
  import_priority "OP_DATA_PRIORITY_022_P18" "OP_DATA_NOTICE_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P18","displayPageSingle":"18","priority":0}'
  import_priority "OP_DATA_PRIORITY_022_P24" "OP_DATA_NOTICE_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P24","displayPageSingle":"24","priority":2}'
  import_priority "OP_DATA_PRIORITY_022_P25" "OP_DATA_NOTICE_022" \
    '{"externalReferenceCode":"OP_DATA_PRIORITY_022_P25","displayPageSingle":"25","priority":3}'
fi
echo ""

echo "Result: $CREATED imported, $SKIPPED skipped, $FAILED failed."
[ "$FAILED" -gt 0 ] && exit 1 || exit 0
