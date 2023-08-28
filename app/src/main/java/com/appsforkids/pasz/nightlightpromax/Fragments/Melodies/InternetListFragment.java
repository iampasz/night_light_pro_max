package com.appsforkids.pasz.nightlightpromax.Fragments.Melodies;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsforkids.pasz.nightlightpromax.Adapters.JsonMusicAdapter;
import com.appsforkids.pasz.nightlightpromax.DownloadFileFromURL;
import com.appsforkids.pasz.nightlightpromax.Fragments.MainFragment;
import com.appsforkids.pasz.nightlightpromax.Interfaces.ActionCalback;
import com.appsforkids.pasz.nightlightpromax.Interfaces.FileIsDownloaded;
import com.appsforkids.pasz.nightlightpromax.Interfaces.GetJson;
import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.ReadJson;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.InstanceRealmConfigurationUseCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class InternetListFragment extends Fragment implements View.OnClickListener {

    ImageView close_button;
    RecyclerView rv;

    JsonMusicAdapter jsonMusicAdapter;
    public InternetListFragment() {
        super(R.layout.list_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i("CHEKFRAGMENT","InternetListFragment");

        close_button = (ImageView) view.findViewById(R.id.close_button);
        rv = (RecyclerView) view.findViewById(R.id.rv_cards);
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



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.close_button){
            TabAudiotFragment tabAudiotFragment = (TabAudiotFragment) getParentFragmentManager()
                    .findFragmentByTag("TAB_AUDIO_FRAGMENT");

            assert tabAudiotFragment != null;
            getParentFragmentManager()
                    .beginTransaction()
                    .remove(tabAudiotFragment)
                    .commit();
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

                    loadMyList(musicItemArrayList);

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

    private void pressDownload(AudioFile audioFile, int position){

        new DownloadFileFromURL(getActivity(), audioFile.getFileName(), new FileIsDownloaded() {
            @Override
            public void fileDownloaded(String path) {

                audioFile.setLockalLink(path);
                audioFile.setStatus(true);

                Realm realm = new InstanceRealmConfigurationUseCase().connect();

                realm.beginTransaction();
                realm.copyToRealm(audioFile);
                realm.commitTransaction();
               // Log.i("CHEK_DOWNLOAD_BUTTON", "Додано до реалму "+audioFile.getFileName()+" "+ audioFile.getLockalLink()+" ");

               // listMusicAdapter.notifyItemRemoved(position);
               //
               // listMusicAdapter.notifyDataSetChanged();
                Log.i("CHEK_DOWNLOAD_BUTTON", jsonMusicAdapter +" renewsition"+position);

                reloadLiat(position);
            }
        }, true).execute(audioFile.getInternetLink());


    }

    private void loadMyList(ArrayList<AudioFile> list){
        ActionCalback actionCalback = new ActionCalback() {
            @Override
            public void play(int position) {
            }
            @Override
            public void download(int position) {
                pressDownload(list.get(position), position);
            }
            @Override
            public void delete(int position) {
            }
        };

        jsonMusicAdapter = new JsonMusicAdapter(actionCalback, list);
        rv.setAdapter(jsonMusicAdapter);
    }

    private void reloadLiat(int position){

       getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {

                jsonMusicAdapter.notifyItemChanged(position);
                // Stuff that updates the UI

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        getFromJson();
        Log.i("RESUMEN", "HERE");

    }

}