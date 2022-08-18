/*     */ package com.gomap.turf;
/*     */ 
/*     */ import androidx.annotation.FloatRange;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.gomap.geojson.Feature;
/*     */ import com.gomap.geojson.FeatureCollection;
/*     */ import com.gomap.geojson.Geometry;
/*     */ import com.gomap.geojson.LineString;
/*     */ import com.gomap.geojson.MultiLineString;
/*     */ import com.gomap.geojson.MultiPoint;
/*     */ import com.gomap.geojson.MultiPolygon;
/*     */ import com.gomap.geojson.Point;
/*     */ import com.gomap.geojson.Polygon;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TurfConversion
/*     */ {
/*  36 */   private static final Map<String, Double> FACTORS = new HashMap<>(); static {
/*  37 */     FACTORS.put("miles", Double.valueOf(3960.0D));
/*  38 */     FACTORS.put("nauticalmiles", Double.valueOf(3441.145D));
/*  39 */     FACTORS.put("degrees", Double.valueOf(57.2957795D));
/*  40 */     FACTORS.put("radians", Double.valueOf(1.0D));
/*  41 */     FACTORS.put("inches", Double.valueOf(2.509056E8D));
/*  42 */     FACTORS.put("yards", Double.valueOf(6969600.0D));
/*  43 */     FACTORS.put("meters", Double.valueOf(6373000.0D));
/*  44 */     FACTORS.put("centimeters", Double.valueOf(6.373E8D));
/*  45 */     FACTORS.put("kilometers", Double.valueOf(6373.0D));
/*  46 */     FACTORS.put("feet", Double.valueOf(2.090879265E7D));
/*  47 */     FACTORS.put("centimetres", Double.valueOf(6.373E8D));
/*  48 */     FACTORS.put("metres", Double.valueOf(6373000.0D));
/*  49 */     FACTORS.put("kilometres", Double.valueOf(6373.0D));
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
/*     */   public static double lengthToDegrees(double distance, String units) {
/*  68 */     return radiansToDegrees(lengthToRadians(distance, units));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double degreesToRadians(double degrees) {
/*  79 */     double radians = degrees % 360.0D;
/*  80 */     return radians * Math.PI / 180.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double radiansToDegrees(double radians) {
/*  91 */     double degrees = radians % 6.283185307179586D;
/*  92 */     return degrees * 180.0D / Math.PI;
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
/*     */   public static double radiansToLength(double radians) {
/* 104 */     return radiansToLength(radians, "kilometers");
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
/*     */   public static double radiansToLength(double radians, @NonNull String units) {
/* 117 */     return radians * ((Double)FACTORS.get(units)).doubleValue();
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
/*     */   public static double lengthToRadians(double distance) {
/* 130 */     return lengthToRadians(distance, "kilometers");
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
/*     */   public static double lengthToRadians(double distance, @NonNull String units) {
/* 143 */     return distance / ((Double)FACTORS.get(units)).doubleValue();
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
/*     */   public static double convertLength(@FloatRange(from = 0.0D) double distance, @NonNull String originalUnit) {
/* 158 */     return convertLength(distance, originalUnit, "kilometers");
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
/*     */   public static double convertLength(@FloatRange(from = 0.0D) double distance, @NonNull String originalUnit, @Nullable String finalUnit) {
/* 174 */     if (finalUnit == null) {
/* 175 */       finalUnit = "kilometers";
/*     */     }
/* 177 */     return radiansToLength(lengthToRadians(distance, originalUnit), finalUnit);
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
/*     */   public static FeatureCollection explode(@NonNull FeatureCollection featureCollection) {
/* 189 */     List<Feature> finalFeatureList = new ArrayList<>();
/* 190 */     for (Point singlePoint : TurfMeta.coordAll(featureCollection, true)) {
/* 191 */       finalFeatureList.add(Feature.fromGeometry((Geometry)singlePoint));
/*     */     }
/* 193 */     return FeatureCollection.fromFeatures(finalFeatureList);
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
/*     */   public static FeatureCollection explode(@NonNull Feature feature) {
/* 205 */     List<Feature> finalFeatureList = new ArrayList<>();
/* 206 */     for (Point singlePoint : TurfMeta.coordAll(feature, true)) {
/* 207 */       finalFeatureList.add(Feature.fromGeometry((Geometry)singlePoint));
/*     */     }
/* 209 */     return FeatureCollection.fromFeatures(finalFeatureList);
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
/*     */   public static Feature polygonToLine(@NonNull Feature feature) {
/* 221 */     return polygonToLine(feature, (JsonObject)null);
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
/*     */   public static Feature polygonToLine(@NonNull Feature feature, @Nullable JsonObject properties) {
/* 234 */     Geometry geometry = feature.geometry();
/* 235 */     if (geometry instanceof Polygon) {
/* 236 */       return polygonToLine((Polygon)geometry, (properties != null) ? properties : (
/* 237 */           feature.type().equals("Feature") ? feature.properties() : new JsonObject()));
/*     */     }
/* 239 */     throw new TurfException("Feature's geometry must be Polygon");
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
/*     */   public static Feature polygonToLine(@NonNull Polygon polygon) {
/* 251 */     return polygonToLine(polygon, (JsonObject)null);
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
/*     */   public static FeatureCollection polygonToLine(@NonNull MultiPolygon multiPolygon) {
/* 265 */     return polygonToLine(multiPolygon, (JsonObject)null);
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
/*     */   public static Feature polygonToLine(@NonNull Polygon polygon, @Nullable JsonObject properties) {
/* 278 */     return coordsToLine(polygon.coordinates(), properties);
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
/*     */   public static FeatureCollection polygonToLine(@NonNull MultiPolygon multiPolygon, @Nullable JsonObject properties) {
/* 294 */     List<List<List<Point>>> coordinates = multiPolygon.coordinates();
/* 295 */     List<Feature> finalFeatureList = new ArrayList<>();
/* 296 */     for (List<List<Point>> polygonCoordinates : coordinates) {
/* 297 */       finalFeatureList.add(coordsToLine(polygonCoordinates, properties));
/*     */     }
/* 299 */     return FeatureCollection.fromFeatures(finalFeatureList);
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
/*     */   public static FeatureCollection multiPolygonToLine(@NonNull Feature feature) {
/* 313 */     return multiPolygonToLine(feature, null);
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
/*     */   public static FeatureCollection multiPolygonToLine(@NonNull Feature feature, @Nullable JsonObject properties) {
/* 330 */     Geometry geometry = feature.geometry();
/* 331 */     if (geometry instanceof MultiPolygon) {
/* 332 */       return polygonToLine((MultiPolygon)geometry, (properties != null) ? properties : (
/* 333 */           feature.type().equals("Feature") ? feature.properties() : new JsonObject()));
/*     */     }
/* 335 */     throw new TurfException("Feature's geometry must be MultiPolygon");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static Feature coordsToLine(@NonNull List<List<Point>> coordinates, @Nullable JsonObject properties) {
/* 341 */     if (coordinates.size() > 1)
/* 342 */       return Feature.fromGeometry((Geometry)MultiLineString.fromLngLats(coordinates), properties); 
/* 343 */     if (coordinates.size() == 1) {
/* 344 */       LineString lineString = LineString.fromLngLats(coordinates.get(0));
/* 345 */       return Feature.fromGeometry((Geometry)lineString, properties);
/*     */     } 
/* 347 */     return null;
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
/*     */   public static FeatureCollection combine(@NonNull FeatureCollection originalFeatureCollection) {
/* 373 */     if (originalFeatureCollection.features() == null)
/* 374 */       throw new TurfException("Your FeatureCollection is null."); 
/* 375 */     if (originalFeatureCollection.features().size() == 0) {
/* 376 */       throw new TurfException("Your FeatureCollection doesn't have any Feature objects in it.");
/*     */     }
/* 378 */     List<Point> pointList = new ArrayList<>(0);
/* 379 */     List<LineString> lineStringList = new ArrayList<>(0);
/* 380 */     List<Polygon> polygonList = new ArrayList<>(0);
/* 381 */     for (Feature singleFeature : originalFeatureCollection.features()) {
/* 382 */       Geometry singleFeatureGeometry = singleFeature.geometry();
/* 383 */       if (singleFeatureGeometry instanceof Point || singleFeatureGeometry instanceof MultiPoint) {
/* 384 */         if (singleFeatureGeometry instanceof Point) {
/* 385 */           pointList.add((Point)singleFeatureGeometry); continue;
/*     */         } 
/* 387 */         pointList.addAll(((MultiPoint)singleFeatureGeometry).coordinates()); continue;
/*     */       } 
/* 389 */       if (singleFeatureGeometry instanceof LineString || singleFeatureGeometry instanceof MultiLineString) {
/*     */         
/* 391 */         if (singleFeatureGeometry instanceof LineString) {
/* 392 */           lineStringList.add((LineString)singleFeatureGeometry); continue;
/*     */         } 
/* 394 */         lineStringList.addAll(((MultiLineString)singleFeatureGeometry).lineStrings()); continue;
/*     */       } 
/* 396 */       if (singleFeatureGeometry instanceof Polygon || singleFeatureGeometry instanceof MultiPolygon) {
/*     */         
/* 398 */         if (singleFeatureGeometry instanceof Polygon) {
/* 399 */           polygonList.add((Polygon)singleFeatureGeometry); continue;
/*     */         } 
/* 401 */         polygonList.addAll(((MultiPolygon)singleFeatureGeometry).polygons());
/*     */       } 
/*     */     } 
/*     */     
/* 405 */     List<Feature> finalFeatureList = new ArrayList<>(0);
/* 406 */     if (!pointList.isEmpty()) {
/* 407 */       finalFeatureList.add(Feature.fromGeometry((Geometry)MultiPoint.fromLngLats(pointList)));
/*     */     }
/* 409 */     if (!lineStringList.isEmpty()) {
/* 410 */       finalFeatureList.add(Feature.fromGeometry((Geometry)MultiLineString.fromLineStrings(lineStringList)));
/*     */     }
/* 412 */     if (!polygonList.isEmpty()) {
/* 413 */       finalFeatureList.add(Feature.fromGeometry((Geometry)MultiPolygon.fromPolygons(polygonList)));
/*     */     }
/* 415 */     return finalFeatureList.isEmpty() ? originalFeatureCollection : 
/* 416 */       FeatureCollection.fromFeatures(finalFeatureList);
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-turf-5.9.0.jar!\com\mapbox\turf\TurfConversion.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */