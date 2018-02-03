package phg.com.automotiveoctoengine.models;

public class Classification {

    private double confidence;
    private String filename;
    private int prediction;
    private double[] probabilities;
    private double score;

    public Classification(int confidence, String filename, int prediction, double[] probabilities, double score) {
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

    public double[] getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(double[] probabilities) {
        this.probabilities = probabilities;
    }

    public double getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
