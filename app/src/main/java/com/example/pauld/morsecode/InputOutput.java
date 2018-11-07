package com.example.pauld.morsecode;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class InputOutput extends AppCompatActivity {
    private Driver d;
    ToneGenerator toneGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_output);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        d = new Driver( (Vibrator) this.getSystemService(VIBRATOR_SERVICE),getApplicationContext(),getPackageName() );

        findViewById(R.id.ioBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.on();
            }
        });

        findViewById(R.id.ioBtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.off();
            }
        });
    }
}

