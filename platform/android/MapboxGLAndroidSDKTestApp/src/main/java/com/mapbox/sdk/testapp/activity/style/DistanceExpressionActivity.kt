package com.mapbox.sdk.testapp.activity.style

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.gomap.geojson.Point
import com.gomap.sdk.camera.CameraPosition
import com.gomap.sdk.geometry.LatLng
import com.gomap.sdk.maps.MapboxMap
import com.gomap.sdk.maps.Style
import com.gomap.sdk.style.expressions.Expression.distance
import com.gomap.sdk.style.expressions.Expression.lt
import com.gomap.sdk.style.layers.FillLayer
import com.gomap.sdk.style.layers.Property.NONE
import com.gomap.sdk.style.layers.PropertyFactory.*
import com.gomap.sdk.style.layers.SymbolLayer
import com.gomap.sdk.style.sources.GeoJsonSource
import com.gomap.turf.TurfConstants
import com.gomap.turf.TurfTransformation
import com.mapbox.sdk.testapp.R
import kotlinx.android.synthetic.main.activity_physical_circle.*

/**
 * An Activity that showcases the within expression to filter features outside a geometry
 */
class DistanceExpressionActivity : AppCompatActivity() {

    private lateinit var mapboxMap: MapboxMap

    private val lat = 37.78794572301525
    private val lon = -122.40752220153807

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_within_expression)

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            mapboxMap = map

            // Setup camera position above Georgetown
            mapboxMap.cameraPosition = CameraPosition.Builder()
                .target(LatLng(lat, lon))
                .zoom(16.0)
                .build()
            setupStyle()
        }
    }

    private fun setupStyle() {
        val center = Point.fromLngLat(lon, lat)
        val circle = TurfTransformation.circle(center, 150.0, TurfConstants.UNIT_METRES)
        // Setup style with additional layers,
        // using Streets as a base style
        mapboxMap.setStyle(
            Style.Builder()
                .fromUri(Style.getPredefinedStyle("Streets"))
                .withSources(
                        GeoJsonSource(
                                POINT_ID,
                                Point.fromLngLat(lon, lat)
                        ),
                        GeoJsonSource(CIRCLE_ID, circle)
                )
                .withLayerBelow(
                    FillLayer(CIRCLE_ID, CIRCLE_ID)
                        .withProperties(
                            fillOpacity(0.5f),
                            fillColor(Color.parseColor("#3bb2d0"))
                        ),
                    "poi-label"
                )
        ) { style ->
            // Show only POI labels inside circle radius using distance expression
            val symbolLayer = style.getLayer("poi_z16") as SymbolLayer
            symbolLayer.setFilter(
                lt(
                    distance(
                        Point.fromLngLat(lon, lat)
                    ),
                    150
                )
            )

            // Hide other types of labels to highlight POI labels
            (style.getLayer("road_label") as SymbolLayer).setProperties(visibility(NONE))
            (style.getLayer("airport-label-major") as SymbolLayer).setProperties(visibility(NONE))
            (style.getLayer("poi_transit") as SymbolLayer).setProperties(visibility(NONE))
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.let {
            mapView.onSaveInstanceState(it)
        }
    }

    companion object {
        const val POINT_ID = "point"
        const val CIRCLE_ID = "circle"
    }
}