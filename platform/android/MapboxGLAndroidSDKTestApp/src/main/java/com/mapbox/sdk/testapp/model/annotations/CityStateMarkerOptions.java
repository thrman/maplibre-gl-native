package com.mapbox.sdk.testapp.model.annotations;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.gomap.sdk.annotations.BaseMarkerOptions;
import com.gomap.sdk.annotations.Icon;
import com.gomap.sdk.annotations.IconFactory;
import com.gomap.sdk.geometry.LatLng;

public class CityStateMarkerOptions extends BaseMarkerOptions<CityStateMarker, CityStateMarkerOptions> {

  private String infoWindowBackgroundColor;

  public CityStateMarkerOptions infoWindowBackground(String color) {
    infoWindowBackgroundColor = color;
    return getThis();
  }

  public CityStateMarkerOptions() {
  }

  private CityStateMarkerOptions(Parcel in) {
    position((LatLng) in.readParcelable(LatLng.class.getClassLoader()));
    snippet(in.readString());
    String iconId = in.readString();
    Bitmap iconBitmap = in.readParcelable(Bitmap.class.getClassLoader());
    Icon icon = IconFactory.recreate(iconId, iconBitmap);
    icon(icon);
    title(in.readString());
  }

  @Override
  public CityStateMarkerOptions getThis() {
    return this;
  }

  @Override
  public CityStateMarker getMarker() {
    return new CityStateMarker(this, infoWindowBackgroundColor);
  }

  public static final Creator<CityStateMarkerOptions> CREATOR
    = new Creator<CityStateMarkerOptions>() {
      public CityStateMarkerOptions createFromParcel(Parcel in) {
        return new CityStateMarkerOptions(in);
      }

      public CityStateMarkerOptions[] newArray(int size) {
        return new CityStateMarkerOptions[size];
      }
    };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel out, int flags) {
    out.writeParcelable(position, flags);
    out.writeString(snippet);
    out.writeString(icon.getId());
    out.writeParcelable(icon.getBitmap(), flags);
    out.writeString(title);
  }
}
