package com.gomap.sdk.maps;

import android.graphics.RectF;

import com.gomap.sdk.annotations.Annotation;

import java.util.List;

interface ShapeAnnotations {

  List<Annotation> obtainAllIn(RectF rectF);

}
