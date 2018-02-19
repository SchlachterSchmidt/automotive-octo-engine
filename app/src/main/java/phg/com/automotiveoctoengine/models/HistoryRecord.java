package phg.com.automotiveoctoengine.models;

public class HistoryRecord {

    private int id;
    private String link;
    private float predicted_label;
    private String taken_at;
    private float distraction_score;

    public float getDistractionScore() {
        return distraction_score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
