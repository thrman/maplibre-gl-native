package com.mapbox.sdk.testapp.activity.location;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gomap.sdk.location.LocationComponent;
import com.gomap.sdk.location.LocationComponentActivationOptions;
import com.gomap.sdk.location.LocationComponentOptions;
import com.gomap.sdk.location.modes.CameraMode;
import com.gomap.sdk.location.modes.RenderMode;
import com.gomap.sdk.location.permissions.PermissionsListener;
import com.gomap.sdk.location.permissions.PermissionsManager;
import com.gomap.sdk.maps.MapView;
import com.gomap.sdk.maps.MapboxMap;
import com.gomap.sdk.maps.OnMapReadyCallback;
import com.gomap.sdk.maps.Style;
import com.mapbox.sdk.testapp.R;

import java.util.List;

public class LocationComponentActivationActivity extends AppCompatActivity implements OnMapReadyCallback {

  private MapView mapView;
  private MapboxMap mapboxMap;
  private PermissionsManager permissionsManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_location_layer_activation_builder);

    mapView = findViewById(R.id.mapView);

    mapView.onCreate(savedInstanceState);

    if (PermissionsManager.areLocationPermissionsGranted(this)) {
      mapView.getMapAsync(this);
    } else {
      permissionsManager = new PermissionsManager(new PermissionsListener() {
        @Override
        public void onExplanationNeeded(List<String> permissionsToExplain) {
          Toast.makeText(LocationComponentActivationActivity.this, "You need to accept location permissions.",
            Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionResult(boolean granted) {
          if (granted) {
            mapView.getMapAsync(LocationComponentActivationActivity.this);
          } else {
            finish();
          }
        }
      });
      permissionsManager.requestLocationPermissions(this);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  @Override
  public void onMapReady(@NonNull MapboxMap mapboxMap) {
    this.mapboxMap = mapboxMap;
    mapboxMap.setStyle(Style.getPredefinedStyle("Bright"),
      style -> activateLocationComponent(style));
  }

  @SuppressLint("MissingPermission")
  private void activateLocationComponent(@NonNull Style style) {
    LocationComponent locationComponent = mapboxMap.getLocationComponent();

    LocationComponentOptions locationComponentOptions = LocationComponentOptions.builder(this)
      .elevation(5)
      .accuracyAlpha(.6f)
      .accuracyColor(Color.GREEN)
      .foregroundDrawable(R.drawable.mapbox_logo_helmet)
      .build();

    LocationComponentActivationOptions locationComponentActivationOptions = LocationComponentActivationOptions
      .builder(this, style)
      .locationComponentOptions(locationComponentOptions)
      .useDefaultLocationEngine(true)
      .build();

    locationComponent.activateLocationComponent(locationComponentActivationOptions);
    locationComponent.setLocationComponentEnabled(true);
    locationComponent.setRenderMode(RenderMode.NORMAL);
    locationComponent.setCameraMode(CameraMode.TRACKING);
  }

  @Override
  protected void onStart() {
    super.onStart();
    mapView.onStart();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mapView.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    mapView.onPause();
  }

  @Override
  protected void onStop() {
    super.onStop();
    mapView.onStop();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }
}