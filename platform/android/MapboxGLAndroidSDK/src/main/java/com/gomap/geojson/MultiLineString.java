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
/*     */ @Keep
/*     */ public final class MultiLineString
/*     */   implements CoordinateContainer<List<List<Point>>>
/*     */ {
/*     */   private static final String TYPE = "MultiLineString";
/*     */   private final String type;
/*     */   private final BoundingBox bbox;
/*     */   private final List<List<Point>> coordinates;
/*     */   
/*     */   public static MultiLineString fromJson(@NonNull String json) {
/*  75 */     GsonBuilder gson = new GsonBuilder();
/*  76 */     gson.registerTypeAdapterFactory(GeoJsonAdapterFactory.create());
/*  77 */     return (MultiLineString)gson.create().fromJson(json, MultiLineString.class);
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
/*     */   public static MultiLineString fromLineStrings(@NonNull List<LineString> lineStrings) {
/*  91 */     List<List<Point>> coordinates = new ArrayList<>(lineStrings.size());
/*  92 */     for (LineString lineString : lineStrings) {
/*  93 */       coordinates.add(lineString.coordinates());
/*     */     }
/*  95 */     return new MultiLineString("MultiLineString", null, coordinates);
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
/*     */   public static MultiLineString fromLineStrings(@NonNull List<LineString> lineStrings, @Nullable BoundingBox bbox) {
/* 112 */     List<List<Point>> coordinates = new ArrayList<>(lineStrings.size());
/* 113 */     for (LineString lineString : lineStrings) {
/* 114 */       coordinates.add(lineString.coordinates());
/*     */     }
/* 116 */     return new MultiLineString("MultiLineString", bbox, coordinates);
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
/*     */   public static MultiLineString fromLineString(@NonNull LineString lineString) {
/* 129 */     List<List<Point>> coordinates = Arrays.asList((List<Point>[])new List[] { lineString.coordinates() });
/* 130 */     return new MultiLineString("MultiLineString", null, coordinates);
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
/*     */   public static MultiLineString fromLineString(@NonNull LineString lineString, @Nullable BoundingBox bbox) {
/* 145 */     List<List<Point>> coordinates = Arrays.asList((List<Point>[])new List[] { lineString.coordinates() });
/* 146 */     return new MultiLineString("MultiLineString", bbox, coordinates);
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
/*     */   public static MultiLineString fromLngLats(@NonNull List<List<Point>> points) {
/* 161 */     return new MultiLineString("MultiLineString", null, points);
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
/*     */   public static MultiLineString fromLngLats(@NonNull List<List<Point>> points, @Nullable BoundingBox bbox) {
/* 178 */     return new MultiLineString("MultiLineString", bbox, points);
/*     */   }
/*     */   
/*     */   static MultiLineString fromLngLats(double[][][] coordinates) {
/* 182 */     List<List<Point>> multiLine = new ArrayList<>(coordinates.length);
/* 183 */     for (int i = 0; i < coordinates.length; i++) {
/* 184 */       List<Point> lineString = new ArrayList<>((coordinates[i]).length);
/* 185 */       for (int j = 0; j < (coordinates[i]).length; j++) {
/* 186 */         lineString.add(Point.fromLngLat(coordinates[i][j]));
/*     */       }
/* 188 */       multiLine.add(lineString);
/*     */     } 
/*     */     
/* 191 */     return new MultiLineString("MultiLineString", null, multiLine);
/*     */   }
/*     */   
/*     */   MultiLineString(String type, @Nullable BoundingBox bbox, List<List<Point>> coordinates) {
/* 195 */     if (type == null) {
/* 196 */       throw new NullPointerException("Null type");
/*     */     }
/* 198 */     this.type = type;
/* 199 */     this.bbox = bbox;
/* 200 */     if (coordinates == null) {
/* 201 */       throw new NullPointerException("Null coordinates");
/*     */     }
/* 203 */     this.coordinates = coordinates;
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
/* 217 */     return this.type;
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
/* 233 */     return this.bbox;
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
/*     */   public List<List<Point>> coordinates() {
/* 245 */     return this.coordinates;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<LineString> lineStrings() {
/* 255 */     List<List<Point>> coordinates = coordinates();
/* 256 */     List<LineString> lineStrings = new ArrayList<>(coordinates.size());
/* 257 */     for (List<Point> points : coordinates) {
/* 258 */       lineStrings.add(LineString.fromLngLats(points));
/*     */     }
/* 260 */     return lineStrings;
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
/* 272 */     GsonBuilder gson = new GsonBuilder();
/* 273 */     gson.registerTypeAdapterFactory(GeoJsonAdapterFactory.create());
/* 274 */     return gson.create().toJson(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeAdapter<MultiLineString> typeAdapter(Gson gson) {
/* 285 */     return new GsonTypeAdapter(gson);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 290 */     return "MultiLineString{type=" + this.type + ", bbox=" + this.bbox + ", coordinates=" + this.coordinates + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 299 */     if (obj == this) {
/* 300 */       return true;
/*     */     }
/* 302 */     if (obj instanceof MultiLineString) {
/* 303 */       MultiLineString that = (MultiLineString)obj;
/* 304 */       return (this.type.equals(that.type()) && ((this.bbox == null) ? (that
/* 305 */         .bbox() == null) : this.bbox.equals(that.bbox())) && this.coordinates
/* 306 */         .equals(that.coordinates()));
/*     */     } 
/* 308 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 313 */     int hashCode = 1;
/* 314 */     hashCode *= 1000003;
/* 315 */     hashCode ^= this.type.hashCode();
/* 316 */     hashCode *= 1000003;
/* 317 */     hashCode ^= (this.bbox == null) ? 0 : this.bbox.hashCode();
/* 318 */     hashCode *= 1000003;
/* 319 */     hashCode ^= this.coordinates.hashCode();
/* 320 */     return hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class GsonTypeAdapter
/*     */     extends BaseGeometryTypeAdapter<MultiLineString, List<List<Point>>>
/*     */   {
/*     */     GsonTypeAdapter(Gson gson) {
/* 332 */       super(gson, new ListOfListOfPointCoordinatesTypeAdapter());
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(JsonWriter jsonWriter, MultiLineString object) throws IOException {
/* 337 */       writeCoordinateContainer(jsonWriter, object);
/*     */     }
/*     */ 
/*     */     
/*     */     public MultiLineString read(JsonReader jsonReader) throws IOException {
/* 342 */       return (MultiLineString)readCoordinateContainer(jsonReader);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     CoordinateContainer<List<List<Point>>> createCoordinateContainer(String type, BoundingBox bbox, List<List<Point>> coords) {
/* 349 */       return new MultiLineString((type == null) ? "MultiLineString" : type, bbox, coords);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\MultiLineString.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */