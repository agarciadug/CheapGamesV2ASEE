
package es.unex.cheapgamesv2.data.model;


import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheapestPriceEver implements Parcelable
{

    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("date")
    @Expose
    private Integer date;
    public final static Creator<CheapestPriceEver> CREATOR = new Creator<CheapestPriceEver>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CheapestPriceEver createFromParcel(android.os.Parcel in) {
            return new CheapestPriceEver(in);
        }

        public CheapestPriceEver[] newArray(int size) {
            return (new CheapestPriceEver[size]);
        }

    }
    ;

    protected CheapestPriceEver(android.os.Parcel in) {
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.date = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public CheapestPriceEver() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(price);
        dest.writeValue(date);
    }

    public int describeContents() {
        return  0;
    }

}
