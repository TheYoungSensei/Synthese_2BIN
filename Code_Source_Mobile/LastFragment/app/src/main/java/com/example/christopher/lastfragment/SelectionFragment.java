package com.example.christopher.lastfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Christopher on 27-04-17.
 */
public class SelectionFragment extends Fragment {

    private OnArtistSelectedListener mCallBack;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallBack = (OnArtistSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mCallBack.onArtistSelected(position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_selection, container, false);
    }

    public interface OnArtistSelectedListener {

        public void onArtistSelected(int position);
    }




}
