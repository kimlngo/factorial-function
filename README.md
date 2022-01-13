# Factorial-Function
This repository contains:
* The factorial cloud function implementation on all three cloud platforms, namely: AWS Lambda, IBM Cloud Function and Azure Cloud Function.
* The workload smoother application which is built on Spring Boot technology
* JMeter profiles used to load and performance test the cloud functions as well as (cloud function + workload smoother)

# Software And Hardware Requirement
## Software Requirement
To build, compile and run the cloud function and workload smoother, user needs the following software:
* [Java version 8](https://www.oracle.com/java/technologies/javase/javase8u211-later-archive-downloads.html): used to build the cloud function and workload smoother. The experiment were conducted using Java 8 - 1.8.0_281.
* [Apache Maven](https://maven.apache.org): Maven is used to package the application for uploading the source code to AWS Console or via Command Line Interface (IBM and Azure). The experiments were conducted on Maven version 3.6.3.
* [Apache JMeter](https://jmeter.apache.org): JMeter is used to load test the cloud function as well as (cloud function + workload smoother). The instruction to install JMeter can be found at [Step to install JMeter](jmeter-profiles/document/Step-To-Install-JMeter.pdf).The experiments were conducted using JMeter version 5.2.1.
* Code editor: user can use code editor such as Eclipse or IntelliJ to view the source code and/or make modification.

## Hardware Requirement
Cloud function will be run on cloud environment hence local hardware does not have strict requirement, as long as the user's computer can build and package the source code.

Nevertheless, if user plans to run JMeter on a local machine to load test the cloud function, it should be run on a intermediate to advanced machine, for example: Intel Core i5 and above to ensure that JMeter can create expected number of threads in the test. As a double check, when load testing the cloud function using JMeter, user should install and run the [VisualVM](https://visualvm.github.io) to ensure JMeter actually has created expected number of threads.

# Artifact Composition
This artifact comprises of three child-artifacts:
1. [Cloud Function Implementation](cloud-function-implementation)
This folder contains the business logic of factorial calculation cloud function. Each cloud platform has specific signature that cloud function should comply in order to work properly. Furthermore, each cloud function also include the mechanism to retrieve the cloud function instance identifier.
* AWS Lambda: use logstreamname as the instance ID
* IBM and Azure Cloud Function: cloud function self-generate the UUID ([UUID.randomUUID()](https://docs.oracle.com/javase/7/docs/api/java/util/UUID.html)) if it finds that the running cloud function instance is newly provisioned.

Within each cloud function by cloud platform, there will be README file which contains the build and deploy commands.

2. [Workload Smoother](workload-smoother)
This folder contains the source code implementation of a Workload Smoother Spring Boot Application implemented in Java. 

The workload smoother is deployed in front of target systems to smooth the workload by introducing queues to ensure excessive requests are queued for later processing and not returned as error.

3. [JMeter Profiles](jmeter-profiles)
This folder contains two JMeter profiles to load test the cloud function as well as (cloud function + workload smoother).

# Getting Started
Objective: Function as a Service is well-known for its auto-scaling feature which cloud provider automatically manage, provision, deploy and initialize the cloud function instance. Cloud user does not need to perform these operations. Nonetheless, each cloud platform may implement auto-scaling in a different way hence it is essential to characterize for better understanding the auto-scaling performance characteristics of FaaS.

There are two experiments corresponding to two Reseach Questions provided in this artifact:

1. ***RQ1 - What are the FaaS' scalability and elasticity characteristics under heavy load test scenarios?***

In this RQ, three FaaS platforms are tested with 100 threads in JMeter. The workload intensity is controlled by two parameters:
* **Ramp Up Time**: this is the ramp-up-period in JMeter which control how long JMeter will ramp to 100 threads. There are four levels used: 100 threads ramped in 10s, 6s, 3s and 1s. The shorter the ramp up time, the more intensive the workload is.
* **Total Number of Requests**: this simulates the heaviness of the workload, there are three levels used: 1000, 3000 and 7000 requests. The higher the heavier.

With the above two parameters, different FaaS scaling characteristics can be evaluated by the following metrics:
* **The number of instances** spawn throughout the test: this reflects the scaling strategy adopted by each cloud provider.
* **The ramp duration**: is the duration cloud platform needs to ramp to expected number of instances. This is measure as the time difference between the first request and the request which was first served by the lastly added instance. For example: if a test requires 100 instances, this duration is the measure as the duration between the first request and the request which was first served by the 100<sup>th</sup> instance. This metric shows how elasticity the cloud platform is.
* **The system throughput**.
* **The system median response time**. Median response time is used to avoid the interference introduced by cold-start.

Each cloud platform will go through 12 tests (4 ramp up times x 3 workload levels) and measured the above 4 metrics for evaluation.

2. ***RQ2 - What is FaaS' performance under saturation? Does workload smoother pattern help to improve the performance?***

**Experiment 2(a)**

In this RQ, FaaS is examined under saturation scenario. To simulate saturation use case, the upper concurrency limit of each cloud platform needs to be explicitly defined:
* For AWS Lambda, user can set the [reserved concurrency](https://docs.aws.amazon.com/lambda/latest/dg/configuration-concurrency.html) to limit the maximum number of cloud function instances that cloud provider will spawn for that function. In this experiment, we set reserved concurrency to 100 instances.
* For Azure Cloud Function, each function instance can serve multiple concurrent requests so we limited this capability such that each cloud function instance will only serve one concurrent request. This can be achieved by setting **maxConcurrentRequests** in [host.json](cloud-function-implementation/azure-factorial/host.json#L10). By default, this value is set to 100.
* We did not test with IBM Cloud Function because we could not reduce its upper concurrency of 1000. A concurrency at 1000 is beyond the scope of the paper.

Saturation is simulated by configuring JMeter to use:
* 150, 200 and 250 threads for AWS Lambda.
* 5, 7 and 10 threads for Azure Cloud Function (We used these values because we noted that Azure Cloud Function only spawned 4 instances to serve the traffic and will throttle the rest of requests).

Evaluation metrics include:
* **The number of instances spawned**.
* **The number of passed, failed, total requests & success rate** (which is the ratio between passed/total number of requests)
* **The system throughput**.
* **The system median response time**.

Once this is completed. We should get the results of FaaS system under saturation scenario. We can move forward with introducing a workload smoother in between JMeter and FaaS functions. This is to justify if workload smoother can improve the FaaS system performance and which metrics can be improve and by how much.

**Experiment 2(b)**

Steps to conduct experiment with workload smoother:
* Deploy testing cloud functions
	* AWS Lambda function: follow the [How-To-Deploy-AWS-Lambda-Function](cloud-function-implementation/aws-factorial/document/How-To-Deploy-AWS-Lambda-Function.pdf) to deploy the cloud function.
	* IBM cloud function: use the command line in the [README](cloud-function-implementation/ibm-factorial/README.md)
	* Azure cloud function: use the command line in the [README](cloud-function-implementation/azure-factorial/README.md)
* Build the workload smoother.
* Start up an EC2 instance (in our test, we used t2.large to ensure no potential bottleneck).
* Copy the **workload.smoother-1.0.0-SNAPSHOT.jar** to the EC2 instance and start the application.
* Test with the JMeter points to the workload smoother instead of the cloud functions.

Note: to ensure evaluation consistency between with and without workload smoother, the total number of requests used in Experiment 2(a) and 2(b) ***should be nearly the same***. Otherwise, it is not apple-to-apple comparison.

Cite the code: [![DOI](https://zenodo.org/badge/400673905.svg)](https://zenodo.org/badge/latestdoi/400673905)
