package com.appsforkids.pasz.nightlightpromax.domain.usecase;

import com.appsforkids.pasz.nightlightpromax.RealmObjects.Light;

import java.util.ArrayList;

import io.realm.Realm;

public class CopyLightsToRealmUseCase {

    public void copy(Realm realm, ArrayList<Light> arrayList) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(int i=0; i<arrayList.size(); i++ ){
                    realm.copyToRealm(arrayList.get(i));
                }
            }
        });
    }
}
