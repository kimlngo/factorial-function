package faas.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.easymock.EasyMock;
import org.powermock.api.easymock.PowerMock;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import faas.request.CalculateRequest;
import faas.response.CalculateResponse;

public class FactorialFunctionTest {
    private static final String RAW_HOST_ID = "2021/05/06/[$LATEST]4c56f98889154129b0850cbdf94c2b47";
    private static final String HOST_ID = "4c56f98889154129b0850cbdf94c2b47";
    private static final String REQUEST_ID = "519c05ac-d424-480f-9ec4-a46ecacfb97e";
    private static final Gson GSON = new Gson();
    
    @Test
    public void testHandleRequest() throws IOException {
        CalculateRequest calRequest = new CalculateRequest();
        calRequest.setNumberIteration(4000);
        
        String inputRequest = String.format("{\"body\" : %s}", GSON.toJson(calRequest));
        System.out.println(inputRequest);
        
        InputStream inputStream = new ByteArrayInputStream(inputRequest.getBytes());
        OutputStream outputStream = new ByteArrayOutputStream();
        
        Context mockContext = PowerMock.createMock(Context.class);
        EasyMock.expect(mockContext.getAwsRequestId()).andReturn(REQUEST_ID);
        EasyMock.expect(mockContext.getLogStreamName()).andReturn(RAW_HOST_ID);
        PowerMock.replay(mockContext);
        
        FactorialFunction sut = new FactorialFunction();
        sut.handleRequest(inputStream, outputStream, mockContext);
        
        CalculateResponse calculateResponse = GSON.fromJson(outputStream.toString(), CalculateResponse.class);
        Assert.assertEquals(calculateResponse.getNumIteration(), 4000);
        Assert.assertEquals(calculateResponse.getStatus(), "success");
        
        Assert.assertEquals(calculateResponse.getRequestId(), REQUEST_ID);
        Assert.assertEquals(calculateResponse.getHostId(), HOST_ID);
        PowerMock.verifyAll();
    }
}
