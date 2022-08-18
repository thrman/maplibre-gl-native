package com.gomap.sdk.maps;

import androidx.collection.LongSparseArray;

import com.gomap.sdk.annotations.Annotation;
import com.gomap.sdk.annotations.BaseMarkerOptions;
import com.gomap.sdk.annotations.Marker;
import com.gomap.sdk.annotations.MarkerOptions;
import com.gomap.sdk.geometry.LatLng;
import com.gomap.sdk.maps.AnnotationContainer;
import com.gomap.sdk.maps.AnnotationManager;
import com.gomap.sdk.maps.Annotations;
import com.gomap.sdk.maps.IconManager;
import com.gomap.sdk.maps.MapView;
import com.gomap.sdk.maps.MapboxMap;
import com.gomap.sdk.maps.MarkerContainer;
import com.gomap.sdk.maps.Markers;
import com.gomap.sdk.maps.NativeMap;
import com.gomap.sdk.maps.NativeMapView;
import com.gomap.sdk.maps.PolygonContainer;
import com.gomap.sdk.maps.Polygons;
import com.gomap.sdk.maps.PolylineContainer;
import com.gomap.sdk.maps.Polylines;
import com.gomap.sdk.maps.ShapeAnnotationContainer;
import com.gomap.sdk.maps.ShapeAnnotations;

import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnnotationManagerTest {

  @Test
  public void checksAddAMarker() throws Exception {
    NativeMap aNativeMapView = mock(NativeMapView.class);
    MapView aMapView = mock(MapView.class);
    LongSparseArray<Annotation> annotationsArray = new LongSparseArray<>();
    IconManager aIconManager = mock(IconManager.class);
    Annotations annotations = new AnnotationContainer(aNativeMapView, annotationsArray);
    Markers markers = new MarkerContainer(aNativeMapView, annotationsArray, aIconManager);
    Polygons polygons = new PolygonContainer(aNativeMapView, annotationsArray);
    Polylines polylines = new PolylineContainer(aNativeMapView, annotationsArray);
    ShapeAnnotations shapeAnnotations = new ShapeAnnotationContainer(aNativeMapView, annotationsArray);
    AnnotationManager annotationManager = new AnnotationManager(aMapView, annotationsArray,
      aIconManager, annotations, markers, polygons, polylines, shapeAnnotations);
    Marker aMarker = mock(Marker.class);
    long aId = 5L;
    when(aNativeMapView.addMarker(aMarker)).thenReturn(aId);
    BaseMarkerOptions aMarkerOptions = mock(BaseMarkerOptions.class);
    MapboxMap aMapboxMap = mock(MapboxMap.class);
    when(aMarkerOptions.getMarker()).thenReturn(aMarker);

    annotationManager.addMarker(aMarkerOptions, aMapboxMap);

    assertEquals(aMarker, annotationManager.getAnnotations().get(0));
    assertEquals(aMarker, annotationManager.getAnnotation(aId));
  }

  @Test
  public void checksAddMarkers() throws Exception {
    NativeMapView aNativeMapView = mock(NativeMapView.class);
    MapView aMapView = mock(MapView.class);
    LongSparseArray<Annotation> annotationsArray = new LongSparseArray<>();
    IconManager aIconManager = mock(IconManager.class);
    Annotations annotations = new AnnotationContainer(aNativeMapView, annotationsArray);
    Markers markers = new MarkerContainer(aNativeMapView, annotationsArray, aIconManager);
    Polygons polygons = new PolygonContainer(aNativeMapView, annotationsArray);
    Polylines polylines = new PolylineContainer(aNativeMapView, annotationsArray);
    ShapeAnnotations shapeAnnotations = new ShapeAnnotationContainer(aNativeMapView, annotationsArray);
    AnnotationManager annotationManager = new AnnotationManager(aMapView, annotationsArray,
      aIconManager, annotations, markers, polygons, polylines, shapeAnnotations);

    long firstId = 1L;
    long secondId = 2L;
    List<BaseMarkerOptions> markerList = new ArrayList<>();
    MarkerOptions firstMarkerOption = new MarkerOptions().position(new LatLng()).title("first");
    MarkerOptions secondMarkerOption = new MarkerOptions().position(new LatLng()).title("second");

    markerList.add(firstMarkerOption);
    markerList.add(secondMarkerOption);
    MapboxMap aMapboxMap = mock(MapboxMap.class);
    when(aNativeMapView.addMarker(any(Marker.class))).thenReturn(firstId, secondId);

    when(aNativeMapView.addMarkers(ArgumentMatchers.<Marker>anyList()))
      .thenReturn(new long[] {firstId, secondId});

    annotationManager.addMarkers(markerList, aMapboxMap);

    assertEquals(2, annotationManager.getAnnotations().size());
    assertEquals("first", ((Marker) annotationManager.getAnnotations().get(0)).getTitle());
    assertEquals("second", ((Marker) annotationManager.getAnnotations().get(1)).getTitle());
    assertEquals("first", ((Marker) annotationManager.getAnnotation(firstId)).getTitle());
    assertEquals("second", ((Marker) annotationManager.getAnnotation(secondId)).getTitle());
  }
}