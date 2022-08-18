package com.mapbox.sdk.testapp.activity.maplayout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gomap.sdk.camera.CameraPosition;
import com.gomap.sdk.camera.CameraUpdateFactory;
import com.gomap.sdk.geometry.LatLng;
import com.gomap.sdk.maps.MapView;
import com.gomap.sdk.maps.Style;
import com.mapbox.sdk.testapp.R;

/**
 * Test activity that displays the city of Suzhou with a mixture of server-generated
 * latin glyphs and CJK glyphs generated locally using "Droid Sans" as a font family.
 */
public class LocalGlyphActivity extends AppCompatActivity {
  private MapView mapView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_local_glyph);

    mapView = findViewById(R.id.mapView);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(mapboxMap -> {
      mapboxMap.setStyle(Style.getPredefinedStyle("Streets"));
      // Set initial position to Suzhou
      mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(
        new CameraPosition.Builder()
          .target(new LatLng(31.3003, 120.7457))
          .zoom(11)
          .bearing(0)
          .tilt(0)
          .build()));
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
  public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
  }
}
