package com.appsforkids.pasz.nightlightpromax.domain.usecase;

import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.Light;

import java.util.ArrayList;

public class CreateDefoltLightsUseCase {
   public ArrayList<Light> getLights(){

       ArrayList<Light> arrayList = new ArrayList<>();

       Light light0 = new Light();
       light0.setMypic(R.drawable.an_bear);
       light0.setStatus(true);
       light0.setId("bear");
       light0.setMytext(R.string.bear);


       Light light1 = new Light();
       light1.setMypic(R.drawable.an_chiken);
       light1.setStatus(false);
       light1.setId("chiken");
       light1.setMytext(R.string.chiken);

       Light light2 = new Light();
       light2.setMypic(R.drawable.an_banny);
       light2.setStatus(false);
       light2.setId("banny");
       light2.setMytext(R.string.banny);

       Light light3 = new Light();
       light3.setMypic(R.drawable.an_elephant);
       light3.setStatus(true);
       light3.setId("elephant");
       light3.setMytext(R.string.elephant);

       Light light4 = new Light();
       light4.setMypic(R.drawable.an_mouse);
       light4.setStatus(false);
       light4.setId("mouse");
       light4.setMytext(R.string.mouse);

       Light light5 = new Light();
       light5.setMypic(R.drawable.an_tortila);
       light5.setStatus(true);
       light5.setId("tortila");
       light5.setMytext(R.string.tortila);

       Light light6 = new Light();
       light6.setMypic(R.drawable.an_penguin);
       light6.setStatus(false);
       light6.setId("penguin");
       light6.setMytext(R.string.penguin);

       Light light7 = new Light();
       light7.setMypic(R.drawable.an_ant);
       light7.setStatus(false);
       light7.setId("ant");
       light7.setMytext(R.string.ant);

       Light light8 = new Light();
       light8.setMypic(R.drawable.an_cat);
       light8.setStatus(false);
       light8.setId("cat");
       light8.setMytext(R.string.cat);

       arrayList.add(light0);
       arrayList.add(light1);
       arrayList.add(light3);
       arrayList.add(light4);
       arrayList.add(light5);
       arrayList.add(light6);
       arrayList.add(light7);
       arrayList.add(light8);

       return arrayList;
    }
}
