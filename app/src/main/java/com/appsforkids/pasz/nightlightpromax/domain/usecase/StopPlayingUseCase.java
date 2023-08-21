package com.appsforkids.pasz.nightlightpromax.domain.usecase;

import android.app.Activity;
import android.content.Context;

import com.appsforkids.pasz.nightlightpromax.MyMediaPlayer;

public class StopPlayingUseCase {

    public boolean stop(MyMediaPlayer player){
        player.stopPlaying();
        return player.isPlaying() ? true : false;
    }

}
