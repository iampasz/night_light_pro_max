package com.appsforkids.pasz.nightlightpromax.Fragments.Images;


import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsforkids.pasz.nightlightpromax.Adapters.MyCategoryImageAdapter;
import com.appsforkids.pasz.nightlightpromax.Interfaces.DoThis;
import com.appsforkids.pasz.nightlightpromax.Interfaces.GetJson;
import com.appsforkids.pasz.nightlightpromax.JSON.JSONValidator;
import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.ReadJson;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.Light;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.ChekInternetConnectionUseCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageOnlineListFragment extends Fragment {

    RecyclerView rv_cards;
    int height;
    ImageView close_button;
    JSONArray jsonArray;
    MyCategoryImageAdapter myCategoryImageAdapter;
    TextView error_text;
    ChekInternetConnectionUseCase chekInternetConnection = new ChekInternetConnectionUseCase();

    public ImageOnlineListFragment() {
        super(R.layout.list_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        int spanCount = 3;
        LinearLayoutManager lm = new LinearLayoutManager(getContext());

        rv_cards = view.findViewById(R.id.rv_cards);
        close_button = view.findViewById(R.id.close_button);
        rv_cards.setLayoutManager(lm);

        error_text = view.findViewById(R.id.error_text);

        if (chekInternetConnection.execute(getContext()) == 0) {
            error_text.setVisibility(View.VISIBLE);
        }
        getJson("https://koko-oko.com/json/nlpm.json");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.widthPixels;
        height = height / spanCount;

        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TabImageFragment tabImageFragment = (TabImageFragment) getParentFragmentManager()
                        .findFragmentByTag("TAB_IMAGE_FRAGMENT");

                assert tabImageFragment != null;
                getParentFragmentManager()
                        .beginTransaction()
                        .remove(tabImageFragment)
                        .commit();
            }
        });

    }

    public void getJson(String url) {
        ReadJson readJson = new ReadJson(new GetJson() {
            @Override
            public ArrayList<AudioFile> getJson(String result) {

                ArrayList<Light> lightsArray;
                lightsArray = new ArrayList<>();

                if (JSONValidator.isJSONValid(result)) {

                    try {
                        String image = new JSONObject(result).getString("images");
                        jsonArray = new JSONArray(image);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    Light light = new Light();
                    light.setMypic(-1);


                    if(jsonArray!=null){
                        myCategoryImageAdapter = new MyCategoryImageAdapter(jsonArray, new DoThis() {
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
                                        .replace(R.id.empty, ImageOnlineGridFragment.class, bundle)
                                        .commit();
                            }
                        });
                        rv_cards.setAdapter(myCategoryImageAdapter);
                    }


                } else {

                }

                return null;
            }

            @Override
            public void noAnswer(Boolean answer) {
            }
        });
        readJson.execute(url);
    }
}
