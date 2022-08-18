package com.gomap.geojson.shifter;

import com.gomap.geojson.Point;
import com.gomap.geojson.Point;
import java.util.List;

public interface CoordinateShifter {
  List<Double> shiftLonLat(double paramDouble1, double paramDouble2);
  
  List<Double> shiftLonLatAlt(double paramDouble1, double paramDouble2, double paramDouble3);
  
  List<Double> unshiftPoint(Point paramPoint);
  
  List<Double> unshiftPoint(List<Double> paramList);
}


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\shifter\CoordinateShifter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */