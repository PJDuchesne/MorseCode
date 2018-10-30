package com.example.pauld.morsecode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

public class TrainingScreen extends AppCompatActivity{

    private String lessonString, currentWordStr, currentLetterStr;
    private StringTokenizer lessonTokens;
    private TextView currentWord, currentLetter, currentLetterMorse, currentInputLetter;
    private View morseInputButton;
    private Button morseResetButton;
    private boolean newWord;
    private int wordCharIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_screen);

        // Link the TextViews/Buttons
        currentWord = findViewById(R.id.current_training_word);
        currentLetter = findViewById(R.id.current_training_letter);
        currentLetterMorse = findViewById(R.id.current_morse_input);
        currentInputLetter = findViewById(R.id.current_predicted_letter);
        morseInputButton = findViewById(R.id.training_screen_button);
        morseResetButton = findViewById(R.id.training_screen_reset_button);

        Intent intent = getIntent();

        // FOR TESTING: hardcoding the lessonString
        lessonString = "ABC B C D E F G";
        lessonString.toUpperCase();
        // Tokenize the lesson by spaces.
        lessonTokens = new StringTokenizer(lessonString, " ");
        gotoNextWord(); // Get first word

        morseInputButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:

                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });

        morseResetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });

    }

    // Loads in the next word in display and updates the first letter input.
    private void gotoNextWord(){
        // Check if there are any tokens left.
        if(!lessonTokens.hasMoreTokens()){
            lessonCompleted();
            return;
        }
        currentWordStr = lessonTokens.nextToken();
        currentWord.setText(currentWordStr);
        newWord = true;
        gotoNextLetter();
    }

    private void gotoNextLetter(){
        if(newWord){
            wordCharIndex = 0;
            newWord = false;
        }
        if(wordCharIndex == currentWordStr.length() - 1){
            wordCompleted();
            return;
        }
        currentLetterStr = currentWordStr.substring(wordCharIndex, wordCharIndex + 1);
        wordCharIndex++;
        currentLetter.setText(currentLetterStr);
        currentLetterMorse.setText(getMorseString(currentLetterStr.charAt(0)));
        resetButtonInput();
    }

    private void wordCompleted(){

    }

    private void resetButtonInput(){
        // TODO Reset the brain.
    }

    private void lessonCompleted(){

    }

    private String getMorseString(char character){
        // TODO ACTUALLY IMPLEMENT A LOOKUP TABLE
        return "-.-.-";
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onStart(){
        super.onStart();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

}
