package com.appsforkids.pasz.nightlightpromax;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class TestLoader extends AsyncTaskLoader {
    public TestLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    @Nullable
    @Override
    public Object loadInBackground() {

        String ala = "hello world";

        return ala;
    }
}
