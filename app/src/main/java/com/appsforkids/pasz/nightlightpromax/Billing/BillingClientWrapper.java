package com.appsforkids.pasz.nightlightpromax.Billing;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import com.appsforkids.pasz.nightlightpromax.Adapters.SubAdapter;
import com.appsforkids.pasz.nightlightpromax.Interfaces.ChekProductList;
import com.appsforkids.pasz.nightlightpromax.Interfaces.ChoseSub;
import com.appsforkids.pasz.nightlightpromax.Interfaces.GetProductListCallback;
import com.appsforkids.pasz.nightlightpromax.Interfaces.GetPurcheseListCallback;
import com.appsforkids.pasz.nightlightpromax.Interfaces.MyCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BillingClientWrapper implements PurchasesUpdatedListener {
    BillingClient billingClient;
    Activity activity;

    public BillingClientWrapper(Activity activity) {
        this.billingClient = createBillingClient(activity);
        this.activity = activity;
    }

    public BillingClient getBillingClient(){
        return billingClient;
    }

    public void connectToGooglePlayBilling(MyCallback myCallback) {


        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
                //connectToGooglePlayBilling();
               // myCallback.isShown(false);
                Log.i("LEARNBILLING", "onBillingServiceDisconnected");



            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {

                    myCallback.isShown(billingResult);
                    Log.i("LEARNBILLING", "onBillingSetupFinished");
                    //getProductDetails();

                }
            }
        });
    }

    //Отримати деталі щодо покупок
    public QueryProductDetailsParams getQueryProductDetailsParams() {
        
        ArrayList<QueryProductDetailsParams.Product> productList = new ArrayList<>();

        QueryProductDetailsParams.Product product = QueryProductDetailsParams
                .Product
                .newBuilder()
                .setProductId("nlpm_sub")
                .setProductType(BillingClient.ProductType.SUBS)
                .build();

        productList.add(product);

        QueryProductDetailsParams queryProductDetailsParams = QueryProductDetailsParams
                .newBuilder()
                .setProductList(productList)
                .build();

        return queryProductDetailsParams;

    }

    public ProductDetailsResponseListener getProductDetailsResponseListener(){
        return new ProductDetailsResponseListener() {
            @Override
            public void onProductDetailsResponse(@NonNull BillingResult billingResult, @NonNull List<ProductDetails> list) {

                List<ProductDetails.SubscriptionOfferDetails> sub_list = list.get(0).getSubscriptionOfferDetails();
                ProductDetails productDetails = list.get(0);

                subButton(productDetails, list.get(0).getSubscriptionOfferDetails().get(0).getOfferToken(), billingClient);

            }
        };

    }

    public BillingClient createBillingClient(Activity activity) {
        return billingClient = BillingClient.newBuilder(activity).enablePendingPurchases().setListener(this).build();
    }

    //Метод який показує список покупок після оновлення
    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
        //о покупках обновлено

        Log.i("LEARNBILLING", "Якщо купили то треба обробити покупку " + billingResult.getResponseCode());

        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
            for (Purchase purchase : list) {
                if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged()) {
                    handlePurchase(purchase);
                    //bottom_text.setText(list.get(0).getPurchaseState()+"");
                    //Toast.makeText(activity, "onPurchasesUpdated", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    //Обработка покупки
    void handlePurchase(Purchase purchase) {
        //Подтвердите прослушиватель ответа на покупку

        Log.i("LEARNBILLING", "Якщо купили то треба обробити покупкую Підтвердивши що покупка була здійсненна");

        AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build();


        Log.i("LEARNBILLING", "Створюємо параметри на підтвердження покупки додаючи токен товару який купили в нас");


        billingClient.acknowledgePurchase(acknowledgePurchaseParams, new AcknowledgePurchaseResponseListener() {
            @Override
            public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {

                //Ось тут збережемо куплену підписку

                Log.i("LEARNBILLING", "Підтверджуємо куплений товар");
                Log.i("LEARNBILLING", billingResult.getResponseCode() + " Яка тут відповідь? onAcknowledgePurchaseResponse");
                //BillingClient.BillingResponseCode.
                //на ответ о подтверждении покупки
            }
        });

        //признано


    }

    public void subButton(ProductDetails productDetails, String selectedOfferToken, BillingClient billingClient) {

        Log.i("LEARNBILLING", "Натиснувши на кнопку, створюємо необхідний рахунок для клієнта");

        BillingFlowParams.ProductDetailsParams list = BillingFlowParams.ProductDetailsParams.newBuilder()
                // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                .setProductDetails(productDetails)
                // to get an offer token, call ProductDetails.getSubscriptionOfferDetails()
                // for a list of offers that are available to the user
                .setOfferToken(selectedOfferToken).build();

        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder().setProductDetailsParamsList(Collections.singletonList(list)).build();
        // Launch the billing flow
        Log.i("LEARNBILLING", "Виставляємо рахунок");
        BillingResult billingResult = billingClient.launchBillingFlow(activity, billingFlowParams);
    }

    public void getPurchaseList(ChekProductList productList) {



        QueryPurchasesParams queryPurchasesParams = QueryPurchasesParams
                .newBuilder()
                .setProductType(BillingClient.ProductType.SUBS)
                .build();


        PurchasesResponseListener purchasesResponseListener = new PurchasesResponseListener() {
            @Override
            public void onQueryPurchasesResponse(@NonNull BillingResult billingResult, @NonNull List<Purchase> list) {

                Log.i("LEARNBILLING", list.size() + " purches list.size()");
                Log.i("LEARNBILLING", list.get(0) + " list.get(0).getPackageName()");

                productList.setList(list);


            }
        };


        billingClient.queryPurchasesAsync(queryPurchasesParams, purchasesResponseListener);

        //billingClient.queryProductDetailsAsync(queryProductDetailsParams, productDetailsResponseListener);


    }

    public List<ProductDetails.SubscriptionOfferDetails> getProductr(List<ProductDetails> list){

        List<ProductDetails.SubscriptionOfferDetails> sub_list = list.get(0).getSubscriptionOfferDetails();

        return sub_list;
    }


    public void getProductDetailsList(GetProductListCallback getProductListCallback){

        ArrayList<QueryProductDetailsParams.Product> productList = new ArrayList<>();

        QueryProductDetailsParams.Product qpdp = QueryProductDetailsParams
                .Product
                .newBuilder()
                .setProductId("nlpm_sub")
                .setProductType(BillingClient.ProductType.SUBS)
                .build();
        productList.add(qpdp);

        QueryProductDetailsParams queryProductDetailsParams = QueryProductDetailsParams
                .newBuilder()
                .setProductList(productList)
                .build();

        Log.i("LEARNBILLING", "Створюємо");

        ProductDetailsResponseListener productDetailsResponseListener = new ProductDetailsResponseListener() {
            @Override
            public void onProductDetailsResponse(@NonNull BillingResult billingResult, @NonNull List<ProductDetails> list) {
                getProductListCallback.get(list);
            }
        };

        billingClient.queryProductDetailsAsync(queryProductDetailsParams, productDetailsResponseListener);
        Log.i("LEARNBILLING", " What next?");
    }

    public void chekSubsccriptions(GetPurcheseListCallback purcheseListCallback){



        connectToGooglePlayBilling(new MyCallback() {
            @Override
            public void isShown(BillingResult billingResult) {
                switch (billingResult.getResponseCode()){
                    case 0:




                        QueryPurchasesParams queryPurchasesParams = QueryPurchasesParams
                                .newBuilder()
                                .setProductType(BillingClient.ProductType.SUBS)
                                .build();
                        PurchasesResponseListener purchasesResponseListener = new PurchasesResponseListener() {
                            @Override
                            public void onQueryPurchasesResponse(@NonNull BillingResult billingResult, @NonNull List<Purchase> list) {

                                if(billingResult.getResponseCode() == 0){

                                   // billingClient.endConnection();

                                    purcheseListCallback.get(list);
                                }
                            }
                        };
                        billingClient.queryPurchasesAsync(queryPurchasesParams, purchasesResponseListener);

                        break;

                    case 1:
                        break;

                    case 2:
                        break;

                }
            }
        });



    }

    public void subscribe(String token){

    }
}
