package com.example.pauld.morsecode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_login, btn_lessons, btn_txt_to_morse, btn_morse_to_txt, btn_settings;

        btn_login        = findViewById(R.id.btn_main_login);
        btn_lessons      = findViewById(R.id.btn_main_lessons);
        btn_txt_to_morse = findViewById(R.id.btn_main_text_to_morse);
        btn_morse_to_txt = findViewById(R.id.btn_main_morse_to_text);
        btn_settings     = findViewById(R.id.btn_main_settings);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_lessons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Lessons.class);
                startActivity(intent);
            }
        });

        btn_txt_to_morse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Lessons.class); // TODO: Update with real page
                startActivity(intent);
            }
        });

        btn_morse_to_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), training_screen_landing_page.class); // TODO: Update with real page
                startActivity(intent);
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}