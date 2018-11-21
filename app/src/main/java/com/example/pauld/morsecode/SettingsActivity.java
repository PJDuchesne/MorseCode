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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch haptic = findViewById(R.id.haptic);
        Switch sound = findViewById(R.id.sound);
        Switch light = findViewById(R.id.light);
        SeekBar iSpeed = findViewById(R.id.seekBar);
        final SeekBar frequency = findViewById(R.id.seekBar2);
        Button bLogout = findViewById(R.id.btnLogout);
        final SettingsSingleton s=SettingsSingleton.getInstance();

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