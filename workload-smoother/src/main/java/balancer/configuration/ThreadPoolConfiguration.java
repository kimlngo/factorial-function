package balancer.configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadPoolConfiguration {
    @Value("${aws.thread.pool.maxSize}")
    private int awsMaxSize;
    
    @Value("${azure.thread.pool.maxSize}")
    private int azureMaxSize;
    
    @Bean(name="awsThreadPool")
    public ThreadPoolExecutor createAwsExcutorService() {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(awsMaxSize);
    }
    
    @Bean(name="azureThreadPool")
    public ThreadPoolExecutor createAzureExecutorService() {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(azureMaxSize);
    }
}
