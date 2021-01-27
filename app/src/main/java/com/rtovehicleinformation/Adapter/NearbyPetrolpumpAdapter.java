package com.rtovehicleinformation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.rtovehicleinformation.Model.NearbyPetrolPump;
import com.rtovehicleinformation.R;
import java.util.ArrayList;

public class NearbyPetrolpumpAdapter extends RecyclerView.Adapter<NearbyPetrolpumpAdapter.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    NearbyPetrolPump nearbyPetrolPump;
    ArrayList<NearbyPetrolPump> nearbyPetrolPumpList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pAddress;
        TextView pDistance;
        ImageView pDistanceIm;
        TextView pName;
        ImageView pTypeicon;
        TextView pTypes;
        LinearLayout placeNearCard;


        public ViewHolder(@NonNull View view) {
            super(view);
            this.pName = view.findViewById(R.id.plName);
            this.pTypes = view.findViewById(R.id.plTypes);
            this.pAddress = view.findViewById(R.id.plAddress);
            this.pDistance = view.findViewById(R.id.plDistance);
            this.pDistanceIm = view.findViewById(R.id.plDistanceIm);
            this.pTypeicon = view.findViewById(R.id.plTypeicon);
            this.placeNearCard = view.findViewById(R.id.placeNearCard);
        }
    }

    public NearbyPetrolpumpAdapter(Context context, ArrayList<NearbyPetrolPump> arrayList) {
        this.context = context;
        this.nearbyPetrolPumpList = arrayList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(this.inflater.inflate(R.layout.item_nearby_petrolpump, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        this.nearbyPetrolPump = this.nearbyPetrolPumpList.get(i);
        viewHolder.pTypes.setText(this.nearbyPetrolPump.getStr3().split(",")[0].replace("[", "").replace("\"", "").replace("_", " "));
        viewHolder.pName.setText(this.nearbyPetrolPump.getName());
        viewHolder.pAddress.setText(this.nearbyPetrolPump.getAddress());
        viewHolder.pDistance.setText(this.nearbyPetrolPump.getDistance());
        viewHolder.pTypeicon.setImageResource(this.nearbyPetrolPump.getIcon());
        viewHolder.placeNearCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NearbyPetrolpumpAdapter.this.mapOpen(i);
            }
        });
    }

    public int getItemCount() {
        return this.nearbyPetrolPumpList.size();
    }

    private void mapOpen(int i) {
        this.nearbyPetrolPump = this.nearbyPetrolPumpList.get(i);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("geo:0,0?q=");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(this.nearbyPetrolPump.getName());
        stringBuilder2.append(this.nearbyPetrolPump.getAddress());
        stringBuilder.append(Uri.encode(stringBuilder2.toString()));
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString()));
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(this.context.getPackageManager()) != null) {
            this.context.startActivity(intent);
        } else {
            Toast.makeText(this.context, "Please Install Google Maps", Toast.LENGTH_LONG).show();
        }
    }

}
