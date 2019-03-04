
package com.michaelzhang.apartmentguide.responses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.michaelzhang.apartmentguide.models.Embedded;

public class AgServiceResponse implements Parcelable
{

    @SerializedName("_embedded")
    @Expose
    private Embedded embedded;
    public final static Parcelable.Creator<AgServiceResponse> CREATOR = new Creator<AgServiceResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AgServiceResponse createFromParcel(Parcel in) {
            return new AgServiceResponse(in);
        }

        public AgServiceResponse[] newArray(int size) {
            return (new AgServiceResponse[size]);
        }

    }
    ;

    protected AgServiceResponse(Parcel in) {
        this.embedded = ((Embedded) in.readValue((Embedded.class.getClassLoader())));
    }

    public AgServiceResponse() {
    }

    public Embedded getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(embedded);
    }

    public int describeContents() {
        return  0;
    }

}
