package com.mapbox.sdk.testapp.activity.style;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.gomap.geojson.Feature;
import com.gomap.geojson.FeatureCollection;
import com.gomap.geojson.Point;
import com.gomap.sdk.geometry.LatLng;
import com.gomap.sdk.maps.MapView;
import com.gomap.sdk.maps.MapboxMap;
import com.gomap.sdk.maps.Style;
import com.gomap.sdk.style.layers.Property;
import com.gomap.sdk.style.layers.SymbolLayer;
import com.gomap.sdk.style.sources.GeoJsonSource;
import com.mapbox.sdk.testapp.R;

import java.util.List;

import timber.log.Timber;

import static com.gomap.sdk.style.expressions.Expression.get;
import static com.gomap.sdk.style.expressions.Expression.literal;
import static com.gomap.sdk.style.expressions.Expression.step;
import static com.gomap.sdk.style.expressions.Expression.stop;
import static com.gomap.sdk.style.expressions.Expression.switchCase;
import static com.gomap.sdk.style.expressions.Expression.zoom;
import static com.gomap.sdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.gomap.sdk.style.layers.PropertyFactory.iconImage;
import static com.gomap.sdk.style.layers.PropertyFactory.iconSize;
import static com.gomap.sdk.style.layers.PropertyFactory.visibility;

/**
 * Test activity showcasing changing the icon with a zoom function and adding selection state to a SymbolLayer.
 */
public class ZoomFunctionSymbolLayerActivity extends AppCompatActivity {

  private static final String LAYER_ID = "symbolLayer";
  private static final String SOURCE_ID = "poiSource";
  private static final String BUS_MAKI_ICON_ID = "bus";
  private static final String CAFE_MAKI_ICON_ID = "cafe-11";
  private static final String KEY_PROPERTY_SELECTED = "selected";
  private static final float ZOOM_STOP_MAX_VALUE = 12.0f;

  private MapView mapView;
  private MapboxMap mapboxMap;
  private GeoJsonSource source;
  private SymbolLayer layer;

  private boolean isInitialPosition = true;
  private boolean isSelected = false;
  private boolean isShowingSymbolLayer = true;

  private MapboxMap.OnMapClickListener mapClickListener = new MapboxMap.OnMapClickListener() {
    @Override
    public boolean onMapClick(@NonNull LatLng point) {
      PointF screenPoint = mapboxMap.getProjection().toScreenLocation(point);
      List<Feature> featureList = mapboxMap.queryRenderedFeatures(screenPoint, LAYER_ID);
      if (!featureList.isEmpty()) {
        Feature feature = featureList.get(0);
        boolean selectedNow = feature.getBooleanProperty(KEY_PROPERTY_SELECTED);
        isSelected = !selectedNow;
        updateSource(mapboxMap.getStyle());
      } else {
        Timber.e("No features found");
      }
      return true;
    }
  };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_zoom_symbol_layer);

    mapView = findViewById(R.id.mapView);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(map -> {
      mapboxMap = map;

      map.setStyle(Style.getPredefinedStyle("Streets"), style -> {
        updateSource(style);
        addLayer(style);
        map.addOnMapClickListener(mapClickListener);
      });
    });
  }

  private void updateSource(Style style) {
    FeatureCollection featureCollection = createFeatureCollection();
    if (source != null) {
      source.setGeoJson(featureCollection);
    } else {
      source = new GeoJsonSource(SOURCE_ID, featureCollection);
      style.addSource(source);
    }
  }

  private void toggleSymbolLayerVisibility() {
    layer.setProperties(
      visibility(isShowingSymbolLayer ? Property.NONE : Property.VISIBLE)
    );
    isShowingSymbolLayer = !isShowingSymbolLayer;
  }

  private FeatureCollection createFeatureCollection() {
    Point point = isInitialPosition
      ? Point.fromLngLat(-74.01618140, 40.701745)
      : Point.fromLngLat(-73.988097, 40.749864);

    JsonObject properties = new JsonObject();
    properties.addProperty(KEY_PROPERTY_SELECTED, isSelected);
    Feature feature = Feature.fromGeometry(point, properties);
    return FeatureCollection.fromFeatures(new Feature[] {feature});
  }

  private void addLayer(Style style) {
    layer = new SymbolLayer(LAYER_ID, SOURCE_ID);
    layer.setProperties(
      iconImage(
        step(zoom(), literal(BUS_MAKI_ICON_ID),
          stop(ZOOM_STOP_MAX_VALUE, CAFE_MAKI_ICON_ID)
        )
      ),
      iconSize(
        switchCase(
          get(KEY_PROPERTY_SELECTED), literal(3.0f),
          literal(1.0f)
        )
      ),
      iconAllowOverlap(true)
    );
    style.addLayer(layer);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_symbols, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (mapboxMap != null) {
      if (item.getItemId() == R.id.menu_action_change_location) {
        isInitialPosition = !isInitialPosition;
        updateSource(mapboxMap.getStyle());
      } else if (item.getItemId() == R.id.menu_action_toggle_source) {
        toggleSymbolLayerVisibility();
      }
    }
    return super.onOptionsItemSelected(item);
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