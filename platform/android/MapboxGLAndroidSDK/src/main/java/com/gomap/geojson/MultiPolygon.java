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
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
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
/*     */ public final class MultiPolygon
/*     */   implements CoordinateContainer<List<List<List<Point>>>>
/*     */ {
/*     */   private static final String TYPE = "MultiPolygon";
/*     */   private final String type;
/*     */   private final BoundingBox bbox;
/*     */   private final List<List<List<Point>>> coordinates;
/*     */   
/*     */   public static MultiPolygon fromJson(String json) {
/*  93 */     GsonBuilder gson = new GsonBuilder();
/*  94 */     gson.registerTypeAdapterFactory(GeoJsonAdapterFactory.create());
/*  95 */     return (MultiPolygon)gson.create().fromJson(json, MultiPolygon.class);
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
/*     */   public static MultiPolygon fromPolygons(@NonNull List<Polygon> polygons) {
/* 109 */     List<List<List<Point>>> coordinates = new ArrayList<>(polygons.size());
/* 110 */     for (Polygon polygon : polygons) {
/* 111 */       coordinates.add(polygon.coordinates());
/*     */     }
/* 113 */     return new MultiPolygon("MultiPolygon", null, coordinates);
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
/*     */   public static MultiPolygon fromPolygons(@NonNull List<Polygon> polygons, @Nullable BoundingBox bbox) {
/* 130 */     List<List<List<Point>>> coordinates = new ArrayList<>(polygons.size());
/* 131 */     for (Polygon polygon : polygons) {
/* 132 */       coordinates.add(polygon.coordinates());
/*     */     }
/* 134 */     return new MultiPolygon("MultiPolygon", bbox, coordinates);
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
/*     */   public static MultiPolygon fromPolygon(@NonNull Polygon polygon) {
/* 148 */     List<List<List<Point>>> coordinates = Arrays.asList((List<List<Point>>[])new List[] { polygon.coordinates() });
/* 149 */     return new MultiPolygon("MultiPolygon", null, coordinates);
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
/*     */   public static MultiPolygon fromPolygon(@NonNull Polygon polygon, @Nullable BoundingBox bbox) {
/* 164 */     List<List<List<Point>>> coordinates = Arrays.asList((List<List<Point>>[])new List[] { polygon.coordinates() });
/* 165 */     return new MultiPolygon("MultiPolygon", bbox, coordinates);
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
/*     */   public static MultiPolygon fromLngLats(@NonNull List<List<List<Point>>> points) {
/* 178 */     return new MultiPolygon("MultiPolygon", null, points);
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
/*     */   public static MultiPolygon fromLngLats(@NonNull List<List<List<Point>>> points, @Nullable BoundingBox bbox) {
/* 193 */     return new MultiPolygon("MultiPolygon", bbox, points);
/*     */   }
/*     */   
/*     */   static MultiPolygon fromLngLats(@NonNull double[][][][] coordinates) {
/* 197 */     List<List<List<Point>>> converted = new ArrayList<>(coordinates.length);
/* 198 */     for (int i = 0; i < coordinates.length; i++) {
/* 199 */       List<List<Point>> innerOneList = new ArrayList<>((coordinates[i]).length);
/* 200 */       for (int j = 0; j < (coordinates[i]).length; j++) {
/* 201 */         List<Point> innerTwoList = new ArrayList<>((coordinates[i][j]).length);
/* 202 */         for (int k = 0; k < (coordinates[i][j]).length; k++) {
/* 203 */           innerTwoList.add(Point.fromLngLat(coordinates[i][j][k]));
/*     */         }
/* 205 */         innerOneList.add(innerTwoList);
/*     */       } 
/* 207 */       converted.add(innerOneList);
/*     */     } 
/*     */     
/* 210 */     return new MultiPolygon("MultiPolygon", null, converted);
/*     */   }
/*     */   
/*     */   MultiPolygon(String type, @Nullable BoundingBox bbox, List<List<List<Point>>> coordinates) {
/* 214 */     if (type == null) {
/* 215 */       throw new NullPointerException("Null type");
/*     */     }
/* 217 */     this.type = type;
/* 218 */     this.bbox = bbox;
/* 219 */     if (coordinates == null) {
/* 220 */       throw new NullPointerException("Null coordinates");
/*     */     }
/* 222 */     this.coordinates = coordinates;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Polygon> polygons() {
/* 232 */     List<List<List<Point>>> coordinates = coordinates();
/* 233 */     List<Polygon> polygons = new ArrayList<>(coordinates.size());
/* 234 */     for (List<List<Point>> points : coordinates) {
/* 235 */       polygons.add(Polygon.fromLngLats(points));
/*     */     }
/* 237 */     return polygons;
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
/* 251 */     return this.type;
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
/* 267 */     return this.bbox;
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
/*     */   public List<List<List<Point>>> coordinates() {
/* 279 */     return this.coordinates;
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
/* 291 */     GsonBuilder gson = new GsonBuilder();
/* 292 */     gson.registerTypeAdapterFactory(GeoJsonAdapterFactory.create());
/* 293 */     return gson.create().toJson(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeAdapter<MultiPolygon> typeAdapter(Gson gson) {
/* 304 */     return new GsonTypeAdapter(gson);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 309 */     return "Polygon{type=" + this.type + ", bbox=" + this.bbox + ", coordinates=" + this.coordinates + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 318 */     if (obj == this) {
/* 319 */       return true;
/*     */     }
/* 321 */     if (obj instanceof MultiPolygon) {
/* 322 */       MultiPolygon that = (MultiPolygon)obj;
/* 323 */       return (this.type.equals(that.type()) && ((this.bbox == null) ? (that
/* 324 */         .bbox() == null) : this.bbox.equals(that.bbox())) && this.coordinates
/* 325 */         .equals(that.coordinates()));
/*     */     } 
/* 327 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 332 */     int hashCode = 1;
/* 333 */     hashCode *= 1000003;
/* 334 */     hashCode ^= this.type.hashCode();
/* 335 */     hashCode *= 1000003;
/* 336 */     hashCode ^= (this.bbox == null) ? 0 : this.bbox.hashCode();
/* 337 */     hashCode *= 1000003;
/* 338 */     hashCode ^= this.coordinates.hashCode();
/* 339 */     return hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class GsonTypeAdapter
/*     */     extends BaseGeometryTypeAdapter<MultiPolygon, List<List<List<Point>>>>
/*     */   {
/*     */     GsonTypeAdapter(Gson gson) {
/* 351 */       super(gson, new ListofListofListOfPointCoordinatesTypeAdapter());
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(JsonWriter jsonWriter, MultiPolygon object) throws IOException {
/* 356 */       writeCoordinateContainer(jsonWriter, object);
/*     */     }
/*     */ 
/*     */     
/*     */     public MultiPolygon read(JsonReader jsonReader) throws IOException {
/* 361 */       return (MultiPolygon)readCoordinateContainer(jsonReader);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     CoordinateContainer<List<List<List<Point>>>> createCoordinateContainer(String type, BoundingBox bbox, List<List<List<Point>>> coords) {
/* 367 */       return new MultiPolygon((type == null) ? "MultiPolygon" : type, bbox, coords);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\MultiPolygon.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */