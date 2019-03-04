package com.michaelzhang.apartmentguide.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michaelzhang.apartmentguide.R;
import com.michaelzhang.apartmentguide.activities.AptDetailActivity;
import com.michaelzhang.apartmentguide.models.Apartment;
import com.michaelzhang.apartmentguide.responses.AgServiceResponse;
import com.michaelzhang.apartmentguide.responses.GoogleMapApiResponse;
import com.michaelzhang.apartmentguide.utils.AgServiceAPI;
import com.michaelzhang.apartmentguide.utils.GoogleMapAPI;
import com.michaelzhang.apartmentguide.utils.GoogleMapClient;
import com.michaelzhang.apartmentguide.utils.AgServiceClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends Fragment
        implements OnMapReadyCallback {

    private MapView mMapView;
    private GoogleMap mMap;
    private OnFragmentInteractionListener mListener;

    private static final LatLng MAP_CENTER = new LatLng(37.38, -122);

    public MapFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(MAP_CENTER)
                .zoom(10)
                .bearing(0)
                .tilt(0)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.getUiSettings().setZoomControlsEnabled(true);

        AgServiceAPI service = AgServiceClient.getServiceClient(getContext())
                .create(AgServiceAPI.class);
        Call<AgServiceResponse> call = service.getAll();
        call.enqueue(new Callback<AgServiceResponse>() {
            @Override
            public void onResponse(Call<AgServiceResponse> call, Response<AgServiceResponse> response) {
                if (response == null || response.body() == null || !isAdded())
                    return;
                displayAptOnMap(response.body().getEmbedded().getApartments());
            }

            @Override
            public void onFailure(Call<AgServiceResponse> call, Throwable t) {
                Log.e("GetApartments failed: ", t.getMessage());
            }
        });
    }

    private void displayAptOnMap(final List<Apartment> aptData) {
        GoogleMapAPI googleMapAPI = GoogleMapClient.getClient(getContext()).create(GoogleMapAPI.class);

        for (final Apartment ap : aptData) {

            Call<GoogleMapApiResponse> call = googleMapAPI.getGeocodingResponse(ap.getAddress().getFullAddress(),
                    getContext().getString(R.string.google_maps_key));
            call.enqueue(new Callback<GoogleMapApiResponse>() {
                @Override
                public void onResponse(Call<GoogleMapApiResponse> call, Response<GoogleMapApiResponse> response) {

                    if (response == null || response.body() == null || !isAdded())
                        return;

                    Double lat = null;
                    Double lng = null;
                    try {
                        lat = response.body().getResults().get(0).getGeometry().getLocation().getLat();
                        lng = response.body().getResults().get(0).getGeometry().getLocation().getLng();
                    } catch (Exception e) {
                        Log.e("ERROR getting latLng", e.getMessage());
                    }

                    if (lat == null || lng == null) {
                        Log.e("MapFragment", "lat or lng is NULL");
                    } else {
                        MarkerOptions options = new MarkerOptions().position(new LatLng(lat, lng));
                        options.title("From $" + ap.getPriceFrom());
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        mMap.addMarker(options).setTag(ap);
                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                Intent intent = new Intent(getActivity(), AptDetailActivity.class);
                                intent.putExtra(getContext().getString(R.string.apt_data_field), (Apartment) marker.getTag());
                                startActivity(intent);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<GoogleMapApiResponse> call, Throwable t) {
                    Log.e("MapFragment", "API call failed");
                }
            });
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
