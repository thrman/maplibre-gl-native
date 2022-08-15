package com.mapbox.mapboxsdk.route.model;

import java.util.List;

public class LegStep {

    private double weight;
    private double distance;
    private double duration;
    private Geometry geometry;
    private String mode;
    private String driving_side;
    private String name;
    private List<Intersection> intersections;
    private Maneuver maneuver ;

}
