package com.michaelzhang.apartmentguide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michaelzhang.apartmentguide.R;
import com.michaelzhang.apartmentguide.models.Apartment;
import com.michaelzhang.apartmentguide.models.FloorPlan;
import com.michaelzhang.apartmentguide.models.Image;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListAdapter extends ArrayAdapter<Apartment> {
    public ListAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_row, parent, false);

            holder.thumbnail = convertView.findViewById(R.id.list_view_thumbnail);
            holder.name = convertView.findViewById(R.id.list_view_name);
            holder.address = convertView.findViewById(R.id.list_view_address);
            holder.fpInfo = convertView.findViewById(R.id.list_view_floorplans);
            holder.priceFrom = convertView.findViewById(R.id.list_view_price_from);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Apartment apt = getItem(position);
        HashMap<String, String> imageMap = new HashMap<>();
        for (Image img : apt.getImages()) {
            imageMap.put(img.getDescription(), img.getLink());
        }

        String imgKey = getContext().getString(R.string.image_exterior_description);
        Picasso.with(getContext()).load(imageMap.get(imgKey)).noFade().fit().into(holder.thumbnail);
        holder.name.setText(apt.getName());
        holder.address.setText(apt.getAddress().getFullAddress());
        setFloorplanTextView(holder.fpInfo, apt.getFloorPlans());
        holder.priceFrom.setText(getPriceFrom(position));

        return convertView;
    }

    private String getPriceFrom(int position) {
        String priceFrom = null;
        for (FloorPlan fp : getItem(position).getFloorPlans()) {
            if (priceFrom == null) {
                priceFrom = fp.getPriceFrom().toString();
            } else {
                if (Integer.valueOf(fp.getPriceFrom()) < Integer.valueOf(priceFrom))
                    priceFrom = fp.getPriceFrom().toString();
            }
        }
        return priceFrom == null ? "N/A" : ("From $" + priceFrom);
    }

    private void setFloorplanTextView(TextView textView, List<FloorPlan> floorPlans) {
        Collections.sort(floorPlans, new Comparator<FloorPlan>() {
            @Override
            public int compare(FloorPlan o1, FloorPlan o2) {
                return o1.compareTo(o2);
            }
        });
        String text = "";
        Map<String, Boolean> distinctBedNumber = new HashMap<>();
        for (int i = 0; i < floorPlans.size(); i++) {
            String bedNumber = floorPlans.get(i).getBed().intValue() + "";
            if (i == 0) {
                text = "  " + bedNumber;
            } else if (!distinctBedNumber.containsKey(bedNumber)) {
                text += ", " + bedNumber;
            }
            distinctBedNumber.put(bedNumber, true);
        }
        textView.setText(text);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bed, 0, 0, 0);
    }

    class ViewHolder {
        ImageView thumbnail;
        TextView name;
        TextView address;
        TextView fpInfo;
        TextView priceFrom;
    }
}
