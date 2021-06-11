package com.example.bgvoc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    VerbsSounds VerbsSounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide();
        initiateVerbsSound();
    }

    private void initiateVerbsSound() {
        try {
            VerbsSounds = new VerbsSounds(getResources().openRawResource(R.raw.wordtosound));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTrainingAccordingToSelection(View view) {
        String selectedButton = findViewById(view.getId()).getTransitionName();
        Intent intent = new Intent(MainActivity.this, TrainingScreen.class);
        Bundle bundle = new Bundle();
        bundle.putString("selectedTraining", selectedButton);
        intent.putExtras(bundle);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.practice_option_menu, menu);
        return true;
    }*/


}