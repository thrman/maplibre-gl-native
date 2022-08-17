/*    */ package com.mapbox.geojson;
/*    */ 
/*    */ import androidx.annotation.Keep;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonToken;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import com.mapbox.geojson.exception.GeoJsonException;
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
/*    */ class ListofListofListOfPointCoordinatesTypeAdapter
/*    */   extends BaseCoordinatesTypeAdapter<List<List<List<Point>>>>
/*    */ {
/*    */   public void write(JsonWriter out, List<List<List<Point>>> points) throws IOException {
/* 27 */     if (points == null) {
/* 28 */       out.nullValue();
/*    */       
/*    */       return;
/*    */     } 
/* 32 */     out.beginArray();
/*    */     
/* 34 */     for (List<List<Point>> listOfListOfPoints : points) {
/*    */       
/* 36 */       out.beginArray();
/*    */       
/* 38 */       for (List<Point> listOfPoints : listOfListOfPoints) {
/*    */         
/* 40 */         out.beginArray();
/*    */         
/* 42 */         for (Point point : listOfPoints) {
/* 43 */           writePoint(out, point);
/*    */         }
/* 45 */         out.endArray();
/*    */       } 
/*    */       
/* 48 */       out.endArray();
/*    */     } 
/* 50 */     out.endArray();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<List<List<Point>>> read(JsonReader in) throws IOException {
/* 56 */     if (in.peek() == JsonToken.NULL) {
/* 57 */       throw new NullPointerException();
/*    */     }
/*    */     
/* 60 */     if (in.peek() == JsonToken.BEGIN_ARRAY) {
/*    */       
/* 62 */       in.beginArray();
/* 63 */       List<List<List<Point>>> listOfListOflistOfPoints = new ArrayList<>();
/* 64 */       while (in.peek() == JsonToken.BEGIN_ARRAY) {
/*    */         
/* 66 */         in.beginArray();
/* 67 */         List<List<Point>> listOfListOfPoints = new ArrayList<>();
/*    */         
/* 69 */         while (in.peek() == JsonToken.BEGIN_ARRAY) {
/*    */           
/* 71 */           in.beginArray();
/* 72 */           List<Point> listOfPoints = new ArrayList<>();
/* 73 */           while (in.peek() == JsonToken.BEGIN_ARRAY) {
/* 74 */             listOfPoints.add(readPoint(in));
/*    */           }
/* 76 */           in.endArray();
/*    */           
/* 78 */           listOfListOfPoints.add(listOfPoints);
/*    */         } 
/* 80 */         in.endArray();
/* 81 */         listOfListOflistOfPoints.add(listOfListOfPoints);
/*    */       } 
/* 83 */       in.endArray();
/* 84 */       return listOfListOflistOfPoints;
/*    */     } 
/*    */     
/* 87 */     throw new GeoJsonException("coordinates should be array of array of array of double");
/*    */   }
/*    */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\ListofListofListOfPointCoordinatesTypeAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */