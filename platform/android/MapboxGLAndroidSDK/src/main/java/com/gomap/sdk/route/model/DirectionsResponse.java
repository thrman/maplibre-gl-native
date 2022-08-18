package com.gomap.sdk.route.model;



import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This is the root Mapbox Directions API response. Inside this class are several nested classes
 * chained together to make up a similar structure to the original APIs JSON response.
 *
 * @see <a href="https://www.mapbox.com/api-documentation/navigation/#directions-response-object">Direction
 *   Response Object</a>
 * @since 1.0.0
 */
public  class DirectionsResponse{

    private  String code;
    private long versions;
    @SerializedName("input_coordinates")
    private List<InputCoordinate> inputCoordinates;
    private List<DirectionsWaypoint> waypoints;
    private List<DirectionsRoute> routes;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getVersions() {
        return versions;
    }

    public void setVersions(long versions) {
        this.versions = versions;
    }

    public List<InputCoordinate> getInputCoordinates() {
        return inputCoordinates;
    }

    public void setInputCoordinates(List<InputCoordinate> inputCoordinates) {
        this.inputCoordinates = inputCoordinates;
    }

    public List<DirectionsWaypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<DirectionsWaypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public List<DirectionsRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(List<DirectionsRoute> routes) {
        this.routes = routes;
    }
}

