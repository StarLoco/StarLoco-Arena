/*    */ package com.ankamagames.graphics.isometric.lines;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Segment
/*    */ {
/*    */   private int m_x;
/*    */   private int m_y;
/*    */   private int m_z;
/*    */   private boolean m_behind;
/*    */   
/*    */   public Segment(int x, int y, int z) {
/* 17 */     this.m_x = x;
/* 18 */     this.m_y = y;
/* 19 */     this.m_z = z;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getX() {
/* 24 */     return this.m_x;
/*    */   }
/*    */   
/*    */   public int getY() {
/* 28 */     return this.m_y;
/*    */   }
/*    */   
/*    */   public int getZ() {
/* 32 */     return this.m_z;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setBehind(boolean behind) {
/* 37 */     this.m_behind = behind;
/*    */   }
/*    */   
/*    */   public boolean isBehind() {
/* 41 */     return this.m_behind;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\lines\Segment.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */