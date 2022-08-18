package com.mapbox.android.gestures;

import android.content.Context;
import android.os.Build;
import android.view.MotionEvent;
import androidx.annotation.UiThread;

import com.gomap.sdk.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@UiThread
public class AndroidGesturesManager {
    public static final int GESTURE_TYPE_SCROLL = 0;

    public static final int GESTURE_TYPE_SCALE = 1;

    public static final int GESTURE_TYPE_ROTATE = 2;

    public static final int GESTURE_TYPE_SHOVE = 3;

    public static final int GESTURE_TYPE_MULTI_FINGER_TAP = 4;

    public static final int GESTURE_TYPE_SINGLE_TAP_UP = 5;

    public static final int GESTURE_TYPE_LONG_PRESS = 6;

    public static final int GESTURE_TYPE_FLING = 7;

    public static final int GESTURE_TYPE_SHOW_PRESS = 8;

    public static final int GESTURE_TYPE_DOWN = 9;

    public static final int GESTURE_TYPE_DOUBLE_TAP = 10;

    public static final int GESTURE_TYPE_DOUBLE_TAP_EVENT = 11;

    public static final int GESTURE_TYPE_SINGLE_TAP_CONFIRMED = 12;

    public static final int GESTURE_TYPE_MOVE = 13;

    public static final int GESTURE_TYPE_SIDEWAYS_SHOVE = 14;

    public static final int GESTURE_TYPE_QUICK_SCALE = 15;

    private final List<Set<Integer>> mutuallyExclusiveGestures = new ArrayList<>();

    private final List<BaseGesture> detectors = new ArrayList<>();

    private StandardGestureDetector standardGestureDetector;

    private StandardScaleGestureDetector standardScaleGestureDetector;

    private RotateGestureDetector rotateGestureDetector;

    private ShoveGestureDetector shoveGestureDetector;

    private MultiFingerTapGestureDetector multiFingerTapGestureDetector;

    private MoveGestureDetector moveGestureDetector;

    private SidewaysShoveGestureDetector sidewaysShoveGestureDetector;

    public AndroidGesturesManager(Context context) {
        this(context, true);
    }

    public AndroidGesturesManager(Context context, boolean applyDefaultThresholds) {
        this(context, new ArrayList<Set<Integer>>(), applyDefaultThresholds);
    }

    @SafeVarargs
    public AndroidGesturesManager(Context context, Set<Integer>... exclusiveGestures) {
        this(context, Arrays.asList(exclusiveGestures), true);
    }

    public AndroidGesturesManager(Context context, List<Set<Integer>> exclusiveGestures, boolean applyDefaultThresholds) {
        this.mutuallyExclusiveGestures.addAll(exclusiveGestures);
        this.rotateGestureDetector = new RotateGestureDetector(context, this);
        this.standardScaleGestureDetector = new StandardScaleGestureDetector(context, this);
        this.shoveGestureDetector = new ShoveGestureDetector(context, this);
        this.sidewaysShoveGestureDetector = new SidewaysShoveGestureDetector(context, this);
        this.multiFingerTapGestureDetector = new MultiFingerTapGestureDetector(context, this);
        this.moveGestureDetector = new MoveGestureDetector(context, this);
        this.standardGestureDetector = new StandardGestureDetector(context, this);
        this.detectors.add(this.rotateGestureDetector);
        this.detectors.add(this.standardScaleGestureDetector);
        this.detectors.add(this.shoveGestureDetector);
        this.detectors.add(this.sidewaysShoveGestureDetector);
        this.detectors.add(this.multiFingerTapGestureDetector);
        this.detectors.add(this.moveGestureDetector);
        this.detectors.add(this.standardGestureDetector);
        if (applyDefaultThresholds)
            initDefaultThresholds();
    }

