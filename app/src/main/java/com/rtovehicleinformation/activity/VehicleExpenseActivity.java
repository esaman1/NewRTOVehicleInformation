package com.rtovehicleinformation.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.rtovehicleinformation.Adapter.VehicleExpenseListAdapter;
import com.rtovehicleinformation.Database.OpenSQLite;
import com.rtovehicleinformation.Model.VehicleExpenseModel;
import com.rtovehicleinformation.R;
import com.rtovehicleinformation.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.rtovehicleinformation.utils.nativeadsmethod.populateNativeAdView;

public class VehicleExpenseActivity extends AppCompatActivity {
    Activity activity = VehicleExpenseActivity.this;

    @BindView(R.id.addexpense)
    ImageView ivaddexpense;

    @BindView(R.id.emptyLayout)
    LinearLayout emptyLayout;

    @BindView(R.id.ivExp)
    ExpandableListView listView;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    @BindView(R.id.tvTotal)
    TextView tvTotal;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_back)
    ImageView ivback;

    OpenSQLite openSQLite;
    VehicleExpenseListAdapter expenseListAdapter;
    ArrayList<VehicleExpenseModel> expenseModels = new ArrayList();

    /* AdRequest adRequest;
      AdView adView;*/

    private UnifiedNativeAd nativeAd;
    public InterstitialAd mInterstitialAd;
    public KProgressHUD hud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_expense);
        ButterKnife.bind(this);
        this.openSQLite = new OpenSQLite(getApplicationContext());
        tAmunt();
//        loadBannerAd();
        interstitialAd();
        loadAd();
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.listView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                VehicleExpenseActivity.this.listView.setIndicatorBounds(VehicleExpenseActivity.this.listView.getMeasuredWidth() - 80, VehicleExpenseActivity.this.listView.getMeasuredWidth());
            }
        });
        this.ivaddexpense.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(VehicleExpenseActivity.this, VehicleExpenseOptionsActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }
        });
        this.listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
                return false;
            }
        });
        this.listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long j) {
                try {
                    final VehicleExpenseModel vehicleExpenseModel = VehicleExpenseActivity.this.expenseModels.get(i).getExpenseModels().get(i2);
                    view = ((LayoutInflater) VehicleExpenseActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_menu, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(VehicleExpenseActivity.this);
                    builder.setView(view);
                    builder.setCancelable(true);
                    final AlertDialog create = builder.create();
                    create.show();
                    Button button = view.findViewById(R.id.btnDeleteBill);
                    view.findViewById(R.id.btnEditBill).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            create.dismiss();
                            Intent intent = new Intent(VehicleExpenseActivity.this, ExpenceFormActivity.class);
                            intent.putExtra("BID", vehicleExpenseModel.getI1());
                            intent.putExtra("BPAYEE", vehicleExpenseModel.getStr3());
                            intent.putExtra("BAMOUNT", vehicleExpenseModel.getStr());
                            intent.putExtra("BCATNAME", vehicleExpenseModel.getStr2());
                            intent.putExtra("BCATICON", vehicleExpenseModel.getI());
                            intent.putExtra("BNOTE", vehicleExpenseModel.getStr4());
                            intent.putExtra("BDUEDATE", vehicleExpenseModel.getStr5());
                            intent.putExtra("btn", "UPDATE");
                            VehicleExpenseActivity.this.startActivity(intent);
                            VehicleExpenseActivity.this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                        }
                    });
                    button.setOnClickListener(new View.OnClickListener() {


                        public void onClick(View view) {
                            create.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(VehicleExpenseActivity.this);
                            builder.setMessage("Do you want to delete item ?");
                            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (VehicleExpenseActivity.this.openSQLite.dlt(vehicleExpenseModel.getI1()) > 0) {
                                        VehicleExpenseActivity.this.tAmunt();
                                    }
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                        }
                    });
                } catch (Exception unused) {
                    unused.printStackTrace();
                }
                return false;
            }
        });

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
                FrameLayout frameLayout = findViewById(R.id.fl_adplaceholder);
                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
                        .inflate(R.layout.layout_native_advance_small, null);
                populateNativeAdView(unifiedNativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }

        });
        VideoOptions videoOptions = new VideoOptions.Builder().build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();
        builder.withNativeAdOptions(adOptions);
        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }

    private void GoBack(){
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

    public void onResume() {
        super.onResume();
        tAmunt();
    }

    @SuppressLint({"WrongConstant"})
    private void tAmunt() {
        String rawQuery = this.openSQLite.rawQuery();
        int i = 0;
        if (rawQuery != null) {
            this.emptyLayout.setVisibility(8);
            this.tvTotal.setVisibility(0);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Total Amount: ");
            stringBuilder.append(getString(R.string.unit));
            stringBuilder.append(" ");
            stringBuilder.append(rawQuery);
            tvTotal.setText(stringBuilder.toString());
        } else {
            this.tvTotal.setVisibility(8);
            this.emptyLayout.setVisibility(0);
        }
        this.expenseModels = this.openSQLite.list();
        this.expenseListAdapter = new VehicleExpenseListAdapter(this, this.expenseModels);
        this.listView.setAdapter(this.expenseListAdapter);
        while (i < this.expenseListAdapter.getGroupCount()) {
            try {
                this.listView.expandGroup(i);
                i++;
            } catch (Exception unused) {
                return;
            }
        }
    }
}
