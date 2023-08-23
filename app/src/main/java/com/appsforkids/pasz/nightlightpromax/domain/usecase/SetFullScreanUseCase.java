package com.appsforkids.pasz.nightlightpromax.domain.usecase;

import android.app.Activity;
import android.view.WindowManager;

public class SetFullScreanUseCase {
    public void fullscrean(Activity activity){
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
