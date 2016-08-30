package com.example.wangfeng.lovepresent;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.wangfeng.lovepresent.dao.PicDao;
import com.example.wangfeng.lovepresent.model.PicModel;

import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private MapView mMapView;
    private BaiduMap mBaiduMap;

    private List<PicModel> mPosList;
    private String mPrePos = "";
    private int mCusIndex = 0;
    private int status = STATUS.START;

    public interface  STATUS{
        public static final int START = 0;
        public static final int HEART = 1;
        public static final int END = 2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mMapView = (MapView)findViewById(R.id.bmapView);

        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMarkerClickListener(new MyOnMarkerClickListener());
        LatLng point = new LatLng(30.250478,120.152583);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(point)
                .zoom(14.6f)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);

        initPosData();
    }

    private void initPosData(){
        mPosList = PicDao.queryAllPos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            status = -1;
            showAllMarker();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showAllMarker(){
        String posArr[];
        while(mCusIndex++ < mPosList.size()){
            PicModel picModel = mPosList.get(mCusIndex - 1);
            if(picModel.getLatlng().equals(mPrePos)) continue;
            mPrePos = picModel.getLatlng();
            posArr = picModel.getLatlng().split(",");
            double pos1 = Double.parseDouble(posArr[0]);
            double pos2 = Double.parseDouble(posArr[1]);
            LatLng point = new LatLng(pos1,pos2);
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.heart);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
            //在地图上添加Marker，并显示


            Marker marker= (Marker)mBaiduMap.addOverlay(option);
            Bundle bundle = new Bundle();
            bundle.putString("latlng", mPrePos);
            marker.setExtraInfo(bundle);
        }
    }
    @Override
    public void onClick(View v) {
        showNext();
    }

    private void showNext(){

        String posArr[];
        while(mCusIndex++ < mPosList.size()){
            PicModel picModel = mPosList.get(mCusIndex - 1);
            if(picModel.getLatlng().equals(mPrePos)) continue;
            mPrePos = picModel.getLatlng();
            posArr = picModel.getLatlng().split(",");
            double pos1 = Double.parseDouble(posArr[0]);
            double pos2 = Double.parseDouble(posArr[1]);
            LatLng point = new LatLng(pos1,pos2);
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.heart);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
            //在地图上添加Marker，并显示


            Marker marker= (Marker)mBaiduMap.addOverlay(option);
            Bundle bundle = new Bundle();
            bundle.putString("latlng", mPrePos);
            marker.setExtraInfo(bundle);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
                    intent.putExtra("latlng", mPrePos);
                    startActivity(intent);
                }
            }, 1000);
            break;
        }
        if(mCusIndex > mPosList.size()){
            status = STATUS.END;
            onStart();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(status == -1) return;
        if(status == STATUS.START) {
            Intent intent = new Intent(MainActivity.this, LensFocusActivity.class);
            intent.putExtra("status", STATUS.START);
            startActivity(intent);
            status = STATUS.HEART;
        }else if (status == STATUS.HEART) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showNext();
                }
            }, 1000);
        }else if(status == STATUS.END) {
            Intent intent = new Intent(MainActivity.this, LensFocusActivity.class);
            intent.putExtra("status", STATUS.END);
            startActivity(intent);
            status = -1;
        }
    }

    class MyOnMarkerClickListener implements BaiduMap.OnMarkerClickListener {
        @Override
        public boolean onMarkerClick(Marker marker) {
            Bundle bundle = marker.getExtraInfo();
            String latlng = bundle.getString("latlng");
            Intent intent = new Intent(MainActivity.this,ViewPagerActivity.class);
            intent.putExtra("latlng",latlng);
            startActivity(intent);
            return false;
        }
    }
}
