package com.mapbox.sdk.testapp.activity.style;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gomap.geojson.Point;
import com.gomap.geojson.Polygon;
import com.gomap.sdk.camera.CameraPosition;
import com.gomap.sdk.camera.CameraUpdateFactory;
import com.gomap.sdk.geometry.LatLng;
import com.gomap.sdk.maps.MapView;
import com.gomap.sdk.maps.Style;
import com.gomap.sdk.style.layers.FillExtrusionLayer;
import com.gomap.sdk.style.sources.GeoJsonSource;
import com.mapbox.sdk.testapp.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.gomap.sdk.style.layers.PropertyFactory.fillExtrusionColor;
import static com.gomap.sdk.style.layers.PropertyFactory.fillExtrusionHeight;
import static com.gomap.sdk.style.layers.PropertyFactory.fillExtrusionOpacity;

/**
 * Test activity showcasing fill extrusions
 */
public class FillExtrusionActivity extends AppCompatActivity {

  private MapView mapView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fill_extrusion_layer);

    mapView = findViewById(R.id.mapView);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(mapboxMap -> {
      mapboxMap.setStyle(Style.getPredefinedStyle("Streets"), style -> {
        List<List<Point>> lngLats = Collections.singletonList(
          Arrays.asList(
            Point.fromLngLat(5.12112557888031, 52.09071040847704),
            Point.fromLngLat(5.121227502822875, 52.09053901776669),
            Point.fromLngLat(5.121484994888306, 52.090601641371805),
            Point.fromLngLat(5.1213884353637695, 52.090766439912635),
            Point.fromLngLat(5.12112557888031, 52.09071040847704)
          )
        );

        Polygon domTower = Polygon.fromLngLats(lngLats);
        GeoJsonSource source = new GeoJsonSource("extrusion-source", domTower);
        style.addSource(source);

        style.addLayer(
          new FillExtrusionLayer("extrusion-layer", source.getId())
            .withProperties(
              fillExtrusionHeight(40f),
              fillExtrusionOpacity(0.5f),
              fillExtrusionColor(Color.RED)
            )
        );

        mapboxMap.animateCamera(
          CameraUpdateFactory.newCameraPosition(
            new CameraPosition.Builder()
              .target(new LatLng(52.09071040847704, 5.12112557888031))
              .tilt(45.0)
              .zoom(18)
              .build()
          ),
          10000
        );
      });
    });
  }


  @Override
  protected void onStart() {
    super.onStart();
    mapView.onStart();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mapView.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    mapView.onPause();
  }

  @Override
  protected void onStop() {
    super.onStop();
    mapView.onStop();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }

}
