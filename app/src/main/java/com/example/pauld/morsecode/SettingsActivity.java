package com.example.pauld.morsecode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private SettingsSingleton s;
    private SeekBar iSpeed,frequency;
    private Switch light,haptic,sound;

    @Override
    protected void onResume(){
        super.onResume();
        iSpeed = findViewById(R.id.seekBar);
        frequency = findViewById(R.id.seekBar2);
        haptic = findViewById(R.id.haptic);
        sound = findViewById(R.id.sound);
        light = findViewById(R.id.light);

        s = SettingsSingleton.getInstance();

        frequency.setProgress(s.getFrequency()/40 - 10);
        iSpeed.setProgress(s.getInputSpeed());
        haptic.setChecked(s.getHapticEnabled());
        sound.setChecked(s.getSoundEnabled());
        light.setChecked(s.getLightEnabled());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        haptic = findViewById(R.id.haptic);
        sound = findViewById(R.id.sound);
        light = findViewById(R.id.light);
        iSpeed = findViewById(R.id.seekBar);
        frequency = findViewById(R.id.seekBar2);
        s = SettingsSingleton.getInstance();


        haptic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                Log.v("Switch State=",""+b);
                if(b==true){
                    s.setHapticEnabled(true);
                }
                else{
                    s.setHapticEnabled(false);
                }
                Log.v("hapticEnabled=",""+s.hapticEnabled);

            }
        });

        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                Log.v("Switch State=",""+b);
                if(b==true){
                    s.setSoundEnabled(true);
                }
                else{
                    s.setSoundEnabled(false);
                }
                Log.v("soundEnabled=",""+s.soundEnabled);

            }
        });

        light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                Log.v("Switch State=",""+b);
                if(b==true){
                    s.setLightEnabled(true);
                }
                else{
                    s.setLightEnabled(false);
                }

                Log.v("lightEnabled=",""+s.lightEnabled);

            }
        });

        iSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                Log.v("seek bar value=",""+i);
                s.setInputSpeed(i);
                Log.v("input speed=",""+s.inputSpeed);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        frequency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.v("seek bar value=",""+i);
                if (i > 0 && i <= 12) {
                    s.setFrequency(440+((i-1)*40));
                }
                else
                {
                    s.setFrequency(0);
                }

                Log.v("frequency=",""+s.frequency);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}