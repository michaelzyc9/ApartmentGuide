package com.michaelzhang.apartmentguide.activities;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.michaelzhang.apartmentguide.R;
import com.michaelzhang.apartmentguide.models.Apartment;
import com.michaelzhang.apartmentguide.models.FloorPlan;
import com.michaelzhang.apartmentguide.models.Image;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

public class AptDetailActivity extends AppCompatActivity
        implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout imageSlider;

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apt_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        Apartment apt = getIntent().getExtras().getParcelable(getString(R.string.apt_data_field));

        TextView name = findViewById(R.id.list_detail_name);
        TextView address = findViewById(R.id.list_detail_address);
        TextView website = findViewById(R.id.list_detail_website);
        website.setMovementMethod(LinkMovementMethod.getInstance());

        setUpImageSlider(apt);
        name.setText(apt.getName());
        address.setText(apt.getAddress().getFullAddress());
        website.setText(apt.getWebsite());
        setUpFloorPlanView(apt);

    }

    private void setUpFloorPlanView(Apartment apt) {
        LinearLayout floorplanContent = findViewById(R.id.list_detail_floorplan_content);
        Collections.sort(apt.getFloorPlans(), new Comparator<FloorPlan>() {
            @Override
            public int compare(FloorPlan o1, FloorPlan o2) {
                return o1.compareTo(o2);
            }
        });

        for (int i = 0; i < apt.getFloorPlans().size(); i++) {
            FloorPlan fp = apt.getFloorPlans().get(i);
            RelativeLayout contentRow = new RelativeLayout(this);
            RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            contentRow.setLayoutParams(relativeParams);

            TextView floorplan = new TextView(this);
            floorplan.setId((new Random().nextInt(Integer.MAX_VALUE)) + 1);
            TextView priceFrom = new TextView(this);

            String bed = "";
            String bath = "";

            if (fp.getBed() == Math.floor(fp.getBed())) {
                bed = String.valueOf(fp.getBed().intValue());
            } else {
                bed = String.valueOf(fp.getBed());
            }
            if (fp.getBath() == Math.floor(fp.getBath())) {
                bath = String.valueOf(fp.getBath().intValue());
            } else {
                bath = String.valueOf(fp.getBath());
            }
            floorplan.setText(bed + " bed, " + bath + " bath");
            priceFrom.setText("From $" + fp.getPriceFrom());

            contentRow.addView(floorplan);
            contentRow.addView(priceFrom);

            //formatting layouts
            RelativeLayout.LayoutParams floorplanParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            floorplan.setLayoutParams(floorplanParams);

            RelativeLayout.LayoutParams priceParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            priceParams.addRule(RelativeLayout.RIGHT_OF, floorplan.getId());
            priceFrom.setLayoutParams(priceParams);
            priceFrom.setGravity(Gravity.RIGHT);

            floorplanContent.addView(contentRow);
        }
    }

    private void setUpImageSlider(Apartment apt) {
        imageSlider = findViewById(R.id.list_detail_image_slider);

        HashMap<String, String> imageMap = new HashMap<>();
        for (Image img : apt.getImages()) {
            imageMap.put(img.getLink(), img.getDescription());
        }

        for (String link : imageMap.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .description(imageMap.get(link))
                    .image(link)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            imageSlider.addSlider(textSliderView);
        }
        imageSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        imageSlider.setCustomAnimation(new DescriptionAnimation());
        imageSlider.setDuration(4000);
        imageSlider.addOnPageChangeListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
