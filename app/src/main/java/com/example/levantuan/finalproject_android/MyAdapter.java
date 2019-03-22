package com.example.levantuan.finalproject_android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by robin on 2018-07-30.
 */


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // Constructor
    public MyAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // Tell Android which XML file should be used as the
    // UI for each row in the RecyclerView
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // Put data in each row of the RecyclerView
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String movie = mData.get(position);
        holder.myTextView.setText(movie);
    }

    // How many rows do you need?
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // UI Nonsense - stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.textViewMovies);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // OPTIONAL: Convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }

    // Bind clicks
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // REQUIRED: Need this function for dealing with clicks
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
