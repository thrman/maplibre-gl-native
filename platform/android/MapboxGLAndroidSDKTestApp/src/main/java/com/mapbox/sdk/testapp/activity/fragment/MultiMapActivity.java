package com.mapbox.sdk.testapp.activity.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.gomap.sdk.maps.Style;
import com.gomap.sdk.maps.SupportMapFragment;
import com.mapbox.sdk.testapp.R;

/**
 * Test Activity showcasing using multiple static map fragments in one layout.
 */
public class MultiMapActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_multi_map);

    FragmentManager fragmentManager = getSupportFragmentManager();
    initFragmentStyle(fragmentManager, R.id.map1, Style.getPredefinedStyle("Streets"));
    initFragmentStyle(fragmentManager, R.id.map2, Style.getPredefinedStyle("Bright"));
    initFragmentStyle(fragmentManager, R.id.map3, Style.getPredefinedStyle("Satellite Hybrid"));
    initFragmentStyle(fragmentManager, R.id.map4, Style.getPredefinedStyle("Pastel"));
  }

  private void initFragmentStyle(FragmentManager fragmentManager, int fragmentId, String styleId) {
    ((SupportMapFragment) fragmentManager.findFragmentById(fragmentId))
      .getMapAsync(mapboxMap -> mapboxMap.setStyle(styleId));
  }
}
