/*     */ package com.mapbox.android.gestures;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.PointF;
/*     */ import android.graphics.RectF;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.DimenRes;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.annotation.UiThread;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class MoveGestureDetector
/*     */   extends ProgressiveGesture<MoveGestureDetector.OnMoveGestureListener>
/*     */ {
/*     */   private static final int MOVE_REQUIRED_POINTERS_COUNT = 1;
/*  34 */   private static final Set<Integer> handledTypes = new HashSet<>();
/*     */   
/*     */   private PointF previousFocalPoint;
/*     */   private boolean resetFocal;
/*     */   float lastDistanceX;
/*     */   
/*     */   static {
/*  41 */     handledTypes.add(Integer.valueOf(13));
/*     */   }
/*     */   
/*     */   float lastDistanceY;
/*     */   @Nullable
/*     */   private RectF moveThresholdRect;
/*     */   private float moveThreshold;
/*  48 */   private final Map<Integer, MoveDistancesObject> moveDistancesObjectMap = new HashMap<>();
/*     */   
/*     */   public MoveGestureDetector(Context context, AndroidGesturesManager gesturesManager) {
/*  51 */     super(context, gesturesManager);
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected Set<Integer> provideHandledTypes() {
/*  57 */     return handledTypes;
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
/*     */   public static class SimpleOnMoveGestureListener
/*     */     implements OnMoveGestureListener
/*     */   {
/*     */     public boolean onMoveBegin(@NonNull MoveGestureDetector detector) {
/*  94 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean onMove(@NonNull MoveGestureDetector detector, float distanceX, float distanceY) {
/*  99 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onMoveEnd(@NonNull MoveGestureDetector detector, float velocityX, float velocityY) {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean analyzeEvent(@NonNull MotionEvent motionEvent) {
/*     */     float x;
/*     */     float y;
/* 110 */     switch (motionEvent.getActionMasked()) {
/*     */       case 0:
/*     */       case 5:
/* 113 */         this.resetFocal = true;
/*     */         
/* 115 */         x = motionEvent.getX(motionEvent.getActionIndex());
/* 116 */         y = motionEvent.getY(motionEvent.getActionIndex());
/* 117 */         this.moveDistancesObjectMap.put(
/* 118 */             Integer.valueOf(motionEvent.getPointerId(motionEvent.getActionIndex())), new MoveDistancesObject(x, y));
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 124 */         this.moveDistancesObjectMap.clear();
/*     */         break;
/*     */       
/*     */       case 6:
/* 128 */         this.resetFocal = true;
/*     */         
/* 130 */         this.moveDistancesObjectMap.remove(Integer.valueOf(motionEvent.getPointerId(motionEvent.getActionIndex())));
/*     */         break;
/*     */       
/*     */       case 3:
/* 134 */         this.moveDistancesObjectMap.clear();
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     return super.analyzeEvent(motionEvent);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean analyzeMovement() {
/* 146 */     super.analyzeMovement();
/* 147 */     updateMoveDistancesObjects();
/*     */     
/* 149 */     if (isInProgress()) {
/* 150 */       PointF currentFocalPoint = getFocalPoint();
/* 151 */       this.lastDistanceX = this.previousFocalPoint.x - currentFocalPoint.x;
/* 152 */       this.lastDistanceY = this.previousFocalPoint.y - currentFocalPoint.y;
/* 153 */       this.previousFocalPoint = currentFocalPoint;
/* 154 */       if (this.resetFocal) {
/* 155 */         this.resetFocal = false;
/* 156 */         return this.listener.onMove(this, 0.0F, 0.0F);
/*     */       } 
/* 158 */       return this.listener.onMove(this, this.lastDistanceX, this.lastDistanceY);
/* 159 */     }  if (canExecute(13) && 
/* 160 */       this.listener.onMoveBegin(this)) {
/* 161 */       gestureStarted();
/* 162 */       this.previousFocalPoint = getFocalPoint();
/* 163 */       this.resetFocal = false;
/* 164 */       return true;
/*     */     } 
/*     */     
/* 167 */     return false;
/*     */   }
/*     */   
/*     */   private void updateMoveDistancesObjects() {
/* 171 */     for (Iterator<Integer> iterator = this.pointerIdList.iterator(); iterator.hasNext(); ) { int pointerId = ((Integer)iterator.next()).intValue();
/* 172 */       ((MoveDistancesObject)this.moveDistancesObjectMap.get(Integer.valueOf(pointerId))).addNewPosition(
/* 173 */           getCurrentEvent().getX(getCurrentEvent().findPointerIndex(pointerId)), 
/* 174 */           getCurrentEvent().getY(getCurrentEvent().findPointerIndex(pointerId))); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   boolean checkAnyMoveAboveThreshold() {
/* 180 */     Iterator<MoveDistancesObject> iterator = this.moveDistancesObjectMap.values().iterator(); if (iterator.hasNext()) { MoveDistancesObject moveDistancesObject = iterator.next();
/*     */       
/* 182 */       boolean thresholdExceeded = (Math.abs(moveDistancesObject.getDistanceXSinceStart()) >= this.moveThreshold || Math.abs(moveDistancesObject.getDistanceYSinceStart()) >= this.moveThreshold);
/*     */       
/* 184 */       boolean isInRect = (this.moveThresholdRect != null && this.moveThresholdRect.contains((getFocalPoint()).x, (getFocalPoint()).y));
/* 185 */       return (!isInRect && thresholdExceeded); }
/*     */     
/* 187 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canExecute(int invokedGestureType) {
/* 192 */     return (super.canExecute(invokedGestureType) && checkAnyMoveAboveThreshold());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void reset() {
/* 197 */     super.reset();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void gestureStopped() {
/* 202 */     super.gestureStopped();
/* 203 */     this.listener.onMoveEnd(this, this.velocityX, this.velocityY);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getRequiredPointersCount() {
/* 208 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMoveThreshold() {
/* 218 */     return this.moveThreshold;
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
/*     */   public void setMoveThreshold(float moveThreshold) {
/* 230 */     this.moveThreshold = moveThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public RectF getMoveThresholdRect() {
/* 242 */     return this.moveThresholdRect;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMoveThresholdRect(@Nullable RectF moveThresholdRect) {
/* 253 */     this.moveThresholdRect = moveThresholdRect;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMoveThresholdResource(@DimenRes int moveThresholdDimen) {
/* 262 */     setMoveThreshold(this.context.getResources().getDimension(moveThresholdDimen));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLastDistanceX() {
/* 272 */     return this.lastDistanceX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLastDistanceY() {
/* 282 */     return this.lastDistanceY;
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
/*     */   public MoveDistancesObject getMoveObject(int pointerIndex) {
/* 297 */     if (isInProgress() && 
/* 298 */       pointerIndex >= 0 && pointerIndex < getPointersCount()) {
/* 299 */       return this.moveDistancesObjectMap.get(this.pointerIdList.get(pointerIndex));
/*     */     }
/*     */     
/* 302 */     return null;
/*     */   }
/*     */   
/*     */   public static interface OnMoveGestureListener {
/*     */     boolean onMoveBegin(@NonNull MoveGestureDetector param1MoveGestureDetector);
/*     */     
/*     */     boolean onMove(@NonNull MoveGestureDetector param1MoveGestureDetector, float param1Float1, float param1Float2);
/*     */     
/*     */     void onMoveEnd(@NonNull MoveGestureDetector param1MoveGestureDetector, float param1Float1, float param1Float2);
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\classes.jar!\com\mapbox\android\gestures\MoveGestureDetector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */