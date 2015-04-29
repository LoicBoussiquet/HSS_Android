package com.example.loic.hssgaming;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    Intent nav;
    JSONObject json;
    String urlTwitch = "https://api.twitch.tv/kraken/streams/";
    String MethodGET = "GET";
    String MethodPOST = "POST";

    TextView txtViewTitle1;
    TextView txtViewViewvers;
    CheckBox chkBoxOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtViewTitle1 = (TextView) findViewById(R.id.txtViewTitle1);
        txtViewViewvers = (TextView) findViewById(R.id.txtViewViewers);
        chkBoxOnline = (CheckBox) findViewById(R.id.chkBoxOnline1);

        new ChargementListeStreamers().execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void AccederStream (View v)
    {
        nav.setAction(Intent.ACTION_VIEW);
        startActivity(nav);
    }

    class ChargementListeStreamers extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> parametres = new ArrayList<>();

            JSONParser jParser = new JSONParser();

            json = new JSONObject();

            json = jParser.makeHttpRequest(urlTwitch + "ogaminglol", MethodGET, parametres);

            return null;
        }



        @Override
        protected void onPostExecute(String s) {

            try {
                if (json.getString("stream").equals("null"))
                {
                    txtViewTitle1.setText("Offline");
                    txtViewViewvers.setText("");
                    chkBoxOnline.setChecked(false);
                }
                else
                {
                    txtViewTitle1.setText(json.getJSONObject("stream").getJSONObject("channel").getString("name"));
                    txtViewViewvers.setText("Viewers : " + json.getJSONObject("stream").getString("viewers"));
                    chkBoxOnline.setChecked(true);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }
}
