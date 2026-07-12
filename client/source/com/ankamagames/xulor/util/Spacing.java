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
/*    */ 
/*    */ public class Spacing
/*    */ {
/* 15 */   public static Spacing ZERO_SPACING = new Spacing(0, 0, 0, 0);
/*    */   private int m_top;
/*    */   private int m_bottom;
/*    */   
/*    */   public Spacing() {
/* 20 */     this.m_top = 0;
/* 21 */     this.m_bottom = 0;
/* 22 */     this.m_left = 0;
/* 23 */     this.m_right = 0;
/*    */   }
/*    */   private int m_left; private int m_right;
/*    */   public Spacing(int top, int bottom, int left, int right) {
/* 27 */     this.m_top = top;
/* 28 */     this.m_bottom = bottom;
/* 29 */     this.m_left = left;
/* 30 */     this.m_right = right;
/*    */   }
/*    */   
/*    */   public int getTop() {
/* 34 */     return this.m_top;
/*    */   }
/*    */   
/*    */   public int getBottom() {
/* 38 */     return this.m_bottom;
/*    */   }
/*    */   
/*    */   public int getLeft() {
/* 42 */     return this.m_left;
/*    */   }
/*    */   
/*    */   public int getRight() {
/* 46 */     return this.m_right;
/*    */   }
/*    */   
/*    */   public void setLeftRight(int lr) {
/* 50 */     this.m_left = lr;
/* 51 */     this.m_right = lr;
/*    */   }
/*    */   
/*    */   public void setTopBottom(int tb) {
/* 55 */     this.m_top = tb;
/* 56 */     this.m_bottom = tb;
/*    */   }
/*    */   
/*    */   public void setLeft(int l) {
/* 60 */     this.m_left = l;
/*    */   }
/*    */   
/*    */   public void setBottom(int b) {
/* 64 */     this.m_bottom = b;
/*    */   }
/*    */   
/*    */   public void setRight(int r) {
/* 68 */     this.m_right = r;
/*    */   }
/*    */   
/*    */   public void setTop(int t) {
/* 72 */     this.m_top = t;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulo\\util\Spacing.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */