package com.example.pauld.morsecode;

import android.os.Vibrator;

public class Driver {

    private Vibrator vibrateHandler;


    public Driver(Vibrator systemService){
        vibrateHandler = systemService;
    }

    public void on(){
        vibrateHandler.vibrate(999999999 );
    }

    public void off(){
        vibrateHandler.cancel();
    }
}
