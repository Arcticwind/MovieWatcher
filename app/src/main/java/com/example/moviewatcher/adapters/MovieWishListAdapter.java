package com.example.moviewatcher.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviewatcher.models.Movie;
import com.example.moviewatcher.R;

import java.util.ArrayList;
import java.util.List;

public class MovieWishListAdapter extends RecyclerView.Adapter<MovieWishListAdapter.ViewHolder> {

    private List<Movie> movieList = new ArrayList<>();
    private Context context;
    private OnWishListItemClickListener listener;

    public Movie getWishMovieAt(int position) {
        return movieList.get(position);
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public MovieWishListAdapter(Context context, List<Movie> movieList) {
        this.movieList = movieList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvGenre, tvType, tvYear;
        private ImageView imgPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.li_tv_movie_title);
            tvGenre = itemView.findViewById(R.id.li_tv_genre);
            tvType = itemView.findViewById(R.id.li_tv_movie_type);
            tvYear = itemView.findViewById(R.id.li_tv_movie_year);
            imgPoster = itemView.findViewById(R.id.li_img_movie_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onItemClick(movieList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MovieWishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieWishListAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(movieList.get(position).getTitle());
        holder.tvGenre.setText(movieList.get(position).getGenre());
        holder.tvType.setText(movieList.get(position).getType());
        holder.tvYear.setText(movieList.get(position).getYear());
        Glide.with(context).load(movieList.get(position).getPosterUrl()).into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setListener(OnWishListItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnWishListItemClickListener {
        void onItemClick(Movie movie);
    }
}
