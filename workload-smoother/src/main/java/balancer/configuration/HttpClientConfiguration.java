package balancer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import balancer.client.AwsHttpThreadClient;
import balancer.client.AzureHttpThreadClient;

@Configuration
public class HttpClientConfiguration {
    
    @Bean
    @Scope("prototype")
    public AwsHttpThreadClient createAwsHttpThreadClient() {
        return new AwsHttpThreadClient();
    }
    
    @Bean
    @Scope("prototype")
    public AzureHttpThreadClient createAzureHttpThreadClient() {
        return new AzureHttpThreadClient();
    }
}
