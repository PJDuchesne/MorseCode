package com.example.pauld.morsecode;

import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Vibrator;
//https://www.soundjay.com/beep-sounds-1.html
public class Driver {

    private Vibrator vibrateHandler;
    private Ringtone soundHandler;


    public Driver(Vibrator systemService, Context applicationContext, String packageName){
        vibrateHandler = systemService;
        Uri sound = Uri.parse("android.resource://" + packageName + "/" + R.raw.beep);
        soundHandler = RingtoneManager.getRingtone(applicationContext, sound);
    }

    public void on(){
        vibrateHandler.vibrate(999999999 );
        try {
            soundHandler.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void off(){
        vibrateHandler.cancel();
        soundHandler.stop();
    }
}
