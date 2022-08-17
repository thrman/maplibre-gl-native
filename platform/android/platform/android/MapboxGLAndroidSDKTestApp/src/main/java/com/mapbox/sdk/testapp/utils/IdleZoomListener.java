package com.mapbox.sdk.testapp.utils;

import android.content.Context;
import android.widget.TextView;

import com.gomap.sdk.camera.CameraPosition;
import com.gomap.sdk.maps.MapboxMap;
import com.mapbox.sdk.testapp.R;

public class IdleZoomListener implements MapboxMap.OnCameraIdleListener {

  private MapboxMap mapboxMap;
  private TextView textView;

  public IdleZoomListener(MapboxMap mapboxMap, TextView textView) {
    this.mapboxMap = mapboxMap;
    this.textView = textView;
  }

  @Override
  public void onCameraIdle() {
    Context context = textView.getContext();
    CameraPosition position = mapboxMap.getCameraPosition();
    textView.setText(String.format(context.getString(R.string.debug_zoom), position.zoom));
  }
}