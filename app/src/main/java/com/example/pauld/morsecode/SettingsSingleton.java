package com.example.pauld.morsecode;

import java.io.Serializable;

public class SettingsSingleton {
    private static volatile SettingsSingleton sInstance;
    public boolean hapticEnabled=true;
    public boolean soundEnabled=true;
    public boolean lightEnabled=true;
    public int inputSpeed=5;
    public int frequency=640;


    private SettingsSingleton() {
        if(sInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SettingsSingleton getInstance() {
        if(sInstance == null){
            synchronized (SettingsSingleton.class){
                if(sInstance==null) sInstance=new SettingsSingleton();
            }
        }
        return sInstance;
    }

    public void setHapticEnabled(boolean hapticEnabled){
        this.hapticEnabled=hapticEnabled;
    }

    public boolean getHapticEnabled(){
        return this.hapticEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled){
        this.soundEnabled=soundEnabled;
    }

    public boolean getSoundEnabled(){
        return this.soundEnabled;
    }

    public void setLightEnabled(boolean lightEnabled){
        this.lightEnabled=lightEnabled;
    }

    public boolean getLightEnabled(){
        return this.lightEnabled;
    }


    public void setInputSpeed(int inputSpeed){
        this.inputSpeed=inputSpeed;
    }

    public int getInputSpeed(){
        return this.inputSpeed;
    }

    public void setFrequency(int frequency){
        this.frequency=frequency;
    }

    public int getFrequency(){
        return this.frequency;
    }

}