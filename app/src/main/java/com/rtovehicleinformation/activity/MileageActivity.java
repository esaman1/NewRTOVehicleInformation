package com.rtovehicleinformation.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.rtovehicleinformation.Adapter.MileageListAdapter;
import com.rtovehicleinformation.Database.OpenSQLite;
import com.rtovehicleinformation.Model.MileageModel;
import com.rtovehicleinformation.R;
import com.rtovehicleinformation.kprogresshud.KProgressHUD;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rtovehicleinformation.utils.nativeadsmethod.populateNativeAdView;

public class MileageActivity extends AppCompatActivity {

    Activity activity = MileageActivity.this;
    @BindView(R.id.addmileage)
    ImageView ivaddmileage;

    @BindView(R.id.emptyLayout)
    LinearLayout emptyLayout;

    @BindView(R.id.my_recycler_view)
    RecyclerView rvmileage;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_back)
    ImageView ivback;

    MileageListAdapter mileageListAdapter;
    ArrayList<MileageModel> mileageModels = new ArrayList();
    OpenSQLite openSQLite;

    /* AdRequest adRequest;
     AdView adView;*/
    public InterstitialAd mInterstitialAd;
    private UnifiedNativeAd nativeAd;
    public KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mileage);
        ButterKnife.bind(this);
        this.openSQLite = new OpenSQLite(this.getApplicationContext());
        this.mileageModels = this.openSQLite.listmileage();
        this.mileageListAdapter = new MileageListAdapter(this.mileageModels, this, new MileageListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int n) {
                if (!MileageActivity.this.isFinishing()) {
                    final AlertDialog.Builder aleartdialog = new AlertDialog.Builder(MileageActivity.this);
                    aleartdialog.setMessage("Do you want to delete item ?");
                    aleartdialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialogInterface, final int i) {
                            if (MileageActivity.this.openSQLite.dltmileage(MileageActivity.this.mileageModels.get(n).getId()) > 0) {
                                MileageActivity.this.mileageModels.remove(n);
                            }
                            if (MileageActivity.this.mileageModels != null && !MileageActivity.this.mileageModels.isEmpty()) {
                                MileageActivity.this.emptyLayout.setVisibility(View.GONE);
                                MileageActivity.this.rvmileage.setVisibility(View.VISIBLE);
                            } else {
                                MileageActivity.this.rvmileage.setVisibility(View.GONE);
                                MileageActivity.this.emptyLayout.setVisibility(View.VISIBLE);
                            }
                            MileageActivity.this.rvmileage.setLayoutManager(new GridLayoutManager(MileageActivity.this, 1));
                            MileageActivity.this.rvmileage.setAdapter(MileageActivity.this.mileageListAdapter);
                        }
                    });
                    aleartdialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    if (!MileageActivity.this.isFinishing()) {
                        aleartdialog.show();
                    }
                }
            }
        });
        this.rvmileage.setLayoutManager(new GridLayoutManager(this, 1));
        this.rvmileage.setAdapter(this.mileageListAdapter);
        final ArrayList<MileageModel> mileageModels = this.mileageModels;
        if (mileageModels != null && !mileageModels.isEmpty()) {
            this.emptyLayout.setVisibility(View.GONE);
            this.rvmileage.setVisibility(View.VISIBLE);
        } else {
            this.rvmileage.setVisibility(View.GONE);
            this.emptyLayout.setVisibility(View.VISIBLE);
        }
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.ivaddmileage.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                Intent intent = new Intent(MileageActivity.this, MileageEntryActivity.class);
                intent.putExtra("KM_UNIT", MileageActivity.this.getResources().getString(R.string.KM_UNIT));
                intent.putExtra("FULL_UNIT", MileageActivity.this.getResources().getString(R.string.FULL_UNIT));
                intent.putExtra("COST_UNIT", MileageActivity.this.getResources().getString(R.string.COST_UNIT));
                MileageActivity.this.startActivity(intent);
                MileageActivity.this.finish();
                MileageActivity.this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

            }
        });
//        loadBannerAd();
        interstitialAd();
        loadAd();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }

   /* private void loadBannerAd() {
        adView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }*/

    private void interstitialAd() {
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                RequestInterstitial();
                GoBack();
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
                FrameLayout frameLayout =
                        findViewById(R.id.fl_adplaceholder);
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


    private void GoBack() {
        super.onBackPressed();
    }

    public void onBackPressed() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            try {
                hud = KProgressHUD.create(activity).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("Showing Ads").setDetailsLabel("Please Wait...");
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
            GoBack();
        }
    }
}
