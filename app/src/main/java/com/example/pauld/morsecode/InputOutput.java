package com.example.pauld.morsecode;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InputOutput extends AppCompatActivity {
    private Driver d;
    private CameraCaptureSession mSession;
    private CaptureRequest.Builder mBuilder;
    private CameraDevice mCameraDevice;
    private CameraManager mCameraManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_output);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        d = new Driver( (Vibrator) this.getSystemService(VIBRATOR_SERVICE),(CameraManager) getSystemService(Context.CAMERA_SERVICE),getApplicationContext());

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

