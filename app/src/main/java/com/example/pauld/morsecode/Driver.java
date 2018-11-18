package com.example.pauld.morsecode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Size;
import android.view.Surface;

import java.util.ArrayList;
import java.util.List;

//https://progur.com/2016/12/how-to-create-8-bit-music-on-android.html
//https://www.soundjay.com/beep-sounds-1.html
//https://stackoverflow.com/questions/27420594/android-5-camera2-use-only-flash
public class Driver {

    private Vibrator vibrateHandler;
    //private MediaPlayer soundHandler;
    private AudioTrack soundHandler;
    private CameraManager lightHandler;
    private byte soundData[];


    public Driver(Vibrator vibratorService, CameraManager cameraService) {
        vibrateHandler = vibratorService;
        generateSound(540, 1000);
        soundHandler = new AudioTrack(AudioManager.STREAM_MUSIC,
                44100,
                AudioFormat.CHANNEL_OUT_DEFAULT,
                AudioFormat.ENCODING_PCM_8BIT, soundData.length,
                AudioTrack.MODE_STATIC
        );
        soundHandler.write(soundData, 0, soundData.length);
        lightHandler = cameraService;
        //Uri sound = Uri.parse("android.resource://" + packageName + "/" + R.raw.beep);
        //soundHandler = MediaPlayer.create(applicationContext,sound);
        //soundHandler.setLooping(false);
    }

    public void on(){
        vibrateHandler.vibrate(999999999 );

        try{
            String cameraId = lightHandler.getCameraIdList()[0]; // Usually front camera is at 0 position.
            lightHandler.setTorchMode(cameraId, true);
        } catch (Exception e){
            e.printStackTrace();
        }
        /*
        try {
            soundHandler.seekTo(0);
            soundHandler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        if(soundHandler.getPlayState() != AudioTrack.PLAYSTATE_PLAYING){
            soundHandler.play();
        }
    }

    public void off(){
        vibrateHandler.cancel();
        /*
        if(soundHandler.isPlaying()){
            soundHandler.pause();
        }
       */
        if(soundHandler.getPlayState() == AudioTrack.PLAYSTATE_PLAYING){
           soundHandler.pause();
           soundHandler.flush();
           soundHandler.stop();
           soundHandler.write(soundData, 0, soundData.length);
        }

        try{
            String cameraId = lightHandler.getCameraIdList()[0]; // Usually front camera is at 0 position.
            lightHandler.setTorchMode(cameraId, false);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void generateSound(int frequency, int duration) {
        int sampleRate = 44100;
        soundData = new byte[sampleRate * duration];
        for (int i = 0; i < soundData.length; i++) {
            byte sample = (byte) (
                    Math.sin(2 * Math.PI * frequency * i / sampleRate) *
                            255);
            soundData[i] = sample;
        }
    }


}


/*
                for(int i = 0;i<5;i++){
                    try {
                        d.on();
                        Log.d("SOUND","ON"+i);
                        Thread.sleep(100);
                        d.off();
                        Log.d("SOUND","OFF"+i);
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
*/





