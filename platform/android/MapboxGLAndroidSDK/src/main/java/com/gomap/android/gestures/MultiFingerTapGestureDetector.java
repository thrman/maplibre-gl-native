/*     */ package com.gomap.android.gestures;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.DimenRes;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.UiThread;
/*     */ import java.util.HashMap;
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
/*     */ public class MultiFingerTapGestureDetector
/*     */   extends MultiFingerGesture<MultiFingerTapGestureDetector.OnMultiFingerTapGestureListener>
/*     */ {
/*     */   private long multiFingerTapTimeThreshold;
/*     */   private float multiFingerTapMovementThreshold;
/*     */   private boolean invalidMovement;
/*     */   private boolean pointerLifted;
/*     */   private int lastPointersDownCount;
/*     */   
/*     */   public MultiFingerTapGestureDetector(Context context, AndroidGesturesManager gesturesManager) {
/*  36 */     super(context, gesturesManager);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean analyzeEvent(@NonNull MotionEvent motionEvent) {
/*     */     boolean canExecute, handled;
/*  45 */     super.analyzeEvent(motionEvent);
/*     */     
/*  47 */     int action = motionEvent.getActionMasked();
/*  48 */     switch (action) {
/*     */       case 5:
/*  50 */         if (this.pointerLifted) {
/*  51 */           this.invalidMovement = true;
/*     */         }
/*  53 */         this.lastPointersDownCount = this.pointerIdList.size();
/*     */         break;
/*     */       
/*     */       case 6:
/*  57 */         this.pointerLifted = true;
/*     */         break;
/*     */       
/*     */       case 1:
/*  61 */         canExecute = canExecute(4);
/*  62 */         handled = false;
/*  63 */         if (canExecute) {
/*  64 */           handled = this.listener.onMultiFingerTap(this, this.lastPointersDownCount);
/*     */         }
/*  66 */         reset();
/*  67 */         return handled;
/*     */       
/*     */       case 2:
/*  70 */         if (this.invalidMovement) {
/*     */           break;
/*     */         }
/*  73 */         this.invalidMovement = exceededMovementThreshold(this.pointersDistanceMap);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     return false;
/*     */   }
/*     */   
/*     */   boolean exceededMovementThreshold(HashMap<PointerDistancePair, MultiFingerDistancesObject> map) {
/*  84 */     for (MultiFingerDistancesObject distancesObject : map.values()) {
/*  85 */       float diffX = Math.abs(distancesObject.getCurrFingersDiffX() - distancesObject.getPrevFingersDiffX());
/*  86 */       float diffY = Math.abs(distancesObject.getCurrFingersDiffY() - distancesObject.getPrevFingersDiffY());
/*     */       
/*  88 */       this.invalidMovement = (diffX > this.multiFingerTapMovementThreshold || diffY > this.multiFingerTapMovementThreshold);
/*     */       
/*  90 */       if (this.invalidMovement) {
/*  91 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canExecute(int invokedGestureType) {
/* 100 */     return (this.lastPointersDownCount > 1 && !this.invalidMovement && getGestureDuration() < this.multiFingerTapTimeThreshold && super
/* 101 */       .canExecute(invokedGestureType));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void reset() {
/* 106 */     super.reset();
/* 107 */     this.lastPointersDownCount = 0;
/* 108 */     this.invalidMovement = false;
/* 109 */     this.pointerLifted = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMultiFingerTapTimeThreshold() {
/* 118 */     return this.multiFingerTapTimeThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMultiFingerTapTimeThreshold(long multiFingerTapTimeThreshold) {
/* 127 */     this.multiFingerTapTimeThreshold = multiFingerTapTimeThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMultiFingerTapMovementThreshold() {
/* 136 */     return this.multiFingerTapMovementThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMultiFingerTapMovementThreshold(float multiFingerTapMovementThreshold) {
/* 147 */     this.multiFingerTapMovementThreshold = multiFingerTapMovementThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMultiFingerTapMovementThresholdResource(@DimenRes int multiFingerTapMovementThresholdDimen) {
/* 156 */     setMultiFingerTapMovementThreshold(this.context.getResources().getDimension(multiFingerTapMovementThresholdDimen));
/*     */   }
/*     */   
/*     */   public static interface OnMultiFingerTapGestureListener {
/*     */     boolean onMultiFingerTap(@NonNull MultiFingerTapGestureDetector param1MultiFingerTapGestureDetector, int param1Int);
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\classes.jar!\com\mapbox\android\gestures\MultiFingerTapGestureDetector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */