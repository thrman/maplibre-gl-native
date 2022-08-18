/*    */ package com.gomap.geojson;
/*    */ 
/*    */ import androidx.annotation.Keep;
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonToken;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import com.gomap.geojson.exception.GeoJsonException;
/*    */ import com.gomap.geojson.shifter.CoordinateShifterManager;
/*    */ import com.gomap.geojson.utils.GeoJsonUtils;
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
/*    */ 
/*    */ @Keep
/*    */ abstract class BaseCoordinatesTypeAdapter<T>
/*    */   extends TypeAdapter<T>
/*    */ {
/*    */   protected void writePoint(JsonWriter out, Point point) throws IOException {
/* 29 */     if (point == null) {
/*    */       return;
/*    */     }
/* 32 */     writePointList(out, point.coordinates());
/*    */   }
/*    */ 
/*    */   
/*    */   protected Point readPoint(JsonReader in) throws IOException {
/* 37 */     List<Double> coordinates = readPointList(in);
/* 38 */     if (coordinates != null && coordinates.size() > 1) {
/* 39 */       return new Point("Point", null, coordinates);
/*    */     }
/*    */     
/* 42 */     throw new GeoJsonException(" Point coordinates should be non-null double array");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writePointList(JsonWriter out, List<Double> value) throws IOException {
/* 48 */     if (value == null) {
/*    */       return;
/*    */     }
/*    */     
/* 52 */     out.beginArray();
/*    */ 
/*    */ 
/*    */     
/* 56 */     List<Double> unshiftedCoordinates = CoordinateShifterManager.getCoordinateShifter().unshiftPoint(value);
/*    */     
/* 58 */     out.value(GeoJsonUtils.trim(((Double)unshiftedCoordinates.get(0)).doubleValue()));
/* 59 */     out.value(GeoJsonUtils.trim(((Double)unshiftedCoordinates.get(1)).doubleValue()));
/*    */ 
/*    */     
/* 62 */     if (value.size() > 2) {
/* 63 */       out.value(unshiftedCoordinates.get(2));
/*    */     }
/* 65 */     out.endArray();
/*    */   }
/*    */ 
/*    */   
/*    */   protected List<Double> readPointList(JsonReader in) throws IOException {
/* 70 */     if (in.peek() == JsonToken.NULL) {
/* 71 */       throw new NullPointerException();
/*    */     }
/*    */     
/* 74 */     List<Double> coordinates = new ArrayList<>();
/* 75 */     in.beginArray();
/* 76 */     while (in.hasNext()) {
/* 77 */       coordinates.add(Double.valueOf(in.nextDouble()));
/*    */     }
/* 79 */     in.endArray();
/*    */     
/* 81 */     if (coordinates.size() > 2) {
/* 82 */       return CoordinateShifterManager.getCoordinateShifter()
/* 83 */         .shiftLonLatAlt(((Double)coordinates.get(0)).doubleValue(), ((Double)coordinates.get(1)).doubleValue(), ((Double)coordinates.get(2)).doubleValue());
/*    */     }
/* 85 */     return CoordinateShifterManager.getCoordinateShifter()
/* 86 */       .shiftLonLat(((Double)coordinates.get(0)).doubleValue(), ((Double)coordinates.get(1)).doubleValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\BaseCoordinatesTypeAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */