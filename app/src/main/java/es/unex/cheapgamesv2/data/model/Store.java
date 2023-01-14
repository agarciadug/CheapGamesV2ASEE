
package es.unex.cheapgamesv2.data.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Store implements Parcelable
{

    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("storeName")
    @Expose
    private String storeName;
    public final static Creator<Store> CREATOR = new Creator<Store>() {

        @SuppressWarnings({
            "unchecked"
        })
        public Store createFromParcel(android.os.Parcel in) {
            return new Store(in);
        }

        public Store[] newArray(int size) {
            return (new Store[size]);
        }
    }
    ;

    protected Store(android.os.Parcel in) {
        this.storeID = ((String) in.readValue((String.class.getClassLoader())));
        this.storeName = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Store() {
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }


    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(storeID);
        dest.writeValue(storeName);
    }

    public int describeContents() {
        return  0;
    }

}
