package com.rtovehicleinformation.activity;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.rtovehicleinformation.R;

public class HomeActivity extends AppCompatActivity {

    String first;
    String second;
    String Third;
    String Fourth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getString(R.string.rate_never));
        sb.append(this.getString(R.string.app_name));
        first = getIntent().getStringExtra("first");
        second = getIntent().getStringExtra("second");
        Third = getIntent().getStringExtra("third");
        Fourth = getIntent().getStringExtra("fourth");
        final FragmentTransaction beginTransaction = this.getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.container_body, FragmentRegNo.getInstance(first, second, Third, Fourth), "HOME_FRAGMENT");
        beginTransaction.commit();
    }
}
