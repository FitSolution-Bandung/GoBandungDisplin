package com.bandung.disiplin.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.bandung.disiplin.config.CONSTANT;
import com.simako.user.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yogi on 7/21/16.
 */
public class KukalDetail extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textViewTittle;
    private TextView textViewDesc;
    private TextView textViewDate;
    private TextView textViewLokasi;
    private Button buttonBroad;
    private Bundle b;
    private ImageView imageViewDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kukar_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("DETAIL");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        b = new Bundle();
        b = getIntent().getExtras();
        String tittle = b.getString("judul");
        String kronologi = b.getString("kronologi");
        String lokasi = b.getString("lokasi");
        String date = b.getString("date");
        String image = b.getString("image");
        final String id_laporan = b.getString("id_laporan");

        buttonBroad = (Button) findViewById(R.id.btn_send);
        imageViewDetail = (ImageView) findViewById(R.id.image_view_detail);
        textViewTittle = (TextView) findViewById(R.id.text_view_tittle);
        textViewDesc = (TextView) findViewById(R.id.text_view_desc);
        textViewDate = (TextView) findViewById(R.id.text_view_date);
        textViewLokasi = (TextView) findViewById(R.id.text_view_lokasi);

//        textViewTittle.setText(tittle);
//        textViewDesc.setText(kronologi);
//        textViewDate.setText(date);
//        textViewLokasi.setText(lokasi);
//        Glide.with(this)
//                .load(image)
//                .placeholder(R.mipmap.ic_account_circle_white_48dps)
//                .crossFade()
//                .into(imageViewDetail);


        buttonBroad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast(id_laporan);
            }
        });

    }

    private void sendBroadcast(String id_lapor) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id_laporan", id_lapor);
            jsonObject.put("grup", "terpadu");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("cek params -->", jsonObject.toString());


        AndroidNetworking.post(CONSTANT.BASE_BROAD)
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("SEND BROAD")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        // do anything with response
                        Log.d("response >", response);
                        Toast.makeText(getApplicationContext(), "Laporan Berhasil Terkirim ke Tim Terpadu", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
//            Intent i = new Intent(this, MainActivity.class);
//            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
