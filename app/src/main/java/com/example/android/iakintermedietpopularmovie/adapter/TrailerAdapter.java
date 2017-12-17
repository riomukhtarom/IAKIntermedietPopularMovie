package com.example.android.iakintermedietpopularmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.iakintermedietpopularmovie.R;
import com.example.android.iakintermedietpopularmovie.model.Trailer;
import com.example.android.iakintermedietpopularmovie.utils.RecyclerViewClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LENOVO on 17/12/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    private Context context;
    private List<Trailer> trailerList;
    private RecyclerViewClickListener recyclerViewClickListener;

    public TrailerAdapter(Context context, List<Trailer> trailerList, RecyclerViewClickListener recyclerViewClickListener) {
        this.context = context;
        this.trailerList = trailerList;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.trailer = trailerList.get(position);
        holder.itemView.setOnClickListener(view-> {
            int itemPosition = holder.getAdapterPosition();
            recyclerViewClickListener.onItemClicked(itemPosition);
        });
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_trailer_item) TextView tvMovieTrailer;
        Trailer trailer;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
