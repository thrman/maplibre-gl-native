/*    */ package com.mapbox.android.gestures;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.content.res.Resources;
/*    */ import android.graphics.PointF;
/*    */ import android.util.DisplayMetrics;
/*    */ import android.util.TypedValue;
/*    */ import android.view.MotionEvent;
/*    */ import androidx.annotation.NonNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Utils
/*    */ {
/*    */   public static PointF determineFocalPoint(@NonNull MotionEvent motionEvent) {
/* 20 */     int pointersCount = motionEvent.getPointerCount();
/* 21 */     float x = 0.0F;
/* 22 */     float y = 0.0F;
/*    */     
/* 24 */     for (int i = 0; i < pointersCount; i++) {
/* 25 */       x += motionEvent.getX(i);
/* 26 */       y += motionEvent.getY(i);
/*    */     } 
/*    */     
/* 29 */     return new PointF(x / pointersCount, y / pointersCount);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static float getRawX(MotionEvent event, int pointerIndex) {
/* 41 */     float offset = event.getRawX() - event.getX();
/* 42 */     if (pointerIndex < event.getPointerCount()) {
/* 43 */       return event.getX(pointerIndex) + offset;
/*    */     }
/* 45 */     return 0.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static float getRawY(MotionEvent event, int pointerIndex) {
/* 57 */     float offset = event.getRawY() - event.getY();
/* 58 */     if (pointerIndex < event.getPointerCount()) {
/* 59 */       return event.getY(pointerIndex) + offset;
/*    */     }
/* 61 */     return 0.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static float dpToPx(float dp) {
/* 71 */     return dp * (Resources.getSystem().getDisplayMetrics()).density;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static float pxToDp(float px) {
/* 81 */     return px / (Resources.getSystem().getDisplayMetrics()).density;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static float pxToMm(float px, Context context) {
/* 92 */     DisplayMetrics dm = context.getResources().getDisplayMetrics();
/* 93 */     return px / TypedValue.applyDimension(5, 1.0F, dm);
/*    */   }
/*    */ }


/* Location:              C:\Users\jd\Desktop\classes.jar!\com\mapbox\android\gestures\Utils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */