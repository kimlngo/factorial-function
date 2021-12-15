package response;

public class FactorialResponse {
    private int numIteration;
    private int result;
    private boolean isSuccess;
    private String hostId;

    public FactorialResponse() {}

    public FactorialResponse(int numIteration, boolean isSuccess) {
        this.numIteration = numIteration;
        this.isSuccess = isSuccess;
    }

    public FactorialResponse(int numIteration, boolean isSuccess, String hostId) {
        this.numIteration = numIteration;
        this.isSuccess = isSuccess;
        this.hostId = hostId;
    }

    public FactorialResponse(int numIteration, int result, boolean isSuccess, String hostId) {
        this.numIteration = numIteration;
        this.result = result;
        this.isSuccess = isSuccess;
        this.hostId = hostId;
    }
    
    public int getNumIteration() {
        return numIteration;
    }

    public void setNumIteration(int numIteration) {
        this.numIteration = numIteration;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}