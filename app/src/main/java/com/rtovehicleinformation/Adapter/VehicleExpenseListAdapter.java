package com.rtovehicleinformation.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rtovehicleinformation.Model.VehicleExpenseModel;
import com.rtovehicleinformation.R;

import java.util.ArrayList;

public class VehicleExpenseListAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<VehicleExpenseModel> expenseModels;

    public long getChildId(int i, int i2) {
        return (long) i2;
    }

    public long getGroupId(int i) {
        return (long) i;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    public VehicleExpenseListAdapter(Context context, ArrayList<VehicleExpenseModel> arrayList) {
        this.context = context;
        this.expenseModels = arrayList;
    }

    public Object getChild(int i, int i2) {
        return this.expenseModels.get(i).getExpenseModels().get(i2);
    }

    @SuppressLint({"WrongConstant"})
    public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
        VehicleExpenseModel vehicleExpenseModel = (VehicleExpenseModel) getChild(i, i2);
        if (view == null) {
            view = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.list_row_item, null);
        }
        TextView tvcatName = view.findViewById(R.id.list_catname);
        TextView tvNote = view.findViewById(R.id.list_note);
        TextView tvAmount = view.findViewById(R.id.list_amount);
        ImageView ivCatIcon = view.findViewById(R.id.list_caticon);
        TextView tvPayee = view.findViewById(R.id.list_payee);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Item: ");
        stringBuilder.append(vehicleExpenseModel.getStr3());
        tvPayee.setText(stringBuilder.toString());
        tvcatName.setText(vehicleExpenseModel.getStr2());
        tvNote.setText(vehicleExpenseModel.getStr4());
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(this.context.getString(R.string.unit));
        stringBuilder2.append(" ");
        stringBuilder2.append(vehicleExpenseModel.getStr());
        tvAmount.setText(stringBuilder2.toString());
        ivCatIcon.setImageResource(vehicleExpenseModel.getI());
        return view;
    }

    public int getChildrenCount(int i) {
        return this.expenseModels.get(i).getExpenseModels().size();
    }

    public Object getGroup(int i) {
        return this.expenseModels.get(i);
    }

    public int getGroupCount() {
        return this.expenseModels.size();
    }

    @SuppressLint({"WrongConstant"})
    public View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
        VehicleExpenseModel vehicleExpenseModel = (VehicleExpenseModel) getGroup(i);
        if (view == null) {
            view = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.list_row_group, null);
        }
        TextView textView = view.findViewById(R.id.lblListHeader);
        textView.setTypeface(null, 1);
        textView.setText(vehicleExpenseModel.getStr5());
        return view;
    }

}
