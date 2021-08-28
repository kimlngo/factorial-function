package faas.request;

import com.google.gson.Gson;

public class CalculateRequest {
    private static final Gson GSON = new Gson();
    private int numberIteration;

    public CalculateRequest() {}
    
    public CalculateRequest(int numberIteration) {
        super();
        this.numberIteration = numberIteration;
    }
    
    public CalculateRequest(String json) {
        CalculateRequest inputReq = GSON.fromJson(json, CalculateRequest.class);
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
