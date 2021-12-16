# Workload-Smoother Application

## Introduction
This is a workload smoother application built on Spring Boot technology. Its purpose is to smooth the workload coming to a downstream service (e.g., FaaS cloud function). The smoothing mechanism is achieved by using unbounded queue so that all excessive requests will be queued for later processing instead of throttled as failure.

The application entry point is [BalancerApplication](src/main/java/balancer/BalancerApplication.java)

The [FactorialController](src/main/java/balancer/controller/FactorialController.java) contains all the REST API mappings (POST and GET). There are two main POST APIs which are /awsfactorial and /azurefactorial, these APIs upon invoked will forward the request corresponding downstream AWS Lambda function and Azure cloud function. Besides these two main APIs, there are other APIs for querying the awsthreadpool and azurethreadpool sizes as well as ping API (to test if the service up and running).

Within [FactorialController](src/main/java/balancer/controller/FactorialController.java), there are two [ThreadPoolExecutors](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ThreadPoolExecutor.html). These thread pools contain pre-defined number of threads to be used for downstream communications. Each thread pool executor also has unbounded queue which will store the requests if all the threads are not available.

Thread pool size for AWS Lambda thread pool executor ([**aws.thread.pool.maxSize**](src/main/resources/application.properties#L6)) and Azure thread pool executor ([**azure.thread.pool.maxSize**](src/main/resources/application.properties#L10)) can be found in [application.properties](src/main/resources/application.properties).

Besides thread pool size, application.properties also contains the URL to be used while communicating with downstream cloud function.

## AWS Lambda Configurations
In order for the external client to invoke AWS Lambda via HTTPS api, an API Gateway needs to be created. Step-by-step guide to create a HTTPS API gateway can be found in [Create-HTTP-API-Gateway-URL-for-AWS-Lambda-Function](Create-HTTP-API-Gateway-URL-for-AWS-Lambda-Function.pdf)

## Azure Cloud Function Configurations
The workload smoother should be configured with the downstream Azure URL. Step-by-step guide can be found at [Obtain-Azure-HTTP-API](Obtain-Azure-HTTP-API.pdf)

## Build, Deploy and Run the workload smoother on EC2
1. Command to build the workload smoother
```
mvn clean install
```

**workload.smoother-1.0.0-SNAPSHOT.jar** will be created under /target folder.

2. Follow [Tutorial: Get started with Amazon EC2 Linux instances](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/EC2_GetStarted.html) to launch an instance in AWS EC2

3. After the instance is up and running. Copy the **workload.smoother-1.0.0-SNAPSHOT.jar** to the instance by using either scp command or winscp client program.

Note: You'll need to provide the credential via a private key to authenticate for remote access.

4. Once logged in to the instance, run the application by the following command
```
java -jar workload.smoother-1.0.0-SNAPSHOT.jar
```
Spring Boot application will be started. For verifying purpose, user can try the following command:

curl -X GET "http://<ec2-hostname>.us-east-2.compute.amazonaws.com:5000/azurethreadpool"

curl -X GET "http://<ec2-hostname>.us-east-2.compute.amazonaws.com:5000/awsthreadpool"

curl -X GET "http://<ec2-hostname>.us-east-2.compute.amazonaws.com:5000/ping?name=peter"

