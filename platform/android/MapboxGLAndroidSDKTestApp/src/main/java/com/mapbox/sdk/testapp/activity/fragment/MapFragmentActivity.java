package com.mapbox.sdk.testapp.activity.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gomap.sdk.camera.CameraPosition;
import com.gomap.sdk.camera.CameraUpdateFactory;
import com.gomap.sdk.geometry.LatLng;
import com.gomap.sdk.maps.MapFragment;
import com.gomap.sdk.maps.MapView;
import com.gomap.sdk.maps.MapboxMap;
import com.gomap.sdk.maps.MapboxMapOptions;
import com.gomap.sdk.maps.OnMapReadyCallback;
import com.gomap.sdk.maps.Style;
import com.mapbox.sdk.testapp.R;

/**
 * Test activity showcasing using the MapFragment API using SDK Fragments.
 * <p>
 * Uses MapboxMapOptions to initialise the Fragment.
 * </p>
 */
public class MapFragmentActivity extends AppCompatActivity implements MapFragment.OnMapViewReadyCallback,
  OnMapReadyCallback, MapView.OnDidFinishRenderingFrameListener {

  private static final String TAG = "com.mapbox.map";
  private MapboxMap mapboxMap;
  private MapView mapView;
  private boolean initialCameraAnimation = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map_fragment);

    MapFragment mapFragment;
    if (savedInstanceState == null) {
      mapFragment = MapFragment.newInstance(createFragmentOptions());
      getFragmentManager()
        .beginTransaction()
        .add(R.id.fragment_container, mapFragment, TAG)
        .commit();
    } else {
      mapFragment = (MapFragment) getFragmentManager().findFragmentByTag(TAG);
    }
    mapFragment.getMapAsync(this);
  }

  private MapboxMapOptions createFragmentOptions() {
    MapboxMapOptions options = MapboxMapOptions.createFromAttributes(this, null);

    options.scrollGesturesEnabled(false);
    options.zoomGesturesEnabled(false);
    options.tiltGesturesEnabled(false);
    options.rotateGesturesEnabled(false);
    options.debugActive(false);

    LatLng dc = new LatLng(38.90252, -77.02291);

    options.minZoomPreference(9);
    options.maxZoomPreference(11);
    options.camera(new CameraPosition.Builder()
      .target(dc)
      .zoom(11)
      .build());
    return options;
  }

  @Override
  public void onMapViewReady(MapView map) {
    mapView = map;
    mapView.addOnDidFinishRenderingFrameListener(this);
  }

  @Override
  public void onMapReady(@NonNull MapboxMap map) {
    mapboxMap = map;
    mapboxMap.setStyle(Style.getPredefinedStyle("Outdoor"));
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mapView != null) {
      mapView.removeOnDidFinishRenderingFrameListener(this);
    }
  }

  @Override
  public void onDidFinishRenderingFrame(boolean fully) {
    if (initialCameraAnimation && fully && mapboxMap != null) {
      mapboxMap.animateCamera(
        CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().tilt(45.0).build()), 5000);
      initialCameraAnimation = false;
    }
  }
}