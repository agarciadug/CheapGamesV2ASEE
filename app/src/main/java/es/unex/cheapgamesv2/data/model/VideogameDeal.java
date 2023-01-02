package es.unex.cheapgamesv2.data.model;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "videogameDeal", indices = {@Index(value = {"dealID"}, unique = true)})
public class VideogameDeal {

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
    @SerializedName("retailPrice")
    @Expose
    private String retailPrice;
    @SerializedName("savings")
    @Expose
    private String savings;

    public final static Parcelable.Creator<VideogameDeal> CREATOR = new Parcelable.Creator<VideogameDeal>() {

        public VideogameDeal createFromParcel(android.os.Parcel in) {
            return new VideogameDeal(in);
        }

        public VideogameDeal[] newArray(int size) {
            return (new VideogameDeal[size]);
        }

    };

    public VideogameDeal(android.os.Parcel in) {
        this.gameID = ((String) in.readValue((String.class.getClassLoader())));
        this.storeID = ((String) in.readValue((String.class.getClassLoader())));
        this.dealID = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.retailPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.savings = ((String) in.readValue((String.class.getClassLoader())));
    }

    public VideogameDeal() {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

}
