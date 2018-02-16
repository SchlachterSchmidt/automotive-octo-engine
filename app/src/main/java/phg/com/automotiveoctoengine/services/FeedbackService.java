package phg.com.automotiveoctoengine.services;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import phg.com.automotiveoctoengine.activities.HomeActivity;
import phg.com.automotiveoctoengine.controllers.SharedPrefManager;
import phg.com.automotiveoctoengine.models.Classification;

class FeedbackService {

    private Context context;
    private static final String COLOR_OK = "#00ff00"; // hex value of green
    private static final String COLOR_WARN = "#FF9900"; // hex value of amber
    private static final String COLOR_NOK = "#CC0000"; // hex value of red

    public FeedbackService(Context context) {
        this.context = context;
    }

    public void prepareFeedback(Classification classification) {
        Intent sIntent = new Intent();
        float currentScore = classification.getScore();
        int currentPrediction = classification.getPrediction();
        Log.d("FEEDBACK CURR SCORE", String.valueOf(currentScore));
        Log.d("FEEDBACK CURR PRED", String.valueOf(currentPrediction));

        SharedPrefManager prefManager = SharedPrefManager.getInstance(context);
        prefManager.setAttentionScore(currentScore);

        // No-No prediction classes, or score drops below 8 immediately flash warning!
        if (    currentPrediction == 1
                || currentPrediction == 3
                || currentPrediction == 7
                || currentPrediction == 8
                || currentScore < 8) {
            sIntent.putExtra("COLOR", COLOR_NOK);
        }

        else if (currentPrediction == 2
                || currentPrediction == 4
                || currentPrediction == 5
                || currentScore < 5) {
            sIntent.putExtra("COLOR", COLOR_WARN);
        }
        else {
            sIntent.putExtra("COLOR", COLOR_OK);
        }

        sIntent.setAction(HomeActivity.UpdateDisplayReceiver.ACTION_UPDATE);
        updateDisplay(sIntent);
    }

    private void updateDisplay(Intent sIntent) {
        context.sendBroadcast(sIntent);
    }
}
