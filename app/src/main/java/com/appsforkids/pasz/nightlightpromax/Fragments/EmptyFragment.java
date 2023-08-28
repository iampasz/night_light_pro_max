package com.appsforkids.pasz.nightlightpromax.Fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appsforkids.pasz.nightlightpromax.Fragments.Images.ImageOnlineListFragment;
import com.appsforkids.pasz.nightlightpromax.R;

public class EmptyFragment extends Fragment {

    public EmptyFragment() {
        super(R.layout.empty_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getParentFragmentManager().beginTransaction().add(R.id.empty, new ImageOnlineListFragment()).commit();

    }
}
