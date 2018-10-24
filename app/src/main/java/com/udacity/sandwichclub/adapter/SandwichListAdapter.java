package com.udacity.sandwichclub.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import java.util.List;

public class SandwichListAdapter extends RecyclerView.Adapter<SandwichListAdapter.ViewHolder> {

    private Context mContext;
    private List<Sandwich> sandwiches;
    private SandwichClickListener listener;

    public SandwichListAdapter(Context context, List<Sandwich> sandwiches, SandwichClickListener listener) {
        this.mContext = context;
        this.sandwiches = sandwiches;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.sandwich_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Picasso.with(mContext).load(sandwiches.get(i).getImage())
                .error(R.drawable.ic_error)
                .placeholder(R.drawable.ic_loading)
                .into(viewHolder.sandwichImage);
        viewHolder.sandwichName.setText(sandwiches.get(i).getMainName());
    }

    @Override
    public int getItemCount() {
        return sandwiches == null ? 0 : sandwiches.size();
    }

    public interface SandwichClickListener {
        void onClick(Sandwich sandwich, ImageView imageView);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView sandwichName;
        ImageView sandwichImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            sandwichName = itemView.findViewById(R.id.sandwich_name);
            sandwichImage = itemView.findViewById(R.id.sandwich_image);
        }

        @Override
        public void onClick(View v) {
            if (listener != null)
                listener.onClick(sandwiches.get(getAdapterPosition()), sandwichImage);
        }
    }
}
