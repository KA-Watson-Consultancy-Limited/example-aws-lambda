## Building the native image

Run `./gradlew buildNativeImage` in the project root to build the native image zip for AWS Lambda.

## AWS Deployment

There are AWS CDK scripts to create the necessary AWS infrastructure, which includes a database. You will need Python 3
and the AWS CDK installed to run the CDK scripts.

If you have NPM install you can install the AWS CDK by running `npm install -g aws-cdk`.

After installing Python 3, run the following from the root of the project:

```
cd cdk
python3 -m venv venv
source venv/bin/activate
pip install -r requirements.txt
cdk deploy --all
```

## Running the Lambda

After the deployment is finished, you can then run the Lambda function. Under the test tab in the Lambda functions
console, select the Cloudwatch template and run the lambda function.

## Deleting the deployment

After you have finished, you can delete the created infrastructure by running `cdk destroy --all`.