package com.rtovehicleinformation.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rtovehicleinformation.R;

public class TrendingListAdapter extends RecyclerView.Adapter<TrendingListAdapter.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    String[] name;
    String[] number;
    OnItemClickListener onItemClickListener;

    public TrendingListAdapter(Context context, String[] strArr, String[] strArr2, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.name = strArr;
        this.number = strArr2;
        this.onItemClickListener = onItemClickListener;
        this.inflater = LayoutInflater.from(context);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemClickListener {
        TextView tvcelebName;
        TextView tvcelebNumber;
        ImageView iv_celeb_icon;
        LinearLayout layout_trendingi_item;

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        }

        public ViewHolder(@NonNull final View view) {
            super(view);
            this.iv_celeb_icon = view.findViewById(R.id.celeb_icon);
            this.tvcelebName = view.findViewById(R.id.celebName);
            this.tvcelebNumber = view.findViewById(R.id.celebNumber);
            this.layout_trendingi_item = view.findViewById(R.id.trendingi_item_layout);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    TrendingListAdapter.this.onItemClickListener.onItemClick(view, ViewHolder.this.getAdapterPosition());
                }
            });
        }
    }



    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(this.inflater.inflate(R.layout.list_trending_item, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvcelebName.setText(this.number[i]);
        viewHolder.tvcelebNumber.setText(this.name[i]);
    }

    public int getItemCount() {
        return this.name.length;
    }

}
