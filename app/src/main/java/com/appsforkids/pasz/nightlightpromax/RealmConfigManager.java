package com.appsforkids.pasz.nightlightpromax;

import android.content.Context;

import io.realm.RealmConfiguration;

public class RealmConfigManager {
    private static RealmConfiguration configuration;

    private RealmConfigManager() {
        // Приватный конструктор, чтобы предотвратить создание экземпляров класса
    }

    public static RealmConfiguration getRealmConfiguration(Context context) {
        if (configuration == null) {
            configuration = new RealmConfiguration.Builder()
                    .name("MyRealm")
                    .allowWritesOnUiThread(true)
                    .build();
        }
        return configuration;
    }
}