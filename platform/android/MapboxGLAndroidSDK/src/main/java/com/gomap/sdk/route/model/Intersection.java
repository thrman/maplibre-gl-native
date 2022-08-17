package com.gomap.sdk.route.model;

public class Intersection {

    private int out;
    private double[] location;
    private boolean[] entry;
    private int[] bearings;

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public boolean[] getEntry() {
        return entry;
    }

    public void setEntry(boolean[] entry) {
        this.entry = entry;
    }

    public int[] getBearings() {
        return bearings;
    }

    public void setBearings(int[] bearings) {
        this.bearings = bearings;
    }
}
