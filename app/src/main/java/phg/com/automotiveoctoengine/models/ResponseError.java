package phg.com.automotiveoctoengine.models;

public class ResponseError {

    public ResponseError(String error) {
        this.error = error;
    }

    private String error;

    public String getError() {
        return  error;
    }
}
