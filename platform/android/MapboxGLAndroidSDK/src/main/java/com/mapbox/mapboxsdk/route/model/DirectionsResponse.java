package com.mapbox.mapboxsdk.route.model;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.PointAsCoordinatesTypeAdapter;

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



}

