package com.example.twitterprivacy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.twitterprivacy.representation.views.DictionaryActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Preference extends AppCompatActivity {
    Button gotoDic, updatePreferences;
    Switch words, tag, mentions, location, sentiment;
    private ImageView backImageView;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ExecutorService executorService;
    private Handler handler;


    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(
                "prefs",
                Context.MODE_PRIVATE
        );
        editor = sharedPreferences.edit();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference);
        setupSharedPreferences();

        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
        gotoDic = findViewById(R.id.gotoDic);
        updatePreferences = findViewById(R.id.update);
        words = findViewById(R.id.wordsSwitch);
        tag = findViewById(R.id.tagSwitch);
        mentions = findViewById(R.id.mentionsSwitch);
        sentiment = findViewById(R.id.sentimentSwitch);
        location = findViewById(R.id.locationSwitch);
        backImageView = findViewById(R.id.backImageView);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(Preference.this,PrivacyPlugin.class));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        tag.setChecked(sharedPreferences.getBoolean("tag",false));
        mentions.setChecked(sharedPreferences.getBoolean("mention",false));
        sentiment.setChecked(sharedPreferences.getBoolean("sentiment",false));
        location.setChecked(sharedPreferences.getBoolean("location",false));
        words.setChecked(sharedPreferences.getBoolean("words",false));
        updatePreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("mention", mentions.isChecked());
                editor.putBoolean("tag", tag.isChecked());
                editor.putBoolean("sentiment", sentiment.isChecked());
                editor.putBoolean("location", location.isChecked());
                editor.putBoolean("words", words.isChecked());
                editor.commit();
                showToast("Done!");
                Log.d("testm", sharedPreferences.getBoolean("mention",false) + "");
                Log.d("testg", sharedPreferences.getBoolean("tag",false) + "");
                Log.d("tests", sharedPreferences.getBoolean("sentiment",false) + "");
                Log.d("testl", sharedPreferences.getBoolean("location",false) + "");

            }
        });

        gotoDic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Preference.this, DictionaryActivity.class);
                startActivity(intent);
            }
        });


    }
    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(),
                msg,
                Toast.LENGTH_SHORT).show();


    }





}
