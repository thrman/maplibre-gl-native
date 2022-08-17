package com.mapbox.sdk.testapp.activity.textureview;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.gomap.sdk.camera.CameraPosition;
import com.gomap.sdk.geometry.LatLng;
import com.gomap.sdk.maps.MapView;
import com.gomap.sdk.maps.MapboxMap;
import com.gomap.sdk.maps.MapboxMapOptions;
import com.gomap.sdk.maps.Style;
import com.mapbox.sdk.testapp.R;
import com.mapbox.sdk.testapp.utils.ResourceUtils;

import java.io.IOException;

import timber.log.Timber;

/**
 * Example showcasing how to create a TextureView with a transparent background.
 */
public class TextureViewTransparentBackgroundActivity extends AppCompatActivity {

  private MapView mapView;
  private MapboxMap mapboxMap;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_textureview_transparent);
    setupBackground();
    setupMapView(savedInstanceState);
  }

  private void setupBackground() {
    ImageView imageView = findViewById(R.id.imageView);
    imageView.setImageResource(R.drawable.water);
    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
  }

  private void setupMapView(Bundle savedInstanceState) {
    MapboxMapOptions mapboxMapOptions = MapboxMapOptions.createFromAttributes(this, null);
    mapboxMapOptions.translucentTextureSurface(true);
    mapboxMapOptions.textureMode(true);
    mapboxMapOptions.camera(new CameraPosition.Builder()
      .zoom(2)
      .target(new LatLng(48.507879, 8.363795))
      .build()
    );

    mapView = new MapView(this, mapboxMapOptions);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(this::initMap);
    ((ViewGroup) findViewById(R.id.coordinator_layout)).addView(mapView);
  }

  private void initMap(MapboxMap mapboxMap) {
    try {
      mapboxMap.setStyle(
        new Style.Builder().fromJson(ResourceUtils.readRawResource(this, R.raw.no_bg_style))
      );
    } catch (IOException exception) {
      Timber.e(exception);
    }
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
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }
}