package com.example.twitterprivacy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.twitterprivacy.data.remote.ApiHelperImpl;
import com.example.twitterprivacy.data.remote.RetrofitClient;
import com.example.twitterprivacy.models.AnalyzeTextInput;
import com.example.twitterprivacy.models.LocationResponse;
import com.example.twitterprivacy.models.Pojo;
import com.example.twitterprivacy.models.RemoveDictionaryWordsInput;
import com.example.twitterprivacy.representation.views.LoginActivity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PrivacyPlugin extends AppCompatActivity {
    private ExecutorService executorService;
    private Handler handler;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<String> wordsList = new ArrayList<>();
    private ProgressBar progressBar;
    private TextView analyzeTxt, hastagTxt, mentionTxt, sentimentTxt, removeTxt, locationTxt;
    TextView name;
    EditText tweet;
    String user, token;
    ImageView logoout;
    //    Boolean isShared = false;
    Boolean hasMention = true;

    Button post;
    String barrierToken = "AAAAAAAAAAAAAAAAAAAAAGzWdAEAAAAA2js79sJhx526dFAOU3I1yIzBHto%3DKme8faENIEG5tf2qThc81ppkRel7jQQR2K1AQaJJmrNazFYJth";

    private void init() {
        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
        progressBar = findViewById(R.id.progressBar);
        analyzeTxt = findViewById(R.id.analyzeTxt);
        hastagTxt = findViewById(R.id.hastagTxt);
        mentionTxt = findViewById(R.id.mentionTxt);
        sentimentTxt = findViewById(R.id.sentimentTxt);
        removeTxt = findViewById(R.id.removeTxt);
        locationTxt = findViewById(R.id.locationTxt);
        setupSharedPreferences();
    }


    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(),
                msg,
                Toast.LENGTH_SHORT).show();
    }

    private void findLocation(String inputText) {
        Log.d("test", "iam here findLocation");

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    LocationResponse response = new ApiHelperImpl(RetrofitClient.getApiService()).
                            findLocation(new Pojo(inputText)).execute().body();
                    Log.d("test location", response.toString());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(response.getCities().size() == 0 && response.getCountries().size() == 0){
                                locationTxt.setText("There are no locations!");
                            }
                            if(response.getCities().size() == 0 && response.getCountries().size() != 0){
                                locationTxt.setText("There are countries " + response.getCountries() + "!");
                            }
                            if(response.getCities().size() != 0 && response.getCountries().size() == 0){
                                locationTxt.setText("There are cities " + response.getCities() + "!");
                            }
                            if(response.getCities().size() != 0 && response.getCountries().size() != 0){
                                locationTxt.setText("There are countries  " + response.getCountries() + "!"
                                        + "and There are cities " + response.getCities() + "!");
                            }


                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("test", e.toString() + " ");
                }
            }
        });
    }

    private void analyzeText(String inputText, String userName) {
        Log.d("test", "iam here");

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String response = new ApiHelperImpl(RetrofitClient.getApiService()).
                            analyzeText(new AnalyzeTextInput(inputText, userName)).execute().body();
                    Log.d("testana", response);
                    // isShared = response;
                    // Log.d("test", isShared + " ");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
//                            if (isShared) {
//                                analyzeTxt.setText("It is fine!");
//                            } else {
//                                analyzeTxt.setText("It is not fine!");
//                            }
                            analyzeTxt.setText(response + "!");
                            if (!sharedPreferences.getBoolean("tag", false) &&
                                    !sharedPreferences.getBoolean("mention", false) &&
                                    !sharedPreferences.getBoolean("sentiment", false)) {
                                progressBar.setVisibility(View.INVISIBLE);
//                                if (isShared) {
//                                    sendTweet();
//                                } else {
//                                    showToast("input text can not share on twitter");
//                                }
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("test", e.toString() + " ");
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            showToast(e.toString());
//                            progressBar.setVisibility(View.INVISIBLE);
//                        }
//                    });

                }


            }
        });
    }


    private void findmention(String inputText) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<ArrayList<String>> response = new ApiHelperImpl(RetrofitClient.getApiService()).
                            findMention(new Pojo(inputText)).execute().body();

                    Log.d("test", "find" + response);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            if (response != null && response.isEmpty()) {
                                hasMention = false;
                                mentionTxt.setText("There are no mentions!");
                            } else {
                                hasMention = true;
                                mentionTxt.setText("There are mentions!");
                                // showToast("There are  mentions!");
                            }
                            if (!sharedPreferences.getBoolean("tag", false) &&
                                    !sharedPreferences.getBoolean("sentiment", false)) {
                                progressBar.setVisibility(View.INVISIBLE);
                                if (!hasMention) {
                                    sendTweet();
                                } else {
                                    showToast("input text can not share on twitter");
                                }
                            }


                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("test", e.toString() + " ");
                }


            }
        });
    }

    private void findhastag(String inputText) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<Object> response = new ApiHelperImpl(RetrofitClient.getApiService()).
                            findHastag(new Pojo(inputText)).execute().body();

                    Log.d("test", "find tag" + response.toString());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            progressBar.setVisibility(View.INVISIBLE);
                            if (!response.isEmpty()) {
                                hastagTxt.setText("There are hashtags!");
                            } else {
                                hastagTxt.setText("There are no hashtags!");
                            }
                            //Log.d("test", "isShared" +isShared + "hasMention" + hasMention);
                            if (!hasMention) {
                                sendTweet();
                            } else {
                                showToast("input text  can not  share on twitter");
                            }

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("test", e.toString() + " ");
                }


            }
        });
    }

    private void findSentiment(String inputText) {
        progressBar.setVisibility(View.VISIBLE);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String response = new ApiHelperImpl(RetrofitClient.getApiService()).
                            findSentiment(new Pojo(inputText)).execute().body();

                    Log.d("test", "find" + response);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!response.isEmpty()) {
                                sentimentTxt.setText("The sentiment is " + response + "!");
                            } else {
                                sentimentTxt.setText("There is no  sentiment!");
                            }

                            if (!sharedPreferences.getBoolean("tag", false)) {
                                progressBar.setVisibility(View.INVISIBLE);
                                if (!sharedPreferences.getBoolean("mention", false)) {
                                    if (response.equals("Positive")) {
                                        sendTweet();
                                    } else {
                                        showToast("input text can not  share on twitter");
                                    }
                                } else {
                                    if (!hasMention && response.equals("Positive")) {
                                        sendTweet();
                                    } else {
                                        showToast("input text can not  share on twitter");
                                    }
                                }
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("test", e.toString() + " ");
                }


            }
        });
    }

    private void removeDictionaryWords(String inputText, ArrayList<String> dictionary) {
        Log.d("testListget", "dictionary" + dictionary.toString());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<Object> response = new ApiHelperImpl(RetrofitClient.getApiService()).
                            removeDictionaryWords(new RemoveDictionaryWordsInput(inputText, dictionary)).execute().body();
                    Log.d("test", response.toString() + "remove");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (response.size() == 0) {
                                removeTxt.setText("There are no words in exclusion word dictionary!");
                            } else {
                                removeTxt.setText("There are words "+ response +" in exclusion word dictionary!");
                            }

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("test", e.toString() + " ");
                }


            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_plugin);
        init();

        getTweets();

        user = sharedPreferences.getString("userName", "");
        token = sharedPreferences.getString("token", "");
        name = (TextView) findViewById(R.id.nametextView);
        logoout = findViewById(R.id.logout);
        tweet = findViewById(R.id.tweetEt);
        post = findViewById(R.id.button);
        name.setText("Hello " + user);


        logoout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logout();

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Tweetm = tweet.getText().toString();
                if (!Tweetm.isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                    analyzeTxt.setText("");
                    hastagTxt.setText("");
                    mentionTxt.setText("");
                    sentimentTxt.setText("");
                    removeTxt.setText("");
                    locationTxt.setText("");
                    analyzeText(Tweetm, user);
                    //if (isShared.equals("0.0")) {
                    if (sharedPreferences.getBoolean("words", false)) {

                        removeDictionaryWords(Tweetm, getList());
                    }
                    if (sharedPreferences.getBoolean("location", false)) {
                        Log.d("test", "location here");
                        findLocation(Tweetm);
                    }

                    Log.d("testm", sharedPreferences.getBoolean("mention", false) + "");
                    Log.d("testg", sharedPreferences.getBoolean("tag", false) + "");
                    if (sharedPreferences.getBoolean("mention", false)) {
                        Log.d("test", "men here");
                        findmention(Tweetm);
                    }
                    if (sharedPreferences.getBoolean("sentiment", false)) {
                        findSentiment(Tweetm);
                    }
                    if (sharedPreferences.getBoolean("tag", false)) {
                        Log.d("test", "tag here");
                        findhastag(Tweetm);
                    }
                } else {
                    showToast("enter text");
                }
                // }


                //  shareTwitter(Tweetm);

                // sendTweet();
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


    private ArrayList<String> getList() {
        Set<String> set = sharedPreferences.getStringSet("wordsList", null);
        if (set != null) {
            wordsList = new ArrayList<>();
            wordsList.addAll(set);
        }
        return wordsList;
    }

    //A second Activity allows the user to select one of at least 5 colours
    public void onPreferenceClick(View v) {
        Intent intent = new Intent(PrivacyPlugin.this, Preference.class);
        Bundle bundle = new Bundle();
        //bundle.putInt("colour", defaultColour);

        intent.putExtras(bundle);

        startActivityForResult(intent, 1);
    }

    //A second Activity allows the user to select one of at least 5 colours
    public void onSubmitClick(View v) {
    }


    public void logout() {
        editor.putBoolean("loginIsEntered", false);
        editor.commit();
        Intent intent = new Intent(PrivacyPlugin.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    void getTweets() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.twitter.com/2/users/1418959211292241941/tweets")

                .addHeader("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAGzWdAEAAAAA2js79sJhx526dFAOU3I1yIzBHto%3DKme8faENIEG5tf2qThc81ppkRel7jQQR2K1AQaJJmrNazFYJth")
                .addHeader("Cookie", "guest_id=v1%3A165680679062409344; guest_id_ads=v1%3A165680679062409344; guest_id_marketing=v1%3A165680679062409344; personalization_id=\"v1_LTnYHXZHxlYgsud7YUSGIw==\"")
                .build();
        Runnable runnable = () -> {
            try {
                Response response = client.newCall(request).execute();
                System.out.println("===============" + response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {

            });
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }

    private void shareTwitter(String message) {
        Intent tweetIntent = new Intent(Intent.ACTION_SEND);
        tweetIntent.putExtra(Intent.EXTRA_TEXT, message);
        tweetIntent.setType("text/plain");

        PackageManager packManager = getPackageManager();
        List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);

        boolean resolved = false;
        for (ResolveInfo resolveInfo : resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                tweetIntent.setClassName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name);
                resolved = true;
                break;
            }
        }
        if (resolved) {
            startActivity(tweetIntent);
        } else {
            Intent i = new Intent();
            i.putExtra(Intent.EXTRA_TEXT, message);
            i.setAction(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(message)));
            startActivity(i);
            Toast.makeText(this, "Twitter app isn't found", Toast.LENGTH_LONG).show();
        }
    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.wtf("TAG", "UTF-8 should always be supported", e);
            return "";
        }
    }

    public void sendTweet() {
        showToast("input text can share on twitter");
//        String msg = tweet.getText().toString();
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_SEND);
//        intent.putExtra(Intent.EXTRA_TEXT, msg);
//        intent.setType("text/plain");
//
//        intent.setPackage("com.twitter.android");
//        startActivity(intent);
    }


}
