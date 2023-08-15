package com.appsforkids.pasz.nightlightpromax.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsforkids.pasz.nightlightpromax.Adapters.ListMusicAdapter;
import com.appsforkids.pasz.nightlightpromax.DownloadFileFromURL;
import com.appsforkids.pasz.nightlightpromax.Interfaces.ActionCalback;
import com.appsforkids.pasz.nightlightpromax.Interfaces.FileIsDownloaded;
import com.appsforkids.pasz.nightlightpromax.Interfaces.GetJson;
import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.ReadJson;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class InternetListFragment extends Fragment implements View.OnClickListener {

    ImageView close_button;
    RecyclerView rv;
    public InternetListFragment() {
        super(R.layout.my_music_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        close_button = (ImageView) view.findViewById(R.id.close_button);
        rv = (RecyclerView) view.findViewById(R.id.rv_images);
        close_button.setOnClickListener(this::onClick);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        getFromJson();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // saveSettings();
    }


    public int hasConnection(final Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return 3;
        } else {
        }

        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return 2;
        } else {
        }

        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return 1;
        } else {
        }
        return 0;
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.close_button){
            getParentFragmentManager().beginTransaction().replace(R.id.my_container, new MainFragment()).commit();
        }
    }

    public void getFromJson(){
        ArrayList<AudioFile> musicItemArrayList = new ArrayList<>();
        ReadJson readJson = new ReadJson(new GetJson() {
            @Override
            public void getJson(String result) {

                try {

                    String jsonText = result;

                    JSONObject jsonRoot = new JSONObject(jsonText);
                    JSONArray jsonArray = jsonRoot.getJSONArray("music");

                    //Toast.makeText(context, jsonArray.length()+" ", Toast.LENGTH_SHORT).show();
                    for(int i = 0; jsonArray.length()>i; i++){

                        AudioFile audioFile = new AudioFile();

                        audioFile.setId(jsonArray.getJSONObject(i).getInt("id"));
                        audioFile.setNameSong(jsonArray.getJSONObject(i).getString("name"));
                        audioFile.setFileName(jsonArray.getJSONObject(i).getString("file_name"));
                        audioFile.setAuthorSong(jsonArray.getJSONObject(i).getString("author"));
                        audioFile.setInternetLink(jsonArray.getJSONObject(i).getString("internet_link"));
                        audioFile.setStatus( jsonArray.getJSONObject(i).getBoolean("status"));
                        musicItemArrayList.add(audioFile);
                    }


                    ActionCalback actionCalback = new ActionCalback() {
                        @Override
                        public void play(int position) {
                        }

                        @Override
                        public void download(int position) {
                            pressDownload(musicItemArrayList.get(position));
                        }

                        @Override
                        public void delete(int position) {
                        }
                    };


                    ListMusicAdapter listMusicAdapter = new ListMusicAdapter(actionCalback, musicItemArrayList);
                    rv.setAdapter(listMusicAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void noAnswer(Boolean answer) {

            }
        });

        readJson.execute("https://koko-oko.com/audio/music.json");

    }

    private void pressDownload(AudioFile audioFile){

        new DownloadFileFromURL(getActivity(), audioFile.getFileName(), new FileIsDownloaded() {
            @Override
            public void fileDownloaded(String path) {

                Log.i("CHEK_DOWNLOAD_BUTTON", path+" Додано до реалму "+audioFile.getFileName()+" "+ audioFile.getLockalLink()+" ");

                audioFile.setLockalLink(path);
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealm(audioFile);
                realm.commitTransaction();
                Log.i("CHEK_DOWNLOAD_BUTTON", "Додано до реалму "+audioFile.getFileName()+" "+ audioFile.getLockalLink()+" ");
            }
        }, true).execute(audioFile.getInternetLink());
    }
}