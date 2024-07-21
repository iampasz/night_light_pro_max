package com.appsforkids.pasz.nightlightpromax.domain.usecase;

import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;

import java.util.ArrayList;

public class GetAudioFilesUseCase {
   public ArrayList<AudioFile> getAudio(){

       ArrayList<AudioFile> arrayList = new ArrayList<>();

       AudioFile audioFile = new AudioFile();
       audioFile.setResourseLink(R.raw.detskaya);
       audioFile.setStatus(true);
       audioFile.setFileName("detskaya");
       audioFile.setNameSong("Lullabies");
       audioFile.setAuthorSong("Detskaya");

       AudioFile audioFile1 = new AudioFile();
       audioFile1.setResourseLink(R.raw.sound);
       audioFile1.setStatus(true);
       audioFile1.setFileName("sound");
       audioFile1.setNameSong("Lord");
       audioFile1.setAuthorSong("Lullabies");

       arrayList.add(audioFile);
       arrayList.add(audioFile1);

       return arrayList;
    }
}
