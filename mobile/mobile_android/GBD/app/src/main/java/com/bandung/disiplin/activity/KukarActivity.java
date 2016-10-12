package com.bandung.disiplin.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.simako.user.R;
import com.bandung.disiplin.model.KukarSigap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yogi on 9/14/16.
 */
public class KukarActivity extends AppCompatActivity {

    private ProgressDialog progress;
    List<KukarSigap> listData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        getData();
    }

    private void getData() {
        listData = new ArrayList<KukarSigap>();
        AndroidNetworking.initialize(getApplicationContext());
        progress = ProgressDialog.show(this, "",
                "Mohon tunggu...", true);

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

                                listData.add(ks);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
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
