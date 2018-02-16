package phg.com.automotiveoctoengine.models;

import java.util.List;

public class HistoryResponse {

    private List<HistoryRecord> results;

    public List<HistoryRecord> getResults() {
        return results;
    }

    public void setResults(List<HistoryRecord> results) {
        this.results = results;
    }
}
