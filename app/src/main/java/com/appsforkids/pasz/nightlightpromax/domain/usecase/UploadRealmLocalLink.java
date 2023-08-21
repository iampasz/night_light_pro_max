package com.appsforkids.pasz.nightlightpromax.domain.usecase;

import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;

import io.realm.Realm;

public class UploadRealmLocalLink {

    public String upload(Realm realm, int position, String lockalLink){
        realm.beginTransaction();
        AudioFile needFile = realm.where(AudioFile.class).equalTo("id", position).findFirst();
        needFile.setLockalLink(lockalLink);
        needFile.setStatus(true);
        realm.commitTransaction();

        return needFile.getInternetLink();

    }
}
