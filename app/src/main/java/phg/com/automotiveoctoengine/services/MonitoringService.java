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

/**
 * Created by phg on 31/01/18.
 */

public class MonitoringService extends IntentService{

    private boolean monitoring = false;
    // dummy value, will be the selected frequency from shared preferences
    private int frequency = 200;

    public MonitoringService() {
        super("MonitoringService");
    }

    public class StopReceiver extends BroadcastReceiver {
        public static final String ACTION_STOP = "stop";

        @Override
        public void onReceive(Context context, Intent intent) {
            monitoring = false;
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(" MonitoringService", "onHandleIntent beginning");
        final CameraController cameraController = CameraController.getInstance();
        Context context = getApplicationContext();
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


        monitoring = true;

        while(monitoring==true) {
            Log.d(" Monitoring", "is on");
            cameraController.startCamera();
            try {
                sleep(frequency);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(monitoring==false){
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
}
