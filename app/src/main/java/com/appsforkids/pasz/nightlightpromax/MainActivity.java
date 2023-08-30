package com.appsforkids.pasz.nightlightpromax;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.appsforkids.pasz.nightlightpromax.Billing.BillingClientWrapper;
import com.appsforkids.pasz.nightlightpromax.Fragments.MainFragment;
import com.appsforkids.pasz.nightlightpromax.Fragments.Subscription;
import com.appsforkids.pasz.nightlightpromax.Interfaces.GetPurcheseListCallback;
import com.appsforkids.pasz.nightlightpromax.Interfaces.MyCallback;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CopyAudiosToRealmUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CopyLightsToRealmUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CreateDefoltAudioUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CreateDefoltLightsUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.InstanceRealmConfigurationUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.LoadToRealmFreeItemsUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.SetFullScreanUseCase;

import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs = null;
    MyMediaPlayer myMediaPlayer;

    boolean subStatus = false;

    SetFullScreanUseCase setFullScreanUseCase = new SetFullScreanUseCase();
    CopyLightsToRealmUseCase copyLightsToRealmUseCase = new CopyLightsToRealmUseCase();
    CreateDefoltLightsUseCase createDefoltLightsUseCase = new CreateDefoltLightsUseCase();
    CreateDefoltAudioUseCase createDefoltAudioUseCase = new CreateDefoltAudioUseCase();
    InstanceRealmConfigurationUseCase instanceRealmConfigurationUseCase = new InstanceRealmConfigurationUseCase();
    CopyAudiosToRealmUseCase copyAudioToRealmUseCase = new CopyAudiosToRealmUseCase();
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Realm.init(this);

        prefs = getSharedPreferences("com.appsforkids.pasz.nightlightpromax", MODE_PRIVATE);
        myMediaPlayer = new MyMediaPlayer(this);
        setFullScreanUseCase.fullscrean(this);


        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false

            realm = instanceRealmConfigurationUseCase.connect();
            copyLightsToRealmUseCase.copy(realm, createDefoltLightsUseCase.getLights());
            copyAudioToRealmUseCase.copy(realm, createDefoltAudioUseCase.getAudio());
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).commit();
        }

        // getSupportFragmentManager().beginTransaction().add(R.id.my_container, new Subscription(), "SUB_FRAGMENT").commit();


        BillingClientWrapper billingClientWrapper = new BillingClientWrapper(this);




        billingClientWrapper.chekSubsccriptions(new GetPurcheseListCallback() {
            @Override
            public void get(List<Purchase> list) {

                if(list.size()>0){
                    subStatus = true;
                }
            }
        });


        if(subStatus){
            getSupportFragmentManager().beginTransaction().add(R.id.my_container, new MainFragment(), "SUB_FRAGMENT").commit();
        }else{
            getSupportFragmentManager().beginTransaction().add(R.id.my_container, new Subscription(), "SUB_FRAGMENT").commit();
        }



//        billingClientWrapper.connectToGooglePlayBilling(new MyCallback() {
//            @Override
//            public void isShown(BillingResult billingResult) {
//
//                Log.i("LEARNBILLING", billingResult.getResponseCode() + " billingResult.getResponseCode() 00");
//
//                switch (billingResult.getResponseCode()) {
//                    case 0:
//                        billingClientWrapper.chekSubsccriptions(new GetPurcheseListCallback() {
//                            @Override
//                            public void get(List<Purchase> list) {
//                                if (list.size() > 0) {
//                                    getSupportFragmentManager().beginTransaction().add(R.id.my_container, new MainFragment(), "MAIN_FRAGMENT").commit();
//                                } else {
//                                    getSupportFragmentManager().beginTransaction().add(R.id.my_container, new Subscription(), "SUB_FRAGMENT").commit();
//                                }
//                            }
//                        });
//
//                        break;
//                    case 8:
//                        break;
//                }
//
////                int SERVICE_TIMEOUT = -3;
////                int FEATURE_NOT_SUPPORTED = -2;
////                int SERVICE_DISCONNECTED = -1;
////                int OK = 0;
////                int USER_CANCELED = 1;
////                int SERVICE_UNAVAILABLE = 2;
////                int BILLING_UNAVAILABLE = 3;
////                int ITEM_UNAVAILABLE = 4;
////                int DEVELOPER_ERROR = 5;
////                int ERROR = 6;
////                int ITEM_ALREADY_OWNED = 7;
////                int ITEM_NOT_OWNED = 8;
//
//
//                Log.i("LEARNBILLING", billingResult + "subResult");
//
//
//            }
//        });


    }

    public MyMediaPlayer getPlayer() {
        return myMediaPlayer;
    }


}

