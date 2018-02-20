package phg.com.automotiveoctoengine.models;

public class ResponseError {

    private String error;

    public ResponseError(String error) {
        this.error = error;
    }
    
    public String getError() {
        return  error;
    }
}
