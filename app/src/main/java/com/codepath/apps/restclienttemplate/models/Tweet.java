package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.codepath.apps.restclienttemplate.TimeFormatter;

public class Tweet {
    public long uid;
    public String body;
    public String date;
    public User user;


    public static Tweet fromJSON(JSONObject josnObject) throws JSONException {
        Tweet tweet = new Tweet();

        tweet.body = josnObject.getString("full_text");
        tweet.uid = josnObject.getLong("id");
        tweet.date = josnObject.getString("created_at");

        tweet.user = new User(josnObject.getJSONObject("user"));

        return tweet;
    }

    public String getRelativeDate() {

        return TimeFormatter.getTimeDifference(date);
    }
}
