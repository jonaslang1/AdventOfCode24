#!/usr/bin/env bash

day=$1
dayStr=$(printf "%02d" "$day")

url="https://adventofcode.com/2024/day/$day"

part=$2

if ! [[ "$day" =~ ^[0-9]+$ ]]; then
  echo "Error: Day (parameter 1) must be an integer."
  exit 1
fi
if ! [[ "$part" =~ ^[0-9]+$ ]]; then
  echo "Error: Part (parameter 2) must be an integer."
  exit 1
fi

source .env

# Get the example for the day
html_content=$(curl -s -b "session=$SESSION_COOKIE" "$url")
first_part=$(echo "$html_content" | awk '/<article class="day-desc"><h2>/,/<\/article>/')
second_part=$(echo "$html_content" | awk '/<article class="day-desc"><h2 id="part2">/,/<\/article>/')

if [ "$part" -eq 1 ]; then
    actual_part=$first_part
else
    actual_part=$second_part
fi

# Extract the example input
example_input=$(echo "$actual_part" | awk '/<pre><code>/,/<\/code><\/pre>/' | sed 's/<[^>]*>//g')
if [ "$part" -eq 1 ]; then
  printf "Example input:\n%s\n" "$example_input"
  echo "$example_input" > "src/day$dayStr/Day${dayStr}_test.txt"
  echo "Example input file created: src/day$dayStr/Day${dayStr}_test.txt with $(wc -l < "src/day$dayStr/Day${dayStr}_test.txt" | tr -d ' ') lines"
fi
# Extract the example result
example_result=$(echo "$actual_part" | awk '/<p>/,/<\/p>/p' | sed -n 's/.*<code><em>\(.*\)<\/em><\/code>.*/\1/p')
echo "Example result for part $part: $example_result"
echo "Result check added for part $part"

sed -i '' "s/\/\/ \*$part\*/check(part$part(testInput) == $example_result)/g" "src/day$dayStr/Day$dayStr.kt"
