package com.example.wangfeng.lovepresent.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by wangfeng on 15/08/14.
 */
@Table(name = "pic")
public class PicModel extends Model implements Serializable {

    @Column(name = "latlng",index = true)
    private String latlng;

    @Column(name = "pic_path")
    private String picPath;

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
