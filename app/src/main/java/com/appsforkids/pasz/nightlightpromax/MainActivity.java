package com.appsforkids.pasz.nightlightpromax;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryPurchasesParams;
import com.appsforkids.pasz.nightlightpromax.Fragments.MainFragment;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.Light;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CopyLightsToRealmUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CreateDefoltLightsUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.SetFullScreanUseCase;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs = null;
    MyMediaPlayer myMediaPlayer;
    boolean subStatus = false;
    SetFullScreanUseCase setFullScreanUseCase = new SetFullScreanUseCase();
    //CopyLightsToRealmUseCase copyLightsToRealmUseCase;
   // CreateDefoltLightsUseCase createDefoltLightsUseCase;
    //InstanceRealmConfigurationUseCase instanceRealmConfigurationUseCase;
    //CopyAudiosToRealmUseCase copyAudioToRealmUseCase;



    public static boolean subscribleStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration
                .Builder()
                .name("MyRealm")
                .allowWritesOnUiThread(true)
                .build();
        Realm realm = Realm.getInstance(configuration);


        prefs = getSharedPreferences("com.appsforkids.pasz.nightlightpromax", MODE_PRIVATE);
        myMediaPlayer = new MyMediaPlayer(this);
        setFullScreanUseCase.fullscrean(this);

        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.my_container, mainFragment, "MAIN_FRAGMENT").commit();



        if (prefs.getBoolean("firstrun", true)) {
            CreateDefoltLightsUseCase createDefoltLightsUseCase = new CreateDefoltLightsUseCase();
            ArrayList<Light> realmArrayList = createDefoltLightsUseCase.getLights();

            CopyLightsToRealmUseCase copyLightsToRealmUseCase = new CopyLightsToRealmUseCase();
            copyLightsToRealmUseCase.copy(realm, realmArrayList);

            prefs.edit().putBoolean("firstrun", false).commit();
        }

        PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
            }
        };
        BillingClient bc = BillingClient.newBuilder(this).enablePendingPurchases().setListener(purchasesUpdatedListener).build();
        bc.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {

                QueryPurchasesParams queryPurchasesParams = QueryPurchasesParams
                        .newBuilder()
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build();

                bc.queryPurchasesAsync(queryPurchasesParams, new PurchasesResponseListener() {
                    @Override
                    public void onQueryPurchasesResponse(@NonNull BillingResult billingResult, @NonNull List<Purchase> list) {

                        if (billingResult.getResponseCode() == 0) {
                            if (list.size() > 0) {
                                subscribleStatus = true;
                            } else {
                                subscribleStatus = false;
                                mainFragment.loaded(true);
                            }
                        }
                    }
                });
            }
        });
    }

    public MyMediaPlayer getPlayer() {
        return myMediaPlayer;
    }
}

