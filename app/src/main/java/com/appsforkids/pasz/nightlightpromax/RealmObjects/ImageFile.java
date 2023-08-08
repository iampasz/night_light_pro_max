package com.appsforkids.pasz.nightlightpromax.RealmObjects;

import java.io.Serializable;

import io.realm.RealmObject;

public class ImageFile  extends RealmObject{


    int imageInternetLink;
    boolean status;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus(){
        return status;
    }

    public int  getImage_internet_link() {
        return imageInternetLink;
    }

    public void setImage(int imageInternetLink) {
        this.imageInternetLink = imageInternetLink;
    }
}
