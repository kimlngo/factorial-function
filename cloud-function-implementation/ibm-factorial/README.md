# ibm-factorial-function
This repository folder contains the function implementation of IBM Lambda Factorial calculation. 
* The calculation logic is performed using loop iteration
* Note that the maven project should be built with Java version 8 and above
* The function memory used in the experiment is 512MB
* Build command line:
```
mvn clean package
```
* After the building, the **ibm-factorial-1.0.0-SNAPSHOT.jar** will be created under "target" folder
* If it is the first time you create the cloud function (action), you can create and deploy the source by the following command
To be noted, ibmcloud tool should be installed on your machine, please follow the IBM instructions at [Installing the stand-alone IBM Cloud CLI](https://cloud.ibm.com/docs/cli?topic=cli-install-ibmcloud-cli)
```
ibmcloud fn action create factorial-function target/ibm-factorial-1.0.0-SNAPSHOT.jar --kind java:8 --memory 512 --timeout 15000 --main faas.impl.FactorialFunction#process
```

* If you have created the cloud function, you can use the following command to update (re-deploy) the function with new modified changes
```
ibmcloud fn action update factorial-function target/ibm-factorial-1.0.0-SNAPSHOT.jar --main faas.impl.FactorialFunction#process
```

* Note: function memory can be configured by either using the [IBM Function Management Webpage](https://cloud.ibm.com/functions) or re-run the update command with a new memory option added

For example:
```
ibmcloud fn action update factorial-function target/ibm-factorial-1.0.0-SNAPSHOT.jar --memory 1024 --main faas.impl.FactorialFunction#process
```