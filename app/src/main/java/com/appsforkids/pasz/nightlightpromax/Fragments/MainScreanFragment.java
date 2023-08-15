package com.appsforkids.pasz.nightlightpromax.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appsforkids.pasz.nightlightpromax.R;

public class MainScreanFragment extends Fragment {

    public MainScreanFragment() {
        super(R.layout.main_screan);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView gallery_bt = view.findViewById(R.id.gallery_bt);
        ImageView turn_on_bt = view.findViewById(R.id.turn_on_bt);
        ImageView melody_bt = view.findViewById(R.id.melody_bt);

        turn_on_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getParentFragmentManager().beginTransaction().replace(R.id.my_container, new MainFragment()).commit();
            }
        });


    }
}
