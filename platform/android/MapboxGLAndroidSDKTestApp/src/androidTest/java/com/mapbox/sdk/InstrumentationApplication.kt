package com.mapbox.sdk

import com.mapbox.sdk.testapp.MapboxApplication

class InstrumentationApplication : MapboxApplication() {
    override fun initializeLeakCanary(): Boolean {
        // do not initialize leak canary during instrumentation tests
        return true
    }
}
