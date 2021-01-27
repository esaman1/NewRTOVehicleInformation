package com.rtovehicleinformation.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rtovehicleinformation.Model.Appdata;
import com.rtovehicleinformation.R;

import java.util.ArrayList;


public class MoreAppAdapter extends RecyclerView.Adapter<MoreAppAdapter.MyViewHolder> {
    private final ArrayList<Appdata> app_data;
    public Context ctx;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAppName;
        public ImageView ivAppIcon;
        LinearLayout llMain;

        public MyViewHolder(View view) {
            super(view);
            tvAppName = view.findViewById(R.id.tvAppName);
            ivAppIcon = view.findViewById(R.id.ivAppIcon);
            llMain = view.findViewById(R.id.llMain);
        }

    }

    public MoreAppAdapter(Context context, ArrayList<Appdata> app_data) {
        this.ctx = context;
        this.app_data = app_data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.more_app_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (app_data.get(position).getApplication_link() != null && app_data.get(position).getApplication_icon() != null && app_data.get(position).getApplication_name() != null) {

            Glide.with(ctx).load(app_data.get(position).getApplication_icon()).into(holder.ivAppIcon);
            holder.tvAppName.setText(app_data.get(position).getApplication_name());
            holder.tvAppName.setSelected(true);
            holder.llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ctx.startActivity(new Intent(
                                "android.intent.action.VIEW",
                                Uri.parse(app_data.get(position).getApplication_link())));
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(ctx, "You don't have Google Play installed",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return app_data.size();
    }
}