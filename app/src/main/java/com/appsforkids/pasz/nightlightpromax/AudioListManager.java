package com.appsforkids.pasz.nightlightpromax;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.appsforkids.pasz.nightlightpromax.Interfaces.ActionCalback;
import com.appsforkids.pasz.nightlightpromax.Interfaces.FileIsDownloaded;
import com.appsforkids.pasz.nightlightpromax.Interfaces.GetJson;
import com.appsforkids.pasz.nightlightpromax.Interfaces.GetJsonAudioList;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CopyAudiosToRealmUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.DeleteFileUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.GetAudioFilesUseCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class AudioListManager {

    Activity activity;
    Realm realm;

    public AudioListManager(Activity activity){
        this.activity = activity;

        RealmConfiguration configuration = RealmConfigManager.getRealmConfiguration(activity);
        realm = Realm.getInstance(configuration);

    }

    public ArrayList<AudioFile> getList(){
        ArrayList<AudioFile> list = new ArrayList<>();


        GetAudioFilesUseCase getAudioFilesUseCase = new GetAudioFilesUseCase();

        list.addAll(getAudioFilesUseCase.getAudio());
        Log.i("MYTAG", list.size()+" nnn ");

        return list;
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

                        AudioFile chekAudio  = getAudioFileFromRealm("fileName", audioFile.getFileName());

                        if (chekAudio != null && chekAudio.getLockalLink() != null) {
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

    public void downloadAudioFile(String fileName, String internetLink) {

        //AudioFile audioFile = list.get(position);

        new DownloadFileFromURL(activity, fileName, new FileIsDownloaded() {
            @Override
            public void fileDownloaded(String path) {
                AudioFile audioFile = new AudioFile();
                audioFile.setFileName(fileName);
                audioFile.setLockalLink(path);
                audioFile.setStatus(true);
                addToRealm(audioFile);
            }
        }, true).execute(internetLink);
    }
    private void reloadList(int position) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
               // getJsonMusicAdapter().notifyItemChanged(position);
            }
        });
    }

    private void showDeleteDialog(int position) {

        // stopPlaying.stop(myMediaPlayer);

        ArrayList<AudioFile> list = getList();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyDialogTheme);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.custom_dialog_layout, null);
        builder.setView(dialogLayout);

        Button positive_button = dialogLayout.findViewById(R.id.positive_button);
        Button negative_button = dialogLayout.findViewById(R.id.negative_button);

        // Створіть діалог і покажіть його
        AlertDialog dialog = builder.create();

        positive_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAudioFile("position", "");
                dialog.dismiss();
            }
        });
        negative_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

    }
//    private void playAudioFile(int position) {
//
//        MyMediaPlayer player = new MyMediaPlayer(activity);
//        ArrayList<AudioFile> list = getList();
//
//        if (position == -1) {
//            player.stop();
//        } else {
//            player.stop();
//            if (list.get(position).getLockalLink() != null) {
//                player.playAudio(list.get(position).getLockalLink());
//            } else {
//                if (list.get(position).getAuthorSong() != null) {
//                    player.playAudio(list.get(position).getResourceLink());
//                }
//            }
//        }
//
//    }
//

    private void notifyMusicAdapter(int position) {
      //  getJsonMusicAdapter().notifyItemChanged(position);
    }

//    private ArrayList<AudioFile> getList() {
//
//        if (list == null) {
//            list = new ArrayList<>();
//
//            Log.i("CHEKMYLISTS", list.size()+" size");
//
//            GetAudioFilesUseCase getAudioFilesUseCase = new GetAudioFilesUseCase();
//            ArrayList<AudioFile> oflineList = getAudioFilesUseCase.getAudio();
//            list.addAll(oflineList);
//            Log.i("CHEKMYLISTS", oflineList.size()+" oflineList.size");
//            Log.i("CHEKMYLISTS", list.size()+" size");
//
//            RealmResults<AudioFile> onlineList = getAudioListFromRealm();
//            list.addAll(onlineList);
//            Log.i("CHEKMYLISTS", onlineList.size()+" onlineList.size");
//            Log.i("CHEKMYLISTS", list.size()+" size");
//
//            getFromJson(new GetJsonAudioList() {
//
//                @Override
//                public void getAudioFileArrayList(ArrayList<AudioFile> list) {
//                    list.addAll(list);
//                    Log.i("CHEKMYLISTS", list.size()+" what");
//                }
//            });
//
//            Log.i("CHEKMYLISTS", "Створюєм новий лист");
//        }else{
//            Log.i("CHEKMYLISTS", "Лист не порожній" +
//                    " повертаю що є");
//        }
//
//
//
//        return list;
//    }
//
    public void deleteAudioFile(String file_name, String internet_link){

        DeleteFileUseCase deleteFileUseCase = new DeleteFileUseCase();
        if (deleteFileUseCase.delete(activity, file_name)) {
            //list.get(position).setLockalLink(null);
            deleteAudioFileFromRealm("internetLink", internet_link);
           // notifyMusicAdapter(position);
        }

    }

    private AudioFile getAudioFileFromRealm(String fieldName, String value){

        return realm.where(AudioFile.class)
                .equalTo(fieldName, value)
                .findFirst();
    }

    private RealmResults<AudioFile> getAudioListFromRealm() {
        //return realm.where(AudioFile.class).equalTo("status", true).findAll();


        RealmResults<AudioFile> list = realm.where(AudioFile.class).findAll();
        Log.i("MYTAG", list.size()+" nnn ");
        return list;
    }

    private void deleteAudioFileFromRealm(String fieldName, String value){
        realm.where(AudioFile.class)
                .equalTo(fieldName, value)
                .findFirst()
                .deleteFromRealm();

//        AudioFile chekAudio = realm.where(AudioFile.class)
//                .equalTo("fileName", audioFile.getFileName())
//                .findFirst();
    }

    private void addToRealm(AudioFile audioFile){
        realm.beginTransaction();
        realm.copyToRealm(audioFile);
        realm.commitTransaction();

    }


//    getFromJson(new GetJsonAudioList() {
//        @Override
//        public void getAudioFileArrayList(ArrayList<AudioFile> list) {
//
//            // Создайте компаратор для сравнения элементов и учитывания пустых строк
//            Comparator<AudioFile> comparator = new Comparator<AudioFile>() {
//                @Override
//                public int compare(AudioFile audio1, AudioFile audio2) {
//                    if (audio1.getLockalLink() == null && audio2.getLockalLink() == null) {
//                        return 0; // Оба элемента пусты, считаем их равными
//                    } else if (audio1.getLockalLink() == null) {
//                        return 1; // Пустая строка будет считаться больше непустой
//                    } else if (audio2.getLockalLink() == null) {
//                        return -1; // Пустая строка будет считаться меньше непустой
//                    } else {
//                        return audio1.getLockalLink().compareTo(audio2.getLockalLink()); // Сравниваем непустые строки
//                    }
//                }
//            };
//
//            // Сортировка ArrayList с использованием компаратора
//            Collections.sort(list, comparator);
//        }
//    });
}
