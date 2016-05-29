package com.example.vutruong.groupproject2.utilities;

import android.os.AsyncTask;

import com.example.vutruong.groupproject2.fragment.ProcessDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by VuTruong on 23/03/2016.
 */
public class HttpGetApiTask extends AsyncTask<String, Void, JSONArray> {

    private CallbackDataInterface sendData;
    private HttpURLConnection urlConnection;
    private BufferedReader bufferedReader;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        sendData.showDialog();
    }

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

        } catch (Exception ex) {
            ex.printStackTrace();
            sendData.sendError(ex);
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                sendData.sendError(ex);
            }

        }

        return jsonArray;
    }


    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
        sendData.sendData(jsonArray);
        sendData.dismissDialog();
    }

    public interface CallbackDataInterface {
        void sendData(JSONArray jsonArray);
        void sendError(Exception ex);
        void showDialog();
        void dismissDialog();
    }

    public void setDataInstance(CallbackDataInterface interfaceInstance) {
        sendData = interfaceInstance;
    }

}
