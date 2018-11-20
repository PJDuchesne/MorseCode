package com.example.pauld.morsecode;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class BrainTestingActivity extends AppCompatActivity {
    private MorseBrain brain;
    private View circleView, testView, resetView;
    private TextView currentMorseTextView, currentCharTextView, overallCharsTextView, testOutputView;
    private ProgressBar currentProgressBar;
    private Driver feedbackDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brain_testing);

        initialize();
    }

    private static String[][] testTable = {
            { ".....", "5 (E, I, S, H, 5)" },
            { "....-", "4 (E, I, S, H, 4)" },
            { "...--", "3 (E, I, S, V, 3)" },
            { "..-.",  "F (E, I, U, F)" },
            { "..---", "2 (E, I, U,  , 2)" },
            { ".-..",  "L (E, A, R, L)" },
            { ".--.",  "P (E, A, W, P)" },
            { ".----", "1 (E, A, W, J, 1)" },
            { "-....", "6 (T, N, D, B, 6)" },
            { "-..-",  "X (T, N, D, X)" },
            { "-.-.",  "C (T, N, K, C)" },
            { "-.--",  "Y (T, N, K, Y)" },
            { "--...", "7 (T, M, G, Z, 7)" },
            { "--.-",  "Q (T, M, G, Q)" },
            { "---..", "8 (T, M, O,  , 8)" },
            { "----.", "9 (T, M, O,  , 9)" },
            { "-----", "0 (T, M, O,  , 0)" }
    };

    private static int tmpIndex = 0;

    private void initialize() {
        testView                = findViewById(R.id.testView);
        resetView               = findViewById(R.id.resetView);
        circleView              = findViewById(R.id.inputCircle);
        currentProgressBar      = findViewById(R.id.progressBar);
        currentMorseTextView    = findViewById(R.id.currentMorseTextView);
        currentCharTextView     = findViewById(R.id.currentCharTextView);
        overallCharsTextView    = findViewById(R.id.overallCharsTextView);
        testOutputView          = findViewById(R.id.outputView);

        feedbackDriver = new Driver( (Vibrator) this.getSystemService(VIBRATOR_SERVICE),(CameraManager) getSystemService(Context.CAMERA_SERVICE),getApplicationContext());
        brain = new MorseBrain(this, currentMorseTextView, currentCharTextView, overallCharsTextView, currentProgressBar, feedbackDriver);

        circleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("[circleView]", "ACTION DOWN");
                        brain.StartInput();
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("[circleView]", "ACTION UP");
                        brain.EndInput();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        resetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brain.ElectricShock();
            }
        });

        testView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Currently for debugging
                brain.TestBrain(testTable[tmpIndex]);

                // Roll over index
                tmpIndex++;
                if (tmpIndex >= testTable.length) tmpIndex = 0;
            }
        });

        testOutputView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Attempt to output a hardcoded string for testing
                String outputTest = "Foobar Test123";

                brain.OutputMorse(outputTest);
            }
        });

    }


}
