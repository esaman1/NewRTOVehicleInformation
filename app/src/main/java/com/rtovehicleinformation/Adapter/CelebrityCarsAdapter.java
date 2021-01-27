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

public class CelebrityCarsAdapter extends RecyclerView.Adapter<CelebrityCarsAdapter.ViewHolder> {

    Context context;
    int[] imgArray;
    LayoutInflater inflater;
    int[] name;
    int[] number;
    OnItemClickListener onItemClickListener;

    public CelebrityCarsAdapter(Context context, int[] iArr, int[] iArr2, int[] iArr3, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.imgArray = iArr;
        this.name = iArr2;
        this.number = iArr3;
        this.onItemClickListener = onItemClickListener;
        this.inflater = LayoutInflater.from(context);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements android.widget.AdapterView.OnItemClickListener {
        TextView tvcelebName;
        TextView tvcelebNumber;
        ImageView ivceleb_icon;

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        }

        public ViewHolder(@NonNull final View view) {
            super(view);
            this.ivceleb_icon = view.findViewById(R.id.celeb_icon);
            this.tvcelebName = view.findViewById(R.id.celebName);
            this.tvcelebNumber = view.findViewById(R.id.celebNumber);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    CelebrityCarsAdapter.this.onItemClickListener.onItemClick(view, ViewHolder.this.getAdapterPosition());
                }
            });
        }
    }


    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(this.inflater.inflate(R.layout.list_celebs_item, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.ivceleb_icon.setImageResource(this.imgArray[i]);
        viewHolder.tvcelebName.setText(this.name[i]);
        viewHolder.tvcelebNumber.setText(this.number[i]);
    }

    public int getItemCount() {
        return this.name.length;
    }
}
