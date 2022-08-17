/*     */ package com.mapbox.turf;
/*     */ 
/*     */ import com.mapbox.geojson.Feature;
/*     */ import com.mapbox.geojson.FeatureCollection;
/*     */ import com.mapbox.geojson.GeoJson;
/*     */ import com.mapbox.geojson.Point;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TurfAssertions
/*     */ {
/*     */   @Deprecated
/*     */   public static Point getCoord(Feature obj) {
/*  32 */     return TurfMeta.getCoord(obj);
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
/*     */   public static void geojsonType(GeoJson value, String type, String name) {
/*  45 */     if (type == null || type.length() == 0 || name == null || name.length() == 0) {
/*  46 */       throw new TurfException("Type and name required");
/*     */     }
/*  48 */     if (value == null || !value.type().equals(type)) {
/*  49 */       throw new TurfException("Invalid input to " + name + ": must be a " + type + ", given " + (
/*  50 */           (value != null) ? value.type() : " null"));
/*     */     }
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
/*     */   public static void featureOf(Feature feature, String type, String name) {
/*  65 */     if (name == null || name.length() == 0) {
/*  66 */       throw new TurfException(".featureOf() requires a name");
/*     */     }
/*  68 */     if (feature == null || !feature.type().equals("Feature") || feature.geometry() == null) {
/*  69 */       throw new TurfException(String.format("Invalid input to %s, Feature with geometry required", new Object[] { name }));
/*     */     }
/*     */     
/*  72 */     if (feature.geometry() == null || !feature.geometry().type().equals(type)) {
/*  73 */       throw new TurfException(String.format("Invalid input to %s: must be a %s, given %s", new Object[] { name, type, feature
/*  74 */               .geometry().type() }));
/*     */     }
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
/*     */   public static void collectionOf(FeatureCollection featureCollection, String type, String name) {
/*  89 */     if (name == null || name.length() == 0) {
/*  90 */       throw new TurfException("collectionOf() requires a name");
/*     */     }
/*  92 */     if (featureCollection == null || !featureCollection.type().equals("FeatureCollection") || featureCollection
/*  93 */       .features() == null) {
/*  94 */       throw new TurfException(String.format("Invalid input to %s, FeatureCollection required", new Object[] { name }));
/*     */     }
/*     */     
/*  97 */     for (Feature feature : featureCollection.features()) {
/*  98 */       if (feature == null || !feature.type().equals("Feature") || feature.geometry() == null) {
/*  99 */         throw new TurfException(String.format("Invalid input to %s, Feature with geometry required", new Object[] { name }));
/*     */       }
/*     */       
/* 102 */       if (feature.geometry() == null || !feature.geometry().type().equals(type))
/* 103 */         throw new TurfException(String.format("Invalid input to %s: must be a %s, given %s", new Object[] { name, type, feature
/* 104 */                 .geometry().type() })); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-turf-5.9.0.jar!\com\mapbox\turf\TurfAssertions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */