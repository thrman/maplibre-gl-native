package com.mapbox.sdk.testapp.model.other;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.mapbox.sdk.testapp.R;

import timber.log.Timber;

public class OfflineDownloadRegionDialog extends DialogFragment {

  public interface DownloadRegionDialogListener {
    void onDownloadRegionDialogPositiveClick(String regionName);
  }

  DownloadRegionDialogListener listener;

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    listener = (DownloadRegionDialogListener) activity;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    // Let the user choose a name for the region
    final EditText regionNameEdit = new EditText(getActivity());

    builder.setTitle("Choose a name for the region")
      .setIcon(R.drawable.ic_airplanemode_active_black)
      .setView(regionNameEdit)
      .setPositiveButton("Start", (dialog, which) -> {
        String regionName = regionNameEdit.getText().toString();
        listener.onDownloadRegionDialogPositiveClick(regionName);
      }).setNegativeButton("Cancel", (dialog, which) -> Timber.d("Download cancelled."));

    return builder.create();
  }
}
