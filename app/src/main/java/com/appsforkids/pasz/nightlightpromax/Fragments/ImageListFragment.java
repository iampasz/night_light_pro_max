package com.appsforkids.pasz.nightlightpromax.Fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsforkids.pasz.nightlightpromax.Adapters.ImageAdapter;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.ImageFile;
import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.Light;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ImageListFragment extends Fragment  {

    GridLayoutManager gm;

    RealmResults<Light> imageFiles;
    RecyclerView rv_cards;
    int height;
    ImageView close_button;

    Realm realm = Realm.getDefaultInstance();

    public ImageListFragment() {
        super(R.layout.image_list_fragment);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int spanCount = 3;
        gm = new GridLayoutManager(getContext(),spanCount, RecyclerView.VERTICAL, false);
        rv_cards = view.findViewById(R.id.rv_cards);
        close_button = view.findViewById(R.id.close_button);
        rv_cards.setLayoutManager(gm);

        imageFiles = getFromRealm();




        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.widthPixels;
        height = height/spanCount;

        ImageAdapter imageAdapter = new ImageAdapter(imageFiles, height);
        rv_cards.setAdapter(imageAdapter);





        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().remove(ImageListFragment.this).commit();
            }
        });


    }

    private  RealmResults<Light> getFromRealm(){


        RealmResults<Light> realmResults = realm.where(Light.class).findAll();

        return realmResults;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        realm.close();
    }
}

