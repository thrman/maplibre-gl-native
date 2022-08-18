package com.gomap.geojson;

import androidx.annotation.Keep;
import java.io.Serializable;

@Keep
public interface GeoJson extends Serializable {
  String type();
  
  String toJson();
  
  BoundingBox bbox();
}


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\GeoJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */