package balancer.client;

import org.json.JSONObject;

public abstract class AbstractThreadClient<T> {
    
    protected int numberIteration;

    protected JSONObject createRequestBody() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("numberIteration", this.numberIteration);
        return requestBody;
    }

    public int getNumberIteration() {
        return numberIteration;
    }

    public void setNumberIteration(int numberIteration) {
        this.numberIteration = numberIteration;
    }
}