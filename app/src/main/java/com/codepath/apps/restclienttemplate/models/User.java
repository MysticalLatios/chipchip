package com.codepath.apps.restclienttemplate.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Parcelable {
    public Long uid;
    public String screenName;
    public String profileImageURL;
    public String name;


    public User(JSONObject json_in) throws JSONException {
        uid = json_in.getLong("id");
        screenName = json_in.getString("screen_name");
        //Set the url to be https
        profileImageURL = json_in.getString("profile_image_url".replace("http", "https"));
        name = json_in.getString("name");
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public String getBiggerProfileImageURL() {
        String bigger = profileImageURL.replace("normal", "bigger");
        return bigger;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.uid);
        dest.writeString(this.screenName);
        dest.writeString(this.profileImageURL);
        dest.writeString(this.name);
    }

    protected User(Parcel in) {
        this.uid = (Long) in.readValue(Long.class.getClassLoader());
        this.screenName = in.readString();
        this.profileImageURL = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