    private void initDefaultThresholds() {
        for (BaseGesture detector : this.detectors) {
            if (detector instanceof MultiFingerGesture)
                if (Build.VERSION.SDK_INT < 24) {
                    ((MultiFingerGesture)detector).setSpanThresholdResource(R.dimen.mapbox_internalMinSpan23);
                } else {
                    ((MultiFingerGesture)detector).setSpanThresholdResource(R.dimen.mapbox_internalMinSpan24);
                }
            if (detector instanceof StandardScaleGestureDetector)
                ((StandardScaleGestureDetector)detector).setSpanSinceStartThresholdResource(R.dimen.mapbox_defaultScaleSpanSinceStartThreshold);
            if (detector instanceof ShoveGestureDetector) {
                ((ShoveGestureDetector)detector).setPixelDeltaThresholdResource(R.dimen.mapbox_defaultShovePixelThreshold);
                ((ShoveGestureDetector)detector).setMaxShoveAngle(20.0F);
            }
            if (detector instanceof SidewaysShoveGestureDetector) {
                ((SidewaysShoveGestureDetector)detector).setPixelDeltaThresholdResource(R.dimen.mapbox_defaultShovePixelThreshold);
                ((SidewaysShoveGestureDetector)detector).setMaxShoveAngle(20.0F);
            }
            if (detector instanceof MultiFingerTapGestureDetector) {
                ((MultiFingerTapGestureDetector)detector).setMultiFingerTapMovementThresholdResource(R.dimen.mapbox_defaultMultiTapMovementThreshold);
                ((MultiFingerTapGestureDetector)detector).setMultiFingerTapTimeThreshold(150L);
            }
            if (detector instanceof RotateGestureDetector)
                ((RotateGestureDetector)detector).setAngleThreshold(15.3F);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean isHandled = false;
        for (BaseGesture detector : this.detectors) {
            if (detector.onTouchEvent(motionEvent))
                isHandled = true;
        }
        return isHandled;
    }

    public void setStandardGestureListener(StandardGestureDetector.StandardOnGestureListener listener) {
        this.standardGestureDetector.setListener(listener);
    }

    public void removeStandardGestureListener() {
        this.standardGestureDetector.removeListener();
    }

    public void setStandardScaleGestureListener(StandardScaleGestureDetector.StandardOnScaleGestureListener listener) {
        this.standardScaleGestureDetector.setListener(listener);
    }

    public void removeStandardScaleGestureListener() {
        this.standardScaleGestureDetector.removeListener();
    }

    public void setRotateGestureListener(RotateGestureDetector.OnRotateGestureListener listener) {
        this.rotateGestureDetector.setListener(listener);
    }

    public void removeRotateGestureListener() {
        this.rotateGestureDetector.removeListener();
    }

    public void setShoveGestureListener(ShoveGestureDetector.OnShoveGestureListener listener) {
        this.shoveGestureDetector.setListener(listener);
    }

    public void removeShoveGestureListener() {
        this.shoveGestureDetector.removeListener();
    }

    public void setMultiFingerTapGestureListener(MultiFingerTapGestureDetector.OnMultiFingerTapGestureListener listener) {
        this.multiFingerTapGestureDetector.setListener(listener);
    }

    public void removeMultiFingerTapGestureListener() {
        this.multiFingerTapGestureDetector.removeListener();
    }

    public void setMoveGestureListener(MoveGestureDetector.OnMoveGestureListener listener) {
        this.moveGestureDetector.setListener(listener);
    }

    public void removeMoveGestureListener() {
        this.moveGestureDetector.removeListener();
    }

    public void setSidewaysShoveGestureListener(SidewaysShoveGestureDetector.OnSidewaysShoveGestureListener listener) {
        this.sidewaysShoveGestureDetector.setListener(listener);
    }

    public void removeSidewaysShoveGestureListener() {
        this.sidewaysShoveGestureDetector.removeListener();
    }

    public List<BaseGesture> getDetectors() {
        return this.detectors;
    }

    public StandardGestureDetector getStandardGestureDetector() {
        return this.standardGestureDetector;
    }

    public StandardScaleGestureDetector getStandardScaleGestureDetector() {
        return this.standardScaleGestureDetector;
    }

    public RotateGestureDetector getRotateGestureDetector() {
        return this.rotateGestureDetector;
    }

    public ShoveGestureDetector getShoveGestureDetector() {
        return this.shoveGestureDetector;
    }

    public MultiFingerTapGestureDetector getMultiFingerTapGestureDetector() {
        return this.multiFingerTapGestureDetector;
    }

    public MoveGestureDetector getMoveGestureDetector() {
        return this.moveGestureDetector;
    }

    public SidewaysShoveGestureDetector getSidewaysShoveGestureDetector() {
        return this.sidewaysShoveGestureDetector;
    }

    @SafeVarargs
    public final void setMutuallyExclusiveGestures(Set<Integer>... exclusiveGestures) {
        setMutuallyExclusiveGestures(Arrays.asList(exclusiveGestures));
    }

    public void setMutuallyExclusiveGestures(List<Set<Integer>> exclusiveGestures) {
        this.mutuallyExclusiveGestures.clear();
        this.mutuallyExclusiveGestures.addAll(exclusiveGestures);
    }

    public List<Set<Integer>> getMutuallyExclusiveGestures() {
        return this.mutuallyExclusiveGestures;
    }

    @Retention(RetentionPolicy.SOURCE)
    public static @interface GestureType {}
}
