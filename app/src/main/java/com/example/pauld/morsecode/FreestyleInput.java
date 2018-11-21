package com.example.pauld.morsecode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ProgressBar;

public class FreestyleInput extends AppCompatActivity {

    private TextView totalInput, predictedInput, predictedMorse;
    private Button resetButton, addLetterButton, resetCurrentInput;
    private ProgressBar progressbar;
    private View inputButton;
    private double screenHeight, screenWidth;
    private final double screenReferenceHeight = 2076;
    private final double screenReferenceWidth = 1080;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freestyleinput);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        totalInput = findViewById(R.id.freestyle_current_total_input);
        predictedInput = findViewById(R.id.freestyle_current_predicted_letter);
        predictedMorse = findViewById(R.id.freestyle_current_morse_input);

        resetButton = findViewById(R.id.freestyle_screen_reset_button);
        inputButton = findViewById(R.id.freestyle_screen_button);
        addLetterButton = findViewById(R.id.freestyle_add_letter_btn);
        resetCurrentInput = findViewById(R.id.freestyle_reset_input_btn);

        formatSizes();
    }

    private void formatSizes(){
        double heightScalar = screenHeight / screenReferenceHeight;
        double widthScalar = screenWidth / screenReferenceWidth;

        // adjust the sizes of buttons

        double tempHeight = ((double)inputButton.getLayoutParams().height) * heightScalar;
        double tempWidth = ((double)inputButton.getLayoutParams().width) * widthScalar;
        if(tempHeight > tempWidth)
            tempHeight = tempWidth;
        else
            tempWidth = tempHeight;

        inputButton.getLayoutParams().height = (int)tempHeight;
        inputButton.getLayoutParams().width = (int)tempWidth;

        tempHeight = ((double)resetButton.getLayoutParams().height) * heightScalar;
        tempWidth = ((double)resetButton.getLayoutParams().width) * widthScalar;

        if(tempHeight > tempWidth)
            tempHeight = tempWidth;
        else
            tempWidth = tempHeight;

        resetButton.getLayoutParams().height = (int)tempHeight;
        resetButton.getLayoutParams().width = (int)tempWidth;
    }
}