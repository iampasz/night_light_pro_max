package com.appsforkids.pasz.nightlightpromax;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;

import java.io.IOException;

public class MyMediaPlayer extends MediaPlayer {

    Context ctx;
    MediaPlayer mediaPlayer;
    public MyMediaPlayer(Context ctx){
        this.ctx = ctx;
    }

   public void  playAudio(String link){

       Uri myUri = Uri.parse(link);
        mediaPlayer = new MediaPlayer();
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
           mediaPlayer.setAudioAttributes(
                   new AudioAttributes.Builder()
                           .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                           .setUsage(AudioAttributes.USAGE_MEDIA)
                           .build()
           );
       }
       try {
           mediaPlayer.setDataSource(ctx, myUri);
           mediaPlayer.setLooping(true);
           mediaPlayer.prepare();
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       mediaPlayer.start();

    }

    public void playAudio(Integer resurseId){
         mediaPlayer = create(ctx, resurseId);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

    }

    public void stopPlaying(){
        if (mediaPlayer != null) mediaPlayer.release();
    }

}
