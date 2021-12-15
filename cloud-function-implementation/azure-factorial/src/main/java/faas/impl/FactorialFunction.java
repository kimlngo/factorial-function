package faas.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import faas.host.FunctionHostId;
import faas.request.FactorialRequest;
import faas.response.FactorialResponse;

public class FactorialFunction {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final Gson GSON = new Gson();
    
    @FunctionName("LinuxLnPFunction")
    public HttpResponseMessage process(@HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) 
    HttpRequestMessage<Optional<FactorialRequest>> request, final ExecutionContext context) throws IOException {
        Logger logger = context.getLogger();
        String invocationId = context.getInvocationId();
        
        long startTime = System.currentTimeMillis();
        logger.info("Function Name: " + context.getFunctionName());

        String hostId = FunctionHostId.getHostId();
        
        BigInteger result = new BigInteger("1");
        try {
            FactorialRequest factorialRequest = request.getBody().orElse(new FactorialRequest(0));
            int numIteration = factorialRequest.getNumIteration();
            logger.info("Number of Iteration: " + numIteration);
            
            for(int i = 1; i <= numIteration; i++) {
                result = result.multiply(new BigInteger(String.valueOf(i)));
            }
            
            logger.info("Result: " + String.valueOf(result).substring(0, 30) + "...");
            long duration = System.currentTimeMillis() - startTime;
            logger.info("Duration: " + duration);
            
            String responseString = constructResponse(context.getFunctionName(), invocationId, hostId, numIteration, duration);
            
            return request.createResponseBuilder(HttpStatus.OK).header(CONTENT_TYPE, APPLICATION_JSON).body(responseString).build();
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Is success?: No");
            throw e;
        }
    }

    private String constructResponse(String functionName, String invocationId, String hostId, int numberIteration, long duration) {
        FactorialResponse response = new FactorialResponse(functionName, invocationId, hostId, numberIteration, duration);
        return GSON.toJson(response);
    }
}
