package com.example.apartmentguide.activities;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.apartmentguide.R;
import com.example.apartmentguide.models.ApartmentBuilding;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AptDetailActivity extends AppCompatActivity
        implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    public static final String APT_DATA = "extra_apt";
    
    public static final String IMAGE_EXTERIOR = "exterior";
    public static final String IMAGE_COMMON = "common-area";
    public static final String IMAGE_INTERIOR = "interior";

    //Image descriptions for sliding image sections
    public static final String DESCRIPTION_EXTERIOR = "Exterior";
    public static final String DESCRIPTION_COMMON = "Amenity";
    public static final String DESCRIPTION_INTERIOR = "Unit";
    
    private SliderLayout imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apt_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        ApartmentBuilding apt = getIntent().getExtras().getParcelable(APT_DATA);

        //ImageView image = findViewById(R.id.list_detail_image);
        TextView name = findViewById(R.id.list_detail_name);
        TextView address = findViewById(R.id.list_detail_address);
        TextView website = findViewById(R.id.list_detail_website);

        //Picasso.get().load(apt.getImages()[0]).noFade().fit().into(image);
        setUpImageSlider(apt);
        name.setText(apt.getName());
        address.setText(apt.getAddress());
        website.setText(apt.getWebsite());
        


        //TODO Floorplan section




    }

    private void setUpImageSlider(ApartmentBuilding apt) {
        imageSlider = findViewById(R.id.list_detail_image_slider);

        HashMap<String,String> urlMap = new HashMap<String, String>();
        urlMap.put(IMAGE_EXTERIOR,apt.getImages()[0]);
        urlMap.put(IMAGE_COMMON,apt.getImages()[1]);
        urlMap.put(IMAGE_INTERIOR,apt.getImages()[2]);

        HashMap<String,String> descriptionMap = new HashMap<String, String>();
        descriptionMap.put(IMAGE_EXTERIOR,DESCRIPTION_EXTERIOR);
        descriptionMap.put(IMAGE_COMMON,DESCRIPTION_COMMON);
        descriptionMap.put(IMAGE_INTERIOR,DESCRIPTION_INTERIOR);

        for(String name : urlMap.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(descriptionMap.get(name))
                    .image(urlMap.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra",name);

            imageSlider.addSlider(textSliderView);
        }
        imageSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        imageSlider.setCustomAnimation(new DescriptionAnimation());
        imageSlider.setDuration(4000);
        imageSlider.addOnPageChangeListener(this);
//        ListView l = (ListView)findViewById(R.id.transformers);
//        l.setAdapter(new TransformerAdapter(this));
//        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                imageSlider.setPresetTransformer(((TextView) view).getText().toString());
//                Toast.makeText(MainActivity.this, ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
