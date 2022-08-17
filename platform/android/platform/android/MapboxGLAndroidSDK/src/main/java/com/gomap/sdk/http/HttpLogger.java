package com.gomap.sdk.http;

import android.util.Log;

import com.gomap.sdk.log.Logger;

public class HttpLogger {

  private static final String TAG = "Mbgl-HttpRequest";

  public static boolean logRequestUrl;
  public static boolean logEnabled = true;

  private HttpLogger(){
  }

  public static void logFailure(int type, String errorMessage, String requestUrl) {
    log(type == HttpRequest.TEMPORARY_ERROR ? Log.DEBUG : type == HttpRequest.CONNECTION_ERROR ? Log.INFO : Log.WARN,
      String.format(
        "Request failed due to a %s error: %s %s",
        type == HttpRequest.TEMPORARY_ERROR ? "temporary" : type == HttpRequest.CONNECTION_ERROR ? "connection" : "permanent",
        errorMessage,
        logRequestUrl ? requestUrl : ""
      )
    );
  }

  public static void log(int type, String errorMessage) {
    if (logEnabled) {
      Logger.log(type, TAG, errorMessage);
    }
  }
}
