package com.example.twitterprivacy.representation.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterprivacy.MyRecyclerViewAdapter;
import com.example.twitterprivacy.Preference;
import com.example.twitterprivacy.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DictionaryActivity extends AppCompatActivity {

    MyRecyclerViewAdapter adapter;
    ArrayList<String> wordsList = new ArrayList<>();
    private ExecutorService executorService;
    private Handler handler;
    private TextView newWordTv;
    private TextView emptyText;
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Button removeListBtn;
    String newWord;

    private void init() {
        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
        emptyText = findViewById(R.id.emptyText);
        recyclerView = findViewById(R.id.wordsRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        removeListBtn = findViewById(R.id.removeListBtn);
        setupSharedPreferences();
    }

    private void setUpBackBtn() {
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(DictionaryActivity.this, Preference.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        init();
        setUpBackBtn();
        // set up the RecyclerView
        if (getList().size() != 0) {
            Log.d("test", "getList().size() != 0");
            emptyText.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter.setData(getList());
            //adapter.notifyItemInserted(getList().size());
            adapter.notifyDataSetChanged();

        } else {
            recyclerView.setVisibility(View.INVISIBLE);
            emptyText.setVisibility(View.VISIBLE);
        }

        Button addWordBtn = findViewById(R.id.addWordBtn);
        newWordTv = findViewById(R.id.newwordTv);
        removeListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearShared();
            }
        });

        addWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newWordTv.getText().toString().isEmpty()) {
                    showToast("enter your word");
                } else {
                    addNewWord();
                }
            }
        });

    }

    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(
                "prefs",
                Context.MODE_PRIVATE
        );
        editor = sharedPreferences.edit();
    }

    private void saveList(ArrayList<String> wordsList) {
        //Set the values
        Set<String> set = new HashSet<String>();
        set.addAll(wordsList);
        editor.putStringSet("wordsList", set);
        editor.commit();
        Log.d("testList", wordsList.toString());
    }

    private ArrayList<String> getList() {
        Set<String> set = sharedPreferences.getStringSet("wordsList", null);
        if (set != null) {
            wordsList = new ArrayList<>();
            wordsList.addAll(set);
            Log.d("testListget", "hello" + wordsList.toString());
        } else {
            wordsList = new ArrayList<>();
        }
        return wordsList;
    }

    private void clearShared() {
        Set<String> set = sharedPreferences.getStringSet("wordsList", null);
        if (set != null) {
            showToast("clear all list");
            wordsList = new ArrayList<>();
            editor.putStringSet("wordsList", null);
            editor.commit();
        }
        emptyText.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);


    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(),
                msg,
                Toast.LENGTH_SHORT).show();


    }

    void addNewWord() {
        emptyText.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        newWord = newWordTv.getText().toString();
        wordsList.add(newWord);
        adapter.setData(wordsList);
        // adapter.notifyItemInserted(wordsList.size());
        adapter.notifyDataSetChanged();
        newWordTv.setText("");
        showToast(newWord);
        saveList(wordsList);
    }
}