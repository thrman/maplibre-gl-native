/*     */ package com.gomap.android.gestures;
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
/*     */ 
/*     */ @UiThread
/*     */ public class SidewaysShoveGestureDetector
/*     */   extends ProgressiveGesture<SidewaysShoveGestureDetector.OnSidewaysShoveGestureListener>
/*     */ {
/*  19 */   private static final Set<Integer> handledTypes = new HashSet<>();
/*     */   
/*     */   static {
/*  22 */     handledTypes.add(Integer.valueOf(14));
/*     */   }
/*     */   
/*     */   private float maxShoveAngle;
/*     */   private float pixelDeltaThreshold;
/*     */   float deltaPixelsSinceStart;
/*     */   float deltaPixelSinceLast;
/*     */   
/*     */   public SidewaysShoveGestureDetector(Context context, AndroidGesturesManager gesturesManager) {
/*  31 */     super(context, gesturesManager);
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected Set<Integer> provideHandledTypes() {
/*  37 */     return handledTypes;
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
/*     */ 
/*     */   
/*     */   public static class SimpleOnSidewaysShoveGestureListener
/*     */     implements OnSidewaysShoveGestureListener
/*     */   {
/*     */     public boolean onSidewaysShoveBegin(@NonNull SidewaysShoveGestureDetector detector) {
/*  78 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean onSidewaysShove(@NonNull SidewaysShoveGestureDetector detector, float deltaPixelsSinceLast, float deltaPixelsSinceStart) {
/*  84 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onSidewaysShoveEnd(@NonNull SidewaysShoveGestureDetector detector, float velocityX, float velocityY) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean analyzeMovement() {
/*  95 */     super.analyzeMovement();
/*     */     
/*  97 */     this.deltaPixelSinceLast = calculateDeltaPixelsSinceLast();
/*  98 */     this.deltaPixelsSinceStart += this.deltaPixelSinceLast;
/*     */     
/* 100 */     if (isInProgress() && this.deltaPixelSinceLast != 0.0F)
/* 101 */       return this.listener.onSidewaysShove(this, this.deltaPixelSinceLast, this.deltaPixelsSinceStart); 
/* 102 */     if (canExecute(14) && 
/* 103 */       this.listener.onSidewaysShoveBegin(this)) {
/* 104 */       gestureStarted();
/* 105 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 109 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canExecute(int invokedGestureType) {
/* 114 */     return (Math.abs(this.deltaPixelsSinceStart) >= this.pixelDeltaThreshold && super
/* 115 */       .canExecute(invokedGestureType));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isSloppyGesture() {
/* 120 */     return (super.isSloppyGesture() || !isAngleAcceptable());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void gestureStopped() {
/* 125 */     super.gestureStopped();
/* 126 */     this.listener.onSidewaysShoveEnd(this, this.velocityX, this.velocityY);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void reset() {
/* 131 */     super.reset();
/* 132 */     this.deltaPixelsSinceStart = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isAngleAcceptable() {
/* 137 */     MultiFingerDistancesObject distancesObject = this.pointersDistanceMap.get(new PointerDistancePair(this.pointerIdList.get(0), this.pointerIdList.get(1)));
/*     */ 
/*     */     
/* 140 */     double angle = Math.toDegrees(Math.abs(Math.atan2(distancesObject
/* 141 */             .getCurrFingersDiffY(), distancesObject.getCurrFingersDiffX())));
/*     */ 
/*     */     
/* 144 */     angle = Math.abs(angle - 90.0D);
/*     */     
/* 146 */     return (angle <= this.maxShoveAngle);
/*     */   }
/*     */   
/*     */   float calculateDeltaPixelsSinceLast() {
/* 150 */     float px0 = getPreviousEvent().getX(getPreviousEvent().findPointerIndex(((Integer)this.pointerIdList.get(0)).intValue()));
/* 151 */     float px1 = getPreviousEvent().getX(getPreviousEvent().findPointerIndex(((Integer)this.pointerIdList.get(1)).intValue()));
/* 152 */     float prevAverageX = (px0 + px1) / 2.0F;
/*     */     
/* 154 */     float cx0 = getCurrentEvent().getX(getCurrentEvent().findPointerIndex(((Integer)this.pointerIdList.get(0)).intValue()));
/* 155 */     float cx1 = getCurrentEvent().getX(getCurrentEvent().findPointerIndex(((Integer)this.pointerIdList.get(1)).intValue()));
/* 156 */     float currAverageX = (cx0 + cx1) / 2.0F;
/*     */     
/* 158 */     return currAverageX - prevAverageX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDeltaPixelsSinceStart() {
/* 167 */     return this.deltaPixelsSinceStart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDeltaPixelSinceLast() {
/* 177 */     return this.deltaPixelSinceLast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPixelDeltaThreshold() {
/* 186 */     return this.pixelDeltaThreshold;
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
/* 197 */     this.pixelDeltaThreshold = pixelDeltaThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPixelDeltaThresholdResource(@DimenRes int pixelDeltaThresholdDimen) {
/* 206 */     setPixelDeltaThreshold(this.context.getResources().getDimension(pixelDeltaThresholdDimen));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxShoveAngle() {
/* 216 */     return this.maxShoveAngle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxShoveAngle(float maxShoveAngle) {
/* 226 */     this.maxShoveAngle = maxShoveAngle;
/*     */   }
/*     */   
/*     */   public static interface OnSidewaysShoveGestureListener {
/*     */     boolean onSidewaysShoveBegin(@NonNull SidewaysShoveGestureDetector param1SidewaysShoveGestureDetector);
/*     */     
/*     */     boolean onSidewaysShove(@NonNull SidewaysShoveGestureDetector param1SidewaysShoveGestureDetector, float param1Float1, float param1Float2);
/*     */     
/*     */     void onSidewaysShoveEnd(@NonNull SidewaysShoveGestureDetector param1SidewaysShoveGestureDetector, float param1Float1, float param1Float2);
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\classes.jar!\com\mapbox\android\gestures\SidewaysShoveGestureDetector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */