/*     */ package com.gomap.android.gestures;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.view.GestureDetector;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.UiThread;
/*     */ import androidx.core.view.GestureDetectorCompat;
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
/*     */ public class StandardGestureDetector
/*     */   extends BaseGesture<StandardGestureDetector.StandardOnGestureListener>
/*     */ {
/*     */   private final GestureDetectorCompat gestureDetector;
/*     */   final StandardOnGestureListener innerListener;
/*     */   
/*     */   public StandardGestureDetector(Context context, AndroidGesturesManager androidGesturesManager) {
/*  30 */     super(context, androidGesturesManager);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  35 */     this.innerListener = new StandardOnGestureListener()
/*     */       {
/*     */         public boolean onSingleTapUp(MotionEvent e)
/*     */         {
/*  39 */           return (StandardGestureDetector.this.canExecute(5) && StandardGestureDetector.this.listener.onSingleTapUp(e));
/*     */         }
/*     */ 
/*     */         
/*     */         public void onLongPress(MotionEvent e) {
/*  44 */           if (StandardGestureDetector.this.canExecute(6)) {
/*  45 */             StandardGestureDetector.this.listener.onLongPress(e);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
/*  51 */           return (StandardGestureDetector.this.canExecute(0) && StandardGestureDetector.this.listener.onScroll(e1, e2, distanceX, distanceY));
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
/*  56 */           return (StandardGestureDetector.this.canExecute(7) && StandardGestureDetector.this.listener.onFling(e1, e2, velocityX, velocityY));
/*     */         }
/*     */ 
/*     */         
/*     */         public void onShowPress(MotionEvent e) {
/*  61 */           if (StandardGestureDetector.this.canExecute(8)) {
/*  62 */             StandardGestureDetector.this.listener.onShowPress(e);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean onDown(MotionEvent e) {
/*  68 */           return (StandardGestureDetector.this.canExecute(9) && StandardGestureDetector.this.listener.onDown(e));
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean onDoubleTap(MotionEvent e) {
/*  73 */           return (StandardGestureDetector.this.canExecute(10) && StandardGestureDetector.this.listener.onDoubleTap(e));
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean onDoubleTapEvent(MotionEvent e) {
/*  78 */           return (StandardGestureDetector.this.canExecute(11) && StandardGestureDetector.this.listener.onDoubleTapEvent(e));
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean onSingleTapConfirmed(MotionEvent e) {
/*  83 */           return (StandardGestureDetector.this.canExecute(12) && StandardGestureDetector.this.listener.onSingleTapConfirmed(e));
/*     */         }
/*     */       };
/*     */     this.gestureDetector = new GestureDetectorCompat(context, this.innerListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface StandardOnGestureListener
/*     */     extends GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {}
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SimpleStandardOnGestureListener
/*     */     implements StandardOnGestureListener
/*     */   {
/*     */     public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
/* 101 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean onDoubleTap(@NonNull MotionEvent e) {
/* 106 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean onDoubleTapEvent(@NonNull MotionEvent e) {
/* 111 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean onDown(@NonNull MotionEvent e) {
/* 116 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onShowPress(@NonNull MotionEvent e) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean onSingleTapUp(@NonNull MotionEvent e) {
/* 126 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean onScroll(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
/* 131 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onLongPress(@NonNull MotionEvent e) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
/* 141 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean analyzeEvent(@NonNull MotionEvent motionEvent) {
/* 147 */     return this.gestureDetector.onTouchEvent(motionEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLongpressEnabled() {
/* 155 */     return this.gestureDetector.isLongpressEnabled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIsLongpressEnabled(boolean enabled) {
/* 163 */     this.gestureDetector.setIsLongpressEnabled(enabled);
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\classes.jar!\com\mapbox\android\gestures\StandardGestureDetector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */