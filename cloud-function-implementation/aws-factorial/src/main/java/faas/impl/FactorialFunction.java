package faas.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;

import faas.hostid.AwsHostId;
import faas.request.FactorialRequest;
import faas.response.FactorialResponse;

public class FactorialFunction implements RequestStreamHandler {
    private static final String BODY = "body";
    private static final String SUCCESS = "success";
    private static final Gson GSON = new Gson();
    
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        long startTime = System.currentTimeMillis();
        JSONParser parser = new JSONParser();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        
        try {
            JSONObject event = (JSONObject) parser.parse(reader);
            
            if(event.get(BODY) != null) {
                FactorialRequest request = new FactorialRequest(event.get(BODY).toString());
                System.out.println("request = " + request);
                
                int numIteration = request.getNumberIteration();
                BigInteger result = new BigInteger("1");
                
                for(int i = 1; i <= numIteration; i++) {
                    result = result.multiply(new BigInteger(String.valueOf(i)));
                }
                
                System.out.println("result = " + String.valueOf(result).substring(0, 30) + "...");
                
                FactorialResponse response = new FactorialResponse(numIteration, SUCCESS, context.getAwsRequestId(), AwsHostId.getHostId(context));
                output.write(GSON.toJson(response).getBytes());
            } else {
                output.write(createErrorResponse("request has no body").getBytes());
            }
        } catch (ParseException e) {
            output.write(createErrorResponse("exception happened. " + e.getMessage()).getBytes());
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            System.out.println("duration = " + duration);
        }
    }
    
    private String createErrorResponse(String errorMsg) {
        return String.format("{\"error\":\"%s\"}", errorMsg);
    }
}
