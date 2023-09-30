package com.appsforkids.pasz.nightlightpromax;

import android.content.Context;
import android.util.Log;

import com.appsforkids.pasz.nightlightpromax.Interfaces.GetJson;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.ImageFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class AddToRealm {

    Context ctx;
    Number maxId = 0;

    public AddToRealm(Context ctx) {
        this.ctx = ctx;
    }


    private static String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br= new BufferedReader(new InputStreamReader(is));
        StringBuilder sb= new StringBuilder();
        String s= null;
        while((  s = br.readLine())!=null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }


    public void addJsonToRealm(){

        ArrayList<AudioFile> musicItemArrayList = new ArrayList<>();

        ReadJson readJson = new ReadJson(new GetJson() {
            @Override
            public ArrayList<AudioFile> getJson(String result) {

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

                    Realm.init(ctx);
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();

                    if(realm.where(AudioFile.class).findFirst()!=null){
                        maxId =  realm.where(AudioFile.class).max("id");
                    }

                    for(int i = 0; i<musicItemArrayList.size(); i++){

                        if(musicItemArrayList.get(i).getId()<=maxId.intValue()){

                        }else{
                            realm.insert(musicItemArrayList.get(i));
                        }
                    }

                    realm.commitTransaction();

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

    public void refreshListFromJSON(){
        ArrayList<AudioFile> musicItemArrayList = new ArrayList<>();
        ReadJson readJson = new ReadJson(new GetJson() {
            @Override
            public ArrayList<AudioFile> getJson(String result) {
                try {
                    String jsonText = result;
                    JSONObject jsonRoot = new JSONObject(jsonText);
                    JSONArray jsonArray = jsonRoot.getJSONArray("music");

                    //Toast.makeText(context, jsonArray.length()+" ", Toast.LENGTH_SHORT).show();

                    Realm.init(ctx);
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();

                    RealmResults<AudioFile> oldArrey= realm.where(AudioFile.class).findAll();
                    Log.i("COMPARE_AR", oldArrey.size()+" all items in realm");

                    for(int i = 0; jsonArray.length()>i; i++){

                        AudioFile audioFile = new AudioFile();

                        audioFile.setId(jsonArray.getJSONObject(i).getInt("id"));
                        audioFile.setNameSong(jsonArray.getJSONObject(i).getString("name"));
                        audioFile.setFileName(jsonArray.getJSONObject(i).getString("file_name"));
                        audioFile.setAuthorSong(jsonArray.getJSONObject(i).getString("author"));
                        audioFile.setInternetLink(jsonArray.getJSONObject(i).getString("internet_link"));
                        audioFile.setStatus( jsonArray.getJSONObject(i).getBoolean("status"));

                        AudioFile nullAudio =  oldArrey.where().equalTo("fileName", audioFile.getFileName()).findFirst();

                        if(nullAudio==null){
                            musicItemArrayList.add(audioFile);
                        }else{
                            Log.i("COMPARE_AR", audioFile.getFileName()+" find this one");
                        }
                    }

                    realm.copyToRealm(musicItemArrayList);
                    realm.commitTransaction();
                    Log.i("COMPARE_AR", musicItemArrayList.size()+" size");

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


    public void getImgJsonFromURL(){

        ReadJson readJson = new ReadJson(new GetJson() {
            @Override
            public ArrayList<AudioFile> getJson(String result) {

                ArrayList<ImageFile> imageItemArrayList = new ArrayList<>();

                try {

                    String jsonText = result;

                    JSONObject jsonRoot = new JSONObject(jsonText);
                    JSONArray jsonArray = jsonRoot.getJSONArray("music");
                    for(int i = 0; jsonArray.length()>i; i++){

                        ImageFile imageBgFile = new ImageFile();
                      //  imageBgFile.setId(jsonArray.getJSONObject(i).getInt("id"));
                        imageItemArrayList.add(imageBgFile);

                    }


                    Realm.init(ctx);
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();

                    if(realm.where(AudioFile.class).findFirst()!=null){
                        maxId =  realm.where(AudioFile.class).max("id");
                    }

                    for(int i = 0; i<imageItemArrayList.size(); i++){

//                        if(imageItemArrayList.get(i).getImage_internet_link()<=maxId.intValue()){
//
//                        }else{
//                            realm.insert(imageItemArrayList.get(i));
//                        }
                    }

                    realm.commitTransaction();

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


}
