package faas.impl;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import request.FactorialRequest;
import response.FactorialResponse;

public class FactorialFunctionTest {
    private static final Gson GSON = new Gson();
    @Test
    public void testProcess() throws Exception {
        FactorialRequest request = new FactorialRequest();
        request.setNumIteration(4000);
        
        JsonObject jsonResponse = FactorialFunction.process(GSON.toJsonTree(request).getAsJsonObject());
        
        FactorialResponse ibmFactorialResponse = GSON.fromJson(GSON.toJson(jsonResponse), FactorialResponse.class);
        
        Assert.assertEquals(ibmFactorialResponse.getNumIteration(), 4000);
        Assert.assertTrue(ibmFactorialResponse.isSuccess());
    }
}
