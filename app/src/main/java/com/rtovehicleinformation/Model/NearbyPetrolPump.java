package com.rtovehicleinformation.Model;

import java.io.Serializable;

public class NearbyPetrolPump implements Serializable
{
    String address;
    double d;
    double d2;
    String distance;
    int icon;
    String name;
    String str3;
    String str5;

    public NearbyPetrolPump(final String name, final String address, final String str3, final String distance, final String str4, final int icon, final double d, final double d2) {
        this.name = name;
        this.address = address;
        this.str3 = str3;
        this.distance = distance;
        this.str5 = str4;
        this.icon = icon;
        this.d = d;
        this.d2 = d2;
    }

    public String getAddress() {
        return this.address;
    }

    public double getD() {
        return this.d;
    }

    public double getD2() {
        return this.d2;
    }

    public String getDistance() {
        return this.distance;
    }

    public int getIcon() {
        return this.icon;
    }

    public String getName() {
        return this.name;
    }

    public String getStr3() {
        return this.str3;
    }

    public String getStr5() {
        return this.str5;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setD(final double d) {
        this.d = d;
    }

    public void setD2(final double d2) {
        this.d2 = d2;
    }

    public void setDistance(final String distance) {
        this.distance = distance;
    }

    public void setIcon(final int icon) {
        this.icon = icon;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setStr3(final String str3) {
        this.str3 = str3;
    }

    public void setStr5(final String str5) {
        this.str5 = str5;
    }
}
