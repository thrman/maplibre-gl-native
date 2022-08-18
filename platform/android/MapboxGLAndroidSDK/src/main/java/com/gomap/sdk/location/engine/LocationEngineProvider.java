package com.gomap.sdk.location.engine;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * The main entry point for location engine integration.
 */
public final class LocationEngineProvider {
  private static final String GOOGLE_LOCATION_SERVICES = "com.google.android.gms.location.LocationServices";
  private static final String GOOGLE_API_AVAILABILITY = "com.google.android.gms.common.GoogleApiAvailability";

  private LocationEngineProvider() {
    // prevent instantiation
  }

  /**
   * Returns instance to the best location engine, given the included libraries.
   *
   * @param context    {@link Context}.
   * @param background true if background optimized engine is desired (note: parameter deprecated)
   * @return a unique instance of {@link LocationEngine} every time method is called.
   * @since 1.0.0
   */
  @NonNull
  @Deprecated
  public static LocationEngine getBestLocationEngine(@NonNull Context context, boolean background) {
    return getBestLocationEngine(context);
  }

  /**
   * Returns instance to the best location engine, given the included libraries.
   *
   * @param context    {@link Context}.
   * @return a unique instance of {@link LocationEngine} every time method is called.
   * @since 1.1.0
   */
  @NonNull
  public static LocationEngine getBestLocationEngine(@NonNull Context context) {
    Utils.checkNotNull(context, "context == null");

    boolean hasGoogleLocationServices = Utils.isOnClasspath(GOOGLE_LOCATION_SERVICES);
    if (Utils.isOnClasspath(GOOGLE_API_AVAILABILITY)) {
      // Check Google Play services APK is available and up-to-date on this device
      hasGoogleLocationServices &= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
        == ConnectionResult.SUCCESS;
    }
    return getLocationEngine(context, hasGoogleLocationServices);
  }

  private static LocationEngine getLocationEngine(Context context, boolean isGoogle) {
    return isGoogle ? new LocationEngineProxy<>(new GoogleLocationEngineImpl(context.getApplicationContext())) :
      new LocationEngineProxy<>(new MapboxFusedLocationEngineImpl(context.getApplicationContext()));
  }
}
