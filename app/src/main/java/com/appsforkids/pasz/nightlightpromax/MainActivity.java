package com.appsforkids.pasz.nightlightpromax;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.appsforkids.pasz.nightlightpromax.Fragments.MainFragment;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CopyAudiosToRealmUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CopyLightsToRealmUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CreateDefoltAudioUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CreateDefoltLightsUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.InstanceRealmConfigurationUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.LoadToRealmFreeItemsUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.SetFullScreanUseCase;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs = null;

    MyMediaPlayer myMediaPlayer;

    SetFullScreanUseCase setFullScreanUseCase = new SetFullScreanUseCase();
    CopyLightsToRealmUseCase copyLightsToRealmUseCase = new CopyLightsToRealmUseCase();

    CreateDefoltLightsUseCase createDefoltLightsUseCase = new CreateDefoltLightsUseCase();
    CreateDefoltAudioUseCase createDefoltAudioUseCase = new CreateDefoltAudioUseCase();

    InstanceRealmConfigurationUseCase instanceRealmConfigurationUseCase = new InstanceRealmConfigurationUseCase();

    CopyAudiosToRealmUseCase copyAudioToRealmUseCase = new CopyAudiosToRealmUseCase();
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Realm.init(this);

        prefs = getSharedPreferences("com.appsforkids.pasz.nightlightpromax", MODE_PRIVATE);
        myMediaPlayer = new MyMediaPlayer(this);
        setFullScreanUseCase.fullscrean(this);


        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false

            realm = instanceRealmConfigurationUseCase.connect();
            copyLightsToRealmUseCase.copy(realm, createDefoltLightsUseCase.getLights());
            copyAudioToRealmUseCase.copy(realm, createDefoltAudioUseCase.getAudio());
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).commit();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.my_container, new MainFragment(), "MAIN_FRAGMENT").commit();


  }

    public MyMediaPlayer getPlayer() {
        return myMediaPlayer;
    }


}

