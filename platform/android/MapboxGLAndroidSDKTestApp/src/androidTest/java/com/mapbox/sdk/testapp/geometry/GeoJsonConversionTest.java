package com.mapbox.sdk.testapp.geometry;

import androidx.test.annotation.UiThreadTest;

import com.google.gson.JsonArray;
import com.gomap.geojson.Feature;
import com.gomap.geojson.Point;
import com.gomap.geojson.Polygon;
import com.gomap.sdk.geometry.LatLng;
import com.gomap.sdk.style.expressions.Expression;
import com.gomap.sdk.style.layers.PropertyFactory;
import com.gomap.sdk.style.layers.SymbolLayer;
import com.gomap.sdk.style.sources.GeoJsonSource;
import com.mapbox.sdk.testapp.action.MapboxMapAction;
import com.mapbox.sdk.testapp.activity.EspressoTest;
import com.mapbox.sdk.testapp.utils.TestingAsyncUtils;

import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static com.gomap.geojson.Feature.fromGeometry;
import static com.gomap.geojson.FeatureCollection.fromFeatures;
import static com.gomap.geojson.GeometryCollection.fromGeometries;
import static com.gomap.geojson.LineString.fromLngLats;
import static com.gomap.geojson.MultiLineString.fromLineString;
import static com.gomap.geojson.MultiPolygon.fromPolygon;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertFalse;

/**
 * Instrumentation test to validate java geojson conversion to c++
 */
public class GeoJsonConversionTest extends EspressoTest {

  // Regression test for #12343
  @Test
  @UiThreadTest
  public void testEmptyFeatureCollection() {
    validateTestSetup();
    mapboxMap.getStyle().addSource(
      new GeoJsonSource("test-id",
        fromFeatures(singletonList(fromGeometry(fromGeometries(emptyList()))))
      )
    );
    mapboxMap.getStyle().addLayer(new SymbolLayer("test-id", "test-id"));
  }

  @Test
  @UiThreadTest
  public void testPointFeatureCollection() {
    validateTestSetup();
    mapboxMap.getStyle().addSource(
      new GeoJsonSource("test-id",
        fromFeatures(singletonList(fromGeometry(Point.fromLngLat(0.0, 0.0))))
      )
    );
    mapboxMap.getStyle().addLayer(new SymbolLayer("test-id", "test-id"));
  }

  @Test
  @UiThreadTest
  public void testMultiPointFeatureCollection() {
    validateTestSetup();
    mapboxMap.getStyle().addSource(
      new GeoJsonSource("test-id",
        fromFeatures(singletonList(fromGeometry(fromLngLats(emptyList()))))
      )
    );
    mapboxMap.getStyle().addLayer(new SymbolLayer("test-id", "test-id"));
  }

  @Test
  @UiThreadTest
  public void testPolygonFeatureCollection() {
    validateTestSetup();
    mapboxMap.getStyle().addSource(
      new GeoJsonSource("test-id",
        fromFeatures(singletonList(fromGeometry(Polygon.fromLngLats(emptyList()))))
      )
    );
    mapboxMap.getStyle().addLayer(new SymbolLayer("test-id", "test-id"));
  }

  @Test
  @UiThreadTest
  public void testMultiPolygonFeatureCollection() {
    validateTestSetup();
    mapboxMap.getStyle().addSource(
      new GeoJsonSource("test-id",
        fromFeatures(singletonList(fromGeometry(fromPolygon(Polygon.fromLngLats(emptyList())))))
      )
    );
    mapboxMap.getStyle().addLayer(new SymbolLayer("test-id", "test-id"));
  }

  @Test
  @UiThreadTest
  public void testLineStringFeatureCollection() {
    validateTestSetup();
    mapboxMap.getStyle().addSource(
      new GeoJsonSource("test-id",
        fromFeatures(singletonList(fromGeometry(fromLngLats(emptyList()))))
      )
    );
    mapboxMap.getStyle().addLayer(new SymbolLayer("test-id", "test-id"));
  }

  @Test
  @UiThreadTest
  public void testMultiLineStringFeatureCollection() {
    validateTestSetup();
    mapboxMap.getStyle().addSource(
      new GeoJsonSource("test-id",
        fromFeatures(singletonList(fromGeometry(fromLineString(fromLngLats(emptyList())))))
      )
    );
    mapboxMap.getStyle().addLayer(new SymbolLayer("test-id", "test-id"));
  }


  @Test
  public void testNegativeNumberPropertyConversion() {
    validateTestSetup();
    onView(isRoot()).perform(new MapboxMapAction((uiController, mapboxMap) -> {
      LatLng latLng = new LatLng();
      Feature feature = Feature.fromGeometry(Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude()));

      JsonArray foregroundJsonArray = new JsonArray();
      foregroundJsonArray.add(0f);
      foregroundJsonArray.add(-3f);
      feature.addProperty("property", foregroundJsonArray);

      GeoJsonSource source = new GeoJsonSource("source", feature);
      mapboxMap.getStyle().addSource(source);

      SymbolLayer layer = new SymbolLayer("layer", "source")
        .withProperties(
          PropertyFactory.iconOffset(Expression.get("property")),
          PropertyFactory.iconImage("zoo-15")
        );
      mapboxMap.getStyle().addLayer(layer);

      TestingAsyncUtils.INSTANCE.waitForLayer(uiController, mapView);

      assertFalse(mapboxMap.queryRenderedFeatures(mapboxMap.getProjection().toScreenLocation(latLng)).isEmpty());
    }, mapboxMap));
  }
}