/*     */ package com.ankamagames.framework.kernel.core.maths;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Point3
/*     */ {
/*     */   private int m_x;
/*     */   
/*     */ 
/*     */   private int m_y;
/*     */   
/*     */   private short m_z;
/*     */   
/*     */ 
/*     */   public Point3() {}
/*     */   
/*     */ 
/*     */   public Point3(Point3 p)
/*     */   {
/*  20 */     this.m_x = p.m_x;
/*  21 */     this.m_y = p.m_y;
/*  22 */     this.m_z = p.m_z;
/*     */   }
/*     */   
/*     */   public Point3(int[] coords) {
/*  26 */     this.m_x = coords[0];
/*  27 */     this.m_y = coords[1];
/*  28 */     this.m_z = ((short)coords[2]);
/*     */   }
/*     */   
/*     */   public Point3(int x, int y, short z) {
/*  32 */     this.m_x = x;
/*  33 */     this.m_y = y;
/*  34 */     this.m_z = z;
/*     */   }
/*     */   
/*     */   public int getX() {
/*  38 */     return this.m_x;
/*     */   }
/*     */   
/*     */   public void setX(int x) {
/*  42 */     this.m_x = x;
/*     */   }
/*     */   
/*     */   public int getY() {
/*  46 */     return this.m_y;
/*     */   }
/*     */   
/*     */   public void setY(int y) {
/*  50 */     this.m_y = y;
/*     */   }
/*     */   
/*     */   public short getZ() {
/*  54 */     return this.m_z;
/*     */   }
/*     */   
/*     */   public void setZ(short z) {
/*  58 */     this.m_z = z;
/*     */   }
/*     */   
/*     */   public void set(int x, int y, short z) {
/*  62 */     this.m_x = x;
/*  63 */     this.m_y = y;
/*  64 */     this.m_z = z;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/*  68 */     if (this == o) return true;
/*  69 */     if ((o == null) || (getClass() != o.getClass())) { return false;
/*     */     }
/*  71 */     Point3 point3 = (Point3)o;
/*     */     
/*  73 */     if (this.m_x != point3.m_x) return false;
/*  74 */     if (this.m_y != point3.m_y) return false;
/*  75 */     if (this.m_z != point3.m_z) { return false;
/*     */     }
/*  77 */     return true;
/*     */   }
/*     */   
/*     */   public boolean equalsIgnoringAltitude(Object o) {
/*  81 */     if (this == o) return true;
/*  82 */     if ((o == null) || (getClass() != o.getClass())) { return false;
/*     */     }
/*  84 */     Point3 point3 = (Point3)o;
/*     */     
/*  86 */     if (this.m_x != point3.m_x) return false;
/*  87 */     if (this.m_y != point3.m_y) { return false;
/*     */     }
/*  89 */     return true;
/*     */   }
/*     */   
/*     */   public boolean equals(int x, int y) {
/*  93 */     return (this.m_x == x) && (this.m_y == y);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  98 */     return 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 107 */       "{Point3 : (" + this.m_x + ", " + this.m_y + ", " + this.m_z + ") @" + Integer.toHexString(hashCode()) + "}";
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 112 */     long bits = 1L;
/* 113 */     bits = 31L * bits + this.m_x;
/* 114 */     bits = 31L * bits + this.m_y;
/* 115 */     bits = 31L * bits + this.m_z;
/* 116 */     return (int)(bits ^ bits >> 32);
/*     */   }
/*     */   
/*     */   public void reset() {
/* 120 */     this.m_x = 0;
/* 121 */     this.m_y = 0;
/* 122 */     this.m_z = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\maths\Point3.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */