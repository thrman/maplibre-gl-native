/*    */ package com.mapbox.geojson;
/*    */ 
/*    */ import androidx.annotation.Keep;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Keep
/*    */ class ListOfDoublesCoordinatesTypeAdapter
/*    */   extends BaseCoordinatesTypeAdapter<List<Double>>
/*    */ {
/*    */   public void write(JsonWriter out, List<Double> value) throws IOException {
/* 21 */     writePointList(out, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<Double> read(JsonReader in) throws IOException {
/* 26 */     return readPointList(in);
/*    */   }
/*    */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\ListOfDoublesCoordinatesTypeAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */