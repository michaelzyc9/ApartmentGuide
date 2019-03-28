
package com.mz.apartmentguide.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Next implements Parcelable
{

    @SerializedName("href")
    @Expose
    private String href;
    public final static Parcelable.Creator<Next> CREATOR = new Creator<Next>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Next createFromParcel(Parcel in) {
            return new Next(in);
        }

        public Next[] newArray(int size) {
            return (new Next[size]);
        }

    }
    ;

    protected Next(Parcel in) {
        this.href = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Next() {
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(href);
    }

    public int describeContents() {
        return  0;
    }

}
