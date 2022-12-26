package es.unex.cheapgamesv2.data.model;


import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info implements Parcelable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("steamAppID")
    @Expose
    private String steamAppID;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    public final static Creator<Info> CREATOR = new Creator<Info>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Info createFromParcel(android.os.Parcel in) {
            return new Info(in);
        }

        public Info[] newArray(int size) {
            return (new Info[size]);
        }

    };

    protected Info(android.os.Parcel in) {
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.steamAppID = ((String) in.readValue((String.class.getClassLoader())));
        this.thumb = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Info() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSteamAppID() {
        return steamAppID;
    }

    public void setSteamAppID(String steamAppID) {
        this.steamAppID = steamAppID;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(steamAppID);
        dest.writeValue(thumb);
    }

    public int describeContents() {
        return 0;
    }

}
