package com.rtovehicleinformation.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.rtovehicleinformation.Adapter.CityListAdapter;
import com.rtovehicleinformation.Model.PetrolDieselData;
import com.rtovehicleinformation.R;
import com.rtovehicleinformation.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityListActivity extends AppCompatActivity {

    Activity activity = CityListActivity.this;
    @BindView(R.id.cityList)
    ListView listView;

    @BindView(R.id.searchCity)
    SearchView searchView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    int CityPosition;
    List<PetrolDieselData> petrolDieselData;
    List<PetrolDieselData> search_result_arraylist = new ArrayList();
    CityListAdapter cityListAdapter;
    AdRequest adRequest;
    AdView adView;
    InterstitialAd mInterstitialAd;
    public KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        ButterKnife.bind(this);
        toolbar.setTitle("Select City");
        loadBannerAd();
        loadInterstitialAd();
        searchView.setSearchableInfo(((SearchManager) getSystemService(SEARCH_SERVICE)).getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            public boolean onClose() {
                search_result_arraylist.clear();
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String str) {
                return false;
            }

            public boolean onQueryTextChange(String str) {
                if (str.equalsIgnoreCase("")) {
                    CityListActivity cityListActivity = CityListActivity.this;
                    cityListActivity.setSearchData(cityListActivity.petrolDieselData);
                } else {
                    CityListActivity.this.SearchByProductTitleName(str);
                }
                return false;
            }
        });
        petrolDieselData = (List) getIntent().getSerializableExtra("data");
        cityListAdapter = new CityListAdapter(getApplication(), this.petrolDieselData);
        listView.setAdapter(this.cityListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long j) {
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
                                CityPosition = i;
                                mInterstitialAd.show();
                            }
                        }
                    }, 2000);
                } else {
                    ListClick(i);
                }
            }
        });
    }



    private void ListClick(int i) {
        Intent intent = new Intent();
        if (search_result_arraylist.size() > 0) {
            intent.putExtra("data", search_result_arraylist.get(i));
        } else {
            intent.putExtra("data", petrolDieselData.get(i));
        }
        setResult(-1, intent);
        finish();
    }

    private void SearchByProductTitleName(String str) {
        this.search_result_arraylist.clear();
        for (int i = 0; i < this.petrolDieselData.size(); i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.petrolDieselData.get(i).getCityName().toLowerCase());
            if (stringBuilder.toString().contains(str.toLowerCase())) {
                this.search_result_arraylist.add(this.petrolDieselData.get(i));
            }
        }
        setSearchData(this.search_result_arraylist);
    }

    private void setSearchData(List<PetrolDieselData> list) {
        CityListAdapter cityListAdapter = this.cityListAdapter;
        if (cityListAdapter != null) {
            cityListAdapter.SetSearchData(list);
            this.cityListAdapter.notifyDataSetChanged();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
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
                ListClick(CityPosition);
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
    public void onBackPressed() {
        super.onBackPressed();
    }
}
