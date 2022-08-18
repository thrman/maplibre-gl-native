package com.mapbox.sdk.testapp.maps

import androidx.test.espresso.UiController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gomap.sdk.maps.MapboxMap
import com.gomap.sdk.maps.Style
import com.gomap.sdk.style.layers.SymbolLayer
import com.gomap.sdk.style.sources.GeoJsonSource
import com.mapbox.sdk.testapp.action.MapboxMapAction
import com.mapbox.sdk.testapp.activity.EspressoTest
import com.mapbox.sdk.testapp.utils.TestingAsyncUtils
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StyleLoadTest : EspressoTest() {

    @Test
    fun updateSourceAfterStyleLoad() {
        validateTestSetup()
        MapboxMapAction.invoke(mapboxMap) { uiController: UiController, mapboxMap: MapboxMap ->
            val source = GeoJsonSource("id")
            val layer = SymbolLayer("id", "id")
            mapboxMap.setStyle(Style.Builder().withSource(source).withLayer(layer))
            TestingAsyncUtils.waitForLayer(uiController, mapView)
            mapboxMap.setStyle(Style.Builder().fromUrl(Style.getPredefinedStyle("Streets")))
            TestingAsyncUtils.waitForLayer(uiController, mapView)
            source.setGeoJson("{}")
        }
    }
}
