
package com.michaelzhang.apartmentguide.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Self implements Parcelable
{

    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("templated")
    @Expose
    private Boolean templated;
    public final static Parcelable.Creator<Self> CREATOR = new Creator<Self>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Self createFromParcel(Parcel in) {
            return new Self(in);
        }

        public Self[] newArray(int size) {
            return (new Self[size]);
        }

    }
    ;

    protected Self(Parcel in) {
        this.href = ((String) in.readValue((String.class.getClassLoader())));
        this.templated = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public Self() {
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Boolean getTemplated() {
        return templated;
    }

    public void setTemplated(Boolean templated) {
        this.templated = templated;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(href);
        dest.writeValue(templated);
    }

    public int describeContents() {
        return  0;
    }

}
