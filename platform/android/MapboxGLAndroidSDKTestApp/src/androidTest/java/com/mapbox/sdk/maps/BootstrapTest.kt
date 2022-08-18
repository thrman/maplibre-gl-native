package com.mapbox.sdk.maps

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mapbox.sdk.AppCenter
import com.gomap.sdk.Mapbox
import com.gomap.sdk.WellKnownTileServer
import com.gomap.sdk.maps.Style
import com.gomap.sdk.storage.FileSource
import com.mapbox.sdk.testapp.MapboxApplication
import com.gomap.sdk.util.TileServerOptions
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BootstrapTest : AppCenter() {

    var apiKeyBackup: String? = null

    @Before
    @UiThreadTest
    fun before() {
        apiKeyBackup = Mapbox.getApiKey()
    }

    @After
    @UiThreadTest
    fun after() {
        val context = InstrumentationRegistry.getInstrumentation().context
        Mapbox.getInstance(context, apiKeyBackup, MapboxApplication.TILE_SERVER)
    }

    @Test
    @UiThreadTest
    fun defaultBootstrap() {
        val context = InstrumentationRegistry.getInstrumentation().context

        Mapbox.getInstance(context)

        val tileServerOptions = TileServerOptions.get(WellKnownTileServer.MapLibre)
        Assert.assertTrue(
            Style.getPredefinedStyles().count() == tileServerOptions.defaultStyles.count()
        )
        Assert.assertTrue(
            Style.getPredefinedStyles().first().url == tileServerOptions.defaultStyles.first().url
        )

        val fileSource = FileSource.getInstance(context)
        Assert.assertEquals(fileSource.apiBaseUrl, tileServerOptions.baseURL)
    }

    @Test
    @UiThreadTest
    fun maptilerBootstrap() {
        val context = InstrumentationRegistry.getInstrumentation().context

        val key = "abcdef"
        Mapbox.getInstance(context, key, WellKnownTileServer.MapTiler)

        val tileServerOptions = TileServerOptions.get(WellKnownTileServer.MapTiler)
        Assert.assertTrue(
            Style.getPredefinedStyles().count() == tileServerOptions.defaultStyles.count()
        )
        Assert.assertTrue(
            Style.getPredefinedStyles().first().url == tileServerOptions.defaultStyles.first().url
        )

        val fileSource = FileSource.getInstance(context)
        Assert.assertEquals(fileSource.apiBaseUrl, tileServerOptions.baseURL)
        Assert.assertEquals(fileSource.apiKey, key)
    }

    @Test
    @UiThreadTest
    fun duplicateBootstrap() {
        val context = InstrumentationRegistry.getInstrumentation().context

        val key = "pk.abcdef"

        Mapbox.getInstance(context, key, WellKnownTileServer.MapTiler)
        Mapbox.getInstance(context)
        Mapbox.getInstance(context, key, WellKnownTileServer.Mapbox)

        val tileServerOptions = TileServerOptions.get(WellKnownTileServer.Mapbox)
        Assert.assertTrue(
            Style.getPredefinedStyles().count() == tileServerOptions.defaultStyles.count()
        )
        Assert.assertTrue(
            Style.getPredefinedStyles().first().url == tileServerOptions.defaultStyles.first().url
        )

        val fileSource = FileSource.getInstance(context)
        Assert.assertEquals(fileSource.apiBaseUrl, tileServerOptions.baseURL)
        Assert.assertEquals(fileSource.apiKey, key)
    }
}
