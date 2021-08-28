package cpu;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import faas.impl.FactorialFunction;
import request.CalculateRequest;
import response.CalculateResponse;

public class FactorialFunctionTest {
    private static final Gson GSON = new Gson();
    @Test
    public void testProcess() throws Exception {
        CalculateRequest request = new CalculateRequest();
        request.setNumIteration(4000);
        
        JsonObject jsonResponse = FactorialFunction.process(GSON.toJsonTree(request).getAsJsonObject());
        
        CalculateResponse ibmFactorialResponse = GSON.fromJson(GSON.toJson(jsonResponse), CalculateResponse.class);
        
        Assert.assertEquals(ibmFactorialResponse.getNumIteration(), 4000);
        Assert.assertTrue(ibmFactorialResponse.isSuccess());
    }
}
