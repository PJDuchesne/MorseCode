package com.example.pauld.morsecode;

import java.io.Serializable;

public class SettingsSingleton {
    private static volatile SettingsSingleton sInstance;
    public boolean inputType=true;
    public int inputSpeed=3;

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

    public void setInputType(boolean inputType){
        this.inputType=inputType;
    }

    public boolean getInputType(){
        return this.inputType;
    }

    public void setInputSpeed(int inputSpeed){
        this.inputSpeed=inputSpeed;
    }

    public int getInputSpeed(){
        return this.inputSpeed;
    }

}

