package com.gomap.sdk.maps;

import android.os.Handler;
import android.os.Looper;


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
