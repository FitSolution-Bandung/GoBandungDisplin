package com.bandung.disiplin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.beautifulparallax.ParallaxViewController;
import com.bandung.disiplin.model.Pemerintah;
import com.simako.user.R;

import java.util.List;


public class PemerintahAdapter extends RecyclerView.Adapter<PemerintahAdapter.MovieViewHolder> {

    private List<Pemerintah> movies;
    private int rowLayout;
    private Context context;
    ParallaxViewController parallaxViewController = new ParallaxViewController();


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        ImageView ivProfile2, img_status,ivRestaurant2;
        TextView tvRestaurantName2;
        TextView tvRestaurantType2;
        TextView tvLocation2;

        public MovieViewHolder(View v) {
            super(v);
            ivProfile2 = (ImageView) v.findViewById(R.id.ivProfile2);
            img_status = (ImageView) v.findViewById(R.id.img_status);
            ivRestaurant2 = (ImageView) v.findViewById(R.id.ivRestaurant2);
            tvRestaurantName2 = (TextView) v.findViewById(R.id.tvRestaurantName2);
            tvRestaurantType2 = (TextView) v.findViewById(R.id.tvRestaurantType2);
            tvLocation2 = (TextView) v.findViewById(R.id.tvLocation2);

        }
    }

    public PemerintahAdapter(List<Pemerintah> movies, int rowLayout, Context context) {
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
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
//        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.tvRestaurantName2.setText(movies.get(position).isi);
        holder.tvRestaurantType2.setText(movies.get(position).tgl_aduan);
        holder.tvLocation2.setText(movies.get(position).nama_kategori);


        if (position == 0) {
            holder.img_status.setImageResource(R.mipmap.ic_suc);
        } else if (position == 1) {
            holder.img_status.setImageResource(R.mipmap.ic_wait);
        } else if (position == 2) {
            holder.img_status.setImageResource(R.mipmap.ic_fai);
        } else {
            holder.img_status.setImageResource(R.mipmap.ic_suc);
        }

        Glide.with(context)
                .load(movies.get(position).file)
                .centerCrop()
                .placeholder(R.mipmap.ic_account_circle_white_48dps)
                .crossFade()
                .into(holder.ivRestaurant2);

        parallaxViewController.imageParallax(holder.ivRestaurant2);


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}