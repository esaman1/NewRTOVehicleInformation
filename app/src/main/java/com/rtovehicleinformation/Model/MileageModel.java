package com.rtovehicleinformation.Model;

import java.util.ArrayList;

public class MileageModel {

    String fromKms;
    String fuel;
    int id;
    String mileage;
    ArrayList<MileageModel> mileageModels;
    String notedDate;
    String perKms;
    String price;
    String toKms;

    public MileageModel() {

    }

    public MileageModel(final String fromKms, final String toKms, final String price, final String fuel, final String notedDate, final String mileage, final String perKms) {
        this.fromKms = fromKms;
        this.toKms = toKms;
        this.price = price;
        this.fuel = fuel;
        this.notedDate = notedDate;
        this.mileage = mileage;
        this.perKms = perKms;
    }

    public String getFromKms() {
        return this.fromKms;
    }

    public String getFuel() {
        return this.fuel;
    }

    public int getId() {
        return this.id;
    }

    public String getMileage() {
        return this.mileage;
    }

    public ArrayList<MileageModel> getMileageModels() {
        return this.mileageModels;
    }

    public String getNotedDate() {
        return this.notedDate;
    }

    public String getPerKms() {
        return this.perKms;
    }

    public String getPrice() {
        return this.price;
    }

    public String getToKms() {
        return this.toKms;
    }

    public void setFromKms(final String fromKms) {
        this.fromKms = fromKms;
    }

    public void setFuel(final String fuel) {
        this.fuel = fuel;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setMileage(final String mileage) {
        this.mileage = mileage;
    }

    public void setMileageModels(final ArrayList<MileageModel> mileageModels) {
        this.mileageModels = mileageModels;
    }

    public void setNotedDate(final String notedDate) {
        this.notedDate = notedDate;
    }

    public void setPerKms(final String perKms) {
        this.perKms = perKms;
    }

    public void setPrice(final String price) {
        this.price = price;
    }

    public void setToKms(final String toKms) {
        this.toKms = toKms;
    }
}
