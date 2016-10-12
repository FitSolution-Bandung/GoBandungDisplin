package com.bandung.disiplin.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bandung.disiplin.activity.KukalDetail;
import com.bandung.disiplin.model.KukarSigap;
import com.github.florent37.beautifulparallax.ParallaxViewController;
import com.simako.user.R;

import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MovieViewHolder> {

    private List<KukarSigap> movies;
    private int rowLayout;
    private Context context;
    ParallaxViewController parallaxViewController = new ParallaxViewController();


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBig;
        TextView textViewTittle;
        TextView textViewDesc;
        TextView textViewLokasi;
        TextView textViewdate;
        TextView textViewDetail;

        public MovieViewHolder(View v) {
            super(v);
            imgBig = (ImageView) v.findViewById(R.id.image_view_big);
            textViewTittle = (TextView) v.findViewById(R.id.text_view_tittle);
            textViewDesc = (TextView) v.findViewById(R.id.text_view_desc);
            textViewLokasi = (TextView) v.findViewById(R.id.text_view_lokasi);
            textViewdate = (TextView) v.findViewById(R.id.text_view_date);
            textViewDetail = (TextView) v.findViewById(R.id.txt_detail);
        }
    }

    public HomeAdapter(List<KukarSigap> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parallaxViewController.registerImageParallax(recyclerView);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {
//        holder.textViewTittle.setText(movies.get(position).judul_laporan);
//        holder.textViewDesc.setText(movies.get(position).kronologi);
//
//        holder.textViewLokasi.setText(movies.get(position).lokasi + "," + movies.get(position).kelurahan + "," + movies.get(position).kecamatan);
//        holder.textViewdate.setText(movies.get(position).tanggal_laporan);
//
//        Glide.with(context)
//                .load(movies.get(position).foto)
//                .placeholder(R.mipmap.ic_account_circle_white_48dps)
//                .crossFade()
//                .into(holder.imgBig);

        parallaxViewController.imageParallax(holder.imgBig);

        holder.textViewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, KukalDetail.class);
                i.putExtra("judul", movies.get(position).judul_laporan);
                i.putExtra("kronologi", movies.get(position).kronologi);
                i.putExtra("lokasi", holder.textViewLokasi.getText().toString());
                i.putExtra("date", movies.get(position).tanggal_laporan);
                i.putExtra("image", movies.get(position).foto);
                i.putExtra("id_laporan", movies.get(position).id_laporan);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);


            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}