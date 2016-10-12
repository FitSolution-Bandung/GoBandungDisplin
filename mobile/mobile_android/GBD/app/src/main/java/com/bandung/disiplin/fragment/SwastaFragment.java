package com.bandung.disiplin.fragment;

/**
 * Created by yogi on 5/15/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simako.user.R;


public class SwastaFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.swasta_fragment, container, false);

        return view;

    }


}