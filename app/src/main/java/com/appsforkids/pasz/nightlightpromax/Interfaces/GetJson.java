package com.appsforkids.pasz.nightlightpromax.Interfaces;

import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;

import java.util.ArrayList;

public interface GetJson {
    ArrayList<AudioFile> getJson(String result);
    void noAnswer(Boolean answer);
}
