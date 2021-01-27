package com.rtovehicleinformation.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class PetrolDieselData implements Serializable
{
    @Expose
    @SerializedName("cityId")
    private int cityId;
    @Expose
    @SerializedName("cityName")
    private String cityName;
    @Expose
    @SerializedName("dDiff")
    private double dDiff;
    @Expose
    @SerializedName("dPrice")
    private double dPrice;
    @Expose
    @SerializedName("pDiff")
    private double pDiff;
    @Expose
    @SerializedName("pPrice")
    private double pPrice;
    @Expose
    @SerializedName("priceDate")
    private String priceDate;
    @Expose
    @SerializedName("priceId")
    private int priceId;
    @Expose
    @SerializedName("stateName")
    private String stateName;

    public int getCityId() {
        return this.cityId;
    }

    public String getCityName() {
        return this.cityName;
    }

    public double getDDiff() {
        return this.dDiff;
    }

    public double getDPrice() {
        return this.dPrice;
    }

    public double getPDiff() {
        return this.pDiff;
    }

    public double getPPrice() {
        return this.pPrice;
    }

    public String getPriceDate() {
        return this.priceDate;
    }

    public int getPriceId() {
        return this.priceId;
    }

    public String getStateName() {
        return this.stateName;
    }

    public void setCityId(final int cityId) {
        this.cityId = cityId;
    }

    public void setCityName(final String cityName) {
        this.cityName = cityName;
    }

    public void setDDiff(final double dDiff) {
        this.dDiff = dDiff;
    }

    public void setDPrice(final double dPrice) {
        this.dPrice = dPrice;
    }

    public void setPDiff(final double pDiff) {
        this.pDiff = pDiff;
    }

    public void setPPrice(final double pPrice) {
        this.pPrice = pPrice;
    }

    public void setPriceDate(final String priceDate) {
        this.priceDate = priceDate;
    }

    public void setPriceId(final int priceId) {
        this.priceId = priceId;
    }

    public void setStateName(final String stateName) {
        this.stateName = stateName;
    }
}
