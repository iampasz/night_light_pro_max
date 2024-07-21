package com.appsforkids.pasz.nightlightpromax.domain.usecase;

import android.content.Context;

import com.appsforkids.pasz.nightlightpromax.MainActivity;
import com.appsforkids.pasz.nightlightpromax.MyMediaPlayer;

public class PlayMelodyUseCase {

    public void play(MyMediaPlayer player,  String link){
        player.playAudioFile(link);
    }
}
