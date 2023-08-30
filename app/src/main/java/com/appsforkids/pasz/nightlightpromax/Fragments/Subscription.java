package com.appsforkids.pasz.nightlightpromax.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.appsforkids.pasz.nightlightpromax.Interfaces.GetProductListCallback;
import com.appsforkids.pasz.nightlightpromax.Interfaces.MyCallback;
import com.appsforkids.pasz.nightlightpromax.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class Subscription extends Fragment {

    BillingClient billingClient;
    RecyclerView rv;
    String token;
    LinearLayout subscribeButton;
    TextView bottom_text;
    Button close;

    public Subscription() {
        super(R.layout.subscription);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        bottom_text = view.findViewById(R.id.bottom_text);
        close = view.findViewById(R.id.close);
        subscribeButton = view.findViewById(R.id.subscribeButton);
        Date currentTime = Calendar.getInstance().getTime();

       // String dt = "2012-01-04";  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(currentTime);
        c.add(Calendar.DATE, 7);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMMM yyyy");
        String output = sdf1.format(c.getTime());

        bottom_text.setText("*Пробный период 7 дней. Подписку можно отменить в любое время. Первое списание после оформления подписки " + output);




        BillingClientWrapper bc = new BillingClientWrapper(getActivity());
        bc.connectToGooglePlayBilling(new MyCallback() {
            @Override
            public void isShown(BillingResult billingResult) {
                //Log.i("LEARNBILLING", shown+" Billing answer");


                switch (billingResult.getResponseCode()) {
                    case 0:

                        bc.getProductDetailsList(new GetProductListCallback() {
                            @Override
                            public void get(List<ProductDetails> list) {

                                Log.i("LEARNBILLING", list.size()+" list.size");


                                List<ProductDetails.SubscriptionOfferDetails> listSub = bc.getProductr(list);

                                ChoseSub choseSub = new ChoseSub() {
                                    @Override
                                    public void setToken(String offerToken) {

                                        Log.i("LEARNBILLING", offerToken+" offerToken");

                                        token = offerToken;

                                    }
                                };
                                SubAdapter subAdapter = new SubAdapter(listSub, choseSub);

                                rv.setAdapter(subAdapter);





                            }
                        });

                        break;
                    case 8:

                        break;
                }


            }
        });

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LEARNBILLING", token+" it is my token");



//
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.my_container, new MainFragment(), "MAIN_FRAGMENT").commit();

                Log.i("LEARNBILLING", " it is my token");
            }
        });


    }
}
