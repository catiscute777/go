package com.example.go.buildservises;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.go.R;

/**
 * MyReceiver is a BroadcastReceiver that listens for specific broadcasts,
 * typically triggered by an alarm or other system event. Upon receiving a
 * broadcast, it displays a notification to the user and shows a toast message.
 */
public class MyReceiver extends BroadcastReceiver {

    /**
     * Called when a broadcast is received.
     * This method is triggered when the system or another application sends a
     * broadcast that this receiver is registered to listen for. It extracts a
     * message from the intent and sends a notification to the user with this message.
     *
     * @param context The Context in which the receiver is running.
     * @param intent  The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
        sendNotification(context, message);
    }

    /**
     * Sends a notification to the user.
     * This method creates and displays a notification with the given message.
     * It also shows a Toast message to provide immediate feedback to the user.
     *
     * @param context The application context.
     * @param message The message to be displayed in the notification and Toast.
     */
    private void sendNotification(Context context, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "avital")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("GO take money")
                .setContentText(message)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManager.notify(0, notificationBuilder.build());
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        Log.d("AlarmReceiver", "Notification sent with message: " + message);
    }
}