#!/usr/bin/env python3
import os

from aws_cdk import Environment, App

from cdk.app_stack import AppStack

env = Environment(
    account=os.environ.get("CDK_DEPLOY_ACCOUNT", os.environ["CDK_DEFAULT_ACCOUNT"]),
    region="eu-west-2")

app = App()
vpc_stack = AppStack(app, "app-stack", env=env)

app.synth()
