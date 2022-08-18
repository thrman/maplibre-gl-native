/*     */ package com.gomap.turf;
/*     */ 
/*     */ import androidx.annotation.FloatRange;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.gomap.geojson.BoundingBox;
import com.gomap.geojson.Feature;
import com.gomap.geojson.FeatureCollection;
import com.gomap.geojson.GeoJson;
import com.gomap.geojson.Geometry;
import com.google.gson.JsonObject;
/*     */ import com.gomap.geojson.BoundingBox;
/*     */ import com.gomap.geojson.Feature;
/*     */ import com.gomap.geojson.FeatureCollection;
/*     */ import com.gomap.geojson.GeoJson;
/*     */ import com.gomap.geojson.Geometry;
/*     */ import com.gomap.geojson.GeometryCollection;
/*     */ import com.gomap.geojson.LineString;
/*     */ import com.gomap.geojson.MultiLineString;
/*     */ import com.gomap.geojson.MultiPoint;
/*     */ import com.gomap.geojson.MultiPolygon;
/*     */ import com.gomap.geojson.Point;
/*     */ import com.gomap.geojson.Polygon;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
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
/*     */ public final class TurfMeasurement
/*     */ {
/*     */   private TurfMeasurement() {
/*  39 */     throw new AssertionError("No Instances.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   public static double EARTH_RADIUS = 6378137.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double bearing(@NonNull Point point1, @NonNull Point point2) {
/*  58 */     double lon1 = TurfConversion.degreesToRadians(point1.longitude());
/*  59 */     double lon2 = TurfConversion.degreesToRadians(point2.longitude());
/*  60 */     double lat1 = TurfConversion.degreesToRadians(point1.latitude());
/*  61 */     double lat2 = TurfConversion.degreesToRadians(point2.latitude());
/*  62 */     double value1 = Math.sin(lon2 - lon1) * Math.cos(lat2);
/*     */     
/*  64 */     double value2 = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1);
/*     */     
/*  66 */     return TurfConversion.radiansToDegrees(Math.atan2(value1, value2));
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
/*     */   public static Point destination(@NonNull Point point, @FloatRange(from = 0.0D) double distance, @FloatRange(from = -180.0D, to = 180.0D) double bearing, @NonNull String units) {
/*  87 */     double longitude1 = TurfConversion.degreesToRadians(point.longitude());
/*  88 */     double latitude1 = TurfConversion.degreesToRadians(point.latitude());
/*  89 */     double bearingRad = TurfConversion.degreesToRadians(bearing);
/*     */     
/*  91 */     double radians = TurfConversion.lengthToRadians(distance, units);
/*     */     
/*  93 */     double latitude2 = Math.asin(Math.sin(latitude1) * Math.cos(radians) + 
/*  94 */         Math.cos(latitude1) * Math.sin(radians) * Math.cos(bearingRad));
/*  95 */     double longitude2 = longitude1 + Math.atan2(Math.sin(bearingRad) * 
/*  96 */         Math.sin(radians) * Math.cos(latitude1), 
/*  97 */         Math.cos(radians) - Math.sin(latitude1) * Math.sin(latitude2));
/*     */     
/*  99 */     return Point.fromLngLat(
/* 100 */         TurfConversion.radiansToDegrees(longitude2), TurfConversion.radiansToDegrees(latitude2));
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
/*     */   public static double distance(@NonNull Point point1, @NonNull Point point2) {
/* 114 */     return distance(point1, point2, "kilometers");
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
/*     */   public static double distance(@NonNull Point point1, @NonNull Point point2, @NonNull String units) {
/* 130 */     double difLat = TurfConversion.degreesToRadians(point2.latitude() - point1.latitude());
/* 131 */     double difLon = TurfConversion.degreesToRadians(point2.longitude() - point1.longitude());
/* 132 */     double lat1 = TurfConversion.degreesToRadians(point1.latitude());
/* 133 */     double lat2 = TurfConversion.degreesToRadians(point2.latitude());
/*     */ 
/*     */     
/* 136 */     double value = Math.pow(Math.sin(difLat / 2.0D), 2.0D) + Math.pow(Math.sin(difLon / 2.0D), 2.0D) * Math.cos(lat1) * Math.cos(lat2);
/*     */     
/* 138 */     return TurfConversion.radiansToLength(2.0D * 
/* 139 */         Math.atan2(Math.sqrt(value), Math.sqrt(1.0D - value)), units);
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
/*     */   public static double length(@NonNull LineString lineString, @NonNull String units) {
/* 153 */     List<Point> coordinates = lineString.coordinates();
/* 154 */     return length(coordinates, units);
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
/*     */   public static double length(@NonNull MultiLineString multiLineString, @NonNull String units) {
/* 168 */     double len = 0.0D;
/* 169 */     for (List<Point> points : (Iterable<List<Point>>)multiLineString.coordinates()) {
/* 170 */       len += length(points, units);
/*     */     }
/* 172 */     return len;
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
/*     */   public static double length(@NonNull Polygon polygon, @NonNull String units) {
/* 187 */     double len = 0.0D;
/* 188 */     for (List<Point> points : (Iterable<List<Point>>)polygon.coordinates()) {
/* 189 */       len += length(points, units);
/*     */     }
/* 191 */     return len;
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
/*     */   public static double length(@NonNull MultiPolygon multiPolygon, @NonNull String units) {
/* 206 */     double len = 0.0D;
/* 207 */     List<List<List<Point>>> coordinates = multiPolygon.coordinates();
/* 208 */     for (List<List<Point>> coordinate : coordinates) {
/* 209 */       for (List<Point> theCoordinate : coordinate) {
/* 210 */         len += length(theCoordinate, units);
/*     */       }
/*     */     } 
/* 213 */     return len;
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
/*     */   public static double length(List<Point> coords, String units) {
/* 226 */     double travelled = 0.0D;
/* 227 */     Point prevCoords = coords.get(0);
/*     */     
/* 229 */     for (int i = 1; i < coords.size(); i++) {
/* 230 */       Point curCoords = coords.get(i);
/* 231 */       travelled += distance(prevCoords, curCoords, units);
/* 232 */       prevCoords = curCoords;
/*     */     } 
/* 234 */     return travelled;
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
/*     */   public static Point midpoint(@NonNull Point from, @NonNull Point to) {
/* 248 */     double dist = distance(from, to, "miles");
/* 249 */     double heading = bearing(from, to);
/* 250 */     return destination(from, dist / 2.0D, heading, "miles");
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
/*     */   public static Point along(@NonNull LineString line, @FloatRange(from = 0.0D) double distance, @NonNull String units) {
/* 265 */     return along(line.coordinates(), distance, units);
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
/*     */   public static Point along(@NonNull List<Point> coords, @FloatRange(from = 0.0D) double distance, @NonNull String units) {
/* 281 */     double travelled = 0.0D;
/* 282 */     for (int i = 0; i < coords.size() && (
/* 283 */       distance < travelled || i != coords.size() - 1); i++) {
/*     */       
/* 285 */       if (travelled >= distance) {
/* 286 */         double overshot = distance - travelled;
/* 287 */         if (overshot == 0.0D) {
/* 288 */           return coords.get(i);
/*     */         }
/* 290 */         double direction = bearing(coords.get(i), coords.get(i - 1)) - 180.0D;
/* 291 */         return destination(coords.get(i), overshot, direction, units);
/*     */       } 
/*     */       
/* 294 */       travelled += distance(coords.get(i), coords.get(i + 1), units);
/*     */     } 
/*     */ 
/*     */     
/* 298 */     return coords.get(coords.size() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] bbox(@NonNull Point point) {
/* 309 */     List<Point> resultCoords = TurfMeta.coordAll(point);
/* 310 */     return bboxCalculator(resultCoords);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] bbox(@NonNull LineString lineString) {
/* 321 */     List<Point> resultCoords = TurfMeta.coordAll(lineString);
/* 322 */     return bboxCalculator(resultCoords);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] bbox(@NonNull MultiPoint multiPoint) {
/* 333 */     List<Point> resultCoords = TurfMeta.coordAll(multiPoint);
/* 334 */     return bboxCalculator(resultCoords);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] bbox(@NonNull Polygon polygon) {
/* 345 */     List<Point> resultCoords = TurfMeta.coordAll(polygon, false);
/* 346 */     return bboxCalculator(resultCoords);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] bbox(@NonNull MultiLineString multiLineString) {
/* 357 */     List<Point> resultCoords = TurfMeta.coordAll(multiLineString);
/* 358 */     return bboxCalculator(resultCoords);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] bbox(MultiPolygon multiPolygon) {
/* 369 */     List<Point> resultCoords = TurfMeta.coordAll(multiPolygon, false);
/* 370 */     return bboxCalculator(resultCoords);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] bbox(GeoJson geoJson) {
/* 381 */     BoundingBox boundingBox = geoJson.bbox();
/* 382 */     if (boundingBox != null) {
/* 383 */       return new double[] { boundingBox
/* 384 */           .west(), boundingBox
/* 385 */           .south(), boundingBox
/* 386 */           .east(), boundingBox
/* 387 */           .north() };
/*     */     }
/*     */ 
/*     */     
/* 391 */     if (geoJson instanceof Geometry)
/* 392 */       return bbox((Geometry)geoJson); 
/* 393 */     if (geoJson instanceof FeatureCollection)
/* 394 */       return bbox((FeatureCollection)geoJson); 
/* 395 */     if (geoJson instanceof Feature) {
/* 396 */       return bbox((Feature)geoJson);
/*     */     }
/* 398 */     throw new UnsupportedOperationException("bbox type not supported for GeoJson instance");
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
/*     */   public static double[] bbox(FeatureCollection featureCollection) {
/* 410 */     return bboxCalculator(TurfMeta.coordAll(featureCollection, false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] bbox(Feature feature) {
/* 421 */     return bboxCalculator(TurfMeta.coordAll(feature, false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] bbox(Geometry geometry) {
/* 432 */     if (geometry instanceof Point)
/* 433 */       return bbox((Point)geometry); 
/* 434 */     if (geometry instanceof MultiPoint)
/* 435 */       return bbox((MultiPoint)geometry); 
/* 436 */     if (geometry instanceof LineString)
/* 437 */       return bbox((LineString)geometry); 
/* 438 */     if (geometry instanceof MultiLineString)
/* 439 */       return bbox((MultiLineString)geometry); 
/* 440 */     if (geometry instanceof Polygon)
/* 441 */       return bbox((Polygon)geometry); 
/* 442 */     if (geometry instanceof MultiPolygon)
/* 443 */       return bbox((MultiPolygon)geometry); 
/* 444 */     if (geometry instanceof GeometryCollection) {
/* 445 */       List<Point> points = new ArrayList<>();
/* 446 */       for (Geometry geo : ((GeometryCollection)geometry).geometries()) {
/*     */         
/* 448 */         double[] bbox = bbox(geo);
/* 449 */         points.add(Point.fromLngLat(bbox[0], bbox[1]));
/* 450 */         points.add(Point.fromLngLat(bbox[2], bbox[1]));
/* 451 */         points.add(Point.fromLngLat(bbox[2], bbox[3]));
/* 452 */         points.add(Point.fromLngLat(bbox[0], bbox[3]));
/*     */       } 
/* 454 */       return bbox(MultiPoint.fromLngLats(points));
/*     */     } 
/* 456 */     throw new RuntimeException("Unknown geometry class: " + geometry.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   private static double[] bboxCalculator(List<Point> resultCoords) {
/* 461 */     double[] bbox = new double[4];
/*     */     
/* 463 */     bbox[0] = Double.POSITIVE_INFINITY;
/* 464 */     bbox[1] = Double.POSITIVE_INFINITY;
/* 465 */     bbox[2] = Double.NEGATIVE_INFINITY;
/* 466 */     bbox[3] = Double.NEGATIVE_INFINITY;
/*     */     
/* 468 */     for (Point point : resultCoords) {
/* 469 */       if (bbox[0] > point.longitude()) {
/* 470 */         bbox[0] = point.longitude();
/*     */       }
/* 472 */       if (bbox[1] > point.latitude()) {
/* 473 */         bbox[1] = point.latitude();
/*     */       }
/* 475 */       if (bbox[2] < point.longitude()) {
/* 476 */         bbox[2] = point.longitude();
/*     */       }
/* 478 */       if (bbox[3] < point.latitude()) {
/* 479 */         bbox[3] = point.latitude();
/*     */       }
/*     */     } 
/* 482 */     return bbox;
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
/*     */   public static Feature bboxPolygon(@NonNull BoundingBox boundingBox) {
/* 495 */     return bboxPolygon(boundingBox, (JsonObject)null, (String)null);
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
/*     */   public static Feature bboxPolygon(@NonNull BoundingBox boundingBox, @Nullable JsonObject properties, @Nullable String id) {
/* 512 */     return Feature.fromGeometry((Geometry)Polygon.fromLngLats(
/* 513 */           Collections.singletonList(
/* 514 */             Arrays.asList(new Point[] {
/* 515 */                 Point.fromLngLat(boundingBox.west(), boundingBox.south()), 
/* 516 */                 Point.fromLngLat(boundingBox.east(), boundingBox.south()), 
/* 517 */                 Point.fromLngLat(boundingBox.east(), boundingBox.north()), 
/* 518 */                 Point.fromLngLat(boundingBox.west(), boundingBox.north()), 
/* 519 */                 Point.fromLngLat(boundingBox.west(), boundingBox.south())
/*     */               }))), properties, id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Feature bboxPolygon(@NonNull double[] bbox) {
/* 531 */     return bboxPolygon(bbox, (JsonObject)null, (String)null);
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
/*     */   public static Feature bboxPolygon(@NonNull double[] bbox, @Nullable JsonObject properties, @Nullable String id) {
/* 547 */     return Feature.fromGeometry((Geometry)Polygon.fromLngLats(
/* 548 */           Collections.singletonList(
/* 549 */             Arrays.asList(new Point[] {
/* 550 */                 Point.fromLngLat(bbox[0], bbox[1]), 
/* 551 */                 Point.fromLngLat(bbox[2], bbox[1]), 
/* 552 */                 Point.fromLngLat(bbox[2], bbox[3]), 
/* 553 */                 Point.fromLngLat(bbox[0], bbox[3]), 
/* 554 */                 Point.fromLngLat(bbox[0], bbox[1])
/*     */               }))), properties, id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Polygon envelope(GeoJson geoJson) {
/* 565 */     return (Polygon)bboxPolygon(bbox(geoJson)).geometry();
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
/*     */   public static BoundingBox square(@NonNull BoundingBox boundingBox) {
/* 577 */     double horizontalDistance = distance(boundingBox.southwest(), 
/* 578 */         Point.fromLngLat(boundingBox.east(), boundingBox.south()));
/*     */     
/* 580 */     double verticalDistance = distance(
/* 581 */         Point.fromLngLat(boundingBox.west(), boundingBox.south()), 
/* 582 */         Point.fromLngLat(boundingBox.west(), boundingBox.north()));
/*     */ 
/*     */     
/* 585 */     if (horizontalDistance >= verticalDistance) {
/* 586 */       double verticalMidpoint = (boundingBox.south() + boundingBox.north()) / 2.0D;
/* 587 */       return BoundingBox.fromLngLats(boundingBox
/* 588 */           .west(), verticalMidpoint - (boundingBox
/* 589 */           .east() - boundingBox.west()) / 2.0D, boundingBox
/* 590 */           .east(), verticalMidpoint + (boundingBox
/* 591 */           .east() - boundingBox.west()) / 2.0D);
/*     */     } 
/*     */     
/* 594 */     double horizontalMidpoint = (boundingBox.west() + boundingBox.east()) / 2.0D;
/* 595 */     return BoundingBox.fromLngLats(horizontalMidpoint - (boundingBox
/* 596 */         .north() - boundingBox.south()) / 2.0D, boundingBox
/* 597 */         .south(), horizontalMidpoint + (boundingBox
/* 598 */         .north() - boundingBox.south()) / 2.0D, boundingBox
/* 599 */         .north());
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
/*     */   public static double area(@NonNull Feature feature) {
/* 612 */     return (feature.geometry() != null) ? area(feature.geometry()) : 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double area(@NonNull FeatureCollection featureCollection) {
/* 623 */     List<Feature> features = featureCollection.features();
/* 624 */     double total = 0.0D;
/* 625 */     if (features != null) {
/* 626 */       for (Feature feature : features) {
/* 627 */         total += area(feature);
/*     */       }
/*     */     }
/* 630 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double area(@NonNull Geometry geometry) {
/* 641 */     return calculateArea(geometry);
/*     */   }
/*     */   
/*     */   private static double calculateArea(@NonNull Geometry geometry) {
/* 645 */     double total = 0.0D;
/* 646 */     if (geometry instanceof Polygon)
/* 647 */       return polygonArea(((Polygon)geometry).coordinates()); 
/* 648 */     if (geometry instanceof MultiPolygon) {
/* 649 */       List<List<List<Point>>> coordinates = ((MultiPolygon)geometry).coordinates();
/* 650 */       for (int i = 0; i < coordinates.size(); i++) {
/* 651 */         total += polygonArea(coordinates.get(i));
/*     */       }
/* 653 */       return total;
/*     */     } 
/*     */     
/* 656 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   private static double polygonArea(@NonNull List<List<Point>> coordinates) {
/* 661 */     double total = 0.0D;
/* 662 */     if (coordinates.size() > 0) {
/* 663 */       total += Math.abs(ringArea(coordinates.get(0)));
/* 664 */       for (int i = 1; i < coordinates.size(); i++) {
/* 665 */         total -= Math.abs(ringArea(coordinates.get(i)));
/*     */       }
/*     */     } 
/* 668 */     return total;
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
/*     */   private static double ringArea(@NonNull List<Point> coordinates) {
/* 691 */     double total = 0.0D;
/* 692 */     int coordsLength = coordinates.size();
/*     */     
/* 694 */     if (coordsLength > 2) {
/* 695 */       for (int i = 0; i < coordsLength; i++) {
/* 696 */         int lowerIndex, middleIndex, upperIndex; if (i == coordsLength - 2) {
/* 697 */           lowerIndex = coordsLength - 2;
/* 698 */           middleIndex = coordsLength - 1;
/* 699 */           upperIndex = 0;
/* 700 */         } else if (i == coordsLength - 1) {
/* 701 */           lowerIndex = coordsLength - 1;
/* 702 */           middleIndex = 0;
/* 703 */           upperIndex = 1;
/*     */         } else {
/* 705 */           lowerIndex = i;
/* 706 */           middleIndex = i + 1;
/* 707 */           upperIndex = i + 2;
/*     */         } 
/* 709 */         Point p1 = coordinates.get(lowerIndex);
/* 710 */         Point p2 = coordinates.get(middleIndex);
/* 711 */         Point p3 = coordinates.get(upperIndex);
/* 712 */         total += (rad(p3.longitude()) - rad(p1.longitude())) * Math.sin(rad(p2.latitude()));
/*     */       } 
/* 714 */       total = total * EARTH_RADIUS * EARTH_RADIUS / 2.0D;
/*     */     } 
/* 716 */     return total;
/*     */   }
/*     */   
/*     */   private static double rad(double num) {
/* 720 */     return num * Math.PI / 180.0D;
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
/*     */   public static Feature center(Feature feature, @Nullable JsonObject properties, @Nullable String id) {
/* 736 */     return center(FeatureCollection.fromFeature(feature), properties, id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Feature center(Feature feature) {
/* 747 */     return center(FeatureCollection.fromFeature(feature), (JsonObject)null, (String)null);
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
/*     */   public static Feature center(FeatureCollection featureCollection, @Nullable JsonObject properties, @Nullable String id) {
/* 764 */     double[] ext = bbox(featureCollection);
/* 765 */     double finalCenterLongitude = (ext[0] + ext[2]) / 2.0D;
/* 766 */     double finalCenterLatitude = (ext[1] + ext[3]) / 2.0D;
/* 767 */     return Feature.fromGeometry((Geometry)Point.fromLngLat(finalCenterLongitude, finalCenterLatitude), properties, id);
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
/*     */   public static Feature center(FeatureCollection featureCollection) {
/* 780 */     return center(featureCollection, (JsonObject)null, (String)null);
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-turf-5.9.0.jar!\com\mapbox\turf\TurfMeasurement.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */