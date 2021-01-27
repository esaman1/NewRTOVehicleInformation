package com.rtovehicleinformation.Model;

public class RTOListInfo {

    private String address;
    private String id;
    private String location;
    private String phoneNo;

    public RTOListInfo(final String id, final String location, final String address, final String phoneNo) {
        this.id = id;
        this.location = location;
        this.address = address;
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return this.address;
    }

    public String getId() {
        return this.id;
    }

    public String getLocation() {
        return this.location;
    }

    public String getPhoneNo() {
        return this.phoneNo;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public void setPhoneNo(final String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
