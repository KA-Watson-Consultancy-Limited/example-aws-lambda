from aws_cdk import Stack, RemovalPolicy, Duration, \
    aws_ec2 as ec2, aws_rds as rds, aws_secretsmanager as secrets_manager, aws_kms as kms, aws_iam as iam, \
    aws_logs as logs, aws_ssm as ssm, aws_lambda as lambda_
from constructs import Construct
import json
from os import path


class AppStack(Stack):

    def __init__(self, scope: Construct, construct_id: str, **kwargs) -> None:
        super().__init__(scope, construct_id, **kwargs)

        vpc = ec2.Vpc(self, "Vpc", cidr="195.0.0.0/16", max_azs=3, nat_gateways=0,
                      subnet_configuration=[ec2.SubnetConfiguration(name="IsolatedSubnet",
                                                                    subnet_type=ec2.SubnetType.PRIVATE_ISOLATED,
                                                                    cidr_mask=24,
                                                                    reserved=False)])

        vpc.add_interface_endpoint("SecretsEndpoint",
                                   service=ec2.InterfaceVpcEndpointAwsService.SECRETS_MANAGER
                                   )

        rds_instance_sg = ec2.SecurityGroup(self, "RDSInstanceSecurityGroup", vpc=vpc, allow_all_outbound=True,
                                            description="Access to the RDS instance",
                                            security_group_name="rds-instances")

        db_lambda_sg = ec2.SecurityGroup(self, "DbLambdaSecurityGroup", vpc=vpc,
                                         allow_all_outbound=True,
                                         description="Access from the events lambdas to the DB",
                                         security_group_name='event-lambdas-sg')

        rds_instance_sg.add_ingress_rule(peer=db_lambda_sg, connection=ec2.Port.tcp(5432),
                                         description=f"Allow access from the event lambdas")

        db_username = "pgadmin"

        kms_key = kms.Key(self, "EncryptionKey", removal_policy=RemovalPolicy.RETAIN, policy=iam.PolicyDocument(
            statements=[
                iam.PolicyStatement(actions=["kms:*"], principals=[iam.AccountRootPrincipal()], resources=["*"])]),
                          enable_key_rotation=True)

        db_param_group = rds.ParameterGroup(self, "ParameterGroup", engine=rds.DatabaseClusterEngine.aurora_postgres(
            version=rds.AuroraPostgresEngineVersion.of("14.6", "14")),
                                            parameters={"log_statement": "all"})

        cluster_param_group = rds.ParameterGroup(self, "ClusterParameterGroup",
                                                 engine=rds.DatabaseClusterEngine.aurora_postgres(
                                                     version=rds.AuroraPostgresEngineVersion.of("14.6", "14")),
                                                 parameters={"rds.force_ssl": "0", "log_statement": "all"})

        db_secret = secrets_manager.Secret(self, "RDSInstanceSecret",
                                           generate_secret_string=secrets_manager.SecretStringGenerator(
                                               exclude_punctuation=True, generate_string_key="password",
                                               secret_string_template=json.dumps({"username": db_username})),
                                           secret_name="/config/aws-lambda/rds")

        db_subnet_group = rds.SubnetGroup(self, "SubnetGroup", description='Subnet group for the cluster',
                                          vpc=vpc, vpc_subnets=ec2.SubnetSelection(subnets=vpc.isolated_subnets))

        db_cluster = rds.DatabaseCluster(self, "DatabaseCluster", engine=rds.DatabaseClusterEngine.aurora_postgres(
            version=rds.AuroraPostgresEngineVersion.of("14.6", "14")),
                                         instance_props=rds.InstanceProps(vpc=vpc, enable_performance_insights=True,
                                                                          instance_type=ec2.InstanceType.of(
                                                                              ec2.InstanceClass.BURSTABLE3,
                                                                              ec2.InstanceSize.MEDIUM),
                                                                          parameter_group=db_param_group,
                                                                          security_groups=[rds_instance_sg],
                                                                          vpc_subnets=ec2.SubnetSelection(
                                                                              subnets=vpc.isolated_subnets)),
                                         storage_encryption_key=kms_key,
                                         monitoring_interval=Duration.minutes(1),
                                         instances=1,
                                         credentials=rds.Credentials.from_secret(db_secret, username=db_username),
                                         subnet_group=db_subnet_group,
                                         deletion_protection=True,
                                         parameter_group=cluster_param_group,
                                         default_database_name="auroradb",
                                         cloudwatch_logs_exports=["postgresql"],
                                         cloudwatch_logs_retention=logs.RetentionDays.SIX_MONTHS)

        base_policies = [
            iam.PolicyStatement(actions=["logs:CreateLogStream",
                                         "logs:CreateLogGroup",
                                         "logs:PutLogEvents",
                                         "ec2:CreateNetworkInterface",
                                         "ec2:DescribeDhcpOptions",
                                         "ec2:DescribeNetworkInterfaces",
                                         "ec2:DeleteNetworkInterface",
                                         "ec2:DescribeSubnets",
                                         "ec2:DescribeSecurityGroups",
                                         "ec2:DescribeVpcs",
                                         "ssm:Describe*",
                                         "ssm:Get*",
                                         "ssm:List*",
                                         "secretsmanager:Describe*",
                                         "secretsmanager:Get*",
                                         "secretsmanager:List*",
                                         "sqs:*",
                                         ],
                                effect=iam.Effect.ALLOW,
                                resources=['*'])]

        lambda_policy = {
            "lambda": iam.PolicyDocument(statements=[*base_policies])
        }

        lambda_role = iam.Role(self, "LambdaRole", assumed_by=iam.ServicePrincipal("lambda.amazonaws.com"),
                               inline_policies=lambda_policy)

        function = lambda_.Function(self, "MyLambda",
                                    code=lambda_.Code.from_asset(
                                        path.join("../build/libs/", "aws-lambda-0.1-lambda.zip")),
                                    handler="com.example.FunctionLambdaRuntime",
                                    runtime=lambda_.Runtime.PROVIDED_AL2,
                                    tracing=lambda_.Tracing.ACTIVE,
                                    security_groups=[db_lambda_sg],
                                    timeout=Duration.seconds(180), vpc=vpc,
                                    vpc_subnets=ec2.SubnetSelection(
                                        subnet_type=ec2.SubnetType.PRIVATE_ISOLATED),
                                    role=lambda_role, memory_size=1024,
                                    )
