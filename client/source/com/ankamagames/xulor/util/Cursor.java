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
/*    */ public class Cursor
/*    */ {
/* 15 */   private int m_hotspotX = 0;
/* 16 */   private int m_hotspotY = 0;
/* 17 */   private CursorType m_type = CursorType.DEFAULT;
/* 18 */   private ThemeTexture m_texture = null;
/*    */   
/*    */   public enum CursorType {
/* 21 */     DEFAULT, MOVE, TEXT,
/* 22 */     HORIZONTAL_RESIZE, VERTICAL_RESIZE,
/* 23 */     NW_RESIZE, SW_RESIZE, HAND, FORBIDDEN;
/*    */   }
/*    */   
/*    */   public Cursor(ThemeTexture texture, CursorType type, int x, int y) {
/* 27 */     this.m_texture = texture;
/* 28 */     this.m_hotspotX = x;
/* 29 */     this.m_hotspotY = y;
/* 30 */     this.m_type = type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getHotspotX() {
/* 37 */     return this.m_hotspotX;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setHotspotX(int hotspotX) {
/* 44 */     this.m_hotspotX = hotspotX;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getHotspotY() {
/* 51 */     return this.m_hotspotY;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setHotspotY(int hotspotY) {
/* 58 */     this.m_hotspotY = hotspotY;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ThemeTexture getTexture() {
/* 65 */     return this.m_texture;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setTexture(ThemeTexture texture) {
/* 72 */     this.m_texture = texture;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CursorType getType() {
/* 79 */     return this.m_type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setType(CursorType type) {
/* 86 */     this.m_type = type;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulo\\util\Cursor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */