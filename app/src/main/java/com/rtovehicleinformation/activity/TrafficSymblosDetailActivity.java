package com.rtovehicleinformation.activity;

import android.app.Activity;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.rtovehicleinformation.Adapter.TrafficDetailsAdapter;
import com.rtovehicleinformation.Adapter.TrafficDetailsAdapter1;
import com.rtovehicleinformation.R;
import com.rtovehicleinformation.kprogresshud.KProgressHUD;
import com.rtovehicleinformation.utils.Const;
import com.rtovehicleinformation.utils.Pref;
import com.rtovehicleinformation.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrafficSymblosDetailActivity extends AppCompatActivity {

    Activity activity = TrafficSymblosDetailActivity.this;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_back)
    ImageView ivback;

    @BindView(R.id.trafficSymbolsDetails)
    RecyclerView trafficSymbolsDetails;
    Boolean equals;
    String hindiText = "hi";
    TrafficDetailsAdapter trafficDetailsAdapter;
    TrafficDetailsAdapter1 trafficDetailsAdapter1;

    AdRequest adRequest;
    AdView adView;
    InterstitialAd mInterstitialAd;
    public KProgressHUD hud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_symblos_detail);
        ButterKnife.bind(this);
        this.hindiText = Pref.getStringValue(this, "lan", "en");
        Const.getSet(this, this.hindiText);
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.equals = this.hindiText.equals("hi");
        loadBannerAd();
        loadInterstitialAd();
        switch (getIntent().getExtras().getInt("POST")) {
            case 0:
                this.tvTitle.setText(this.equals.booleanValue() ? "अनिवार्य" : "Mandatory");
                this.trafficSymbolsDetails.setLayoutManager(new GridLayoutManager(this, 2));
                this.trafficDetailsAdapter = new TrafficDetailsAdapter(this, Utils.mandatoryName, Utils.mandatoryImage);
                this.trafficSymbolsDetails.setAdapter(this.trafficDetailsAdapter);
                break;
            case 1:
                this.tvTitle.setText(this.equals.booleanValue() ? "Traffic Light सिग्नल" : "Traffic Light Signals");
                this.trafficSymbolsDetails.setLayoutManager(new GridLayoutManager(this, 1));
                this.trafficDetailsAdapter1 = new TrafficDetailsAdapter1(this, Utils.trafficLightSignals1, Utils.trafficLightSignals2, Utils.trafficLightSignalsImg);
                this.trafficSymbolsDetails.setAdapter(this.trafficDetailsAdapter1);
                break;
            case 2:
                this.tvTitle.setText(this.equals.booleanValue() ? "चेतावनी देनेवाला" : "Cautionary");
                this.trafficSymbolsDetails.setLayoutManager(new GridLayoutManager(this, 2));
                this.trafficDetailsAdapter = new TrafficDetailsAdapter(this, Utils.cautionaryName, Utils.cautionaryImage);
                this.trafficSymbolsDetails.setAdapter(this.trafficDetailsAdapter);
                break;
            case 3:
                this.tvTitle.setText(this.equals.booleanValue() ? "ज्ञान बढ़ाने वाला" : "Informatory");
                this.trafficSymbolsDetails.setLayoutManager(new GridLayoutManager(this, 2));
                this.trafficDetailsAdapter = new TrafficDetailsAdapter(this, Utils.informatoryName, Utils.informatoryImage);
                this.trafficSymbolsDetails.setAdapter(this.trafficDetailsAdapter);
                break;
            case 4:
                this.tvTitle.setText(this.equals.booleanValue() ? "सड़क" : "Road");
                this.trafficSymbolsDetails.setLayoutManager(new GridLayoutManager(this, 2));
                this.trafficDetailsAdapter = new TrafficDetailsAdapter(this, Utils.roadName, Utils.roadImage);
                this.trafficSymbolsDetails.setAdapter(this.trafficDetailsAdapter);
                break;
            case 5:
                this.tvTitle.setText(this.equals.booleanValue() ? "नियम" : "Rules");
                this.trafficSymbolsDetails.setLayoutManager(new GridLayoutManager(this, 2));
                this.trafficDetailsAdapter = new TrafficDetailsAdapter(this, Utils.rulesName, Utils.rulesImage);
                this.trafficSymbolsDetails.setAdapter(this.trafficDetailsAdapter);
                break;
            case 6:
                this.tvTitle.setText(this.equals.booleanValue() ? "Traffic पुलिस" : "Traffic Police");
                this.trafficSymbolsDetails.setLayoutManager(new GridLayoutManager(this, 2));
                this.trafficDetailsAdapter = new TrafficDetailsAdapter(this, Utils.trafficPoliceName, Utils.trafficPoliceImage);
                this.trafficSymbolsDetails.setAdapter(this.trafficDetailsAdapter);
                break;
        }


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
                RequestInterstitial();
                GoTOBack();
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
        RequestInterstitial();
    }

    public void RequestInterstitial() {
        try {
            if (mInterstitialAd != null) {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            } else {
                mInterstitialAd = new InterstitialAd(activity);
                mInterstitialAd.setAdUnitId(getString(R.string.interstitial));
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    private void GoTOBack(){
        finish();
    }

    public void onBackPressed() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            try {
                hud = KProgressHUD.create(activity).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("Showing Ads")
                        .setDetailsLabel("Please Wait...");
                hud.show();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NullPointerException e2) {
                e2.printStackTrace();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        hud.dismiss();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();

                    } catch (NullPointerException e2) {
                        e2.printStackTrace();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            }, 2000);
        } else {
            GoTOBack();
        }
    }
}
