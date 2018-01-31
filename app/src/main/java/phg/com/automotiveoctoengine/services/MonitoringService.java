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

import static java.lang.Thread.sleep;

public class MonitoringService extends IntentService{

    private boolean monitoring = false;
    // dummy value, will be the selected frequency from shared preferences
    private int frequency = 1000;
    final CameraController cameraController = CameraController.getInstance();

    public MonitoringService() {
        super("MonitoringService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(" MonitoringService", "onHandleIntent beginning");
        // Context context = getApplicationContext();
        cameraController.setContext(getApplicationContext());

        IntentFilter filter = new IntentFilter(StopReceiver.ACTION_STOP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        StopReceiver receiver = new StopReceiver();
        registerReceiver(receiver, filter);
        Log.d(" MonitoringService", "onHandleIntent after start camera");

        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Monitoring started", Toast.LENGTH_SHORT).show();
            }
        });

        // while monitoring is enabled, take pics at regular intervals
        monitoring = true;
        while(monitoring==true) {
            Log.d(" Monitoring", "is on");
            cameraController.takePicture();
            try {
                sleep(frequency);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(monitoring==false) {
            cameraController.stopCamera();
            Log.d(" MonitoringService", "onHandleIntent after stop camera");

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