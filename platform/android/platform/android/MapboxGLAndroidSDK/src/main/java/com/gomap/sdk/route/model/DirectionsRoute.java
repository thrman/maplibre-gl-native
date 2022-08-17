package com.gomap.sdk.route.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DirectionsRoute {

    @SerializedName("weight_name")
    private String weightName;
    private double weight;
    private double distance;
    private double duration;
    private List<RouteLeg> legs;

    public String getWeightName() {
        return weightName;
    }

    public void setWeightName(String weightName) {
        this.weightName = weightName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public List<RouteLeg> getLegs() {
        return legs;
    }

    public void setLegs(List<RouteLeg> legs) {
        this.legs = legs;
    }
}
