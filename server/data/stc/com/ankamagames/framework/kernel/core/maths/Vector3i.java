/*     */ package com.ankamagames.framework.kernel.core.maths;
/*     */ 
/*     */ 
/*     */ public class Vector3i
/*     */ {
/*     */   private static final double PI = 3.141592653589793D;
/*     */   
/*     */   private static final double PI_8 = 0.39269908169872414D;
/*     */   
/*     */   private static final double PI_8_3 = 1.1780972450961724D;
/*     */   
/*     */   private static final double PI_8_5 = 1.9634954084936207D;
/*     */   
/*     */   private static final double PI_8_7 = 2.748893571891069D;
/*     */   
/*     */   private static final double PI_4 = 0.7853981633974483D;
/*     */   
/*     */   private static final double PI_4_3 = 2.356194490192345D;
/*     */   
/*     */   private int m_x;
/*     */   
/*     */   private int m_y;
/*     */   private int m_z;
/*     */   
/*     */   public Vector3i()
/*     */   {
/*  27 */     this(0, 0, 0);
/*     */   }
/*     */   
/*     */   public Vector3i(Vector3i v) {
/*  31 */     this(v.m_x, v.m_y, v.m_z);
/*     */   }
/*     */   
/*     */   public Vector3i(int[] v) {
/*  35 */     this(v[0], v[1], v[2]);
/*     */   }
/*     */   
/*     */   public Vector3i(Point3 startPoint, Point3 endPoint) {
/*  39 */     this.m_x = (endPoint.getX() - startPoint.getX());
/*  40 */     this.m_y = (endPoint.getY() - startPoint.getY());
/*  41 */     this.m_z = (endPoint.getZ() - startPoint.getZ());
/*     */   }
/*     */   
/*     */   public Vector3i(int x, int y, int z) {
/*  45 */     this.m_x = x;
/*  46 */     this.m_y = y;
/*  47 */     this.m_z = z;
/*     */   }
/*     */   
/*     */   public Vector3i(int startX, int startY, int startZ, int endX, int endY, int endZ) {
/*  51 */     this.m_x = (endX - startX);
/*  52 */     this.m_y = (endY - startY);
/*  53 */     this.m_z = (endZ - startZ);
/*     */   }
/*     */   
/*     */   public void set(int[] coords) {
/*  57 */     this.m_x = coords[0];
/*  58 */     this.m_y = coords[1];
/*  59 */     this.m_z = coords[2];
/*     */   }
/*     */   
/*     */   public int getX() {
/*  63 */     return this.m_x;
/*     */   }
/*     */   
/*     */   public void setX(int x) {
/*  67 */     this.m_x = x;
/*     */   }
/*     */   
/*     */   public int getY() {
/*  71 */     return this.m_y;
/*     */   }
/*     */   
/*     */   public void setY(int y) {
/*  75 */     this.m_y = y;
/*     */   }
/*     */   
/*     */   public int getZ() {
/*  79 */     return this.m_z;
/*     */   }
/*     */   
/*     */   public void setZ(int z) {
/*  83 */     this.m_z = z;
/*     */   }
/*     */   
/*     */   public Vector3i add(Vector3i v) {
/*  87 */     return new Vector3i(v.m_x + this.m_x, v.m_y + this.m_y, v.m_z + this.m_z);
/*     */   }
/*     */   
/*     */   public Vector3i sub(Vector3i v) {
/*  91 */     return new Vector3i(this.m_x - v.m_x, this.m_y - v.m_y, this.m_z - v.m_z);
/*     */   }
/*     */   
/*     */   public Vector3i mul(Vector3i v) {
/*  95 */     return new Vector3i(this.m_x * v.m_x + this.m_x * v.m_y + this.m_x * v.m_z, 
/*  96 */       this.m_y * v.m_x + this.m_y * v.m_y + this.m_y * v.m_z, 
/*  97 */       this.m_z * v.m_x + this.m_z * v.m_y + this.m_z * v.m_z);
/*     */   }
/*     */   
/*     */   public Vector3i mul(int s) {
/* 101 */     return new Vector3i(s * this.m_x, s * this.m_y, s * this.m_z);
/*     */   }
/*     */   
/*     */   public float dot(Vector3i v) {
/* 105 */     return this.m_x * v.m_x + this.m_y * v.m_y + this.m_z * v.m_z;
/*     */   }
/*     */   
/*     */   public int sqrLength() {
/* 109 */     return this.m_x * this.m_x + this.m_y * this.m_y + this.m_z * this.m_z;
/*     */   }
/*     */   
/*     */   public int length() {
/* 113 */     int l = this.m_x * this.m_x + this.m_y * this.m_y + this.m_z * this.m_z;
/* 114 */     return Functions.isqrt(l);
/*     */   }
/*     */   
/*     */   public Vector3i normalize() {
/* 118 */     int l = length();
/* 119 */     return mul(1 / l);
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 124 */     if ((obj == null) || (!(obj instanceof Vector3i)))
/* 125 */       return false;
/* 126 */     Vector3i v = (Vector3i)obj;
/* 127 */     return (v.m_x == this.m_x) && (v.m_y == this.m_y) && (v.m_z == this.m_z);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 131 */     return "[" + this.m_x + " ; " + this.m_y + " ; " + this.m_z + "]";
/*     */   }
/*     */   
/*     */   public static Direction8 getDirection8FromVector(double vx, double vy) {
/* 135 */     double a = Math.atan(Math.abs(vy) / Math.abs(vx));
/*     */     
/*     */ 
/* 138 */     if (vx < 0.0D) {
/* 139 */       a = 3.141592653589793D - a;
/*     */     }
/* 141 */     if (vy > 0.0D)
/* 142 */       a = -a;
/*     */     Direction8 direction;
/*     */     Direction8 direction;
/* 145 */     if ((a <= 2.748893571891069D) && (a >= 1.9634954084936207D)) {
/* 146 */       direction = Direction8.NORTH; } else { Direction8 direction;
/* 147 */       if ((a <= 1.9634954084936207D) && (a >= 1.1780972450961724D)) {
/* 148 */         direction = Direction8.NORTH_EAST; } else { Direction8 direction;
/* 149 */         if ((a <= 1.1780972450961724D) && (a >= 0.39269908169872414D)) {
/* 150 */           direction = Direction8.EAST; } else { Direction8 direction;
/* 151 */           if ((a <= 0.39269908169872414D) && (a >= -0.39269908169872414D)) {
/* 152 */             direction = Direction8.SOUTH_EAST; } else { Direction8 direction;
/* 153 */             if ((a <= -0.39269908169872414D) && (a >= -1.1780972450961724D)) {
/* 154 */               direction = Direction8.SOUTH; } else { Direction8 direction;
/* 155 */               if ((a <= -1.1780972450961724D) && (a >= -1.9634954084936207D)) {
/* 156 */                 direction = Direction8.SOUTH_WEST; } else { Direction8 direction;
/* 157 */                 if ((a <= -1.9634954084936207D) && (a >= -2.748893571891069D)) {
/* 158 */                   direction = Direction8.WEST;
/*     */                 } else
/* 160 */                   direction = Direction8.NORTH_WEST;
/*     */               }
/*     */             }
/*     */           } } } }
/* 164 */     return direction;
/*     */   }
/*     */   
/*     */   public Direction8 toDirection8() {
/* 168 */     return getDirection8FromVector(this.m_x, this.m_y);
/*     */   }
/*     */   
/*     */   public static Direction8 getDirection4FromVector(double vx, double vy)
/*     */   {
/* 173 */     double a = Math.atan(Math.abs(vy) / Math.abs(vx));
/*     */     
/*     */ 
/* 176 */     if (vx < 0.0D) {
/* 177 */       a = 3.141592653589793D - a;
/*     */     }
/* 179 */     if (vy > 0.0D)
/* 180 */       a = -a;
/*     */     Direction8 direction;
/*     */     Direction8 direction;
/* 183 */     if ((a <= 2.356194490192345D) && (a >= 0.7853981633974483D)) {
/* 184 */       direction = Direction8.NORTH_EAST; } else { Direction8 direction;
/* 185 */       if ((a <= 0.7853981633974483D) && (a >= -0.7853981633974483D)) {
/* 186 */         direction = Direction8.SOUTH_EAST; } else { Direction8 direction;
/* 187 */         if ((a <= -0.7853981633974483D) && (a >= -2.356194490192345D)) {
/* 188 */           direction = Direction8.SOUTH_WEST;
/*     */         } else
/* 190 */           direction = Direction8.NORTH_WEST;
/*     */       }
/*     */     }
/* 193 */     return direction;
/*     */   }
/*     */   
/*     */   public Direction8 toDirection4() {
/* 197 */     return getDirection4FromVector(this.m_x, this.m_y);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 205 */     if (!$assertionsDisabled) throw new AssertionError("Il n'est pas prévu que ces objets comparables servent de clef dans une HashTable/HashMap.");
/* 206 */     return super.hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\maths\Vector3i.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */