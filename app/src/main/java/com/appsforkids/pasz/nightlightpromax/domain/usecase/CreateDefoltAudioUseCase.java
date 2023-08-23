package com.appsforkids.pasz.nightlightpromax.domain.usecase;

import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.Light;

import java.util.ArrayList;

public class CreateDefoltAudioUseCase {
   public ArrayList<AudioFile> getAudio(){

       ArrayList<AudioFile> arrayList = new ArrayList<>();
       AudioFile audioFile = new AudioFile();
       audioFile.setResourseLink(R.raw.detskaya);
       audioFile.setStatus(true);
       audioFile.setFileName("Detskaya");
       audioFile.setAuthorSong("Detskaya");
       audioFile.setFileName("detskaya");

       arrayList.add(audioFile);

       return arrayList;
    }
}
