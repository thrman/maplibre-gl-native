/*     */ package com.gomap.geojson;
/*     */ 
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import com.gomap.geojson.gson.GeoJsonAdapterFactory;
/*     */ import com.gomap.geojson.utils.PolylineUtils;
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
/*     */ public final class LineString
/*     */   implements CoordinateContainer<List<Point>>
/*     */ {
/*     */   private static final String TYPE = "LineString";
/*     */   private final String type;
/*     */   private final BoundingBox bbox;
/*     */   private final List<Point> coordinates;
/*     */   
/*     */   public static LineString fromJson(String json) {
/*  75 */     GsonBuilder gson = new GsonBuilder();
/*  76 */     gson.registerTypeAdapterFactory(GeoJsonAdapterFactory.create());
/*  77 */     return (LineString)gson.create().fromJson(json, LineString.class);
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
/*     */   public static LineString fromLngLats(@NonNull MultiPoint multiPoint) {
/*  90 */     return new LineString("LineString", null, multiPoint.coordinates());
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
/*     */ 
/*     */   
/*     */   public static LineString fromLngLats(@NonNull List<Point> points) {
/* 108 */     return new LineString("LineString", null, points);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static LineString fromLngLats(@NonNull List<Point> points, @Nullable BoundingBox bbox) {
/* 127 */     return new LineString("LineString", bbox, points);
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
/*     */   public static LineString fromLngLats(@NonNull MultiPoint multiPoint, @Nullable BoundingBox bbox) {
/* 141 */     return new LineString("LineString", bbox, multiPoint.coordinates());
/*     */   }
/*     */   
/*     */   LineString(String type, @Nullable BoundingBox bbox, List<Point> coordinates) {
/* 145 */     if (type == null) {
/* 146 */       throw new NullPointerException("Null type");
/*     */     }
/* 148 */     this.type = type;
/* 149 */     this.bbox = bbox;
/* 150 */     if (coordinates == null) {
/* 151 */       throw new NullPointerException("Null coordinates");
/*     */     }
/* 153 */     this.coordinates = coordinates;
/*     */   }
/*     */   
/*     */   static LineString fromLngLats(double[][] coordinates) {
/* 157 */     ArrayList<Point> converted = new ArrayList<>(coordinates.length);
/* 158 */     for (int i = 0; i < coordinates.length; i++) {
/* 159 */       converted.add(Point.fromLngLat(coordinates[i]));
/*     */     }
/* 161 */     return fromLngLats(converted);
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
/*     */ 
/*     */   
/*     */   public static LineString fromPolyline(@NonNull String polyline, int precision) {
/* 179 */     return fromLngLats(PolylineUtils.decode(polyline, precision), (BoundingBox)null);
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
/* 193 */     return this.type;
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
/* 209 */     return this.bbox;
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
/* 221 */     return this.coordinates;
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
/* 233 */     GsonBuilder gson = new GsonBuilder();
/* 234 */     gson.registerTypeAdapterFactory(GeoJsonAdapterFactory.create());
/* 235 */     return gson.create().toJson(this);
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
/*     */   public String toPolyline(int precision) {
/* 248 */     return PolylineUtils.encode(coordinates(), precision);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeAdapter<LineString> typeAdapter(Gson gson) {
/* 259 */     return new GsonTypeAdapter(gson);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 264 */     return "LineString{type=" + this.type + ", bbox=" + this.bbox + ", coordinates=" + this.coordinates + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 273 */     if (obj == this) {
/* 274 */       return true;
/*     */     }
/* 276 */     if (obj instanceof LineString) {
/* 277 */       LineString that = (LineString)obj;
/* 278 */       return (this.type.equals(that.type()) && ((this.bbox == null) ? (that
/* 279 */         .bbox() == null) : this.bbox.equals(that.bbox())) && this.coordinates
/* 280 */         .equals(that.coordinates()));
/*     */     } 
/* 282 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 287 */     int hashCode = 1;
/* 288 */     hashCode *= 1000003;
/* 289 */     hashCode ^= this.type.hashCode();
/* 290 */     hashCode *= 1000003;
/* 291 */     hashCode ^= (this.bbox == null) ? 0 : this.bbox.hashCode();
/* 292 */     hashCode *= 1000003;
/* 293 */     hashCode ^= this.coordinates.hashCode();
/* 294 */     return hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class GsonTypeAdapter
/*     */     extends BaseGeometryTypeAdapter<LineString, List<Point>>
/*     */   {
/*     */     GsonTypeAdapter(Gson gson) {
/* 305 */       super(gson, new ListOfPointCoordinatesTypeAdapter());
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(JsonWriter jsonWriter, LineString object) throws IOException {
/* 310 */       writeCoordinateContainer(jsonWriter, object);
/*     */     }
/*     */ 
/*     */     
/*     */     public LineString read(JsonReader jsonReader) throws IOException {
/* 315 */       return (LineString)readCoordinateContainer(jsonReader);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     CoordinateContainer<List<Point>> createCoordinateContainer(String type, BoundingBox bbox, List<Point> coordinates) {
/* 322 */       return new LineString((type == null) ? "LineString" : type, bbox, coordinates);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\LineString.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */