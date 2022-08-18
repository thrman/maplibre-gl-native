/*     */ package com.gomap.android.gestures;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.PointF;
/*     */ import android.view.GestureDetector;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.DimenRes;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.UiThread;
/*     */ import androidx.core.view.GestureDetectorCompat;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @UiThread
/*     */ public class StandardScaleGestureDetector
/*     */   extends ProgressiveGesture<StandardScaleGestureDetector.StandardOnScaleGestureListener>
/*     */ {
/*  24 */   private static final Set<Integer> handledTypes = new HashSet<>();
/*     */   
/*     */   static {
/*  27 */     handledTypes.add(Integer.valueOf(1));
/*  28 */     handledTypes.add(Integer.valueOf(15));
/*     */   }
/*     */ 
/*     */   
/*     */   private static final float QUICK_SCALE_MULTIPLIER = 0.5F;
/*     */   
/*     */   private final GestureDetectorCompat innerGestureDetector;
/*     */   
/*     */   private boolean quickScale;
/*     */   private PointF quickScaleFocalPoint;
/*     */   private float startSpan;
/*     */   private float startSpanX;
/*     */   private float startSpanY;
/*     */   private float currentSpan;
/*     */   private float currentSpanX;
/*     */   private float currentSpanY;
/*     */   private float previousSpan;
/*     */   private float previousSpanX;
/*     */   private float previousSpanY;
/*     */   private float spanDeltaSinceStart;
/*     */   private float spanSinceStartThreshold;
/*     */   private boolean isScalingOut;
/*     */   private float scaleFactor;
/*     */   
/*     */   public StandardScaleGestureDetector(Context context, AndroidGesturesManager androidGesturesManager) {
/*  53 */     super(context, androidGesturesManager);
/*  54 */     GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener()
/*     */       {
/*     */         public boolean onDoubleTapEvent(MotionEvent event) {
/*  57 */           if (event.getActionMasked() == 0) {
/*  58 */             StandardScaleGestureDetector.this.quickScale = true;
/*  59 */             StandardScaleGestureDetector.this.quickScaleFocalPoint = new PointF(event.getX(), event.getY());
/*     */           } 
/*  61 */           return true;
/*     */         }
/*     */       };
/*  64 */     this.innerGestureDetector = new GestureDetectorCompat(context, (GestureDetector.OnGestureListener)simpleOnGestureListener);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean analyzeMovement() {
/*  69 */     super.analyzeMovement();
/*     */     
/*  71 */     if (isInProgress() && this.quickScale && getPointersCount() > 1) {
/*     */ 
/*     */       
/*  74 */       gestureStopped();
/*  75 */       return false;
/*     */     } 
/*     */     
/*  78 */     PointF focal = this.quickScale ? this.quickScaleFocalPoint : getFocalPoint();
/*     */     
/*  80 */     this.currentSpanX = 0.0F;
/*  81 */     this.currentSpanY = 0.0F;
/*  82 */     for (int i = 0; i < getPointersCount(); i++) {
/*  83 */       this.currentSpanX += Math.abs(getCurrentEvent().getX(i) - focal.x);
/*  84 */       this.currentSpanY += Math.abs(getCurrentEvent().getY(i) - focal.y);
/*     */     } 
/*  86 */     this.currentSpanX *= 2.0F;
/*  87 */     this.currentSpanY *= 2.0F;
/*     */     
/*  89 */     if (this.quickScale) {
/*  90 */       this.currentSpan = this.currentSpanY;
/*     */     } else {
/*  92 */       this.currentSpan = (float)Math.hypot(this.currentSpanX, this.currentSpanY);
/*     */     } 
/*     */     
/*  95 */     if (this.startSpan == 0.0F) {
/*  96 */       this.startSpan = this.currentSpan;
/*  97 */       this.startSpanX = this.currentSpanX;
/*  98 */       this.startSpanY = this.currentSpanY;
/*     */     } 
/*     */     
/* 101 */     this.spanDeltaSinceStart = Math.abs(this.startSpan - this.currentSpan);
/*     */     
/* 103 */     this.scaleFactor = calculateScaleFactor();
/* 104 */     this.isScalingOut = (this.scaleFactor < 1.0F);
/*     */     
/* 106 */     boolean handled = false;
/* 107 */     if (isInProgress() && this.currentSpan > 0.0F) {
/* 108 */       handled = this.listener.onScale(this);
/* 109 */     } else if (canExecute(this.quickScale ? 15 : 1) && this.spanDeltaSinceStart >= this.spanSinceStartThreshold) {
/*     */       
/* 111 */       handled = this.listener.onScaleBegin(this);
/* 112 */       if (handled) {
/* 113 */         gestureStarted();
/*     */       }
/*     */     } 
/* 116 */     this.previousSpan = this.currentSpan;
/* 117 */     this.previousSpanX = this.currentSpanX;
/* 118 */     this.previousSpanY = this.currentSpanY;
/* 119 */     return handled;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void gestureStopped() {
/* 124 */     super.gestureStopped();
/* 125 */     this.listener.onScaleEnd(this, this.velocityX, this.velocityY);
/* 126 */     this.quickScale = false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void reset() {
/* 131 */     super.reset();
/* 132 */     this.startSpan = 0.0F;
/* 133 */     this.spanDeltaSinceStart = 0.0F;
/* 134 */     this.currentSpan = 0.0F;
/* 135 */     this.previousSpan = 0.0F;
/* 136 */     this.scaleFactor = 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean analyzeEvent(@NonNull MotionEvent motionEvent) {
/* 141 */     int action = motionEvent.getActionMasked();
/*     */     
/* 143 */     if (this.quickScale) {
/* 144 */       if (action == 5 || action == 3) {
/* 145 */         if (isInProgress()) {
/* 146 */           interrupt();
/*     */         }
/*     */         else {
/*     */           
/* 150 */           this.quickScale = false;
/*     */         } 
/* 152 */       } else if (!isInProgress() && action == 1) {
/*     */ 
/*     */         
/* 155 */         this.quickScale = false;
/*     */       } 
/*     */     }
/*     */     
/* 159 */     boolean handled = super.analyzeEvent(motionEvent);
/* 160 */     return handled | this.innerGestureDetector.onTouchEvent(motionEvent);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getRequiredPointersCount() {
/* 165 */     if (isInProgress()) {
/* 166 */       return this.quickScale ? 1 : 2;
/*     */     }
/* 168 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSloppyGesture() {
/* 175 */     return (super.isSloppyGesture() || (!this.quickScale && getPointersCount() < 2));
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected Set<Integer> provideHandledTypes() {
/* 181 */     return handledTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface StandardOnScaleGestureListener
/*     */   {
/*     */     boolean onScaleBegin(@NonNull StandardScaleGestureDetector param1StandardScaleGestureDetector);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean onScale(@NonNull StandardScaleGestureDetector param1StandardScaleGestureDetector);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void onScaleEnd(@NonNull StandardScaleGestureDetector param1StandardScaleGestureDetector, float param1Float1, float param1Float2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SimpleStandardOnScaleGestureListener
/*     */     implements StandardOnScaleGestureListener
/*     */   {
/*     */     public boolean onScaleBegin(@NonNull StandardScaleGestureDetector detector) {
/* 219 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean onScale(@NonNull StandardScaleGestureDetector detector) {
/* 224 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onScaleEnd(@NonNull StandardScaleGestureDetector detector, float velocityX, float velocityY) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isScalingOut() {
/* 239 */     return this.isScalingOut;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSpanSinceStartThreshold() {
/* 249 */     return this.spanSinceStartThreshold;
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
/*     */   public void setSpanSinceStartThreshold(float spanSinceStartThreshold) {
/* 261 */     this.spanSinceStartThreshold = spanSinceStartThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpanSinceStartThresholdResource(@DimenRes int spanSinceStartThresholdDimen) {
/* 271 */     setSpanSinceStartThreshold(this.context.getResources().getDimension(spanSinceStartThresholdDimen));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getScaleFactor() {
/* 278 */     return this.scaleFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getStartSpan() {
/* 288 */     return this.startSpan;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getStartSpanX() {
/* 298 */     return this.startSpanX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getStartSpanY() {
/* 308 */     return this.startSpanY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCurrentSpan() {
/* 318 */     return this.currentSpan;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCurrentSpanX() {
/* 328 */     return this.currentSpanX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCurrentSpanY() {
/* 338 */     return this.currentSpanY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPreviousSpan() {
/* 348 */     return this.previousSpan;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPreviousSpanX() {
/* 358 */     return this.previousSpanX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPreviousSpanY() {
/* 368 */     return this.previousSpanY;
/*     */   }
/*     */   
/*     */   private float calculateScaleFactor() {
/* 372 */     if (this.quickScale) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 377 */       boolean scaleOut = ((getCurrentEvent().getY() < this.quickScaleFocalPoint.y && this.currentSpan < this.previousSpan) || (getCurrentEvent().getY() > this.quickScaleFocalPoint.y && this.currentSpan > this.previousSpan));
/* 378 */       float spanDiff = Math.abs(1.0F - this.currentSpan / this.previousSpan) * 0.5F;
/* 379 */       return (this.previousSpan <= 0.0F) ? 1.0F : (scaleOut ? (1.0F + spanDiff) : (1.0F - spanDiff));
/*     */     } 
/* 381 */     return (this.previousSpan > 0.0F) ? (this.currentSpan / this.previousSpan) : 1.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\classes.jar!\com\mapbox\android\gestures\StandardScaleGestureDetector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */