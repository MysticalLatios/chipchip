package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    public Long uid;
    public String screenName;
    public String profileImageURL;
    public String name;


    public User(JSONObject json_in) throws JSONException {
        uid = json_in.getLong("id");
        screenName = json_in.getString("screen_name");
        profileImageURL = json_in.getString("profile_image_url");
        name = json_in.getString("name");
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public String getBiggerProfileImageURL() {
        String bigger = profileImageURL.replace("normal", "bigger");
        return bigger;
    }
}
