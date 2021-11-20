package com.wsu.restintegration.ui.login;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class MyAsyncTasks extends AsyncTask<String, String, String> {
    public  JSONArray jsonObject = new JSONArray();
    private String baseurl;
    private HttpURLConnection urlConnection = null;
    private String method, path;

    public MyAsyncTasks setBaseURLandPort(String url , String port){
        this.baseurl = url+":"+port+"/";
        return this;
    }

    public MyAsyncTasks path(String path){
        this.path = path;
        return this;
    }
    public MyAsyncTasks method(String method){
        this.method = method;
        return this;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = "";
        try {
            try {
                URL url = new URL(this.baseurl+path);
                this.urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(this.method);
                InputStream in = urlConnection.getInputStream();
                InputStreamReader isw = new InputStreamReader(in);
                int data = isw.read();

                while (data != -1) {
                    result += (char) data;
                    data = isw.read();
                }
                // return the data to onPostExecute method
                return result;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Exception: " + e.getMessage();
        }
        return result;
    }

    @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog to show the user what is happening
        }



        @Override
        protected abstract void onPostExecute(String s);


}