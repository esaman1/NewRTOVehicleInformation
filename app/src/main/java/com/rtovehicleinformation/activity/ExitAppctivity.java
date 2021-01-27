/*
package com.rtovehicleinformation.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.gson.Gson;
import com.rtovehicleinformation.Adapter.MoreAppAdapter;
import com.rtovehicleinformation.Model.Appdata;
import com.rtovehicleinformation.R;
import com.rtovehicleinformation.application.AppController;
import com.rtovehicleinformation.preferences.EPreferences;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ExitAppctivity extends AppCompatActivity {

    Activity activity = ExitAppctivity.this;
    private ArrayList<Appdata> array_appdata;
    private MoreAppAdapter moreAppAdapter;
    private RecyclerView recyclerView;
    LinearLayout llPolicy;
    RelativeLayout rlSkip;
    Button btnSkip;
    ImageView ivSkip;
    EPreferences ePreferences;
    private UnifiedNativeAd nativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_app);
        ePreferences = EPreferences.getInstance(this);
        array_appdata = new ArrayList<>();
        makeJsonRequest(getResources().getString(R.string.more_appurl));
        recyclerView = findViewById(R.id.ad_inter_recycle_view);
        SetAnimations();
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        rlSkip = findViewById(R.id.rlSkip);
        llPolicy = findViewById(R.id.llPrivacy);
        btnSkip = findViewById(R.id.btnSkip);
        ivSkip = findViewById(R.id.ivSkip);
        llPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse("http://google.com"));
                startActivity(intent1);
            }
        });
        loadNativeAds();
    }

    private void SetAnimations() {
        final RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(3000L);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        this.findViewById(R.id.img1).startAnimation(rotateAnimation);
    }

    private void makeJsonRequest(String url) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("application_detail");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                Appdata app_data = new Gson().fromJson(jsonArray.get(i).toString(), Appdata.class);
                                if (!app_data.getApplication_name().equalsIgnoreCase(getString(R.string.app_name))) {
                                    array_appdata.add(app_data);
                                    moreAppAdapter = new MoreAppAdapter(ExitAppctivity.this, array_appdata);
                                    recyclerView.setAdapter(moreAppAdapter);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, "");
    }


    private void loadNativeAds() {
        AdLoader.Builder builder = new AdLoader.Builder(this, getResources().getString(R.string.admob_native));
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                FrameLayout frameLayout = findViewById(R.id.fl_adplaceholder);
                findViewById(R.id.tvLoadingAds).setVisibility(View.GONE);
                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater().inflate(R.layout.ad_unified, null);
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


    private void populateNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView unifiedNativeAdView) {
        MediaView mediaView = unifiedNativeAdView.findViewById(R.id.ad_media);
        unifiedNativeAdView.setMediaView(mediaView);
        unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
        unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
        unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
        unifiedNativeAdView.setStarRatingView(unifiedNativeAdView.findViewById(R.id.ad_stars));
        unifiedNativeAdView.setStoreView(unifiedNativeAdView.findViewById(R.id.ad_store));
        ((TextView) unifiedNativeAdView.getHeadlineView()).setText(nativeAd.getHeadline());
        if (nativeAd.getBody() == null) {
            unifiedNativeAdView.getBodyView().setVisibility(View.GONE);
        } else {
            unifiedNativeAdView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() == null) {
            unifiedNativeAdView.getCallToActionView().setVisibility(View.GONE);
        } else {
            unifiedNativeAdView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) unifiedNativeAdView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        if (nativeAd.getStore() == null) {
            unifiedNativeAdView.findViewById(R.id.appinstall_store_icon).setVisibility(View.GONE);
            unifiedNativeAdView.getStoreView().setVisibility(View.GONE);
        } else {
            unifiedNativeAdView.findViewById(R.id.appinstall_store_icon).setVisibility(View.VISIBLE);
            unifiedNativeAdView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getStoreView()).setText(nativeAd.getStore());
        }
        if (nativeAd.getStarRating() == null) {
            unifiedNativeAdView.getStarRatingView().setVisibility(View.GONE);
        } else {
            ((RatingBar) unifiedNativeAdView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            unifiedNativeAdView.getStarRatingView().setVisibility(View.VISIBLE);
        }
        unifiedNativeAdView.setNativeAd(nativeAd);

    }
    @Override
    public void onBackPressed() {
        if (this.ePreferences.getBoolean("pref_key_rate", false)) {
            ExitDialog();
        } else {
            RateDialog();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void RateDialog() {
        final boolean[] isRate = {false, false};
        final Dialog dialog = new Dialog(ExitAppctivity.this);
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
        final Dialog dialog = new Dialog(ExitAppctivity.this);
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

    private void populateUnifiedNativeAdViewDialog(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);
        VideoController vc = nativeAd.getVideoController();
        if (vc.hasVideoContent()) {
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    super.onVideoEnd();
                }
            });
        }
    }
}
*/
