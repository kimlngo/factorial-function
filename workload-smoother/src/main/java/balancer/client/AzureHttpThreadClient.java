package balancer.client;

import java.util.concurrent.Callable;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import balancer.configuration.SpringContext;
import balancer.response.AzureFactorialResponse;

public class AzureHttpThreadClient extends AbstractThreadClient implements Callable {
    private static final String AZURE_FACTORIAL_URL = SpringContext.getProperties("azure.factorial.url");

    @Override
    public ResponseEntity<AzureFactorialResponse> call() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(createRequestBody().toString(), createHeaders());
        
        try {
            return restTemplate.postForEntity(AZURE_FACTORIAL_URL, request, AzureFactorialResponse.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.out.println("Exception, status code: " + e.getStatusCode());
            return new ResponseEntity<AzureFactorialResponse>(e.getStatusCode());
        }
    }
    
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
