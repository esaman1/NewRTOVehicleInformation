package com.rtovehicleinformation.Model;

import java.util.ArrayList;

public class VehicleExpenseModel {

    ArrayList<VehicleExpenseModel> expenseModels;
    int i;
    int i1;
    String str;
    String str2;
    String str3;
    String str4;
    String str5;

    public VehicleExpenseModel(final String str, final String str2, final String str3, final String str4, final String str5, final int i) {
        this.str = str;
        this.str2 = str2;
        this.str3 = str3;
        this.str4 = str4;
        this.str5 = str5;
        this.i = i;
    }

    public ArrayList<VehicleExpenseModel> getExpenseModels() {
        return this.expenseModels;
    }

    public int getI() {
        return this.i;
    }

    public int getI1() {
        return this.i1;
    }

    public String getStr() {
        return this.str;
    }

    public String getStr2() {
        return this.str2;
    }

    public String getStr3() {
        return this.str3;
    }

    public String getStr4() {
        return this.str4;
    }

    public String getStr5() {
        return this.str5;
    }

    public void setExpenseModels(final ArrayList<VehicleExpenseModel> expenseModels) {
        this.expenseModels = expenseModels;
    }

    public void setI(final int i) {
        this.i = i;
    }

    public void setI1(final int i1) {
        this.i1 = i1;
    }

    public void setStr(final String str) {
        this.str = str;
    }

    public void setStr2(final String str2) {
        this.str2 = str2;
    }

    public void setStr3(final String str3) {
        this.str3 = str3;
    }

    public void setStr4(final String str4) {
        this.str4 = str4;
    }

    public void setStr5(final String str5) {
        this.str5 = str5;
    }

    public void setTotalAmount(final String s) {
    }

}
