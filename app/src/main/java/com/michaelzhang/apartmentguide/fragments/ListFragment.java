package com.michaelzhang.apartmentguide.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.michaelzhang.apartmentguide.R;
import com.michaelzhang.apartmentguide.activities.AptDetailActivity;
import com.michaelzhang.apartmentguide.adapters.ListAdapter;
import com.michaelzhang.apartmentguide.models.Apartment;
import com.michaelzhang.apartmentguide.responses.AgServiceResponse;
import com.michaelzhang.apartmentguide.utils.AgServiceAPI;
import com.michaelzhang.apartmentguide.utils.AgServiceClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends android.support.v4.app.ListFragment {

    private OnFragmentInteractionListener mListener;
    private ListAdapter mAdapter;

    public ListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new ListAdapter(getActivity(), 0);
        AgServiceAPI service = AgServiceClient.getServiceClient(getContext())
                .create(AgServiceAPI.class);
        Call<AgServiceResponse> call = service.getAll();
        call.enqueue(new Callback<AgServiceResponse>() {
            @Override
            public void onResponse(Call<AgServiceResponse> call, Response<AgServiceResponse> response) {
                if (response == null || response.body() == null || !isAdded())
                    return;
                for (Apartment ap : response.body().getEmbedded().getApartments()) {
                    Log.e("Apartment: ", ap.getName());
                    mAdapter.add(ap);
                }
                mAdapter.notifyDataSetChanged();
                setListAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<AgServiceResponse> call, Throwable t) {
                Log.e("GetApartments failed: ", t.getMessage());
            }
        });

        ListView lv = (ListView) getActivity().findViewById(android.R.id.list);
        View footerView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_view, null, false);
        lv.addFooterView(footerView);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (position < mAdapter.getCount()) {
            super.onListItemClick(l, v, position, id);
            Intent intent = new Intent(getActivity(), AptDetailActivity.class);
            intent.putExtra(getContext().getString(R.string.apt_data_field), mAdapter.getItem(position));
            startActivity(intent);
        }
    }
}
