package com.appsforkids.pasz.nightlightpromax.Fragments;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsforkids.pasz.nightlightpromax.Adapters.ImageOnlineAdapter;
import com.appsforkids.pasz.nightlightpromax.Adapters.MyCategoryImageAdapter;
import com.appsforkids.pasz.nightlightpromax.GSON.MyGson;
import com.appsforkids.pasz.nightlightpromax.Interfaces.DoThis;
import com.appsforkids.pasz.nightlightpromax.Interfaces.GetJson;
import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.ReadJson;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.Light;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ImageOnlineListFragment extends Fragment {

    //GridLayoutManager gm;

    RecyclerView rv_cards;
    int height;
    ImageView close_button;


    public ImageOnlineListFragment() {
        super(R.layout.image_online_list_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int spanCount = 3;

        //gm = new GridLayoutManager(getContext(),spanCount, RecyclerView.VERTICAL, false);
        // gm = new GridLayoutManager(getContext(),spanCount, RecyclerView.VERTICAL, false);

        LinearLayoutManager lm = new LinearLayoutManager(getContext());

        rv_cards = view.findViewById(R.id.rv_cards);
        close_button = view.findViewById(R.id.close_button);
        rv_cards.setLayoutManager(lm);

        getJson("https://koko-oko.com/json/nlpm.json");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.widthPixels;
        height = height / spanCount;

        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.my_container, new MainFragment()).commit();
            }
        });

    }

    public void getJson(String url) {

        ReadJson readJson = new ReadJson(new GetJson() {
            @Override
            public void getJson(String result) {

                ArrayList<Light> lightsArray;
                lightsArray = new ArrayList<>();

                JSONArray jsonArray;

                try {
                    String image = new JSONObject(result).getString("images");
                    jsonArray = new JSONArray(image);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Light light = new Light();
                light.setMypic(-1);

                MyCategoryImageAdapter myCategoryImageAdapter = new MyCategoryImageAdapter(jsonArray, new DoThis() {
                    @Override
                    public void doThis(int position) {

                        Bundle bundle = new Bundle();
                        try {
                            bundle.putString("json", jsonArray.get(position).toString());
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        getParentFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .add(R.id.my_container, ImageOnlineGridFragment.class, bundle)
                                .commit();

                    }
                });
                rv_cards.setAdapter(myCategoryImageAdapter);

            }

            @Override
            public void noAnswer(Boolean answer) {
            }
        });
        readJson.execute(url);
    }


}
