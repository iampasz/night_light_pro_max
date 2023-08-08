package com.appsforkids.pasz.nightlightpromax;


import android.os.AsyncTask;

import com.appsforkids.pasz.nightlightpromax.Interfaces.GetJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadJson extends AsyncTask<String, String, String> {

    GetJson getJson;
    boolean answer = false;

    public ReadJson(GetJson getJson){
        this.getJson = getJson;
    }

    protected void onPreExecute() {
        super.onPreExecute();

//        pd = new ProgressDialog(MainActivity.this);
//        pd.setMessage("Please wait");
//        pd.setCancelable(false);
//        pd.show();
    }

    protected String doInBackground(String... params) {

        //Log.i("TAG_RESALT", params+"doInBackground");

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            //Log.i("TAG_RESALT", connection+"");


            InputStream stream = connection.getInputStream();

            //Log.i("TAG_RESALT", stream+"");

            reader = new BufferedReader(new InputStreamReader(stream));

            //Log.i("TAG_RESALT", reader+"");

            StringBuffer buffer = new StringBuffer();
            String line = "";

            //Log.i("TAG_RESALT", reader.readLine()+"");

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                //Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

            }

            //Log.i("TAG_RESALT", buffer.toString()+"");

            return buffer.toString();


        } catch (MalformedURLException e) {
            //Log.i("TAG_RESALT", e+"");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            //Log.i("TAG_RESALT", e+"");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                //Log.i("TAG_RESALT", e+"");
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        //Log.i("TAG_RESALT", result+"");
        if(result==null){
            getJson.noAnswer(false);
        }else{
            getJson.noAnswer(true);
            getJson.getJson(result);

            answer = true;
        }

    }
}
