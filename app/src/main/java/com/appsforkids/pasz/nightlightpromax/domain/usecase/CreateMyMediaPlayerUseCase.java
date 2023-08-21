package com.appsforkids.pasz.nightlightpromax.domain.usecase;
import android.content.Context;
import com.appsforkids.pasz.nightlightpromax.MyMediaPlayer;

public class CreateMyMediaPlayerUseCase {
    public MyMediaPlayer create(Context ctx){
        return new MyMediaPlayer(ctx);
    }
}
