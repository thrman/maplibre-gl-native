/*    */ package com.mapbox.android.gestures;
/*    */ 
/*    */ import android.util.Pair;
/*    */ 
/*    */ public class PointerDistancePair
/*    */   extends Pair<Integer, Integer> {
/*    */   public PointerDistancePair(Integer first, Integer second) {
/*  8 */     super(first, second);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 13 */     if (o instanceof PointerDistancePair) {
/* 14 */       PointerDistancePair otherPair = (PointerDistancePair)o;
/* 15 */       if ((((Integer)this.first).equals(otherPair.first) && ((Integer)this.second).equals(otherPair.second)) || (((Integer)this.first)
/* 16 */         .equals(otherPair.second) && ((Integer)this.second).equals(otherPair.first))) {
/* 17 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 21 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jd\Desktop\classes.jar!\com\mapbox\android\gestures\PointerDistancePair.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */