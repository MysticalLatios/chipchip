package com.codepath.apps.restclienttemplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.github.scribejava.apis.TwitterApi;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private RestClient client;
    private RecyclerView rvTweets;
    private TweetsAdapter adapter;
    private List<Tweet> tweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = RestApplication.getRestClient(this);

        //Get our recycler view layout
        rvTweets = findViewById(R.id.rvTweets);
        //Init the list of tweets
        tweets = new ArrayList<>();
        //Init our adapter
        adapter = new TweetsAdapter(this,tweets);

        //Setup the layout and set the adapter for the layout
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvTweets.setAdapter(adapter);


        populateHomeTimeLine();


    }

    private void populateHomeTimeLine() {
        client.getHomeTimeLine(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //Log.d("TwitterClient", response.toString());
                //Iterate through the tweets
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jason = response.getJSONObject(i);
                        //Convert each json object into a tweet object
                        Tweet tweet = Tweet.fromJSON(jason);
                        //add the tweet to our list of tweets
                        tweets.add(tweet);
                        //Notify our adapter that it actually has new data that it could display
                        adapter.notifyItemInserted(tweets.size() - 1);

                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("TwitterClient", responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("TwitterClient", errorResponse.toString());
            }
        });
    }
}
