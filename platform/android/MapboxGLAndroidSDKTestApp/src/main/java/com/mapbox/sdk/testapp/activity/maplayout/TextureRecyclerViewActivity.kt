package com.mapbox.sdk.testapp.activity.maplayout

import android.annotation.SuppressLint
import com.mapbox.sdk.testapp.R

/**
 * TestActivity showcasing how to integrate multiple TexureView MapViews in a RecyclerView.
 */
@SuppressLint("ClickableViewAccessibility")
class TextureRecyclerViewActivity : GLSurfaceRecyclerViewActivity() {

    override fun getMapItemLayoutId(): Int {
        return R.layout.item_map_texture
    }
}
