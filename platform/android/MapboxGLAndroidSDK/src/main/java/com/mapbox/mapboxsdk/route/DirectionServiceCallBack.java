package com.mapbox.mapboxsdk.route;

import com.mapbox.mapboxsdk.route.model.DirectionsResponse;

public interface DirectionServiceCallBack {

    public void onCallBack(DirectionsResponse directionsResponse);
}
