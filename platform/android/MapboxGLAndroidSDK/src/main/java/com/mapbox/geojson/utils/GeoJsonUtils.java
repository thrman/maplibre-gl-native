/*    */ package com.mapbox.geojson.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GeoJsonUtils
/*    */ {
/* 10 */   private static double ROUND_PRECISION = 1.0E7D;
/* 11 */   private static long MAX_DOUBLE_TO_ROUND = (long)(9.223372036854776E18D / ROUND_PRECISION);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static double trim(double value) {
/* 20 */     if (value > MAX_DOUBLE_TO_ROUND || value < -MAX_DOUBLE_TO_ROUND) {
/* 21 */       return value;
/*    */     }
/* 23 */     return Math.round(value * ROUND_PRECISION) / ROUND_PRECISION;
/*    */   }
/*    */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojso\\utils\GeoJsonUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */