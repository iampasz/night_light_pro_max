package com.appsforkids.pasz.nightlightpromax.domain.usecase;

import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;

import io.realm.Realm;

public class RemoveAudioFromRealmUseCase {
    public void remove(Realm realm, String link) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(AudioFile.class).equalTo("internetLink", link).findFirst().deleteFromRealm();
            }
        });

    }
}
