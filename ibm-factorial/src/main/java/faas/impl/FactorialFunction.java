package faas.impl;

import java.math.BigInteger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import faas.util.FunctionHostId;
import faas.util.TimeStampUtil;
import request.CalculateRequest;
import response.CalculateResponse;

public class FactorialFunction {
    private static final Gson GSON = new Gson();
    private static final String FUNCTION_NAME = "FactorialFunction";
    
    public static JsonObject process(JsonObject args) throws Exception {
        long startTime = TimeStampUtil.getTimeStamp();
        System.out.println("Function Name: " + FUNCTION_NAME);

        String hostId = FunctionHostId.getHostId();
        
        BigInteger result = new BigInteger("1");
        try {
            CalculateRequest request = GSON.fromJson(GSON.toJson(args), CalculateRequest.class);
            int numIteration = request.getNumIteration();
            System.out.println("Number of Iteration: " + numIteration);
            
            for(int i = 1; i <= numIteration; i++) {
                result = result.multiply(new BigInteger(String.valueOf(i)));
            }
            
            System.out.println("Result: " + String.valueOf(result).substring(0, 30) + "...");
            
            CalculateResponse response = new CalculateResponse(numIteration, true, hostId);
            return GSON.toJsonTree(response).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Is success?: No");
            throw e;
        } finally {
            System.out.println("Duration: " + (TimeStampUtil.getTimeStamp() - startTime));
        }
    }
}
