package com.rtovehicleinformation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.rtovehicleinformation.Model.RTOListInfo;
import com.rtovehicleinformation.R;
import java.util.List;

public class RTOListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<RTOListInfo> mRTOListInfo;

    public long getItemId(int i) {
        return (long) i;
    }

    public RTOListAdapter(Context context, List<RTOListInfo> list) {
        this.context = context;
        this.mRTOListInfo = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return this.mRTOListInfo.size();
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        view = this.inflater.inflate(R.layout.rto_list_item, viewGroup, false);
        TextView textView = (TextView) view.findViewById(R.id.location);
        ((TextView) view.findViewById(R.id.id)).setText(((RTOListInfo) this.mRTOListInfo.get(i)).getId());
        textView.setText(((RTOListInfo) this.mRTOListInfo.get(i)).getLocation());
        return view;
    }

    public void SetSearchData(List<RTOListInfo> list) {
        this.mRTOListInfo = list;
    }

}
