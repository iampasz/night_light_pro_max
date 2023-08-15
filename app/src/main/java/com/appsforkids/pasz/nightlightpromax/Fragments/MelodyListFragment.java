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
import com.appsforkids.pasz.nightlightpromax.Interfaces.ActionCalback;
import com.appsforkids.pasz.nightlightpromax.Interfaces.PlayMyMusic;
import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.MySettings;


import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MelodyListFragment extends Fragment implements View.OnClickListener {

    ImageView close_button;
    RecyclerView rv;
    int currentMusicPosition = -1;
    int clickId;
    String nameSong ="";

    Realm realm;


    public MelodyListFragment() {
        super(R.layout.my_music_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        close_button = (ImageView) view.findViewById(R.id.close_button);
        rv = (RecyclerView) view.findViewById(R.id.rv_images);

        close_button.setOnClickListener(this::onClick);


        realm = Realm.getDefaultInstance();

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);



        ActionCalback actionCalback = new ActionCalback() {
            @Override
            public void play(int position) {

            }

            @Override
            public void download(int position) {

            }

            @Override
            public void delete(int position) {

            }
        };

        ArrayList<AudioFile> arrayList = new ArrayList<>();
        arrayList.addAll(getFromRealm());

        ListMusicAdapter listMusicAdapter = new ListMusicAdapter(actionCalback, arrayList);


        rv.setAdapter(listMusicAdapter);
        //getAudios();

      //  arrayList = new ArrayList<>();
     //   arrayList.addAll(getAudios());

        //Set current music position
        //setSettings();

//        PlayMyMusic playMyMusic = new PlayMyMusic() {
//            Boolean answer = false;
//            @Override
//            public void pressPosition(int position, Boolean play_status) {
//
//                nameSong = arrayList.get(position).getNameSong();
//
//                if(play_status){
//
//                }else{
//                    nameSong ="";
//                }
//
//                if(arrayList.get(position).getLockalLink()!=null){
//                    pressPlay(position, play_status);
//                }else{
//
//                    if(!play_status){
//                        pressPlay(position, play_status);
//                    }else{
//
//                        switch (hasConnection(getContext())) {
//                            case 0:
//                                getParentFragmentManager()
//                                        .beginTransaction()
//                                        .add(R.id.my_container, SimpleMessageFragment.init(getResources().getString(R.string.message_1)))
//                                        .commit();
//                                break;
//                            case 1:
//                                getParentFragmentManager().beginTransaction().add(R.id.my_container, SimpleMessageFragment.init(getResources().getString(R.string.project_id))).commit();
//                                pressPlay(position, play_status);
//                                break;
//                            case 2:
//                                getParentFragmentManager()
//                                        .beginTransaction()
//                                        .add(R.id.my_container,
//                                                MessageFragment.init(getResources().getString(R.string.message_1), new DoThisAction() {
//                                                    @Override
//                                                    public void doThis() {
//                                                        pressPlay(position, play_status);
//                                                    }
//                                                    @Override
//                                                    public void doThis(int hours, int minutes) {
//                                                    }
//                                                    @Override
//                                                    public  void doThat() {
//                                                        listMusicAdapter.setPressedPosition();
//                                                        //listMusicAdapter.notifyDataSetChanged();
//                                                    }
//                                                })).commit();
//                                break;
//                            case 3:
//                                getParentFragmentManager().beginTransaction().add(R.id.my_container, SimpleMessageFragment.init(getResources().getString(R.string.project_id))).commit();
//                                pressPlay(position, play_status);
//                                break;
//                        }
//                    }
//                }
//            }
//
//        };
//
//        DownloadAndDelete downloadButton = new DownloadAndDelete() {
//            @Override
//            public void download(int position) {
//
//                switch (hasConnection(getContext())) {
//                    case 0:
//                        getParentFragmentManager().beginTransaction().add(R.id.my_container, SimpleMessageFragment.init(getResources().getString(R.string.message_1))).commit();
//
//                        break;
//                    case 1:
//                        pressDownload(position);
//                        break;
//                    case 2:
//                        getParentFragmentManager()
//                                .beginTransaction()
//                                .add(R.id.my_container,
//                                        MessageFragment.init(getResources().getString(R.string.project_id), new DoThisAction() {
//                                            @Override
//                                            public void doThis() {
//                                                listMusicAdapter.setPressedPosition();
//                                                pressDownload(position);
//                                            }
//                                            @Override
//                                            public void doThis(int hours, int minutes) {
//                                            }
//                                            @Override
//                                            public void doThat() {
//                                            }
//                                        })).commit();
//                        break;
//                    case 3:
//                        pressDownload(position);
//                        break;
//                }
//            }
//
//            @Override
//            public void delete(int position) {
//
//            }
//        };
//
//        listMusicAdapter = new ListMusicAdapter(playMyMusic, downloadButton, arrayList);
//        rv_melody.setAdapter(listMusicAdapter);
    }

    //Save current music position
    private void saveSettings() {

        assert Realm.getDefaultConfiguration() != null;
        Realm realm = Realm.getInstance(Realm.getDefaultConfiguration());
        realm.beginTransaction();
        MySettings settings = realm.where(MySettings.class).findFirst();
        settings.setCurrentMusicPosition(currentMusicPosition);
        settings.setCurrentMusic(nameSong);

        realm.commitTransaction();
    }

    //Get currentMusicPosition from saved settings
    public void setSettings() {
        assert Realm.getDefaultConfiguration() != null;
        Realm realm = Realm.getInstance(Realm.getDefaultConfiguration());
        realm.beginTransaction();
        MySettings settings = realm.where(MySettings.class).findFirst();
        nameSong = settings.getCurrentMusic();
        realm.commitTransaction();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // saveSettings();
    }

    private String saveLink(int position, String lockalLink) {
        assert Realm.getDefaultConfiguration() != null;
        Realm realm = Realm.getInstance(Realm.getDefaultConfiguration());
        realm.beginTransaction();
        AudioFile needFile = realm.where(AudioFile.class).equalTo("id", position).findFirst();
        needFile.setLockalLink(lockalLink);
        needFile.setStatus(true);
        realm.commitTransaction();

        return needFile.getInternetLink();
    }

    private void refreshList() {
        //listMusicAdapter.setPressedPosition();
       // arrayList.clear();
        //getAudios();
        //listMusicAdapter.notifyDataSetChanged();
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

    private void pressPlay(int position, Boolean play_status){
     //   AudioFile audioFile = arrayList.get(position);

     //   if (audioFile.getResourceLink() != 0) {
           // ((MainActivity) getActivity()).playMusic(audioFile.getResourceLink(),audioFile.getNameSong(), audioFile.authorSong, play_status);
     //   } else {
       //     if (audioFile.getLockalLink() != null) {
             //   ((MainActivity) getActivity()).playLockalMusic(audioFile, play_status);
      //      } else {
                // ((MainActivity) getActivity()).playInternetMusic(audioFile, play_status);
    //        }
     //   }

   //     if (play_status) {
  //          currentMusicPosition = position;
   //     } else {
    //        currentMusicPosition = -1;
    //    }
    }

    private void pressDownload(int position){
       // AudioFile audioFile = arrayList.get(position);

       // clickId = audioFile.getId();

      //  String file_name = audioFile.getFileName();
      //  new DownloadFileFromURL(getActivity(), file_name, new FileIsDownloaded() {
      //      @Override
      //      public void fileDownloaded(String path) {

        //        saveLink(clickId, path);
       //         refreshList();

       //         Log.i("LOADED_FILE", path+"");

      //      }
    //    }, true).execute(audioFile.getInternetLink());
    }

    private RealmResults<AudioFile> getAudios(){
        assert Realm.getDefaultConfiguration() != null;
        Realm realm = Realm.getInstance(Realm.getDefaultConfiguration());
        RealmResults<AudioFile> realmResults;
        //RealmResults<AudioFile> realmResults = realm.where(AudioFile.class).findAll();

                //Перша мелодія яка яку не потрібно завантажувати з інтернету
        AudioFile firstAudio = new AudioFile();
        firstAudio.setResourseLink(R.raw.detskaya);
        firstAudio.setNameSong("Stream");
        firstAudio.setAuthorSong("Twarres");
        firstAudio.setStatus(true);
        firstAudio.setLockalLink("plug");

     //   arrayList.add(firstAudio);

        switch (hasConnection(getContext())){
            case 0:
                realmResults = realm.where(AudioFile.class).equalTo("status", true).findAll();

                break;
            default:
                realmResults = realm.where(AudioFile.class).sort("status", Sort.DESCENDING).findAll();
        }

       // realm.commitTransaction();//??

        return realmResults;
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.close_button){
            getParentFragmentManager().beginTransaction().replace(R.id.my_container, new MainFragment()).commit();
        }

    }

    private RealmResults<AudioFile> getFromRealm(){

        RealmResults<AudioFile> array = realm.where(AudioFile.class).isNotNull("lockalLink").findAll();

        //Log.i("READARRAY", array.get(0).getLockalLink());
        //Log.i("READARRAY", array.get(1).getLockalLink());
        //Log.i("READARRAY", array.get(2).getLockalLink());


        return array;

    }
}