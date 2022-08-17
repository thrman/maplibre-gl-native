package com.mapbox.sdk.testapp.activity.style;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gomap.sdk.maps.MapView;
import com.gomap.sdk.maps.MapboxMap;
import com.gomap.sdk.maps.Style;
import com.mapbox.sdk.testapp.R;
import com.mapbox.sdk.testapp.utils.ResourceUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ref.WeakReference;

import timber.log.Timber;

/**
 * Test activity showcasing how to use a file:// resource for the style.json and how to use MapboxMap#setStyleJson.
 */
public class StyleFileActivity extends AppCompatActivity {

  private MapboxMap mapboxMap;
  private MapView mapView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_style_file);

    mapView = findViewById(R.id.mapView);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(map -> {
      mapboxMap = map;
      mapboxMap.setStyle(Style.getPredefinedStyle("Streets"), style -> {
        FloatingActionButton fab = findViewById(R.id.fab_file);
        fab.setColorFilter(ContextCompat.getColor(StyleFileActivity.this, R.color.primary));
        fab.setOnClickListener(view -> new CreateStyleFileTask(view.getContext(), mapboxMap).execute());

        FloatingActionButton fabStyleJson = findViewById(R.id.fab_style_json);
        fabStyleJson.setColorFilter(ContextCompat.getColor(StyleFileActivity.this, R.color.primary));
        fabStyleJson.setOnClickListener(view -> new LoadStyleFileTask(view.getContext(), mapboxMap).execute());
      });
    });
  }

  /**
   * Task to read a style file from the raw folder
   */
  private static class LoadStyleFileTask extends AsyncTask<Void, Void, String> {
    private WeakReference<Context> context;
    private WeakReference<MapboxMap> mapboxMap;

    LoadStyleFileTask(Context context, MapboxMap mapboxMap) {
      this.context = new WeakReference<>(context);
      this.mapboxMap = new WeakReference<>(mapboxMap);
    }

    @Override
    protected String doInBackground(Void... voids) {
      String styleJson = "";
      try {
        styleJson = ResourceUtils.readRawResource(context.get(), R.raw.sat_style);
      } catch (Exception exception) {
        Timber.e(exception, "Can't load local file style");
      }
      return styleJson;
    }

    @Override
    protected void onPostExecute(String json) {
      super.onPostExecute(json);
      Timber.d("Read json, %s", json);
      MapboxMap mapboxMap = this.mapboxMap.get();
      if (mapboxMap != null) {
        mapboxMap.setStyle(new Style.Builder().fromJson(json));
      }
    }
  }

  /**
   * Task to write a style file to local disk and load it in the map view
   */
  private static class CreateStyleFileTask extends AsyncTask<Void, Integer, Long> {
    private File cacheStyleFile;
    private WeakReference<Context> context;
    private WeakReference<MapboxMap> mapboxMap;

    CreateStyleFileTask(Context context, MapboxMap mapboxMap) {
      this.context = new WeakReference<>(context);
      this.mapboxMap = new WeakReference<>(mapboxMap);
    }

    @Override
    protected Long doInBackground(Void... params) {
      try {
        cacheStyleFile = File.createTempFile("my-", ".style.json");
        cacheStyleFile.createNewFile();
        Timber.i("Writing style file to: %s", cacheStyleFile.getAbsolutePath());
        Context context = this.context.get();
        if (context != null) {
          writeToFile(cacheStyleFile, ResourceUtils.readRawResource(context, R.raw.local_style));
        }
      } catch (Exception exception) {
        Toast.makeText(context.get(), "Could not create style file in cache dir", Toast.LENGTH_SHORT).show();
      }
      return 1L;
    }

    protected void onPostExecute(Long result) {
      // Actual file:// usage
      MapboxMap mapboxMap = this.mapboxMap.get();
      if (mapboxMap != null) {
        mapboxMap.setStyle(new Style.Builder().fromUri("file://" + cacheStyleFile.getAbsolutePath()));
      }
    }

    private void writeToFile(File file, String contents) throws IOException {
      BufferedWriter writer = null;
      try {
        writer = new BufferedWriter(new FileWriter(file));
        writer.write(contents);
      } finally {
        if (writer != null) {
          writer.close();
        }
      }
    }
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
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }
}
