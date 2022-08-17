package com.mapbox.sdk.testapp.activity.style;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gomap.sdk.maps.MapView;
import com.gomap.sdk.maps.MapboxMap;
import com.gomap.sdk.maps.Style;
import com.gomap.sdk.style.layers.HillshadeLayer;
import com.gomap.sdk.style.sources.RasterDemSource;
import com.mapbox.sdk.testapp.R;

/**
 * Test activity showcasing using HillshadeLayer.
 */
public class HillshadeLayerActivity extends AppCompatActivity {

  private static final String LAYER_ID = "hillshade";
  private static final String LAYER_BELOW_ID = "water_intermittent";
  private static final String SOURCE_ID = "terrain-rgb";
  private static final String SOURCE_URL = "maptiler://sources/terrain-rgb";

  private MapView mapView;
  private MapboxMap mapboxMap;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fill_extrusion_layer);

    mapView = findViewById(R.id.mapView);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(map -> {
      mapboxMap = map;

      RasterDemSource rasterDemSource = new RasterDemSource(SOURCE_ID, SOURCE_URL);
      HillshadeLayer hillshadeLayer = new HillshadeLayer(LAYER_ID, SOURCE_ID);
      mapboxMap.setStyle(new Style.Builder()
        .fromUri(Style.getPredefinedStyle("Streets"))
        .withLayerBelow(hillshadeLayer, LAYER_BELOW_ID)
        .withSource(rasterDemSource)
      );
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