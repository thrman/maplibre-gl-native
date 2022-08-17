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
/*     */ import com.mapbox.geojson.shifter.CoordinateShifterManager;
/*     */ import java.io.IOException;
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
/*     */ @Keep
/*     */ public final class Point
/*     */   implements CoordinateContainer<List<Double>>
/*     */ {
/*     */   private static final String TYPE = "Point";
/*     */   @NonNull
/*     */   private final String type;
/*     */   @Nullable
/*     */   private final BoundingBox bbox;
/*     */   @NonNull
/*     */   private final List<Double> coordinates;
/*     */   
/*     */   public static Point fromJson(@NonNull String json) {
/*  75 */     GsonBuilder gson = new GsonBuilder();
/*  76 */     gson.registerTypeAdapterFactory(GeoJsonAdapterFactory.create());
/*  77 */     return (Point)gson.create().fromJson(json, Point.class);
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
/*     */   public static Point fromLngLat(double longitude, double latitude) {
/*  95 */     List<Double> coordinates = CoordinateShifterManager.getCoordinateShifter().shiftLonLat(longitude, latitude);
/*  96 */     return new Point("Point", null, coordinates);
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
/*     */ 
/*     */   
/*     */   public static Point fromLngLat(double longitude, double latitude, @Nullable BoundingBox bbox) {
/* 117 */     List<Double> coordinates = CoordinateShifterManager.getCoordinateShifter().shiftLonLat(longitude, latitude);
/* 118 */     return new Point("Point", bbox, coordinates);
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
/*     */ 
/*     */   
/*     */   public static Point fromLngLat(double longitude, double latitude, double altitude) {
/* 139 */     List<Double> coordinates = CoordinateShifterManager.getCoordinateShifter().shiftLonLatAlt(longitude, latitude, altitude);
/*     */     
/* 141 */     return new Point("Point", null, coordinates);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Point fromLngLat(double longitude, double latitude, double altitude, @Nullable BoundingBox bbox) {
/* 164 */     List<Double> coordinates = CoordinateShifterManager.getCoordinateShifter().shiftLonLatAlt(longitude, latitude, altitude);
/* 165 */     return new Point("Point", bbox, coordinates);
/*     */   }
/*     */   
/*     */   static Point fromLngLat(@NonNull double[] coords) {
/* 169 */     if (coords.length == 2) {
/* 170 */       return fromLngLat(coords[0], coords[1]);
/*     */     }
/* 172 */     if (coords.length > 2) {
/* 173 */       return fromLngLat(coords[0], coords[1], coords[2]);
/*     */     }
/* 175 */     return null;
/*     */   }
/*     */   
/*     */   Point(String type, @Nullable BoundingBox bbox, List<Double> coordinates) {
/* 179 */     if (type == null) {
/* 180 */       throw new NullPointerException("Null type");
/*     */     }
/* 182 */     this.type = type;
/* 183 */     this.bbox = bbox;
/* 184 */     if (coordinates == null || coordinates.size() == 0) {
/* 185 */       throw new NullPointerException("Null coordinates");
/*     */     }
/* 187 */     this.coordinates = coordinates;
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
/*     */   public double longitude() {
/* 200 */     return ((Double)coordinates().get(0)).doubleValue();
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
/*     */   public double latitude() {
/* 213 */     return ((Double)coordinates().get(1)).doubleValue();
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
/*     */   public double altitude() {
/* 226 */     if (coordinates().size() < 3) {
/* 227 */       return Double.NaN;
/*     */     }
/* 229 */     return ((Double)coordinates().get(2)).doubleValue();
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
/*     */   public boolean hasAltitude() {
/* 241 */     return !Double.isNaN(altitude());
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
/* 255 */     return this.type;
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
/* 271 */     return this.bbox;
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
/*     */   public List<Double> coordinates() {
/* 285 */     return this.coordinates;
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
/* 297 */     GsonBuilder gson = new GsonBuilder();
/* 298 */     gson.registerTypeAdapterFactory(GeoJsonAdapterFactory.create());
/* 299 */     return gson.create().toJson(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeAdapter<Point> typeAdapter(Gson gson) {
/* 310 */     return new GsonTypeAdapter(gson);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 315 */     return "Point{type=" + this.type + ", bbox=" + this.bbox + ", coordinates=" + this.coordinates + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 324 */     if (obj == this) {
/* 325 */       return true;
/*     */     }
/* 327 */     if (obj instanceof Point) {
/* 328 */       Point that = (Point)obj;
/* 329 */       return (this.type.equals(that.type()) && ((this.bbox == null) ? (that
/* 330 */         .bbox() == null) : this.bbox.equals(that.bbox())) && this.coordinates
/* 331 */         .equals(that.coordinates()));
/*     */     } 
/* 333 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 338 */     int hashCode = 1;
/* 339 */     hashCode *= 1000003;
/* 340 */     hashCode ^= this.type.hashCode();
/* 341 */     hashCode *= 1000003;
/* 342 */     hashCode ^= (this.bbox == null) ? 0 : this.bbox.hashCode();
/* 343 */     hashCode *= 1000003;
/* 344 */     hashCode ^= this.coordinates.hashCode();
/* 345 */     return hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class GsonTypeAdapter
/*     */     extends BaseGeometryTypeAdapter<Point, List<Double>>
/*     */   {
/*     */     GsonTypeAdapter(Gson gson) {
/* 356 */       super(gson, new ListOfDoublesCoordinatesTypeAdapter());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void write(JsonWriter jsonWriter, Point object) throws IOException {
/* 362 */       writeCoordinateContainer(jsonWriter, object);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Point read(JsonReader jsonReader) throws IOException {
/* 368 */       return (Point)readCoordinateContainer(jsonReader);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     CoordinateContainer<List<Double>> createCoordinateContainer(String type, BoundingBox bbox, List<Double> coordinates) {
/* 375 */       return new Point((type == null) ? "Point" : type, bbox, coordinates);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\Point.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */