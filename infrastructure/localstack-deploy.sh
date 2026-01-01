#!/bin/bash

set -e # Stops the script if any command fails

aws --endpoint-url=http://localhost:4566 cloudformation delete-stack \
    --stack-name student-management || true

aws --endpoint-url=http://localhost:4566 cloudformation wait stack-delete-complete \
    --stack-name student-management || true

aws --endpoint-url=http://localhost:4566 cloudformation deploy \
    --stack-name student-management \
    --template-file "./cdk.out/localstack.template.json"

aws --endpoint-url=http://localhost:4566 elbv2 describe-load-balancers \
    --query "LoadBalancers[0].DNSName" --output text