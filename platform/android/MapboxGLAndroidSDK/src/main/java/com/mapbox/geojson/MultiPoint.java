/*     */ package com.mapbox.geojson;
/*     */ 
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import com.mapbox.geojson.gson.GeoJsonAdapterFactory;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Keep
/*     */ public final class MultiPoint
/*     */   implements CoordinateContainer<List<Point>>
/*     */ {
/*     */   private static final String TYPE = "MultiPoint";
/*     */   private final String type;
/*     */   private final BoundingBox bbox;
/*     */   private final List<Point> coordinates;
/*     */   
/*     */   public static MultiPoint fromJson(@NonNull String json) {
/*  60 */     GsonBuilder gson = new GsonBuilder();
/*  61 */     gson.registerTypeAdapterFactory(GeoJsonAdapterFactory.create());
/*  62 */     return (MultiPoint)gson.create().fromJson(json, MultiPoint.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MultiPoint fromLngLats(@NonNull List<Point> points) {
/*  77 */     return new MultiPoint("MultiPoint", null, points);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MultiPoint fromLngLats(@NonNull List<Point> points, @Nullable BoundingBox bbox) {
/*  93 */     return new MultiPoint("MultiPoint", bbox, points);
/*     */   }
/*     */   
/*     */   static MultiPoint fromLngLats(@NonNull double[][] coordinates) {
/*  97 */     ArrayList<Point> converted = new ArrayList<>(coordinates.length);
/*  98 */     for (int i = 0; i < coordinates.length; i++) {
/*  99 */       converted.add(Point.fromLngLat(coordinates[i]));
/*     */     }
/*     */     
/* 102 */     return new MultiPoint("MultiPoint", null, converted);
/*     */   }
/*     */   
/*     */   MultiPoint(String type, @Nullable BoundingBox bbox, List<Point> coordinates) {
/* 106 */     if (type == null) {
/* 107 */       throw new NullPointerException("Null type");
/*     */     }
/* 109 */     this.type = type;
/* 110 */     this.bbox = bbox;
/* 111 */     if (coordinates == null) {
/* 112 */       throw new NullPointerException("Null coordinates");
/*     */     }
/* 114 */     this.coordinates = coordinates;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public String type() {
/* 128 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BoundingBox bbox() {
/* 144 */     return this.bbox;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public List<Point> coordinates() {
/* 156 */     return this.coordinates;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toJson() {
/* 168 */     GsonBuilder gson = new GsonBuilder();
/* 169 */     gson.registerTypeAdapterFactory(GeoJsonAdapterFactory.create());
/* 170 */     return gson.create().toJson(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeAdapter<MultiPoint> typeAdapter(Gson gson) {
/* 181 */     return new GsonTypeAdapter(gson);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 186 */     return "MultiPoint{type=" + this.type + ", bbox=" + this.bbox + ", coordinates=" + this.coordinates + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 195 */     if (obj == this) {
/* 196 */       return true;
/*     */     }
/* 198 */     if (obj instanceof MultiPoint) {
/* 199 */       MultiPoint that = (MultiPoint)obj;
/* 200 */       return (this.type.equals(that.type()) && ((this.bbox == null) ? (that
/* 201 */         .bbox() == null) : this.bbox.equals(that.bbox())) && this.coordinates
/* 202 */         .equals(that.coordinates()));
/*     */     } 
/* 204 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 209 */     int hashCode = 1;
/* 210 */     hashCode *= 1000003;
/* 211 */     hashCode ^= this.type.hashCode();
/* 212 */     hashCode *= 1000003;
/* 213 */     hashCode ^= (this.bbox == null) ? 0 : this.bbox.hashCode();
/* 214 */     hashCode *= 1000003;
/* 215 */     hashCode ^= this.coordinates.hashCode();
/* 216 */     return hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class GsonTypeAdapter
/*     */     extends BaseGeometryTypeAdapter<MultiPoint, List<Point>>
/*     */   {
/*     */     GsonTypeAdapter(Gson gson) {
/* 227 */       super(gson, new ListOfPointCoordinatesTypeAdapter());
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(JsonWriter jsonWriter, MultiPoint object) throws IOException {
/* 232 */       writeCoordinateContainer(jsonWriter, object);
/*     */     }
/*     */ 
/*     */     
/*     */     public MultiPoint read(JsonReader jsonReader) throws IOException {
/* 237 */       return (MultiPoint)readCoordinateContainer(jsonReader);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     CoordinateContainer<List<Point>> createCoordinateContainer(String type, BoundingBox bbox, List<Point> coordinates) {
/* 244 */       return new MultiPoint((type == null) ? "MultiPoint" : type, bbox, coordinates);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\MultiPoint.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */