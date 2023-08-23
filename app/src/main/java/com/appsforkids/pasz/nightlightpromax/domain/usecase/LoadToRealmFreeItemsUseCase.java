package com.appsforkids.pasz.nightlightpromax.domain.usecase;

import io.realm.Realm;

public class LoadToRealmFreeItemsUseCase {

    public void load(Realm realm){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

              //  realm.copyToRealm()

            }
        });

    }
}
