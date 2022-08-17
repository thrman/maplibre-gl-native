package com.mapbox.sdk.testapp.activity.style;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gomap.sdk.maps.MapView;
import com.gomap.sdk.maps.MapboxMap;
import com.gomap.sdk.maps.OnMapReadyCallback;
import com.gomap.sdk.maps.Style;
import com.gomap.sdk.style.layers.LineLayer;
import com.gomap.sdk.style.sources.GeoJsonOptions;
import com.gomap.sdk.style.sources.GeoJsonSource;
import com.mapbox.sdk.testapp.R;
import com.mapbox.sdk.testapp.utils.ResourceUtils;

import java.io.IOException;

import timber.log.Timber;

import static com.gomap.sdk.style.expressions.Expression.interpolate;
import static com.gomap.sdk.style.expressions.Expression.lineProgress;
import static com.gomap.sdk.style.expressions.Expression.linear;
import static com.gomap.sdk.style.expressions.Expression.rgb;
import static com.gomap.sdk.style.expressions.Expression.stop;
import static com.gomap.sdk.style.layers.Property.LINE_CAP_ROUND;
import static com.gomap.sdk.style.layers.Property.LINE_JOIN_ROUND;
import static com.gomap.sdk.style.layers.PropertyFactory.lineCap;
import static com.gomap.sdk.style.layers.PropertyFactory.lineColor;
import static com.gomap.sdk.style.layers.PropertyFactory.lineGradient;
import static com.gomap.sdk.style.layers.PropertyFactory.lineJoin;
import static com.gomap.sdk.style.layers.PropertyFactory.lineWidth;

/**
 * Activity showcasing applying a gradient coloring to a line layer.
 */
public class GradientLineActivity extends AppCompatActivity implements OnMapReadyCallback {

  public static final String LINE_SOURCE = "gradient";
  private MapView mapView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gradient_line);

    mapView = findViewById(R.id.mapView);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(this);
  }

  @Override
  public void onMapReady(@NonNull MapboxMap mapboxMap) {
    try {
      String geoJson = ResourceUtils.readRawResource(GradientLineActivity.this, R.raw.test_line_gradient_feature);
      mapboxMap.setStyle(new Style.Builder()
        .withSource(new GeoJsonSource(LINE_SOURCE, geoJson, new GeoJsonOptions().withLineMetrics(true)))
        .withLayer(new LineLayer("gradient", LINE_SOURCE)
          .withProperties(
            lineGradient(interpolate(
              linear(), lineProgress(),
              stop(0f, rgb(0, 0, 255)),
              stop(0.5f, rgb(0, 255, 0)),
              stop(1f, rgb(255, 0, 0)))
            ),
            lineColor(Color.RED),
            lineWidth(10.0f),
            lineCap(LINE_CAP_ROUND),
            lineJoin(LINE_JOIN_ROUND)
          ))
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
