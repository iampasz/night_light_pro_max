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
import com.appsforkids.pasz.nightlightpromax.AudioListManager;
import com.appsforkids.pasz.nightlightpromax.Interfaces.ActionCalback;
import com.appsforkids.pasz.nightlightpromax.MyMediaPlayer;
import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;

import java.util.ArrayList;

public class ComonMelody extends Fragment implements ActionCalback {

    MyMediaPlayer player;
    AudioListManager audioListManager;

    public ComonMelody() {
        super(R.layout.list_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        player = new MyMediaPlayer(getContext());
        audioListManager = new AudioListManager(getActivity());

        ArrayList<AudioFile> list = audioListManager.getList();
        JsonMusicAdapter jsonMusicAdapter = new JsonMusicAdapter(this, list);

        RecyclerView rv = view.findViewById(R.id.rv_cards);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(jsonMusicAdapter);

        ImageView close_button = (ImageView) view.findViewById(R.id.close_button);
        close_button.setOnClickListener(v -> closeFragment());
    }

    @Override
    public void play(String fileName) {

        Log.i("MYTAG", fileName+" fileName");

        for (AudioFile obj : audioListManager.getList()) {
            if (obj.getFileName().equals(fileName)) {

                Log.i("MYTAG", obj.getFileName()+" нужный файл");

                if (obj.getLockalLink() != null) {
                    player.playAudioFile(obj.getLockalLink());
                    Log.i("MYTAG", obj.getInternetLink()+" obj.getInternetLink()");
                } else {
                    Log.i("MYTAG", " пусто");
                    if (obj.getResourceLink() != 0) {
                        player.playAudioFile(obj.getResourceLink());
                        Log.i("MYTAG", obj.getLockalLink()+" obj.getLockalLink()");
                    }else {
                        Log.i("MYTAG", " пусто2");
                    }
                }
                break;
            }else{
                player.stopPlaying();
            }
        }
    }

    @Override
    public void download(int id) {
        audioListManager.downloadAudioFile("id", "");
    }

    @Override
    public void delete(String fileName,String internetLink) {
        audioListManager.deleteAudioFile("id", "");
    }

    private void closeFragment() {
        getParentFragmentManager()
                .beginTransaction()
                .remove(ComonMelody.this)
                .commit();
    }
}