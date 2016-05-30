package com.example.vutruong.groupproject2.utilities;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by VuTruong on 23/03/2016.
 */
public class HttpGetApiTask extends AsyncTask<String, Void, AsyncTaskResult<JSONArray>> {

    private CallbackDataInterface mCallback;
    private HttpURLConnection urlConnection;
    private BufferedReader bufferedReader;

    @Override
    protected AsyncTaskResult<JSONArray> doInBackground(String... params) {
        StringBuilder builder = new StringBuilder();
        JSONArray jsonArray = null;

        try {
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(stream));

            String inputString = "";
            Log.d("TruongVN", "inputStream " + inputString);
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }

            String finalJson = builder.toString();
            jsonArray = new JSONArray(finalJson);

        } catch (Exception ex) {
            return new AsyncTaskResult<>(ex);
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException ex) {
                return new AsyncTaskResult<>(ex);
            }
        }

        return new AsyncTaskResult<>(jsonArray);

    }


    @Override
    protected void onPostExecute(AsyncTaskResult<JSONArray> jsonArray) {
        super.onPostExecute(jsonArray);
        if (jsonArray.getError() != null) {
            mCallback.sendError(jsonArray.getError());

        } else if (isCancelled()) {
            //TODO do for cancelled

        } else {
            JSONArray result = jsonArray.getResult();
            mCallback.sendData(result);
        }
    }

    public interface CallbackDataInterface {
        void sendData(JSONArray jsonArray);

        void sendError(Exception ex);
    }

    public void setDataInstance(CallbackDataInterface interfaceInstance) {
        mCallback = interfaceInstance;
    }
}
