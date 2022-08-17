package com.mapbox.geojson;

import androidx.annotation.Keep;

@Keep
public interface CoordinateContainer<T> extends Geometry {
  T coordinates();
}


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\CoordinateContainer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */