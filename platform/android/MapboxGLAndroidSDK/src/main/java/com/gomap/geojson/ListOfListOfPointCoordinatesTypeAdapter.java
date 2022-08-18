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
/*    */ 
/*    */ 
/*    */ @Keep
/*    */ class ListOfListOfPointCoordinatesTypeAdapter
/*    */   extends BaseCoordinatesTypeAdapter<List<List<Point>>>
/*    */ {
/*    */   public void write(JsonWriter out, List<List<Point>> points) throws IOException {
/* 27 */     if (points == null) {
/* 28 */       out.nullValue();
/*    */       
/*    */       return;
/*    */     } 
/* 32 */     out.beginArray();
/*    */     
/* 34 */     for (List<Point> listOfPoints : points) {
/*    */       
/* 36 */       out.beginArray();
/*    */       
/* 38 */       for (Point point : listOfPoints) {
/* 39 */         writePoint(out, point);
/*    */       }
/* 41 */       out.endArray();
/*    */     } 
/*    */     
/* 44 */     out.endArray();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<List<Point>> read(JsonReader in) throws IOException {
/* 50 */     if (in.peek() == JsonToken.NULL) {
/* 51 */       throw new NullPointerException();
/*    */     }
/*    */     
/* 54 */     if (in.peek() == JsonToken.BEGIN_ARRAY) {
/*    */       
/* 56 */       in.beginArray();
/* 57 */       List<List<Point>> points = new ArrayList<>();
/*    */       
/* 59 */       while (in.peek() == JsonToken.BEGIN_ARRAY) {
/*    */         
/* 61 */         in.beginArray();
/* 62 */         List<Point> listOfPoints = new ArrayList<>();
/*    */         
/* 64 */         while (in.peek() == JsonToken.BEGIN_ARRAY) {
/* 65 */           listOfPoints.add(readPoint(in));
/*    */         }
/* 67 */         in.endArray();
/* 68 */         points.add(listOfPoints);
/*    */       } 
/* 70 */       in.endArray();
/*    */       
/* 72 */       return points;
/*    */     } 
/*    */     
/* 75 */     throw new GeoJsonException("coordinates should be array of array of array of double");
/*    */   }
/*    */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\ListOfListOfPointCoordinatesTypeAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */