package phg.com.automotiveoctoengine.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;

import android.hardware.Camera;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import phg.com.automotiveoctoengine.R;
import phg.com.automotiveoctoengine.controllers.CameraController;
import phg.com.automotiveoctoengine.controllers.Preview;


public class CalibrateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate);
        calibrate();
    }


    private void calibrate() {

        Context context = this;
        CameraController cameraController = CameraController.getInstance();
        Preview preview = new Preview(context, (SurfaceView)findViewById(R.id.surfaceView));
        Camera camera = cameraController.getCameraRef();

        preview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        ((FrameLayout) findViewById(R.id.frameLayout)).addView(preview);
        preview.setKeepScreenOn(true);
        preview.setCamera(camera);
    }
}