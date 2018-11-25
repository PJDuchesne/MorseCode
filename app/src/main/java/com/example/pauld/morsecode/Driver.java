package com.example.pauld.morsecode;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Vibrator;
// TODO @George: Formalize these references
//https://progur.com/2016/12/how-to-create-8-bit-music-on-android.html
//https://www.soundjay.com/beep-sounds-1.html
//https://stackoverflow.com/questions/27420594/android-5-camera2-use-only-flash
public class Driver {

    private Vibrator vibrateHandler;
    private AudioTrack soundHandler;
    private CameraManager lightHandler;
    private byte soundData[];
    private SettingsSingleton desiredSettings;

    public Driver(Vibrator vibratorService, CameraManager systemService, Context applicationContext) {
        desiredSettings = SettingsSingleton.getInstance();
        vibrateHandler = vibratorService;
        generateSound(desiredSettings.getFrequency(), 1);
        soundHandler = new AudioTrack(AudioManager.STREAM_MUSIC,
                44100,
                AudioFormat.CHANNEL_OUT_DEFAULT,
                AudioFormat.ENCODING_PCM_8BIT, soundData.length,
                AudioTrack.MODE_STATIC
        );
        soundHandler.write(soundData, 0, soundData.length);
        if( applicationContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            lightHandler = systemService;
        }
        else{
            lightHandler = null;
        }
    }

    public void on(){
        if(SettingsSingleton.getInstance().getHapticEnabled()){
            vibrateHandler.vibrate(999999999 );
        }
        if(soundHandler.getPlayState() != AudioTrack.PLAYSTATE_PLAYING && SettingsSingleton.getInstance().getSoundEnabled()){
            soundHandler.play();
        }

        if( lightHandler != null && SettingsSingleton.getInstance().getLightEnabled()){
            try{
                String cameraId = lightHandler.getCameraIdList()[0];
                lightHandler.setTorchMode(cameraId, true);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void off(){
        vibrateHandler.cancel();
        if(soundHandler.getPlayState() == AudioTrack.PLAYSTATE_PLAYING){
           soundHandler.pause();
           soundHandler.flush();
           soundHandler.stop();
           generateSound(SettingsSingleton.getInstance().getFrequency(), 1);
           soundHandler.write(soundData, 0, soundData.length);
        }

        if( lightHandler != null){
            try{
                String cameraId = lightHandler.getCameraIdList()[0];
                lightHandler.setTorchMode(cameraId, false);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void generateSound(int frequency, int duration) {
        int sampleRate = 44100;
        soundData = new byte[sampleRate * duration];
        for (int i = 0; i < soundData.length; i++) {
            byte sample = (byte) ( Math.sin(2 * Math.PI * frequency * i / sampleRate) * 255);
            soundData[i] = sample;
        }
    }
}
