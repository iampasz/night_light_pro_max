package com.appsforkids.pasz.nightlightpromax;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    private MutableLiveData<Boolean> isStarted = new MutableLiveData<Boolean>(false);
    private MutableLiveData<Integer> value;
    public LiveData<Integer> getValue() {
        if (value == null) {
            value = new MutableLiveData<Integer>(0);
        }
        return value;
    }
    public void execute(){

        if(!isStarted.getValue()){
            isStarted.postValue(true);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {

                    for(int i = value.getValue();  i <= 100; i++){
                        try {
                            value.postValue(i);
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }
}