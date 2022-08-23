package com.gomap.sdk.poi;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.provider.Settings;
import android.util.Log;

import com.gomap.geojson.Point;
import com.gomap.sdk.constants.MapboxConstants;
import com.gomap.sdk.geometry.LatLng;
import com.gomap.sdk.route.DirectionServiceCallBack;
import com.gomap.sdk.route.model.DirectionsResponse;
import com.gomap.sdk.route.model.RouteType;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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

    @SuppressLint("LongLogTag")
    /**
     * center
     * radius
     * // {"location":{"lng":"54.123456","lat":"24.123456"},"imei":"xxxx","center":{"lng":"54.123456","lat":"24.123456"},"radius":15000}
     *表示 以center 点为中心，半径为 radius 范围内的 poi点数据
     */
    public synchronized void requestPoi(LatLng center , int radius, Activity activity, final NetCallBack callBack) {

        clearRequest();

        String url = MapboxConstants.BASE_API_URL + "/api-server/api/poi/near-by/search";

        HashMap map = new HashMap<String,Object>();
        map.put("imei", Settings.System.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID));
        map.put("radius",15000);

        HashMap location = new HashMap<String,Object>();
        location.put("lng",center.getLongitude());
        location.put("lat",center.getLatitude());
        map.put("location",location);

        HashMap centerMap = new HashMap<String,Object>();
        centerMap.put("lng",center.getLongitude());
        centerMap.put("lat",center.getLatitude());
        map.put("center",centerMap);

        String requestStr = new Gson().toJson(map);

        Log.i("NetUtils",requestStr);

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
