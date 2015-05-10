package com.example.loic.hssgaming;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class StreamNotificationService extends IntentService {

    JSONObject json;
    String urlTwitch = "https://api.twitch.tv/kraken/streams/";
    String MethodGET = "GET";

    public StreamNotificationService() {
        super("StreamNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        List<NameValuePair> parametres = new ArrayList<>();

        List<String> streamers = Arrays.asList(getResources().getStringArray(R.array.streamers));

        JSONParser jParser = new JSONParser();
        json = new JSONObject();

        for (int i = 0; i < streamers.size(); i++) {
            json = jParser.makeHttpRequest(urlTwitch + streamers.get(i), MethodGET, parametres);

            try {
                if (json.getString("stream").equals("null")) {

                } else {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                    PendingIntent notificationIntent = PendingIntent.getActivity(this, 0, new Intent(this, ActivityStream.class).putExtra("streamer", streamers.get(i)), PendingIntent.FLAG_UPDATE_CURRENT);

                    builder.setAutoCancel(true)
                            .setTicker(streamers.get(i) + " est en train de streamer")
                            .setContentTitle(streamers.get(i) + " est en train de streamer")
                            .setContentText("Cliquez ici pour regarder son stream")
                            .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                            .setSmallIcon(R.drawable.abc_ic_clear_mtrl_alpha)
                            .setContentIntent(notificationIntent);

                    Notification notification = builder.build();
                    NotificationManager manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);


                    manager.notify(0, notification);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}
