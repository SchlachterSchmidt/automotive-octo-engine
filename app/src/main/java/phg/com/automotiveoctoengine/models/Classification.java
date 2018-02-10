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

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getPrediction() {
        return prediction;
    }

    public void setPrediction(int prediction) {
        this.prediction = prediction;
    }

    public float[] getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(float[] probabilities) {
        this.probabilities = probabilities;
    }

    public float getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
