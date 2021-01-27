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

public class TrafficDetailsAdapter1 extends RecyclerView.Adapter<TrafficDetailsAdapter1.ViewHolder> {
    Context context;
    int[] img;
    LayoutInflater inflater;
    int[] txt1;
    int[] txt2;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivtrafficDetails;
        TextView trafficDetails1;
        TextView tvtrafficDetails2;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.trafficDetails1 = view.findViewById(R.id.trafficSignalsText1);
            this.tvtrafficDetails2 = view.findViewById(R.id.trafficSignalsText2);
            this.ivtrafficDetails = view.findViewById(R.id.trafficSignalsImage);
        }
    }

    public TrafficDetailsAdapter1(Context context, int[] iArr, int[] iArr2, int[] iArr3) {
        this.context = context;
        this.txt1 = iArr;
        this.txt2 = iArr2;
        this.img = iArr3;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(this.inflater.inflate(R.layout.traffic_signals_item1, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.trafficDetails1.setText(this.txt1[i]);
        viewHolder.tvtrafficDetails2.setText(this.txt2[i]);
        viewHolder.ivtrafficDetails.setImageResource(this.img[i]);
    }

    public int getItemCount() {
        return this.txt1.length;
    }

}
