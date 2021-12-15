package com.kimlngo.functions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gson.Gson;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;

import faas.impl.FactorialFunction;
import faas.request.FactorialRequest;
import faas.response.FactorialResponse;

public class FactorialFunctionTest {
    private static final Gson GSON = new Gson();
    private static final int NUMBER_ITERATION = 4000;
    private static final String INVOCATION_ID = "123";
    private static final String FUNCTION_NAME = "FactorialFunction";
    
    @Test
    public void testHttpTriggerJava() throws Exception {
        // Setup
        @SuppressWarnings("unchecked")
        final HttpRequestMessage<Optional<FactorialRequest>> req = mock(HttpRequestMessage.class);

        FactorialRequest fibRequest = new FactorialRequest(NUMBER_ITERATION);
        
        final Optional<FactorialRequest> queryBody = Optional.of(fibRequest);
        doReturn(queryBody).when(req).getBody();

        doAnswer(new Answer<HttpResponseMessage.Builder>() {
            @Override
            public HttpResponseMessage.Builder answer(InvocationOnMock invocation) {
                HttpStatus status = (HttpStatus) invocation.getArguments()[0];
                return new HttpResponseMessageMock.HttpResponseMessageBuilderMock().status(status);
            }
        }).when(req).createResponseBuilder(any(HttpStatus.class));

        final ExecutionContext context = mock(ExecutionContext.class);
        doReturn(Logger.getGlobal()).when(context).getLogger();
        doReturn(FUNCTION_NAME).when(context).getFunctionName();
        doReturn(INVOCATION_ID).when(context).getInvocationId();

        // Invoke
        final HttpResponseMessage ret = new FactorialFunction().process(req, context);

        // Verify
        assertEquals(ret.getStatus(), HttpStatus.OK);
        
        FactorialResponse factorialResp = GSON.fromJson(String.valueOf(ret.getBody()), FactorialResponse.class);
        assertEquals(factorialResp.getNumberOfIteration(), NUMBER_ITERATION);
        assertEquals(factorialResp.getInvocationId(), INVOCATION_ID);
        assertEquals(factorialResp.getFunctionName(), FUNCTION_NAME);
    }
}
