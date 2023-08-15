package com.appsforkids.pasz.nightlightpromax;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.appsforkids.pasz.nightlightpromax.Interfaces.FileIsDownloaded;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import io.realm.Realm;

/**
 * Background Async Task to download file
 * */public class DownloadFileFromURL extends AsyncTask<String, String, String> {
    String file_name;
    @SuppressLint("StaticFieldLeak")
    Activity activity;
    ProgressDialog mProgressDialog;
    FileIsDownloaded fileIsDownloaded;
    Boolean showLoader = false;

    public DownloadFileFromURL(Activity activity, String file_name, FileIsDownloaded fileIsDownloaded, Boolean showLoader) {
        this.file_name = file_name;
        this.activity = activity;
        this.showLoader = showLoader;
        this.fileIsDownloaded = fileIsDownloaded;
        // instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setMessage("Downloaded: "+file_name);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
    }

    /**
     * Before starting background thread Show Progress Bar Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       //activity.showDialog(progress_bar_type);
        if(showLoader){
            mProgressDialog.show();
        }

    }

    /**
     * Downloading file in background thread
     * */
    @Override
    protected String doInBackground(@NonNull String... f_url) {

        Log.i("CHEK", f_url[0]+ " Internet ling for aploading");

        int count;
        try {
            URL url = new URL(f_url[0]);
            URLConnection connection = url.openConnection();

            connection.connect();
            // this will be useful so that you can show a tipical 0-100%
            // progress bar
            int lenghtOfFile = connection.getContentLength();

            Log.i("CHEK", lenghtOfFile+ " Розмір файлу");

            // download the file
            InputStream input = new BufferedInputStream(url.openStream(),
                    8192);

            OutputStream output = activity.openFileOutput(file_name, MODE_PRIVATE);
            byte[] data = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                // writing data to file
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
            Log.i("CHEK",  "File is loaded");
            fileIsDownloaded.fileDownloaded(activity.getFilesDir().getAbsoluteFile()+"/"+file_name+"");


        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            Log.i("CHEK",  "Файл не завантажується. Можливо проблеми з лінком або з інтернет налаштуваннями");
        }

        return null;
    }

    protected void onProgressUpdate(String... progress) {
        // if we get here, length is known, now set indeterminate to false
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgress(Integer.parseInt(progress[0]));
    }

    @Override
    protected void onPostExecute(String file_url) {
        mProgressDialog.dismiss();
       }

//    private AudioFile getAudio(int id){
//        Realm realm = Realm.getDefaultInstance();
//        return realm.where(AudioFile.class).equalTo("id", id).findFirst();
//    }
}
