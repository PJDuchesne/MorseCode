package com.example.pauld.morsecode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class training_screen_landing_page extends AppCompatActivity {

    private Button generateTrainingScreen, gotoBrainTestScreen;
    private EditText trainingScreenInputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_screen_landing_page);
        generateTrainingScreen = findViewById(R.id.training_screen_navigation);
        gotoBrainTestScreen = findViewById(R.id.brain_test_navigation);
        trainingScreenInputText = findViewById(R.id.insert_training_text);

        generateTrainingScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TrainingScreen.class);
                intent.putExtra("LESSON_STRING", trainingScreenInputText.getText().toString());
                startActivity(intent);
            }
        });

        gotoBrainTestScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BrainTestingActivity.class);
                startActivity(intent);
            }
        });
    }
}
