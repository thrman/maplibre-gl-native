/*     */ package com.mapbox.geojson;
/*     */ 
/*     */ import androidx.annotation.FloatRange;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.mapbox.geojson.gson.BoundingBoxTypeAdapter;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class BoundingBox
/*     */   implements Serializable
/*     */ {
/*     */   private final Point southwest;
/*     */   private final Point northeast;
/*     */   
/*     */   public static BoundingBox fromJson(String json) {
/*  50 */     Gson gson = (new GsonBuilder()).registerTypeAdapter(BoundingBox.class, new BoundingBoxTypeAdapter()).create();
/*  51 */     return (BoundingBox)gson.fromJson(json, BoundingBox.class);
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
/*     */   public static BoundingBox fromPoints(@NonNull Point southwest, @NonNull Point northeast) {
/*  66 */     return new BoundingBox(southwest, northeast);
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
/*     */   @Deprecated
/*     */   public static BoundingBox fromCoordinates(@FloatRange(from = -180.0D, to = 180.0D) double west, @FloatRange(from = -90.0D, to = 90.0D) double south, @FloatRange(from = -180.0D, to = 180.0D) double east, @FloatRange(from = -90.0D, to = 90.0D) double north) {
/*  89 */     return fromLngLats(west, south, east, north);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static BoundingBox fromCoordinates(@FloatRange(from = -180.0D, to = 180.0D) double west, @FloatRange(from = -90.0D, to = 90.0D) double south, double southwestAltitude, @FloatRange(from = -180.0D, to = 180.0D) double east, @FloatRange(from = -90.0D, to = 90.0D) double north, double northEastAltitude) {
/* 117 */     return fromLngLats(west, south, southwestAltitude, east, north, northEastAltitude);
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
/*     */   public static BoundingBox fromLngLats(@FloatRange(from = -180.0D, to = 180.0D) double west, @FloatRange(from = -90.0D, to = 90.0D) double south, @FloatRange(from = -180.0D, to = 180.0D) double east, @FloatRange(from = -90.0D, to = 90.0D) double north) {
/* 137 */     return new BoundingBox(Point.fromLngLat(west, south), Point.fromLngLat(east, north));
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static BoundingBox fromLngLats(@FloatRange(from = -180.0D, to = 180.0D) double west, @FloatRange(from = -90.0D, to = 90.0D) double south, double southwestAltitude, @FloatRange(from = -180.0D, to = 180.0D) double east, @FloatRange(from = -90.0D, to = 90.0D) double north, double northEastAltitude) {
/* 163 */     return new BoundingBox(
/* 164 */         Point.fromLngLat(west, south, southwestAltitude), 
/* 165 */         Point.fromLngLat(east, north, northEastAltitude));
/*     */   }
/*     */   
/*     */   BoundingBox(Point southwest, Point northeast) {
/* 169 */     if (southwest == null) {
/* 170 */       throw new NullPointerException("Null southwest");
/*     */     }
/* 172 */     this.southwest = southwest;
/* 173 */     if (northeast == null) {
/* 174 */       throw new NullPointerException("Null northeast");
/*     */     }
/* 176 */     this.northeast = northeast;
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
/*     */   public Point southwest() {
/* 188 */     return this.southwest;
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
/*     */   public Point northeast() {
/* 200 */     return this.northeast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double west() {
/* 211 */     return southwest().longitude();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double south() {
/* 222 */     return southwest().latitude();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double east() {
/* 233 */     return northeast().longitude();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double north() {
/* 244 */     return northeast().latitude();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeAdapter<BoundingBox> typeAdapter(Gson gson) {
/* 255 */     return (TypeAdapter<BoundingBox>)new BoundingBoxTypeAdapter();
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
/*     */   public final String toJson() {
/* 268 */     Gson gson = (new GsonBuilder()).registerTypeAdapter(BoundingBox.class, new BoundingBoxTypeAdapter()).create();
/* 269 */     return gson.toJson(this, BoundingBox.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 274 */     return "BoundingBox{southwest=" + this.southwest + ", northeast=" + this.northeast + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 282 */     if (obj == this) {
/* 283 */       return true;
/*     */     }
/* 285 */     if (obj instanceof BoundingBox) {
/* 286 */       BoundingBox that = (BoundingBox)obj;
/* 287 */       return (this.southwest.equals(that.southwest()) && this.northeast
/* 288 */         .equals(that.northeast()));
/*     */     } 
/* 290 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 295 */     int hashCode = 1;
/* 296 */     hashCode *= 1000003;
/* 297 */     hashCode ^= this.southwest.hashCode();
/* 298 */     hashCode *= 1000003;
/* 299 */     hashCode ^= this.northeast.hashCode();
/* 300 */     return hashCode;
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\BoundingBox.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */