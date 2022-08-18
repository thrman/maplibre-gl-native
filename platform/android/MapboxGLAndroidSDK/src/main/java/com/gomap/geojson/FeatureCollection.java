/*     */ package com.gomap.geojson;
/*     */ 
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.annotations.JsonAdapter;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import com.gomap.geojson.gson.BoundingBoxTypeAdapter;
/*     */ import com.gomap.geojson.gson.GeoJsonAdapterFactory;
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
/*     */ @Keep
/*     */ public final class FeatureCollection
/*     */   implements GeoJson
/*     */ {
/*     */   private static final String TYPE = "FeatureCollection";
/*     */   private final String type;
/*     */   @JsonAdapter(BoundingBoxTypeAdapter.class)
/*     */   private final BoundingBox bbox;
/*     */   private final List<Feature> features;
/*     */   
/*     */   public static FeatureCollection fromJson(@NonNull String json) {
/*  65 */     GsonBuilder gson = new GsonBuilder();
/*  66 */     gson.registerTypeAdapterFactory(GeoJsonAdapterFactory.create());
/*  67 */     gson.registerTypeAdapterFactory(GeometryAdapterFactory.create());
/*  68 */     return (FeatureCollection)gson.create().fromJson(json, FeatureCollection.class);
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
/*     */   public static FeatureCollection fromFeatures(@NonNull Feature[] features) {
/*  82 */     return new FeatureCollection("FeatureCollection", null, Arrays.asList(features));
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
/*     */   public static FeatureCollection fromFeatures(@NonNull List<Feature> features) {
/*  95 */     return new FeatureCollection("FeatureCollection", null, features);
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
/*     */   public static FeatureCollection fromFeatures(@NonNull Feature[] features, @Nullable BoundingBox bbox) {
/* 111 */     return new FeatureCollection("FeatureCollection", bbox, Arrays.asList(features));
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
/*     */   public static FeatureCollection fromFeatures(@NonNull List<Feature> features, @Nullable BoundingBox bbox) {
/* 127 */     return new FeatureCollection("FeatureCollection", bbox, features);
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
/*     */   public static FeatureCollection fromFeature(@NonNull Feature feature) {
/* 139 */     List<Feature> featureList = Arrays.asList(new Feature[] { feature });
/* 140 */     return new FeatureCollection("FeatureCollection", null, featureList);
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
/*     */   public static FeatureCollection fromFeature(@NonNull Feature feature, @Nullable BoundingBox bbox) {
/* 154 */     List<Feature> featureList = Arrays.asList(new Feature[] { feature });
/* 155 */     return new FeatureCollection("FeatureCollection", bbox, featureList);
/*     */   }
/*     */   
/*     */   FeatureCollection(String type, @Nullable BoundingBox bbox, @Nullable List<Feature> features) {
/* 159 */     if (type == null) {
/* 160 */       throw new NullPointerException("Null type");
/*     */     }
/* 162 */     this.type = type;
/* 163 */     this.bbox = bbox;
/* 164 */     this.features = features;
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
/*     */   @Nullable
/*     */   public List<Feature> features() {
/* 207 */     return this.features;
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
/*     */   public String toJson() {
/* 220 */     GsonBuilder gson = new GsonBuilder();
/* 221 */     gson.registerTypeAdapterFactory(GeoJsonAdapterFactory.create());
/* 222 */     gson.registerTypeAdapterFactory(GeometryAdapterFactory.create());
/* 223 */     return gson.create().toJson(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeAdapter<FeatureCollection> typeAdapter(Gson gson) {
/* 234 */     return new GsonTypeAdapter(gson);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 239 */     return "FeatureCollection{type=" + this.type + ", bbox=" + this.bbox + ", features=" + this.features + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 248 */     if (obj == this) {
/* 249 */       return true;
/*     */     }
/* 251 */     if (obj instanceof FeatureCollection) {
/* 252 */       FeatureCollection that = (FeatureCollection)obj;
/* 253 */       return (this.type.equals(that.type()) && ((this.bbox == null) ? (that
/* 254 */         .bbox() == null) : this.bbox.equals(that.bbox())) && ((this.features == null) ? (that
/*     */         
/* 256 */         .features() == null) : this.features.equals(that.features())));
/*     */     } 
/* 258 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 263 */     int hashCode = 1;
/* 264 */     hashCode *= 1000003;
/* 265 */     hashCode ^= this.type.hashCode();
/* 266 */     hashCode *= 1000003;
/* 267 */     hashCode ^= (this.bbox == null) ? 0 : this.bbox.hashCode();
/* 268 */     hashCode *= 1000003;
/* 269 */     hashCode ^= (this.features == null) ? 0 : this.features.hashCode();
/* 270 */     return hashCode;
/*     */   }
/*     */ 
/*     */   
/*     */   static final class GsonTypeAdapter
/*     */     extends TypeAdapter<FeatureCollection>
/*     */   {
/*     */     private volatile TypeAdapter<String> stringAdapter;
/*     */     
/*     */     private volatile TypeAdapter<BoundingBox> boundingBoxAdapter;
/*     */     
/*     */     private volatile TypeAdapter<List<Feature>> listFeatureAdapter;
/*     */     private final Gson gson;
/*     */     
/*     */     GsonTypeAdapter(Gson gson) {
/* 285 */       this.gson = gson;
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(JsonWriter jsonWriter, FeatureCollection object) throws IOException {
/* 290 */       if (object == null) {
/* 291 */         jsonWriter.nullValue();
/*     */         return;
/*     */       } 
/* 294 */       jsonWriter.beginObject();
/* 295 */       jsonWriter.name("type");
/* 296 */       if (object.type() == null) {
/* 297 */         jsonWriter.nullValue();
/*     */       } else {
/* 299 */         TypeAdapter<String> stringAdapter = this.stringAdapter;
/* 300 */         if (stringAdapter == null) {
/* 301 */           stringAdapter = this.gson.getAdapter(String.class);
/* 302 */           this.stringAdapter = stringAdapter;
/*     */         } 
/* 304 */         stringAdapter.write(jsonWriter, object.type());
/*     */       } 
/* 306 */       jsonWriter.name("bbox");
/* 307 */       if (object.bbox() == null) {
/* 308 */         jsonWriter.nullValue();
/*     */       } else {
/* 310 */         TypeAdapter<BoundingBox> boundingBoxTypeAdapter = this.boundingBoxAdapter;
/* 311 */         if (boundingBoxTypeAdapter == null) {
/* 312 */           boundingBoxTypeAdapter = this.gson.getAdapter(BoundingBox.class);
/* 313 */           this.boundingBoxAdapter = boundingBoxTypeAdapter;
/*     */         } 
/* 315 */         boundingBoxTypeAdapter.write(jsonWriter, object.bbox());
/*     */       } 
/* 317 */       jsonWriter.name("features");
/* 318 */       if (object.features() == null) {
/* 319 */         jsonWriter.nullValue();
/*     */       } else {
/* 321 */         TypeAdapter<List<Feature>> listFeatureAdapter = this.listFeatureAdapter;
/* 322 */         if (listFeatureAdapter == null) {
/* 323 */           TypeToken typeToken = TypeToken.getParameterized(List.class, new Type[] { Feature.class });
/*     */           
/* 325 */           listFeatureAdapter = this.gson.getAdapter(typeToken);
/* 326 */           this.listFeatureAdapter = listFeatureAdapter;
/*     */         } 
/* 328 */         listFeatureAdapter.write(jsonWriter, object.features());
/*     */       } 
/* 330 */       jsonWriter.endObject();
/*     */     }
/*     */ 
/*     */     
/*     */     public FeatureCollection read(JsonReader jsonReader) throws IOException {
/* 335 */       if (jsonReader.peek() == JsonToken.NULL) {
/* 336 */         jsonReader.nextNull();
/* 337 */         return null;
/*     */       } 
/* 339 */       jsonReader.beginObject();
/* 340 */       String type = null;
/* 341 */       BoundingBox bbox = null;
/* 342 */       List<Feature> features = null;
/* 343 */       while (jsonReader.hasNext()) {
/* 344 */         TypeAdapter<String> stringAdapter; TypeAdapter<BoundingBox> boundingBoxAdapter; TypeAdapter<List<Feature>> listFeatureAdapter; String name = jsonReader.nextName();
/* 345 */         if (jsonReader.peek() == JsonToken.NULL) {
/* 346 */           jsonReader.nextNull();
/*     */           continue;
/*     */         } 
/* 349 */         switch (name) {
/*     */           case "type":
/* 351 */             stringAdapter = this.stringAdapter;
/* 352 */             if (stringAdapter == null) {
/* 353 */               stringAdapter = this.gson.getAdapter(String.class);
/* 354 */               this.stringAdapter = stringAdapter;
/*     */             } 
/* 356 */             type = (String)stringAdapter.read(jsonReader);
/*     */             continue;
/*     */           
/*     */           case "bbox":
/* 360 */             boundingBoxAdapter = this.boundingBoxAdapter;
/* 361 */             if (boundingBoxAdapter == null) {
/* 362 */               boundingBoxAdapter = this.gson.getAdapter(BoundingBox.class);
/* 363 */               this.boundingBoxAdapter = boundingBoxAdapter;
/*     */             } 
/* 365 */             bbox = (BoundingBox)boundingBoxAdapter.read(jsonReader);
/*     */             continue;
/*     */           
/*     */           case "features":
/* 369 */             listFeatureAdapter = this.listFeatureAdapter;
/* 370 */             if (listFeatureAdapter == null) {
/* 371 */               TypeToken typeToken = TypeToken.getParameterized(List.class, new Type[] { Feature.class });
/*     */               
/* 373 */               listFeatureAdapter = this.gson.getAdapter(typeToken);
/* 374 */               this.listFeatureAdapter = listFeatureAdapter;
/*     */             } 
/* 376 */             features = (List<Feature>)listFeatureAdapter.read(jsonReader);
/*     */             continue;
/*     */         } 
/*     */         
/* 380 */         jsonReader.skipValue();
/*     */       } 
/*     */ 
/*     */       
/* 384 */       jsonReader.endObject();
/* 385 */       return new FeatureCollection(type, bbox, features);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\FeatureCollection.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */