#!/usr/bin/env bash

# This script is used to create a new day folder in the format of "dayXX" where XX is the next day number
day=2
dayStr=$(printf "%02d" $day)

source .env

mkdir "src/day$dayStr"
curl "https://adventofcode.com/2024/day/$day/input" \
  -H 'accept: text/plain' \
  -H "cookie: session=$SESSION_COOKIE" \
  --compressed > "src/day$dayStr/Day$dayStr.txt"
touch "src/day$dayStr/Day${dayStr}_test.txt"

cp "src/Day00.kt.template" "src/day$dayStr/Day$dayStr.kt"
sed -i "s/\*\*/$dayStr/g" "src/day$dayStr/Day$dayStr.kt"

git add "src/day$dayStr/Day$dayStr.kt"