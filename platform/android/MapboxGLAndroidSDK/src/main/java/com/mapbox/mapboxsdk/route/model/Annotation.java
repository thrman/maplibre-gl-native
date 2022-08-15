package com.mapbox.mapboxsdk.route.model;

public class Annotation {

    private int[] datasources;
    private double[] weight;
    private double[] distance;
    private double[] duration;
    private double[] orispeeds;
    private long[] nodes;
    private String[] speedlimit;
    private String[] highway;
    private String[] turnlanes;
    private String[] branchinfo;

    public int[] getDatasources() {
        return datasources;
    }

    public void setDatasources(int[] datasources) {
        this.datasources = datasources;
    }

    public double[] getWeight() {
        return weight;
    }

    public void setWeight(double[] weight) {
        this.weight = weight;
    }

    public double[] getDistance() {
        return distance;
    }

    public void setDistance(double[] distance) {
        this.distance = distance;
    }

    public double[] getDuration() {
        return duration;
    }

    public void setDuration(double[] duration) {
        this.duration = duration;
    }

    public double[] getOrispeeds() {
        return orispeeds;
    }

    public void setOrispeeds(double[] orispeeds) {
        this.orispeeds = orispeeds;
    }

    public long[] getNodes() {
        return nodes;
    }

    public void setNodes(long[] nodes) {
        this.nodes = nodes;
    }

    public String[] getSpeedlimit() {
        return speedlimit;
    }

    public void setSpeedlimit(String[] speedlimit) {
        this.speedlimit = speedlimit;
    }

    public String[] getHighway() {
        return highway;
    }

    public void setHighway(String[] highway) {
        this.highway = highway;
    }

    public String[] getTurnlanes() {
        return turnlanes;
    }

    public void setTurnlanes(String[] turnlanes) {
        this.turnlanes = turnlanes;
    }

    public String[] getBranchinfo() {
        return branchinfo;
    }

    public void setBranchinfo(String[] branchinfo) {
        this.branchinfo = branchinfo;
    }
}
