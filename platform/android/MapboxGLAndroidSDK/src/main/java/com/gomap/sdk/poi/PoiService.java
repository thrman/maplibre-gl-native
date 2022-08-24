package com.gomap.sdk.poi;


import android.util.Log;

import com.gomap.sdk.constants.MapboxConstants;
import com.gomap.sdk.geometry.LatLng;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PoiService {

    private Call call ;
    private PoiService(){}

    static class DirectionServiceHolder{
        static PoiService instance = new PoiService();
    }

    public static PoiService getInstance(){
        return DirectionServiceHolder.instance;
    }

    /**
     * 关键系搜素 ，结果含有推荐类型，一般是第一个
     *
     * @param keyword
     * @param androidId
     * @param location
     * @param callBack
     */
    public synchronized void requestPoiAsInputKeyword( String keyword,String androidId,LatLng location , final NetCallBack callBack) {

        clearRequest();

        String url = MapboxConstants.BASE_API_URL + "/api-server/api/poi/input-prompt/search";

        HashMap map = new HashMap<String,Object>();
        map.put("imei", androidId);
        map.put("name",keyword);

        HashMap locationMap = new HashMap<String,Object>();
        locationMap.put("lng",location.getLongitude());
        locationMap.put("lat",location.getLatitude());
        map.put("location",locationMap);

        String requestStr = new Gson().toJson(map);

        Log.i("PoiService",requestStr);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), requestStr);

        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();

        call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onCallBack(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                callBack.onCallBack(result);
            }
        });

    }

    /**
     *keyword 基础上 关键字搜索
     * @param keyword keyword
     * @param androidId
     * @param location
     * @param callBack
     */
    public synchronized void requestPoiAsKeyword( String keyword,String androidId,LatLng location , final NetCallBack callBack) {

        clearRequest();

        String url = MapboxConstants.BASE_API_URL + "/api-server/api/poi/weight/search";

        HashMap map = new HashMap<String,Object>();
        map.put("imei", androidId);
        map.put("name",keyword);

        HashMap locationMap = new HashMap<String,Object>();
        locationMap.put("lng",location.getLongitude());
        locationMap.put("lat",location.getLatitude());
        map.put("location",locationMap);

        String requestStr = new Gson().toJson(map);

        Log.i("PoiService",requestStr);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), requestStr);

        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();

        call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onCallBack(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                callBack.onCallBack(result);
            }
        });

    }

    /**
     * 在keyword 基础上  范围搜索
     * @param keyword
     * @param androidId
     * @param location
     * @param center
     * @param radius
     * @param callBack
     */
    public synchronized void requestPoiAsRadius( String keyword,String androidId,LatLng location , LatLng center , int radius, final NetCallBack callBack) {

        clearRequest();

        String url = MapboxConstants.BASE_API_URL + "/api-server/api/poi/weight/search";

        HashMap map = new HashMap<String,Object>();
        map.put("imei", androidId);
        map.put("name",keyword);

        HashMap locationMap = new HashMap<String,Object>();
        locationMap.put("lng",location.getLongitude());
        locationMap.put("lat",location.getLatitude());
        map.put("location",locationMap);

        HashMap centerMap = new HashMap<String,Object>();
        centerMap.put("lng",center.getLongitude());
        centerMap.put("lat",center.getLatitude());
        map.put("center",centerMap);

        map.put("radius",radius);

        String requestStr = new Gson().toJson(map);

        Log.i("PoiService",requestStr);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), requestStr);

        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();

        call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onCallBack(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                callBack.onCallBack(result);
            }
        });

    }

    /**
     *
     * @param name
     * @param androidId
     * @param location
     * @param callBack
     */
    public synchronized void requestPoiAsType( String name,String androidId,LatLng location , String[] fields,final NetCallBack callBack) {

        clearRequest();

        String url = MapboxConstants.BASE_API_URL + "/api-server/api/poi/weight/search";

        HashMap map = new HashMap<String,Object>();
        map.put("imei", androidId);
        map.put("name",name);

        HashMap locationMap = new HashMap<String,Object>();
        locationMap.put("lng",location.getLongitude());
        locationMap.put("lat",location.getLatitude());
        map.put("location",locationMap);
        map.put("fields",fields);

        String requestStr = new Gson().toJson(map);

        Log.i("PoiService",requestStr);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), requestStr);

        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();

        call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onCallBack(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                callBack.onCallBack(result);
            }
        });

    }

    /**
     * location 当前定位
     * center  地图中心点
     * radius  范围
     * // {"location":{"lng":"54.123456","lat":"24.123456"},"imei":"xxxx","center":{"lng":"54.123456","lat":"24.123456"},"radius":15000}
     *表示 以center 点为中心，半径为 radius 范围内的 poi点数据
     */
    public synchronized void requestNearbyPoi(LatLng location , LatLng center ,String androidId, int radius, final NetCallBack callBack) {

        clearRequest();

        String url = MapboxConstants.BASE_API_URL + "/api-server/api/poi/near-by/search";

        HashMap map = new HashMap<String,Object>();
        map.put("imei", androidId);
        map.put("radius",radius);

        HashMap locationMap = new HashMap<String,Object>();
        locationMap.put("lng",location.getLongitude());
        locationMap.put("lat",location.getLatitude());
        map.put("location",locationMap);

        HashMap centerMap = new HashMap<String,Object>();
        centerMap.put("lng",center.getLongitude());
        centerMap.put("lat",center.getLatitude());
        map.put("center",centerMap);

        String requestStr = new Gson().toJson(map);

        Log.i("PoiService",requestStr);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), requestStr);

        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();

        call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onCallBack(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                callBack.onCallBack(result);
            }
        });

    }

    public void clearRequest(){
        if (call != null){
            call.cancel();
        }
    }

    public interface NetCallBack {
        public void onCallBack(String response);
    }
}
