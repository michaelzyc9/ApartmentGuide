package com.example.apartmentguide.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apartmentguide.R;
import com.example.apartmentguide.models.ApartmentBuilding;
import com.example.apartmentguide.models.GoogleMapApiResponse;
import com.example.apartmentguide.utils.GetApartmentsInterface;
import com.example.apartmentguide.utils.GoogleMapAPIs;
import com.example.apartmentguide.utils.GoogleMapApiClient;
import com.example.apartmentguide.utils.RetrofitClientInstance;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment
        implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MapView mMapView;
    private GoogleMap mMap;
    private OnFragmentInteractionListener mListener;

    private static final LatLng MOUNTAIN_VIEW = new LatLng(37.4, -122.1);

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);//when you already implement OnMapReadyCallback in your fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
                .target(MOUNTAIN_VIEW)      // Sets the center of the map to Mountain View
                .zoom(10)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.getUiSettings().setZoomControlsEnabled(true);

        GetApartmentsInterface service = RetrofitClientInstance.getRetrofitInstance()
                .create(GetApartmentsInterface.class);
        Call<List<ApartmentBuilding>> call = service.getApartments();
        call.enqueue(new Callback<List<ApartmentBuilding>>() {
            @Override
            public void onResponse(Call<List<ApartmentBuilding>> call, Response<List<ApartmentBuilding>> response) {
                if (response == null)
                    return;
                displayAptOnMap(response.body());
            }

            @Override
            public void onFailure(Call<List<ApartmentBuilding>> call, Throwable t) {
                Log.e("GetApartments failed: ", t.getMessage());
            }
        });
    }

    private void displayAptOnMap(List<ApartmentBuilding> aptData) {
        GoogleMapAPIs googleMapAPIs = GoogleMapApiClient.getClient(getContext()).create(GoogleMapAPIs.class);

        for (final ApartmentBuilding ap : aptData) {

            Call<GoogleMapApiResponse> call = googleMapAPIs.getGeocodingResponse(ap.getAddress(),
                    getContext().getString(R.string.google_maps_key));
            call.enqueue(new Callback<GoogleMapApiResponse>() {
                @Override
                public void onResponse(Call<GoogleMapApiResponse> call, Response<GoogleMapApiResponse> response) {

                    Double lat = response.body().getResults().get(0).getGeometry().getLocation().getLat();
                    Double lng = response.body().getResults().get(0).getGeometry().getLocation().getLng();

                    if(lat == null || lng == null){
                        Log.e("MapFragment","lat or lng is NULL");
                    } else {
                        MarkerOptions options = new MarkerOptions().position(new LatLng(lat, lng));
                        options.title("From $" + ap.getPriceFrom());
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        mMap.addMarker(options);
                    }
                }

                @Override
                public void onFailure(Call<GoogleMapApiResponse> call, Throwable t) {
                    Log.e("MapFragment","API call failed");
                }
            });
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
