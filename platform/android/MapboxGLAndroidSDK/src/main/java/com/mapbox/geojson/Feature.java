/*     */ package com.mapbox.geojson;
/*     */ 
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.annotations.JsonAdapter;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import com.mapbox.geojson.gson.BoundingBoxTypeAdapter;
/*     */ import com.mapbox.geojson.gson.GeoJsonAdapterFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class Feature
/*     */   implements GeoJson
/*     */ {
/*     */   private static final String TYPE = "Feature";
/*     */   private final String type;
/*     */   @JsonAdapter(BoundingBoxTypeAdapter.class)
/*     */   private final BoundingBox bbox;
/*     */   private final String id;
/*     */   private final Geometry geometry;
/*     */   private final JsonObject properties;
/*     */   
/*     */   public static Feature fromJson(@NonNull String json) {
/*  78 */     GsonBuilder gson = new GsonBuilder();
/*  79 */     gson.registerTypeAdapterFactory(GeoJsonAdapterFactory.create());
/*  80 */     gson.registerTypeAdapterFactory(GeometryAdapterFactory.create());
/*     */     
/*  82 */     Feature feature = (Feature)gson.create().fromJson(json, Feature.class);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     if (feature.properties() != null) {
/*  88 */       return feature;
/*     */     }
/*  90 */     return new Feature("Feature", feature.bbox(), feature
/*  91 */         .id(), feature.geometry(), new JsonObject());
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
/*     */   public static Feature fromGeometry(@Nullable Geometry geometry) {
/* 103 */     return new Feature("Feature", null, null, geometry, new JsonObject());
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
/*     */   public static Feature fromGeometry(@Nullable Geometry geometry, @Nullable BoundingBox bbox) {
/* 117 */     return new Feature("Feature", bbox, null, geometry, new JsonObject());
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
/*     */   public static Feature fromGeometry(@Nullable Geometry geometry, @Nullable JsonObject properties) {
/* 131 */     return new Feature("Feature", null, null, geometry, 
/* 132 */         (properties == null) ? new JsonObject() : properties);
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
/*     */   public static Feature fromGeometry(@Nullable Geometry geometry, @Nullable JsonObject properties, @Nullable BoundingBox bbox) {
/* 148 */     return new Feature("Feature", bbox, null, geometry, 
/* 149 */         (properties == null) ? new JsonObject() : properties);
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
/*     */   public static Feature fromGeometry(@Nullable Geometry geometry, @Nullable JsonObject properties, @Nullable String id) {
/* 164 */     return new Feature("Feature", null, id, geometry, 
/* 165 */         (properties == null) ? new JsonObject() : properties);
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
/*     */   public static Feature fromGeometry(@Nullable Geometry geometry, @NonNull JsonObject properties, @Nullable String id, @Nullable BoundingBox bbox) {
/* 181 */     return new Feature("Feature", bbox, id, geometry, 
/* 182 */         (properties == null) ? new JsonObject() : properties);
/*     */   }
/*     */ 
/*     */   
/*     */   Feature(String type, @Nullable BoundingBox bbox, @Nullable String id, @Nullable Geometry geometry, @Nullable JsonObject properties) {
/* 187 */     if (type == null) {
/* 188 */       throw new NullPointerException("Null type");
/*     */     }
/* 190 */     this.type = type;
/* 191 */     this.bbox = bbox;
/* 192 */     this.id = id;
/* 193 */     this.geometry = geometry;
/* 194 */     this.properties = properties;
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
/* 208 */     return this.type;
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
/* 224 */     return this.bbox;
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
/*     */   public String id() {
/* 236 */     return this.id;
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
/*     */   public Geometry geometry() {
/* 249 */     return this.geometry;
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
/*     */   public JsonObject properties() {
/* 261 */     return this.properties;
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
/*     */   public String toJson() {
/* 277 */     Gson gson = (new GsonBuilder()).registerTypeAdapterFactory(GeoJsonAdapterFactory.create()).registerTypeAdapterFactory(GeometryAdapterFactory.create()).create();
/*     */ 
/*     */ 
/*     */     
/* 281 */     Feature feature = this;
/* 282 */     if (properties().size() == 0) {
/* 283 */       feature = new Feature("Feature", bbox(), id(), geometry(), null);
/*     */     }
/*     */     
/* 286 */     return gson.toJson(feature);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeAdapter<Feature> typeAdapter(Gson gson) {
/* 297 */     return new GsonTypeAdapter(gson);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStringProperty(String key, String value) {
/* 308 */     properties().addProperty(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNumberProperty(String key, Number value) {
/* 319 */     properties().addProperty(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBooleanProperty(String key, Boolean value) {
/* 330 */     properties().addProperty(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCharacterProperty(String key, Character value) {
/* 341 */     properties().addProperty(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addProperty(String key, JsonElement value) {
/* 352 */     properties().add(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStringProperty(String key) {
/* 363 */     JsonElement propertyKey = properties().get(key);
/* 364 */     return (propertyKey == null) ? null : propertyKey.getAsString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getNumberProperty(String key) {
/* 375 */     JsonElement propertyKey = properties().get(key);
/* 376 */     return (propertyKey == null) ? null : propertyKey.getAsNumber();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean getBooleanProperty(String key) {
/* 387 */     JsonElement propertyKey = properties().get(key);
/* 388 */     return (propertyKey == null) ? null : Boolean.valueOf(propertyKey.getAsBoolean());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Character getCharacterProperty(String key) {
/* 399 */     JsonElement propertyKey = properties().get(key);
/* 400 */     return (propertyKey == null) ? null : Character.valueOf(propertyKey.getAsCharacter());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonElement getProperty(String key) {
/* 411 */     return properties().get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonElement removeProperty(String key) {
/* 422 */     return properties().remove(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasProperty(String key) {
/* 433 */     return properties().has(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNonNullValueForProperty(String key) {
/* 444 */     return (hasProperty(key) && !getProperty(key).isJsonNull());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 449 */     return "Feature{type=" + this.type + ", bbox=" + this.bbox + ", id=" + this.id + ", geometry=" + this.geometry + ", properties=" + this.properties + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 460 */     if (obj == this) {
/* 461 */       return true;
/*     */     }
/* 463 */     if (obj instanceof Feature) {
/* 464 */       Feature that = (Feature)obj;
/* 465 */       if (this.type.equals(that.type()) && ((this.bbox == null) ? (that
/* 466 */         .bbox() == null) : this.bbox.equals(that.bbox())) && ((this.id == null) ? (that
/* 467 */         .id() == null) : this.id.equals(that.id())) && ((this.geometry == null) ? (that
/*     */         
/* 469 */         .geometry() == null) : this.geometry.equals(that.geometry()))) if ((this.properties == null) ? (that
/*     */           
/* 471 */           .properties() == null) : this.properties.equals(that.properties()));  return false;
/*     */     } 
/* 473 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 478 */     int hashCode = 1;
/* 479 */     hashCode *= 1000003;
/* 480 */     hashCode ^= this.type.hashCode();
/* 481 */     hashCode *= 1000003;
/* 482 */     hashCode ^= (this.bbox == null) ? 0 : this.bbox.hashCode();
/* 483 */     hashCode *= 1000003;
/* 484 */     hashCode ^= (this.id == null) ? 0 : this.id.hashCode();
/* 485 */     hashCode *= 1000003;
/* 486 */     hashCode ^= (this.geometry == null) ? 0 : this.geometry.hashCode();
/* 487 */     hashCode *= 1000003;
/* 488 */     hashCode ^= (this.properties == null) ? 0 : this.properties.hashCode();
/* 489 */     return hashCode;
/*     */   }
/*     */ 
/*     */   
/*     */   static final class GsonTypeAdapter
/*     */     extends TypeAdapter<Feature>
/*     */   {
/*     */     private volatile TypeAdapter<String> stringTypeAdapter;
/*     */     
/*     */     private volatile TypeAdapter<BoundingBox> boundingBoxTypeAdapter;
/*     */     
/*     */     private volatile TypeAdapter<Geometry> geometryTypeAdapter;
/*     */     private volatile TypeAdapter<JsonObject> jsonObjectTypeAdapter;
/*     */     private final Gson gson;
/*     */     
/*     */     GsonTypeAdapter(Gson gson) {
/* 505 */       this.gson = gson;
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(JsonWriter jsonWriter, Feature object) throws IOException {
/* 510 */       if (object == null) {
/* 511 */         jsonWriter.nullValue();
/*     */         return;
/*     */       } 
/* 514 */       jsonWriter.beginObject();
/* 515 */       jsonWriter.name("type");
/* 516 */       if (object.type() == null) {
/* 517 */         jsonWriter.nullValue();
/*     */       } else {
/* 519 */         TypeAdapter<String> stringTypeAdapter = this.stringTypeAdapter;
/* 520 */         if (stringTypeAdapter == null) {
/* 521 */           stringTypeAdapter = this.gson.getAdapter(String.class);
/* 522 */           this.stringTypeAdapter = stringTypeAdapter;
/*     */         } 
/* 524 */         stringTypeAdapter.write(jsonWriter, object.type());
/*     */       } 
/* 526 */       jsonWriter.name("bbox");
/* 527 */       if (object.bbox() == null) {
/* 528 */         jsonWriter.nullValue();
/*     */       } else {
/* 530 */         TypeAdapter<BoundingBox> boundingBoxTypeAdapter = this.boundingBoxTypeAdapter;
/* 531 */         if (boundingBoxTypeAdapter == null) {
/* 532 */           boundingBoxTypeAdapter = this.gson.getAdapter(BoundingBox.class);
/* 533 */           this.boundingBoxTypeAdapter = boundingBoxTypeAdapter;
/*     */         } 
/* 535 */         boundingBoxTypeAdapter.write(jsonWriter, object.bbox());
/*     */       } 
/* 537 */       jsonWriter.name("id");
/* 538 */       if (object.id() == null) {
/* 539 */         jsonWriter.nullValue();
/*     */       } else {
/* 541 */         TypeAdapter<String> stringTypeAdapter = this.stringTypeAdapter;
/* 542 */         if (stringTypeAdapter == null) {
/* 543 */           stringTypeAdapter = this.gson.getAdapter(String.class);
/* 544 */           this.stringTypeAdapter = stringTypeAdapter;
/*     */         } 
/* 546 */         stringTypeAdapter.write(jsonWriter, object.id());
/*     */       } 
/* 548 */       jsonWriter.name("geometry");
/* 549 */       if (object.geometry() == null) {
/* 550 */         jsonWriter.nullValue();
/*     */       } else {
/* 552 */         TypeAdapter<Geometry> geometryTypeAdapter = this.geometryTypeAdapter;
/* 553 */         if (geometryTypeAdapter == null) {
/* 554 */           geometryTypeAdapter = this.gson.getAdapter(Geometry.class);
/* 555 */           this.geometryTypeAdapter = geometryTypeAdapter;
/*     */         } 
/* 557 */         geometryTypeAdapter.write(jsonWriter, object.geometry());
/*     */       } 
/* 559 */       jsonWriter.name("properties");
/* 560 */       if (object.properties() == null) {
/* 561 */         jsonWriter.nullValue();
/*     */       } else {
/* 563 */         TypeAdapter<JsonObject> jsonObjectTypeAdapter = this.jsonObjectTypeAdapter;
/* 564 */         if (jsonObjectTypeAdapter == null) {
/* 565 */           jsonObjectTypeAdapter = this.gson.getAdapter(JsonObject.class);
/* 566 */           this.jsonObjectTypeAdapter = jsonObjectTypeAdapter;
/*     */         } 
/* 568 */         jsonObjectTypeAdapter.write(jsonWriter, object.properties());
/*     */       } 
/* 570 */       jsonWriter.endObject();
/*     */     }
/*     */ 
/*     */     
/*     */     public Feature read(JsonReader jsonReader) throws IOException {
/* 575 */       if (jsonReader.peek() == JsonToken.NULL) {
/* 576 */         jsonReader.nextNull();
/* 577 */         return null;
/*     */       } 
/* 579 */       jsonReader.beginObject();
/* 580 */       String type = null;
/* 581 */       BoundingBox bbox = null;
/* 582 */       String id = null;
/* 583 */       Geometry geometry = null;
/* 584 */       JsonObject properties = null;
/* 585 */       while (jsonReader.hasNext()) {
/* 586 */         TypeAdapter<String> strTypeAdapter; TypeAdapter<BoundingBox> boundingBoxTypeAdapter; TypeAdapter<Geometry> geometryTypeAdapter; TypeAdapter<JsonObject> jsonObjectTypeAdapter; String name = jsonReader.nextName();
/* 587 */         if (jsonReader.peek() == JsonToken.NULL) {
/* 588 */           jsonReader.nextNull();
/*     */           continue;
/*     */         } 
/* 591 */         switch (name) {
/*     */           case "type":
/* 593 */             strTypeAdapter = this.stringTypeAdapter;
/* 594 */             if (strTypeAdapter == null) {
/* 595 */               strTypeAdapter = this.gson.getAdapter(String.class);
/* 596 */               this.stringTypeAdapter = strTypeAdapter;
/*     */             } 
/* 598 */             type = (String)strTypeAdapter.read(jsonReader);
/*     */             continue;
/*     */           
/*     */           case "bbox":
/* 602 */             boundingBoxTypeAdapter = this.boundingBoxTypeAdapter;
/* 603 */             if (boundingBoxTypeAdapter == null) {
/* 604 */               boundingBoxTypeAdapter = this.gson.getAdapter(BoundingBox.class);
/* 605 */               this.boundingBoxTypeAdapter = boundingBoxTypeAdapter;
/*     */             } 
/* 607 */             bbox = (BoundingBox)boundingBoxTypeAdapter.read(jsonReader);
/*     */             continue;
/*     */           
/*     */           case "id":
/* 611 */             strTypeAdapter = this.stringTypeAdapter;
/* 612 */             if (strTypeAdapter == null) {
/* 613 */               strTypeAdapter = this.gson.getAdapter(String.class);
/* 614 */               this.stringTypeAdapter = strTypeAdapter;
/*     */             } 
/* 616 */             id = (String)strTypeAdapter.read(jsonReader);
/*     */             continue;
/*     */           
/*     */           case "geometry":
/* 620 */             geometryTypeAdapter = this.geometryTypeAdapter;
/* 621 */             if (geometryTypeAdapter == null) {
/* 622 */               geometryTypeAdapter = this.gson.getAdapter(Geometry.class);
/* 623 */               this.geometryTypeAdapter = geometryTypeAdapter;
/*     */             } 
/* 625 */             geometry = (Geometry)geometryTypeAdapter.read(jsonReader);
/*     */             continue;
/*     */           
/*     */           case "properties":
/* 629 */             jsonObjectTypeAdapter = this.jsonObjectTypeAdapter;
/* 630 */             if (jsonObjectTypeAdapter == null) {
/* 631 */               jsonObjectTypeAdapter = this.gson.getAdapter(JsonObject.class);
/* 632 */               this.jsonObjectTypeAdapter = jsonObjectTypeAdapter;
/*     */             } 
/* 634 */             properties = (JsonObject)jsonObjectTypeAdapter.read(jsonReader);
/*     */             continue;
/*     */         } 
/*     */         
/* 638 */         jsonReader.skipValue();
/*     */       } 
/*     */ 
/*     */       
/* 642 */       jsonReader.endObject();
/* 643 */       return new Feature(type, bbox, id, geometry, properties);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\Feature.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */