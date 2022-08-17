/*     */ package com.mapbox.turf.models;
/*     */ 
/*     */ import androidx.annotation.Nullable;
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
/*     */ public class LineIntersectsResult
/*     */ {
/*     */   private final Double horizontalIntersection;
/*     */   private final Double verticalIntersection;
/*     */   private final boolean onLine1;
/*     */   private final boolean onLine2;
/*     */   
/*     */   private LineIntersectsResult(@Nullable Double horizontalIntersection, @Nullable Double verticalIntersection, boolean onLine1, boolean onLine2) {
/*  27 */     this.horizontalIntersection = horizontalIntersection;
/*  28 */     this.verticalIntersection = verticalIntersection;
/*  29 */     this.onLine1 = onLine1;
/*  30 */     this.onLine2 = onLine2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder builder() {
/*  41 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Double horizontalIntersection() {
/*  52 */     return this.horizontalIntersection;
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
/*     */   public Double verticalIntersection() {
/*  64 */     return this.verticalIntersection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLine1() {
/*  75 */     return this.onLine1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLine2() {
/*  85 */     return this.onLine2;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  90 */     return "LineIntersectsResult{horizontalIntersection=" + this.horizontalIntersection + ", verticalIntersection=" + this.verticalIntersection + ", onLine1=" + this.onLine1 + ", onLine2=" + this.onLine2 + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 100 */     if (obj == this) {
/* 101 */       return true;
/*     */     }
/* 103 */     if (obj instanceof LineIntersectsResult) {
/* 104 */       LineIntersectsResult that = (LineIntersectsResult)obj;
/* 105 */       if ((this.horizontalIntersection == null) ? (that
/* 106 */         .horizontalIntersection() == null) : this.horizontalIntersection
/* 107 */         .equals(that.horizontalIntersection())) if ((this.verticalIntersection == null) ? (that
/*     */           
/* 109 */           .verticalIntersection() == null) : this.verticalIntersection
/* 110 */           .equals(that.verticalIntersection())) if (this.onLine1 == that
/* 111 */             .onLine1() && this.onLine2 == that
/* 112 */             .onLine2());   return false;
/*     */     } 
/* 114 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 119 */     int hashCode = 1;
/* 120 */     hashCode *= 1000003;
/* 121 */     hashCode ^= (this.horizontalIntersection == null) ? 0 : this.horizontalIntersection.hashCode();
/* 122 */     hashCode *= 1000003;
/* 123 */     hashCode ^= (this.verticalIntersection == null) ? 0 : this.verticalIntersection.hashCode();
/* 124 */     hashCode *= 1000003;
/* 125 */     hashCode ^= this.onLine1 ? 1231 : 1237;
/* 126 */     hashCode *= 1000003;
/* 127 */     hashCode ^= this.onLine2 ? 1231 : 1237;
/* 128 */     return hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Builder toBuilder() {
/* 138 */     return new Builder(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private Double horizontalIntersection;
/*     */ 
/*     */     
/*     */     private Double verticalIntersection;
/*     */ 
/*     */     
/* 152 */     private Boolean onLine1 = Boolean.valueOf(false);
/* 153 */     private Boolean onLine2 = Boolean.valueOf(false);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder(LineIntersectsResult source) {
/* 159 */       this.horizontalIntersection = source.horizontalIntersection();
/* 160 */       this.verticalIntersection = source.verticalIntersection();
/* 161 */       this.onLine1 = Boolean.valueOf(source.onLine1());
/* 162 */       this.onLine2 = Boolean.valueOf(source.onLine2());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder horizontalIntersection(@Nullable Double horizontalIntersection) {
/* 173 */       this.horizontalIntersection = horizontalIntersection;
/* 174 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder verticalIntersection(@Nullable Double verticalIntersection) {
/* 185 */       this.verticalIntersection = verticalIntersection;
/* 186 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder onLine1(boolean onLine1) {
/* 198 */       this.onLine1 = Boolean.valueOf(onLine1);
/* 199 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder onLine2(boolean onLine2) {
/* 211 */       this.onLine2 = Boolean.valueOf(onLine2);
/* 212 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public LineIntersectsResult build() {
/* 223 */       String missing = "";
/* 224 */       if (this.onLine1 == null) {
/* 225 */         missing = missing + " onLine1";
/*     */       }
/* 227 */       if (this.onLine2 == null) {
/* 228 */         missing = missing + " onLine2";
/*     */       }
/* 230 */       if (!missing.isEmpty()) {
/* 231 */         throw new IllegalStateException("Missing required properties:" + missing);
/*     */       }
/* 233 */       return new LineIntersectsResult(this.horizontalIntersection, this.verticalIntersection, this.onLine1
/*     */ 
/*     */           
/* 236 */           .booleanValue(), this.onLine2
/* 237 */           .booleanValue());
/*     */     }
/*     */     
/*     */     Builder() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-turf-5.9.0.jar!\com\mapbox\turf\models\LineIntersectsResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */