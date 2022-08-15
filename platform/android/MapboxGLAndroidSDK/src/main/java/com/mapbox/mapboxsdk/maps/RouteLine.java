package com.mapbox.mapboxsdk.maps;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;

import com.mapbox.mapboxsdk.annotations.Polyline;


public class RouteLine {

    private AnnotationManager annotationManager;

    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public void drawLine(){

        mainHandler.post(new Runnable() {
            @Override
            public void run() {

//                polylineOptions.color(Color.parseColor("#6495ED"));
//                Polyline polylines = mapboxMap.addPolyline(polylineOptions);

            }
        });

    }

}
