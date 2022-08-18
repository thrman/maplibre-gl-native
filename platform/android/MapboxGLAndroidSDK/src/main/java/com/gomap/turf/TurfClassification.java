/*    */ package com.gomap.turf;
/*    */ 
/*    */ import androidx.annotation.NonNull;
/*    */ import com.gomap.geojson.Point;
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
/*    */ public class TurfClassification
/*    */ {
/*    */   @NonNull
/*    */   public static Point nearestPoint(@NonNull Point targetPoint, @NonNull List<Point> points) {
/* 32 */     if (points.isEmpty()) {
/* 33 */       return targetPoint;
/*    */     }
/* 35 */     Point nearestPoint = points.get(0);
/* 36 */     double minDist = Double.POSITIVE_INFINITY;
/* 37 */     for (Point point : points) {
/* 38 */       double distanceToPoint = TurfMeasurement.distance(targetPoint, point);
/* 39 */       if (distanceToPoint < minDist) {
/* 40 */         nearestPoint = point;
/* 41 */         minDist = distanceToPoint;
/*    */       } 
/*    */     } 
/* 44 */     return nearestPoint;
/*    */   }
/*    */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-turf-5.9.0.jar!\com\mapbox\turf\TurfClassification.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */