/*    */ package com.mapbox.geojson.gson;
/*    */ 
/*    */ import androidx.annotation.Keep;
/*    */ import androidx.annotation.NonNull;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import com.mapbox.geojson.Geometry;
/*    */ import com.mapbox.geojson.GeometryAdapterFactory;
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
/*    */ @Keep
/*    */ public class GeometryGeoJson
/*    */ {
/*    */   public static Geometry fromJson(@NonNull String json) {
/* 27 */     GsonBuilder gson = new GsonBuilder();
/* 28 */     gson.registerTypeAdapterFactory(GeoJsonAdapterFactory.create());
/* 29 */     gson.registerTypeAdapterFactory(GeometryAdapterFactory.create());
/*    */     
/* 31 */     return (Geometry)gson.create().fromJson(json, Geometry.class);
/*    */   }
/*    */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\gson\GeometryGeoJson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */