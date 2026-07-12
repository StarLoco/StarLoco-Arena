/*    */ package com.ankamagames.xulor.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Dimension
/*    */ {
/* 11 */   private int m_width = -1;
/* 12 */   private int m_height = -1;
/* 13 */   private Percentage m_widthPercentage = null;
/* 14 */   private Percentage m_heightPercentage = null;
/*    */   
/*    */   public Dimension() {}
/*    */   
/*    */   public Dimension(int w, int h)
/*    */   {
/* 20 */     this.m_width = w;
/* 21 */     this.m_height = h;
/*    */   }
/*    */   
/*    */   public Dimension(Percentage width, Percentage height) {
/* 25 */     this.m_widthPercentage = width;
/* 26 */     this.m_heightPercentage = height;
/*    */   }
/*    */   
/*    */   public int getWidth() {
/* 30 */     return this.m_width;
/*    */   }
/*    */   
/*    */   public int getHeight() {
/* 34 */     return this.m_height;
/*    */   }
/*    */   
/*    */   public Percentage getWidthPercentage() {
/* 38 */     return this.m_widthPercentage;
/*    */   }
/*    */   
/*    */   public Percentage getHeightPercentage() {
/* 42 */     return this.m_heightPercentage;
/*    */   }
/*    */   
/*    */   public void setValue(int w, int h) {
/* 46 */     this.m_width = w;
/* 47 */     this.m_height = h;
/*    */   }
/*    */   
/*    */   public void setPercentage(Percentage width, Percentage height) {
/* 51 */     this.m_heightPercentage = height;
/* 52 */     this.m_widthPercentage = width;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setHeight(int height)
/*    */   {
/* 59 */     this.m_height = height;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setHeightPercentage(Percentage heightPercentage)
/*    */   {
/* 66 */     this.m_heightPercentage = heightPercentage;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setWidth(int width)
/*    */   {
/* 73 */     this.m_width = width;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setWidthPercentage(Percentage widthPercentage)
/*    */   {
/* 80 */     this.m_widthPercentage = widthPercentage;
/*    */   }
/*    */   
/*    */   public boolean hasPercentage() {
/* 84 */     return (this.m_widthPercentage != null) || (this.m_heightPercentage != null);
/*    */   }
/*    */   
/*    */   public Dimension cloneDimension() {
/* 88 */     Dimension clone = new Dimension(this.m_width, this.m_height);
/* 89 */     clone.setHeightPercentage(this.m_heightPercentage);
/* 90 */     clone.setWidthPercentage(this.m_widthPercentage);
/* 91 */     return clone;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\util\Dimension.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */