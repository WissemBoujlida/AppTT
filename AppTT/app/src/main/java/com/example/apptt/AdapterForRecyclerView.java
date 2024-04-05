package com.example.apptt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterForRecyclerView extends RecyclerView.Adapter<AdapterForRecyclerView.RecyclerViewHolder> {

    Context context;
    ArrayList<Offer> offersList;
    public AdapterForRecyclerView(Context context, ArrayList<Offer> offersList) {
        this.context = context;
        this.offersList = offersList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.recycler_row, parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tv_offerName.setText(offersList.get(position).getName());
        holder.tv_offerBriefDescription.setText(offersList.get(position).getBriefDescription());
        holder.iv_offerImage.setImageResource(offersList.get(position).getImage());

        holder.offersLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, OfferActivity.class);
            intent. putExtra("description", offersList.get(position).getDescription());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tv_offerName, tv_offerBriefDescription;
        ImageView iv_offerImage;
        ConstraintLayout offersLayout;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_offerName = itemView.findViewById(R.id.tv_offerName);
            tv_offerBriefDescription = itemView.findViewById(R.id.tv_offerBriefDescription);
            iv_offerImage = itemView.findViewById(R.id.iv_offer);
            offersLayout = itemView.findViewById(R.id.offersLayout);
        }
    }
}
