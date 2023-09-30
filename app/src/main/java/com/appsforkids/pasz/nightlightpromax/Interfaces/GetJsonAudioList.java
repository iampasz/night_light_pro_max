package com.appsforkids.pasz.nightlightpromax.Interfaces;

import com.android.billingclient.api.ProductDetails;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;

import java.util.ArrayList;
import java.util.List;

public interface GetJsonAudioList {
    void getAudioFileArrayList(ArrayList<AudioFile> list);
}
