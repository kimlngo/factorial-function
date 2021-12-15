#Workload-Smoother Application

##Introduction
This is a workload smoother application built on Spring Boot technology. Its purpose is to smooth the workload coming to a downstream service (e.g., FaaS cloud function). The smoothing mechanism is achieved by using unbounded queue so that all excessive requests will be queued for later processing instead of throttled as failure.

The application entry point is [BalancerApplication](src/main/java/balancer/BalancerApplication.java)

The [FactorialController](src/main/java/balancer/controller/FactorialController.java) contains all the REST API mappings (POST and GET). There are two main POST APIs which are /awsfactorial and /azurefactorial, these APIS upon invoked will forward the request corresponding downstream AWS Lambda function and Azure cloud function. Besides these two main APIS, there are other APIS for querying the awsthreadpool and azurethreadpool sizes as well as ping API (to test if the service up and running).

Within [FactorialController](src/main/java/balancer/controller/FactorialController.java), there are two [ThreadPoolExecutors](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ThreadPoolExecutor.html). These thread pools contain pre-defined number of threads to be used for downstream communications. Each thread pool executor also has unbounded queue which will store the requests if all the threads are not available.

Thread pool size for AWS Lambda thread pool executor (**aws.thread.pool.maxSize**) and Azure thread pool executor (**azure.thread.pool.maxSize**) can be found in [application.properties](src/main/resources/application.properties).

Besides thread pool size, application.properties also contains the URL and credential to be used while communicating with downstream cloud function.

##AWS Lambda Configurations
In order for the external client to invoke AWS Lambda via HTTPS api, an API Gateway needs to be created. Step-by-step guide to create a HTTPS API gateway can be found in [Create-HTTP-API-Gateway-URL-for-AWS-Lambda-Function](Create-HTTP-API-Gateway-URL-for-AWS-Lambda-Function.pdf)

##Azure Cloud Function Configurations