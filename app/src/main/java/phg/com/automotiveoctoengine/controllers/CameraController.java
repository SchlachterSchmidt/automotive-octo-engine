package phg.com.automotiveoctoengine.controllers;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.widget.Toast;

import java.io.IOException;

import phg.com.automotiveoctoengine.interfaces.OnPictureSavedListener;

import static java.lang.Thread.sleep;

public class CameraController implements OnPictureSavedListener {

    // singleton CameraController class
    private static CameraController instance = null;
    // NotificationController nc = new NotificationController();

    private Camera camera;
    private String imagePath = "null";
    private Context context;

    private CameraController() {
        getCamera();
        // setCameraPreferences();
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
        try {
            camera.setPreviewTexture(surfaceTexture);
        } catch (IOException e) {
            e.printStackTrace();
        }

        camera.startPreview();
        // works on emulator and device
        try {
            camera.takePicture(null, null, photoHandler);
            //nc.redFlashLight(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ToDo: sort this unsynchronized shit out... this is the ugliest possible hackaround
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return imagePath;
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
            int USE_CAMERA = 1;
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
        int JPEG_COMPRESSION = 50;
        int PIC_WIDTH = 500;
        int PIC_HEIGHT = 500;
        String FLASH_OFF = "FLASH_MODE_OFF";

        parameters.setJpegQuality(JPEG_COMPRESSION);
        parameters.setPictureSize(PIC_WIDTH, PIC_HEIGHT);
        parameters.setFlashMode(FLASH_OFF);
        camera.setParameters(parameters);
    }

    @Override
    public void onPictureSaved(String imagePath) {
        if (imagePath != null && !imagePath.equals("")) {
            this.imagePath = imagePath;
        }
    }
}