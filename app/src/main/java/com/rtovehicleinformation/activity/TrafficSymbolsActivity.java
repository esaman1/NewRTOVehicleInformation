package com.rtovehicleinformation.activity;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.rtovehicleinformation.Adapter.TrafficSignalsAdapter;
import com.rtovehicleinformation.R;
import com.rtovehicleinformation.utils.Const;
import com.rtovehicleinformation.utils.Pref;
import com.rtovehicleinformation.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rtovehicleinformation.utils.nativeadsmethod.populateNativeAdView;


public class TrafficSymbolsActivity extends AppCompatActivity {
    Activity activity = TrafficSymbolsActivity.this;
    String hindiText = "hi";
    TrafficSignalsAdapter trafficDetailsAdapter;
    int numberOfColumns = 2;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.trafficSymbols)
    RecyclerView rvtrafficSymbols;

    @BindView(R.id.iv_back)
    ImageView ivback;

    /* AdRequest adRequest;
     AdView adView;*/
    private UnifiedNativeAd nativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_symbols);
        ButterKnife.bind(this);
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.hindiText = Pref.getStringValue(this, "lan", "en");
        Const.getSet(this, this.hindiText);
        this.rvtrafficSymbols.setLayoutManager(new GridLayoutManager(this, this.numberOfColumns));
        this.trafficDetailsAdapter = new TrafficSignalsAdapter(this, Utils.symbolName, new TrafficSignalsAdapter.OnItemClickListener() {
            public void onItemClick(View view, int i) {
                Intent intent = new Intent(TrafficSymbolsActivity.this, TrafficSymblosDetailActivity.class);
                intent.putExtra("POST", i);
                startActivityForResult(intent, 101);
            }
        });
        this.rvtrafficSymbols.setAdapter(this.trafficDetailsAdapter);
//        loadBannerAd();
        loadAd();
    }

  /*  private void loadBannerAd() {
        adView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }*/

    private void loadAd() {
        AdLoader.Builder builder = new AdLoader.Builder(this, getResources().getString(R.string.admob_native));
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                findViewById(R.id.tvLoadAds).setVisibility(View.GONE);
                nativeAd = unifiedNativeAd;
                FrameLayout frameLayout = findViewById(R.id.fl_adplaceholder);
                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
                        .inflate(R.layout.layout_native_advance_small, null);
                populateNativeAdView(unifiedNativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }

        });
        VideoOptions videoOptions = new VideoOptions.Builder().build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
        builder.withNativeAdOptions(adOptions);
        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }


    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 101 && i == -1) {
            this.hindiText = Pref.getStringValue(this, "lan", "en");
            Const.getSet(this, this.hindiText);
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hi_en_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
        } else if (itemId == R.id.action_language) {
            if (this.hindiText.equals("hi")) {
                Const.getSet(this, "en");
                Pref.setStringValue(this, "lan", "en");
            } else {
                Const.getSet(this, "hi");
                Pref.setStringValue(this, "lan", "hi");
            }
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

}
