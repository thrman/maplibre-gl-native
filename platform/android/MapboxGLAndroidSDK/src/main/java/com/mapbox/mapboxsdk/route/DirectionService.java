package com.mapbox.mapboxsdk.route;


import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.route.model.DirectionsResponse;

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

    public void requestRouteDirection(List<Point> pointList) {
        StringBuilder pointStringBuilder = new StringBuilder("54.428887,24.429133;54.321749,24.458565;");
//        for (Point point:
//             pointList) {
//            pointStringBuilder.append(point.longitude()).append(",").append(point.latitude()).append(";");
//        }
        String pointStr = pointStringBuilder.substring(0,pointStringBuilder.length()-1).toString();

        String url = "https://gomap-dev.kharita.ai/map-route-driving/route/v1/driving/"+pointStr+"?overview=false&alternatives=true&steps=true&annotations=true&geometries=geojson&hints=;";

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
                Log.i("DirectionService result", result);

            }
        });

    }
}
