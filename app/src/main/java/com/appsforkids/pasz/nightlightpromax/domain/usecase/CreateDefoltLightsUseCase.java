package com.appsforkids.pasz.nightlightpromax.domain.usecase;

import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.Light;

import java.util.ArrayList;

public class CreateDefoltLightsUseCase {
   public ArrayList<Light> getLights(){

       ArrayList<Light> arrayList = new ArrayList<>();
       Light light = new Light();
       light.setMypic(R.drawable.an_ant);
       light.setStatus(true);
       light.setId("ant");

       Light light1 = new Light();
       light1.setMypic(R.drawable.an_cat);
       light1.setStatus(true);
       light.setId("cat");

       Light light2 = new Light();
       light2.setMypic(R.drawable.an_cow);
       light2.setStatus(true);
       light2.setId("cow");

       arrayList.add(light);
       arrayList.add(light1);
       arrayList.add(light2);

       return arrayList;

    }
}
