package com.example.apartmentguide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apartmentguide.R;
import com.example.apartmentguide.models.ApartmentBuilding;
import com.squareup.picasso.Picasso;

public class GalleryAdapter extends ArrayAdapter<ApartmentBuilding> {

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

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(getContext()).load(getItem(position).getImages()[0]).noFade().fit().into(holder.image);

        return convertView;
    }

    private class ViewHolder{
        ImageView image;
    }
}
