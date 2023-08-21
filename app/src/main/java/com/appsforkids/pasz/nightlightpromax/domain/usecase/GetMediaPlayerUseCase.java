package com.appsforkids.pasz.nightlightpromax.domain.usecase;

import android.app.Activity;

import com.appsforkids.pasz.nightlightpromax.Fragments.MainFragment;
import com.appsforkids.pasz.nightlightpromax.MainActivity;
import com.appsforkids.pasz.nightlightpromax.MyMediaPlayer;

public class GetMediaPlayerUseCase {

    public MyMediaPlayer getPlayer(Activity activity){

        return ((MainActivity)activity).getPlayer();
    }
}
