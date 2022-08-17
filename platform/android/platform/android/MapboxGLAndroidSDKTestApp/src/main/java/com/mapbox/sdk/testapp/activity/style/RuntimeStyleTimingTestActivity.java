package com.mapbox.sdk.testapp.activity.style;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gomap.sdk.maps.MapView;
import com.gomap.sdk.maps.MapboxMap;
import com.gomap.sdk.maps.Style;
import com.gomap.sdk.style.layers.CircleLayer;
import com.gomap.sdk.style.sources.VectorSource;
import com.mapbox.sdk.testapp.R;

import static com.gomap.sdk.style.layers.Property.VISIBLE;
import static com.gomap.sdk.style.layers.PropertyFactory.circleColor;
import static com.gomap.sdk.style.layers.PropertyFactory.circleRadius;
import static com.gomap.sdk.style.layers.PropertyFactory.visibility;

/**
 * Test activity for unit test execution
 */
public class RuntimeStyleTimingTestActivity extends AppCompatActivity {

  public MapView mapView;
  private MapboxMap mapboxMap;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_runtime_style);

    // Initialize map as normal
    mapView = (MapView) findViewById(R.id.mapView);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(mapboxMap -> {
      RuntimeStyleTimingTestActivity.this.mapboxMap = mapboxMap;

      CircleLayer parksLayer = new CircleLayer("parks", "parks_source");
      parksLayer.setSourceLayer("parks");
      parksLayer.setProperties(
        visibility(VISIBLE),
        circleRadius(8f),
        circleColor(Color.argb(1, 55, 148, 179))
      );

      VectorSource parks = new VectorSource("parks_source", "maptiler://sources/7ac429c7-c96e-46dd-8c3e-13d48988986a");
      mapboxMap.setStyle(new Style.Builder()
        .fromUri(Style.getPredefinedStyle("Streets"))
        .withSource(parks)
        .withLayer(parksLayer));
    });
  }

  public MapboxMap getMapboxMap() {
    return mapboxMap;
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
