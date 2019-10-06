package com.salam.hedghoglabtest.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PosterParceable implements Parcelable {
    public PosterParceable(String URL, String id) {
        this.URL = URL;
        this.id = id;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String URL;
    private String id;

    public PosterParceable(Parcel in) {

        id = in.readString();
        URL = in.readString();
    }

    public static final Creator<PosterParceable> CREATOR = new Creator<PosterParceable>() {
        @Override
        public PosterParceable createFromParcel(Parcel in) {
            return new PosterParceable(in);
        }

        @Override
        public PosterParceable[] newArray(int size) {
            return new PosterParceable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(URL);
    }
}
