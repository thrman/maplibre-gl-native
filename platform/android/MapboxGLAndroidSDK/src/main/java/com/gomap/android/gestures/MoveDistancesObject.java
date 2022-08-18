/*     */ package com.gomap.android.gestures;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MoveDistancesObject
/*     */ {
/*     */   private final float initialX;
/*     */   private final float initialY;
/*     */   private float prevX;
/*     */   private float prevY;
/*     */   private float currX;
/*     */   private float currY;
/*     */   private float distanceXSinceLast;
/*     */   private float distanceYSinceLast;
/*     */   private float distanceXSinceStart;
/*     */   private float distanceYSinceStart;
/*     */   
/*     */   public MoveDistancesObject(float initialX, float initialY) {
/*  21 */     this.initialX = initialX;
/*  22 */     this.initialY = initialY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNewPosition(float x, float y) {
/*  31 */     this.prevX = this.currX;
/*  32 */     this.prevY = this.currY;
/*     */     
/*  34 */     this.currX = x;
/*  35 */     this.currY = y;
/*     */     
/*  37 */     this.distanceXSinceLast = this.prevX - this.currX;
/*  38 */     this.distanceYSinceLast = this.prevY - this.currY;
/*     */     
/*  40 */     this.distanceXSinceStart = this.initialX - this.currX;
/*  41 */     this.distanceYSinceStart = this.initialY - this.currY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getInitialX() {
/*  49 */     return this.initialX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getInitialY() {
/*  57 */     return this.initialY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPreviousX() {
/*  65 */     return this.prevX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPreviousY() {
/*  73 */     return this.prevY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCurrentX() {
/*  81 */     return this.currX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCurrentY() {
/*  89 */     return this.currY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDistanceXSinceLast() {
/*  97 */     return this.distanceXSinceLast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDistanceYSinceLast() {
/* 105 */     return this.distanceYSinceLast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDistanceXSinceStart() {
/* 113 */     return this.distanceXSinceStart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDistanceYSinceStart() {
/* 121 */     return this.distanceYSinceStart;
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\classes.jar!\com\mapbox\android\gestures\MoveDistancesObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */