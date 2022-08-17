/*     */ package com.mapbox.geojson;
/*     */ 
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import com.mapbox.geojson.gson.GeoJsonAdapterFactory;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
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
/*     */ @Keep
/*     */ public final class GeometryCollection
/*     */   implements Geometry
/*     */ {
/*     */   private static final String TYPE = "GeometryCollection";
/*     */   private final String type;
/*     */   private final BoundingBox bbox;
/*     */   private final List<Geometry> geometries;
/*     */   
/*     */   public static GeometryCollection fromJson(String json) {
/*  85 */     GsonBuilder gson = new GsonBuilder();
/*  86 */     gson.registerTypeAdapterFactory(GeoJsonAdapterFactory.create());
/*  87 */     gson.registerTypeAdapterFactory(GeometryAdapterFactory.create());
/*  88 */     return (GeometryCollection)gson.create().fromJson(json, GeometryCollection.class);
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
/*     */   public static GeometryCollection fromGeometries(@NonNull List<Geometry> geometries) {
/* 100 */     return new GeometryCollection("GeometryCollection", null, geometries);
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
/*     */   public static GeometryCollection fromGeometries(@NonNull List<Geometry> geometries, @Nullable BoundingBox bbox) {
/* 114 */     return new GeometryCollection("GeometryCollection", bbox, geometries);
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
/*     */   public static GeometryCollection fromGeometry(@NonNull Geometry geometry) {
/* 126 */     List<Geometry> geometries = Arrays.asList(new Geometry[] { geometry });
/* 127 */     return new GeometryCollection("GeometryCollection", null, geometries);
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
/*     */   public static GeometryCollection fromGeometry(@NonNull Geometry geometry, @Nullable BoundingBox bbox) {
/* 141 */     List<Geometry> geometries = Arrays.asList(new Geometry[] { geometry });
/* 142 */     return new GeometryCollection("GeometryCollection", bbox, geometries);
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
/*     */   GeometryCollection(String type, @Nullable BoundingBox bbox, List<Geometry> geometries) {
/* 156 */     if (type == null) {
/* 157 */       throw new NullPointerException("Null type");
/*     */     }
/* 159 */     this.type = type;
/* 160 */     this.bbox = bbox;
/* 161 */     if (geometries == null) {
/* 162 */       throw new NullPointerException("Null geometries");
/*     */     }
/* 164 */     this.geometries = geometries;
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
/* 178 */     return this.type;
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
/* 194 */     return this.bbox;
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
/*     */   @NonNull
/*     */   public List<Geometry> geometries() {
/* 207 */     return this.geometries;
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
/* 219 */     GsonBuilder gson = new GsonBuilder();
/* 220 */     gson.registerTypeAdapterFactory(GeoJsonAdapterFactory.create());
/* 221 */     gson.registerTypeAdapterFactory(GeometryAdapterFactory.create());
/* 222 */     return gson.create().toJson(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeAdapter<GeometryCollection> typeAdapter(Gson gson) {
/* 233 */     return new GsonTypeAdapter(gson);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 238 */     return "GeometryCollection{type=" + this.type + ", bbox=" + this.bbox + ", geometries=" + this.geometries + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 247 */     if (obj == this) {
/* 248 */       return true;
/*     */     }
/* 250 */     if (obj instanceof GeometryCollection) {
/* 251 */       GeometryCollection that = (GeometryCollection)obj;
/* 252 */       return (this.type.equals(that.type()) && ((this.bbox == null) ? (that
/* 253 */         .bbox() == null) : this.bbox.equals(that.bbox())) && this.geometries
/* 254 */         .equals(that.geometries()));
/*     */     } 
/* 256 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 261 */     int hashCode = 1;
/* 262 */     hashCode *= 1000003;
/* 263 */     hashCode ^= this.type.hashCode();
/* 264 */     hashCode *= 1000003;
/* 265 */     hashCode ^= (this.bbox == null) ? 0 : this.bbox.hashCode();
/* 266 */     hashCode *= 1000003;
/* 267 */     hashCode ^= this.geometries.hashCode();
/* 268 */     return hashCode;
/*     */   }
/*     */ 
/*     */   
/*     */   static final class GsonTypeAdapter
/*     */     extends TypeAdapter<GeometryCollection>
/*     */   {
/*     */     private volatile TypeAdapter<String> stringTypeAdapter;
/*     */     
/*     */     private volatile TypeAdapter<BoundingBox> boundingBoxTypeAdapter;
/*     */     
/*     */     private volatile TypeAdapter<List<Geometry>> listGeometryAdapter;
/*     */     private final Gson gson;
/*     */     
/*     */     GsonTypeAdapter(Gson gson) {
/* 283 */       this.gson = gson;
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(JsonWriter jsonWriter, GeometryCollection object) throws IOException {
/* 288 */       if (object == null) {
/* 289 */         jsonWriter.nullValue();
/*     */         return;
/*     */       } 
/* 292 */       jsonWriter.beginObject();
/* 293 */       jsonWriter.name("type");
/* 294 */       if (object.type() == null) {
/* 295 */         jsonWriter.nullValue();
/*     */       } else {
/* 297 */         TypeAdapter<String> stringTypeAdapter = this.stringTypeAdapter;
/* 298 */         if (stringTypeAdapter == null) {
/* 299 */           stringTypeAdapter = this.gson.getAdapter(String.class);
/* 300 */           this.stringTypeAdapter = stringTypeAdapter;
/*     */         } 
/* 302 */         stringTypeAdapter.write(jsonWriter, object.type());
/*     */       } 
/* 304 */       jsonWriter.name("bbox");
/* 305 */       if (object.bbox() == null) {
/* 306 */         jsonWriter.nullValue();
/*     */       } else {
/* 308 */         TypeAdapter<BoundingBox> boundingBoxTypeAdapter = this.boundingBoxTypeAdapter;
/* 309 */         if (boundingBoxTypeAdapter == null) {
/* 310 */           boundingBoxTypeAdapter = this.gson.getAdapter(BoundingBox.class);
/* 311 */           this.boundingBoxTypeAdapter = boundingBoxTypeAdapter;
/*     */         } 
/* 313 */         boundingBoxTypeAdapter.write(jsonWriter, object.bbox());
/*     */       } 
/* 315 */       jsonWriter.name("geometries");
/* 316 */       if (object.geometries() == null) {
/* 317 */         jsonWriter.nullValue();
/*     */       } else {
/* 319 */         TypeAdapter<List<Geometry>> listGeometryAdapter = this.listGeometryAdapter;
/* 320 */         if (listGeometryAdapter == null) {
/* 321 */           TypeToken typeToken = TypeToken.getParameterized(List.class, new Type[] { Geometry.class });
/*     */           
/* 323 */           listGeometryAdapter = this.gson.getAdapter(typeToken);
/* 324 */           this.listGeometryAdapter = listGeometryAdapter;
/*     */         } 
/* 326 */         listGeometryAdapter.write(jsonWriter, object.geometries());
/*     */       } 
/* 328 */       jsonWriter.endObject();
/*     */     }
/*     */ 
/*     */     
/*     */     public GeometryCollection read(JsonReader jsonReader) throws IOException {
/* 333 */       if (jsonReader.peek() == JsonToken.NULL) {
/* 334 */         jsonReader.nextNull();
/* 335 */         return null;
/*     */       } 
/* 337 */       jsonReader.beginObject();
/* 338 */       String type = null;
/* 339 */       BoundingBox bbox = null;
/* 340 */       List<Geometry> geometries = null;
/* 341 */       while (jsonReader.hasNext()) {
/* 342 */         TypeAdapter<String> stringTypeAdapter; TypeAdapter<BoundingBox> boundingBoxTypeAdapter; TypeAdapter<List<Geometry>> listGeometryAdapter; String name = jsonReader.nextName();
/* 343 */         if (jsonReader.peek() == JsonToken.NULL) {
/* 344 */           jsonReader.nextNull();
/*     */           continue;
/*     */         } 
/* 347 */         switch (name) {
/*     */           case "type":
/* 349 */             stringTypeAdapter = this.stringTypeAdapter;
/* 350 */             if (stringTypeAdapter == null) {
/* 351 */               stringTypeAdapter = this.gson.getAdapter(String.class);
/* 352 */               this.stringTypeAdapter = stringTypeAdapter;
/*     */             } 
/* 354 */             type = (String)stringTypeAdapter.read(jsonReader);
/*     */             continue;
/*     */           
/*     */           case "bbox":
/* 358 */             boundingBoxTypeAdapter = this.boundingBoxTypeAdapter;
/* 359 */             if (boundingBoxTypeAdapter == null) {
/* 360 */               boundingBoxTypeAdapter = this.gson.getAdapter(BoundingBox.class);
/* 361 */               this.boundingBoxTypeAdapter = boundingBoxTypeAdapter;
/*     */             } 
/* 363 */             bbox = (BoundingBox)boundingBoxTypeAdapter.read(jsonReader);
/*     */             continue;
/*     */           
/*     */           case "geometries":
/* 367 */             listGeometryAdapter = this.listGeometryAdapter;
/* 368 */             if (listGeometryAdapter == null) {
/* 369 */               TypeToken typeToken = TypeToken.getParameterized(List.class, new Type[] { Geometry.class });
/*     */               
/* 371 */               listGeometryAdapter = this.gson.getAdapter(typeToken);
/* 372 */               this.listGeometryAdapter = listGeometryAdapter;
/*     */             } 
/* 374 */             geometries = (List<Geometry>)listGeometryAdapter.read(jsonReader);
/*     */             continue;
/*     */         } 
/*     */         
/* 378 */         jsonReader.skipValue();
/*     */       } 
/*     */ 
/*     */       
/* 382 */       jsonReader.endObject();
/* 383 */       return new GeometryCollection((type == null) ? "GeometryCollection" : type, bbox, geometries);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\GeometryCollection.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */