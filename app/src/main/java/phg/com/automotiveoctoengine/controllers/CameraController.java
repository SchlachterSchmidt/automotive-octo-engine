package phg.com.automotiveoctoengine.controllers;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import phg.com.automotiveoctoengine.interfaces.OnPictureSavedListener;

import static android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE;

public class CameraController implements OnPictureSavedListener {

    // singleton CameraController class
    private static CameraController instance = null;

    private Camera camera;
    private String imagePath = "null";
    private Context context;

    private Boolean imageReceived;

    private CameraController() {
        getCamera();
        setCameraPreferences();
    }

    public static CameraController getInstance() {
        if (instance == null)
            instance = new CameraController();
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String takePicture()  {
        PhotoHandler photoHandler = new PhotoHandler(context, CameraController.this);
        SurfaceTexture surfaceTexture = new SurfaceTexture(1);

        imageReceived = false;

        try {
            camera.setPreviewTexture(surfaceTexture);
            camera.startPreview();
            camera.takePicture(null, null, photoHandler);

            synchronized(this) {
                while (!imageReceived) {
                    wait();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return imagePath;
    }

    public Camera getCameraRef() {
        if (camera != null) {
            return camera;
        }
        else {
            getCamera();
            setCameraPreferences();
            return camera;
        }
    }

    @Override
    public void onPictureSaved(String imagePath) {
        if (imagePath != null && !imagePath.equals("")) {

            synchronized(this){
                this.imagePath = imagePath;
                imageReceived = true;
                notify();
            }
        }
    }

    public void stopCamera() {
        camera.stopPreview();
    }

    public void retakeCamera() {
        getCamera();
        setCameraPreferences();
    }

    public void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    public void setPreviewDisplay(SurfaceHolder holder) {
        try {
            if (camera != null) {
                camera.setPreviewDisplay(holder);
            }
        } catch (IOException exception) {
            Log.e("Preview", "IOException caused by setPreviewDisplay()", exception);
        }
    }

    private int getCameraId() {

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(context);
        final int USE_CAMERA = sharedPrefManager.getCameraFacing();

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

    private void getCamera() {
        // moved into separate method as there are situations where we need to get the camera without
        // calling the constructor
        int cameraId = getCameraId();
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

    private void setCameraPreferences() {
        if (camera == null) getCamera();

        final String LOW = "LOW";
        final int LOW_INT = 40;
        final int SMALL_WIDTH = 640;
        final int SMALL_HEIGHT = 480;
        final String MEDIUM = "MEDIUM";
        final int MEDIUM_INT = 70;
        final int MEDIUM_WIDTH = 720;
        final int MEDIUM_HEIGHT = 1280;
        final String HIGH = "HIGH";
        final int HIGH_INT = 100;
        final int HIGH_WIDTH = 1920;
        final int HIGH_HEIGHT = 1080;

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(context);
        Camera.Parameters parameters = camera.getParameters();

        String imageCompression = sharedPrefManager.getImageCompression();
        if (imageCompression.equals(LOW)) {
            parameters.setJpegQuality(LOW_INT);
        }
        else if (imageCompression.equals(MEDIUM)) {
            parameters.setJpegQuality(MEDIUM_INT);
        }
        else if (imageCompression.equals(HIGH)) {
            parameters.setJpegQuality(HIGH_INT);
        }

        String imageQuality = sharedPrefManager.getImageQuality();
        if (imageQuality.equals(LOW)) {
            parameters.setPictureSize(SMALL_WIDTH, SMALL_HEIGHT);
        }
        else if (imageQuality.equals(MEDIUM)) {
            parameters.setPictureSize(MEDIUM_WIDTH, MEDIUM_HEIGHT);
        }
        else if (imageQuality.equals(HIGH)) {
            parameters.setPictureSize(HIGH_WIDTH, HIGH_HEIGHT);
        }

        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
    }
}