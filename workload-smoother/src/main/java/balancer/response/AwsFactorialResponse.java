package balancer.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AwsFactorialResponse {
    @JsonProperty("numIteration")
    private int numIteration;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("requestId")
    private String requestId;
    
    @JsonProperty("hostId")
    private String hostId;

    public AwsFactorialResponse() {}
    
    public AwsFactorialResponse(int numIteration, String status, String requestId, String logStreamName) {
        super();
        this.numIteration = numIteration;
        this.status = status;
        this.requestId = requestId;
        this.hostId = logStreamName;
    }

    @Override
    public String toString() {
        return String.format("%d\t%s\t%s\t%s", numIteration, status, requestId, hostId);
    }
}
