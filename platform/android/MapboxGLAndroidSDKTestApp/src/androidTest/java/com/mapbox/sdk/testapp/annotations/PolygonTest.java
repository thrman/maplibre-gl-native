package com.mapbox.sdk.testapp.annotations;

import android.graphics.Color;

import com.gomap.sdk.annotations.Polygon;
import com.gomap.sdk.annotations.PolygonOptions;
import com.gomap.sdk.geometry.LatLng;
import com.mapbox.sdk.testapp.activity.EspressoTest;

import org.junit.Ignore;
import org.junit.Test;

import static com.mapbox.sdk.testapp.action.MapboxMapAction.invoke;
import static org.junit.Assert.assertEquals;

public class PolygonTest extends EspressoTest {

  @Test
  @Ignore
  public void addPolygonTest() {
    validateTestSetup();
    invoke(mapboxMap, (uiController, mapboxMap) -> {
      LatLng latLngOne = new LatLng();
      LatLng latLngTwo = new LatLng(1, 0);
      LatLng latLngThree = new LatLng(1, 1);

      assertEquals("Polygons should be empty", 0, mapboxMap.getPolygons().size());

      final PolygonOptions options = new PolygonOptions();
      options.strokeColor(Color.BLUE);
      options.fillColor(Color.RED);
      options.add(latLngOne);
      options.add(latLngTwo);
      options.add(latLngThree);
      Polygon polygon = mapboxMap.addPolygon(options);

      assertEquals("Polygons should be 1", 1, mapboxMap.getPolygons().size());
      assertEquals("Polygon id should be 0", 0, polygon.getId());
      assertEquals("Polygon points size should match", 3, polygon.getPoints().size());
      assertEquals("Polygon stroke color should match", Color.BLUE, polygon.getStrokeColor());
      assertEquals("Polygon target should match", Color.RED, polygon.getFillColor());
      mapboxMap.clear();
      assertEquals("Polygons should be empty", 0, mapboxMap.getPolygons().size());
    });
  }
}