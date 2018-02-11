package phg.com.automotiveoctoengine.controllers;


import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.widget.Toast;

import phg.com.automotiveoctoengine.Interfaces.OnPictureSavedListener;

class PhotoHandler implements PictureCallback {

    private final Context context;
    private final OnPictureSavedListener picTakenListener;

    public PhotoHandler(Context context, OnPictureSavedListener listener) {
        this.context = context;
        this.picTakenListener = listener;

    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

        File pictureFileDir = getDir();

        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

            Toast.makeText(context, "Can't create directory to save image.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "Picture_" + date + ".jpg";

        String filename = pictureFileDir.getPath() + File.separator + photoFile;

        File pictureFile = new File(filename);

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(picTakenListener != null) {
            picTakenListener.onPictureSaved(pictureFile.getAbsolutePath());
        }
    }

    private File getDir() {
        File sdDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "ToggleButtonImages");
    }
}