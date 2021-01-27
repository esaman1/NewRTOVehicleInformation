package com.rtovehicleinformation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rtovehicleinformation.Model.PetrolDieselData;
import com.rtovehicleinformation.R;

import java.util.List;

public class CityListAdapter extends BaseAdapter {

    public static List<PetrolDieselData> petrolDieselDataList;
    Context context;

    public long getItemId(int i) {
        return (long) i;
    }

    public CityListAdapter(Context context, List<PetrolDieselData> list) {
        this.context = context;
        petrolDieselDataList = list;
    }

    public int getCount() {
        return petrolDieselDataList.size();
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        view = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.city_item, viewGroup, false);
        TextView tvCityName = view.findViewById(R.id.cityName);
        TextView tvPrices = view.findViewById(R.id.prices);
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Petrol Price : ");
        stringBuilder2.append(petrolDieselDataList.get(i).getPPrice());
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append("\n\nDiesel Price : ");
        stringBuilder.append(petrolDieselDataList.get(i).getDPrice());
        tvPrices.setText(stringBuilder.toString());
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(petrolDieselDataList.get(i).getCityName());
        stringBuilder3.append("\n\n");
        stringBuilder3.append(petrolDieselDataList.get(i).getStateName());
        tvCityName.setText(stringBuilder3.toString());
        return view;
    }

    public void SetSearchData(List<PetrolDieselData> list) {
        petrolDieselDataList = list;
    }
}
