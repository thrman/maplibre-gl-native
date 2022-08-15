package com.mapbox.mapboxsdk.route.model;

public class Maneuver {
    private int bearing_after;
    private String[] location;
    private String type;
    private int bearing_before;
    private String modifier;

    public int getBearing_after() {
        return bearing_after;
    }

    public void setBearing_after(int bearing_after) {
        this.bearing_after = bearing_after;
    }

    public String[] getLocation() {
        return location;
    }

    public void setLocation(String[] location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBearing_before() {
        return bearing_before;
    }

    public void setBearing_before(int bearing_before) {
        this.bearing_before = bearing_before;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
}
