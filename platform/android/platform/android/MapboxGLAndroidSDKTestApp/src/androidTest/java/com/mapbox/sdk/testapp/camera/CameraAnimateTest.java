package com.mapbox.sdk.testapp.camera;

import com.gomap.sdk.camera.CameraUpdate;
import com.gomap.sdk.maps.MapboxMap;

public class CameraAnimateTest extends CameraTest {
  @Override
  void executeCameraMovement(CameraUpdate cameraUpdate, MapboxMap.CancelableCallback callback) {
    mapboxMap.animateCamera(cameraUpdate, callback);
  }
}