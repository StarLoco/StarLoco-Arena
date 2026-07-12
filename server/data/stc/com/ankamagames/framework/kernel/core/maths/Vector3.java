/*     */ package com.ankamagames.framework.kernel.core.maths;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Vector3
/*     */ {
/*     */   private double m_x;
/*     */   
/*     */ 
/*     */   private double m_y;
/*     */   
/*     */ 
/*     */   private double m_z;
/*     */   
/*     */ 
/*  16 */   public static final Vector3 AXIS_X = new Vector3(1.0D, 0.0D, 0.0D);
/*  17 */   public static final Vector3 AXIS_Y = new Vector3(0.0D, 1.0D, 0.0D);
/*  18 */   public static final Vector3 AXIS_Z = new Vector3(0.0D, 0.0D, 1.0D);
/*     */   
/*     */   public Vector3() {
/*  21 */     this(0.0D, 0.0D, 0.0D);
/*     */   }
/*     */   
/*     */   public Vector3(Vector3 v) {
/*  25 */     this(v.m_x, v.m_y, v.m_z);
/*     */   }
/*     */   
/*  28 */   public Vector3(Point3 start, Point3 end) { this(end.getX() - start.getX(), end.getY() - start.getY(), end.getZ() - start.getZ()); }
/*     */   
/*     */   public Vector3(double[] v)
/*     */   {
/*  32 */     this(v[0], v[1], v[2]);
/*     */   }
/*     */   
/*     */   public Vector3(double x, double y, double z) {
/*  36 */     this.m_x = x;
/*  37 */     this.m_y = y;
/*  38 */     this.m_z = z;
/*     */   }
/*     */   
/*     */   public double getX() {
/*  42 */     return this.m_x;
/*     */   }
/*     */   
/*     */   public float getXf() {
/*  46 */     return (float)this.m_x;
/*     */   }
/*     */   
/*     */   public void setX(double x) {
/*  50 */     this.m_x = x;
/*     */   }
/*     */   
/*     */   public double getY() {
/*  54 */     return this.m_y;
/*     */   }
/*     */   
/*     */   public float getYf() {
/*  58 */     return (float)this.m_y;
/*     */   }
/*     */   
/*     */   public void setY(double y) {
/*  62 */     this.m_y = y;
/*     */   }
/*     */   
/*     */   public double getZ() {
/*  66 */     return this.m_z;
/*     */   }
/*     */   
/*     */   public float getZf() {
/*  70 */     return (float)this.m_z;
/*     */   }
/*     */   
/*     */   public void setZ(double z) {
/*  74 */     this.m_z = z;
/*     */   }
/*     */   
/*     */   public void set(double x, double y, double z) {
/*  78 */     this.m_x = x;
/*  79 */     this.m_y = y;
/*  80 */     this.m_z = z;
/*     */   }
/*     */   
/*     */   public void setCurrent(Vector3 v) {
/*  84 */     if (v == null) { return;
/*     */     }
/*  86 */     this.m_x = v.m_x;
/*  87 */     this.m_y = v.m_y;
/*  88 */     this.m_z = v.m_z;
/*     */   }
/*     */   
/*     */   public void addCurrent(Vector3 v) {
/*  92 */     if (v == null) { return;
/*     */     }
/*  94 */     this.m_x += v.m_x;
/*  95 */     this.m_y += v.m_y;
/*  96 */     this.m_z += v.m_z;
/*     */   }
/*     */   
/*     */   public void subCurrent(Vector3 v) {
/* 100 */     if (v == null) { return;
/*     */     }
/* 102 */     this.m_x -= v.m_x;
/* 103 */     this.m_y -= v.m_y;
/* 104 */     this.m_z -= v.m_z;
/*     */   }
/*     */   
/*     */   public void mulCurrent(double scale) {
/* 108 */     this.m_x *= scale;
/* 109 */     this.m_y *= scale;
/* 110 */     this.m_z *= scale;
/*     */   }
/*     */   
/*     */   public Vector3 add(Vector3 v) {
/* 114 */     return new Vector3(v.m_x + this.m_x, v.m_y + this.m_y, v.m_z + this.m_z);
/*     */   }
/*     */   
/*     */   public Vector3 sub(Vector3 v) {
/* 118 */     return new Vector3(this.m_x - v.m_x, this.m_y - v.m_y, this.m_z - v.m_z);
/*     */   }
/*     */   
/*     */   public Vector3 mul(Vector3 v) {
/* 122 */     return new Vector3(this.m_x * v.m_x + this.m_x * v.m_y + this.m_x * v.m_z, 
/* 123 */       this.m_y * v.m_x + this.m_y * v.m_y + this.m_y * v.m_z, 
/* 124 */       this.m_z * v.m_x + this.m_z * v.m_y + this.m_z * v.m_z);
/*     */   }
/*     */   
/*     */   public Vector3 mul(double s) {
/* 128 */     return new Vector3(s * this.m_x, s * this.m_y, s * this.m_z);
/*     */   }
/*     */   
/*     */   public double dot(Vector3 v) {
/* 132 */     return this.m_x * v.m_x + this.m_y * v.m_y + this.m_z * v.m_z;
/*     */   }
/*     */   
/*     */   public double dot(Vector3i v) {
/* 136 */     return this.m_x * v.getX() + this.m_y * v.getY() + this.m_z * v.getZ();
/*     */   }
/*     */   
/*     */   public double sqrLength() {
/* 140 */     return this.m_x * this.m_x + this.m_y * this.m_y + this.m_z * this.m_z;
/*     */   }
/*     */   
/*     */   public double length() {
/* 144 */     double l = this.m_x * this.m_x + this.m_y * this.m_y + this.m_z * this.m_z;
/* 145 */     if (l == 0.0D)
/* 146 */       l = 1.0E-7D;
/* 147 */     return Math.sqrt(l);
/*     */   }
/*     */   
/*     */   public Vector3 normalize() {
/* 151 */     double l = length();
/* 152 */     if (l == 0.0D)
/* 153 */       l = 1.0E-7D;
/* 154 */     return mul(1.0D / l);
/*     */   }
/*     */   
/*     */   public void normalizeCurrent() {
/* 158 */     double l = length();
/* 159 */     if (l == 0.0D) { return;
/*     */     }
/* 161 */     mulCurrent(1.0D / l);
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 165 */     if ((obj == null) || (!(obj instanceof Vector3)))
/* 166 */       return false;
/* 167 */     Vector3 v = (Vector3)obj;
/* 168 */     return (v.m_x == this.m_x) && (v.m_y == this.m_y) && (v.m_z == this.m_z);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 172 */     return "[" + this.m_x + " ; " + this.m_y + " ; " + this.m_z + "]";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 180 */     if (!$assertionsDisabled) throw new AssertionError("Il n'est pas prévu que ces objets comparables servent de clef dans une HashTable/HashMap.");
/* 181 */     return super.hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\maths\Vector3.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */