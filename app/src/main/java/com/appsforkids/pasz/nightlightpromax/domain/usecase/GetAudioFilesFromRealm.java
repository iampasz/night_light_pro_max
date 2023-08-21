package com.appsforkids.pasz.nightlightpromax.domain.usecase;

import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;

import io.realm.Realm;
import io.realm.RealmResults;

public class GetAudioFilesFromRealm {
    public RealmResults<AudioFile> getArray(Realm realm, String field){
        return realm.where(AudioFile.class).isNotNull(field).findAll();
    }
}
