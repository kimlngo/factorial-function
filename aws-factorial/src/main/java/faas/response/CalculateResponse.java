package faas.response;

public class CalculateResponse {
    private int numIteration;
    private String status;
    private String requestId;
    private String hostId;

    public CalculateResponse(int numIteration, String status, String requestId, String logStreamName) {
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

    public int getNumIteration() {
        return numIteration;
    }

    public void setNumIteration(int numIteration) {
        this.numIteration = numIteration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }
}