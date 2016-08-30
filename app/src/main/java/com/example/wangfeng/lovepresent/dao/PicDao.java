package com.example.wangfeng.lovepresent.dao;

import android.support.annotation.NonNull;

import com.activeandroid.query.Select;
import com.example.wangfeng.lovepresent.model.PicModel;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by wangfeng on 15/8/15.
 */
public class PicDao {
    public static List<PicModel> query(String key){
        Select select = new Select();
        return select.from(PicModel.class)
                                .where("latlng = ?",key)
                                .execute();
    }
    public static List<PicModel> queryAllPos(){
        Select select = new Select();
        return select.from(PicModel.class)
                .orderBy("id")
                .execute();
    }
}
