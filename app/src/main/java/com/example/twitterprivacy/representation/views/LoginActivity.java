package com.example.twitterprivacy.representation.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.twitterprivacy.PrivacyPlugin;
import com.example.twitterprivacy.R;
import com.example.twitterprivacy.Settings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class LoginActivity extends AppCompatActivity {

    TwitterLoginButton loginButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(
                "prefs",
                Context.MODE_PRIVATE
        );
        editor = sharedPreferences.edit();
    }
    private void checkLoginEntered(){
        if(sharedPreferences.getBoolean("loginIsEntered", false)){
            startActivity(new Intent(LoginActivity.this,PrivacyPlugin.class));
            finish();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_login_activiry);

        setupSharedPreferences();
        checkLoginEntered();


        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
                Toast.makeText(getApplicationContext(), result.data.getUserName(), Toast.LENGTH_LONG).show();

                loginMethod(session);
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loginMethod(TwitterSession twitterSession) {
        String userName = twitterSession.getUserName();
        String token = twitterSession.getAuthToken().token;
        editor.putString("userName", userName);
        editor.putString("token", token);
        editor.commit();

        Toast.makeText(getApplicationContext(), userName, Toast.LENGTH_LONG).show();
        goHome(userName, token);
        System.out.println("token==================="+token);
        System.out.println("token==================="+twitterSession.getUserId());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }


    void storeDefaultSettings(String token, Settings settings) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(token).add(settings).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

                System.out.println("data has been added");

            }
        });

    }


    void goHome(String username, String token) {
        editor.putBoolean("loginIsEntered",true);
        editor.commit();
        Intent intent = new Intent(LoginActivity.this, PrivacyPlugin.class);
        intent.putExtra("username", username);
        intent.putExtra("token", token);
        startActivity(intent);
        finish();

    }

    void postTweet() {


    }
}
