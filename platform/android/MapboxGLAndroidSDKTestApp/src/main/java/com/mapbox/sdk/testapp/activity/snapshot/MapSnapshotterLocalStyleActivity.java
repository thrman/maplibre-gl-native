package com.mapbox.sdk.testapp.activity.snapshot;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.gomap.sdk.camera.CameraPosition;
import com.gomap.sdk.geometry.LatLng;
import com.gomap.sdk.maps.Style;
import com.gomap.sdk.snapshotter.MapSnapshot;
import com.gomap.sdk.snapshotter.MapSnapshotter;
import com.mapbox.sdk.testapp.R;
import com.mapbox.sdk.testapp.utils.ResourceUtils;

import java.io.IOException;

import timber.log.Timber;

/**
 * Test activity showing how to use a the MapSnapshotter with a local style
 */
public class MapSnapshotterLocalStyleActivity extends AppCompatActivity
  implements MapSnapshotter.SnapshotReadyCallback {

  private MapSnapshotter mapSnapshotter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map_snapshotter_marker);

    final View container = findViewById(R.id.container);
    container.getViewTreeObserver()
      .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
          //noinspection deprecation
          container.getViewTreeObserver().removeGlobalOnLayoutListener(this);

          String styleJson;
          try {
            styleJson = ResourceUtils.readRawResource(MapSnapshotterLocalStyleActivity.this, R.raw.sat_style);
          } catch (IOException exception) {
            throw new RuntimeException(exception);
          }

          Timber.i("Starting snapshot");
          mapSnapshotter = new MapSnapshotter(
            getApplicationContext(),
            new MapSnapshotter
              .Options(Math.min(container.getMeasuredWidth(), 1024), Math.min(container.getMeasuredHeight(), 1024))
              .withStyleBuilder(new Style.Builder().fromJson(styleJson))
              .withCameraPosition(new CameraPosition.Builder().target(new LatLng(52.090737, 5.121420)).zoom(18).build())
          );
          mapSnapshotter.start(MapSnapshotterLocalStyleActivity.this, error -> Timber.e(error));
        }
      });
  }

  @Override
  protected void onStop() {
    super.onStop();
    mapSnapshotter.cancel();
  }

  @Override
  public void onSnapshotReady(MapSnapshot snapshot) {
    Timber.i("Snapshot ready");
    ImageView imageView = (ImageView) findViewById(R.id.snapshot_image);
    imageView.setImageBitmap(snapshot.getBitmap());
  }
}
