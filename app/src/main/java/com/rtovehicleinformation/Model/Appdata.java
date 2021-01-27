package com.rtovehicleinformation.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Appdata implements Serializable {

    @SerializedName("application_status")
    @Expose
    private String application_status;

    @SerializedName("application_icon")
    @Expose
    private String application_icon;


    @SerializedName("application_name")
    @Expose
    private String application_name;


    @SerializedName("application_link")
    @Expose
    private String application_link;

    public Appdata() {
    }


    public Appdata(String application_status, String application_icon, String application_name, String application_link) {
        super();
        this.application_status = application_status;
        this.application_icon = application_icon;
        this.application_name = application_name;
        this.application_link = application_link;

    }

    public String getApplication_status() {
        return application_status;
    }

    public void setApplication_status(String application_status) {
        this.application_status = application_status;
    }

    public String getApplication_icon() {
        return application_icon;
    }

    public void setApplication_icon(String application_icon) {
        this.application_icon = application_icon;
    }

    public String getApplication_name() {
        return application_name;
    }

    public void setApplication_name(String application_name) {
        this.application_name = application_name;
    }

    public String getApplication_link() {
        return application_link;
    }

    public void setApplication_link(String application_link) {
        this.application_link = application_link;
    }
}
