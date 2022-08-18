/*    */ package com.gomap.turf;
/*    */ 
/*    */ import androidx.annotation.IntRange;
/*    */ import androidx.annotation.NonNull;
/*    */ import com.gomap.geojson.Point;
/*    */ import com.gomap.geojson.Polygon;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class TurfTransformation
/*    */ {
/*    */   private static final int DEFAULT_STEPS = 64;
/*    */   
/*    */   public static Polygon circle(@NonNull Point center, double radius) {
/* 37 */     return circle(center, radius, 64, "kilometers");
/*    */   }
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
/*    */ 
/*    */ 
/*    */   
/*    */   public static Polygon circle(@NonNull Point center, double radius, String units) {
/* 53 */     return circle(center, radius, 64, units);
/*    */   }
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
/*    */ 
/*    */ 
/*    */   
/*    */   public static Polygon circle(@NonNull Point center, double radius, @IntRange(from = 1L) int steps, String units) {
/* 69 */     List<Point> coordinates = new ArrayList<>();
/* 70 */     for (int i = 0; i < steps; i++) {
/* 71 */       coordinates.add(TurfMeasurement.destination(center, radius, i * 360.0D / steps, units));
/*    */     }
/*    */     
/* 74 */     if (coordinates.size() > 0) {
/* 75 */       coordinates.add(coordinates.get(0));
/*    */     }
/* 77 */     List<List<Point>> coordinate = new ArrayList<>();
/* 78 */     coordinate.add(coordinates);
/* 79 */     return Polygon.fromLngLats(coordinate);
/*    */   }
/*    */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-turf-5.9.0.jar!\com\mapbox\turf\TurfTransformation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */