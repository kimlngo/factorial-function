package faas.request;

public class FactorialRequest {
    private int numberIteration;

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

    @Override
    public String toString() {
        return "Request [numIteration=" + numberIteration + "]";
    }

}