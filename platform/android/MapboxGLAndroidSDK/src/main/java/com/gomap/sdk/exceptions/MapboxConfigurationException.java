package com.gomap.sdk.exceptions;

import android.content.Context;

import androidx.annotation.NonNull;

import com.gomap.sdk.Mapbox;

/**
 * A MapboxConfigurationException is thrown by MapboxMap when the SDK hasn't been properly initialised.
 * <p>
 * This occurs either when {@link Mapbox} is not correctly initialised or the provided apiKey
 * through {@link Mapbox#getInstance(Context, String)} isn't valid.
 * </p>
 *
 * @see Mapbox#getInstance(Context, String)
 */
public class MapboxConfigurationException extends RuntimeException {

  /**
   * Creates a Mapbox configuration exception thrown by MapboxMap when the SDK hasn't been properly initialised.
   */
  public MapboxConfigurationException() {
    super("\nUsing MapView requires calling Mapbox.getInstance(Context context, String apiKey) before "
      + "inflating or creating the view.");
  }

  /**
   * Creates a Mapbox configuration exception thrown by MapboxMap when the SDK hasn't been properly initialised.
   */
  public MapboxConfigurationException(@NonNull String message) {
    super(message);
  }
}
