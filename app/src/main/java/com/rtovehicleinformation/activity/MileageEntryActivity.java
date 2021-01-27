package com.rtovehicleinformation.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Handler;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.rtovehicleinformation.Database.OpenSQLite;
import com.rtovehicleinformation.Model.MileageModel;
import com.rtovehicleinformation.R;
import com.rtovehicleinformation.kprogresshud.KProgressHUD;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MileageEntryActivity extends AppCompatActivity {

    Activity activity = MileageEntryActivity.this;
    Boolean clickable = Boolean.FALSE;
    String cost;
    float cresVal = 0.0f;
    SimpleDateFormat dateFormat;
    float fuleVal = 0.0f;
    String full;
    String km;
    float lresVal = 0.0f;
    int mday;
    int mmonth;
    int myear;
    OpenSQLite openSQLite;
    float prcVal = 0.0f;
    float resVal = 0.0f;
    float val = 0.0f;

    @BindView(R.id.current_res_ed_txt)
    EditText current_res_ed_txt;

    @BindView(R.id.current_res_lay)
    TextInputLayout current_res_lay;

    @BindView(R.id.fuel_unit_ed_txt)
    EditText fuel_unit_ed_txt;

    @BindView(R.id.fuel_unit_lay)
    TextInputLayout fuel_unit_lay;

    @BindView(R.id.last_res_ed_txt)
    EditText last_res_ed_txt;

    @BindView(R.id.last_res_lay)
    TextInputLayout last_res_lay;

    @BindView(R.id.mileage_txt)
    TextView mileage_txt;

    @BindView(R.id.noted_date_ed_txt)
    EditText noted_date_ed_txt;

    @BindView(R.id.noted_date_lay)
    TextInputLayout noted_date_lay;

    @BindView(R.id.per_kms_txt)
    TextView tv_per_kms_txt;

    @BindView(R.id.price_ed_txt)
    EditText et_price_ed_txt;

    @BindView(R.id.price_lay)
    TextInputLayout price_lay;

    @BindView(R.id.save_calc_txt)
    Button btn_save_calc_txt;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_back)
    ImageView ivback;

    public KProgressHUD hud;
    public InterstitialAd mInterstitialAd;

    class CurResTxt implements TextWatcher {
        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }


        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            MileageEntryActivity.this.clickable = Boolean.valueOf(false);
            MileageEntryActivity.this.btnTxtChange();
        }
    }

    class DueDateClick implements View.OnClickListener {

        class DateSet implements DatePickerDialog.OnDateSetListener {
            DueDateClick DueDateClick;

            public DateSet(DueDateClick dueDateClick) {
                this.DueDateClick = dueDateClick;
            }

            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                MileageEntryActivity.this.mday = i3;
                MileageEntryActivity.this.mmonth = i2;
                MileageEntryActivity.this.myear = i;
                MileageEntryActivity.this.Date(MileageEntryActivity.this.myear, MileageEntryActivity.this.mmonth, MileageEntryActivity.this.mday);
            }
        }

        DueDateClick() {
        }

        @SuppressLint({"WrongConstant"})
        public void onClick(View view) {
            Calendar instance = Calendar.getInstance();
            new DatePickerDialog(MileageEntryActivity.this, new DateSet(this), instance.get(1), instance.get(2), instance.get(5)).show();
        }
    }

    class FuelTxt implements TextWatcher {
        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        FuelTxt() {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            MileageEntryActivity.this.clickable = Boolean.valueOf(false);
            MileageEntryActivity.this.btnTxtChange();
        }
    }

    class Handlr implements Runnable {
        Handlr() {
        }

        public void run() {
            MileageEntryActivity mileageEntryActivity = MileageEntryActivity.this;
            mileageEntryActivity.Date(mileageEntryActivity.myear, MileageEntryActivity.this.mmonth, MileageEntryActivity.this.mday);
        }
    }

    class LstResTxt implements TextWatcher {
        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        LstResTxt() {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            MileageEntryActivity.this.clickable = Boolean.valueOf(false);
            MileageEntryActivity.this.btnTxtChange();
        }
    }

    class PriceTxt implements TextWatcher {
        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        PriceTxt() {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            MileageEntryActivity.this.clickable = Boolean.valueOf(false);
            MileageEntryActivity.this.btnTxtChange();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mileage_entry);
        ButterKnife.bind(this);
//        this.toolbar.setTitle("Vehicle Mileage");
        Intent intent = getIntent();
        this.km = intent.getExtras().getString("KM_UNIT");
        this.full = intent.getExtras().getString("FULL_UNIT");
        this.cost = intent.getExtras().getString("COST_UNIT");
        this.openSQLite = new OpenSQLite(getApplicationContext());
        this.dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar instance = Calendar.getInstance();
        this.mday = instance.get(5);
        this.mmonth = instance.get(2);
        this.myear = instance.get(1);
        interstitialAd();
        this.noted_date_ed_txt.setFocusable(false);
        this.noted_date_ed_txt.setClickable(true);
        this.noted_date_ed_txt.setOnClickListener(new DueDateClick());
        this.btn_save_calc_txt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                SaveAndCalculate();


            }
        });
        this.et_price_ed_txt.addTextChangedListener(new PriceTxt());
        this.fuel_unit_ed_txt.addTextChangedListener(new FuelTxt());
        this.current_res_ed_txt.addTextChangedListener(new CurResTxt());
        this.last_res_ed_txt.addTextChangedListener(new LstResTxt());
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        SetHint();
    }

    private void interstitialAd() {
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                if (mileageModel != null) {
                    Save(mileageModel);
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
    }

    MileageModel mileageModel;

    private void SaveAndCalculate() {
        if (LstRes() && PrcText() && FuelText() && CurRes() && Value()) {
            if (clickable) {
                mileageModel = new MileageModel(last_res_ed_txt.getText().toString(), current_res_ed_txt.getText().toString(), et_price_ed_txt.getText().toString(), fuel_unit_ed_txt.getText().toString(), noted_date_ed_txt.getText().toString(), new DecimalFormat("##.##").format((double) MileageEntryActivity.this.resVal), new DecimalFormat("##.##").format((double) MileageEntryActivity.this.val));
                if (MileageEntryActivity.this.btn_save_calc_txt.getText().equals(" Save ")) {
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
                        Save(mileageModel);
                    }
                }
            }
            Calculate();
        }
    }

    private boolean LstRes() {
        if (this.last_res_ed_txt.getText().toString().trim().isEmpty()) {
            TextInputLayout textInputLayout = this.last_res_lay;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getString(R.string.error_last_kms));
            stringBuilder.append(" ");
            stringBuilder.append(this.km);
            textInputLayout.setError(stringBuilder.toString());
            SetText(this.last_res_ed_txt);
            return false;
        }
        this.current_res_lay.setErrorEnabled(false);
        return true;
    }

    private boolean PrcText() {
        if (this.et_price_ed_txt.getText().toString().trim().isEmpty()) {
            this.price_lay.setError(getString(R.string.error_price));
            SetText(this.et_price_ed_txt);
            return false;
        }
        this.price_lay.setErrorEnabled(false);
        return true;
    }

    private boolean FuelText() {
        if (this.fuel_unit_ed_txt.getText().toString().trim().isEmpty()) {
            this.fuel_unit_lay.setError(getString(R.string.error_fuel));
            SetText(this.fuel_unit_ed_txt);
            return false;
        }
        this.fuel_unit_lay.setErrorEnabled(false);
        return true;
    }

    private boolean CurRes() {
        if (this.current_res_ed_txt.getText().toString().trim().isEmpty()) {
            TextInputLayout textInputLayout = this.current_res_lay;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getString(R.string.error_current_kms));
            stringBuilder.append(" ");
            stringBuilder.append(this.km);
            textInputLayout.setError(stringBuilder.toString());
            SetText(this.current_res_ed_txt);
            return false;
        }
        this.current_res_lay.setErrorEnabled(false);
        return true;
    }

    private boolean Value() {
        float parseFloat;
        float f = 0.0f;
        try {
            parseFloat = Float.parseFloat(this.current_res_ed_txt.getText().toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
            parseFloat = 0.0f;
        }
        try {
            f = Float.parseFloat(this.last_res_ed_txt.getText().toString().trim());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (f >= parseFloat) {
            this.current_res_lay.setError(getString(R.string.error_current_kms_value));
            SetText(this.current_res_ed_txt);
            return false;
        }
        this.current_res_lay.setErrorEnabled(false);
        return true;
    }

    private void SetText(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(5);
        }
    }

    public void SetHint() {
        TextInputLayout textInputLayout = this.last_res_lay;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Last Reserve* (");
        stringBuilder.append(this.km);
        stringBuilder.append(")");
        textInputLayout.setHint(stringBuilder.toString());
        textInputLayout = this.price_lay;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Price* (");
        stringBuilder.append(this.cost);
        stringBuilder.append(")");
        textInputLayout.setHint(stringBuilder.toString());
        textInputLayout = this.fuel_unit_lay;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Fuel* (");
        stringBuilder.append(this.full);
        stringBuilder.append(")");
        textInputLayout.setHint(stringBuilder.toString());
        textInputLayout = this.current_res_lay;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Current Reserve* (");
        stringBuilder.append(this.km);
        stringBuilder.append(")");
        textInputLayout.setHint(stringBuilder.toString());
        this.current_res_ed_txt.setText("");
        this.clickable = Boolean.valueOf(false);
        btnTxtChange();
        new Handler().postDelayed(new Handlr(), 100);
    }

    public void Calculate() {
        this.lresVal = 0.0f;
        this.cresVal = 0.0f;
        this.prcVal = 0.0f;
        this.fuleVal = 0.0f;
        this.resVal = 0.0f;
        this.val = 0.0f;
        try {
            this.lresVal = Float.parseFloat(this.last_res_ed_txt.getText().toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.cresVal = Float.parseFloat(this.current_res_ed_txt.getText().toString().trim());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            this.prcVal = Float.parseFloat(this.et_price_ed_txt.getText().toString().trim());
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            this.fuleVal = Float.parseFloat(this.fuel_unit_ed_txt.getText().toString().trim());
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        float f = this.cresVal;
        float f2 = this.lresVal;
        this.resVal = (f - f2) / this.fuleVal;
        this.val = this.prcVal / (f - f2);
        TextView textView = this.mileage_txt;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(new DecimalFormat("##.##").format((double) this.resVal));
        stringBuilder.append(" ");
        stringBuilder.append(this.km);
        stringBuilder.append("s/");
        stringBuilder.append(this.full);
        textView.setText(stringBuilder.toString());
        textView = this.tv_per_kms_txt;
        stringBuilder = new StringBuilder();
        stringBuilder.append(new DecimalFormat("##.##").format((double) this.val));
        stringBuilder.append(" ");
        stringBuilder.append(this.cost);
        stringBuilder.append("/");
        stringBuilder.append(this.km);
        textView.setText(stringBuilder.toString());
        this.clickable = Boolean.valueOf(true);
        btnTxtChange();
    }

    private void Date(int i, int i2, int i3) {
        Date parse;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(i2 + 1);
            stringBuilder.append("/");
            stringBuilder.append(i3);
            stringBuilder.append("/");
            stringBuilder.append(i);
            parse = simpleDateFormat.parse(String.valueOf(stringBuilder));
        } catch (ParseException e) {
            e.printStackTrace();
            parse = null;
        }
        this.noted_date_ed_txt.setText(new SimpleDateFormat("dd-MM-yyyy").format(parse));
    }

    public void btnTxtChange() {
        if (this.clickable.booleanValue()) {
            this.btn_save_calc_txt.setText(" Save ");
            this.btn_save_calc_txt.setBackground(getResources().getDrawable(R.drawable.btn_rounded_save));
            return;
        }
        this.btn_save_calc_txt.setText(" Calculate ");
        this.btn_save_calc_txt.setBackground(getResources().getDrawable(R.drawable.btn_rounded));
    }

    private void Save(MileageModel mileageModel) {
        if (this.openSQLite.insrtMileage(mileageModel) > 0) {
            onBackPressed();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
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
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MileageActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}
