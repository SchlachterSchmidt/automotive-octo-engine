package phg.com.automotiveoctoengine.controllers;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

class NotificationController {

    private static final int LED_NOTIFICATION_ID= 0; //arbitrary constant

    // currently not working
    public void redFlashLight(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        notification.ledARGB = 0xFFff0000;
        notification.flags = Notification.FLAG_SHOW_LIGHTS;
        notification.ledOnMS = 100;
        notification.ledOffMS = 100;
        notificationManager.notify(LED_NOTIFICATION_ID, notification);
    }
}
