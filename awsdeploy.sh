#!/usr/bin/env sh

# exit when any command fails
set -e

# keep track of the last executed command
trap 'last_command=$current_command; current_command=$BASH_COMMAND' DEBUG
# echo an error message before exiting
trap 'echo "\"${last_command}\" command filed with exit code $?."' EXIT

echo "============================== Maven Packaging =============================="
mvn clean && mvn package;
echo "============================== AWS SAM Packaging =============================="
aws cloudformation package --template-file sam.yaml --output-template-file output-sam.yaml --s3-bucket tjin-taim-customer-lambda-bucket
echo "============================== AWS SAM Deploying =============================="
aws cloudformation deploy --template-file output-sam.yaml --stack-name TaimCustomerServiceLambda --capabilities CAPABILITY_IAM
echo "============================== AWS SAM Describing =============================="
aws cloudformation describe-stacks --stack-name TaimCustomerServiceLambda