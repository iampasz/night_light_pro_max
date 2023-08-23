package com.appsforkids.pasz.nightlightpromax.domain.usecase;

import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;

import io.realm.Realm;
import io.realm.RealmResults;

public class GetAudioFilesFromRealmUseCase {
    public RealmResults<AudioFile> getArray(Realm realm){
        return realm.where(AudioFile.class).equalTo("status", true).findAll();
    }
}
