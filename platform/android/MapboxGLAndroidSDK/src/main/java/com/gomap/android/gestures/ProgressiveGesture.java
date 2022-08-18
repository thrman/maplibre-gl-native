/*     */ package com.gomap.android.gestures;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.view.MotionEvent;
/*     */ import android.view.VelocityTracker;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.UiThread;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @UiThread
/*     */ public abstract class ProgressiveGesture<L>
/*     */   extends MultiFingerGesture<L>
/*     */ {
/*  19 */   private final Set<Integer> handledTypes = provideHandledTypes();
/*     */   
/*     */   private boolean isInProgress;
/*     */   
/*     */   private boolean interrupted;
/*     */   VelocityTracker velocityTracker;
/*     */   float velocityX;
/*     */   float velocityY;
/*     */   
/*     */   public ProgressiveGesture(Context context, AndroidGesturesManager gesturesManager) {
/*  29 */     super(context, gesturesManager);
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected abstract Set<Integer> provideHandledTypes();
/*     */   
/*     */   protected boolean analyzeEvent(@NonNull MotionEvent motionEvent) {
/*  37 */     int action = motionEvent.getActionMasked();
/*  38 */     if (action == 0 || action == 5 || action == 6 || action == 3)
/*     */     {
/*     */ 
/*     */       
/*  42 */       reset();
/*     */     }
/*     */     
/*  45 */     if (this.interrupted) {
/*  46 */       this.interrupted = false;
/*  47 */       reset();
/*  48 */       gestureStopped();
/*     */     } 
/*     */     
/*  51 */     if (this.velocityTracker != null) {
/*  52 */       this.velocityTracker.addMovement(getCurrentEvent());
/*     */     }
/*     */     
/*  55 */     boolean movementHandled = super.analyzeEvent(motionEvent);
/*     */     
/*  57 */     if (action == 1 || action == 6) {
/*  58 */       if (this.pointerIdList.size() < getRequiredPointersCount() && this.isInProgress) {
/*  59 */         gestureStopped();
/*  60 */         return true;
/*     */       } 
/*  62 */     } else if (action == 3 && 
/*  63 */       this.isInProgress) {
/*  64 */       gestureStopped();
/*  65 */       return true;
/*     */     } 
/*     */ 
/*     */     
/*  69 */     return movementHandled;
/*     */   }
/*     */   
/*     */   protected void gestureStarted() {
/*  73 */     this.isInProgress = true;
/*  74 */     if (this.velocityTracker == null) {
/*  75 */       this.velocityTracker = VelocityTracker.obtain();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void gestureStopped() {
/*  80 */     this.isInProgress = false;
/*  81 */     if (this.velocityTracker != null) {
/*  82 */       this.velocityTracker.computeCurrentVelocity(1000);
/*  83 */       this.velocityX = this.velocityTracker.getXVelocity();
/*  84 */       this.velocityY = this.velocityTracker.getYVelocity();
/*  85 */       this.velocityTracker.recycle();
/*  86 */       this.velocityTracker = null;
/*     */     } 
/*  88 */     reset();
/*     */   }
/*     */   
/*     */   Set<Integer> getHandledTypes() {
/*  92 */     return this.handledTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInProgress() {
/* 101 */     return this.isInProgress;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 106 */     super.setEnabled(enabled);
/* 107 */     if (!enabled) {
/* 108 */       interrupt();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interrupt() {
/* 117 */     if (isInProgress())
/* 118 */       this.interrupted = true; 
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\classes.jar!\com\mapbox\android\gestures\ProgressiveGesture.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */