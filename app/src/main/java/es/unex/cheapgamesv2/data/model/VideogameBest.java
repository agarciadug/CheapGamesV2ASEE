package es.unex.cheapgamesv2.data.model;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "videogameBest", indices = {@Index(value = {"gameID"}, unique = true)})
public class VideogameBest {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("gameID")
    @Expose
    private String gameID;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @PrimaryKey
    @NonNull
    @SerializedName("dealID")
    @Expose
    private String dealID;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("salePrice")
    @Expose
    private String salePrice;
    @SerializedName("retailPrice")
    @Expose
    private String retailPrice;
    @SerializedName("savings")
    @Expose
    private String savings;
    @SerializedName("thumb")
    @Expose
    private String thumb;

    public final static Parcelable.Creator<VideogameBest> CREATOR = new Parcelable.Creator<VideogameBest>() {

        public VideogameBest createFromParcel(android.os.Parcel in) {
            return new VideogameBest(in);
        }

        public VideogameBest[] newArray(int size) {
            return (new VideogameBest[size]);
        }

    };

    public VideogameBest(android.os.Parcel in) {
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.gameID = ((String) in.readValue((String.class.getClassLoader())));
        this.storeID = ((String) in.readValue((String.class.getClassLoader())));
        this.dealID = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.salePrice = ((String) in.readValue((String.class.getClassLoader())));
        this.retailPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.savings = ((String) in.readValue((String.class.getClassLoader())));
        this.thumb = ((String) in.readValue((String.class.getClassLoader())));
    }

    public VideogameBest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getDealID() {
        return dealID;
    }

    public void setDealID(String dealID) {
        this.dealID = dealID;
    }

    public String getPrice() { return price; }

    public void setPrice(String salePrice) {
        this.price = price;
    }

    public String getSalePrice() { return salePrice; }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getSavings() {
        return savings;
    }

    public void setSavings(String savings) {
        this.savings = savings;
    }


    public String getThumb() { return thumb; }

    public void setThumb(String thumb) { this.thumb = thumb; }

}
