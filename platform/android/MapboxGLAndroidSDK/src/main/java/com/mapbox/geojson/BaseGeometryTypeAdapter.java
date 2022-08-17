/*     */ package com.mapbox.geojson;
/*     */ 
/*     */ import androidx.annotation.Keep;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import com.mapbox.geojson.exception.GeoJsonException;
/*     */ import com.mapbox.geojson.gson.BoundingBoxTypeAdapter;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
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
/*     */ abstract class BaseGeometryTypeAdapter<G, T>
/*     */   extends TypeAdapter<G>
/*     */ {
/*     */   private volatile TypeAdapter<String> stringAdapter;
/*     */   private volatile TypeAdapter<BoundingBox> boundingBoxAdapter;
/*     */   private volatile TypeAdapter<T> coordinatesAdapter;
/*     */   private final Gson gson;
/*     */   
/*     */   BaseGeometryTypeAdapter(Gson gson, TypeAdapter<T> coordinatesAdapter) {
/*  33 */     this.gson = gson;
/*  34 */     this.coordinatesAdapter = coordinatesAdapter;
/*  35 */     this.boundingBoxAdapter = (TypeAdapter<BoundingBox>)new BoundingBoxTypeAdapter();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeCoordinateContainer(JsonWriter jsonWriter, CoordinateContainer<T> object) throws IOException {
/*  40 */     if (object == null) {
/*  41 */       jsonWriter.nullValue();
/*     */       return;
/*     */     } 
/*  44 */     jsonWriter.beginObject();
/*  45 */     jsonWriter.name("type");
/*  46 */     if (object.type() == null) {
/*  47 */       jsonWriter.nullValue();
/*     */     } else {
/*  49 */       TypeAdapter<String> stringAdapter = this.stringAdapter;
/*  50 */       if (stringAdapter == null) {
/*  51 */         stringAdapter = this.gson.getAdapter(String.class);
/*  52 */         this.stringAdapter = stringAdapter;
/*     */       } 
/*  54 */       stringAdapter.write(jsonWriter, object.type());
/*     */     } 
/*  56 */     jsonWriter.name("bbox");
/*  57 */     if (object.bbox() == null) {
/*  58 */       jsonWriter.nullValue();
/*     */     } else {
/*  60 */       TypeAdapter<BoundingBox> boundingBoxAdapter = this.boundingBoxAdapter;
/*  61 */       if (boundingBoxAdapter == null) {
/*  62 */         boundingBoxAdapter = this.gson.getAdapter(BoundingBox.class);
/*  63 */         this.boundingBoxAdapter = boundingBoxAdapter;
/*     */       } 
/*  65 */       boundingBoxAdapter.write(jsonWriter, object.bbox());
/*     */     } 
/*  67 */     jsonWriter.name("coordinates");
/*  68 */     if (object.coordinates() == null) {
/*  69 */       jsonWriter.nullValue();
/*     */     } else {
/*  71 */       TypeAdapter<T> coordinatesAdapter = this.coordinatesAdapter;
/*  72 */       if (coordinatesAdapter == null) {
/*  73 */         throw new GeoJsonException("Coordinates type adapter is null");
/*     */       }
/*  75 */       coordinatesAdapter.write(jsonWriter, object.coordinates());
/*     */     } 
/*  77 */     jsonWriter.endObject();
/*     */   }
/*     */   
/*     */   public CoordinateContainer<T> readCoordinateContainer(JsonReader jsonReader) throws IOException {
/*  81 */     if (jsonReader.peek() == JsonToken.NULL) {
/*  82 */       jsonReader.nextNull();
/*  83 */       return null;
/*     */     } 
/*     */     
/*  86 */     jsonReader.beginObject();
/*  87 */     String type = null;
/*  88 */     BoundingBox bbox = null;
/*  89 */     T coordinates = null;
/*     */     
/*  91 */     while (jsonReader.hasNext()) {
/*  92 */       TypeAdapter<String> stringAdapter; TypeAdapter<BoundingBox> boundingBoxAdapter; TypeAdapter<T> coordinatesAdapter; String name = jsonReader.nextName();
/*  93 */       if (jsonReader.peek() == JsonToken.NULL) {
/*  94 */         jsonReader.nextNull();
/*     */         continue;
/*     */       } 
/*  97 */       switch (name) {
/*     */         case "type":
/*  99 */           stringAdapter = this.stringAdapter;
/* 100 */           if (stringAdapter == null) {
/* 101 */             stringAdapter = this.gson.getAdapter(String.class);
/* 102 */             this.stringAdapter = stringAdapter;
/*     */           } 
/* 104 */           type = (String)stringAdapter.read(jsonReader);
/*     */           continue;
/*     */         
/*     */         case "bbox":
/* 108 */           boundingBoxAdapter = this.boundingBoxAdapter;
/* 109 */           if (boundingBoxAdapter == null) {
/* 110 */             boundingBoxAdapter = this.gson.getAdapter(BoundingBox.class);
/* 111 */             this.boundingBoxAdapter = boundingBoxAdapter;
/*     */           } 
/* 113 */           bbox = (BoundingBox)boundingBoxAdapter.read(jsonReader);
/*     */           continue;
/*     */         
/*     */         case "coordinates":
/* 117 */           coordinatesAdapter = this.coordinatesAdapter;
/* 118 */           if (coordinatesAdapter == null) {
/* 119 */             throw new GeoJsonException("Coordinates type adapter is null");
/*     */           }
/* 121 */           coordinates = (T)coordinatesAdapter.read(jsonReader);
/*     */           continue;
/*     */       } 
/*     */       
/* 125 */       jsonReader.skipValue();
/*     */     } 
/*     */ 
/*     */     
/* 129 */     jsonReader.endObject();
/*     */     
/* 131 */     return createCoordinateContainer(type, bbox, coordinates);
/*     */   }
/*     */   
/*     */   abstract CoordinateContainer<T> createCoordinateContainer(String paramString, BoundingBox paramBoundingBox, T paramT);
/*     */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\BaseGeometryTypeAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */