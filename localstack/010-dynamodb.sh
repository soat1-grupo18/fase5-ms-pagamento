#!/bin/bash

set -v

awslocal dynamodb create-table \
  --table-name Pagamentos \
  --attribute-definitions '[ { "AttributeName": "id", "AttributeType": "S" }]' \
  --key-schema '[ { "AttributeName": "id", "KeyType": "HASH" }]'