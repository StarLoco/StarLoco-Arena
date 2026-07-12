/*     */ package com.ankamagames.framework.kernel.core.maths;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Direction8
/*     */   implements Direction
/*     */ {
/*  14 */   EAST(0, new int[] { 1, -1 }),
/*  15 */   SOUTH_EAST(1, new int[] { 1 }),
/*  16 */   SOUTH(2, new int[] { 1, 1 }),
/*  17 */   SOUTH_WEST(3, new int[] { 0, 1 }),
/*  18 */   WEST(4, new int[] { -1, 1 }),
/*  19 */   NORTH_WEST(5, new int[] { -1 }),
/*  20 */   NORTH(6, new int[] { -1, -1 }),
/*  21 */   NORTH_EAST(7, new int[] { 0, -1
/*     */     }),
/*  23 */   TOP(8, new int[2]),
/*  24 */   BOTTOM(9, new int[2]),
/*  25 */   NONE(-1, new int[2]); private static Direction8[] DIRECTION_8_VALUES;
/*     */   static {
/*  27 */     DIRECTION_8_VALUES = new Direction8[] { SOUTH_EAST, SOUTH_WEST, NORTH_WEST, NORTH_EAST, EAST, WEST, NORTH, SOUTH };
/*  28 */     DIRECTION_4_VALUES = new Direction8[] { SOUTH_EAST, SOUTH_WEST, NORTH_WEST, NORTH_EAST };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Direction8[] DIRECTION_4_VALUES;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int m_index;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int[] m_vector;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Direction8(int index, int[] vector) {
/*  49 */     this.m_index = index;
/*  50 */     this.m_vector = vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/*  59 */     return this.m_index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getVector() {
/*  68 */     return this.m_vector;
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
/*     */   public static Direction8 getDirectionFromIndex(int index) {
/*  80 */     Direction8[] directions = values(); byte b; int i; Direction8[] arrayOfDirection81;
/*  81 */     for (i = (arrayOfDirection81 = directions).length, b = 0; b < i; ) { Direction8 direction = arrayOfDirection81[b];
/*  82 */       if (direction.getIndex() == index)
/*  83 */         return direction; 
/*     */       b++; }
/*     */     
/*  86 */     return NONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDirection4() {
/*  93 */     return !(this.m_vector[0] != 0 && this.m_vector[1] != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDiagonal() {
/* 100 */     return (this.m_vector[0] != 0 && this.m_vector[1] != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Direction8 getHorizontalDirection() {
/* 109 */     switch (this) {
/*     */       case EAST:
/*     */       case SOUTH_EAST:
/*     */       case SOUTH:
/* 113 */         return SOUTH_EAST;
/*     */       case WEST:
/*     */       case NORTH_WEST:
/*     */       case NORTH:
/* 117 */         return NORTH_WEST;
/*     */     } 
/* 119 */     return NONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Direction8 getVerticalDirection() {
/* 129 */     switch (this) {
/*     */       case SOUTH:
/*     */       case SOUTH_WEST:
/*     */       case WEST:
/* 133 */         return SOUTH_WEST;
/*     */       case EAST:
/*     */       case NORTH:
/*     */       case NORTH_EAST:
/* 137 */         return NORTH_EAST;
/*     */     } 
/* 139 */     return NONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Direction8[] getDirection8Values() {
/* 147 */     return DIRECTION_8_VALUES;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Direction8[] getDirection4Values() {
/* 154 */     return DIRECTION_4_VALUES;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isOpposite(Direction8 direction1, Direction8 direction2) {
/* 165 */     return (Math.abs(direction1.getIndex() - direction2.getIndex()) == 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Direction8 opposite() {
/* 172 */     switch (this) {
/*     */       case EAST:
/* 174 */         return WEST;
/*     */       case SOUTH_EAST:
/* 176 */         return NORTH_WEST;
/*     */       case SOUTH:
/* 178 */         return NORTH;
/*     */       case SOUTH_WEST:
/* 180 */         return NORTH_EAST;
/*     */       case WEST:
/* 182 */         return EAST;
/*     */       case NORTH_WEST:
/* 184 */         return SOUTH_EAST;
/*     */       case NORTH:
/* 186 */         return SOUTH;
/*     */       case NORTH_EAST:
/* 188 */         return SOUTH_WEST;
/*     */       case TOP:
/* 190 */         return BOTTOM;
/*     */       case null:
/* 192 */         return TOP;
/*     */     } 
/* 194 */     return NONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Direction8 getNextDirection8(int increment) {
/* 204 */     int index = getIndex();
/* 205 */     index += increment;
/* 206 */     index %= DIRECTION_8_VALUES.length;
/*     */     
/* 208 */     if (index < 0) {
/* 209 */       index += DIRECTION_8_VALUES.length;
/*     */     }
/*     */     
/* 212 */     return getDirectionFromIndex(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Direction8 getNextDirection4(int increment) {
/* 222 */     int index = getIndex();
/* 223 */     if (index % 2 == 0) {
/* 224 */       index--;
/*     */     }
/* 226 */     index += 2 * increment;
/* 227 */     index %= DIRECTION_8_VALUES.length;
/*     */     
/* 229 */     if (index < 0) {
/* 230 */       index += DIRECTION_8_VALUES.length;
/*     */     }
/*     */     
/* 233 */     return getDirectionFromIndex(index);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\maths\Direction8.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */