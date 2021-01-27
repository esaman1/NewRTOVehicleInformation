package com.rtovehicleinformation.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rtovehicleinformation.Model.VehicleDetails;
import com.rtovehicleinformation.R;
import com.rtovehicleinformation.Retrofit.APIClient;
import com.rtovehicleinformation.Retrofit.APIInterface;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleDetailActivity extends AppCompatActivity {

    Activity activity = VehicleDetailActivity.this;
    APIInterface apiInterface;
    String VehicleNo;
    TextView address, registrationNo, registrationDate, chassisNo, engineNo, ownerName, vehicleClass, fuelType, markerModel,
            fitnessUpto, insuranceUpto, fuelNorms, vehicleId, brandId, brandName, modelName, modelSlug, shoRoomPrice,vehicleType,vehicleColor,seatCapacity,ownership,ownershipDesc,searchCount;
    ImageView vehicleImage;
    Button checkRcDetail;
    EditText vehicleNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detail);
        vehicleImage = findViewById(R.id.vehicleImage);
        checkRcDetail = findViewById(R.id.checkRcDetail);
        vehicleNumber = findViewById(R.id.vehicleNumber);
        address = findViewById(R.id.address);
        registrationNo = findViewById(R.id.registerednumber);
        registrationDate = findViewById(R.id.registrationDate);
        chassisNo = findViewById(R.id.chassisNumber);
        engineNo = findViewById(R.id.engineNumber);
        ownerName = findViewById(R.id.ownerName);
        vehicleClass = findViewById(R.id.vehicleClass);
        fuelType = findViewById(R.id.fuelType);
        markerModel = findViewById(R.id.markerModel);
        fitnessUpto = findViewById(R.id.fitnessUpto);
        insuranceUpto = findViewById(R.id.insuranceUpto);
        fuelNorms = findViewById(R.id.fuelNorms);
        vehicleId = findViewById(R.id.vehicleId);
        brandId = findViewById(R.id.brandId);
        modelName = findViewById(R.id.modelName);
        modelSlug = findViewById(R.id.modelSlug);
        shoRoomPrice = findViewById(R.id.showRoomPrice);
        brandName = findViewById(R.id.brandName);
        vehicleType=findViewById(R.id.vehicleType);
        vehicleColor=findViewById(R.id.vehicleColor);
        seatCapacity=findViewById(R.id.seatCapacity);
        ownership=findViewById(R.id.ownership);
        ownershipDesc=findViewById(R.id.ownershipDesc);
        searchCount=findViewById(R.id.searchCount);


        apiInterface = APIClient.getClient().create(APIInterface.class);

        checkRcDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sVehicleNumber = vehicleNumber.getText().toString().trim();

                if (!TextUtils.isEmpty(sVehicleNumber)) {
                    GetVehicleDetail(sVehicleNumber);
                } else {
                    Toast.makeText(activity, "Please Enter Valid Number !!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void GetVehicleDetail(String number) {


//        VehicleNo = "GJ03LM9134";
        Call<JsonObject> call = apiInterface.GetVehicleDetail(number);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                            JSONObject jsonObj = new JSONObject(new Gson().toJson(response.body()));
                            VehicleDetails vehicleDetails = new VehicleDetails();
                            String statusCode = jsonObj.getString("statusCode");

                            Log.e("TAG","CodeStatus"+statusCode);

                            if (statusCode.equals("200"))
                            {
                                JSONObject ownerDetails = jsonObj.getJSONObject("details");

                                Log.e("ownership",jsonObj.getJSONObject("details").toString());

                                if(jsonObj.getJSONObject("details").toString().contains("ownership"))
                                {
                                    vehicleType.setText(ownerDetails.getString("vehicleType"));
                                    vehicleColor.setText(ownerDetails.getString("vehicleColor"));
                                    seatCapacity.setText(ownerDetails.getString("seatCapacity"));
                                    ownership.setText(ownerDetails.getString("ownership"));
                                    ownershipDesc.setText(ownerDetails.getString("ownershipDesc"));
                                    searchCount.setText(ownerDetails.getString("searchCount"));


                                }
                                else
                                {
                                    Log.e("check","Ownership and Ownership Description not available");
                                }

                                address.setText(ownerDetails.getString("registrationAuthority"));
                                registrationNo.setText(ownerDetails.getString("registrationNo"));
                                registrationDate.setText(ownerDetails.getString("registrationDate"));
                                chassisNo.setText(ownerDetails.getString("chassisNo"));
                                ownerName.setText(ownerDetails.getString("ownerName"));
                                vehicleClass.setText(ownerDetails.getString("vehicleClass"));
                                fuelType.setText(ownerDetails.getString("fuelType"));
                                engineNo.setText(ownerDetails.getString("engineNo"));
                                markerModel.setText(ownerDetails.getString("makerModel"));
                                fitnessUpto.setText(ownerDetails.getString("fitnessUpto"));
                                insuranceUpto.setText(ownerDetails.getString("insuranceUpto"));
                                fuelNorms.setText(ownerDetails.getString("fuelNorms"));

                                String vehicleInfo = ownerDetails.getString("vehicleInfo");
                                JSONObject vehicleinfo = ownerDetails.getJSONObject("vehicleInfo");

                                if (vehicleInfo != null) {

                                    vehicleId.setText(vehicleinfo.getString("id"));
                                    brandId.setText(vehicleinfo.getString("brandId"));
                                    brandName.setText(vehicleinfo.getString("brandName"));
                                    modelName.setText(vehicleinfo.getString("modelName"));
                                    modelSlug.setText(vehicleinfo.getString("modelSlug"));
                                    shoRoomPrice.setText(vehicleinfo.getString("exShowroomPrice"));
                                }else {
                                    Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
                                    Log.e("vehicleInfo", "vehicleInfo Not Found");
                                }

                            } else {
                                Toast.makeText(activity, "Data Not Found", Toast.LENGTH_LONG).show();
                                Log.e("Data", "Data Not Found");
                            }



/*
                        vehicleDetails.setRegistrationAuthority(ownerDetails.getString("registrationAuthority"));
                        vehicleDetails.setRegistrationNo(ownerDetails.getString("registrationNo"));
                        vehicleDetails.setRegistrationDate(ownerDetails.getString("registrationDate"));
                        vehicleDetails.setChassisNo(ownerDetails.getString("chassisNo"));
                        vehicleDetails.setEngineNo(ownerDetails.getString("engineNo"));
                        vehicleDetails.setOwnerName(ownerDetails.getString("ownerName"));
                        vehicleDetails.setVehicleClass(ownerDetails.getString("vehicleClass"));
                        vehicleDetails.setFuelType(ownerDetails.getString("fuelType"));
                        vehicleDetails.setMakerModel(ownerDetails.getString("makerModel"));
                        vehicleDetails.setFitnessUpto(ownerDetails.getString("fitnessUpto"));
                        vehicleDetails.setInsuranceUpto(ownerDetails.getString("insuranceUpto"));*
                        }/


                        address.setText(ownerDetails.getString("registrationAuthority"));
                        registrationNo.setText(ownerDetails.getString("registrationNo"));
                        registrationDate.setText(ownerDetails.getString("registrationDate"));
                        chassisNo.setText(ownerDetails.getString("chassisNo"));
                        ownerName.setText(ownerDetails.getString("ownerName"));
                        vehicleClass.setText(ownerDetails.getString("vehicleClass"));
                        fuelType.setText(ownerDetails.getString("fuelType"));
                        engineNo.setText(ownerDetails.getString("engineNo"));
                        markerModel.setText(ownerDetails.getString("makerModel"));
                        fitnessUpto.setText(ownerDetails.getString("fitnessUpto"));
                        insuranceUpto.setText(ownerDetails.getString("insuranceUpto"));
                        fuelNorms.setText(ownerDetails.getString("fuelNorms"));



                        if (innerJObject != null){

                        }


                        Glide.with(activity)
                                .load(innerJObject.getString("imageUrl"))
                                .centerCrop()
                                .placeholder(R.drawable.vehicle_place_holder)
                                .into(vehicleImage);


                        /* textView.setText(vehicledetail.getString("ownerName"));*/

                       /* if (ownerDetails.getJSONObject("vrhicleInfo") == null) {


                        }*/

                       /* JSONObject innerJObject = ownerDetails.getJSONObject("vehicleInfo");

                        if(jsonObj.getJSONObject("details").toString().contains("ownership"))
                        {
                            vehicleDetails.setRegistrationAuthority(ownerDetails.getString("registrationAuthority"));
                            vehicleDetails.setRegistrationNo(ownerDetails.getString("registrationNo"));
                            vehicleDetails.setRegistrationDate(ownerDetails.getString("registrationDate"));
                            vehicleDetails.setChassisNo(ownerDetails.getString("chassisNo"));
                            vehicleDetails.setEngineNo(ownerDetails.getString("engineNo"));
                            vehicleDetails.setOwnerName(ownerDetails.getString("ownerName"));
                            vehicleDetails.setVehicleClass(ownerDetails.getString("vehicleClass"));
                            vehicleDetails.setFuelType(ownerDetails.getString("fuelType"));
                            vehicleDetails.setMakerModel(ownerDetails.getString("makerModel"));
                            vehicleDetails.setFitnessUpto(ownerDetails.getString("fitnessUpto"));
                            vehicleDetails.setInsuranceUpto(ownerDetails.getString("insuranceUpto"));

                            vehicleDetails.setId(innerJObject.getString("id"));
                            vehicleDetails.setBrandId(innerJObject.getString("brandId"));
                            vehicleDetails.setBrandName(innerJObject.getString("brandName"));
                            vehicleDetails.setModelName(innerJObject.getString("modelName"));
                            vehicleDetails.setModelSlug(innerJObject.getString("modelSlug"));
                            vehicleDetails.setExShowroomPrice(innerJObject.getString("exShowroomPrice"));
                            vehicleDetails.setImageUrl(innerJObject.getString("imageUrl"));


                            vehicleDetails.setVehicleType(ownerDetails.getString("vehicleType"));
                            vehicleDetails.setVehicleColor(ownerDetails.getString("vehicleColor"));

                            vehicleDetails.setOwnershipDesc(ownerDetails.getString("ownershipDesc"));
                            vehicleDetails.setSearchCount(ownerDetails.getString("searchCount"));
                        }*/


                        //Data Binding


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(VehicleDetailActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "OnFailure" + t.getMessage());
            }
        });


    }
}