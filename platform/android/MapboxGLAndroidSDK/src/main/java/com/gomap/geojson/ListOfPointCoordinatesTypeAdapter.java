/*    */ package com.gomap.geojson;
/*    */ 
/*    */ import androidx.annotation.Keep;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonToken;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import com.gomap.geojson.exception.GeoJsonException;
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
/*    */ class ListOfPointCoordinatesTypeAdapter
/*    */   extends BaseCoordinatesTypeAdapter<List<Point>>
/*    */ {
/*    */   public void write(JsonWriter out, List<Point> points) throws IOException {
/* 25 */     if (points == null) {
/* 26 */       out.nullValue();
/*    */       
/*    */       return;
/*    */     } 
/* 30 */     out.beginArray();
/*    */     
/* 32 */     for (Point point : points) {
/* 33 */       writePoint(out, point);
/*    */     }
/*    */     
/* 36 */     out.endArray();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Point> read(JsonReader in) throws IOException {
/* 42 */     if (in.peek() == JsonToken.NULL) {
/* 43 */       throw new NullPointerException();
/*    */     }
/*    */     
/* 46 */     if (in.peek() == JsonToken.BEGIN_ARRAY) {
/* 47 */       List<Point> points = new ArrayList<>();
/* 48 */       in.beginArray();
/*    */       
/* 50 */       while (in.peek() == JsonToken.BEGIN_ARRAY) {
/* 51 */         points.add(readPoint(in));
/*    */       }
/* 53 */       in.endArray();
/*    */       
/* 55 */       return points;
/*    */     } 
/*    */     
/* 58 */     throw new GeoJsonException("coordinates should be non-null array of array of double");
/*    */   }
/*    */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\ListOfPointCoordinatesTypeAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */