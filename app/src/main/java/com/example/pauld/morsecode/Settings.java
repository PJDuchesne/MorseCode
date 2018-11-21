package com.example.pauld.morsecode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar();
        Switch haptic = findViewById(R.id.haptic);
        Switch sound = findViewById(R.id.sound);
        SeekBar iSpeed = findViewById(R.id.seekBar);
        Button bLogout = findViewById(R.id.btnLogout);

        final FirebaseAuth userAuth = FirebaseAuth.getInstance();
        if (userAuth.getCurrentUser() != null ){
            bLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userAuth.signOut();
                    Toast.makeText(getApplicationContext(),"You have been signed out!",Toast.LENGTH_SHORT).show();
                    v.setVisibility(View.INVISIBLE);
                }
            });
        }else{
            bLogout.setVisibility(View.INVISIBLE);
        }

        final SettingsSingleton s=SettingsSingleton.getInstance();


        haptic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.v("Switch State=",""+b);
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
                Log.v("Switch State=",""+b);
                if(b==true){
                    s.setSoundEnabled(true);
                }
                else{
                    s.setSoundEnabled(false);
                }
                Log.v("soundEnabled=",""+s.soundEnabled);

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

