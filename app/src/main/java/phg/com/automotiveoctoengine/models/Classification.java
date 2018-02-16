package phg.com.automotiveoctoengine.models;

public class Classification {

    private double confidence;
    private String filename;
    private int prediction;
    private float[] probabilities;
    private float score;

    public Classification(int confidence, String filename, int prediction, float[] probabilities, float score) {
        this.confidence = confidence;
        this.filename = filename;
        this.prediction = prediction;
        this.probabilities = probabilities;
        this.score = score;
    }

    public double getConfidence() {
        return confidence;
    }

    public String getFilename() {
        return filename;
    }

    public int getPrediction() {
        return prediction;
    }

    public float[] getProbabilities() {
        return probabilities;
    }

    public float getScore() {
        return score;
    }
}
