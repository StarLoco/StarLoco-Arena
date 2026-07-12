/*     */ package org.fenggui.util;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Rectangle
/*     */ {
/*     */   private int width;
/*     */   private int height;
/*     */   private int x;
/*     */   private int y;
/*     */   
/*     */   public Rectangle() {}
/*     */   
/*     */   public Rectangle(Rectangle copy) {
/*  45 */     this.width = copy.width;
/*  46 */     this.height = copy.height;
/*  47 */     this.x = copy.x;
/*  48 */     this.y = copy.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle(int x, int y, int width, int height) {
/*  55 */     this.width = width;
/*  56 */     this.height = height;
/*  57 */     this.x = x;
/*  58 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(Rectangle copy) {
/*  64 */     this.width = copy.width;
/*  65 */     this.height = copy.height;
/*  66 */     this.x = copy.x;
/*  67 */     this.y = copy.y;
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
/*     */   
/*     */   public void set(int x, int y, int width, int height) {
/*  80 */     this.width = width;
/*  81 */     this.height = height;
/*  82 */     this.x = x;
/*  83 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  89 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeight(int height) {
/*  95 */     this.height = height;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWidth(int width) {
/* 101 */     this.width = width;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(int x) {
/* 107 */     this.x = x;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(int y) {
/* 113 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 119 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/* 125 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY() {
/* 131 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(int x, int y) {
/* 137 */     return (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersect(Rectangle rect) {
/* 148 */     int minX = Math.min(this.x, rect.x);
/* 149 */     int minY = Math.min(this.y, rect.y);
/* 150 */     int maxX = Math.max(this.x, rect.x);
/* 151 */     int maxY = Math.max(this.y, rect.y);
/* 152 */     if (maxX - minX < this.width + rect.getWidth())
/*     */     {
/* 154 */       if (maxY - minY < this.height + rect.getHeight()) return true; 
/*     */     }
/* 156 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 162 */     return "[" + this.x + "," + this.y + " " + this.width + "x" + this.height + "]";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggu\\util\Rectangle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */