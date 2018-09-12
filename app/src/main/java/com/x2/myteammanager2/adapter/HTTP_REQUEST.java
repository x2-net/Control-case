package com.x2.myteammanager2.adapter;

import android.content.Context;
import android.media.MediaDrm;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;


import javax.security.auth.callback.Callback;

public class HTTP_REQUEST extends AsyncTask<Void, Void, String> {
// public class HTTP_REQUEST {

    private String controller;
    private String namespace;
    private String method;
    // private Map<String, String> params = new HashMap<String, String>();
    private Context mContext;
    private ArrayList<NameValuePair> parameter;
    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }



    public ArrayList<NameValuePair> getParameter(String key) {
        return this.parameter;
    }

    public ArrayList<NameValuePair> getAllParameter() {
        return this.parameter;
    }

    public void setParameter(String key, String value) {

        this.parameter.add(new BasicNameValuePair(key, value));
    }


    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }


    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


    // class MyAsyncTask extends AsyncTask<Void, Void, String> {

    // private ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
    private OnEventListener<String> mCallback;
    // private Context mContext;
    public Exception mException;
    public Bundle myBundle;
    String result = "";
    InputStream inputStream = null;


    // Constructor
    // public MyAsyncTask (Context context, OnEventListener callback ){
    public HTTP_REQUEST(Context context, ProgressBar progressBar, OnEventListener callback) {

        mCallback = callback;
        mContext = context;

        this.parameter = new ArrayList<>();


    }


    protected void onPreExecute() {
        // loadingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Void... params) {



        StringBuilder s = new StringBuilder( this.getHost() );
            /*s.append("&params=");
            s.append("username:" + myBundle.getString("username"));
            s.append(",password:" + myBundle.getString("password"));
            s.append(",deviceToken:" + myBundle.getString("deviceToken"));
            s.append(",device:android");*/


        String url_select = s.toString();

        Log.e("GetParams", getAllParameter().toString());


        // ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        try {
            // Set up HTTP post

            // HttpClient is more then less deprecated. Need to change to URLConnection
            HttpClient httpClient = new DefaultHttpClient();


            HttpPost httpPost = new HttpPost(url_select);
            httpPost.setEntity(new UrlEncodedFormEntity(getAllParameter()));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            // Read content & Log
            inputStream = httpEntity.getContent();


        } catch (UnsupportedEncodingException e1) {
            // Log.e("UnsupportedEncodingException", e1.toString());
            e1.printStackTrace();
        } catch (ClientProtocolException e2) {
            Log.e("ClientProtocolException", e2.toString());
            e2.printStackTrace();
        } catch (IllegalStateException e3) {
            Log.e("IllegalStateException", e3.toString());
            e3.printStackTrace();
        } catch (IOException e4) {
            Log.e("IOException", e4.toString());
            e4.printStackTrace();
        }


        // Convert response to string using String Builder
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder sBuilder = new StringBuilder();

            String line = null;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            result = sBuilder.toString();

            // Log.e("StringBuilding", "Error converting result " + result.toString());


        } catch (Exception e) {
            Log.e("StringBuilding", "Error converting result " + e.toString());
        }


        // Toast toast = Toast.makeText(getBaseContext(),result.toString(), Toast.LENGTH_LONG);
        // toast.show();


        return result;

    } // protected Void doInBackground(String... params)


    protected void onPostExecute(String v) {
        //parse JSON data

        // Log.e("Result of Test", result.toString()) ;

        try {

            if (mCallback != null) {


                JSONObject jsonObject   = new JSONObject(result);

                // JSONObject jsonArray    = new JSONObject(jsonObject.optString("data"));
                // Log.v("Message AS ", jsonArray.toString());
                // JSONObject data = new JSONObject(jArray.optString("data"));


                JSONArray jsonArray = jsonObject.getJSONArray("data");
                // Log.e("Message AS ", String.valueOf(data.optString("app_zugang").equals("null"))) ;
                // Log.e("Message AS ", jsonArray.toString() ) ;

                /*for (int i = 0; i < jsonArray.length(); i++){

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    Log.e("Message AS ", jsonObject1.optString("name"));

                }*/




                /*Return Array oder Object
                        bunlari const belirle Switch ile ayir*/









                mCallback.onSuccess(null);

                // this.progressDialog.dismiss();

            } else {

                Log.e("Message AS ", "I think this is a error");

                mCallback.onFailure(mException);

            }

            // } catch (JSONException e) {
        } catch (Exception e) {


            Log.e("JSONException", "Error: " + e.toString());
        } // catch (JSONException e)


    } // protected void onPostExecute(Void v)
    // }


}
