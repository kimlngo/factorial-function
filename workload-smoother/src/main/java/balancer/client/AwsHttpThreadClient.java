package balancer.client;

import java.util.concurrent.Callable;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import balancer.configuration.SpringContext;
import balancer.response.AwsFactorialResponse;

public class AwsHttpThreadClient extends AbstractThreadClient implements Callable {
    private static final String AWS_FACTORIAL_URL = SpringContext.getProperties("aws.factorial.url");
    
    @Override
    public AwsFactorialResponse call() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(createRequestBody().toString(), createHeaders());
        
        return restTemplate.postForObject(AWS_FACTORIAL_URL, request, AwsFactorialResponse.class);
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
