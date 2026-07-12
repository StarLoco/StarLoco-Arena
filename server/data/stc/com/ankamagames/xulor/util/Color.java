/*    */ package com.ankamagames.xulor.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Color
/*    */ {
/* 14 */   public static final Color BLACK = new Color(0.0D, 0.0D, 0.0D);
/* 15 */   public static final Color RED = new Color(1.0D, 0.0D, 0.0D);
/* 16 */   public static final Color BLUE = new Color(0.0D, 0.0D, 1.0D);
/* 17 */   public static final Color GREEN = new Color(0.0D, 1.0D, 0.0D);
/* 18 */   public static final Color WHITE = new Color(1.0D, 1.0D, 1.0D);
/* 19 */   public static final Color WHITE_WITH_A_LITTLE_ALPHA = new Color(1.0D, 1.0D, 1.0D, 0.75D);
/* 20 */   public static final Color DARK_GRAY = new Color(0.25D, 0.25D, 0.25D);
/* 21 */   public static final Color GRAY = new Color(0.5D, 0.5D, 0.5D);
/* 22 */   public static final Color LIGHT_GRAY = new Color(0.75D, 0.75D, 0.75D);
/*    */   
/*    */   private double m_r;
/*    */   private double m_g;
/*    */   
/* 27 */   public Color(float[] color) { setValue(color); }
/*    */   
/*    */   private double m_b;
/*    */   private double m_a;
/* 31 */   public Color(double red, double green, double blue, double alpha) { this.m_r = red;
/* 32 */     this.m_g = green;
/* 33 */     this.m_b = blue;
/* 34 */     this.m_a = alpha;
/*    */   }
/*    */   
/*    */   public Color(double red, double green, double blue) {
/* 38 */     this.m_r = red;
/* 39 */     this.m_g = green;
/* 40 */     this.m_b = blue;
/* 41 */     this.m_a = 1.0D;
/*    */   }
/*    */   
/*    */   public Color() {
/* 45 */     this(BLACK);
/*    */   }
/*    */   
/*    */   public Color(Color c) {
/* 49 */     this.m_r = c.m_r;
/* 50 */     this.m_g = c.m_g;
/* 51 */     this.m_b = c.m_b;
/* 52 */     this.m_a = c.m_a;
/*    */   }
/*    */   
/*    */   public double getRed() {
/* 56 */     return this.m_r;
/*    */   }
/*    */   
/*    */   public double getGreen() {
/* 60 */     return this.m_g;
/*    */   }
/*    */   
/*    */   public double getBlue() {
/* 64 */     return this.m_b;
/*    */   }
/*    */   
/*    */   public double getAlpha() {
/* 68 */     return this.m_a;
/*    */   }
/*    */   
/*    */   public void setValue(float[] color) {
/* 72 */     if ((color != null) && (color.length >= 3)) {
/* 73 */       this.m_r = color[0];
/* 74 */       this.m_g = color[1];
/* 75 */       this.m_b = color[2];
/*    */     } else {
/* 77 */       this.m_r = (this.m_g = this.m_b = 0.0D);
/*    */     }
/* 79 */     if ((color != null) && (color.length >= 4)) {
/* 80 */       this.m_a = color[3];
/*    */     } else {
/* 82 */       this.m_a = 1.0D;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\util\Color.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */