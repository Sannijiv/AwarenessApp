
package com.example.sanjiv.awarenessapp;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.Random;

public class NotificationService extends Service {
    private PowerManager.WakeLock mWakeLock;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();
    private DatabaseReference mDatabase = database.getReference();
    private int unique;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void handleIntent(Intent intent) {

        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "TAG");
        mWakeLock.acquire();

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        if (!cm.getBackgroundDataSetting()) {
            stopSelf();
            return;
        }

        new NotificationTask().execute();
    }

    private class NotificationTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Log.d("ADebugTag", "Process start");
            getLampInfo();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            stopSelf();
            Log.d("ADebugTag", "Process onDestroy");
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return START_NOT_STICKY;
    }


    public void onDestroy() {
        super.onDestroy();
        mWakeLock.release();
    }

    public void getLampInfo() {
        database.getReference("lampen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    LampModel lamp = snapshot.getValue(LampModel.class);
                    if (lamp.getHuidigeDecibel() > lamp.getMaxDecibel()) {
                        notificationCall(lamp);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void notificationCall(LampModel lamp) {

        NotificationCompat.Builder mNotification = new NotificationCompat.Builder(this)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_menu_manage)
                .setTicker("This is the ticker")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Tafel is te luid - Lampnaam" + lamp.getNaam())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(("Huidige geluidsniveau is: " + lamp.getHuidigeDecibel()
                        + ",geluidsniveau overschreden met " + (lamp.getHuidigeDecibel() - lamp.getMaxDecibel()))));


        Random random = new Random();
        mNotification.setAutoCancel(true);
        unique = (int) ((new Date().getTime() / 1000L) + random.nextInt(9999 - 1000) + 1 % Integer.MAX_VALUE);
        Log.d("ADebugTag", "Number = " + unique);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("menuFragment", "nav_notificaties");

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mNotification.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
        notificationManager.notify(unique, mNotification.build());


    }
}

