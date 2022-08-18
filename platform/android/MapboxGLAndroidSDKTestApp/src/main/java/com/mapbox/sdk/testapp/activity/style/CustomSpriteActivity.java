package com.mapbox.sdk.testapp.activity.style;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.gomap.sdk.camera.CameraUpdateFactory;
import com.gomap.sdk.geometry.LatLng;
import com.gomap.sdk.maps.MapView;
import com.gomap.sdk.maps.MapboxMap;
import com.gomap.sdk.maps.Style;
import com.gomap.sdk.style.layers.Layer;
import com.gomap.sdk.style.layers.SymbolLayer;
import com.gomap.sdk.style.sources.GeoJsonSource;
import com.mapbox.sdk.testapp.R;

import timber.log.Timber;

import static com.gomap.sdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.gomap.sdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.gomap.sdk.style.layers.PropertyFactory.iconImage;

/**
 * Test activity showcasing adding a sprite image and use it in a Symbol Layer
 */
public class CustomSpriteActivity extends AppCompatActivity {

  private static final String CUSTOM_ICON = "custom-icon";

  private GeoJsonSource source;
  private MapboxMap mapboxMap;
  private MapView mapView;
  private Layer layer;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_sprite);

    mapView = findViewById(R.id.mapView);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(map -> {
      mapboxMap = map;

      map.setStyle(Style.getPredefinedStyle("Streets"), style -> {
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setColorFilter(ContextCompat.getColor(CustomSpriteActivity.this, R.color.primary));
        fab.setOnClickListener(new View.OnClickListener() {

          private Point point;

          @Override
          public void onClick(View view) {
            if (point == null) {
              Timber.i("First click -> Car");
              // Add an icon to reference later
              style.addImage(CUSTOM_ICON, BitmapFactory.decodeResource(getResources(), R.drawable.ic_car_top));

              // Add a source with a geojson point
              point = Point.fromLngLat(13.400972d, 52.519003d);
              source = new GeoJsonSource(
                "point",
                FeatureCollection.fromFeatures(new Feature[] {Feature.fromGeometry(point)})
              );
              mapboxMap.getStyle().addSource(source);

              // Add a symbol layer that references that point source
              layer = new SymbolLayer("layer", "point");
              layer.setProperties(
                // Set the id of the sprite to use
                iconImage(CUSTOM_ICON),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
              );

              // lets add a circle below labels!
              mapboxMap.getStyle().addLayerBelow(layer, "water_intermittent");

              fab.setImageResource(R.drawable.ic_directions_car_black);
            } else {
              // Update point
              point = Point.fromLngLat(point.longitude() + 0.001,
                point.latitude() + 0.001);
              source.setGeoJson(FeatureCollection.fromFeatures(new Feature[] {Feature.fromGeometry(point)}));

              // Move the camera as well
              mapboxMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(
                point.latitude(), point.longitude())));
            }
          }
        });
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
