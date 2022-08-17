/*    */ package com.mapbox.android.gestures;
/*    */ 
/*    */ import androidx.annotation.IntRange;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class PermittedActionsGuard
/*    */ {
/*    */   private static final int BITS_PER_PERMITTED_ACTION = 8;
/*    */   private static final int PERMITTED_ACTION_MASK = 255;
/*    */   private static final int NO_ACTION_PERMITTED = 255;
/*    */   
/*    */   boolean isMissingActions(int action, @IntRange(from = 0L) int eventPointerCount, @IntRange(from = 0L) int internalPointerCount) {
/* 18 */     long permittedActions = updatePermittedActions(eventPointerCount, internalPointerCount);
/* 19 */     if (action == permittedActions)
/*    */     {
/* 21 */       return false;
/*    */     }
/*    */     
/* 24 */     while (permittedActions != 0L) {
/*    */       
/* 26 */       long testCase = permittedActions & 0xFFL;
/* 27 */       if (action == testCase)
/*    */       {
/* 29 */         return false;
/*    */       }
/*    */ 
/*    */       
/* 33 */       permittedActions >>= 8L;
/*    */     } 
/*    */ 
/*    */     
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private long updatePermittedActions(@IntRange(from = 0L) int eventPointerCount, @IntRange(from = 0L) int internalPointerCount) {
/* 46 */     long permittedActions = 0L;
/*    */     
/* 48 */     if (internalPointerCount == 0) {
/*    */       
/* 50 */       permittedActions <<= 8L;
/* 51 */       permittedActions += 0L;
/*    */     } else {
/* 53 */       if (Math.abs(eventPointerCount - internalPointerCount) > 1)
/*    */       {
/* 55 */         return 255L;
/*    */       }
/* 57 */       if (eventPointerCount > internalPointerCount)
/*    */       
/* 59 */       { permittedActions <<= 8L;
/* 60 */         permittedActions += 5L; }
/* 61 */       else { if (eventPointerCount < internalPointerCount)
/*    */         {
/*    */           
/* 64 */           return 255L;
/*    */         }
/*    */         
/* 67 */         if (eventPointerCount == 1) {
/* 68 */           permittedActions <<= 8L;
/* 69 */           permittedActions++;
/*    */         } else {
/* 71 */           permittedActions <<= 8L;
/* 72 */           permittedActions += 6L;
/*    */         } 
/* 74 */         permittedActions <<= 8L;
/* 75 */         permittedActions += 2L; }
/*    */     
/*    */     } 
/*    */ 
/*    */     
/* 80 */     return permittedActions;
/*    */   }
/*    */ }


/* Location:              C:\Users\jd\Desktop\classes.jar!\com\mapbox\android\gestures\PermittedActionsGuard.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */