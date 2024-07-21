package com.appsforkids.pasz.nightlightpromax;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RawRes;
import androidx.annotation.StringRes;

import java.io.IOException;

public class MyMediaPlayer extends MediaPlayer {

    Context ctx;
    MediaPlayer mediaPlayer;
    String activeAudio = "";
    public MyMediaPlayer(Context ctx){
        this.ctx = ctx;
    }

   public void  playAudioFile(String link){
       stopPlaying();
        if(link.equals("")){

           // activeAudio = "";
        }else{
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

           // activeAudio = link;
        }

    }

    public void playAudioFile(Integer resurseId){
        stopPlaying();
        if(resurseId==0){

            //activeAudio = "";
        }else {
            mediaPlayer = create(ctx, resurseId);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
           // activeAudio = resurseId.toString();
        }
    }

    public void stopPlaying(){
        if (mediaPlayer != null) mediaPlayer.release();
    }
}
