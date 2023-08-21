package com.appsforkids.pasz.nightlightpromax.domain.usecase;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class InstanceRealmConfigurationUseCase {
    public Realm connect(){
        RealmConfiguration configuration = new RealmConfiguration
                .Builder()
                .name("MyRealm")
                .allowWritesOnUiThread(true)
                .build();

        return Realm.getInstance(configuration);
    }
}
