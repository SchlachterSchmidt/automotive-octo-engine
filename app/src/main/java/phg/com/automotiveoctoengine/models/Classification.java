package phg.com.automotiveoctoengine.models;

public class Classification {

    private final double confidence;
    private final String filename;
    private final int prediction;
    private final float[] probabilities;
    private final float score;

    public Classification(int confidence, String filename, int prediction, float[] probabilities, float score) {
        this.confidence = confidence;
        this.filename = filename;
        this.prediction = prediction;
        this.probabilities = probabilities;
        this.score = score;
    }

    public int getPrediction() {
        return prediction;
    }

    public float getScore() {
        return score;
    }
}
