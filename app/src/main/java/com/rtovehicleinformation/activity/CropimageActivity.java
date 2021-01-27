package com.rtovehicleinformation.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.rtovehicleinformation.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CropimageActivity extends AppCompatActivity {
    String image;
    @BindView(R.id.quick_start_cropped_image)
    ImageView quick_start_cropped_image;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_cropimage);
        ButterKnife.bind(this);
        this.image = getIntent().getStringExtra("imagepath");
        Glide.with(this).load(getIntent().getStringExtra("imagepath")).into(this.quick_start_cropped_image);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }
}
