package com.gomap.sdk.module.http

import com.gomap.sdk.module.http.HttpRequestImpl
import com.gomap.sdk.module.http.HttpRequestUtil
import com.gomap.sdk.MapboxInjector
import com.gomap.sdk.utils.ConfigUtils
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import okhttp3.OkHttpClient
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HttpRequestUtilTest {

    @Test
    fun replaceHttpClient() {
        MapboxInjector.inject(mockk(relaxed = true), "", ConfigUtils.getMockedOptions())

        assertEquals(HttpRequestImpl.DEFAULT_CLIENT, HttpRequestImpl.client)

        val httpMock = mockk<OkHttpClient>()
        HttpRequestUtil.setOkHttpClient(httpMock)
        assertEquals(
            "Http client should have set to the mocked client",
            httpMock,
            HttpRequestImpl.client
        )

        HttpRequestUtil.setOkHttpClient(null)
        assertEquals(
            "Http client should have been reset to the default client",
            HttpRequestImpl.DEFAULT_CLIENT,
            HttpRequestImpl.client
        )

        MapboxInjector.clear()
    }
}
