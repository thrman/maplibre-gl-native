package com.mapbox.sdk.snapshotter

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.gomap.sdk.camera.CameraPosition
import com.gomap.sdk.geometry.LatLng
import com.gomap.sdk.maps.Style
import com.gomap.sdk.snapshotter.MapSnapshotter
import com.gomap.sdk.style.layers.BackgroundLayer
import com.gomap.sdk.style.layers.PropertyFactory
import com.mapbox.sdk.testapp.activity.FeatureOverviewActivity
import junit.framework.Assert.assertNotNull
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Integration test that validates if a snapshotter creation
 */
@RunWith(AndroidJUnit4::class)
class MapSnapshotterTest {

    @Rule
    @JvmField
    var rule = ActivityTestRule(FeatureOverviewActivity::class.java)

    private val countDownLatch = CountDownLatch(1)

    @Test
    fun mapSnapshotter() {
        var mapSnapshotter: MapSnapshotter?
        rule.activity.runOnUiThread {
            val bg = BackgroundLayer("rand_tint")
            bg.setProperties(PropertyFactory.backgroundColor("rgba(255,128,0,0.7)"))
            val options = MapSnapshotter.Options(512, 512)
                .withPixelRatio(1.0f)
                .withStyleBuilder(
                    Style.Builder().fromUri(Style.getPredefinedStyle("Satellite Hybrid"))
                        .withLayerAbove(bg, "country-label")
                )
                .withCameraPosition(
                    CameraPosition.Builder()
                        .zoom(12.0)
                        .target(LatLng(51.145495, 5.742234))
                        .build()
                )
            mapSnapshotter = MapSnapshotter(rule.activity, options)
            mapSnapshotter!!.start(
                {
                    assertNotNull(it)
                    assertNotNull(it.bitmap)
                    countDownLatch.countDown()
                },
                {
                    Assert.fail(it)
                }
            )
        }
        if (!countDownLatch.await(30, TimeUnit.SECONDS)) {
            throw TimeoutException()
        }
    }
}
