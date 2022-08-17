/*     */ package com.mapbox.android.gestures;
/*     */ 
/*     */ import android.content.Context;
/*     */ import androidx.annotation.DimenRes;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.UiThread;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @UiThread
/*     */ public class ShoveGestureDetector
/*     */   extends ProgressiveGesture<ShoveGestureDetector.OnShoveGestureListener>
/*     */ {
/*  18 */   private static final Set<Integer> handledTypes = new HashSet<>();
/*     */   
/*     */   static {
/*  21 */     handledTypes.add(Integer.valueOf(3));
/*     */   }
/*     */   
/*     */   private float maxShoveAngle;
/*     */   private float pixelDeltaThreshold;
/*     */   float deltaPixelsSinceStart;
/*     */   float deltaPixelSinceLast;
/*     */   
/*     */   public ShoveGestureDetector(Context context, AndroidGesturesManager gesturesManager) {
/*  30 */     super(context, gesturesManager);
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected Set<Integer> provideHandledTypes() {
/*  36 */     return handledTypes;
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
/*     */   public static class SimpleOnShoveGestureListener
/*     */     implements OnShoveGestureListener
/*     */   {
/*     */     public boolean onShoveBegin(@NonNull ShoveGestureDetector detector) {
/*  75 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean onShove(@NonNull ShoveGestureDetector detector, float deltaPixelsSinceLast, float deltaPixelsSinceStart) {
/*  82 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onShoveEnd(@NonNull ShoveGestureDetector detector, float velocityX, float velocityY) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean analyzeMovement() {
/*  93 */     super.analyzeMovement();
/*     */     
/*  95 */     this.deltaPixelSinceLast = calculateDeltaPixelsSinceLast();
/*  96 */     this.deltaPixelsSinceStart += this.deltaPixelSinceLast;
/*     */     
/*  98 */     if (isInProgress() && this.deltaPixelSinceLast != 0.0F)
/*  99 */       return this.listener.onShove(this, this.deltaPixelSinceLast, this.deltaPixelsSinceStart); 
/* 100 */     if (canExecute(3) && 
/* 101 */       this.listener.onShoveBegin(this)) {
/* 102 */       gestureStarted();
/* 103 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canExecute(int invokedGestureType) {
/* 112 */     return (Math.abs(this.deltaPixelsSinceStart) >= this.pixelDeltaThreshold && super
/* 113 */       .canExecute(invokedGestureType));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isSloppyGesture() {
/* 118 */     return (super.isSloppyGesture() || !isAngleAcceptable());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void gestureStopped() {
/* 123 */     super.gestureStopped();
/* 124 */     this.listener.onShoveEnd(this, this.velocityX, this.velocityY);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void reset() {
/* 129 */     super.reset();
/* 130 */     this.deltaPixelsSinceStart = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isAngleAcceptable() {
/* 135 */     MultiFingerDistancesObject distancesObject = this.pointersDistanceMap.get(new PointerDistancePair(this.pointerIdList.get(0), this.pointerIdList.get(1)));
/*     */ 
/*     */     
/* 138 */     double angle = Math.toDegrees(Math.abs(Math.atan2(distancesObject
/* 139 */             .getCurrFingersDiffY(), distancesObject.getCurrFingersDiffX())));
/*     */     
/* 141 */     return (angle <= this.maxShoveAngle || 180.0D - angle <= this.maxShoveAngle);
/*     */   }
/*     */   
/*     */   float calculateDeltaPixelsSinceLast() {
/* 145 */     float py0 = getPreviousEvent().getY(getPreviousEvent().findPointerIndex(((Integer)this.pointerIdList.get(0)).intValue()));
/* 146 */     float py1 = getPreviousEvent().getY(getPreviousEvent().findPointerIndex(((Integer)this.pointerIdList.get(1)).intValue()));
/* 147 */     float prevAverageY = (py0 + py1) / 2.0F;
/*     */     
/* 149 */     float cy0 = getCurrentEvent().getY(getCurrentEvent().findPointerIndex(((Integer)this.pointerIdList.get(0)).intValue()));
/* 150 */     float cy1 = getCurrentEvent().getY(getCurrentEvent().findPointerIndex(((Integer)this.pointerIdList.get(1)).intValue()));
/* 151 */     float currAverageY = (cy0 + cy1) / 2.0F;
/*     */     
/* 153 */     return currAverageY - prevAverageY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDeltaPixelsSinceStart() {
/* 162 */     return this.deltaPixelsSinceStart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDeltaPixelSinceLast() {
/* 172 */     return this.deltaPixelSinceLast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPixelDeltaThreshold() {
/* 181 */     return this.pixelDeltaThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPixelDeltaThreshold(float pixelDeltaThreshold) {
/* 192 */     this.pixelDeltaThreshold = pixelDeltaThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPixelDeltaThresholdResource(@DimenRes int pixelDeltaThresholdDimen) {
/* 201 */     setPixelDeltaThreshold(this.context.getResources().getDimension(pixelDeltaThresholdDimen));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxShoveAngle() {
/* 210 */     return this.maxShoveAngle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxShoveAngle(float maxShoveAngle) {
/* 219 */     this.maxShoveAngle = maxShoveAngle;
/*     */   }
/*     */   
/*     */   public static interface OnShoveGestureListener {
/*     */     boolean onShoveBegin(@NonNull ShoveGestureDetector param1ShoveGestureDetector);
/*     */     
/*     */     boolean onShove(@NonNull ShoveGestureDetector param1ShoveGestureDetector, float param1Float1, float param1Float2);
/*     */     
/*     */     void onShoveEnd(@NonNull ShoveGestureDetector param1ShoveGestureDetector, float param1Float1, float param1Float2);
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\classes.jar!\com\mapbox\android\gestures\ShoveGestureDetector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */