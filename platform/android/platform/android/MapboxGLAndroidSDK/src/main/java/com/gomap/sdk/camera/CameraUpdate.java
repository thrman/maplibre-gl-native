package com.gomap.sdk.camera;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gomap.sdk.maps.MapboxMap;

/**
 * Interface definition for camera updates.
 */
public interface CameraUpdate {

  /**
   * Get the camera position from the camera update.
   *
   * @param mapboxMap Map object to build the position from
   * @return the camera position from the implementing camera update
   */
  @Nullable
  CameraPosition getCameraPosition(@NonNull MapboxMap mapboxMap);

}

