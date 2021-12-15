package balancer.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AzureFactorialResponse {
    @JsonProperty("functionName")
    private String functionName;
    
    @JsonProperty("invocationId")
    private String invocationId;
    
    @JsonProperty("hostId")
    private String hostId;
    
    @JsonProperty("numberOfIteration")
    private int numberOfIteration;
    
    @JsonProperty("executionDuration")
    private int executionDuration;

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getInvocationId() {
        return invocationId;
    }

    public void setInvocationId(String invocationId) {
        this.invocationId = invocationId;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public int getNumberOfIteration() {
        return numberOfIteration;
    }

    public void setNumberOfIteration(int numberOfIteration) {
        this.numberOfIteration = numberOfIteration;
    }

    public int getExecutionDuration() {
        return executionDuration;
    }

    public void setExecutionDuration(int executionDuration) {
        this.executionDuration = executionDuration;
    }
}
