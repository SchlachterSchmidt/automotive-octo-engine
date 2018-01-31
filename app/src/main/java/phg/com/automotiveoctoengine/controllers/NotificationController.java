package phg.com.automotiveoctoengine.controllers;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;


/**
 * Created by phg on 31/01/18.
 */

public class NotificationController {

    private static final int LED_NOTIFICATION_ID= 0; //arbitrary constant

    public void redFlashLight(Context context) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notif = new Notification();
        notif.ledARGB = 0xFFff0000;
        notif.flags = Notification.FLAG_SHOW_LIGHTS;
        notif.ledOnMS = 100;
        notif.ledOffMS = 100;
        nm.notify(LED_NOTIFICATION_ID, notif);
    }
}
