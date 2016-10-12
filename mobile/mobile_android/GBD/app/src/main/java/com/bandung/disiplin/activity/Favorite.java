package com.bandung.disiplin.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.simako.user.R;

/**
 * Created by yogi on 7/17/16.
 */
public class Favorite extends AppCompatActivity {

    private Toolbar toolbar;
    private MediaPlayer mediaPlayer;
    private ImageView imgBroadcast;
    private TextView txtCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steeling);


        toolbar = (Toolbar) findViewById(R.id.toolbar_color);
        toolbar.setTitle("Steeling");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        imgBroadcast = (ImageView) findViewById(R.id.image_broadcast);
        txtCancel = (TextView) findViewById(R.id.text_view_cancel);

        imgBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                imgBroadcast.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
//                imgBroadcast.invalidate();
                imgBroadcast.setImageResource(R.drawable.steeling_a);
                //txtCancel.setVisibility(View.VISIBLE);
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);
                    } else {
                        mediaPlayer.release();
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);
                    }
                    mediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        imgBroadcast.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                imgBroadcast.getDrawable().clearColorFilter();
//                imgBroadcast.invalidate();
                imgBroadcast.setImageResource(R.drawable.steeling_b);
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);
                    }
                    mediaPlayer.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //txtCancel.setVisibility(View.GONE);
                return true;
            }
        });


//        img_agenda.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        img_agenda.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
//                        img_agenda.invalidate();
//                        Intent i = new Intent(getApplicationContext(),
//                                ReminderMain.class);
//                        startActivity(i);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        img_agenda.getDrawable().clearColorFilter();
//                        img_agenda.invalidate();
//                        break;
//                }
//                return true;
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
