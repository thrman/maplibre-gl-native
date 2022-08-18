/*    */ package com.gomap.geojson;
/*    */ 
/*    */ import androidx.annotation.Keep;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
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
/*    */ public class PointAsCoordinatesTypeAdapter
/*    */   extends BaseCoordinatesTypeAdapter<Point>
/*    */ {
/*    */   public void write(JsonWriter out, Point value) throws IOException {
/* 21 */     writePoint(out, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public Point read(JsonReader in) throws IOException {
/* 26 */     return readPoint(in);
/*    */   }
/*    */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\PointAsCoordinatesTypeAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */