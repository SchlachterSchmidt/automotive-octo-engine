package phg.com.automotiveoctoengine.controllers;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class CameraController{

    // singleton CameraController class
    private static CameraController instance = null;
    // NotificationController nc = new NotificationController();

    private Camera camera;
    private int cameraId;
    private Context context;

    // dummy values - to be replaced with the settings in shared preferences file
    private final int USE_CAMERA = 1;           // 1 for front camera, 0 for back camera
    private final int JPEG_COMPRESSION = 50;   // 100 = no compression, 1 maximum compression
    private final int PIC_WIDTH = 500;          // picture width in pixels
    private final int PIC_HEIGHT = 500;         // picture height in pixels
    private final String FLASH_OFF = "FLASH_MODE_OFF";

    private CameraController() {
        getCamera();
        // setCameraPreferences();
    }

    private void getCamera() {
        // moved into separate method as there are situations where we need to get the camera without
        // calling the constructor
        cameraId = getCameraId();
        if (cameraId < 0)
            Toast.makeText(context, "No camera found", Toast.LENGTH_SHORT).show();
        else {
            try {
                camera = Camera.open(cameraId);
            } catch (Exception e) {
                Toast.makeText(context, "Camera found but in use by other app", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static CameraController getInstance() {
        if (instance == null)
            instance = new CameraController();
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void takePicture() {
        Log.d(" Camera Controller", " taking pic");
        SurfaceTexture surfaceTexture = new SurfaceTexture(1);
        try {
            camera.setPreviewTexture(surfaceTexture);
        } catch (IOException e) {
            e.printStackTrace();
        }

        camera.startPreview();
        // works on emulator and device
        try {
            camera.takePicture(null, null, new PhotoHandler(context));
            //nc.redFlashLight(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopCamera() {
        camera.stopPreview();
    }

    public void retakeCamera() {
        getCamera();
        // setCameraPreferences();
    }

    public void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    private int getCameraId() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();

        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == USE_CAMERA) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    // ToDo: somehow doesn't set the preferences
    // images remain 640 by 480
    private void setCameraPreferences() {
        if (camera == null) getCamera();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setJpegQuality(JPEG_COMPRESSION);
        parameters.setPictureSize(PIC_WIDTH, PIC_HEIGHT);
        parameters.setFlashMode(FLASH_OFF);
        camera.setParameters(parameters);
    }
}