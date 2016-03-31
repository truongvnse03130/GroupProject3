package com.example.vutruong.groupproject2.utilities;

import android.os.AsyncTask;
import android.util.Log;

import com.example.vutruong.groupproject2.entity.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by VuTruong on 23/03/2016.
 */
public class HttpGetApiTask extends AsyncTask<String, Void, JSONArray> {

    private SendingBackDataInterface data;
    private HttpURLConnection urlConnection;
    private BufferedReader bufferedReader;

    @Override
    protected JSONArray doInBackground(String... params) {
        StringBuilder builder = new StringBuilder();
        JSONArray jsonArray = null;
        try {
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(stream));

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }

            String finalJson = builder.toString();
            jsonArray = new JSONArray(finalJson);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonArray;
    }


    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
        data.sendData(jsonArray);
    }

    public interface SendingBackDataInterface {
        void sendData(JSONArray jsonArray);
    }

    public void setDataInstance(SendingBackDataInterface interfaceInstance) {
        data = interfaceInstance;
    }

}
