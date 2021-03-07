package com.x.metiz.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.x.metiz.HotelModel;
import com.x.metiz.R;

import java.util.ArrayList;

public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.ViewHolder> {

    ArrayList<HotelModel.HotelData.Hotel.PlaceData> arrPlaceData=new ArrayList<>();
    Context context;

    public HotelListAdapter(Context context, ArrayList<HotelModel.HotelData.Hotel.PlaceData> arrPlaceData) {
        this.context=context;
        this.arrPlaceData=arrPlaceData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(arrPlaceData.get(position).getThumbnail_url()).into(holder.imgHotel);
        holder.tvHotelName.setText(arrPlaceData.get(position).getName());
        holder.tvHotelAddress.setText(arrPlaceData.get(position).getName_suffix());
    }

    @Override
    public int getItemCount() {
        return arrPlaceData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHotel;
        TextView tvHotelName, tvHotelAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHotel=itemView.findViewById(R.id.imgHotel);
            tvHotelName=itemView.findViewById(R.id.tvHotelName);
            tvHotelAddress=itemView.findViewById(R.id.tvHotelAddress);
        }
    }
}
