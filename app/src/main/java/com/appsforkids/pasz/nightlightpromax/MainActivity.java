package com.appsforkids.pasz.nightlightpromax;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import com.appsforkids.pasz.nightlightpromax.Billing.BillingClientWrapper;
import com.appsforkids.pasz.nightlightpromax.Fragments.MainFragment;
import com.appsforkids.pasz.nightlightpromax.Fragments.Subscription;
import com.appsforkids.pasz.nightlightpromax.Interfaces.GetPurcheseListCallback;
import com.appsforkids.pasz.nightlightpromax.Interfaces.MyCallback;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.ChekInternetConnection;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CopyAudiosToRealmUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CopyLightsToRealmUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CreateDefoltAudioUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CreateDefoltLightsUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.InstanceRealmConfigurationUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.LoadToRealmFreeItemsUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.SetFullScreanUseCase;

import java.util.ArrayList;
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

    ChekInternetConnection chekInternetConnection = new ChekInternetConnection();
    Realm realm;

   public static int internetStatus;
   public static boolean subscribleStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        Realm.init(this);
        internetStatus = chekInternetConnection.execute(this);

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

        PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {

               // Log.i("BillingClientWrapper", list.size() + " hghg");

            }
        };


        BillingClient bc = BillingClient.newBuilder(this).enablePendingPurchases().setListener(purchasesUpdatedListener).build();
//
//        bc.startConnection(new BillingClientStateListener() {
//            @Override
//            public void onBillingServiceDisconnected() {
//
//            }
//
//            @Override
//            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
//
//                ArrayList<QueryProductDetailsParams.Product> productList = new ArrayList<>();
//
//                QueryProductDetailsParams.Product product = QueryProductDetailsParams
//                        .Product
//                        .newBuilder()
//                        .setProductId("nlpm_sub")
//                        .setProductType(BillingClient.ProductType.SUBS)
//                        .build();
//
//                productList.add(product);
//
//                Log.i("BillingClientWrapper", productList.size() + " productList.size()");
//
//
//                QueryProductDetailsParams queryProductDetailsParams =
//                        QueryProductDetailsParams.newBuilder()
//                                .setProductList(productList)
//                                .build();
//
//
//                bc.queryProductDetailsAsync(queryProductDetailsParams, new ProductDetailsResponseListener() {
//                    @Override
//                    public void onProductDetailsResponse(@NonNull BillingResult billingResult, @NonNull List<ProductDetails> list) {
//
//                        Log.i("BillingClientWrapper", billingResult.getResponseCode() + " billingResult.getResponseCode()");
//                        Log.i("BillingClientWrapper", list.size() + " list.size()");
//
//                    }
//                });
//
//
//            }
//        });
//
//        bc.endConnection();

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


                        if (list.size() > 0) {

                            subscribleStatus = true;
                           // bc.endConnection();
                           // getSupportFragmentManager().beginTransaction().add(R.id.my_container, new MainFragment()).commit();
                        } else {

                            subscribleStatus = false;
//                            if(internetStatus==0){
//                                getSupportFragmentManager().beginTransaction().add(R.id.my_container, new MainFragment()).commit();
//                            }else{
//                                getSupportFragmentManager().beginTransaction().add(R.id.my_container, new Subscription()).commit();
//                            }


                        }

                        getSupportFragmentManager().beginTransaction().add(R.id.my_container, new MainFragment(), "MAIN_FRAGMENT").commit();

                    }
                });

            }
        });
    }

    public MyMediaPlayer getPlayer() {
        return myMediaPlayer;
    }


}

