/*     */ package com.gomap.android.gestures;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.PointF;
/*     */ import android.os.Build;
/*     */ import android.util.DisplayMetrics;
/*     */ import android.util.Log;
/*     */ import android.view.Display;
/*     */ import android.view.MotionEvent;
/*     */ import android.view.ViewConfiguration;
/*     */ import androidx.annotation.DimenRes;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.UiThread;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @UiThread
/*     */ public abstract class MultiFingerGesture<L>
/*     */   extends BaseGesture<L>
/*     */ {
/*     */   private static final float PRESSURE_THRESHOLD = 0.67F;
/*     */   private static final int DEFAULT_REQUIRED_FINGERS_COUNT = 2;
/*     */   private final float edgeSlop;
/*     */   private float spanThreshold;
/*  47 */   private final PermittedActionsGuard permittedActionsGuard = new PermittedActionsGuard();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   final List<Integer> pointerIdList = new ArrayList<>();
/*  54 */   final HashMap<PointerDistancePair, MultiFingerDistancesObject> pointersDistanceMap = new HashMap<>();
/*  55 */   private PointF focalPoint = new PointF();
/*     */   
/*     */   private DisplayMetrics displayMetrics;
/*     */   
/*     */   public MultiFingerGesture(Context context, AndroidGesturesManager gesturesManager) {
/*  60 */     super(context, gesturesManager);
/*     */     
/*  62 */     ViewConfiguration config = ViewConfiguration.get(context);
/*  63 */     this.edgeSlop = config.getScaledEdgeSlop();
/*  64 */     queryDisplayMetrics();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean analyzeEvent(@NonNull MotionEvent motionEvent) {
/*  69 */     int action = motionEvent.getActionMasked();
/*     */     
/*  71 */     if (action == 0)
/*     */     {
/*  73 */       queryDisplayMetrics();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  78 */     boolean isMissingEvents = (this.permittedActionsGuard.isMissingActions(action, motionEvent.getPointerCount(), this.pointerIdList.size()) || (action == 2 && isMissingPointers(motionEvent)));
/*     */     
/*  80 */     if (isMissingEvents) {
/*     */       
/*  82 */       if (this instanceof ProgressiveGesture && ((ProgressiveGesture)this).isInProgress()) {
/*  83 */         ((ProgressiveGesture)this).gestureStopped();
/*     */       }
/*  85 */       this.pointerIdList.clear();
/*  86 */       this.pointersDistanceMap.clear();
/*     */     } 
/*     */     
/*  89 */     if (!isMissingEvents || action == 0)
/*     */     {
/*     */       
/*  92 */       updatePointerList(motionEvent);
/*     */     }
/*     */     
/*  95 */     this.focalPoint = Utils.determineFocalPoint(motionEvent);
/*     */     
/*  97 */     if (isMissingEvents) {
/*  98 */       Log.w("MultiFingerGesture", "Some MotionEvents were not passed to the library or events from different view trees are merged.");
/*     */       
/* 100 */       return false;
/*     */     } 
/* 102 */     if (action == 2 && 
/* 103 */       this.pointerIdList.size() >= getRequiredPointersCount() && checkPressure()) {
/* 104 */       calculateDistances();
/* 105 */       if (!isSloppyGesture()) {
/* 106 */         return analyzeMovement();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 112 */     return false;
/*     */   }
/*     */   
/*     */   private void queryDisplayMetrics() {
/* 116 */     if (this.windowManager != null) {
/* 117 */       this.displayMetrics = new DisplayMetrics();
/* 118 */       Display display = this.windowManager.getDefaultDisplay();
/* 119 */       if (Build.VERSION.SDK_INT >= 17) {
/*     */ 
/*     */         
/* 122 */         display.getRealMetrics(this.displayMetrics);
/*     */       } else {
/*     */         
/* 125 */         display.getMetrics(this.displayMetrics);
/*     */       } 
/*     */     } else {
/*     */       
/* 129 */       this.displayMetrics = this.context.getResources().getDisplayMetrics();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updatePointerList(MotionEvent motionEvent) {
/* 134 */     int action = motionEvent.getActionMasked();
/*     */     
/* 136 */     if (action == 0 || action == 5) {
/* 137 */       this.pointerIdList.add(Integer.valueOf(motionEvent.getPointerId(motionEvent.getActionIndex())));
/* 138 */     } else if (action == 1 || action == 6) {
/* 139 */       this.pointerIdList.remove(Integer.valueOf(motionEvent.getPointerId(motionEvent.getActionIndex())));
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isMissingPointers(MotionEvent motionEvent) {
/* 144 */     for (Iterator<Integer> iterator = this.pointerIdList.iterator(); iterator.hasNext(); ) { int pointerId = ((Integer)iterator.next()).intValue();
/* 145 */       boolean hasPointer = (motionEvent.findPointerIndex(pointerId) != -1);
/* 146 */       if (!hasPointer) {
/* 147 */         return true;
/*     */       } }
/*     */     
/* 150 */     return false;
/*     */   }
/*     */   
/*     */   boolean checkPressure() {
/* 154 */     float currentPressure = getCurrentEvent().getPressure();
/* 155 */     float previousPressure = getPreviousEvent().getPressure();
/* 156 */     return (currentPressure / previousPressure > 0.67F);
/*     */   }
/*     */   
/*     */   private boolean checkSpanBelowThreshold() {
/* 160 */     for (MultiFingerDistancesObject distancesObject : this.pointersDistanceMap.values()) {
/* 161 */       if (distancesObject.getCurrFingersDiffXY() < this.spanThreshold) {
/* 162 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 166 */     return false;
/*     */   }
/*     */   
/*     */   protected int getRequiredPointersCount() {
/* 170 */     return 2;
/*     */   }
/*     */   
/*     */   protected boolean analyzeMovement() {
/* 174 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSloppyGesture() {
/* 186 */     float rightSlopEdge = this.displayMetrics.widthPixels - this.edgeSlop;
/* 187 */     float bottomSlopEdge = this.displayMetrics.heightPixels - this.edgeSlop;
/*     */     
/* 189 */     float edgeSlop = this.edgeSlop;
/*     */     
/* 191 */     for (Iterator<Integer> iterator = this.pointerIdList.iterator(); iterator.hasNext(); ) { int pointerId = ((Integer)iterator.next()).intValue();
/* 192 */       int pointerIndex = getCurrentEvent().findPointerIndex(pointerId);
/* 193 */       float x = Utils.getRawX(getCurrentEvent(), pointerIndex);
/* 194 */       float y = Utils.getRawY(getCurrentEvent(), pointerIndex);
/*     */       
/* 196 */       boolean isSloppy = (x < edgeSlop || y < edgeSlop || x > rightSlopEdge || y > bottomSlopEdge);
/*     */ 
/*     */       
/* 199 */       if (isSloppy) {
/* 200 */         return true;
/*     */       } }
/*     */ 
/*     */     
/* 204 */     return checkSpanBelowThreshold();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canExecute(int invokedGestureType) {
/* 209 */     return (super.canExecute(invokedGestureType) && !isSloppyGesture());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void reset() {}
/*     */   
/*     */   private void calculateDistances() {
/* 216 */     this.pointersDistanceMap.clear();
/*     */     
/* 218 */     for (int i = 0; i < this.pointerIdList.size() - 1; i++) {
/* 219 */       for (int j = i + 1; j < this.pointerIdList.size(); j++) {
/* 220 */         int primaryPointerId = ((Integer)this.pointerIdList.get(i)).intValue();
/* 221 */         int secondaryPointerId = ((Integer)this.pointerIdList.get(j)).intValue();
/*     */         
/* 223 */         float px0 = getPreviousEvent().getX(getPreviousEvent().findPointerIndex(primaryPointerId));
/* 224 */         float py0 = getPreviousEvent().getY(getPreviousEvent().findPointerIndex(primaryPointerId));
/* 225 */         float px1 = getPreviousEvent().getX(getPreviousEvent().findPointerIndex(secondaryPointerId));
/* 226 */         float py1 = getPreviousEvent().getY(getPreviousEvent().findPointerIndex(secondaryPointerId));
/* 227 */         float prevFingersDiffX = px1 - px0;
/* 228 */         float prevFingersDiffY = py1 - py0;
/*     */         
/* 230 */         float cx0 = getCurrentEvent().getX(getCurrentEvent().findPointerIndex(primaryPointerId));
/* 231 */         float cy0 = getCurrentEvent().getY(getCurrentEvent().findPointerIndex(primaryPointerId));
/* 232 */         float cx1 = getCurrentEvent().getX(getCurrentEvent().findPointerIndex(secondaryPointerId));
/* 233 */         float cy1 = getCurrentEvent().getY(getCurrentEvent().findPointerIndex(secondaryPointerId));
/* 234 */         float currFingersDiffX = cx1 - cx0;
/* 235 */         float currFingersDiffY = cy1 - cy0;
/*     */         
/* 237 */         this.pointersDistanceMap.put(new PointerDistancePair(Integer.valueOf(primaryPointerId), Integer.valueOf(secondaryPointerId)), new MultiFingerDistancesObject(prevFingersDiffX, prevFingersDiffY, currFingersDiffX, currFingersDiffY));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCurrentSpan(int firstPointerIndex, int secondPointerIndex) {
/* 262 */     if (!verifyPointers(firstPointerIndex, secondPointerIndex)) {
/* 263 */       throw new NoSuchElementException("There is no such pair of pointers!");
/*     */     }
/*     */     
/* 266 */     MultiFingerDistancesObject distancesObject = this.pointersDistanceMap.get(new PointerDistancePair(this.pointerIdList
/* 267 */           .get(firstPointerIndex), this.pointerIdList.get(secondPointerIndex)));
/*     */     
/* 269 */     return distancesObject.getCurrFingersDiffXY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPreviousSpan(int firstPointerIndex, int secondPointerIndex) {
/* 288 */     if (!verifyPointers(firstPointerIndex, secondPointerIndex)) {
/* 289 */       throw new NoSuchElementException("There is no such pair of pointers!");
/*     */     }
/*     */     
/* 292 */     MultiFingerDistancesObject distancesObject = this.pointersDistanceMap.get(new PointerDistancePair(this.pointerIdList
/* 293 */           .get(firstPointerIndex), this.pointerIdList.get(secondPointerIndex)));
/*     */     
/* 295 */     return distancesObject.getPrevFingersDiffXY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCurrentSpanX(int firstPointerIndex, int secondPointerIndex) {
/* 313 */     if (!verifyPointers(firstPointerIndex, secondPointerIndex)) {
/* 314 */       throw new NoSuchElementException("There is no such pair of pointers!");
/*     */     }
/*     */     
/* 317 */     MultiFingerDistancesObject distancesObject = this.pointersDistanceMap.get(new PointerDistancePair(this.pointerIdList
/* 318 */           .get(firstPointerIndex), this.pointerIdList.get(secondPointerIndex)));
/*     */     
/* 320 */     return Math.abs(distancesObject.getCurrFingersDiffX());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCurrentSpanY(int firstPointerIndex, int secondPointerIndex) {
/* 338 */     if (!verifyPointers(firstPointerIndex, secondPointerIndex)) {
/* 339 */       throw new NoSuchElementException("There is no such pair of pointers!");
/*     */     }
/*     */     
/* 342 */     MultiFingerDistancesObject distancesObject = this.pointersDistanceMap.get(new PointerDistancePair(this.pointerIdList
/* 343 */           .get(firstPointerIndex), this.pointerIdList.get(secondPointerIndex)));
/*     */     
/* 345 */     return Math.abs(distancesObject.getCurrFingersDiffY());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPreviousSpanX(int firstPointerIndex, int secondPointerIndex) {
/* 363 */     if (!verifyPointers(firstPointerIndex, secondPointerIndex)) {
/* 364 */       throw new NoSuchElementException("There is no such pair of pointers!");
/*     */     }
/*     */     
/* 367 */     MultiFingerDistancesObject distancesObject = this.pointersDistanceMap.get(new PointerDistancePair(this.pointerIdList
/* 368 */           .get(firstPointerIndex), this.pointerIdList.get(secondPointerIndex)));
/*     */     
/* 370 */     return Math.abs(distancesObject.getPrevFingersDiffX());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPreviousSpanY(int firstPointerIndex, int secondPointerIndex) {
/* 388 */     if (!verifyPointers(firstPointerIndex, secondPointerIndex)) {
/* 389 */       throw new NoSuchElementException("There is no such pair of pointers!");
/*     */     }
/*     */     
/* 392 */     MultiFingerDistancesObject distancesObject = this.pointersDistanceMap.get(new PointerDistancePair(this.pointerIdList
/* 393 */           .get(firstPointerIndex), this.pointerIdList.get(secondPointerIndex)));
/*     */     
/* 395 */     return Math.abs(distancesObject.getPrevFingersDiffY());
/*     */   }
/*     */   
/*     */   private boolean verifyPointers(int firstPointerIndex, int secondPointerIndex) {
/* 399 */     return (firstPointerIndex != secondPointerIndex && firstPointerIndex >= 0 && secondPointerIndex >= 0 && firstPointerIndex < 
/* 400 */       getPointersCount() && secondPointerIndex < getPointersCount());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPointersCount() {
/* 409 */     return this.pointerIdList.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointF getFocalPoint() {
/* 418 */     return this.focalPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSpanThreshold() {
/* 427 */     return this.spanThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpanThreshold(float spanThreshold) {
/* 438 */     this.spanThreshold = spanThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpanThresholdResource(@DimenRes int spanThresholdDimen) {
/* 447 */     setSpanThreshold(this.context.getResources().getDimension(spanThresholdDimen));
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\classes.jar!\com\mapbox\android\gestures\MultiFingerGesture.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */