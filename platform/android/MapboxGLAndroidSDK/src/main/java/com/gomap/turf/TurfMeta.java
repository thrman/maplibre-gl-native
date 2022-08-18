/*     */ package com.gomap.turf;
/*     */ 
/*     */ import androidx.annotation.NonNull;
/*     */ import com.gomap.geojson.Feature;
import com.gomap.geojson.FeatureCollection;
import com.gomap.geojson.Geometry;
import com.gomap.geojson.Feature;
/*     */ import com.gomap.geojson.FeatureCollection;
/*     */ import com.gomap.geojson.Geometry;
/*     */ import com.gomap.geojson.GeometryCollection;
/*     */ import com.gomap.geojson.LineString;
/*     */ import com.gomap.geojson.MultiLineString;
/*     */ import com.gomap.geojson.MultiPoint;
/*     */ import com.gomap.geojson.MultiPolygon;
/*     */ import com.gomap.geojson.Point;
/*     */ import com.gomap.geojson.Polygon;
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
/*     */ public final class TurfMeta
/*     */ {
/*     */   @NonNull
/*     */   public static List<Point> coordAll(@NonNull Point point) {
/*  43 */     return coordAll(new ArrayList<Point>(), point);
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
/*     */   private static List<Point> coordAll(@NonNull List<Point> coords, @NonNull Point point) {
/*  56 */     coords.add(point);
/*  57 */     return coords;
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
/*     */   public static List<Point> coordAll(@NonNull MultiPoint multiPoint) {
/*  71 */     return coordAll(new ArrayList<Point>(), multiPoint);
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
/*     */   private static List<Point> coordAll(@NonNull List<Point> coords, @NonNull MultiPoint multiPoint) {
/*  84 */     coords.addAll(multiPoint.coordinates());
/*  85 */     return coords;
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
/*     */   public static List<Point> coordAll(@NonNull LineString lineString) {
/*  99 */     return coordAll(new ArrayList<Point>(), lineString);
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
/*     */   private static List<Point> coordAll(@NonNull List<Point> coords, @NonNull LineString lineString) {
/* 112 */     coords.addAll(lineString.coordinates());
/* 113 */     return coords;
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
/*     */   @NonNull
/*     */   public static List<Point> coordAll(@NonNull Polygon polygon, @NonNull boolean excludeWrapCoord) {
/* 129 */     return coordAll(new ArrayList<Point>(), polygon, excludeWrapCoord);
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
/*     */   @NonNull
/*     */   private static List<Point> coordAll(@NonNull List<Point> coords, @NonNull Polygon polygon, @NonNull boolean excludeWrapCoord) {
/* 147 */     int wrapShrink = excludeWrapCoord ? 1 : 0;
/* 148 */     for (int i = 0; i < polygon.coordinates().size(); i++) {
/* 149 */       for (int j = 0; j < ((List)polygon.coordinates().get(i)).size() - wrapShrink; j++) {
/* 150 */         coords.add(((List<Point>)polygon.coordinates().get(i)).get(j));
/*     */       }
/*     */     } 
/* 153 */     return coords;
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
/*     */   @NonNull
/*     */   public static List<Point> coordAll(@NonNull MultiLineString multiLineString) {
/* 168 */     return coordAll(new ArrayList<Point>(), multiLineString);
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
/*     */   private static List<Point> coordAll(@NonNull List<Point> coords, @NonNull MultiLineString multiLineString) {
/* 182 */     for (int i = 0; i < multiLineString.coordinates().size(); i++) {
/* 183 */       coords.addAll(multiLineString.coordinates().get(i));
/*     */     }
/* 185 */     return coords;
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
/*     */   @NonNull
/*     */   public static List<Point> coordAll(@NonNull MultiPolygon multiPolygon, @NonNull boolean excludeWrapCoord) {
/* 203 */     return coordAll(new ArrayList<Point>(), multiPolygon, excludeWrapCoord);
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
/*     */   @NonNull
/*     */   private static List<Point> coordAll(@NonNull List<Point> coords, @NonNull MultiPolygon multiPolygon, @NonNull boolean excludeWrapCoord) {
/* 221 */     int wrapShrink = excludeWrapCoord ? 1 : 0;
/* 222 */     for (int i = 0; i < multiPolygon.coordinates().size(); i++) {
/* 223 */       for (int j = 0; j < ((List)multiPolygon.coordinates().get(i)).size(); j++) {
/* 224 */         for (int k = 0; k < ((List)((List)multiPolygon.coordinates().get(i)).get(j)).size() - wrapShrink; k++) {
/* 225 */           coords.add(((List<Point>)((List<List<Point>>)multiPolygon.coordinates().get(i)).get(j)).get(k));
/*     */         }
/*     */       } 
/*     */     } 
/* 229 */     return coords;
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
/*     */   @NonNull
/*     */   public static List<Point> coordAll(@NonNull Feature feature, @NonNull boolean excludeWrapCoord) {
/* 247 */     return addCoordAll(new ArrayList<Point>(), feature, excludeWrapCoord);
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
/*     */   @NonNull
/*     */   public static List<Point> coordAll(@NonNull FeatureCollection featureCollection, @NonNull boolean excludeWrapCoord) {
/* 266 */     List<Point> finalCoordsList = new ArrayList<>();
/* 267 */     for (Feature singleFeature : featureCollection.features()) {
/* 268 */       addCoordAll(finalCoordsList, singleFeature, excludeWrapCoord);
/*     */     }
/* 270 */     return finalCoordsList;
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
/*     */   @NonNull
/*     */   private static List<Point> addCoordAll(@NonNull List<Point> pointList, @NonNull Feature feature, @NonNull boolean excludeWrapCoord) {
/* 291 */     return coordAllFromSingleGeometry(pointList, feature.geometry(), excludeWrapCoord);
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
/*     */   @NonNull
/*     */   private static List<Point> coordAllFromSingleGeometry(@NonNull List<Point> pointList, @NonNull Geometry geometry, @NonNull boolean excludeWrapCoord) {
/* 311 */     if (geometry instanceof Point) {
/* 312 */       pointList.add((Point)geometry);
/* 313 */     } else if (geometry instanceof MultiPoint) {
/* 314 */       pointList.addAll(((MultiPoint)geometry).coordinates());
/* 315 */     } else if (geometry instanceof LineString) {
/* 316 */       pointList.addAll(((LineString)geometry).coordinates());
/* 317 */     } else if (geometry instanceof MultiLineString) {
/* 318 */       coordAll(pointList, (MultiLineString)geometry);
/* 319 */     } else if (geometry instanceof Polygon) {
/* 320 */       coordAll(pointList, (Polygon)geometry, excludeWrapCoord);
/* 321 */     } else if (geometry instanceof MultiPolygon) {
/* 322 */       coordAll(pointList, (MultiPolygon)geometry, excludeWrapCoord);
/* 323 */     } else if (geometry instanceof GeometryCollection) {
/*     */       
/* 325 */       for (Geometry singleGeometry : ((GeometryCollection)geometry).geometries()) {
/* 326 */         coordAllFromSingleGeometry(pointList, singleGeometry, excludeWrapCoord);
/*     */       }
/*     */     } 
/* 329 */     return pointList;
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
/*     */   public static Point getCoord(Feature obj) {
/* 341 */     if (obj.geometry() instanceof Point) {
/* 342 */       return (Point)obj.geometry();
/*     */     }
/* 344 */     throw new TurfException("A Feature with a Point geometry is required.");
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-turf-5.9.0.jar!\com\mapbox\turf\TurfMeta.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */