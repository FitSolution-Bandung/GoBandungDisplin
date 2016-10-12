package com.bandung.disiplin.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.bandung.disiplin.MainActivity;

import com.bandung.disiplin.fragment.LaporanAnda;
import com.simako.user.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;


import nl.changer.polypicker.Config;
import nl.changer.polypicker.ImagePickerActivity;
import nl.changer.polypicker.utils.ImageInternalFetcher;



/*
 * Created by yogi on 7/17/16.
 */
public class LaporDetail extends AppCompatActivity {

    public String SERVER = "http://apm.icalan.co.id/api";

    public String SERVER_B = "http://apm.icalan.co.id/api/kirim_aduan";

    private static final String TAG = LaporDetail.class.getSimpleName();
    ProgressDialog progress;
    Button b1, b2;
    ImageView iv;
    private Toolbar toolbar;
    private Button btnSignIn;
    private File destination;
    private String selected;
    private String position_sample;
    private EditText isi;
    private Button btnCancel;

    //untuk tambahan capture
    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    private static final int INTENT_REQUEST_GET_N_IMAGES = 14;
    HashSet<Uri> mMedia = new HashSet<Uri>();
    private ViewGroup mSelectedImagesContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pemerintah);
        //tambahan untuk capture
        mSelectedImagesContainer = (ViewGroup) findViewById(R.id.selected_photos_container);


//        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        b1 = (Button) findViewById(R.id.button);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        iv = (ImageView) findViewById(R.id.imageView);

        isi = (EditText) findViewById(R.id.isi);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("kamseu","sdfsdf");
//                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, 0);
                // start polypicker activity to grab some images.

//                Intent intent = new Intent(getApplicationContext(), ImagePickerActivity.class);
//                Config config = new Config.Builder()
//                        .setTabBackgroundColor(R.color.white)    // set tab background color. Default white.
//                        .setTabSelectionIndicatorColor(R.color.blue)
//                        .setCameraButtonColor(R.color.green)
//                        .setSelectionLimit(2)    // set photo selection limit. Default unlimited selection.
//                        .build();
//                ImagePickerActivity.setConfig(config);
//                startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);
//                getNImages();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner);


        // Spinner Drop down elements
        List categories = new ArrayList();
        categories.add("kemacetan");
        categories.add("sampah");
        categories.add("-");
        categories.add("-");
        categories.add("pelanggaran");
        categories.add("-");
        categories.add("jalan rusak");
        categories.add("pengemis");
        categories.add("kaki lima liar");
        categories.add("-");
        categories.add("-");
        categories.add("-");
        categories.add("-");

        // Creating adapter for spinner
        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = parent.getItemAtPosition(position).toString();
                position_sample = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar_color);
        toolbar.setTitle("Lapor");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("logn", "post");

                progress = ProgressDialog.show(LaporDetail.this, "",
                        "Silahkan Tunggu", true);
                Bitmap image = ((BitmapDrawable) iv.getDrawable()).getBitmap();
//                new Upload(image, "IMG_" + timestamp).execute();
                AndroidNetworking.upload(SERVER_B)
                        .addMultipartFile("file", destination)
                        .addMultipartParameter("id_user", "1")
                        .addMultipartParameter("id_pemerintahan", "1")
                        .addMultipartParameter("isi", isi.getText().toString())
                        .addMultipartParameter("alamat", "Cirebon")
                        .addMultipartParameter("id_kat_aduan", position_sample)

                        .setTag("uploadTest")
                        .setPriority(Priority.HIGH)
                        .build()
                        .setUploadProgressListener(new UploadProgressListener() {
                            @Override
                            public void onProgress(long bytesUploaded, long totalBytes) {
                                // do anything with progress
                            }
                        })
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // do anything with response
                                progress.dismiss();
                                Log.d("response", response.toString());
                                Toast.makeText(getApplicationContext(), "Laporan Berhasil Dikirim", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LaporDetail.this, MainActivity.class);
                                startActivity(i);
                            }

                            @Override
                            public void onError(ANError error) {
                                // handle error
                                progress.dismiss();
                            }
                        });
            }
        });

        getNImages();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); //
            Intent i = new Intent(LaporDetail.this, MainActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Result","mask ke restul");
        if (resultCode == Activity.RESULT_OK) {
            Log.d("Result","mask ke rs");
            if (requestCode == INTENT_REQUEST_GET_N_IMAGES) {
                Log.d("Result","mask ke rs 2");
                //Intent intent = new Intent();
                Parcelable[] parcelableUris = data.getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);


                if (parcelableUris == null) {
                    Log.d("parcable","kosong");
                    return;
                }
                Log.d("parcable","tidak kosong");
                // Java doesn't allow array casting, this is a little hack
                Uri[] uris = new Uri[parcelableUris.length];
                System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);

                if (uris != null) {
                    for (Uri uri : uris) {
                        Log.d("parcable"," uri: " + uri);
                        Log.i(TAG, " uri: " + uri);
                        mMedia.add(uri);
                    }

                    showMedia();
                }
            }
        }

//        if (resultCode != 0) {
//            Bitmap bp = (Bitmap) data.getExtras().get("data");
//            iv.setImageBitmap(bp);
//
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            bp.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//            destination = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
//            FileOutputStream fo;
//            try {
//                fo = new FileOutputStream(destination);
//                fo.write(bytes.toByteArray());
//                fo.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

    }
    private void getImages() {
        Intent intent = new Intent(getApplicationContext(), ImagePickerActivity.class);
        startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);
    }
    private void getNImages() {
        Intent intent = new Intent(getApplicationContext(), ImagePickerActivity.class);
        Config config = new Config.Builder()
                .setTabBackgroundColor(R.color.white)    // set tab background color. Default white.
                .setTabSelectionIndicatorColor(R.color.blue)
                .setCameraButtonColor(R.color.orange)
                .setSelectionLimit(5)    // set photo selection limit. Default unlimited selection.
                .build();
        ImagePickerActivity.setConfig(config);
        startActivityForResult(intent, INTENT_REQUEST_GET_N_IMAGES);
//        startActivityForResult(intent, 111);
    }
    private void showMedia() {
        // Remove all views before
        // adding the new ones.
        mSelectedImagesContainer.removeAllViews();
        Log.d("ShowMedia","masuk ke showmedia");
        Iterator<Uri> iterator = mMedia.iterator();
        ImageInternalFetcher imageFetcher = new ImageInternalFetcher(getApplicationContext(), 500);
        while (iterator.hasNext()) {
            Uri uri = iterator.next();

            // showImage(uri);
            Log.i(TAG, " uri: " + uri);
            if (mMedia.size() >= 1) {
                mSelectedImagesContainer.setVisibility(View.VISIBLE);
                Log.d("Looping","masuk ke looping");
            }

            View imageHolder = LayoutInflater.from(getApplicationContext()).inflate(R.layout.media_layout, null);

            // View removeBtn = imageHolder.findViewById(R.id.remove_media);
            // initRemoveBtn(removeBtn, imageHolder, uri);
            ImageView thumbnail = (ImageView) imageHolder.findViewById(R.id.media_image);

            if (!uri.toString().contains("content://")) {
                // probably a relative uri
                uri = Uri.fromFile(new File(uri.toString()));
            }

            imageFetcher.loadImage(uri, thumbnail);

            mSelectedImagesContainer.addView(imageHolder);

            // set the dimension to correctly
            // show the image thumbnail.
            int wdpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
            int htpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
//            thumbnail.setLayoutParams(new FrameLayout.LayoutParams(wdpx, htpx));
            thumbnail.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }

}
