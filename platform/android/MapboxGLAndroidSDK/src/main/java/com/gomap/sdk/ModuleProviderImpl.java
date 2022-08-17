package com.gomap.sdk;

import androidx.annotation.NonNull;

import com.gomap.sdk.http.HttpRequest;
import com.gomap.sdk.module.http.HttpRequestImpl;
import com.gomap.sdk.module.loader.LibraryLoaderProviderImpl;

public class ModuleProviderImpl implements ModuleProvider {

  @Override
  @NonNull
  public HttpRequest createHttpRequest() {
    return new HttpRequestImpl();
  }

  @NonNull
  @Override
  public LibraryLoaderProvider createLibraryLoaderProvider() {
    return new LibraryLoaderProviderImpl();
  }
}
