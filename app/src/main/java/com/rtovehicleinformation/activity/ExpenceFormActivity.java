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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.rtovehicleinformation.Database.OpenSQLite;
import com.rtovehicleinformation.Model.VehicleExpenseModel;
import com.rtovehicleinformation.R;
import com.rtovehicleinformation.kprogresshud.KProgressHUD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpenceFormActivity extends AppCompatActivity {

    Activity activity = ExpenceFormActivity.this;

    @BindView(R.id.amountInputLayout)
    TextInputLayout amountInputLayout;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnSave)
    Button btnSave;

    @BindView(R.id.dateInputLayout)
    TextInputLayout dateInputLayout;

    @BindView(R.id.etAmount)
    EditText etAmount;
    @BindView(R.id.etDueDate)
    EditText etDueDate;
    @BindView(R.id.etNotes)
    EditText etNotes;
    @BindView(R.id.etPayeeItem)
    EditText etPayeeItem;

    @BindView(R.id.itemInputLayout)
    TextInputLayout etItemInput;

    @BindView(R.id.notesInputLayout)
    TextInputLayout etnotesInput;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_back)
    ImageView ivback;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    int catImg;
    String catName;
    SimpleDateFormat dateFormat;
    int day;
    String dueDate;
    String addList;
    String amount;
    OpenSQLite openSQLite;
    String payee;
    int year;
    int month;
    String note;
    Intent intent;
    AdRequest adRequest;
    AdView adView;
    InterstitialAd mInterstitialAd;
    public KProgressHUD hud;

    class DueDateClick implements View.OnClickListener {
        ExpenceFormActivity expenceFormActivity;

        class DateSet implements DatePickerDialog.OnDateSetListener {
            DueDateClick DueDateClick;

            public DateSet(DueDateClick dueDateClick) {
                this.DueDateClick = dueDateClick;
            }

            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                Calendar instance = Calendar.getInstance();
                instance.set(i, i2, i3);
                this.DueDateClick.expenceFormActivity.etDueDate.setText(this.DueDateClick.expenceFormActivity.dateFormat.format(instance.getTime()));
            }
        }

        public DueDateClick(ExpenceFormActivity expenceFormActivity) {
            this.expenceFormActivity = expenceFormActivity;
        }

        @SuppressLint({"WrongConstant"})
        public void onClick(View view) {
            Calendar instance = Calendar.getInstance();
            new DatePickerDialog(this.expenceFormActivity, new DateSet(this), instance.get(1), instance.get(2), instance.get(5)).show();
        }
    }

    class Hndlr implements Runnable {
        ExpenceFormActivity expenceFormActivity;

        public Hndlr(ExpenceFormActivity expenceFormActivity) {
            this.expenceFormActivity = expenceFormActivity;
        }

        public void run() {
            this.expenceFormActivity.DateFormat(ExpenceFormActivity.this.day, ExpenceFormActivity.this.month, ExpenceFormActivity.this.year);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expence_form);
        ButterKnife.bind(this);
        intent = getIntent();
        if (getIntent().getExtras() != null) {
            this.addList = intent.getStringExtra("btn");
            if (this.addList.equals("SAVE")) {
                this.catName = intent.getStringExtra("categoryname");
                this.catImg = intent.getIntExtra("categoryimg", R.drawable.other_expense);
            } else {
                this.payee = intent.getExtras().getString("BPAYEE");
                this.amount = intent.getExtras().getString("BAMOUNT");
                this.note = intent.getExtras().getString("BNOTE");
                this.dueDate = intent.getExtras().getString("BDUEDATE");
                this.catName = intent.getExtras().getString("BCATNAME");
                this.catImg = intent.getExtras().getInt("BCATICON");
            }
        }
        loadBannerAd();
        loadInterstitialAd();
        this.openSQLite = new OpenSQLite(getApplicationContext());
        this.dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar instance = Calendar.getInstance();
        this.day = instance.get(1);
        this.month = instance.get(Calendar.MONTH);
        this.year = instance.get(5);
        this.etDueDate.setFocusable(false);
        this.etDueDate.setClickable(true);
        if (this.addList.equals("UPDATE")) {
            this.btnSave.setTag("UPDATE");
            this.btnSave.setText("Update");
            this.etPayeeItem.setText(this.payee);
            this.etAmount.setText(this.amount);
            this.etNotes.setText(this.note);
            this.etDueDate.setText(this.dueDate);
        } else {
            SetHint();
        }
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        this.etDueDate.setOnClickListener(new DueDateClick(this));

        this.btnSave.setOnClickListener(new View.OnClickListener() {
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
                                mInterstitialAd.show();
                            }
                        }
                    }, 2000);
                } else {
                    SaveDetails();
                }
            }
        });
        tvTitle.setText(catName);

    }

    private void SaveDetails() {
        if (ExpenceFormActivity.this.PayeeItem() && ExpenceFormActivity.this.Setdate() && ExpenceFormActivity.this.Amount() && ExpenceFormActivity.this.CategorySet()) {
            VehicleExpenseModel vehicleExpenseModel = new VehicleExpenseModel(ExpenceFormActivity.this.etAmount.getText().toString(), ExpenceFormActivity.this.catName, ExpenceFormActivity.this.etPayeeItem.getText().toString(), ExpenceFormActivity.this.etNotes.getText().toString(), ExpenceFormActivity.this.etDueDate.getText().toString(), ExpenceFormActivity.this.catImg);
            if (btnSave.getTag().equals("SAVE")) {
                Save(vehicleExpenseModel);
                startActivity(new Intent(ExpenceFormActivity.this, VehicleExpenseActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                return;
            }
            Update(intent.getExtras().getInt("BID"), vehicleExpenseModel);
            startActivity(new Intent(ExpenceFormActivity.this, VehicleExpenseActivity.class));
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
    }

    private void DateFormat(int i, int i2, int i3) {
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
        this.etDueDate.setText(new SimpleDateFormat("dd-MM-yyyy").format(parse));
    }

    private void Update(int i, VehicleExpenseModel vehicleExpenseModel) {
        if (((long) this.openSQLite.updt(i, vehicleExpenseModel)) > 0) {
            finish();
        }
    }

    private void str(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(5);
        }
    }

    private void Save(VehicleExpenseModel vehicleExpenseModel) {
        if (this.openSQLite.insrt(vehicleExpenseModel) > 0) {
            finish();
        }
    }

    private boolean Amount() {
        if (this.etAmount.getText().toString().trim().isEmpty()) {
            TextInputLayout textInputLayout = this.amountInputLayout;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getString(R.string.comman_name));
            stringBuilder.append(" Your Amount ");
            textInputLayout.setError(stringBuilder.toString());
            str(this.etAmount);
            return false;
        }
        this.amountInputLayout.setErrorEnabled(false);
        return true;
    }

    private boolean PayeeItem() {
        if (this.etPayeeItem.getText().toString().trim().isEmpty()) {
            TextInputLayout textInputLayout = this.etItemInput;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getString(R.string.comman_name));
            stringBuilder.append(" Your Payee/Item");
            textInputLayout.setError(stringBuilder.toString());
            str(this.etPayeeItem);
            return false;
        }
        this.etItemInput.setErrorEnabled(false);
        return true;
    }

    private boolean Notes() {
        if (this.etNotes.getText().toString().trim().isEmpty()) {
            TextInputLayout textInputLayout = this.etnotesInput;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getString(R.string.comman_name));
            stringBuilder.append(" Your Notes");
            textInputLayout.setError(stringBuilder.toString());
            str(this.etNotes);
            return false;
        }
        this.etnotesInput.setErrorEnabled(false);
        return true;
    }

    private boolean Setdate() {
        if (this.etDueDate.getText().toString().trim().isEmpty()) {
            DateFormat(this.day, this.month, this.year);
        }
        return true;
    }

    private boolean CategorySet() {
        if (this.catImg == 0) {
            this.catImg = R.drawable.other_expense;
        }
        if (this.catName == null) {
            this.catName = "Other";
        }
        return true;
    }

    public void SetHint() {
        this.etItemInput.setHint("Payee/Item Name");
        this.etnotesInput.setHint("Notes");
        this.amountInputLayout.setHint("Amount(Rs)");
        new Handler().postDelayed(new Hndlr(this), 500);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
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
                SaveDetails();
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

    public void onBackPressed() {
        super.onBackPressed();
    }

}
