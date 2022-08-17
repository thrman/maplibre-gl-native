package com.gomap.sdk.route.model;

import java.util.List;

public class Geometry {

    private List<double[]> coordinates ;
    private String type;

    public List<double[]> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<double[]> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
