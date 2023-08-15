package com.appsforkids.pasz.nightlightpromax.RealmObjects;

import java.io.Serializable;

import io.realm.RealmObject;

public class MySettings extends RealmObject implements Serializable {

    int nightlight;
    int animationPosition = 0;
    int nightlightPosition;
    int coins;
    int backgroundColor;
    float bright;
    int rate;
    boolean adds;
    int currentMelody;
    int timerTime;
    int gradientColor;
    boolean backgroundTumbler;
    int suitCounter;
   String  currentMusic;

    public void setCurrentMusic(String currentMusic) {
        this.currentMusic = currentMusic;
    }

    public String getCurrentMusic() {
        return currentMusic;
    }

    public int getSuitCounter() {
        return suitCounter;
    }

    public void setSuitCounter(int suitCounter) {
        this.suitCounter = suitCounter;
    }

    public boolean getBackgroundTumbler() {
        return backgroundTumbler;
    }

    public void setBackgroundTumbler(boolean backgroundTumbler) {
        this.backgroundTumbler = backgroundTumbler;
    }


    public void setGradientColor(int gradientColor) {
        this.gradientColor = gradientColor;
    }

    public int getGradientColor() {
        return gradientColor;
    }

    public void setTimerTime(int timerTime) {
        this.timerTime = timerTime;
    }

    public int getTimerTime() {
        return timerTime;
    }

    public void setCurrentMusicPosition(int currentMelody) {
        this.currentMelody = currentMelody;
    }

    public int getCurrentMusicPosition() {
        return currentMelody;
    }

    public void setAdds(boolean adds) {
        this.adds = adds;
    }

    public boolean getAdds() {
        return adds;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setNightlight(int nightlight) {
        this.nightlight = nightlight;
    }

    public int getNightlight() {
        return nightlight;
    }

    public void setAnimationPosition(int currentAnimation) {
        this.animationPosition = currentAnimation;
    }

    public int getAnimationPosition() {
        return animationPosition;
    }

    public int getNightlightPosition() {
        return nightlightPosition;
    }

    public void setNightlightPosition(int nightlightPosition) {
        this.nightlightPosition = nightlightPosition;
    }

    public void createCoinse(int coins) {
        this.coins = coins;
    }

    public int getCoins() {
        return coins;
    }

    public void addCoins(int coins) {
        this.coins = this.coins+coins;
    }

    public void spendMoney(int coins) {
        this.coins = this.coins - coins;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBright(float bright) {
    }

    public float getBright(){

        return bright;
    }
}
