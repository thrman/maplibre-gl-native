/*     */ package com.mapbox.turf;
/*     */ 
/*     */ import androidx.annotation.FloatRange;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.mapbox.geojson.Feature;
/*     */ import com.mapbox.geojson.Geometry;
/*     */ import com.mapbox.geojson.LineString;
/*     */ import com.mapbox.geojson.Point;
/*     */ import com.mapbox.turf.models.LineIntersectsResult;
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
/*     */ public final class TurfMisc
/*     */ {
/*     */   private static final String INDEX_KEY = "index";
/*     */   
/*     */   private TurfMisc() {
/*  26 */     throw new AssertionError("No Instances.");
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
/*     */   public static LineString lineSlice(@NonNull Point startPt, @NonNull Point stopPt, @NonNull Feature line) {
/*  44 */     if (line.geometry() == null) {
/*  45 */       throw new NullPointerException("Feature.geometry() == null");
/*     */     }
/*  47 */     if (!line.geometry().type().equals("LineString")) {
/*  48 */       throw new TurfException("input must be a LineString Feature or Geometry");
/*     */     }
/*  50 */     return lineSlice(startPt, stopPt, (LineString)line.geometry());
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
/*     */   public static LineString lineSlice(@NonNull Point startPt, @NonNull Point stopPt, @NonNull LineString line) {
/*  68 */     List<Point> coords = line.coordinates();
/*     */     
/*  70 */     if (coords.size() < 2) {
/*  71 */       throw new TurfException("Turf lineSlice requires a LineString made up of at least 2 coordinates.");
/*     */     }
/*  73 */     if (startPt.equals(stopPt)) {
/*  74 */       throw new TurfException("Start and stop points in Turf lineSlice cannot equal each other.");
/*     */     }
/*     */     
/*  77 */     Feature startVertex = nearestPointOnLine(startPt, coords);
/*  78 */     Feature stopVertex = nearestPointOnLine(stopPt, coords);
/*  79 */     List<Feature> ends = new ArrayList<>();
/*  80 */     if (((Integer)startVertex.getNumberProperty("index")).intValue() <= ((Integer)stopVertex
/*  81 */       .getNumberProperty("index")).intValue()) {
/*  82 */       ends.add(startVertex);
/*  83 */       ends.add(stopVertex);
/*     */     } else {
/*  85 */       ends.add(stopVertex);
/*  86 */       ends.add(startVertex);
/*     */     } 
/*  88 */     List<Point> points = new ArrayList<>();
/*  89 */     points.add((Point)((Feature)ends.get(0)).geometry());
/*  90 */     int i = ((Integer)((Feature)ends.get(0)).getNumberProperty("index")).intValue() + 1;
/*  91 */     for (; i < ((Integer)((Feature)ends.get(1)).getNumberProperty("index")).intValue() + 1; i++) {
/*  92 */       points.add(coords.get(i));
/*     */     }
/*  94 */     points.add((Point)((Feature)ends.get(1)).geometry());
/*  95 */     return LineString.fromLngLats(points);
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
/*     */   @NonNull
/*     */   public static LineString lineSliceAlong(@NonNull Feature line, @FloatRange(from = 0.0D) double startDist, @FloatRange(from = 0.0D) double stopDist, @NonNull String units) {
/* 120 */     if (line.geometry() == null) {
/* 121 */       throw new NullPointerException("Feature.geometry() == null");
/*     */     }
/* 123 */     if (!line.geometry().type().equals("LineString")) {
/* 124 */       throw new TurfException("input must be a LineString Feature or Geometry");
/*     */     }
/*     */     
/* 127 */     return lineSliceAlong((LineString)line.geometry(), startDist, stopDist, units);
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
/*     */   @NonNull
/*     */   public static LineString lineSliceAlong(@NonNull LineString line, @FloatRange(from = 0.0D) double startDist, @FloatRange(from = 0.0D) double stopDist, @NonNull String units) {
/* 153 */     List<Point> coords = line.coordinates();
/*     */     
/* 155 */     if (coords.size() < 2)
/* 156 */       throw new TurfException("Turf lineSlice requires a LineString Geometry made up of at least 2 coordinates. The LineString passed in only contains " + coords
/* 157 */           .size() + "."); 
/* 158 */     if (startDist == stopDist) {
/* 159 */       throw new TurfException("Start and stop distance in Turf lineSliceAlong cannot equal each other.");
/*     */     }
/*     */ 
/*     */     
/* 163 */     List<Point> slice = new ArrayList<>(2);
/*     */     
/* 165 */     double travelled = 0.0D;
/* 166 */     for (int i = 0; i < coords.size(); i++) {
/*     */       
/* 168 */       if (startDist >= travelled && i == coords.size() - 1) {
/*     */         break;
/*     */       }
/* 171 */       if (travelled > startDist && slice.size() == 0) {
/* 172 */         double overshot = startDist - travelled;
/* 173 */         if (overshot == 0.0D) {
/* 174 */           slice.add(coords.get(i));
/* 175 */           return LineString.fromLngLats(slice);
/*     */         } 
/* 177 */         double direction = TurfMeasurement.bearing(coords.get(i), coords.get(i - 1)) - 180.0D;
/* 178 */         Point interpolated = TurfMeasurement.destination(coords.get(i), overshot, direction, units);
/* 179 */         slice.add(interpolated);
/*     */       } 
/*     */       
/* 182 */       if (travelled >= stopDist) {
/* 183 */         double overshot = stopDist - travelled;
/* 184 */         if (overshot == 0.0D) {
/* 185 */           slice.add(coords.get(i));
/* 186 */           return LineString.fromLngLats(slice);
/*     */         } 
/* 188 */         double direction = TurfMeasurement.bearing(coords.get(i), coords.get(i - 1)) - 180.0D;
/* 189 */         Point interpolated = TurfMeasurement.destination(coords.get(i), overshot, direction, units);
/* 190 */         slice.add(interpolated);
/* 191 */         return LineString.fromLngLats(slice);
/*     */       } 
/*     */       
/* 194 */       if (travelled >= startDist) {
/* 195 */         slice.add(coords.get(i));
/*     */       }
/*     */       
/* 198 */       if (i == coords.size() - 1) {
/* 199 */         return LineString.fromLngLats(slice);
/*     */       }
/*     */       
/* 202 */       travelled += TurfMeasurement.distance(coords.get(i), coords.get(i + 1), units);
/*     */     } 
/*     */     
/* 205 */     if (travelled < startDist) {
/* 206 */       throw new TurfException("Start position is beyond line");
/*     */     }
/*     */     
/* 209 */     return LineString.fromLngLats(slice);
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
/*     */   public static Feature nearestPointOnLine(@NonNull Point pt, @NonNull List<Point> coords) {
/* 223 */     return nearestPointOnLine(pt, coords, null);
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
/*     */   public static Feature nearestPointOnLine(@NonNull Point pt, @NonNull List<Point> coords, @Nullable String units) {
/* 241 */     if (coords.size() < 2) {
/* 242 */       throw new TurfException("Turf nearestPointOnLine requires a List of Points made up of at least 2 coordinates.");
/*     */     }
/*     */ 
/*     */     
/* 246 */     if (units == null) {
/* 247 */       units = "kilometers";
/*     */     }
/*     */     
/* 250 */     Feature closestPt = Feature.fromGeometry(
/* 251 */         (Geometry)Point.fromLngLat(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
/* 252 */     closestPt.addNumberProperty("dist", Double.valueOf(Double.POSITIVE_INFINITY));
/*     */     
/* 254 */     for (int i = 0; i < coords.size() - 1; i++) {
/* 255 */       Feature start = Feature.fromGeometry((Geometry)coords.get(i));
/* 256 */       Feature stop = Feature.fromGeometry((Geometry)coords.get(i + 1));
/*     */       
/* 258 */       start.addNumberProperty("dist", Double.valueOf(TurfMeasurement.distance(pt, (Point)start
/* 259 */               .geometry(), units)));
/*     */       
/* 261 */       stop.addNumberProperty("dist", Double.valueOf(TurfMeasurement.distance(pt, (Point)stop
/* 262 */               .geometry(), units)));
/*     */       
/* 264 */       double heightDistance = Math.max(start
/* 265 */           .properties().get("dist").getAsDouble(), stop
/* 266 */           .properties().get("dist").getAsDouble());
/*     */       
/* 268 */       double direction = TurfMeasurement.bearing((Point)start.geometry(), (Point)stop
/* 269 */           .geometry());
/* 270 */       Feature perpendicularPt1 = Feature.fromGeometry(
/* 271 */           (Geometry)TurfMeasurement.destination(pt, heightDistance, direction + 90.0D, units));
/* 272 */       Feature perpendicularPt2 = Feature.fromGeometry(
/* 273 */           (Geometry)TurfMeasurement.destination(pt, heightDistance, direction - 90.0D, units));
/* 274 */       LineIntersectsResult intersect = lineIntersects(((Point)perpendicularPt1
/* 275 */           .geometry()).longitude(), ((Point)perpendicularPt1
/* 276 */           .geometry()).latitude(), ((Point)perpendicularPt2
/* 277 */           .geometry()).longitude(), ((Point)perpendicularPt2
/* 278 */           .geometry()).latitude(), ((Point)start
/* 279 */           .geometry()).longitude(), ((Point)start
/* 280 */           .geometry()).latitude(), ((Point)stop
/* 281 */           .geometry()).longitude(), ((Point)stop
/* 282 */           .geometry()).latitude());
/*     */ 
/*     */       
/* 285 */       Feature intersectPt = null;
/* 286 */       if (intersect != null) {
/* 287 */         intersectPt = Feature.fromGeometry(
/* 288 */             (Geometry)Point.fromLngLat(intersect.horizontalIntersection().doubleValue(), intersect.verticalIntersection().doubleValue()));
/* 289 */         intersectPt.addNumberProperty("dist", Double.valueOf(TurfMeasurement.distance(pt, (Point)intersectPt
/* 290 */                 .geometry(), units)));
/*     */       } 
/*     */       
/* 293 */       if (((Double)start.getNumberProperty("dist")).doubleValue() < ((Double)closestPt
/* 294 */         .getNumberProperty("dist")).doubleValue()) {
/* 295 */         closestPt = start;
/* 296 */         closestPt.addNumberProperty("index", Integer.valueOf(i));
/*     */       } 
/* 298 */       if (((Double)stop.getNumberProperty("dist")).doubleValue() < ((Double)closestPt
/* 299 */         .getNumberProperty("dist")).doubleValue()) {
/* 300 */         closestPt = stop;
/* 301 */         closestPt.addNumberProperty("index", Integer.valueOf(i));
/*     */       } 
/* 303 */       if (intersectPt != null && ((Double)intersectPt
/* 304 */         .getNumberProperty("dist")).doubleValue() < ((Double)closestPt
/* 305 */         .getNumberProperty("dist")).doubleValue()) {
/* 306 */         closestPt = intersectPt;
/* 307 */         closestPt.addNumberProperty("index", Integer.valueOf(i));
/*     */       } 
/*     */     } 
/*     */     
/* 311 */     return closestPt;
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
/*     */   private static LineIntersectsResult lineIntersects(double line1StartX, double line1StartY, double line1EndX, double line1EndY, double line2StartX, double line2StartY, double line2EndX, double line2EndY) {
/* 324 */     LineIntersectsResult result = LineIntersectsResult.builder().onLine1(false).onLine2(false).build();
/*     */     
/* 326 */     double denominator = (line2EndY - line2StartY) * (line1EndX - line1StartX) - (line2EndX - line2StartX) * (line1EndY - line1StartY);
/*     */     
/* 328 */     if (denominator == 0.0D) {
/* 329 */       if (result.horizontalIntersection() != null && result.verticalIntersection() != null) {
/* 330 */         return result;
/*     */       }
/* 332 */       return null;
/*     */     } 
/*     */     
/* 335 */     double varA = line1StartY - line2StartY;
/* 336 */     double varB = line1StartX - line2StartX;
/* 337 */     double numerator1 = (line2EndX - line2StartX) * varA - (line2EndY - line2StartY) * varB;
/* 338 */     double numerator2 = (line1EndX - line1StartX) * varA - (line1EndY - line1StartY) * varB;
/* 339 */     varA = numerator1 / denominator;
/* 340 */     varB = numerator2 / denominator;
/*     */ 
/*     */ 
/*     */     
/* 344 */     result = result.toBuilder().horizontalIntersection(Double.valueOf(line1StartX + varA * (line1EndX - line1StartX))).build();
/*     */     
/* 346 */     result = result.toBuilder().verticalIntersection(Double.valueOf(line1StartY + varA * (line1EndY - line1StartY))).build();
/*     */ 
/*     */     
/* 349 */     if (varA > 0.0D && varA < 1.0D) {
/* 350 */       result = result.toBuilder().onLine1(true).build();
/*     */     }
/*     */     
/* 353 */     if (varB > 0.0D && varB < 1.0D) {
/* 354 */       result = result.toBuilder().onLine2(true).build();
/*     */     }
/*     */     
/* 357 */     if (result.onLine1() && result.onLine2()) {
/* 358 */       return result;
/*     */     }
/* 360 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-turf-5.9.0.jar!\com\mapbox\turf\TurfMisc.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */