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


       arrayList.add(light);
       arrayList.add(light1);

       return arrayList;

    }
}
