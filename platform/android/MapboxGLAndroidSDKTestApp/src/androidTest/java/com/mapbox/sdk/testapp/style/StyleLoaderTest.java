package com.mapbox.sdk.testapp.style;

import com.gomap.sdk.maps.MapView;
import com.gomap.sdk.maps.Style;
import com.mapbox.sdk.testapp.R;
import com.mapbox.sdk.testapp.activity.EspressoTest;
import com.mapbox.sdk.testapp.utils.ResourceUtils;

import org.junit.Test;

import java.io.IOException;

import static com.mapbox.sdk.testapp.action.MapboxMapAction.invoke;
import static org.junit.Assert.assertEquals;

/**
 * Tests around style loading
 */
public class StyleLoaderTest extends EspressoTest {

  @Test
  public void testSetGetStyleJsonString() {
    validateTestSetup();
    invoke(mapboxMap, (uiController, mapboxMap) -> {
      try {
        String expected = ResourceUtils.readRawResource(rule.getActivity(), R.raw.local_style);
        mapboxMap.setStyle(new Style.Builder().fromJson(expected));
        String actual = mapboxMap.getStyle().getJson();
        assertEquals("Style json should match", expected, actual);
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    });
  }

  @Test
  public void testDefaultStyleLoadWithActivityLifecycleChange() {
    validateTestSetup();
    invoke(mapboxMap, (uiController, mapboxMap) -> {
      try {
        String expected = ResourceUtils.readRawResource(rule.getActivity(), R.raw.local_style);
        mapboxMap.setStyle(new Style.Builder().fromJson(expected));

        // fake activity stop/start
        MapView mapView = (MapView) rule.getActivity().findViewById(R.id.mapView);
        mapView.onPause();
        mapView.onStop();

        mapView.onStart();
        mapView.onResume();

        String actual = mapboxMap.getStyle().getJson();
        assertEquals("Style URL should be empty", "", mapboxMap.getStyle().getUri());
        assertEquals("Style json should match", expected, actual);
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    });
  }
}