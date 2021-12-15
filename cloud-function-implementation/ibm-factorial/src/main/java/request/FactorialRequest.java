package request;

public class FactorialRequest {
    private int numberIteration;

    public FactorialRequest() {}
    
    public FactorialRequest(int numIteration) {
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