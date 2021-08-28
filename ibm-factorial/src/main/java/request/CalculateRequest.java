package request;

public class CalculateRequest {
    private int numberIteration;

    public CalculateRequest() {}
    
    public CalculateRequest(int numIteration) {
        super();
        this.numberIteration = numIteration;
    }

    public int getNumIteration() {
        return numberIteration;
    }

    public void setNumIteration(int numIteration) {
        this.numberIteration = numIteration;
    }
}