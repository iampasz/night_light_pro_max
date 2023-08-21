package com.appsforkids.pasz.nightlightpromax.Fragments;

import static android.R.anim.linear_interpolator;

import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.appsforkids.pasz.nightlightpromax.Fragments.Images.TabImageFragment;
import com.appsforkids.pasz.nightlightpromax.Fragments.Melodies.TabAudiotFragment;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.Light;
import com.appsforkids.pasz.nightlightpromax.Adapters.MyAdapter;
import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.MySettings;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CreateMyMediaPlayerUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.InstanceRealmConfigurationUseCase;


import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import me.relex.circleindicator.CircleIndicator3;

public class MainFragment extends Fragment implements Brights.MyInterface, View.OnClickListener, View.OnTouchListener
{
    //My Buttons
    ImageView starsButton;
    ImageView timerButton;
    ImageView bgcolorButton;
    ImageView sunButton;
    ImageView lockButton;
    ImageView gallery_bt;
    ImageView melody_bt;
    //

    //My Background
    ConstraintLayout fonLayout;
    ImageView fon1;
    ImageView fon2;
    ImageView fon3;

    Realm realm;

   // ImageView automate;

    //Counter
    int bgCount;
    int bgColorCount;

    //My AUTO Change nightlighter

    private Animation mFadeInAnimation, mFadeOutAnimation;


    Flow flow;
    ViewPager2 pager;
    CircleIndicator3 indicator;
    TextView bottom_text;
    FrameLayout lockScrean;
    CountDownTimer offMessage;
    CountDownTimer globalTimer;
    boolean timerStatus = false;
    int fonStatus = 0;
    MyAdapter mAdapter;

    boolean checkAutomate = true;
    boolean isAuto = false;
    boolean chekMenu = true;
    boolean show = true;
    boolean automateIsOn = false;
    AnimationSet set, set2, automate_set;
    CountDownTimer countDownTimer;
    RealmResults<Light> mylight;

    CreateMyMediaPlayerUseCase createMyMediaPlayerUseCase = new CreateMyMediaPlayerUseCase();



    public MainFragment() {
        super(R.layout.main_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        realm = new InstanceRealmConfigurationUseCase().connect();

        init(view);
        startGlobalTimer();
        setMyAnimations();
        setMyViewPager();
        setSettings();

        starsButton.setOnClickListener(this);Realm realm = new InstanceRealmConfigurationUseCase().connect();
        timerButton.setOnClickListener(this);
        bgcolorButton.setOnClickListener(this);
        sunButton.setOnClickListener(this);

        lockButton.setOnClickListener(this);
        gallery_bt.setOnClickListener(this);
        melody_bt.setOnClickListener(this);

        lockScrean.setOnTouchListener(this);
    }

    public void startTimer(int hours, int minutes) {
        bottom_text.setVisibility(View.VISIBLE);
        bottom_text.setText("");
        int mySeconds = (((hours * 60 * 60) + (60 * minutes)) * 1000);
        //sendAnalystics("timer", "timer is: " + hours +" and " + minutes);
    }

    @Override
    public void onTrigger() {
        mAdapter.notifyDataSetChanged();
        Log.i("BRIGHTS", "notifyDataSetChanged");
    }

    public void showToast(String string) {

        bottom_text.setVisibility(View.VISIBLE);

        if (offMessage == null) {
            offMessage = new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    if (!timerStatus) {
                        bottom_text.setVisibility(View.INVISIBLE);
                        bottom_text.setText("");
                    }

                }
            }.start();
        } else {
            offMessage.cancel();
            offMessage.start();
        }

        bottom_text.setText(string);
    }
    public void lockButton() {

        //closeOpenFragment();

        if (chekMenu) {
            lockScrean.setClickable(true);
            bottom_text.setVisibility(View.INVISIBLE);
            flow.setVisibility(View.INVISIBLE);
            // adsView.setVisibility(View.INVISIBLE);
            //mAdView.setVisibility(View.GONE);
            indicator.setVisibility(View.INVISIBLE);
            lockButton.setImageResource(R.drawable.bt_closed);
            lockScrean.setClickable(true);
            gallery_bt.setVisibility(View.INVISIBLE);
            melody_bt.setVisibility(View.INVISIBLE);
            chekMenu = false;
            show = false;
        } else {

            lockScrean.setClickable(false);
            bottom_text.setVisibility(View.VISIBLE);
            flow.setVisibility(View.VISIBLE);
            gallery_bt.setVisibility(View.VISIBLE);
            melody_bt.setVisibility(View.VISIBLE);
            indicator.setVisibility(View.VISIBLE);

            lockButton.setImageResource(R.drawable.bt_unlock);
            lockScrean.setClickable(false);
            chekMenu = true;
            show = true;

        }

    }
    private void startGlobalTimer() {
        if (globalTimer != null) {
            globalTimer.start();
        } else {
            globalTimer = new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {

                    // clearAlpha();

                    if (chekMenu) {

                    } else {
                        lockButton.setVisibility(View.INVISIBLE);
                        bottom_text.setVisibility(View.INVISIBLE);
                    }

//                    if(lockButton!=null){
//                        lockButton.setVisibility(View.INVISIBLE);
//                       // flow.setVisibility(View.INVISIBLE);
//                       // adsView.setVisibility(View.GONE);
//                    }
//                    if(bottom_text!=null){
//                        bottom_text.setVisibility(View.INVISIBLE);
//                    }


                }
            }.start();
        }
    }
    private void showButtons() {
        if (chekMenu) {
            //            lockButton.setVisibility(View.VISIBLE);
            bottom_text.setVisibility(View.VISIBLE);
            flow.setVisibility(View.VISIBLE);
//            adsView.setVisibility(View.VISIBLE);
            // settings_button.setVisibility(View.VISIBLE);
            //rv.setVisibility(View.VISIBLE);
        } else {
            lockButton.setVisibility(View.VISIBLE);
            bottom_text.setVisibility(View.VISIBLE);
        }
    }
    public static ColorMatrixColorFilter brightIt(int fb) {
        ColorMatrix cmB = new ColorMatrix();
        cmB.set(new float[]{
                1, 0, 0, 0, fb,
                0, 1, 0, 0, fb,
                0, 0, 1, 0, fb,
                0, 0, 0, 1, 0});

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(cmB);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(colorMatrix);
        return f;
    }
    public int getBrightsPreference() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("MYPREFS", 0);
        return sharedPref.getInt("BRIGHT", 0);
    }
    private void closeOpenFragment() {
        List<Fragment> arrayFragments = getParentFragmentManager().getFragments();
        if (arrayFragments.size() > 0) {
            getParentFragmentManager().beginTransaction().remove(arrayFragments.get(0)).commit();
        }
    }

    private RealmResults<Light> getLightersFromRealm() {
        return realm.where(Light.class).equalTo("status", true).findAll();
    }

    private void init(View view) {
        pager = view.findViewById(R.id.pager);
        starsButton = view.findViewById(R.id.stars_button);
        sunButton = view.findViewById(R.id.sunButton);
        lockButton = view.findViewById(R.id.lockButton);
        fon1 = view.findViewById(R.id.fon1);
        fon2 = view.findViewById(R.id.fon2);
        fon3 = view.findViewById(R.id.fon3);
        timerButton = view.findViewById(R.id.timerButton);
        melody_bt = view.findViewById(R.id.melody_bt);
        bgcolorButton = view.findViewById(R.id.bgcolor);
        bottom_text = view.findViewById(R.id.bottom_text);
        gallery_bt = view.findViewById(R.id.gallery_bt);
        lockScrean = view.findViewById(R.id.lockScrean);
        fonLayout = view.findViewById(R.id.main);
        flow = view.findViewById(R.id.flow);
        indicator = view.findViewById(R.id.indicator);
       // automate = view.findViewById(R.id.automate);
    }
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.melody_bt) {
            getParentFragmentManager()
                    .beginTransaction()
                    .add(R.id.my_container, new TabAudiotFragment())
                    .commit();
        }

        if (v.getId() == R.id.gallery_bt) {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.my_container, new TabImageFragment())
                    .commit();
        }

        if (v.getId() == R.id.bgcolor) {
            //startConsoleTimer();
            bgcolorButton.setAlpha(1f);
            //  closeOpenFragment();

            bgColorCount++;
            if(bgColorCount>5){
                bgColorCount=0;
            }
            setBgColor(bgColorCount);
        }

        if (v.getId() == R.id.timerButton) {
            //closeOpenFragment();

            TimerFragment timer_fragment = (TimerFragment) getParentFragmentManager().findFragmentByTag("TIMER_FRAGMENT");

            if (timer_fragment != null && timer_fragment.isVisible()) {
                mAdapter.notifyDataSetChanged();
            } else {
                getParentFragmentManager().beginTransaction().replace(R.id.container, new TimerFragment(), "TIMER_FRAGMENT").commit();
            }
        }

        if (v.getId() == R.id.sunButton) {
            // closeOpenFragment();
            Brights brightsFragment = (Brights) getParentFragmentManager().findFragmentByTag("BRIGHTS_FRAGMENT");
            if (brightsFragment != null && brightsFragment.isVisible()) {
                mAdapter.notifyDataSetChanged();
            } else {
                getParentFragmentManager().beginTransaction().add(R.id.my_container, new Brights(), "BRIGHTS_FRAGMENT").commit();
            }
        }


        if (v.getId() == R.id.stars_button) {
            //startConsoleTimer();
            starsButton.setAlpha(1f);
            //  closeOpenFragment();

            bgCount++;
            if(bgCount>2){
                bgCount=0;
            }
            setBackground(bgCount);
        }

        if (v.getId() == R.id.lockButton) {
            lockButton();
        }
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        showButtons();
        startGlobalTimer();
        return false;
    }
    private void setMyAnimations() {

        mFadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
        mFadeOutAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fadeout);

        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f); //2
        ScaleAnimation scale = new ScaleAnimation(2.0f, 2.0f, 2.0f, 2.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(100000); //3
        rotate.setRepeatMode(Animation.INFINITE); //4
        rotate.setRepeatCount(-1); //5
        rotate.setInterpolator(getContext(), linear_interpolator);
        scale.setDuration(100000); //3
        scale.setRepeatMode(Animation.REVERSE); //4
        scale.setRepeatCount(-1); //5
        set = new AnimationSet(false); //10
        set.addAnimation(rotate); //11
        set.addAnimation(scale);

        RotateAnimation rotate2 = new RotateAnimation(360, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f); //2
        rotate2.setDuration(100000);
        rotate2.setInterpolator(getContext(), linear_interpolator);
        rotate2.setRepeatMode(Animation.INFINITE);
        rotate2.setRepeatCount(-1);
        set2 = new AnimationSet(false); //10
        set2.addAnimation(scale);
        set2.addAnimation(rotate2);
    }
    private void setMyViewPager() {
        mylight = getLightersFromRealm();
        mAdapter = new MyAdapter(mylight);
        pager.setAdapter(mAdapter);
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        indicator.setViewPager(pager);
        mAdapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());
    };
    @Override
    public void onStop() {
        super.onStop();
        //saveSettings
        MySettings mySettings = realm.where(MySettings.class).findFirst();
        realm.beginTransaction();
        mySettings.saveBackground(bgCount);
        mySettings.saveBackgroundColor(bgColorCount);
        realm.commitTransaction();
    }
    public void setSettings(){
        MySettings mySettings = realm.where(MySettings.class).findFirst();
        if(mySettings==null){
            mySettings = new MySettings();
            mySettings.saveBackground(0);
            mySettings.saveBackgroundColor(0);
            realm.beginTransaction();
            realm.copyToRealm(mySettings);
            realm.commitTransaction();
        }else{
            bgCount = mySettings.getBackground();
            bgColorCount = mySettings.getBackgroundColor();

            setBackground(bgCount);
            setBgColor(bgColorCount);
        }
    }
    private void setBackground(int bgCount) {
    switch (bgCount) {
        case 0:
            fon2.startAnimation(set);
            fon3.startAnimation(set2);
            fon2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            fon3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            fon1.setVisibility(View.VISIBLE);
            fon2.setVisibility(View.VISIBLE);
            fon3.setVisibility(View.VISIBLE);

            showToast(getString(R.string.star_on));
            break;

        case 1:

            fon2.clearAnimation();
            fon3.clearAnimation();
            fon2.setScaleType(ImageView.ScaleType.CENTER_CROP);
            fon3.setScaleType(ImageView.ScaleType.CENTER_CROP);

            showToast(getString(R.string.star_anim_off));
            break;

        case 2:

            fon1.setVisibility(View.GONE);
            fon2.setVisibility(View.GONE);
            fon3.setVisibility(View.GONE);
            showToast(getString(R.string.star_off));

            break;
    }
}
    private void setBgColor(int bgColorCount) {

        switch (bgColorCount) {
            case 0:

                fon1.setImageResource(R.drawable.bg_green);
                fon2.setImageResource(R.drawable.stars_green);
                fon3.setImageResource(R.drawable.lg_green);
                fonLayout.setBackgroundResource(R.color.green);
                showToast(getString(R.string.bgcolor_green));
                break;

            case 1:
                fon1.setImageResource(R.drawable.bg_purple);
                fon2.setImageResource(R.drawable.stars_purple);
                fon3.setImageResource(R.drawable.lg_purple);
                fonLayout.setBackgroundResource(R.color.purpl);
                showToast(getString(R.string.bgcolor_purple));
                break;

            case 2:
                fon1.setImageResource(R.drawable.bg_red);
                fon2.setImageResource(R.drawable.stars_red);
                fon3.setImageResource(R.drawable.lg_red);
                fonLayout.setBackgroundResource(R.color.orange);
                showToast(getString(R.string.bgcolor_orange));
                break;

            case 3:
                fon1.setImageResource(R.drawable.bg_blue);
                fon2.setImageResource(R.drawable.stars_blue);
                fon3.setImageResource(R.drawable.lg_blue);
                fonLayout.setBackgroundResource(R.color.blue);
                showToast(getString(R.string.bgcolor_c));
                break;

            case 4:
                fon1.setImageResource(R.drawable.bg_gray);
                fon2.setImageResource(R.drawable.stars_gray);
                fon3.setImageResource(R.drawable.lg_gray);
                fonLayout.setBackgroundResource(R.color.black);
                showToast(getString(R.string.bgcolor_durk));
                break;

            case 5:
                fon1.setImageResource(R.drawable.bg_dark_blue);
                fon2.setImageResource(R.drawable.stars_dark_blue);
                fon3.setImageResource(R.drawable.lg_dark_blue);
                fonLayout.setBackgroundResource(R.color.darkblue);
                showToast(getString(R.string.bgcolor_blue));
                break;
        }
    }
}

