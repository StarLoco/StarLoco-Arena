/*    */ package com.ankamagames.framework.graphics.opengl.base.render;
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
/*    */ public class ViewPort
/*    */ {
/*    */   private double m_x;
/*    */   private double m_y;
/*    */   private double m_width;
/*    */   private double m_height;
/*    */   
/*    */   public ViewPort(double x, double y, double width, double height) {
/* 20 */     this.m_x = x;
/* 21 */     this.m_y = y;
/* 22 */     this.m_width = width;
/* 23 */     this.m_height = height;
/*    */   }
/*    */   
/*    */   public double getX() {
/* 27 */     return this.m_x;
/*    */   }
/*    */   
/*    */   public void setX(double x) {
/* 31 */     this.m_x = x;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 35 */     return this.m_y;
/*    */   }
/*    */   
/*    */   public void setY(double y) {
/* 39 */     this.m_y = y;
/*    */   }
/*    */   
/*    */   public double getWidth() {
/* 43 */     return this.m_width;
/*    */   }
/*    */   
/*    */   public void setWidth(double width) {
/* 47 */     this.m_width = width;
/*    */   }
/*    */   
/*    */   public double getHeight() {
/* 51 */     return this.m_height;
/*    */   }
/*    */   
/*    */   public void setHeight(double height) {
/* 55 */     this.m_height = height;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\render\ViewPort.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */