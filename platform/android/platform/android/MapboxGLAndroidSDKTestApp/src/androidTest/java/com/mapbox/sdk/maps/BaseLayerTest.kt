package com.mapbox.sdk.maps

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gomap.sdk.maps.NativeMap
import com.gomap.sdk.maps.NativeMapView
import com.mapbox.sdk.AppCenter
import com.gomap.sdk.style.layers.Layer
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseLayerTest : AppCenter() {
    private lateinit var nativeMapView: NativeMap

    companion object {
        const val WIDTH = 500
        const val HEIGHT = WIDTH
    }

    fun before() {
        val context = InstrumentationRegistry.getInstrumentation().context
        nativeMapView = NativeMapView(context, false, null, null, NativeMapViewTest.DummyRenderer(context))
        nativeMapView.resizeView(WIDTH, HEIGHT)
    }

    fun setupLayer(layer: Layer) {
        nativeMapView.addLayer(layer)
    }
}
