package com.codepath.apps.restclienttemplate.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import com.codepath.apps.restclienttemplate.TimeFormatter;

public class Tweet implements Parcelable {
    public long uid;
    public String body;
    public String date;
    public User user;


    public static Tweet fromJSON(JSONObject josnObject) throws JSONException {
        Tweet tweet = new Tweet();

        try {
            tweet.body = josnObject.getString("full_text");
        }
        catch (JSONException e){
            tweet.body = josnObject.getString("text");
        }

        tweet.uid = josnObject.getLong("id");
        tweet.date = josnObject.getString("created_at");

        tweet.user = new User(josnObject.getJSONObject("user"));

        return tweet;
    }

    public String getRelativeDate() {

        return TimeFormatter.getTimeDifference(date);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.uid);
        dest.writeString(this.body);
        dest.writeString(this.date);
        dest.writeParcelable(this.user, flags);
    }

    public Tweet() {
    }

    protected Tweet(Parcel in) {
        this.uid = in.readLong();
        this.body = in.readString();
        this.date = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Parcelable.Creator<Tweet> CREATOR = new Parcelable.Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel source) {
            return new Tweet(source);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };
}
