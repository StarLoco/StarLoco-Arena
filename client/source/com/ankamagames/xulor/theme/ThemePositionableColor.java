/*    */ package com.ankamagames.xulor.theme;
/*    */ 
/*    */ import com.ankamagames.xulor.util.Alignment;
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
/*    */ 
/*    */ 
/*    */ public class ThemePositionableColor
/*    */   extends ThemeColor
/*    */   implements IThemeElement, IPositionable
/*    */ {
/*    */   public static final String TAG = "PositionableColor";
/*    */   private Alignment m_pos;
/*    */   
/*    */   public void add(IThemeElement element) {
/* 25 */     if (element instanceof ThemeColor) {
/* 26 */       setColor(((ThemeColor)element).getColor());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Alignment getPosition() {
/* 34 */     return this.m_pos;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPosition(Alignment pos) {
/* 41 */     this.m_pos = pos;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemePositionableColor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */