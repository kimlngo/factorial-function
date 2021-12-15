package balancer.request;

import com.google.gson.Gson;

public class FactorialRequest {
    private static final Gson gson = new Gson();
    private int numberIteration;

    public FactorialRequest() {}
    
    public FactorialRequest(int numberIteration) {
        super();
        this.numberIteration = numberIteration;
    }
    
    public FactorialRequest(String json) {
        FactorialRequest inputReq = gson.fromJson(json, FactorialRequest.class);
        this.numberIteration = inputReq.getNumberIteration();
    }

    public int getNumberIteration() {
        return numberIteration;
    }

    public void setNumberIteration(int numberIteration) {
        this.numberIteration = numberIteration;
    }

    @Override
    public String toString() {
        return "CalculateRequest [numberIteration=" + numberIteration + "]";
    }

}
