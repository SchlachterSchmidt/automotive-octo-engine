package phg.com.automotiveoctoengine.models;

import java.util.List;

public class HistoryResponse {

    public HistoryResponse(List<HistoryRecord> results) {
        this.results = results;
    }

    private List<HistoryRecord> results;

    public List<HistoryRecord> getResults() {
        return results;
    }
}
