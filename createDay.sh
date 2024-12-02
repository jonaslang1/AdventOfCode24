#!/usr/bin/env bash

# This script is used to create a new day folder in the format of "dayXX" where XX is the next day number

# Set up the variables
day=$1
dayStr=$(printf "%02d" "$day")

url="https://adventofcode.com/2024/day/$day"

if ! [[ "$day" =~ ^[0-9]+$ ]]; then
  echo "Error: Day (parameter 1) must be an integer."
  exit 1
fi

source .env

# Create the package folder
mkdir "src/day$dayStr"

# Create the template code file
cp "src/Day00.kt.template" "src/day$dayStr/Day$dayStr.kt"
sed -i "s/\*\*/$dayStr/g" "src/day$dayStr/Day$dayStr.kt"
echo "Code file created: src/day$dayStr/Day$dayStr.kt"

# Create the example test
./createPartTest.sh "$day" 1

# Get the real input for the day
curl "$url/input" \
  -H 'accept: text/plain' \
  -H "cookie: session=$SESSION_COOKIE" \
  --compressed > "src/day$dayStr/Day$dayStr.txt"
echo "Input file created: src/day$dayStr/Day$dayStr.txt with $(wc --lines < "src/day$dayStr/Day$dayStr.txt") lines"

# Add the files to git
git add "src/day$dayStr/Day$dayStr.kt"
git add "src/day$dayStr/Day$dayStr.txt"
git add "src/day$dayStr/Day${dayStr}_test.txt"