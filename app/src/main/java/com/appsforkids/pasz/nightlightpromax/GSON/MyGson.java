package com.appsforkids.pasz.nightlightpromax.GSON;


import java.util.List;


public class MyGson {

    List<JsonImage> image;

    public List<JsonImage> getImages() {
        return image;
    }

    public void setImages(List<JsonImage> images) {
        this.image = images;
    }


    public class JsonImage {

        String internet_link;

        public String getInternet_link() {
            return internet_link;
        }

        public void setInternet_link(String internet_link) {
            this.internet_link = internet_link;
        }
    }

}
