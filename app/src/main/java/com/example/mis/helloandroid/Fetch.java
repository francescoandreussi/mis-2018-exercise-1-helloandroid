package com.example.mis.helloandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Html;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SyncFailedException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownServiceException;

public class Fetch extends AsyncTask<URL,Void,String>{
    private String data;
    private Context myContext;

    //
    public Fetch(Context context){
        myContext = context;
    }

    @Override
    protected String doInBackground(URL...urls) {
        String id = "";
        try{
            HttpURLConnection httpURLConnection = (HttpURLConnection) urls[0].openConnection();
            try {
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }
            } catch (UnknownServiceException e) {
                id = "Unknown service";
            } finally {
                httpURLConnection.disconnect();
            }
        } catch (IOException e) {
            id = "Impossible to connect";
        }
        return id;
    }

    @Override
    protected void onPostExecute(String id) {
        super.onPostExecute(id);
        if (id.equals("Impossible to connect")) {
            Toast toast = Toast.makeText(myContext, id, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else if (id.equals("Unknown service")) {
            Toast toast = Toast.makeText(myContext, id, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            MainActivity.result.setText(Html.fromHtml(this.data));
        }
    }
}
