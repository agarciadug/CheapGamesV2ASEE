
package es.unex.cheapgamesv2.data.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deal implements Parcelable
{

    @SerializedName("storeID")
    @Expose
    private String storeID;
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
    public final static Creator<Deal> CREATOR = new Creator<Deal>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Deal createFromParcel(android.os.Parcel in) {
            return new Deal(in);
        }

        public Deal[] newArray(int size) {
            return (new Deal[size]);
        }

    }
    ;

    protected Deal(android.os.Parcel in) {
        this.storeID = ((String) in.readValue((String.class.getClassLoader())));
        this.dealID = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.retailPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.savings = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Deal() {
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

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(storeID);
        dest.writeValue(dealID);
        dest.writeValue(price);
        dest.writeValue(retailPrice);
        dest.writeValue(savings);
    }

    public int describeContents() {
        return  0;
    }

}
