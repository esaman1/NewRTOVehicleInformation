package com.rtovehicleinformation.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.cardview.widget.CardView;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.rtovehicleinformation.BuildConfig;
import com.rtovehicleinformation.R;
import com.rtovehicleinformation.kprogresshud.KProgressHUD;
import com.rtovehicleinformation.preferences.EPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rtovehicleinformation.utils.nativeadsmethod.populateNativeAdView;
import static com.rtovehicleinformation.utils.nativeadsmethod.populateUnifiedNativeAdViewDialog;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Activity activity = MainActivity.this;

    String strDate;

    @BindView(R.id.cv_owner_info)
    CardView reg;

    @BindView(R.id.cv_trafficSymbols)
    CardView trafficSymbols;

    @BindView(R.id.cv_trending)
    CardView trending;

    @BindView(R.id.cv_vehicleExpense)
    CardView vehicleExpense;

    @BindView(R.id.cv_vehicleMileage)
    CardView vehicleMileage;

    @BindView(R.id.iv_nav_drawer)
    ImageView ivNavDrawer;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.tv_version)
    TextView tvVersionCode;

    public ActionBarDrawerToggle mDrawerToggle;

    public KProgressHUD hud;
    public InterstitialAd mInterstitialAd;
    private UnifiedNativeAd nativeAd;
    int id;

    EPreferences ePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ePreferences = EPreferences.getInstance(this);
        ButterKnife.bind(this);
        interstitialAd();
        CallNativeAds();
        GetAppVersion();
        NavigationView navigationView = findViewById(R.id.nav_view);
        ivNavDrawer.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        strDate = new SimpleDateFormat("ddMMMyyyy").format(Calendar.getInstance().getTime());
        init();
    }

    private void init() {
        vehicleExpense.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
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
                                id = 101;
                                mInterstitialAd.show();
                            }
                        }
                    }, 2000);
                } else {
                    VehicelExpanse();
                }

            }
        });

        vehicleMileage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
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
                                id = 102;
                                mInterstitialAd.show();
                            }
                        }
                    }, 2000);
                } else {
                    VehicelMileage();
                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
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
                                id = 100;
                                mInterstitialAd.show();
                            }
                        }
                    }, 2000);
                } else {
                    OwnerInfo();
                }


            }
        });

        trafficSymbols.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
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
                                id = 103;
                                mInterstitialAd.show();
                            }
                        }
                    }, 2000);
                } else {
                    TrafficSymbole();
                }
            }
        });

        trending.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
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
                                id = 104;
                                mInterstitialAd.show();
                            }
                        }
                    }, 2000);
                } else {
                    Tranding();
                }
            }
        });
    }

    private void GetAppVersion() {
        PackageInfo info = null;
        PackageManager manager = activity.getPackageManager();
        try {
            info = manager.getPackageInfo(activity.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info != null) {
            tvVersionCode.setText("v" + info.versionName);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        SelectedNavItem(menuItem.getItemId());
        return true;
    }

    private void SelectedNavItem(int itemId) {
        switch (itemId) {
            case R.id.nav_rate_us:
                RateApp();
                break;
            case R.id.nav_invite:
                ShareApp();
                break;
//            case R.id.nav_more:
//                startActivity(new Intent(activity,));
//                break;
            case R.id.nav_feddback:
                Feedback();
                break;
            case R.id.nav_privacy:
                Intent intent1 = new Intent("android.intent.action.VIEW");
                intent1.setData(Uri.parse(getResources().getString(R.string.privacy_link)));
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void Feedback() {
        Intent intent2 = new Intent("android.intent.action.SENDTO", Uri.fromParts("mailto", getResources().getString(R.string.feedback_email), null));
        intent2.putExtra("android.intent.extra.SUBJECT", "Feedback");
        intent2.putExtra("android.intent.extra.TEXT", "Write your feedback here");
        startActivity(Intent.createChooser(intent2, "Send email..."));
    }

    private void RateApp() {
        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        }
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        }
    }

    private void ShareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Wavy Music");
            String shareMessage = "\nGet free RTO Vehicle Information at here:";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + activity.getPackageName() + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void OwnerInfo() {
        startActivity(new Intent(activity, VehicleDetailActivity.class));
//        startActivity(new Intent(activity, HomeActivity.class));
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void VehicelExpanse() {
        startActivity(new Intent(activity, VehicleExpenseActivity.class));
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void VehicelMileage() {
        startActivity(new Intent(activity, MileageActivity.class));
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void TrafficSymbole() {
        startActivity(new Intent(activity, TrafficSymbolsActivity.class));
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void Tranding() {
        startActivity(new Intent(activity, TrendingActivity.class));
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void CallNativeAds() {
        AdLoader.Builder builder = new AdLoader.Builder(this, getResources().getString(R.string.admob_native));
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                FrameLayout frameLayout = findViewById(R.id.fl_adplaceholder);
                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
                        .inflate(R.layout.layout_native_advance_small, null);
                populateNativeAdView(unifiedNativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }
        });
        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private void interstitialAd() {
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                RequestInterstitial();
                switch (id) {
                    case 100:
                        OwnerInfo();
                        break;
                    case 101:
                        VehicelExpanse();
                        break;
                    case 102:
                        VehicelMileage();
                        break;
                    case 103:
                        TrafficSymbole();
                        break;
                    case 104:
                        Tranding();
                        break;
                }
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
        getMenuInflater().inflate(R.menu.homescreen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        } else if (itemId == R.id.share) {
            try {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.SUBJECT", "RTO Information");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("\nLet me recommend you this application\n\n");
                stringBuilder.append("https://play.google.com/store/apps/details?id=");
                stringBuilder.append(BuildConfig.APPLICATION_ID);
                stringBuilder.append("\n\n");
                intent.putExtra("android.intent.extra.TEXT", stringBuilder.toString());
                startActivity(Intent.createChooser(intent, "choose one"));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            } catch (Exception unused) {
                unused.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void RateDialog() {
        final boolean[] isRate = {false, false};
        final Dialog dialog = new Dialog(activity);
        final ImageView ivStar1, ivStar2, ivStar3, ivStar4, ivStar5;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setWindowAnimations(R.style.PauseDialogAnimation1);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.setCanceledOnTouchOutside(false);
        AdLoader.Builder builder = new AdLoader.Builder(this, getResources().getString(R.string.admob_native));
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                FrameLayout frameLayout = dialog.findViewById(R.id.fl_adplaceholder);
                dialog.findViewById(R.id.tvLoadingAds).setVisibility(View.GONE);
                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater().inflate(R.layout.ad_unified_small, null);
                populateUnifiedNativeAdViewDialog(unifiedNativeAd, adView);
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

        ivStar1 = dialog.findViewById(R.id.ivStar1);
        ivStar2 = dialog.findViewById(R.id.ivStar2);
        ivStar3 = dialog.findViewById(R.id.ivStar3);
        ivStar4 = dialog.findViewById(R.id.ivStar4);
        ivStar5 = dialog.findViewById(R.id.ivStar5);
        ivStar1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ivStar1.setImageResource(R.drawable.star_fill);
                ivStar2.setImageResource(R.drawable.star_empty);
                ivStar3.setImageResource(R.drawable.star_empty);
                ivStar4.setImageResource(R.drawable.star_empty);
                ivStar5.setImageResource(R.drawable.star_empty);
                isRate[0] = false;
                isRate[1] = true;
                return false;
            }
        });
        ivStar2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ivStar1.setImageResource(R.drawable.star_fill);
                ivStar2.setImageResource(R.drawable.star_fill);
                ivStar3.setImageResource(R.drawable.star_empty);
                ivStar4.setImageResource(R.drawable.star_empty);
                ivStar5.setImageResource(R.drawable.star_empty);
                isRate[0] = false;
                isRate[1] = true;
                return false;
            }
        });
        ivStar3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ivStar1.setImageResource(R.drawable.star_fill);
                ivStar2.setImageResource(R.drawable.star_fill);
                ivStar3.setImageResource(R.drawable.star_fill);
                ivStar4.setImageResource(R.drawable.star_empty);
                ivStar5.setImageResource(R.drawable.star_empty);
                isRate[0] = false;
                isRate[1] = true;
                return false;
            }
        });
        ivStar4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ivStar1.setImageResource(R.drawable.star_fill);
                ivStar2.setImageResource(R.drawable.star_fill);
                ivStar3.setImageResource(R.drawable.star_fill);
                ivStar4.setImageResource(R.drawable.star_fill);
                ivStar5.setImageResource(R.drawable.star_empty);
                isRate[0] = true;
                isRate[1] = true;
                return false;
            }
        });
        ivStar5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ivStar1.setImageResource(R.drawable.star_fill);
                ivStar2.setImageResource(R.drawable.star_fill);
                ivStar3.setImageResource(R.drawable.star_fill);
                ivStar4.setImageResource(R.drawable.star_fill);
                ivStar5.setImageResource(R.drawable.star_fill);
                isRate[0] = true;
                isRate[1] = true;
                return false;
            }
        });
        dialog.findViewById(R.id.btnLater).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                System.exit(0);
            }
        });
        dialog.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRate[1]) {
                    ePreferences.putBoolean("pref_key_rate", true);
                    dialog.dismiss();
                    if (isRate[0]) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                        } catch (ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getApplicationContext().getPackageName())));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
                    }
                    System.exit(0);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Select Your Review Star", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    public void ExitDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setWindowAnimations(R.style.PauseDialogAnimation1);
        dialog.setContentView(R.layout.dialog_layout_exit);
        dialog.setCanceledOnTouchOutside(false);
        AdLoader.Builder builder = new AdLoader.Builder(this, getResources().getString(R.string.admob_native));
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                FrameLayout frameLayout = dialog.findViewById(R.id.fl_adplaceholder);
                dialog.findViewById(R.id.tvLoadingAds).setVisibility(View.GONE);
                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater().inflate(R.layout.ad_unified_small, null);
                populateUnifiedNativeAdViewDialog(unifiedNativeAd, adView);
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
        dialog.findViewById(R.id.btnLater).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
        dialog.show();
    }


    @Override
    public void onBackPressed() {
        if (this.ePreferences.getBoolean("pref_key_rate", false)) {
            ExitDialog();
        } else {
            RateDialog();
        }
    }
}
