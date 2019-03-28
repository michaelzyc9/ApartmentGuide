
package com.mz.apartmentguide.responses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mz.apartmentguide.models.Embedded;
import com.mz.apartmentguide.models.Links_;
import com.mz.apartmentguide.models.Page;

public class AgServiceResponse implements Parcelable
{

    @SerializedName("_embedded")
    @Expose
    private Embedded embedded;
    @SerializedName("_links")
    @Expose
    private Links_ links;
    @SerializedName("page")
    @Expose
    private Page page;
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
        this.links = ((Links_) in.readValue((Links_.class.getClassLoader())));
        this.page = ((Page) in.readValue((Page.class.getClassLoader())));
    }

    public AgServiceResponse() {
    }

    public Embedded getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
    }

    public Links_ getLinks() {
        return links;
    }

    public void setLinks(Links_ links) {
        this.links = links;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(embedded);
        dest.writeValue(links);
        dest.writeValue(page);
    }

    public int describeContents() {
        return 0;
    }

}