package com.appsforkids.pasz.nightlightpromax.domain.usecase;

import com.appsforkids.pasz.nightlightpromax.domain.models.RealmFields;

public class ReturnNamesUseCase {
    public RealmFields execute(){
        return new RealmFields(4, 55);
    }
}
