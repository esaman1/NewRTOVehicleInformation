package com.rtovehicleinformation.activity;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.rtovehicleinformation.Adapter.CelebrityCarsAdapter;
import com.rtovehicleinformation.R;
import com.rtovehicleinformation.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CelebrityCarsActivity extends AppCompatActivity {

    Activity activity = CelebrityCarsActivity.this;
    @BindView(R.id.celeb_cars)
    RecyclerView rvcelebcars;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_back)
    ImageView ivback;

    AdRequest adRequest;
    AdView adView;
    InterstitialAd mInterstitialAd;
    CelebrityCarsAdapter celebrityCarsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebrity_cars);
        ButterKnife.bind(this);
        rvcelebcars.setLayoutManager(new GridLayoutManager(this, 1));
        celebrityCarsAdapter = new CelebrityCarsAdapter(this, Utils.celebsCarImg, Utils.celebsName, Utils.celebsNum, new CelebrityCarsAdapter.OnItemClickListener() {
            public void onItemClick(View view, int i) {
                substring(getResources().getString(Utils.celebsNum[i]));
            }
        });
        rvcelebcars.setAdapter(this.celebrityCarsAdapter);
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loadBannerAd();
        loadInterstitialAd();
    }

    private void loadBannerAd() {
        adView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void loadInterstitialAd() {
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);

            }
        });
    }

    public void substring(String str) {
        String substring = str.substring(0, 6);
        str = str.substring(6);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("firstPart", substring);
        intent.putExtra("lastPart", str);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
