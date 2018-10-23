package com.example.pauld.morsecode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch inputType = findViewById(R.id.IType);
        SeekBar iSpeed = findViewById(R.id.seekBar);
        Button bLogout = findViewById(R.id.btnLogout);
        final SettingsSingleton s=SettingsSingleton.getInstance();

        inputType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.v("Switch State=",""+b);
                if(b==true){
                    s.setInputType(true);
                }
                else{
                    s.setInputType(false);
                }
                Log.v("inputType=",""+s.inputType);

            }
        });

        iSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.v("seek bar value=",""+i);
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

    }


}
