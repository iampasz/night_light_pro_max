package com.appsforkids.pasz.nightlightpromax.RealmObjects;

import java.io.Serializable;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by pasz on 27.10.2016.
 */

//1. создай класс Light, с констуктором (инт, инт), туда будешь передавать ид картинки и ид строки
//2. создай аррейЛист и добавь в него новые экземпляры Light и нужными тебе картинками и именами
//3. напиши метод который будет создавать новый фрагмент, а в аргументы ему передай класс Light
//4. в твой адаптер должен принимать список фрагментов
//5. в фрагментн доставай из аргументов Light из него картинку и текст и добавляй в поля

public class Light extends RealmObject {
    private int id;
    private int mypic;
    private int mytext;
    private boolean status;
    private boolean online;
    private String internetLink;

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setInternetLink(String internetLink) {
        this.internetLink = internetLink;
    }

    public String getInternetLink() {
        return internetLink;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMypic(int mypic) {
        this.mypic = mypic;
    }

    public void setMytext(int mytext) {
        this.mytext = mytext;
    }

    public int getMypic() {
        return mypic;
    }

    public int getMytext() {
        return mytext;
    }

    public boolean getStatus() {
        return status;
    }

    public boolean getOnline() {
        return online;
    }
}






