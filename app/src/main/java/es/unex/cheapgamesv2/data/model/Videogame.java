package es.unex.cheapgamesv2.data.model;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "videogame", indices = {@Index(value = {"gameID","steamAppID"}, unique = true)})
public class Videogame implements Parcelable
{

    @PrimaryKey
    @NonNull
    @SerializedName("gameID")
    @Expose
    private String gameID;
    @SerializedName("steamAppID")
    @Expose
    private String steamAppID;
    @SerializedName("cheapest")
    @Expose
    private String cheapest;
    @SerializedName("cheapestDealID")
    @Expose
    private String cheapestDealID;
    @SerializedName("external")
    @Expose
    private String external;
    @SerializedName("thumb")
    @Expose
    private String thumb;

    public final static Creator<Videogame> CREATOR = new Creator<Videogame>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Videogame createFromParcel(android.os.Parcel in) {
            return new Videogame(in);
        }

        public Videogame[] newArray(int size) {
            return (new Videogame[size]);
        }

    }
            ;

    public Videogame(android.os.Parcel in) {
        this.gameID = ((String) in.readValue((String.class.getClassLoader())));
        this.steamAppID = ((String) in.readValue((String.class.getClassLoader())));
        this.cheapest = ((String) in.readValue((String.class.getClassLoader())));
        this.cheapestDealID = ((String) in.readValue((String.class.getClassLoader())));
        this.external = ((String) in.readValue((String.class.getClassLoader())));
        this.thumb = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Videogame() {
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getSteamAppID() {
        return steamAppID;
    }

    public void setSteamAppID(String steamAppID) {
        this.steamAppID = steamAppID;
    }

    public String getCheapest() {
        return cheapest;
    }

    public void setCheapest(String cheapest) {
        this.cheapest = cheapest;
    }

    public String getCheapestDealID() {
        return cheapestDealID;
    }

    public void setCheapestDealID(String cheapestDealID) {
        this.cheapestDealID = cheapestDealID;
    }

    public String getExternal() {
        return external;
    }

    public void setExternal(String external) {
        this.external = external;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(gameID);
        dest.writeValue(steamAppID);
        dest.writeValue(cheapest);
        dest.writeValue(cheapestDealID);
        dest.writeValue(external);
        dest.writeValue(thumb);
    }

    public int describeContents() {
        return  0;
    }


}
