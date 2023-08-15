package com.appsforkids.pasz.nightlightpromax.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.appsforkids.pasz.nightlightpromax.Interfaces.DoThisAction;
import com.appsforkids.pasz.nightlightpromax.R;
import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.AnimationListener;
import com.easyandroidanimations.library.FadeInAnimation;
import com.easyandroidanimations.library.FadeOutAnimation;
import com.easyandroidanimations.library.ScaleInAnimation;

import java.io.Serializable;



public class MessageFragment extends Fragment implements Serializable {

    @SuppressLint("NonConstantResourceId")

    FrameLayout no_button;

    @SuppressLint("NonConstantResourceId")

    FrameLayout yes_button;

    @SuppressLint("NonConstantResourceId")

    TextView dialog_message;

    @SuppressLint("NonConstantResourceId")

    ConstraintLayout frame_constraine;

    DoThisAction doThisAction;

    public static MessageFragment init(String message, DoThisAction doThisAction){

        MessageFragment unlockFragment = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putSerializable("do_this", doThisAction);
        unlockFragment.setArguments(bundle);

        return unlockFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.message_fragment, container, false);

        dialog_message.setText(getArguments().getString("message"));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new FadeInAnimation(view).setDuration(300).animate();
        new ScaleInAnimation(view).setDuration(500).animate();

        doThisAction = (DoThisAction)  getArguments().getSerializable("do_this");

        no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //new FlipVerticalAnimation(frame_constraine).setDuration(1000).animate();
                //new ScaleOutAnimation(frame_constraine).setDuration(500).animate();

                doThisAction.doThis(0,0);
                doThisAction.doThat();

                FragmentManager fm = getParentFragmentManager();
                fm.beginTransaction().remove(MessageFragment.this).commit();

            }
        });

        yes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new FadeOutAnimation(frame_constraine).setDuration(200).setListener(new AnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        doThisAction.doThis();

                        FragmentManager fm = getParentFragmentManager();
                        fm.beginTransaction().remove(MessageFragment.this).commit();

                    }
                }).animate();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
