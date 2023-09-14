package com.appsforkids.pasz.nightlightpromax.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.appsforkids.pasz.nightlightpromax.Adapters.SubAdapter;
import com.appsforkids.pasz.nightlightpromax.Billing.BillingClientWrapper;
import com.appsforkids.pasz.nightlightpromax.Interfaces.ChoseSub;
import com.appsforkids.pasz.nightlightpromax.Interfaces.GetProductDetailsListCallBack;
import com.appsforkids.pasz.nightlightpromax.Interfaces.GetProductListCallback;
import com.appsforkids.pasz.nightlightpromax.Interfaces.IsLoadDataCallback;
import com.appsforkids.pasz.nightlightpromax.Interfaces.MyCallback;
import com.appsforkids.pasz.nightlightpromax.MainActivity;
import com.appsforkids.pasz.nightlightpromax.MyViewModel;
import com.appsforkids.pasz.nightlightpromax.Objects.Test;
import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.TestLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class Subscription extends Fragment {

    BillingClient bc;
    RecyclerView rv;
    String token;
    LinearLayout subscribeButton;
    TextView bottom_text;
    Button close;
    Activity activity;
    ProductDetails productDetails;

    ArrayList<Test> testArrayList;

    SubAdapter subAdapter;

    MotionLayout motionLayout;

    BillingResult myBillingResult;


    public Subscription() {
        super(R.layout.subscription);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        motionLayout = view.findViewById(R.id.motionLayout);
        MyViewModel model = new ViewModelProvider(this).get(MyViewModel.class);
        model.getValue().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
            }
        });
        model.execute();


        rv = view.findViewById(R.id.rv);


        bottom_text = view.findViewById(R.id.bottom_text);
        close = view.findViewById(R.id.close);
        subscribeButton = view.findViewById(R.id.subscribeButton);
        Date currentTime = Calendar.getInstance().getTime();

        activity = getActivity();
        // String dt = "2012-01-04";  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(currentTime);
        c.add(Calendar.DATE, 7);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMMM yyyy");
        String output = sdf1.format(c.getTime());

        bottom_text.setText(getResources().getString(R.string.subscribe_details) +" "+ output);

        setAdapterList();

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myBillingResult.getResponseCode()==0){
                    someMethod();
                }
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fadeout, R.anim.fadein)
                        .remove(Subscription.this).commit();
            }
        });
    }


    public void someMethod() {

        BillingFlowParams.ProductDetailsParams params = BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(productDetails)
                .setOfferToken(token).build();

        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder().setProductDetailsParamsList(Collections.singletonList(params)).build();
        // Launch the billing flow
        Log.i("LEARNBILLING", "Виставляємо рахунок");
        bc.launchBillingFlow(activity, billingFlowParams);

    }

    public void getList(GetProductDetailsListCallBack getProductDetailsListCallBack) {

        ArrayList<QueryProductDetailsParams.Product> productList = new ArrayList<>();

        QueryProductDetailsParams.Product product = QueryProductDetailsParams
                .Product
                .newBuilder()
                .setProductId("nlpm_sub")
                .setProductType(BillingClient.ProductType.SUBS)
                .build();

        productList.add(product);

        QueryProductDetailsParams queryProductDetailsParams =
                QueryProductDetailsParams.newBuilder()
                        .setProductList(productList)
                        .build();

        bc = BillingClient.newBuilder(getContext()).enablePendingPurchases().setListener(new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {

                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
                    for (Purchase purchase : list) {
                        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged()) {
                            handlePurchase(purchase);

                        }
                    }
                }
            }
        }).build();

        bc.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
                Log.i("BILLINGRESULT", " onBillingServiceDisconnected 01");
            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {

                myBillingResult = billingResult;

                if (billingResult.getResponseCode()==0) {
                    bc.queryProductDetailsAsync(queryProductDetailsParams, new ProductDetailsResponseListener() {
                        @Override
                        public void onProductDetailsResponse(@NonNull BillingResult billingResult, @NonNull List<ProductDetails> list) {
                            if (list.size() > 0) {
                                productDetails = list.get(0);
                                settMyadapter(list.get(0).getSubscriptionOfferDetails());
                            }

                        }
                    });
                }
            }
        });
    }

    void handlePurchase(Purchase purchase) {
        //Подтвердите прослушиватель ответа на покупку
        Log.i("LEARNBILLING", "Якщо купили то треба обробити покупкую Підтвердивши що покупка була здійсненна");
        AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build();
        Log.i("LEARNBILLING", "Створюємо параметри на підтвердження покупки додаючи токен товару який купили в нас");

        bc.acknowledgePurchase(acknowledgePurchaseParams, new AcknowledgePurchaseResponseListener() {
            @Override
            public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {

                getParentFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fadeout, R.anim.fadein)
                        .remove(Subscription.this).commit();

                if( MainFragment.mAdView !=null){
                    MainFragment.mAdView.setVisibility(View.GONE);
                }


                MainActivity.subscribleStatus = true;

            }
        });
        //признано
    }

    void setAdapterList() {

        getList(new GetProductDetailsListCallBack() {
            @Override
            public void getProductLit(List<ProductDetails> list) {
            }
        });
    }


    public void settMyadapter(List<ProductDetails.SubscriptionOfferDetails> list) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                subAdapter = new SubAdapter(list, new ChoseSub() {
                    @Override
                    public void setToken(String offerToken) {
                        token = offerToken;
                    }
                });
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                rv.setAdapter(subAdapter);
                //subAdapter.notifyDataSetChanged();
                motionLayout.transitionToState(R.id.end);
            }
        });
    }
}
