/*     */ package com.gomap.android.gestures;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.view.MotionEvent;
/*     */ import android.view.WindowManager;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.annotation.UiThread;
/*     */ import java.util.Iterator;
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
/*     */ @UiThread
/*     */ public abstract class BaseGesture<L>
/*     */ {
/*     */   protected final Context context;
/*     */   protected final WindowManager windowManager;
/*     */   private final AndroidGesturesManager gesturesManager;
/*     */   private MotionEvent currentEvent;
/*     */   private MotionEvent previousEvent;
/*     */   private long gestureDuration;
/*     */   private boolean isEnabled = true;
/*     */   protected L listener;
/*     */   
/*     */   public BaseGesture(Context context, AndroidGesturesManager gesturesManager) {
/*  33 */     this.context = context;
/*  34 */     this.windowManager = (WindowManager)context.getSystemService("window");
/*  35 */     this.gesturesManager = gesturesManager;
/*     */   }
/*     */   
/*     */   protected boolean onTouchEvent(MotionEvent motionEvent) {
/*  39 */     return analyze(motionEvent);
/*     */   }
/*     */   
/*     */   private boolean analyze(@Nullable MotionEvent motionEvent) {
/*  43 */     if (motionEvent == null) {
/*  44 */       return false;
/*     */     }
/*     */     
/*  47 */     if (this.previousEvent != null) {
/*  48 */       this.previousEvent.recycle();
/*  49 */       this.previousEvent = null;
/*     */     } 
/*     */     
/*  52 */     if (this.currentEvent != null) {
/*  53 */       this.previousEvent = MotionEvent.obtain(this.currentEvent);
/*  54 */       this.currentEvent.recycle();
/*  55 */       this.currentEvent = null;
/*     */     } 
/*     */     
/*  58 */     this.currentEvent = MotionEvent.obtain(motionEvent);
/*  59 */     this.gestureDuration = this.currentEvent.getEventTime() - this.currentEvent.getDownTime();
/*     */     
/*  61 */     return analyzeEvent(motionEvent);
/*     */   }
/*     */   
/*     */   protected abstract boolean analyzeEvent(@NonNull MotionEvent paramMotionEvent);
/*     */   
/*     */   protected boolean canExecute(int invokedGestureType) {
/*  67 */     if (this.listener == null || !this.isEnabled) {
/*  68 */       return false;
/*     */     }
/*     */     
/*  71 */     for (Set<Integer> exclusives : this.gesturesManager.getMutuallyExclusiveGestures()) {
/*  72 */       if (exclusives.contains(Integer.valueOf(invokedGestureType))) {
/*  73 */         for (Iterator<Integer> iterator = exclusives.iterator(); iterator.hasNext(); ) { int gestureType = ((Integer)iterator.next()).intValue();
/*  74 */           for (BaseGesture detector : this.gesturesManager.getDetectors()) {
/*  75 */             if (detector instanceof ProgressiveGesture) {
/*  76 */               ProgressiveGesture progressiveDetector = (ProgressiveGesture)detector;
/*  77 */               if (progressiveDetector.getHandledTypes().contains(Integer.valueOf(gestureType)) && progressiveDetector
/*  78 */                 .isInProgress()) {
/*  79 */                 return false;
/*     */               }
/*     */             } 
/*     */           }  }
/*     */       
/*     */       }
/*     */     } 
/*     */     
/*  87 */     return true;
/*     */   }
/*     */   
/*     */   protected void setListener(L listener) {
/*  91 */     this.listener = listener;
/*     */   }
/*     */   
/*     */   protected void removeListener() {
/*  95 */     this.listener = null;
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
/*     */   public long getGestureDuration() {
/* 108 */     return this.gestureDuration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MotionEvent getCurrentEvent() {
/* 117 */     return this.currentEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MotionEvent getPreviousEvent() {
/* 126 */     return this.previousEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 135 */     return this.isEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 144 */     this.isEnabled = enabled;
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\classes.jar!\com\mapbox\android\gestures\BaseGesture.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */