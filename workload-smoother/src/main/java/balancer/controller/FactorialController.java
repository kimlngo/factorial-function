package balancer.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import balancer.client.AwsHttpThreadClient;
import balancer.client.AzureHttpThreadClient;
import balancer.request.FactorialRequest;
import balancer.response.AwsFactorialResponse;
import balancer.response.AzureFactorialResponse;
import util.PoolSizeUtil;

@RestController
@SuppressWarnings("unchecked")
public class FactorialController {
    private static final Logger logger = LoggerFactory.getLogger(FactorialController.class);
    private static int pingCount = 0;
    
    @Autowired
    @Qualifier("awsThreadPool")
    private ThreadPoolExecutor awsThreadPoolExecutor;
    
    @Autowired
    @Qualifier("azureThreadPool")
    private ThreadPoolExecutor azureThreadPoolExecutor;
    
    @Autowired
    private ApplicationContext context;
    
    @PostMapping(value = "/awsfactorial", consumes ="application/json", produces = "application/json")
    public AwsFactorialResponse awsCalculateFactorial(@RequestBody FactorialRequest request) throws InterruptedException, ExecutionException {
        logger.info("Queue size: " + awsThreadPoolExecutor.getQueue().size());
        AwsHttpThreadClient awsHttpClient = context.getBean(AwsHttpThreadClient.class);
        awsHttpClient.setNumberIteration(request.getNumberIteration());
        
        Future<AwsFactorialResponse> future = awsThreadPoolExecutor.submit(awsHttpClient);
        return future.get();
    }
    
    @PostMapping(value = "/azurefactorial", consumes ="application/json", produces = "application/json")
    public ResponseEntity<AzureFactorialResponse> azureCalculateFactorial(@RequestBody FactorialRequest request) throws InterruptedException, ExecutionException {
        logger.info("Queue size: " + azureThreadPoolExecutor.getQueue().size());
        AzureHttpThreadClient azureThreadClient = context.getBean(AzureHttpThreadClient.class);
        azureThreadClient.setNumberIteration(request.getNumberIteration());
        
        Future<ResponseEntity<AzureFactorialResponse>> future = azureThreadPoolExecutor.submit(azureThreadClient);
        
        return future.get();
    }
    
    @GetMapping(value = "/ping")
    public String ping(@RequestParam(defaultValue = "world") String name) {
        System.out.println("Received request: " + (++pingCount));
        
        try {
            for(int i = 0; i < 10; i++) {
                System.out.print("... ");
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getStackTrace());
        }
        System.out.println("\nRequest processing completed");
        return "{\"message\" : \"Hello " + name + "\"}";
    }
    
    @GetMapping(value = "/awsthreadpool")
    public String getAwsThreadPool() {
        return PoolSizeUtil.getThreadPoolSizeResponse("aws.thread.pool.maxSize");
    }
    
    @GetMapping(value = "/azurethreadpool")
    public String getAzureThreadPool() {
        return PoolSizeUtil.getThreadPoolSizeResponse("azure.thread.pool.maxSize");
    }
}
