package com.codepath.apps.restclienttemplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    private EditText etCompose;
    private Button btnTweet;
    private TextView tvTweetLength;
    private Thread threadTweetLengthUpdate;

    private RestClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = RestApplication.getRestClient(this);
        etCompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);
        tvTweetLength = findViewById(R.id.tvTweetLength);


        //Set click listener to button to call api for tweeting the tweet
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent = etCompose.getText().toString();
                if (tweetContent.isEmpty()) {
                    Toast.makeText(ComposeActivity.this, "Empty tweet! Add some more text", Toast.LENGTH_LONG).show();
                }
                else if(tweetContent.length() > 140){
                    Toast.makeText(ComposeActivity.this, "Your tweet is " + (tweetContent.length() - 140) + " characters to long", Toast.LENGTH_LONG).show();
                }
                //tweet must be good!
                else {
                    Toast.makeText(ComposeActivity.this, tweetContent, Toast.LENGTH_LONG).show();
                    client.composeTweet(tweetContent, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d("RestClient", "Good tweet post: " + response.toString() );
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.e("RestClient", "tweet post failed D=");
                        }
                    });
                }
            }
        });



        //Start a new thread for constantly updating our tweet length text view
        threadTweetLengthUpdate = new Thread() {

            @Override
            public void run() {
                try {
                    while (!threadTweetLengthUpdate.isInterrupted()) {
                        //Ohh wow this is such a hack, I should have some sort of listener on the etCompose, so I only update it when it gets updated, but I have no idea how to do that, so instead we have this mess of the use of Thread()
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvTweetLength.setText(etCompose.getText().toString().length() + "/140");
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        threadTweetLengthUpdate.start();
    }


}
