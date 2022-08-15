package com.mapbox.mapboxsdk.route.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DirectionsRoute {

    @SerializedName("weight_name")
    private String weightName;
    private double weight;
    private double distance;
    private double duration;
    private List<RouteLeg> legs;

}
