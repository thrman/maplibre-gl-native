package com.gomap.sdk.maps;

import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.gomap.sdk.annotations.BaseMarkerOptions;
import com.gomap.sdk.annotations.Marker;

import java.util.List;

/**
 * Interface that defines convenient methods for working with a {@link Marker}'s collection.
 */
interface Markers {
  Marker addBy(@NonNull BaseMarkerOptions markerOptions, @NonNull MapboxMap mapboxMap);

  List<Marker> addBy(@NonNull List<? extends BaseMarkerOptions> markerOptionsList, @NonNull MapboxMap mapboxMap);

  void update(@NonNull Marker updatedMarker, @NonNull MapboxMap mapboxMap);

  List<Marker> obtainAll();

  @NonNull
  List<Marker> obtainAllIn(@NonNull RectF rectangle);

  void reload();
}
