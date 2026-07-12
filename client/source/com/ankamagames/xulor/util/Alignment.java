/*     */ package com.ankamagames.xulor.util;
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
/*     */ public enum Alignment
/*     */ {
/*  14 */   NORTH_WEST, NORTH_NORTH_WEST, NORTH, NORTH_NORTH_EAST, NORTH_EAST,
/*  15 */   WEST_NORTH_WEST, EAST_NORTH_EAST,
/*  16 */   WEST, CENTER, EAST,
/*  17 */   WEST_SOUTH_WEST, EAST_SOUTH_EAST,
/*  18 */   SOUTH_WEST, SOUTH_SOUTH_WEST, SOUTH, SOUTH_SOUTH_EAST, SOUTH_EAST;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX(int componentWidth) {
/*  26 */     switch (this) {
/*     */       case NORTH_WEST:
/*     */       case WEST_NORTH_WEST:
/*     */       case WEST:
/*     */       case WEST_SOUTH_WEST:
/*     */       case SOUTH_WEST:
/*  32 */         return 0;
/*     */       case NORTH_NORTH_WEST:
/*     */       case SOUTH_SOUTH_WEST:
/*  35 */         return (int)Math.max(0.0D, componentWidth * 0.25D);
/*     */       case NORTH:
/*     */       case null:
/*     */       case SOUTH:
/*  39 */         return (int)Math.max(0.0D, componentWidth * 0.5D);
/*     */       case NORTH_NORTH_EAST:
/*     */       case SOUTH_SOUTH_EAST:
/*  42 */         return (int)Math.max(0.0D, componentWidth * 0.75D);
/*     */       case NORTH_EAST:
/*     */       case EAST_NORTH_EAST:
/*     */       case EAST:
/*     */       case EAST_SOUTH_EAST:
/*     */       case SOUTH_EAST:
/*  48 */         return Math.max(0, componentWidth);
/*     */     } 
/*  50 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY(int componentHeight) {
/*  59 */     switch (this) {
/*     */       case NORTH_WEST:
/*     */       case NORTH_NORTH_WEST:
/*     */       case NORTH:
/*     */       case NORTH_NORTH_EAST:
/*     */       case NORTH_EAST:
/*  65 */         return Math.max(0, componentHeight);
/*     */       case WEST_NORTH_WEST:
/*     */       case EAST_NORTH_EAST:
/*  68 */         return (int)Math.max(0.0D, componentHeight * 0.75D);
/*     */       case WEST:
/*     */       case null:
/*     */       case EAST:
/*  72 */         return (int)Math.max(0.0D, componentHeight * 0.5D);
/*     */       case WEST_SOUTH_WEST:
/*     */       case EAST_SOUTH_EAST:
/*  75 */         return (int)Math.max(0.0D, componentHeight * 0.25D);
/*     */       case SOUTH_WEST:
/*     */       case SOUTH_SOUTH_WEST:
/*     */       case SOUTH:
/*     */       case SOUTH_SOUTH_EAST:
/*     */       case SOUTH_EAST:
/*  81 */         return 0;
/*     */     } 
/*  83 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX(int componentWidth, int parentWidth) {
/*  93 */     switch (this) {
/*     */       case NORTH_WEST:
/*     */       case WEST_NORTH_WEST:
/*     */       case WEST:
/*     */       case WEST_SOUTH_WEST:
/*     */       case SOUTH_WEST:
/*  99 */         return 0;
/*     */       case NORTH_NORTH_WEST:
/*     */       case SOUTH_SOUTH_WEST:
/* 102 */         return (int)Math.round((parentWidth - componentWidth) * 0.25D);
/*     */       case NORTH:
/*     */       case null:
/*     */       case SOUTH:
/* 106 */         return (int)Math.round((parentWidth - componentWidth) * 0.5D);
/*     */       case NORTH_NORTH_EAST:
/*     */       case SOUTH_SOUTH_EAST:
/* 109 */         return (int)Math.round((parentWidth - componentWidth) * 0.75D);
/*     */       case NORTH_EAST:
/*     */       case EAST_NORTH_EAST:
/*     */       case EAST:
/*     */       case EAST_SOUTH_EAST:
/*     */       case SOUTH_EAST:
/* 115 */         return Math.max(0, parentWidth - componentWidth);
/*     */     } 
/* 117 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY(int componentHeight, int parentHeight) {
/* 127 */     switch (this) {
/*     */       case NORTH_WEST:
/*     */       case NORTH_NORTH_WEST:
/*     */       case NORTH:
/*     */       case NORTH_NORTH_EAST:
/*     */       case NORTH_EAST:
/* 133 */         return parentHeight - componentHeight;
/*     */       case WEST_NORTH_WEST:
/*     */       case EAST_NORTH_EAST:
/* 136 */         return (int)Math.round((parentHeight - componentHeight) * 0.75D);
/*     */       case WEST:
/*     */       case null:
/*     */       case EAST:
/* 140 */         return (int)Math.round((parentHeight - componentHeight) * 0.5D);
/*     */       case WEST_SOUTH_WEST:
/*     */       case EAST_SOUTH_EAST:
/* 143 */         return (int)Math.round((parentHeight - componentHeight) * 0.25D);
/*     */       case SOUTH_WEST:
/*     */       case SOUTH_SOUTH_WEST:
/*     */       case SOUTH:
/*     */       case SOUTH_SOUTH_EAST:
/*     */       case SOUTH_EAST:
/* 149 */         return 0;
/*     */     } 
/* 151 */     return 0;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulo\\util\Alignment.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */