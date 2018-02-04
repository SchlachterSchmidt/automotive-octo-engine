package phg.com.automotiveoctoengine.services;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import phg.com.automotiveoctoengine.controllers.CameraController;
import phg.com.automotiveoctoengine.daos.MonitoringDAO;
import phg.com.automotiveoctoengine.models.Classification;

import static java.lang.Thread.sleep;

public class MonitoringService extends IntentService {

    // dummy value, will be the selected frequency from shared preferences
    private final int frequency = 3000;
    private boolean monitoring = false;


    private final CameraController cameraController = CameraController.getInstance();
    private final MonitoringDAO monitoringDAO = new MonitoringDAO();
    private FeedbackService feedbackService = new FeedbackService(this);

    public MonitoringService() {
        super("MonitoringService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        cameraController.setContext(getApplicationContext());

        // mHandler allows to send Toasts to the HomeActivity
        Handler mHandler = new Handler(getMainLooper());

        IntentFilter filter = new IntentFilter(StopReceiver.ACTION_STOP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        StopReceiver receiver = new StopReceiver();
        registerReceiver(receiver, filter);


        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Monitoring started", Toast.LENGTH_SHORT).show();
            }
        });

        // while monitoring is enabled, take pics at regular intervals
        monitoring = true;
        while(monitoring) {
            String imagePath = cameraController.takePicture();
            Classification classification = monitoringDAO.classify(getApplicationContext(), imagePath);
            feedbackService.prepareFeedback(classification);
            try {
                sleep(frequency);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(!monitoring) {
            cameraController.stopCamera();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Monitoring stopped", Toast.LENGTH_SHORT).show();
                }
            });
            unregisterReceiver(receiver);
            stopSelf();
        }
    }

    public class StopReceiver extends BroadcastReceiver {
        public static final String ACTION_STOP = "stop";

        @Override
        public void onReceive(Context context, Intent intent) {
            monitoring = false;
        }
    }

    public class PauseReceiver extends BroadcastReceiver {
        public static final String ACTION_PAUSE = "pause";

        @Override
        public void onReceive(Context context, Intent intent) {
            cameraController.releaseCamera();
        }
    }

    public class ResumeReceiver extends BroadcastReceiver {
        public static final String ACTION_RESUME = "resume";

        @Override
        public void onReceive(Context context, Intent intent) {
            cameraController.retakeCamera();
        }
    }
}