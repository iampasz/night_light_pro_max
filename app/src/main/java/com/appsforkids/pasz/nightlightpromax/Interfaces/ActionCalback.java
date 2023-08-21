package com.appsforkids.pasz.nightlightpromax.Interfaces;

import java.io.IOException;

public interface ActionCalback {

    void play(int position);
    void download(int position);
    void delete(int position) throws IOException;
}
