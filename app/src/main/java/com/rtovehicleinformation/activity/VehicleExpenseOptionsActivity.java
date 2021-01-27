package com.rtovehicleinformation.activity;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.rtovehicleinformation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VehicleExpenseOptionsActivity extends AppCompatActivity {

    Activity activity = VehicleExpenseOptionsActivity.this;
    @BindView(R.id.accessories)
    CardView accessories;
    @BindView(R.id.carfuel)
    CardView carfuel;
    @BindView(R.id.cleanlines)
    CardView cleanlines;
    @BindView(R.id.maintenance)
    CardView maintenance;
    @BindView(R.id.otherexpense)
    CardView otherexpense;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_back)
    ImageView ivback;

    AdRequest adRequest;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_expense_options);
        ButterKnife.bind(this);
        loadBannerAd();
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.accessories.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(VehicleExpenseOptionsActivity.this, ExpenceFormActivity.class);
                intent.putExtra("categoryname", "Accessories & Tuning");
                intent.putExtra("categoryimg", R.drawable.accessories_tuning_ve);
                intent.putExtra("btn", "SAVE");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }
        });
        this.cleanlines.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(VehicleExpenseOptionsActivity.this, ExpenceFormActivity.class);
                intent.putExtra("categoryname", "Cleanlines & comfort");
                intent.putExtra("categoryimg", R.drawable.cleaning_tuning_ve);
                intent.putExtra("btn", "SAVE");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }
        });
        this.carfuel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(VehicleExpenseOptionsActivity.this, ExpenceFormActivity.class);
                intent.putExtra("categoryname", "Car Fuel");
                intent.putExtra("categoryimg", R.drawable.petrol_pump_ve);
                intent.putExtra("btn", "SAVE");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }
        });
        this.maintenance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(VehicleExpenseOptionsActivity.this, ExpenceFormActivity.class);
                intent.putExtra("categoryname", "Maintenance");
                intent.putExtra("categoryimg", R.drawable.maintenance_ve);
                intent.putExtra("btn", "SAVE");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }
        });
        this.otherexpense.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(VehicleExpenseOptionsActivity.this, ExpenceFormActivity.class);
                intent.putExtra("categoryname", "Other expense");
                intent.putExtra("categoryimg", R.drawable.wallet_ve);
                intent.putExtra("btn", "SAVE");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }
        });

    }

    private void loadBannerAd() {
        adView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }


    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        return true;
    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), VehicleExpenseActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}
