package com.appsforkids.pasz.nightlightpromax;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.appsforkids.pasz.nightlightpromax.Fragments.MainFragment;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CreateMyMediaPlayerUseCase;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    MyMediaPlayer myMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        myMediaPlayer = new MyMediaPlayer(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Realm.init(this);
        getSupportFragmentManager().beginTransaction().add(R.id.my_container, new MainFragment(), "main_fragment").commit();
    }

    public MyMediaPlayer getPlayer() {
        return myMediaPlayer;
    }
}

