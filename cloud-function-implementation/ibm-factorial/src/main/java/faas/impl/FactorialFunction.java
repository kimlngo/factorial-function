package faas.impl;

import java.math.BigInteger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import faas.host.FunctionHostId;
import request.FactorialRequest;
import response.FactorialResponse;

public class FactorialFunction {
    private static final Gson GSON = new Gson();
    private static final String FUNCTION_NAME = "FactorialFunction";
    
    public static JsonObject process(JsonObject args) throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("Function Name: " + FUNCTION_NAME);

        String hostId = FunctionHostId.getHostId();
        
        BigInteger result = new BigInteger("1");
        try {
            FactorialRequest request = GSON.fromJson(GSON.toJson(args), FactorialRequest.class);
            int numIteration = request.getNumIteration();
            System.out.println("Number of Iteration: " + numIteration);
            
            for(int i = 1; i <= numIteration; i++) {
                result = result.multiply(new BigInteger(String.valueOf(i)));
            }
            
            System.out.println("Result: " + String.valueOf(result).substring(0, 30) + "...");
            
            FactorialResponse response = new FactorialResponse(numIteration, true, hostId);
            return GSON.toJsonTree(response).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Is success?: No");
            throw e;
        } finally {
            System.out.println("Duration: " + (System.currentTimeMillis() - startTime));
        }
    }
}
