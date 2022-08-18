package com.gomap.sdk.exceptions;

public class CalledFromWorkerThreadException extends RuntimeException {

  public CalledFromWorkerThreadException(String message) {
    super(message);
  }
}
