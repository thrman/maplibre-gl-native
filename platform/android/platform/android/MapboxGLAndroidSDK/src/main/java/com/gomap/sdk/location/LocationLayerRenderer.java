package com.gomap.sdk.location;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.gomap.sdk.location.modes.RenderMode;
import com.gomap.sdk.geometry.LatLng;
import com.gomap.sdk.maps.Style;
import com.gomap.sdk.style.expressions.Expression;

interface LocationLayerRenderer {
  void initializeComponents(Style style);

  void addLayers(LocationComponentPositionManager positionManager);

  void removeLayers();

  void hide();

  void cameraTiltUpdated(double tilt);

  void cameraBearingUpdated(double bearing);

  void show(@RenderMode.Mode int renderMode, boolean isStale);

  void styleAccuracy(float accuracyAlpha, int accuracyColor);

  void setLatLng(LatLng latLng);

  void setGpsBearing(Float gpsBearing);

  void setCompassBearing(Float compassBearing);

  void setAccuracyRadius(Float accuracy);

  void styleScaling(Expression scaleExpression);

  void setLocationStale(boolean isStale, int renderMode);

  void adjustPulsingCircleLayerVisibility(boolean visible);

  void stylePulsingCircle(LocationComponentOptions options);

  void updatePulsingUi(float radius, @Nullable Float opacity);

  void updateIconIds(String foregroundIconString, String foregroundStaleIconString, String backgroundIconString,
                     String backgroundStaleIconString, String bearingIconString);

  void addBitmaps(@RenderMode.Mode int renderMode, @Nullable Bitmap shadowBitmap, Bitmap backgroundBitmap,
                  Bitmap backgroundStaleBitmap, Bitmap bearingBitmap,
                  Bitmap foregroundBitmap, Bitmap foregroundStaleBitmap);
}
