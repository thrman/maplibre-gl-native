/*     */ package com.mapbox.android.gestures;
/*     */ 
/*     */ import android.content.Context;
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
/*     */ public class RotateGestureDetector
/*     */   extends ProgressiveGesture<RotateGestureDetector.OnRotateGestureListener>
/*     */ {
/*  17 */   private static final Set<Integer> handledTypes = new HashSet<>();
/*     */   
/*     */   static {
/*  20 */     handledTypes.add(Integer.valueOf(2));
/*     */   }
/*     */   
/*     */   private float angleThreshold;
/*     */   float deltaSinceStart;
/*     */   float deltaSinceLast;
/*     */   
/*     */   public RotateGestureDetector(Context context, AndroidGesturesManager gesturesManager) {
/*  28 */     super(context, gesturesManager);
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected Set<Integer> provideHandledTypes() {
/*  34 */     return handledTypes;
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
/*     */ 
/*     */   
/*     */   public static class SimpleOnRotateGestureListener
/*     */     implements OnRotateGestureListener
/*     */   {
/*     */     public boolean onRotateBegin(@NonNull RotateGestureDetector detector) {
/*  77 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean onRotate(@NonNull RotateGestureDetector detector, float rotationDegreesSinceLast, float rotationDegreesSinceFirst) {
/*  83 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onRotateEnd(@NonNull RotateGestureDetector detector, float velocityX, float velocityY, float angularVelocity) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean analyzeMovement() {
/*  97 */     super.analyzeMovement();
/*     */     
/*  99 */     this.deltaSinceLast = getRotationDegreesSinceLast();
/* 100 */     this.deltaSinceStart += this.deltaSinceLast;
/*     */     
/* 102 */     if (isInProgress() && this.deltaSinceLast != 0.0F)
/* 103 */       return this.listener.onRotate(this, this.deltaSinceLast, this.deltaSinceStart); 
/* 104 */     if (canExecute(2) && 
/* 105 */       this.listener.onRotateBegin(this)) {
/* 106 */       gestureStarted();
/* 107 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canExecute(int invokedGestureType) {
/* 116 */     return (Math.abs(this.deltaSinceStart) >= this.angleThreshold && super.canExecute(invokedGestureType));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void gestureStopped() {
/* 121 */     super.gestureStopped();
/*     */     
/* 123 */     if (this.deltaSinceLast == 0.0F) {
/* 124 */       this.velocityX = 0.0F;
/* 125 */       this.velocityY = 0.0F;
/*     */     } 
/*     */     
/* 128 */     float angularVelocity = calculateAngularVelocityVector(this.velocityX, this.velocityY);
/* 129 */     this.listener.onRotateEnd(this, this.velocityX, this.velocityY, angularVelocity);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void reset() {
/* 134 */     super.reset();
/* 135 */     this.deltaSinceStart = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   float getRotationDegreesSinceLast() {
/* 140 */     MultiFingerDistancesObject distancesObject = this.pointersDistanceMap.get(new PointerDistancePair(this.pointerIdList.get(0), this.pointerIdList.get(1)));
/*     */ 
/*     */     
/* 143 */     double diffRadians = Math.atan2(distancesObject.getPrevFingersDiffY(), distancesObject.getPrevFingersDiffX()) - Math.atan2(distancesObject
/* 144 */         .getCurrFingersDiffY(), distancesObject
/* 145 */         .getCurrFingersDiffX());
/* 146 */     return (float)Math.toDegrees(diffRadians);
/*     */   }
/*     */   
/*     */   float calculateAngularVelocityVector(float velocityX, float velocityY) {
/* 150 */     float angularVelocity = Math.abs(
/* 151 */         (float)(((getFocalPoint()).x * velocityY + (getFocalPoint()).y * velocityX) / (Math.pow((getFocalPoint()).x, 2.0D) + Math.pow((getFocalPoint()).y, 2.0D))));
/*     */     
/* 153 */     if (this.deltaSinceLast < 0.0F) {
/* 154 */       angularVelocity = -angularVelocity;
/*     */     }
/*     */     
/* 157 */     return angularVelocity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDeltaSinceStart() {
/* 166 */     return this.deltaSinceStart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDeltaSinceLast() {
/* 176 */     return this.deltaSinceLast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAngleThreshold() {
/* 186 */     return this.angleThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAngleThreshold(float angleThreshold) {
/* 196 */     this.angleThreshold = angleThreshold;
/*     */   }
/*     */   
/*     */   public static interface OnRotateGestureListener {
/*     */     boolean onRotateBegin(@NonNull RotateGestureDetector param1RotateGestureDetector);
/*     */     
/*     */     boolean onRotate(@NonNull RotateGestureDetector param1RotateGestureDetector, float param1Float1, float param1Float2);
/*     */     
/*     */     void onRotateEnd(@NonNull RotateGestureDetector param1RotateGestureDetector, float param1Float1, float param1Float2, float param1Float3);
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\classes.jar!\com\mapbox\android\gestures\RotateGestureDetector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */