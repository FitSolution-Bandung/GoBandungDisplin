package com.bandung.disiplin.fragment;

/**
 * Created by yogi on 5/15/16.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.bandung.disiplin.activity.LaporDetail;
import com.bandung.disiplin.adapter.HomeAdapter;
import com.bandung.disiplin.model.KukarSigap;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.simako.user.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import nl.changer.polypicker.Config;
import nl.changer.polypicker.ImagePickerActivity;
import nl.changer.polypicker.utils.ImageInternalFetcher;


public class LaporanAnda extends Fragment {
    private View view;

    private ProgressDialog progress;
    private HomeAdapter adapter;
    List<KukarSigap> pemerintah_data;


    private FloatingActionMenu menu1;
    private FloatingActionButton fab1, fab2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.laporan_anda_fragment, container, false);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.movies_recycler_view);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        pemerintah_data = new ArrayList<KukarSigap>();
        adapter = new HomeAdapter(pemerintah_data, R.layout.row_home, getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        //untuk set data dummy
        setDataDummy();

        configViews(view);
        return view;

    }
    private void setDataDummy(){
        // untuk set data dummy
        KukarSigap ks = new KukarSigap();
        ks.id_laporan = "1";
        ks.pelapor = "Yogi";
        ks.judul_laporan = "Macet";
        ks.kronologi = "Ada angkot berhenti sembarangan di bahu jalan, padahal ada rambu dilarang parkir";
        ks.foto = "Foto";
        ks.lokasi = "Jln. Buah batu no.155";
        ks.kecamatan = "Buah batu";
        ks.kelurahan = "Dayeuh Kolot";
        ks.tanggal_laporan = "12/06/2016";
        pemerintah_data.add(ks);
        adapter.notifyDataSetChanged();
    }
    private void getData(){
        AndroidNetworking.initialize(getActivity().getApplicationContext());
        progress = ProgressDialog.show(getActivity(), "",
                "Mohon tunggu...", true);
        AndroidNetworking.get("http://mako.sangka.co.id/sapi/laporan")
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
                                KukarSigap ks = new KukarSigap();
                                ks.id_laporan = data.getString("id_laporan");
                                ks.pelapor = data.getString("pelapor");
                                ks.judul_laporan = data.getString("judul_laporan");
                                ks.kronologi = data.getString("kronologi");
                                ks.foto = data.getString("foto");
                                ks.lokasi = data.getString("lokasi");
                                ks.kecamatan = data.getString("kecamatan");
                                ks.kelurahan = data.getString("kelurahan");
                                ks.tanggal_laporan = data.getString("tanggal_laporan");

                                pemerintah_data.add(ks);

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

    private void configViews(View view) {
        menu1 = (FloatingActionMenu) view.findViewById(R.id.menu1);
        fab1 = (FloatingActionButton) view.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) view.findViewById(R.id.fab2);


        fab1.setColorNormalResId(R.color.primary);
        fab2.setColorNormalResId(R.color.primary);
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
//                getNImages();
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


    }

}