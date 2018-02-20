package phg.com.automotiveoctoengine.models;

import java.util.List;

public class HistoryResponse {

    private List<HistoryRecord> results;

    public HistoryResponse(List<HistoryRecord> results) {
        this.results = results;
    }

    public List<HistoryRecord> getResults() {
        return results;
    }
}
