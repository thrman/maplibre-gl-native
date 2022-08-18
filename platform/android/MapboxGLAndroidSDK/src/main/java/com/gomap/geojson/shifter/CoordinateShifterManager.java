/*    */ package com.gomap.geojson.shifter;
/*    */ 
/*    */ import com.gomap.geojson.Point;
import com.gomap.geojson.Point;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CoordinateShifterManager
/*    */ {
/* 15 */   private static final CoordinateShifter DEFAULT = new CoordinateShifter()
/*    */     {
/*    */       public List<Double> shiftLonLat(double lon, double lat) {
/* 18 */         return Arrays.asList(new Double[] { Double.valueOf(lon), Double.valueOf(lat) });
/*    */       }
/*    */ 
/*    */       
/*    */       public List<Double> shiftLonLatAlt(double lon, double lat, double alt) {
/* 23 */         return Double.isNaN(alt) ? 
/* 24 */           Arrays.<Double>asList(new Double[] { Double.valueOf(lon), Double.valueOf(lat)
/* 25 */             }) : Arrays.<Double>asList(new Double[] { Double.valueOf(lon), Double.valueOf(lat), Double.valueOf(alt) });
/*    */       }
/*    */ 
/*    */       
/*    */       public List<Double> unshiftPoint(Point point) {
/* 30 */         return point.coordinates();
/*    */       }
/*    */ 
/*    */       
/*    */       public List<Double> unshiftPoint(List<Double> coordinates) {
/* 35 */         return coordinates;
/*    */       }
/*    */     };
/*    */   
/* 39 */   private static volatile CoordinateShifter coordinateShifter = DEFAULT;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static CoordinateShifter getCoordinateShifter() {
/* 48 */     return coordinateShifter;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setCoordinateShifter(CoordinateShifter coordinateShifter) {
/* 59 */     CoordinateShifterManager.coordinateShifter = (coordinateShifter == null) ? DEFAULT : coordinateShifter;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isUsingDefaultShifter() {
/* 67 */     return (coordinateShifter == DEFAULT);
/*    */   }
/*    */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\shifter\CoordinateShifterManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */