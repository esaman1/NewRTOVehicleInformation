package com.rtovehicleinformation.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.rtovehicleinformation.R;
import com.rtovehicleinformation.utils.Utils;

public class TrafficSignalsAdapter extends RecyclerView.Adapter<TrafficSignalsAdapter.ViewHolder> {

    public static OnItemClickListener onItemClickListener;
    Context context;
    LayoutInflater inflater;
    int[] tv;

    public TrafficSignalsAdapter(Context context, int[] iArr, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.tv = iArr;
        this.inflater = LayoutInflater.from(context);
        TrafficSignalsAdapter.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemClickListener {
        ImageView tvtrafficSignals;
        TextView tvtrafficSignalsText;

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        }

        public ViewHolder(@NonNull final View view) {
            super(view);
            this.tvtrafficSignalsText = view.findViewById(R.id.trafficSignalsText1);
            this.tvtrafficSignals = view.findViewById(R.id.trafficSignalsImage);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    TrafficSignalsAdapter.onItemClickListener.onItemClick(view, ViewHolder.this.getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(this.inflater.inflate(R.layout.traffic_signals_item, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvtrafficSignalsText.setText(this.tv[i]);
        viewHolder.tvtrafficSignals.setImageResource(Utils.symbolImage[i]);
    }

    public int getItemCount() {
        return this.tv.length;
    }
}
