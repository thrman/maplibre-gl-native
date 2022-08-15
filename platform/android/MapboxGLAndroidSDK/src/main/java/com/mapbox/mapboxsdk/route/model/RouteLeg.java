package com.mapbox.mapboxsdk.route.model;

import java.util.List;

public class RouteLeg {
    private double weight;
    private double distance;
    private double duration;
    private String summary;
    private List<LegStep> steps;
    private Annotation annotation;

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<LegStep> getSteps() {
        return steps;
    }

    public void setSteps(List<LegStep> steps) {
        this.steps = steps;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }
}
