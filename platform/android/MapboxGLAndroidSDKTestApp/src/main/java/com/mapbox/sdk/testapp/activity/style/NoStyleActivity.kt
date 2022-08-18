package com.mapbox.sdk.testapp.activity.style

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.gomap.sdk.camera.CameraUpdateFactory
import com.gomap.sdk.geometry.LatLng
import com.gomap.sdk.maps.Style
import com.gomap.sdk.style.layers.PropertyFactory.iconImage
import com.gomap.sdk.style.layers.SymbolLayer
import com.gomap.sdk.style.sources.GeoJsonSource
import com.mapbox.sdk.testapp.R
import kotlinx.android.synthetic.main.activity_map_simple.*
import java.net.URI

/**
 * Activity showcasing how to load symbols on a map without a Style URI or Style JSON.
 */
class NoStyleActivity : AppCompatActivity() {

    private val imageIcon: Drawable by lazy {
        ResourcesCompat.getDrawable(resources, R.drawable.ic_add_white, theme)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_simple)
        mapView.getMapAsync { map ->
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraTarget, cameraZoom))
            map.setStyle(
                Style.Builder()
                    .withImage(imageId, imageIcon)
                    .withSource(GeoJsonSource(sourceId, URI("asset://points-sf.geojson")))
                    .withLayer(SymbolLayer(layerId, sourceId).withProperties(iconImage(imageId)))
            )
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
        const val layerId = "custom-layer-id"
        const val sourceId = "custom-source-id"
        const val imageId = "image-id"
        const val cameraZoom = 10.0
        val cameraTarget = LatLng(37.758912, -122.442578)
    }
}
