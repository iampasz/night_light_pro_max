package com.appsforkids.pasz.nightlightpromax.Fragments.Melodies;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.appsforkids.pasz.nightlightpromax.Adapters.JsonMusicAdapter;
import com.appsforkids.pasz.nightlightpromax.DownloadFileFromURL;
import com.appsforkids.pasz.nightlightpromax.Interfaces.ActionCalback;
import com.appsforkids.pasz.nightlightpromax.Interfaces.FileIsDownloaded;
import com.appsforkids.pasz.nightlightpromax.Interfaces.GetJson;
import com.appsforkids.pasz.nightlightpromax.Interfaces.GetJsonAudioList;
import com.appsforkids.pasz.nightlightpromax.MainActivity;
import com.appsforkids.pasz.nightlightpromax.MyMediaPlayer;
import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.ReadJson;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.ChekInternetConnection;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CreateDefoltAudioUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CreateMyMediaPlayerUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.DeleteFileUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.GetAudioFilesFromRealmUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.GetMediaPlayerUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.InstanceRealmConfigurationUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.RemoveAudioFromRealmUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.StopPlayingUseCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.realm.Realm;
import io.realm.RealmResults;

public class ComonMelody extends Fragment {

    ArrayList<AudioFile> arrayList;
    RecyclerView rv_cards;
    CreateMyMediaPlayerUseCase createMyMediaPlayerUseCase = new CreateMyMediaPlayerUseCase();
    MyMediaPlayer myMediaPlayer;
    RealmResults realmResults;
    RemoveAudioFromRealmUseCase removeAudioUseCase = new RemoveAudioFromRealmUseCase();
    DeleteFileUseCase deleteFileUseCase = new DeleteFileUseCase();
    GetMediaPlayerUseCase getMediaPlayerUseCase = new GetMediaPlayerUseCase();
    JsonMusicAdapter jsonMusicAdapter;
    StopPlayingUseCase stopPlaying = new StopPlayingUseCase();
    Realm realm = new InstanceRealmConfigurationUseCase().connect();
    GetAudioFilesFromRealmUseCase getAudioFilesFromRealmUseCase = new GetAudioFilesFromRealmUseCase();
    ChekInternetConnection chekInternetConnection = new ChekInternetConnection();

    public ComonMelody() {
        super(R.layout.list_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView close_button = (ImageView) view.findViewById(R.id.close_button);

        rv_cards = view.findViewById(R.id.rv_cards);
        rv_cards.setLayoutManager(new LinearLayoutManager(getContext()));

        arrayList = new ArrayList<>();
        CreateDefoltAudioUseCase createDefoltAudioUseCase = new CreateDefoltAudioUseCase();
        arrayList.addAll(createDefoltAudioUseCase.getAudio());




        if( chekInternetConnection.execute(getContext())==0){
            realmResults = getAudioFilesFromRealmUseCase.getArray(realm);
            arrayList.addAll(realmResults);
        }else{
            getFromJson(new GetJsonAudioList() {
                @Override
                public void getAudioFileArrayList(ArrayList<AudioFile> list) {

                    // Создайте компаратор для сравнения элементов и учитывания пустых строк
                    Comparator<AudioFile> comparator = new Comparator<AudioFile>() {
                        @Override
                        public int compare(AudioFile audio1, AudioFile audio2) {
                            if (audio1.getLockalLink() == null && audio2.getLockalLink() == null) {
                                return 0; // Оба элемента пусты, считаем их равными
                            } else if (audio1.getLockalLink() == null) {
                                return 1; // Пустая строка будет считаться больше непустой
                            } else if (audio2.getLockalLink() == null) {
                                return -1; // Пустая строка будет считаться меньше непустой
                            } else {
                                return audio1.getLockalLink().compareTo(audio2.getLockalLink()); // Сравниваем непустые строки
                            }
                        }
                    };

                    // Сортировка ArrayList с использованием компаратора
                    Collections.sort(list, comparator);

                    arrayList.addAll(list);
                    jsonMusicAdapter.notifyDataSetChanged();

                }
            });
        }


        jsonMusicAdapter = new JsonMusicAdapter(new ActionCalback() {
            @Override
            public void play(int position) {

                if (position == -1) {
                    stopPlaying.stop(myMediaPlayer);
                } else {
                    stopPlaying.stop(myMediaPlayer);

                    if (arrayList.get(position).getLockalLink() != null) {
                        myMediaPlayer.playAudio(arrayList.get(position).getLockalLink());
                    } else {
                        if (arrayList.get(position).getAuthorSong() != null) {
                            myMediaPlayer.playAudio(arrayList.get(position).getResourceLink());
                        }
                    }
                }

            }

            @Override
            public void download(int position) {
                pressDownload(arrayList.get(position), position);
            }

            @Override
            public void delete(int position) throws IOException {

                stopPlaying.stop(myMediaPlayer);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme);
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.custom_dialog_layout, null);
                builder.setView(dialogLayout);

                Button positive_button = dialogLayout.findViewById(R.id.positive_button);
                Button negative_button = dialogLayout.findViewById(R.id.negative_button);

                // Створіть діалог і покажіть його
                AlertDialog dialog = builder.create();

                positive_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AudioFile audioFile = arrayList.get(position);

                        String file_name = audioFile.getFileName();
                        String internet_link = audioFile.getInternetLink();

                        if (deleteFileUseCase.delete(getActivity(), file_name)) {
                            arrayList.get(position).setLockalLink(null);
                            removeAudioUseCase.remove(realm, arrayList.get(position).getInternetLink());
                            jsonMusicAdapter.notifyItemChanged(position);
                        }

                        dialog.dismiss();

                    }
                });

                negative_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                //dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_menu4c);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();





            }
        }, arrayList);
        rv_cards.setAdapter(jsonMusicAdapter);
        myMediaPlayer = getMediaPlayerUseCase.getPlayer(getActivity());


        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getParentFragmentManager()
                        .beginTransaction()
                        .remove(ComonMelody.this)
                        .commit();

            }
        });

    }


    public void getFromJson(GetJsonAudioList getJsonAudioList) {
        ArrayList<AudioFile> musicItemArrayList = new ArrayList<>();

        ReadJson readJson = new ReadJson(new GetJson() {
            @Override
            public ArrayList<AudioFile> getJson(String result) {
                try {
                    String jsonText = result;
                    JSONObject jsonRoot = new JSONObject(jsonText);
                    JSONArray jsonArray = jsonRoot.getJSONArray("music");
                    //Toast.makeText(context, jsonArray.length()+" ", Toast.LENGTH_SHORT).show();
                    for (int i = 0; jsonArray.length() > i; i++) {
                        AudioFile audioFile = new AudioFile();
                        audioFile.setId(jsonArray.getJSONObject(i).getInt("id"));
                        audioFile.setNameSong(jsonArray.getJSONObject(i).getString("name"));
                        audioFile.setFileName(jsonArray.getJSONObject(i).getString("file_name"));
                        audioFile.setAuthorSong(jsonArray.getJSONObject(i).getString("author"));
                        audioFile.setInternetLink(jsonArray.getJSONObject(i).getString("internet_link"));
                        audioFile.setStatus(jsonArray.getJSONObject(i).getBoolean("status"));
                        AudioFile chekAudio = realm.where(AudioFile.class)
                                .equalTo("fileName", audioFile.getFileName())
                                .findFirst();

                        Log.i("CHHHHEK", chekAudio + " CCC ");

                        if (chekAudio != null && chekAudio.getLockalLink() != null) {
                            //musicItemArrayList.add(audioFile);
                            audioFile.setLockalLink(chekAudio.getLockalLink());

                        }
                        musicItemArrayList.add(audioFile);


                    }

                    getJsonAudioList.getAudioFileArrayList(musicItemArrayList);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void noAnswer(Boolean answer) {

            }
        });
        readJson.execute("https://koko-oko.com/audio/music.json");

    }

    private void pressDownload(AudioFile audioFile, int position) {

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
                //Log.i("CHEK_DOWNLOAD_BUTTON", jsonMusicAdapter +" renewsition"+position);

                reloadLiat(position);

                //jsonMusicAdapter.notifyDataSetChanged();
            }
        }, true).execute(audioFile.getInternetLink());


    }

    private void reloadLiat(int position) {

        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {

                jsonMusicAdapter.notifyItemChanged(position);
                // Stuff that updates the UI

            }
        });
    }
}