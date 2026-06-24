#!/bin/bash
#
# Import seed data for OP_OBJ_NOTICE_MASTER via custom object batch API.
# Run this AFTER deploying oddspark-batch client extension.
# Idempotent — checks each ERC before importing, skips entries that already exist.
#
# Usage:
#   bash scripts/import-notice-master-data.sh
#   bash scripts/import-notice-master-data.sh http://localhost:8080 test@liferay.com:test

HOST="${1:-http://localhost:8080}"
CREDENTIALS="${2:-test@liferay.com:1}"
BASE_URL="$HOST/o/c/noticemasters"

CREATED=0
SKIPPED=0
FAILED=0

import_entry() {
  local ERC="$1"
  local DATA="$2"

  STATUS_CODE=$(curl -s -o /dev/null -w "%{http_code}" \
    "$BASE_URL/by-external-reference-code/$ERC" -u "$CREDENTIALS")

  if [ "$STATUS_CODE" = "200" ]; then
    echo "  SKIP $ERC (already exists)"
    SKIPPED=$((SKIPPED + 1))
    return
  fi

  RESPONSE=$(curl -s -X POST "$BASE_URL" \
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

echo "Importing notice master seed data to $HOST..."
echo ""

import_entry "OP_DATA_NOTICE_001" '{
  "externalReferenceCode": "OP_DATA_NOTICE_001",
  "noticeTitle": "Horse Racing System Maintenance Notice",
  "noticeContent": "The horse racing system will undergo scheduled maintenance on 2026-07-01 from 02:00 to 04:00 JST. All services will be unavailable during this time.",
  "categories": ["1"], "competitions": ["1"],
  "publishStatus": true, "importantNotice": false,
  "displayBeforeOrAfterLogin": "3", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-06-01T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_002" '{
  "externalReferenceCode": "OP_DATA_NOTICE_002",
  "noticeTitle": "New Bicycle Racing Prediction Service Launched",
  "noticeContent": "We are pleased to announce the launch of our new bicycle racing prediction service. Get expert predictions for all major races starting July 2026.",
  "categories": ["2"], "competitions": ["2"],
  "publishStatus": true, "importantNotice": false,
  "displayBeforeOrAfterLogin": "1", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-06-10T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_003" '{
  "externalReferenceCode": "OP_DATA_NOTICE_003",
  "noticeTitle": "General Site Maintenance",
  "noticeContent": "General maintenance notice covering all competition types. Please save your work before the maintenance window begins.",
  "categories": ["3"], "competitions": ["1", "2", "3"],
  "publishStatus": false, "importantNotice": false,
  "displayBeforeOrAfterLogin": "3", "currentVersion": "2", "liveVersion": "1",
  "displayDate": "2026-07-01T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_004" '{
  "externalReferenceCode": "OP_DATA_NOTICE_004",
  "noticeTitle": "LOTO Service Update",
  "noticeContent": "LOTO service has been updated with new features. Please check the updated help page for details.",
  "categories": ["2"], "competitions": ["4"],
  "publishStatus": true, "importantNotice": false,
  "displayBeforeOrAfterLogin": "2", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-06-15T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_005" '{
  "externalReferenceCode": "OP_DATA_NOTICE_005",
  "noticeTitle": "End of Year Campaign",
  "noticeContent": "Special end of year campaign for all competition types. Enjoy bonus points and special odds throughout December.",
  "categories": ["1"], "competitions": ["1", "2", "3", "4", "5"],
  "publishStatus": true, "importantNotice": false,
  "displayBeforeOrAfterLogin": "3", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-06-20T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_006" '{
  "externalReferenceCode": "OP_DATA_NOTICE_006",
  "noticeTitle": "Boat Racing Night Session Update",
  "noticeContent": "Boat racing night session schedule for July 2026 has been updated. Please check the latest race information.",
  "categories": ["1"], "competitions": ["3"],
  "publishStatus": true, "importantNotice": false,
  "displayBeforeOrAfterLogin": "3", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-06-21T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_007" '{
  "externalReferenceCode": "OP_DATA_NOTICE_007",
  "noticeTitle": "Odds Calculation System Improvement",
  "noticeContent": "We have improved the odds calculation algorithm for better accuracy. The changes will take effect from July 2026.",
  "categories": ["2"], "competitions": ["1", "3"],
  "publishStatus": true, "importantNotice": false,
  "displayBeforeOrAfterLogin": "1", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-06-22T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_008" '{
  "externalReferenceCode": "OP_DATA_NOTICE_008",
  "noticeTitle": "Scheduled Server Upgrade",
  "noticeContent": "Server upgrade is scheduled for 2026-07-10 from 01:00 to 03:00 JST. Some features may be temporarily unavailable.",
  "categories": ["3"], "competitions": ["1", "2", "3", "4", "5"],
  "publishStatus": false, "importantNotice": true,
  "displayBeforeOrAfterLogin": "3", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-06-25T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_009" '{
  "externalReferenceCode": "OP_DATA_NOTICE_009",
  "noticeTitle": "New Mobile App Release",
  "noticeContent": "Our new mobile application has been released. Download now to enjoy improved performance and new features.",
  "categories": ["2"], "competitions": ["2", "4"],
  "publishStatus": true, "importantNotice": false,
  "displayBeforeOrAfterLogin": "2", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-06-26T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_010" '{
  "externalReferenceCode": "OP_DATA_NOTICE_010",
  "noticeTitle": "Point Exchange Campaign",
  "noticeContent": "Exchange your accumulated points for special rewards during our summer campaign running throughout July 2026.",
  "categories": ["1"], "competitions": ["1", "2", "3", "4", "5"],
  "publishStatus": true, "importantNotice": false,
  "displayBeforeOrAfterLogin": "3", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-06-27T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_011" '{
  "externalReferenceCode": "OP_DATA_NOTICE_011",
  "noticeTitle": "Horse Racing Result Correction Notice",
  "noticeContent": "The official results for the June 20th Nakayama race have been corrected due to a photo finish review. Please check the updated results page.",
  "categories": ["1"], "competitions": ["1"],
  "publishStatus": true, "importantNotice": true,
  "displayBeforeOrAfterLogin": "1", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-06-28T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_012" '{
  "externalReferenceCode": "OP_DATA_NOTICE_012",
  "noticeTitle": "Bicycle Racing Keirin Grand Prix Entry List",
  "noticeContent": "The entry list for the Keirin Grand Prix 2026 has been published. View participating riders and race schedule on the event page.",
  "categories": ["2"], "competitions": ["2"],
  "publishStatus": true, "importantNotice": false,
  "displayBeforeOrAfterLogin": "2", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-06-29T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_013" '{
  "externalReferenceCode": "OP_DATA_NOTICE_013",
  "noticeTitle": "Boat Racing Weather Cancellation",
  "noticeContent": "Due to severe weather conditions, all boat racing events scheduled for July 3rd have been cancelled. Tickets will be refunded automatically.",
  "categories": ["1"], "competitions": ["3"],
  "publishStatus": true, "importantNotice": true,
  "displayBeforeOrAfterLogin": "3", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-07-02T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_014" '{
  "externalReferenceCode": "OP_DATA_NOTICE_014",
  "noticeTitle": "LOTO6 Jackpot Winner Announcement",
  "noticeContent": "Congratulations to the LOTO6 jackpot winner for the June 25th draw. The winning amount was 450 million yen. Check if you are a winner on the results page.",
  "categories": ["2"], "competitions": ["4"],
  "publishStatus": true, "importantNotice": false,
  "displayBeforeOrAfterLogin": "1", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-06-26T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_015" '{
  "externalReferenceCode": "OP_DATA_NOTICE_015",
  "noticeTitle": "Payment Method Update: PayPay Added",
  "noticeContent": "We are pleased to announce that PayPay has been added as a supported payment method. You can now top up your account balance using PayPay.",
  "categories": ["2"], "competitions": ["1", "2", "3", "4", "5"],
  "publishStatus": true, "importantNotice": false,
  "displayBeforeOrAfterLogin": "3", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-07-01T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_016" '{
  "externalReferenceCode": "OP_DATA_NOTICE_016",
  "noticeTitle": "Autumn Horse Racing Season Preview",
  "noticeContent": "The 2026 autumn horse racing season begins in September. Early prediction data and race schedules will be available from August 1st.",
  "categories": ["1"], "competitions": ["1"],
  "publishStatus": false, "importantNotice": false,
  "displayBeforeOrAfterLogin": "2", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-07-15T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_017" '{
  "externalReferenceCode": "OP_DATA_NOTICE_017",
  "noticeTitle": "Account Security Enhancement",
  "noticeContent": "We have enhanced account security with two-factor authentication. Please enable 2FA in your account settings to protect your account.",
  "categories": ["3"], "competitions": ["1", "2", "3", "4", "5"],
  "publishStatus": true, "importantNotice": true,
  "displayBeforeOrAfterLogin": "2", "currentVersion": "2", "liveVersion": "2",
  "displayDate": "2026-06-30T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_018" '{
  "externalReferenceCode": "OP_DATA_NOTICE_018",
  "noticeTitle": "Bicycle Racing Track Renovation Notice",
  "noticeContent": "Fukuoka Keirin track will undergo renovation from July 10 to August 31. Races will be temporarily held at the Kokura venue during this period.",
  "categories": ["3"], "competitions": ["2"],
  "publishStatus": true, "importantNotice": false,
  "displayBeforeOrAfterLogin": "3", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-07-05T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_019" '{
  "externalReferenceCode": "OP_DATA_NOTICE_019",
  "noticeTitle": "LOTO Numbers Lottery Holiday Schedule",
  "noticeContent": "Due to the national holiday on July 15th, LOTO draws will be held on July 14th instead. Please submit your entries before 20:00 on July 14th.",
  "categories": ["1"], "competitions": ["4", "5"],
  "publishStatus": true, "importantNotice": false,
  "displayBeforeOrAfterLogin": "1", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-07-08T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_020" '{
  "externalReferenceCode": "OP_DATA_NOTICE_020",
  "noticeTitle": "Multi-Competition Prediction Feature Released",
  "noticeContent": "You can now create prediction entries across multiple competition types in a single session. Try the new multi-competition dashboard from your account page.",
  "categories": ["2"], "competitions": ["1", "2", "3"],
  "publishStatus": true, "importantNotice": false,
  "displayBeforeOrAfterLogin": "2", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-07-10T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_021" '{
  "externalReferenceCode": "OP_DATA_NOTICE_021",
  "noticeTitle": "Boat Racing Tokuyama Grand Prix Schedule",
  "noticeContent": "The Tokuyama Boat Racing Grand Prix schedule for August 2026 has been announced. The main event runs from August 20 to August 25.",
  "categories": ["1"], "competitions": ["3"],
  "publishStatus": false, "importantNotice": false,
  "displayBeforeOrAfterLogin": "3", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-07-12T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_022" '{
  "externalReferenceCode": "OP_DATA_NOTICE_022",
  "noticeTitle": "Emergency Database Maintenance",
  "noticeContent": "Emergency maintenance is required on July 20th from 03:00 to 05:00 JST. All services will be unavailable. We apologize for the inconvenience.",
  "categories": ["3"], "competitions": ["1", "2", "3", "4", "5"],
  "publishStatus": true, "importantNotice": true,
  "displayBeforeOrAfterLogin": "3", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-07-18T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_023" '{
  "externalReferenceCode": "OP_DATA_NOTICE_023",
  "noticeTitle": "Withdrawal Processing Time Change",
  "noticeContent": "From August 1st, withdrawal processing time will be reduced from 3 business days to 1 business day. Bank transfer fees remain unchanged.",
  "categories": ["2"], "competitions": ["1", "2", "3", "4", "5"],
  "publishStatus": false, "importantNotice": false,
  "displayBeforeOrAfterLogin": "2", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-07-20T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_024" '{
  "externalReferenceCode": "OP_DATA_NOTICE_024",
  "noticeTitle": "Horse Racing JRA Sapporo Meet Opening",
  "noticeContent": "JRA Sapporo Summer Meet 2026 opens on July 25th. Predictions and race cards are now available for all 8 racing days through August 10th.",
  "categories": ["1"], "competitions": ["1"],
  "publishStatus": true, "importantNotice": false,
  "displayBeforeOrAfterLogin": "1", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-07-22T00:00:00Z"
}'

import_entry "OP_DATA_NOTICE_025" '{
  "externalReferenceCode": "OP_DATA_NOTICE_025",
  "noticeTitle": "Summer Bonus Points Campaign",
  "noticeContent": "Earn double points on all predictions made during the summer campaign from July 25 to August 31. Points can be redeemed for exclusive rewards.",
  "categories": ["2"], "competitions": ["1", "2", "3", "4", "5"],
  "publishStatus": true, "importantNotice": false,
  "displayBeforeOrAfterLogin": "3", "currentVersion": "1", "liveVersion": "1",
  "displayDate": "2026-07-24T00:00:00Z"
}'

echo ""
echo "Result: $CREATED imported, $SKIPPED skipped, $FAILED failed."
[ "$FAILED" -gt 0 ] && exit 1 || exit 0
