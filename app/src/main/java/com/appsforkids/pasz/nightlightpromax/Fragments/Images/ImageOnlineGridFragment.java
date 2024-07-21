package com.appsforkids.pasz.nightlightpromax.Fragments.Images;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsforkids.pasz.nightlightpromax.Adapters.ImageOnlineAdapter;
import com.appsforkids.pasz.nightlightpromax.Fragments.MainFragment;
import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.Light;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageOnlineGridFragment extends Fragment  {

    GridLayoutManager gm;
    String json;
    RecyclerView rv_cards;
    int height;
    ImageView close_button;

    public ImageOnlineGridFragment() {
        super(R.layout.list_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int spanCount = 3;
        gm = new GridLayoutManager(getContext(),spanCount, RecyclerView.VERTICAL, false);
        rv_cards = view.findViewById(R.id.rv_cards);
        close_button = view.findViewById(R.id.close_button);
        rv_cards.setLayoutManager(gm);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.widthPixels;
        height = height/spanCount;
        //json = requireArguments().getString("json");
        json = getArguments().getString("json");
        ArrayList<Light> lightsArray = new ArrayList<>();

        try {
            JSONObject pack = new JSONObject(json);
            JSONArray jsonArray = (JSONArray) pack.get("pack");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Light light = new Light();
                light.setInternetLink(jsonObject.getString("internet_link"));
                light.setMypic(-1);
                light.setId(jsonObject.getString("internet_link"));

                String animalName = jsonObject.getString("name");

                @SuppressLint("DiscouragedApi") int resourceID = getResources()
                        .getIdentifier(animalName, "string", getActivity().getPackageName());


                light.setMytext(resourceID);

                lightsArray.add(light);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        ImageOnlineAdapter imageOnlineAdapter = new ImageOnlineAdapter(lightsArray, height);
        rv_cards.setAdapter(imageOnlineAdapter);

        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainFragment mainFragment = (MainFragment) getParentFragmentManager()
                        .findFragmentByTag("MAIN_FRAGMENT");
                mainFragment.refresh();

               // getParentFragmentManager().beginTransaction().remove(ImageOnlineGridFragment.this).commit();

                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.empty, new ImageOnlineListFragment())
                        .commit();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

