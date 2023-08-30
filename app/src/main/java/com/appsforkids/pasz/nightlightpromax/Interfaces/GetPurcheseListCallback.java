package com.appsforkids.pasz.nightlightpromax.Interfaces;

import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;

import java.util.List;

public interface GetPurcheseListCallback {

    void  get(List<Purchase> list);
}
