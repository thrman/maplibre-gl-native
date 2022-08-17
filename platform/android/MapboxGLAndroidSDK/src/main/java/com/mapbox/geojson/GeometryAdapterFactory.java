/*    */ package com.mapbox.geojson;
/*    */ 
/*    */ import androidx.annotation.Keep;
/*    */ import com.google.gson.TypeAdapterFactory;
/*    */ import com.mapbox.geojson.internal.typeadapters.RuntimeTypeAdapterFactory;
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
/*    */ public abstract class GeometryAdapterFactory
/*    */   implements TypeAdapterFactory
/*    */ {
/*    */   private static TypeAdapterFactory geometryTypeFactory;
/*    */   
/*    */   public static TypeAdapterFactory create() {
/* 28 */     if (geometryTypeFactory == null)
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 36 */       geometryTypeFactory = (TypeAdapterFactory)RuntimeTypeAdapterFactory.of(Geometry.class, "type", true).registerSubtype(GeometryCollection.class, "GeometryCollection").registerSubtype(Point.class, "Point").registerSubtype(MultiPoint.class, "MultiPoint").registerSubtype(LineString.class, "LineString").registerSubtype(MultiLineString.class, "MultiLineString").registerSubtype(Polygon.class, "Polygon").registerSubtype(MultiPolygon.class, "MultiPolygon");
/*    */     }
/* 38 */     return geometryTypeFactory;
/*    */   }
/*    */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\GeometryAdapterFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */