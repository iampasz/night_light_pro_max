package com.appsforkids.pasz.nightlightpromax.Fragments;

import static android.R.anim.linear_interpolator;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.appsforkids.pasz.nightlightpromax.FabrikLightsArray;
import com.appsforkids.pasz.nightlightpromax.Interfaces.MyCallback;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.Light;
import com.appsforkids.pasz.nightlightpromax.Adapters.MyAdapter;
import com.appsforkids.pasz.nightlightpromax.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import me.relex.circleindicator.CircleIndicator3;

public class MainFragment extends Fragment implements Brights.MyInterface {

    @BindView(R2.id.pager) ViewPager2 pager;
    @BindView(R2.id.starsButton) ImageView starsButton;
    @BindView(R2.id.sunButton) ImageView sunButton;
    @BindView(R2.id.lullabyButton) ImageView lullabyButton;
    @BindView(R2.id.fon1) ImageView fon1;
    @BindView(R2.id.fon1) ImageView fon2;
    @BindView(R2.id.fon3) ImageView fon3;
    @BindView(R2.id.timerButton) ImageView timerButton;
    @BindView(R2.id.melody_bt) ImageView melody_bt;
    @BindView(R2.id.bgcolor) ImageView bgcolorButton;
    @BindView(R2.id.automate) ImageView automate;
    @BindView(R2.id.bottom_text) TextView bottom_text;
    @BindView(R2.id.autoButton) ImageView autoButton;
    @BindView(R2.id.gallery_bt) ImageView gallery_bt;
    @BindView(R2.id.lockScrean) FrameLayout lockScrean;
    @BindView(R2.id.main) ConstraintLayout fonLayout;
    @BindView(R2.id.flow) Flow flow;
    @BindView(R2.id.indicator) CircleIndicator3 indicator;

    ImageView lockButton;

    CountDownTimer offMessage;
    CountDownTimer globalTimer;
    CountDownTimer cdt;

    int current_item = 2;
    boolean timerStatus = false;
    int fonStatus = 0;
    int lullabyStatus = 0;
    int bgStatus = 0;
    MyAdapter mAdapter;
    RealmResults<Light> mylight;
    private Timer  autoChange;

    private Animation  mFadeOutAnimation;
    boolean checkAutomate = true;
    boolean coution;
    boolean chekMenu = true;
    boolean show = true;
    CheckBox cbPositiveNull;
    AnimationSet set, set2;
    MediaPlayer mediaPlayer;



    int mCounter;
    int useCounter = 0;
    int addCounter;
    int currentNlBright;

    AdRequest adRequest;
    private InterstitialAd mInterstitialAd;
    private RewardedAd rewardedAd;

    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "mysettings";

    Realm realm = Realm.getDefaultInstance();

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.main_fragment, container, false);
//        return view;
//    }

    public MainFragment() {
        super(R.layout.main_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(getActivity());

        melody_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.my_container, new TabAudiotFragment()).commit();
            }
        });
        gallery_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.my_container, new TabImageFragment()).commit();
            }
        });
        startGlobalTimer();
        saveNoAdsCount(-1);
        addCounter = getNoAdsCount();
        adRequest = new AdRequest.Builder().build();

        lockScrean.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //clearAlpha();
                showButtons();
                startGlobalTimer();
                return false;
            }
        });

//        lockButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                clearAlpha();
//                //lockButton.setAlpha(1f);
//
//                lockButton();
//            }
//        });


        if (useCounter >= 5) {
        }

        // mediaPlayer = MediaPlayer.create(getContext(), R.raw.sound);

        Log.i("LONGPROCESS", "mediaPlayer");


       // mFadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
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


        FabrikLightsArray fabrik = new FabrikLightsArray();


        //textName.setText(mylight.get(0).mytext);

        //mylight = fabrik.getLightsArray();
        mylight = getLightersFromRealm();

        Log.i("LONGPROCESS", "mylight");


        mAdapter = new MyAdapter(mylight);

        Log.i("LONGPROCESS", "mAdapter");


        pager.setAdapter(mAdapter);

        Log.i("LONGPROCESS", " mPager.setAdapter(mAdapter);");


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            mPager.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//                    Toast.makeText(MainActivity.this, "fewfwefew", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //bottom_text.setText(mylight.get(mPager.getCurrentItem()).getMytext());

                // Light light = new Light(R.drawable.acsept_button, R.string.answer_no);

            }
        });

        Log.i("LONGPROCESS", " indicator");



        indicator.setViewPager(pager);

// optional
        mAdapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());

        Log.i("LONGPROCESS", " mAdapter.re");


//        adsView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                clearAlpha();
//                adsView.setAlpha(1f);
//                NoAds noAdsFragment = (NoAds) getParentFragmentManager().findFragmentByTag("NO_ADS");
//
//                if (noAdsFragment != null && noAdsFragment.isVisible()) {
//                    getParentFragmentManager().beginTransaction().remove(noAdsFragment).commit();
//                    //adsView.setImageResource(R.drawable.ads);
//                } else {
//                    //adsView.setImageResource(R.drawable.lock);
//                    getParentFragmentManager().beginTransaction().replace(R.id.container, new NoAds(), "NO_ADS").commit();
//                }
//            }
//        });

        Log.i("LONGPROCESS", " adsView");


        starsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startConsoleTimer();
                clearAlpha();
                starsButton.setAlpha(1f);
                //  closeOpenFragment();
                setBgStatus();
            }
        });

        Log.i("LONGPROCESS", " starsButton");

        lullabyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startConsoleTimer();
                clearAlpha();
                lullabyButton.setAlpha(1f);

                // closeOpenFragment();
                setLullabyStatus();
            }
        });

        Log.i("LONGPROCESS", " lullabyButton");

        bgcolorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startConsoleTimer();

                clearAlpha();
                bgcolorButton.setAlpha(1f);

                //  closeOpenFragment();
                setFonStatus();
            }
        });

        Log.i("LONGPROCESS", " bgcolorButton");
        timerButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                clearAlpha();
                timerButton.setAlpha(1f);

                //closeOpenFragment();


                TimerFragment timer_fragment = (TimerFragment) getParentFragmentManager().findFragmentByTag("TIMER_FRAGMENT");

                if (timer_fragment != null && timer_fragment.isVisible()) {
                    mAdapter.notifyDataSetChanged();
                } else {
                    getParentFragmentManager().beginTransaction().replace(R.id.container, new TimerFragment(), "TIMER_FRAGMENT").commit();
                }
            }
        });

        Log.i("LONGPROCESS", " timerButton");


        sunButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clearAlpha();
                sunButton.setAlpha(1f);

               // closeOpenFragment();

                Brights brightsFragment = (Brights) getParentFragmentManager().findFragmentByTag("BRIGHTS_FRAGMENT");

                if (brightsFragment != null && brightsFragment.isVisible()) {
                    mAdapter.notifyDataSetChanged();
                } else {
                    getParentFragmentManager().beginTransaction().replace(R.id.container, new Brights(), "BRIGHTS_FRAGMENT").commit();
                }

            }

        });

        Log.i("LONGPROCESS", " sunButton");

        autoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //     closeOpenFragment();
                setAutomate();
            }
        });

        Log.i("LONGPROCESS", " autoButton");

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getSettings();

        Log.i("LONGPROCESS", " getSettings");
    }


    public void startTimer(int hours, int minutes) {
        bottom_text.setVisibility(View.VISIBLE);
        bottom_text.setText("");
        int mySeconds = (((hours * 60 * 60) + (60 * minutes)) * 1000);
        closeApp(mySeconds);
        //sendAnalystics("timer", "timer is: " + hours +" and " + minutes);

    }

    @Override
    public void onTrigger() {
        mAdapter.notifyDataSetChanged();
        Log.i("BRIGHTS", "notifyDataSetChanged");
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lockButton.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    class MySleepTimer extends TimerTask {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    getActivity().finish();
                }
            });
        }
    }

//    class MyAutoTimer extends TimerTask {
//        @Override
//        public void run() {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    mFadeInAnimation.setAnimationListener(animationFadeOutListener);
//                    automate.startAnimation(mFadeInAnimation);
//                }
//            });
//        }
//    }

//    private void autoChengPic(MyAutoTimer mAutoTimer, int delay) {
//        if (autoChange != null) {
//            autoChange.cancel();
//        }
//
//        autoChange = new Timer();
//        mAutoTimer = new MyAutoTimer();
//        autoChange.schedule(mAutoTimer, delay, 10000);
//    }

    int ic = 0;
    Animation.AnimationListener animationFadeOutListener = new Animation.AnimationListener() {

        @Override
        public void onAnimationEnd(Animation animation) {



            if(mylight.size()>0){
                automate.setImageResource(mylight.get(ic).getMypic());

            }

//            int value = getBrightsPreference(holder.imageView.getContext());
//            holder.imageView.setColorFilter(brightIt(value));

            currentNlBright = getBrightsPreference();

            automate.setColorFilter(brightIt(currentNlBright));

            automate.startAnimation(mFadeOutAnimation);
            ic++;

            if (ic == mylight.size()) {
                ic = 0;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub
            automate.setVisibility(View.VISIBLE);
        }
    };

    Animation.AnimationListener animationFadeInListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            // mImageView.startAnimation(mFadeOutAnimation);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub
        }
    };

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

    private void setBgStatus() {

        switch (bgStatus) {
            case 0:
                fon2.startAnimation(set);
                fon3.startAnimation(set2);
                fon2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                fon3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                fon1.setVisibility(View.VISIBLE);
                fon2.setVisibility(View.VISIBLE);
                fon3.setVisibility(View.VISIBLE);
                bgStatus++;
                showToast(getString(R.string.star_on));
                break;

            case 1:

                fon2.clearAnimation();
                fon3.clearAnimation();
                fon2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                fon3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                bgStatus++;
                showToast(getString(R.string.star_anim_off));
                break;

            case 2:

                fon1.setVisibility(View.GONE);
                fon2.setVisibility(View.GONE);
                fon3.setVisibility(View.GONE);
                showToast(getString(R.string.star_off));
                bgStatus = 0;
                break;
        }

    }

    private void setLullabyStatus() {
        switch (lullabyStatus) {
            case 0:
                lullabyButton.setImageResource(R.drawable.bt_repeat);
                mediaPlayer = MediaPlayer.create(getContext(), R.raw.sound);
                mediaPlayer.start();
                lullabyStatus = 1;
                showToast(getString(R.string.melody));
                break;
            case 1:
                mediaPlayer.reset();

                mediaPlayer = MediaPlayer.create(getContext(), R.raw.sound);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();

                lullabyStatus = 2;
                showToast(getString(R.string.melody_loop));
                break;
            case 2:
                lullabyButton.setImageResource(R.drawable.bt_nota);
                mediaPlayer.reset();
                lullabyStatus = 0;
                showToast(getString(R.string.melody_off));
                break;
        }
    }

    private void setAutomate() {

        clearAlpha();


        getBrightsPreference();

        if (checkAutomate) {
            autoButton.setAlpha(1f);
            automate.setVisibility(View.VISIBLE);
            pager.setVisibility(View.GONE);
            showToast(getString(R.string.auto_change));
            autoChange = new Timer();
           // mAutoTimer = new MyAutoTimer();
           // autoChange.schedule(mAutoTimer, 3000, 10000);
            checkAutomate = false;
        } else {
            automate.clearAnimation();
            automate.setVisibility(View.GONE);
            showToast(getString(R.string.auto_change_off));
            if (autoChange != null) {
                autoChange.cancel();
            }
            pager.setVisibility(View.VISIBLE);
            checkAutomate = true;
        }
    }

    private void setFonStatus() {

        switch (fonStatus) {
            case 0:

                fon1.setImageResource(R.drawable.bg_green);
                fon2.setImageResource(R.drawable.stars_green);
                fon3.setImageResource(R.drawable.lg_green);
                fonLayout.setBackgroundResource(R.color.green);
                fonStatus++;
                showToast(getString(R.string.bgcolor_green));
                break;

            case 1:
                fon1.setImageResource(R.drawable.bg_purple);
                fon2.setImageResource(R.drawable.stars_purple);
                fon3.setImageResource(R.drawable.lg_purple);
                fonLayout.setBackgroundResource(R.color.purpl);
                fonStatus++;
                showToast(getString(R.string.bgcolor_purple));
                break;

            case 2:
                fon1.setImageResource(R.drawable.bg_red);
                fon2.setImageResource(R.drawable.stars_red);
                fon3.setImageResource(R.drawable.lg_red);
                fonLayout.setBackgroundResource(R.color.orange);
                fonStatus++;
                showToast(getString(R.string.bgcolor_orange));
                break;

            case 3:
                fon1.setImageResource(R.drawable.bg_blue);
                fon2.setImageResource(R.drawable.stars_blue);
                fon3.setImageResource(R.drawable.lg_blue);
                fonLayout.setBackgroundResource(R.color.blue);
                showToast(getString(R.string.bgcolor_c));
                fonStatus++;
                break;

            case 4:
                fon1.setImageResource(R.drawable.bg_gray);
                fon2.setImageResource(R.drawable.stars_gray);
                fon3.setImageResource(R.drawable.lg_gray);
                fonLayout.setBackgroundResource(R.color.black);
                showToast(getString(R.string.bgcolor_durk));
                fonStatus++;
                break;

            case 5:
                fon1.setImageResource(R.drawable.bg_dark_blue);
                fon2.setImageResource(R.drawable.stars_dark_blue);
                fon3.setImageResource(R.drawable.lg_dark_blue);
                fonLayout.setBackgroundResource(R.color.darkblue);
                showToast(getString(R.string.bgcolor_blue));
                fonStatus = 0;
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        lullabyButton.setAlpha(0.25f);

        // mediaPlayer.reset();
        saveSettings();
        lullabyStatus = 0;

        realm.close();
    }

    @Override
    public void onPause() {
        //    mAdView.pause();

        super.onPause();
        lullabyButton.setAlpha(0.25f);
        //mediaPlayer.reset();

//        if(mCounter==myposition){
//             useCounter++;
//        }else{
//            useCounter=0;
//        }

        // SharedPreferences.Editor editor = mSettings.edit();
        // editor.putInt(APP_PREFERENCES_COUNTER, myposition);
        //   editor.putInt(APP_PREFERENCES_USE_COUNTER, useCounter);
        //  editor.apply();

    }

    @Override
    public void onStop() {
        super.onStop();
        //sendStatistic();
        //  lullabyButton.setAlpha(0.25f);
        //mediaPlayer.reset();
        lullabyStatus = 0;

        saveSettings();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void saveSettings() {
        SharedPreferences sharedPref = getContext().getSharedPreferences("MYPREFS", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("CURRENT_ITEM", pager.getCurrentItem());
        Log.i("SETTINGS", "saveSettings" + pager.getCurrentItem());
        editor.apply();
    }

    public void saveNoAdsCount(int adCounter) {

        int currentCount = getNoAdsCount();
        if (currentCount < 1 && adCounter == -1) {

        } else {
            SharedPreferences sharedPref = getContext().getSharedPreferences("MYPREFS", 0);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("NO_ADS_COUNTER", currentCount + adCounter);
            editor.apply();
        }

        Log.i("ADDCOUNTER", currentCount + " Реклама отключена");
    }

    public int getNoAdsCount() {
        SharedPreferences sharedPref = getContext().getSharedPreferences("MYPREFS", 0);
        int current_item = sharedPref.getInt("NO_ADS_COUNTER", 0);
        return current_item;
    }

    public void getSettings() {
        SharedPreferences sharedPref = getContext().getSharedPreferences("MYPREFS", 0);
        current_item = sharedPref.getInt("CURRENT_ITEM", 0);
       // mPager.setCurrentItem(current_item);
        Log.i("SETTINGS", "current_item" + current_item);
    }

    public void closeApp(int mySeconds) {

        if (cdt != null) {
            timerStatus = false;
            cdt.cancel();
            cdt = null;
        }
        if (mySeconds > 0) {
            timerStatus = true;
            cdt = new CountDownTimer(mySeconds, 1000) {
                @SuppressLint("DefaultLocale")
                @Override
                public void onTick(long l) {
                    bottom_text.setText(String.format("%02d:%02d:%02d", (l / 1000) / 3600, ((l / 1000) % 3600) / 60, (l / 1000) % 60));
                }

                @Override
                public void onFinish() {
                    Log.i("FINISH", "App is OFF");
                    getActivity().finish();
                }
            };
            cdt.start();
        } else {
            bottom_text.setVisibility(View.INVISIBLE);
        }
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
            chekMenu = false;
            show = false;
        } else {

            lockScrean.setClickable(false);
            bottom_text.setVisibility(View.VISIBLE);
            flow.setVisibility(View.VISIBLE);
            //adsView.setVisibility(View.VISIBLE);
            indicator.setVisibility(View.VISIBLE);

            lockButton.setImageResource(R.drawable.bt_unlock);
            lockScrean.setClickable(false);
            chekMenu = true;
            show = true;

            if (getNoAdsCount() > 0) {

            } else {
                //mAdView.setVisibility(View.VISIBLE);
                showInterstitial();
            }


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


    public void loadInterstitial() {

        if (addCounter > 0) {

        } else {
            InterstitialAd.load(getContext(), "ca-app-pub-1237459888817948/5738678237", adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            // The mInterstitialAd reference will be null until
                            // an ad is loaded.
                            mInterstitialAd = interstitialAd;
                            Log.i("ADS", "onAdLoaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error
                            Log.d("ADS", loadAdError.toString());
                            mInterstitialAd = null;
                        }
                    });
        }

    }

    public void showInterstitial() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(getActivity());
            loadInterstitial();
        } else {
            Log.d("ADS", "The interstitial ad wasn't ready yet.");
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

    public void clearAlpha() {
  //      starsButton.setAlpha(0.25f);
   //     timerButton.setAlpha(0.25f);
   //     lullabyButton.setAlpha(0.25f);
  ///      bgcolorButton.setAlpha(0.25f);
   //     sunButton.setAlpha(0.25f);
  //      autoButton.setAlpha(0.25f);
//        adsView.setAlpha(0.25f);
//        lockButton.setAlpha(0.25f);
    }

    public void loadRewardedAd() {
        RewardedAd.load(getContext(), "ca-app-pub-1237459888817948/5203491613",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d("TAG", loadAdError.toString());
                        rewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;
                        Log.d("TAG", "Ad was loaded.");
                    }
                });
    }

    public void showRewardedAd(MyCallback myCallback) {
        if (rewardedAd != null) {
            rewardedAd.show(getActivity(), new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    Log.d("TAG", "The user earned the reward.");

                    saveNoAdsCount(2);

                    myCallback.isShown(true);
                    loadRewardedAd();

                }
            });

            rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdClicked() {
                    // Called when a click is recorded for an ad.
                    Log.d("TAG", "Ad was clicked.");
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    // Set the ad reference to null so you don't show the ad a second time.
                    Log.d("TAG", "Ad dismissed fullscreen content.");
                    rewardedAd = null;
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    // Called when ad fails to show.
                    Log.e("TAG", "Ad failed to show fullscreen content.");
                    rewardedAd = null;
                }

                @Override
                public void onAdImpression() {
                    // Called when an impression is recorded for an ad.
                    Log.d("TAG", "Ad recorded an impression.");
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    Log.d("TAG", "Ad showed fullscreen content.");
                }
            });

        } else {
            Log.d("TAG", "The rewarded ad wasn't ready yet.");
            myCallback.isShown(false);
        }
    }

//    public void onAdLoaded() {
//        Log.d("Banner adapter class name: " + ad.getResponseInfo().getMediationAdapterClassName());
//    }


    private RealmResults<Light> getLightersFromRealm() {

        return realm.where(Light.class).equalTo("status", true).findAll();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}
