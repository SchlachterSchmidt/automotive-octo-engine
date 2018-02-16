package phg.com.automotiveoctoengine.models;

public class HistoryRecord {

    private int id;
    private String link;
    private float predicted_label;
    private String taken_at;
    private float distraction_score;

    public float getDistraction_score() {
        return distraction_score;
    }

    public void setDistraction_score(float distraction_score) {
        this.distraction_score = distraction_score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public float getPredicted_label() {
        return predicted_label;
    }

    public void setPredicted_label(float predicted_label) {
        this.predicted_label = predicted_label;
    }

    public String getTaken_at() {
        return taken_at;
    }

    public void setTaken_at(String taken_at) {
        this.taken_at = taken_at;
    }
}
