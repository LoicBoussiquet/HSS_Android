package com.example.loic.hssgaming;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ActivityStream extends ActionBarActivity {

    TextView txtViewTitle;
    TextView txtViewViewers;
    CheckBox chkBoxOnline;

    JSONObject json;
    String urlTwitch = "https://api.twitch.tv/kraken/streams/";
    String MethodGET = "GET";
    //String MethodPOST = "POST";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        txtViewTitle = (TextView) findViewById(R.id.txtViewTitle);
        txtViewViewers = (TextView) findViewById(R.id.txtViewViewers);
        chkBoxOnline = (CheckBox) findViewById(R.id.chkBoxOnline);


        String Streamer;

        Intent stremers = getIntent();

        Streamer = stremers.getStringExtra("streamer");

        new ChargementInfoStreamer(Streamer).execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_stream, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class ChargementInfoStreamer extends AsyncTask<String,String,String>
    {
        String sStreamer;

        public ChargementInfoStreamer (String _Streamer)
        {
            sStreamer = _Streamer;
        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> parametres = new ArrayList<>();

            JSONParser jParser = new JSONParser();
            json = new JSONObject();
            json = jParser.makeHttpRequest(urlTwitch + sStreamer, MethodGET, parametres);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                if (json.getString("stream").equals("null"))
                {
                    txtViewTitle.setText("Offline");
                    txtViewViewers.setText("");
                    chkBoxOnline.setChecked(false);
                }
                else
                {
                    txtViewTitle.setText(json.getJSONObject("stream").getJSONObject("channel").getString("name"));
                    txtViewViewers.setText("Viewers : " + json.getJSONObject("stream").getString("viewers"));
                    chkBoxOnline.setChecked(true);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }
}
