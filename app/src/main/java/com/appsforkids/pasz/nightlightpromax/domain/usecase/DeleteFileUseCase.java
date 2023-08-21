package com.appsforkids.pasz.nightlightpromax.domain.usecase;

import android.app.Activity;

import java.io.File;

public class DeleteFileUseCase {
   public boolean  delete(Activity activity, String file_name){
        File dir = activity.getFilesDir();
        File file = new File(dir, file_name);
        return file.delete();
    }
}
