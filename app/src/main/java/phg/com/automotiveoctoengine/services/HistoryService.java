package phg.com.automotiveoctoengine.services;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import phg.com.automotiveoctoengine.daos.HistoryDAO;
import phg.com.automotiveoctoengine.models.HistoryRecord;

public class HistoryService {

    private final Context context;

    public HistoryService(Context context) {
        this.context = context;
    }

    public List<HistoryRecord> getRecords() {
        HistoryDAO historyDAO = new HistoryDAO(context);

        try {
            return historyDAO.getRecords();
        } catch (IOException e) {
            e.printStackTrace();
            List<HistoryRecord> emptyList = new ArrayList<>();
            return emptyList;
        }
    }
}
