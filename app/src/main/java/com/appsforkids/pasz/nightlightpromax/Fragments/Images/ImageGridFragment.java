package com.appsforkids.pasz.nightlightpromax.Fragments.Images;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsforkids.pasz.nightlightpromax.Adapters.ImageAdapter;
import com.appsforkids.pasz.nightlightpromax.Fragments.MainFragment;
import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.Light;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.InstanceRealmConfigurationUseCase;

import io.realm.Realm;
import io.realm.RealmResults;

public class ImageGridFragment extends Fragment  {

    GridLayoutManager gm;

    String json;
    RecyclerView rv_cards;
    int height;
    ImageView close_button;
    RealmResults<Light> imageFiles;
  //  ConstraintLayout constrain_image;

    ImageAdapter imageAdapter;

    Realm realm = new InstanceRealmConfigurationUseCase().connect();
    public ImageGridFragment() {
        super(R.layout.list_fragment);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int spanCount = 3;
        gm = new GridLayoutManager(getContext(),spanCount, RecyclerView.VERTICAL, false);
        rv_cards = view.findViewById(R.id.rv_cards);
        close_button = view.findViewById(R.id.close_button);
       // constrain_image = view.findViewById(R.id.constrain_image);

        rv_cards.setLayoutManager(gm);

        imageFiles = getFromRealm();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int padding = (int) getResources().getDimension(R.dimen.padding);
        height = displayMetrics.widthPixels;
        height = height-(padding*2);

//        float scale = getResources().getDisplayMetrics().density;
//        int dpAsPixels = (int) (sizeInDp*scale + 0.5f);

        height = height/spanCount;



         imageAdapter = new ImageAdapter(imageFiles, height);
        rv_cards.setAdapter(imageAdapter);

        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TabImageFragment tabImageFragment = (TabImageFragment) getParentFragmentManager()
                        .findFragmentByTag("TAB_IMAGE_FRAGMENT");

                MainFragment mainFragment = (MainFragment) getParentFragmentManager()
                        .findFragmentByTag("MAIN_FRAGMENT");
                mainFragment.refresh();

                assert tabImageFragment != null;
                getParentFragmentManager()
                        .beginTransaction()
                        .remove(tabImageFragment)
                        .commit();
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

    @Override
    public void onResume() {
        super.onResume();
        imageAdapter.notifyDataSetChanged();
    }
}

