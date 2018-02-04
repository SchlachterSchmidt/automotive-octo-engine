package phg.com.automotiveoctoengine.services;


import android.content.Context;
import android.content.Intent;

import phg.com.automotiveoctoengine.activities.HomeActivity;
import phg.com.automotiveoctoengine.models.Classification;

public class FeedbackService {

    Context context;
    static final String COLOR_OK = ""; // hex value of green
    static final String COLOR_WARN = ""; // hex value of amber
    static final String COLOR_NOK = ""; // hex value of red

    public FeedbackService(Context context) {
        this.context = context;
    }

    public void prepareFeedback(Classification classification) {
        Intent sIntent = new Intent();


        String colourValue = "#00ff00";


        sIntent.putExtra("COLOR", colourValue);
        sIntent.setAction(HomeActivity.UpdateDisplayReceiver.ACTION_UPDATE);
        updateDisplay(sIntent);
    }

    private void updateDisplay(Intent sIntent) {
        context.sendBroadcast(sIntent);
    }
}
