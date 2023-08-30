package com.appsforkids.pasz.nightlightpromax.Interfaces;

import com.android.billingclient.api.ProductDetails;

import java.util.List;

public interface GetProductListCallback {

    void  get(List<ProductDetails> list);
}
