package com.bandung.disiplin.fragment;

/**
 * Created by yogi on 5/15/16.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.bandung.disiplin.adapter.PemerintahAdapter;
import com.bandung.disiplin.model.Pemerintah;
import com.simako.user.R;
import com.bandung.disiplin.activity.LaporDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PemerintahanFragment extends Fragment {

    private View view;
    private ObservableScrollView mScrollView;
    private FloatingActionMenu menu1;
    private FloatingActionButton fab1, fab2;
    List<Pemerintah> pemerintah_data;
    private ProgressDialog progress;
    private PemerintahAdapter adapter;

    public static PemerintahanFragment newInstance() {
        return new PemerintahanFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.pemerintahan_fragment, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);


        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
        pemerintah_data = new ArrayList<Pemerintah>();
        menu1 = (FloatingActionMenu) view.findViewById(R.id.menu1);
        fab1 = (FloatingActionButton) view.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) view.findViewById(R.id.fab2);

        menu1.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                menu1.toggle(true);
            }
        });
        menu1.hideMenuButton(false);        // hide buttons
        menu1.setClosedOnTouchOutside(true);    // set close when touched outside
        menu1.showMenuButton(true);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LaporDetail.class);
                startActivity(i);
                menu1.close(true);
                getActivity().finish();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LaporDetail.class);
                startActivity(i);
                menu1.close(true);
                getActivity().finish();

            }
        });

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.movies_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());;
        adapter = new PemerintahAdapter(pemerintah_data, R.layout.list_item_pemerintahan, getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //recyclerView.setNestedScrollingEnabled(false);

        AndroidNetworking.initialize(getActivity().getApplicationContext());
        progress = ProgressDialog.show(getActivity(), "",
                "Silahkan Tunggu", true);

        AndroidNetworking.get("http://apm.icalan.co.id/api/get_aduan")
                .setTag("GET DATA ADUAN")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        Log.d("get data aduan", response.toString());
                        progress.dismiss();


                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                Pemerintah pm = new Pemerintah();
                                pm.nama_kategori = data.getString("nama_kategori");
                                pm.alamat = data.getString("alamat");
                                pm.file = data.getString("file");
                                pm.id_pengaduan = data.getString("id_pengaduan");
                                pm.id_user = data.getString("id_user");
                                pm.isi = data.getString("isi");
                                pm.nama_lengkap = data.getString("nama_lengkap");
                                pm.tgl_aduan = data.getString("tgl_aduan");

                                pemerintah_data.add(pm);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("get data aduan error", error.toString());
                        progress.dismiss();
                    }
                });

    }


}