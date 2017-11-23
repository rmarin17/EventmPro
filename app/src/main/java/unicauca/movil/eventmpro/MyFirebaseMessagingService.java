package unicauca.movil.eventmpro;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Calendar;

import unicauca.movil.eventmpro.db.NotificationDao;
import unicauca.movil.eventmpro.models.Mensaje;

import static android.content.ContentValues.TAG;

/**
 * Created by RicardoM on 14/10/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String men;
    NotificationDao dao;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            men = remoteMessage.getNotification().getBody();


            Calendar calendario = Calendar.getInstance();
            int hora, min,dia,mes,ano;
            String fecha_sistema,hora_sistema;

            dia = calendario.get(Calendar.DAY_OF_MONTH);
            mes = calendario.get(Calendar.MONTH)+1;
            ano = calendario.get(Calendar.YEAR);
            hora = calendario.get(Calendar.HOUR_OF_DAY);
            min = calendario.get(Calendar.MINUTE);
            fecha_sistema = dia+"/"+mes+"/"+ano;
            hora_sistema = ""+hora+":"+min+"";
            dao = new NotificationDao(this);
            Mensaje m = new Mensaje();
            m.setMensaje(men);
            m.setFecha(fecha_sistema);
            m.setHora(hora_sistema);
            dao.insert(m);
            sendNotification(men);
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void sendNotification(String body) {

        Intent notifyIntent = new Intent(this, unicauca.movil.eventmpro.Notification.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("EventmPro")
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }


}
