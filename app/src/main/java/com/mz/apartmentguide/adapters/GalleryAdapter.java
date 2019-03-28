package com.mz.apartmentguide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mz.apartmentguide.R;
import com.mz.apartmentguide.models.Apartment;
import com.mz.apartmentguide.models.FloorPlan;
import com.mz.apartmentguide.models.Image;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class GalleryAdapter extends ArrayAdapter<Apartment> {

    public GalleryAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_content, parent, false);

            holder.image = convertView.findViewById(R.id.grid_image);
            holder.priceFrom = convertView.findViewById(R.id.grid_price_from);
            holder.cityZip = convertView.findViewById(R.id.grid_city_zip);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Apartment apt = getItem(position);
        HashMap<String, String> imageMap = new HashMap<>();
        for (Image img : apt.getImages()) {
            imageMap.put(img.getDescription(), img.getLink());
        }

        String imgKey = getContext().getString(R.string.image_interior_description);
        Picasso.with(getContext()).load(imageMap.get(imgKey)).noFade().fit().into(holder.image);
        holder.priceFrom.setText(getPriceFrom(position));
        holder.cityZip.setText(getCityAndZip(position));

        return convertView;
    }

    private String getCityAndZip(int position) {
        String address = getItem(position).getAddress().getFullAddress();
        String[] addArr = address.split(",");
        if (addArr.length < 2)
            return null;
        String cityAndZip = addArr[addArr.length - 2] + ", " + addArr[addArr.length - 1];
        return cityAndZip;
    }

    private String getPriceFrom(int position) {
        String priceFrom = null;
        for (FloorPlan fp : getItem(position).getFloorPlans()) {
            if (priceFrom == null) {
                priceFrom = String.valueOf(fp.getPriceFrom());
            } else {
                if (Integer.valueOf(fp.getPriceFrom()) < Integer.valueOf(priceFrom))
                    priceFrom = String.valueOf(fp.getPriceFrom());
            }
        }
        return priceFrom == null ? "N/A" : ("From $" + priceFrom);
    }

    private class ViewHolder {
        ImageView image;
        TextView priceFrom;
        TextView cityZip;
    }
}
