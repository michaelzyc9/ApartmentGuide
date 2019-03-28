
package com.mz.apartmentguide.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links_ implements Parcelable
{

    @SerializedName("next")
    @Expose
    private Next next;
    public final static Parcelable.Creator<Links_> CREATOR = new Creator<Links_>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Links_ createFromParcel(Parcel in) {
            return new Links_(in);
        }

        public Links_[] newArray(int size) {
            return (new Links_[size]);
        }

    }
    ;

    protected Links_(Parcel in) {
        this.next = ((Next) in.readValue((Next.class.getClassLoader())));
    }

    public Links_() {
    }

    public Next getNext() {
        return next;
    }

    public void setNext(Next next) {
        this.next = next;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(next);
    }

    public int describeContents() {
        return  0;
    }

}
