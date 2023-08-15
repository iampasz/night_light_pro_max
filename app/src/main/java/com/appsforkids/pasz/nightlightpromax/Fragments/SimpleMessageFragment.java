package com.appsforkids.pasz.nightlightpromax.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.appsforkids.pasz.nightlightpromax.R;
import com.easyandroidanimations.library.FadeInAnimation;
import com.easyandroidanimations.library.ScaleInAnimation;


public class SimpleMessageFragment extends Fragment {


    ImageView ok_button;


    TextView dialog_message;


    public static SimpleMessageFragment init(String message){

        SimpleMessageFragment unlockFragment = new SimpleMessageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        unlockFragment.setArguments(bundle);

        return unlockFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.simple_message_fragment, container, false);

        dialog_message = view.findViewById(R.id.dialog_message);
        dialog_message.setText(getArguments().getString("message"));

        ok_button = view.findViewById(R.id.ok_button);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new FadeInAnimation(view).setDuration(300).animate();
        new ScaleInAnimation(view).setDuration(500).animate();



        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getParentFragmentManager();
                fm.beginTransaction().remove(SimpleMessageFragment.this).commit();

            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
