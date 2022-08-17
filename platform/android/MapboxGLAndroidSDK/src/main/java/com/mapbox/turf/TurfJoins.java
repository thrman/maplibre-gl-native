/*     */ package com.mapbox.turf;
/*     */ 
/*     */ import com.mapbox.geojson.Feature;
/*     */ import com.mapbox.geojson.FeatureCollection;
/*     */ import com.mapbox.geojson.Geometry;
/*     */ import com.mapbox.geojson.MultiPolygon;
/*     */ import com.mapbox.geojson.Point;
/*     */ import com.mapbox.geojson.Polygon;
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
/*     */ public final class TurfJoins
/*     */ {
/*     */   public static boolean inside(Point point, Polygon polygon) {
/*  36 */     List<List<Point>> coordinates = polygon.coordinates();
/*  37 */     List<List<List<Point>>> multiCoordinates = new ArrayList<>();
/*  38 */     multiCoordinates.add(coordinates);
/*  39 */     return inside(point, MultiPolygon.fromLngLats(multiCoordinates));
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
/*     */   public static boolean inside(Point point, MultiPolygon multiPolygon) {
/*  54 */     List<List<List<Point>>> polys = multiPolygon.coordinates();
/*     */     
/*  56 */     boolean insidePoly = false;
/*  57 */     for (int i = 0; i < polys.size() && !insidePoly; i++) {
/*     */       
/*  59 */       if (inRing(point, ((List<List<Point>>)polys.get(i)).get(0))) {
/*  60 */         boolean inHole = false;
/*  61 */         int temp = 1;
/*     */         
/*  63 */         while (temp < ((List)polys.get(i)).size() && !inHole) {
/*  64 */           if (inRing(point, ((List<List<Point>>)polys.get(i)).get(temp))) {
/*  65 */             inHole = true;
/*     */           }
/*  67 */           temp++;
/*     */         } 
/*  69 */         if (!inHole) {
/*  70 */           insidePoly = true;
/*     */         }
/*     */       } 
/*     */     } 
/*  74 */     return insidePoly;
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
/*     */   public static FeatureCollection pointsWithinPolygon(FeatureCollection points, FeatureCollection polygons) {
/*  88 */     ArrayList<Feature> features = new ArrayList<>();
/*  89 */     for (int i = 0; i < polygons.features().size(); i++) {
/*  90 */       for (int j = 0; j < points.features().size(); j++) {
/*  91 */         Point point = (Point)((Feature)points.features().get(j)).geometry();
/*     */         
/*  93 */         boolean isInside = inside(point, (Polygon)((Feature)polygons.features().get(i)).geometry());
/*  94 */         if (isInside) {
/*  95 */           features.add(Feature.fromGeometry((Geometry)point));
/*     */         }
/*     */       } 
/*     */     } 
/*  99 */     return FeatureCollection.fromFeatures(features);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean inRing(Point pt, List<Point> ring) {
/* 104 */     boolean isInside = false;
/*     */     int j = 0;
/* 106 */     for (int i = 0; i < ring.size(); j = i++) {
/* 107 */       double xi = ((Point)ring.get(i)).longitude();
/* 108 */       double yi = ((Point)ring.get(i)).latitude();
/* 109 */       double xj = ((Point)ring.get(j)).longitude();
/* 110 */       double yj = ((Point)ring.get(j)).latitude();
/*     */ 
/*     */       
/* 113 */       boolean intersect = (((yi > pt.latitude()) ? true : false) != ((yj > pt.latitude()) ? true : false) && pt.longitude() < (xj - xi) * (pt.latitude() - yi) / (yj - yi) + xi);
/* 114 */       if (intersect) {
/* 115 */         isInside = !isInside;
/*     */       }
/*     */     } 
/* 118 */     return isInside;
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-turf-5.9.0.jar!\com\mapbox\turf\TurfJoins.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */