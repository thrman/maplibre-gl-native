/*    */ package com.mapbox.geojson.gson;
/*    */ 
/*    */ import androidx.annotation.Keep;
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import com.mapbox.geojson.BoundingBox;
/*    */ import com.mapbox.geojson.Point;
/*    */ import com.mapbox.geojson.exception.GeoJsonException;
/*    */ import com.mapbox.geojson.shifter.CoordinateShifterManager;
/*    */ import com.mapbox.geojson.utils.GeoJsonUtils;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Keep
/*    */ public class BoundingBoxTypeAdapter
/*    */   extends TypeAdapter<BoundingBox>
/*    */ {
/*    */   public void write(JsonWriter out, BoundingBox value) throws IOException {
/* 29 */     if (value == null) {
/* 30 */       out.nullValue();
/*    */       
/*    */       return;
/*    */     } 
/* 34 */     out.beginArray();
/*    */ 
/*    */     
/* 37 */     Point point = value.southwest();
/*    */     
/* 39 */     List<Double> unshiftedCoordinates = CoordinateShifterManager.getCoordinateShifter().unshiftPoint(point);
/*    */     
/* 41 */     out.value(GeoJsonUtils.trim(((Double)unshiftedCoordinates.get(0)).doubleValue()));
/* 42 */     out.value(GeoJsonUtils.trim(((Double)unshiftedCoordinates.get(1)).doubleValue()));
/* 43 */     if (point.hasAltitude()) {
/* 44 */       out.value(unshiftedCoordinates.get(2));
/*    */     }
/*    */ 
/*    */     
/* 48 */     point = value.northeast();
/*    */     
/* 50 */     unshiftedCoordinates = CoordinateShifterManager.getCoordinateShifter().unshiftPoint(point);
/* 51 */     out.value(GeoJsonUtils.trim(((Double)unshiftedCoordinates.get(0)).doubleValue()));
/* 52 */     out.value(GeoJsonUtils.trim(((Double)unshiftedCoordinates.get(1)).doubleValue()));
/* 53 */     if (point.hasAltitude()) {
/* 54 */       out.value(unshiftedCoordinates.get(2));
/*    */     }
/*    */     
/* 57 */     out.endArray();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public BoundingBox read(JsonReader in) throws IOException {
/* 63 */     List<Double> rawCoordinates = new ArrayList<>();
/* 64 */     in.beginArray();
/* 65 */     while (in.hasNext()) {
/* 66 */       rawCoordinates.add(Double.valueOf(in.nextDouble()));
/*    */     }
/* 68 */     in.endArray();
/*    */     
/* 70 */     if (rawCoordinates.size() == 6) {
/* 71 */       return BoundingBox.fromLngLats(((Double)rawCoordinates
/* 72 */           .get(0)).doubleValue(), ((Double)rawCoordinates
/* 73 */           .get(1)).doubleValue(), ((Double)rawCoordinates
/* 74 */           .get(2)).doubleValue(), ((Double)rawCoordinates
/* 75 */           .get(3)).doubleValue(), ((Double)rawCoordinates
/* 76 */           .get(4)).doubleValue(), ((Double)rawCoordinates
/* 77 */           .get(5)).doubleValue());
/*    */     }
/* 79 */     if (rawCoordinates.size() == 4) {
/* 80 */       return BoundingBox.fromLngLats(((Double)rawCoordinates
/* 81 */           .get(0)).doubleValue(), ((Double)rawCoordinates
/* 82 */           .get(1)).doubleValue(), ((Double)rawCoordinates
/* 83 */           .get(2)).doubleValue(), ((Double)rawCoordinates
/* 84 */           .get(3)).doubleValue());
/*    */     }
/* 86 */     throw new GeoJsonException("The value of the bbox member MUST be an array of length 2*n where n is the number of dimensions represented in the contained geometries,with all axes of the most southwesterly point followed  by all axes of the more northeasterly point. The axes order of a bbox follows the axes order of geometries.");
/*    */   }
/*    */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\gson\BoundingBoxTypeAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */