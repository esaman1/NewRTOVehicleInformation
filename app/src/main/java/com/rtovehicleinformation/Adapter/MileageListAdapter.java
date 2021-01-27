package com.rtovehicleinformation.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rtovehicleinformation.Model.MileageModel;
import com.rtovehicleinformation.R;
import java.text.DecimalFormat;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MileageListAdapter extends RecyclerView.Adapter<MileageListAdapter.ViewHolder> {

    OnItemClickListener clickListener;
    Context context;
    LayoutInflater inflater;
    ArrayList<MileageModel> mileageModels;

    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.LyoutClick)
        LinearLayout LyoutClick;
        @BindView(R.id.from_kms)
        TextView tvfrom_kms;
        @BindView(R.id.mileage)
        TextView tvmileage;
        @BindView(R.id.noted_date)
        TextView tvnoted_date;
        @BindView(R.id.per_kms)
        TextView tvper_kms;
        @BindView(R.id.price_fuel)
        TextView tvprice_fuel;
        @BindView(R.id.view_log_row_delete_img)
        ImageView tv_row_delete_img;
        @BindView(R.id.to_kms)
        TextView tv_to_kms;

        public ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public MileageListAdapter(ArrayList<MileageModel> arrayList, Context context, OnItemClickListener onItemClickListener) {
        this.clickListener = onItemClickListener;
        this.mileageModels = arrayList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(this.inflater.inflate(R.layout.list_mileage_item, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        MileageModel mileageModel = this.mileageModels.get(i);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You spent ");
        stringBuilder.append(mileageModel.getPrice());
        stringBuilder.append(" ");
        stringBuilder.append(this.context.getString(R.string.COST_UNIT));
        stringBuilder.append(" for ");
        stringBuilder.append(mileageModel.getFuel());
        stringBuilder.append(" liter fuel.");
        viewHolder.tvprice_fuel.setText(stringBuilder.toString());
        viewHolder.tvfrom_kms.setText(mileageModel.getFromKms());
        viewHolder.tv_to_kms.setText(mileageModel.getToKms());
        stringBuilder = new StringBuilder();
        stringBuilder.append(new DecimalFormat("##.##").format((double) Float.parseFloat(mileageModel.getMileage())));
        stringBuilder.append(" ");
        stringBuilder.append(this.context.getString(R.string.KM_UNIT));
        viewHolder.tvmileage.setText(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(new DecimalFormat("##.##").format((double) Float.parseFloat(mileageModel.getPerKms())));
        stringBuilder.append(" ");
        stringBuilder.append(this.context.getString(R.string.COST_UNIT_KM));
        viewHolder.tvper_kms.setText(stringBuilder.toString());
        viewHolder.tvnoted_date.setText(mileageModel.getNotedDate());
        viewHolder.LyoutClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MileageListAdapter.this.clickListener.onItemClick(i);
            }
        });
    }

    public int getItemCount() {
        return this.mileageModels.size();
    }
}
