package android.example.com.newdicodingmovie.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.example.com.newdicodingmovie.R;
import android.example.com.newdicodingmovie.apiSource.DataSourceAPI;
import android.example.com.newdicodingmovie.parcelable.MovieParcelable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.TextUtils;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NotificationReceiver extends BroadcastReceiver {
    public static final String RELEASE_NOTIF = "type_release";
    public static final String DAILY_NOTIF = "type_daily";
    private final static int RELEASE_ID = 200;
    private final static int DAILY_ID = 100;
    private ArrayList<MovieParcelable> MovieList = new ArrayList<>();
    private static final String MESSAGE_SEND = "extra_message" ;
    private static final String TYPE_SEND = "extra_type" ;

    public NotificationReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        String type = intent.getStringExtra(TYPE_SEND);
        final String message = intent.getStringExtra(MESSAGE_SEND);
        //title akan selalu memakai String reminder release movie
        final String titleTetap = type.equalsIgnoreCase(RELEASE_NOTIF) ? context.getResources().getString(R.string.reminder_release_movie) : null;
        if (type.equalsIgnoreCase(RELEASE_NOTIF)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String date = dateFormat.format(new Date());
            MovieList.clear();
            new MovieAsyncTask().execute(DataSourceAPI.getNotificationURL(date));
            new CountDownTimer(15000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    int jumlah = MovieList.size();
                    if (MovieList.size() > 0) {
                        for (int i = 0; i < MovieList.size(); i++) {
                            String message = MovieList.get(--jumlah).getTitle() + " " + context.getResources().getString(R.string.reminder_has_release);
                            int notifId = RELEASE_ID + i;
                            showAlarmNotification(context, titleTetap, message, notifId);
                        }
                    }
                }
            }.start();
        } else if (type.equalsIgnoreCase(DAILY_NOTIF)) {
            showAlarmNotification(context, titleTetap, message, DAILY_ID);
        }
    }

    private final static String FORMAAT_TIME = "HH:mm";
    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            dateFormat.setLenient(false);
            dateFormat.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    public void setRepeatingAlarm(Context context, String time, String type, String message) {
        if (isDateInvalid(time, FORMAAT_TIME)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra(MESSAGE_SEND, message);
        intent.putExtra(TYPE_SEND, type);

        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        if (type.equals(RELEASE_NOTIF)) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RELEASE_ID, intent, 0);
            if (alarmManager != null) {
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        } else if (type.equals(DAILY_NOTIF)) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, DAILY_ID, intent, 0);
            if (alarmManager != null) {
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    public void cancelAlarm(final Context context, String type) {
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final Intent intent = new Intent(context, NotificationReceiver.class);
        int requestCode = type.equalsIgnoreCase(RELEASE_NOTIF) ? RELEASE_ID : DAILY_ID;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }


    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager channel";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_alarm_black_18dp)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }

    }

    public class MovieAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            String teks = null;
            try {
                teks = DataSourceAPI.getHttp.getFromHTTP(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return teks;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null && !TextUtils.isEmpty(s)) {
                try {
                    JSONObject jObject = new JSONObject(s);
                    JSONArray jArray = jObject.getJSONArray("results");
                    for (int i = 0; i < jArray.length(); i++) {
                        MovieParcelable movie = new MovieParcelable(jArray.getJSONObject(i));
                        MovieList.add(movie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
