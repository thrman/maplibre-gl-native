/*     */ package com.gomap.geojson;
/*     */ 
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.annotation.Size;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import com.gomap.geojson.exception.GeoJsonException;
/*     */ import com.gomap.geojson.gson.GeoJsonAdapterFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Keep
/*     */ public final class Polygon
/*     */   implements CoordinateContainer<List<List<Point>>>
/*     */ {
/*     */   private static final String TYPE = "Polygon";
/*     */   private final String type;
/*     */   private final BoundingBox bbox;
/*     */   private final List<List<Point>> coordinates;
/*     */   
/*     */   public static Polygon fromJson(@NonNull String json) {
/*  82 */     GsonBuilder gson = new GsonBuilder();
/*  83 */     gson.registerTypeAdapterFactory(GeoJsonAdapterFactory.create());
/*  84 */     return (Polygon)gson.create().fromJson(json, Polygon.class);
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
/*     */   public static Polygon fromLngLats(@NonNull List<List<Point>> coordinates) {
/*  98 */     return new Polygon("Polygon", null, coordinates);
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
/*     */   public static Polygon fromLngLats(@NonNull List<List<Point>> coordinates, @Nullable BoundingBox bbox) {
/* 114 */     return new Polygon("Polygon", bbox, coordinates);
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
/*     */   static Polygon fromLngLats(@NonNull double[][][] coordinates) {
/* 127 */     List<List<Point>> converted = new ArrayList<>(coordinates.length);
/* 128 */     for (double[][] coordinate : coordinates) {
/* 129 */       List<Point> innerList = new ArrayList<>(coordinate.length);
/* 130 */       for (double[] pointCoordinate : coordinate) {
/* 131 */         innerList.add(Point.fromLngLat(pointCoordinate));
/*     */       }
/* 133 */       converted.add(innerList);
/*     */     } 
/* 135 */     return new Polygon("Polygon", null, converted);
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
/*     */   public static Polygon fromOuterInner(@NonNull LineString outer, @Nullable LineString... inner) {
/* 152 */     isLinearRing(outer);
/* 153 */     List<List<Point>> coordinates = new ArrayList<>();
/* 154 */     coordinates.add(outer.coordinates());
/*     */     
/* 156 */     if (inner == null) {
/* 157 */       return new Polygon("Polygon", null, coordinates);
/*     */     }
/* 159 */     for (LineString lineString : inner) {
/* 160 */       isLinearRing(lineString);
/* 161 */       coordinates.add(lineString.coordinates());
/*     */     } 
/* 163 */     return new Polygon("Polygon", null, coordinates);
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
/*     */   public static Polygon fromOuterInner(@NonNull LineString outer, @Nullable BoundingBox bbox, @Nullable LineString... inner) {
/* 182 */     isLinearRing(outer);
/* 183 */     List<List<Point>> coordinates = new ArrayList<>();
/* 184 */     coordinates.add(outer.coordinates());
/*     */     
/* 186 */     if (inner == null) {
/* 187 */       return new Polygon("Polygon", bbox, coordinates);
/*     */     }
/* 189 */     for (LineString lineString : inner) {
/* 190 */       isLinearRing(lineString);
/* 191 */       coordinates.add(lineString.coordinates());
/*     */     } 
/* 193 */     return new Polygon("Polygon", bbox, coordinates);
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
/*     */   public static Polygon fromOuterInner(@NonNull LineString outer, @Nullable @Size(min = 1L) List<LineString> inner) {
/* 213 */     isLinearRing(outer);
/* 214 */     List<List<Point>> coordinates = new ArrayList<>();
/* 215 */     coordinates.add(outer.coordinates());
/*     */     
/* 217 */     if (inner == null || inner.isEmpty()) {
/* 218 */       return new Polygon("Polygon", null, coordinates);
/*     */     }
/* 220 */     for (LineString lineString : inner) {
/* 221 */       isLinearRing(lineString);
/* 222 */       coordinates.add(lineString.coordinates());
/*     */     } 
/* 224 */     return new Polygon("Polygon", null, coordinates);
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
/*     */   public static Polygon fromOuterInner(@NonNull LineString outer, @Nullable BoundingBox bbox, @Nullable @Size(min = 1L) List<LineString> inner) {
/* 245 */     isLinearRing(outer);
/* 246 */     List<List<Point>> coordinates = new ArrayList<>();
/* 247 */     coordinates.add(outer.coordinates());
/*     */     
/* 249 */     if (inner == null) {
/* 250 */       return new Polygon("Polygon", bbox, coordinates);
/*     */     }
/* 252 */     for (LineString lineString : inner) {
/* 253 */       isLinearRing(lineString);
/* 254 */       coordinates.add(lineString.coordinates());
/*     */     } 
/* 256 */     return new Polygon("Polygon", bbox, coordinates);
/*     */   }
/*     */   
/*     */   Polygon(String type, @Nullable BoundingBox bbox, List<List<Point>> coordinates) {
/* 260 */     if (type == null) {
/* 261 */       throw new NullPointerException("Null type");
/*     */     }
/* 263 */     this.type = type;
/* 264 */     this.bbox = bbox;
/* 265 */     if (coordinates == null) {
/* 266 */       throw new NullPointerException("Null coordinates");
/*     */     }
/* 268 */     this.coordinates = coordinates;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public LineString outer() {
/* 280 */     return LineString.fromLngLats(coordinates().get(0));
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
/*     */   @Nullable
/*     */   public List<LineString> inner() {
/* 293 */     List<List<Point>> coordinates = coordinates();
/* 294 */     if (coordinates.size() <= 1) {
/* 295 */       return new ArrayList<>(0);
/*     */     }
/* 297 */     List<LineString> inner = new ArrayList<>(coordinates.size() - 1);
/* 298 */     for (List<Point> points : coordinates.subList(1, coordinates.size())) {
/* 299 */       inner.add(LineString.fromLngLats(points));
/*     */     }
/* 301 */     return inner;
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
/* 315 */     return this.type;
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
/* 331 */     return this.bbox;
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
/*     */   public List<List<Point>> coordinates() {
/* 345 */     return this.coordinates;
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
/* 357 */     GsonBuilder gson = new GsonBuilder();
/* 358 */     gson.registerTypeAdapterFactory(GeoJsonAdapterFactory.create());
/* 359 */     return gson.create().toJson(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeAdapter<Polygon> typeAdapter(Gson gson) {
/* 370 */     return new GsonTypeAdapter(gson);
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
/*     */   private static boolean isLinearRing(LineString lineString) {
/* 385 */     if (lineString.coordinates().size() < 4) {
/* 386 */       throw new GeoJsonException("LinearRings need to be made up of 4 or more coordinates.");
/*     */     }
/* 388 */     if (!((Point)lineString.coordinates().get(0)).equals(lineString
/* 389 */         .coordinates().get(lineString.coordinates().size() - 1))) {
/* 390 */       throw new GeoJsonException("LinearRings require first and last coordinate to be identical.");
/*     */     }
/* 392 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 397 */     return "Polygon{type=" + this.type + ", bbox=" + this.bbox + ", coordinates=" + this.coordinates + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 406 */     if (obj == this) {
/* 407 */       return true;
/*     */     }
/* 409 */     if (obj instanceof Polygon) {
/* 410 */       Polygon that = (Polygon)obj;
/* 411 */       return (this.type.equals(that.type()) && ((this.bbox == null) ? (that
/* 412 */         .bbox() == null) : this.bbox.equals(that.bbox())) && this.coordinates
/* 413 */         .equals(that.coordinates()));
/*     */     } 
/* 415 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 420 */     int hashCode = 1;
/* 421 */     hashCode *= 1000003;
/* 422 */     hashCode ^= this.type.hashCode();
/* 423 */     hashCode *= 1000003;
/* 424 */     hashCode ^= (this.bbox == null) ? 0 : this.bbox.hashCode();
/* 425 */     hashCode *= 1000003;
/* 426 */     hashCode ^= this.coordinates.hashCode();
/* 427 */     return hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class GsonTypeAdapter
/*     */     extends BaseGeometryTypeAdapter<Polygon, List<List<Point>>>
/*     */   {
/*     */     GsonTypeAdapter(Gson gson) {
/* 438 */       super(gson, new ListOfListOfPointCoordinatesTypeAdapter());
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(JsonWriter jsonWriter, Polygon object) throws IOException {
/* 443 */       writeCoordinateContainer(jsonWriter, object);
/*     */     }
/*     */ 
/*     */     
/*     */     public Polygon read(JsonReader jsonReader) throws IOException {
/* 448 */       return (Polygon)readCoordinateContainer(jsonReader);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     CoordinateContainer<List<List<Point>>> createCoordinateContainer(String type, BoundingBox bbox, List<List<Point>> coords) {
/* 455 */       return new Polygon((type == null) ? "Polygon" : type, bbox, coords);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\Polygon.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */