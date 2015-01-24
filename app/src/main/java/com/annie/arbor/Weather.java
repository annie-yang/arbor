package com.annie.arbor;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class Weather extends AsyncTask<Double, Void, HttpResponse> {
    Context context;

    public Weather (Context context) {
        context = context;
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append((line + "\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    protected HttpResponse doInBackground(Double... coords) {
        HttpResponse response = null;
        String completeURI = "https://api.forecast.io/forecast/7022acbb75de34f8cf622cf1de8d0600/" + coords[0] + "," + coords[1];
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(completeURI));
            response = client.execute(request);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpEntity entity = response.getEntity();
        InputStream is = null;
        try {
            is = entity.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String responseString = convertStreamToString(is);
        Log.d("httpresponse", responseString);

        JSONObject respJSONObj = null;
        JSONObject JSONCurrently = null;

        String currentlyString = null;
        String currentlySummary = null;
        String currentlyTemperature = null;
        try {
            respJSONObj = new JSONObject(responseString);
            currentlyString = respJSONObj.getString("currently");
            JSONCurrently = new JSONObject(currentlyString);
            currentlySummary = JSONCurrently.getString("summary");
            currentlyTemperature = JSONCurrently.getString("temperature");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("weather", currentlySummary + ", " + currentlyTemperature);
        return response;
    }

}