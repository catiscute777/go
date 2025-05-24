package com.example.go.forbuyer;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.go.Class.FB;
import com.example.go.Class.Users;
import com.example.go.Class.text;
import com.example.go.R;
import com.example.go.buildservises.MyReceiver;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * FirebasedatabseService is a background service that listens for changes in a Firebase Realtime Database.
 * When a specific change is detected (a matching message in a user's request list),
 * it schedules an alarm to notify the user.
 */
public class FirebasedatabseService extends Service {

    /**
     * Called when the service is started.
     * This method initializes the service by creating a notification channel,
     * retrieving user-specific data from the database, and setting up a listener
     * to monitor for changes in the user's data.
     *
     * @param intent  The Intent supplied to {@link Context#startService}, as given.
     * @param flags   Additional data about this start request.
     * @param startId A unique integer representing this specific request to start.
     * @return The return value indicates what semantics the system should use for the service's current started state.
     */
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        startForegroundService();
        String refernce = intent.getStringExtra("myuser");
        refernce = refernce.substring(refernce.lastIndexOf("/") + 1);
        String email = intent.getStringExtra("email");
        String text = intent.getStringExtra("msg");
        com.example.go.Class.text message = new text(email, text);
        DatabaseReference myref = FB.F.getReference("Users/" + refernce);
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users n = snapshot.getValue(Users.class);

                for (int i = 0; i < n.getRequests().size(); i++) {
                    if (n.getRequests().get(i).getemailuser().equals(message.getemailuser()) && (n.getRequests().get(i).getContent().equals(message.getContent()))&&(!n.getRequests().get(i).getIsRead())) {
                        Log.d("msg", "true");
                        scheduleAlarm("you can go take the money");
                        n.getRequests().get(i).setIsReadtrue();
                        myref.setValue(n);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return START_REDELIVER_INTENT;
    }
    /**
     *  Starts a foreground service with a notification.
     *  This method creates a notification with specific properties,
     *  sets it as the foreground service, and starts the service.
     *  The notification channel is created if the Android version is Oreo or higher.
     * */

    private void startForegroundService() {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "avital")
                .setContentTitle("wait for approve message from seller")
                .setContentText("app CashexChange is running in the background.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        startForeground(1, notification.build());
    }

    /**
     * Creates a notification channel for sending notifications.
     * This method checks if the Android version is Oreo or higher and, if so,
     * creates a notification channel with specific properties.
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Database Change Channel";
            String description = "Channel for database change notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("avital", name, importance);
            channel.setDescription(description);
            if (channel != null) {
                Log.d("MainActivity", "Notification channel exists: " + channel.getName());
            } else {
                Log.d("MainActivity", "Notification channel does not exist.");
            }
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }


    /**
     * Called when a client is binding to the service with bindService().
     *
     * @param intent The Intent that was used to bind to this service.
     * @return Return null in this case, because the service is not designed to bind.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    /**
     * Schedules an alarm to notify the user.
     * This method creates an intent to broadcast a message via MyReceiver and schedules
     * an alarm that will trigger the broadcast at the current time.
     *
     * @param youCanGoTakeMoney The message to be broadcasted to the user.
     */
    @SuppressLint("ScheduleExactAlarm")
    private void scheduleAlarm(String youCanGoTakeMoney) {
        Log.d("msg", "working");
        Intent intent = new Intent(this, MyReceiver.class);
        intent.putExtra("message", youCanGoTakeMoney);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);

    }
}