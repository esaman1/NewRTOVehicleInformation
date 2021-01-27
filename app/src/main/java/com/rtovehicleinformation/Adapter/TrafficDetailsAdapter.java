package com.rtovehicleinformation.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rtovehicleinformation.R;

public class TrafficDetailsAdapter extends RecyclerView.Adapter<TrafficDetailsAdapter.ViewHolder> {
    Context context;
    int[] img;
    LayoutInflater inflater;
    int[] txt;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivtrafficDetails;
        TextView tvtrafficDetails;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.tvtrafficDetails = view.findViewById(R.id.trafficDetailsText);
            this.ivtrafficDetails = view.findViewById(R.id.trafficDetailsImage);
        }
    }

    public TrafficDetailsAdapter(Context context, int[] iArr, int[] iArr2) {
        this.context = context;
        this.txt = iArr;
        this.img = iArr2;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(this.inflater.inflate(R.layout.traffic_details_item, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvtrafficDetails.setText(this.txt[i]);
        viewHolder.ivtrafficDetails.setImageResource(this.img[i]);
    }

    public int getItemCount() {
        return this.txt.length;
    }

}
