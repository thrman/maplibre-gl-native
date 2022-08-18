/*    */ package com.gomap.android.gestures;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MultiFingerDistancesObject
/*    */ {
/*    */   private final float prevFingersDiffX;
/*    */   private final float prevFingersDiffY;
/*    */   private final float currFingersDiffX;
/*    */   private final float currFingersDiffY;
/*    */   private final float prevFingersDiffXY;
/*    */   private final float currFingersDiffXY;
/*    */   
/*    */   public MultiFingerDistancesObject(float prevFingersDiffX, float prevFingersDiffY, float currFingersDiffX, float currFingersDiffY) {
/* 16 */     this.prevFingersDiffX = prevFingersDiffX;
/* 17 */     this.prevFingersDiffY = prevFingersDiffY;
/* 18 */     this.currFingersDiffX = currFingersDiffX;
/* 19 */     this.currFingersDiffY = currFingersDiffY;
/*    */     
/* 21 */     this
/* 22 */       .prevFingersDiffXY = (float)Math.sqrt((prevFingersDiffX * prevFingersDiffX + prevFingersDiffY * prevFingersDiffY));
/*    */     
/* 24 */     this
/* 25 */       .currFingersDiffXY = (float)Math.sqrt((currFingersDiffX * currFingersDiffX + currFingersDiffY * currFingersDiffY));
/*    */   }
/*    */   
/*    */   public float getPrevFingersDiffX() {
/* 29 */     return this.prevFingersDiffX;
/*    */   }
/*    */   
/*    */   public float getPrevFingersDiffY() {
/* 33 */     return this.prevFingersDiffY;
/*    */   }
/*    */   
/*    */   public float getCurrFingersDiffX() {
/* 37 */     return this.currFingersDiffX;
/*    */   }
/*    */   
/*    */   public float getCurrFingersDiffY() {
/* 41 */     return this.currFingersDiffY;
/*    */   }
/*    */   
/*    */   public float getPrevFingersDiffXY() {
/* 45 */     return this.prevFingersDiffXY;
/*    */   }
/*    */   
/*    */   public float getCurrFingersDiffXY() {
/* 49 */     return this.currFingersDiffXY;
/*    */   }
/*    */ }


/* Location:              C:\Users\jd\Desktop\classes.jar!\com\mapbox\android\gestures\MultiFingerDistancesObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */