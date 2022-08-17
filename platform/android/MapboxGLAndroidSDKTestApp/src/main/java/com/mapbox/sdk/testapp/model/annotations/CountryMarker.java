package com.mapbox.sdk.testapp.model.annotations;

import com.gomap.sdk.annotations.BaseMarkerOptions;
import com.gomap.sdk.annotations.Marker;

public class CountryMarker extends Marker {

  private String abbrevName;
  private int flagRes;

  public CountryMarker(BaseMarkerOptions baseMarkerOptions, String abbrevName, int iconRes) {
    super(baseMarkerOptions);
    this.abbrevName = abbrevName;
    this.flagRes = iconRes;
  }

  public String getAbbrevName() {
    return abbrevName;
  }

  public int getFlagRes() {
    return flagRes;
  }
}
