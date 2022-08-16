package com.mapbox.mapboxsdk.route;


import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.constants.MapboxConstants;
import com.mapbox.mapboxsdk.route.model.DirectionsResponse;
import com.mapbox.mapboxsdk.route.model.RouteType;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DirectionService {

    private DirectionService(){}

    static class DirectionServiceHolder{
        static DirectionService instance = new DirectionService();
    }

    public static DirectionService getInstance(){
        return DirectionServiceHolder.instance;
    }

    public void requestRouteDirection(List<Point> pointList, final DirectionServiceCallBack callBack) {
        requestRouteDirection(RouteType.driving,pointList,callBack);
    }

    @SuppressLint("LongLogTag")
    public void requestRouteDirection(RouteType routeType, List<Point> pointList, final DirectionServiceCallBack callBack) {
        StringBuilder pointStringBuilder = new StringBuilder();
        for (Point point:
                pointList) {
            pointStringBuilder.append(point.longitude()).append(",").append(point.latitude()).append(";");
        }
        String pointStr = pointStringBuilder.substring(0,pointStringBuilder.length()-1).toString();

        StringBuilder stringBuilder = new StringBuilder(MapboxConstants.BASE_API_URL);
        if (routeType == RouteType.cycling){
            stringBuilder.append("/map-route-cycling/route/v1/cycling/");
        }else if (routeType == RouteType.walking){
            stringBuilder.append("/map-route-walking/route/v1/walking/");
        }else {
            stringBuilder.append("/map-route-driving/route/v1/driving/");
        }
        stringBuilder.append(pointStr+"?overview=false&alternatives=true&steps=true&annotations=true&geometries=geojson&hints=;");
        String url = stringBuilder.toString();

        Log.i("DirectionService requestRouteDirection", url);
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        final Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("DirectionService onFailure", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                DirectionsResponse directionsResponse = new Gson().fromJson(result,DirectionsResponse.class);
                callBack.onCallBack(directionsResponse);
                Log.i("DirectionService result", result);

            }
        });

    }
}
