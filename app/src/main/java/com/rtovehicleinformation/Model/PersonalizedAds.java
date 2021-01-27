package com.rtovehicleinformation.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class PersonalizedAds implements Serializable
{
    @Expose
    @SerializedName("banner")
    private Banner banner;
    @Expose
    @SerializedName("code")
    private String code;

    public Banner getBanner() {
        return this.banner;
    }

    public String getCode() {
        return this.code;
    }

    public void setBanner(final Banner banner) {
        this.banner = banner;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public static class Banner
    {
        @Expose
        @SerializedName("add_type_id")
        private String add_type_id;
        @Expose
        @SerializedName("app_id")
        private String app_id;
        @Expose
        @SerializedName("app_name")
        private String app_name;
        @Expose
        @SerializedName("file_path")
        private String file_path;
        @Expose
        @SerializedName("id")
        private String id;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("package_name")
        private String package_name;

        public String getAdd_type_id() {
            return this.add_type_id;
        }

        public String getApp_id() {
            return this.app_id;
        }

        public String getApp_name() {
            return this.app_name;
        }

        public String getFile_path() {
            return this.file_path;
        }

        public String getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public String getPackage_name() {
            return this.package_name;
        }

        public void setAdd_type_id(final String add_type_id) {
            this.add_type_id = add_type_id;
        }

        public void setApp_id(final String app_id) {
            this.app_id = app_id;
        }

        public void setApp_name(final String app_name) {
            this.app_name = app_name;
        }

        public void setFile_path(final String file_path) {
            this.file_path = file_path;
        }

        public void setId(final String id) {
            this.id = id;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public void setPackage_name(final String package_name) {
            this.package_name = package_name;
        }
    }
}
