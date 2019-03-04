
package com.michaelzhang.apartmentguide.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Page implements Parcelable
{

    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("totalElements")
    @Expose
    private Integer totalElements;
    @SerializedName("totalPages")
    @Expose
    private Integer totalPages;
    @SerializedName("number")
    @Expose
    private Integer number;
    public final static Parcelable.Creator<Page> CREATOR = new Creator<Page>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Page createFromParcel(Parcel in) {
            return new Page(in);
        }

        public Page[] newArray(int size) {
            return (new Page[size]);
        }

    }
    ;

    protected Page(Parcel in) {
        this.size = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalElements = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.number = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Page() {
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(size);
        dest.writeValue(totalElements);
        dest.writeValue(totalPages);
        dest.writeValue(number);
    }

    public int describeContents() {
        return  0;
    }

}
