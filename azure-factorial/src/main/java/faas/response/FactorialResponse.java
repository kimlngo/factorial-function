package faas.response;

public class FactorialResponse {
    private String functionName;
    private String invocationId;
    private String hostId;
    private int numberOfIteration;
    private long executionDuration;

    public FactorialResponse(String functionName, String invocationId, String hostId, int numberOfIteration, long executionDuration) {
        this.functionName = functionName;
        this.invocationId = invocationId;
        this.hostId = hostId;
        this.numberOfIteration = numberOfIteration;
        this.executionDuration = executionDuration;
    }

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

    public long getExecutionDuration() {
        return executionDuration;
    }

    public void setExecutionDuration(long executionDuration) {
        this.executionDuration = executionDuration;
    }

}
