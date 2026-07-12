/*    */ package com.ankamagames.xulor.binding.fenggui.util;
/*    */ 
/*    */ import org.fenggui.IAppearance;
/*    */ import org.fenggui.IWidget;
/*    */ import org.fenggui.LabelAppearance;
/*    */ import org.fenggui.StandardWidget;
/*    */ import org.fenggui.Switch;
/*    */ import org.fenggui.TextEditorAppearance;
/*    */ import org.fenggui.render.Font;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FontSwitch
/*    */   extends Switch
/*    */ {
/*    */   Font m_font;
/*    */   
/*    */   public FontSwitch(String state, Font font)
/*    */   {
/* 26 */     super(state);
/* 27 */     this.m_font = font;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setup(IWidget widget)
/*    */   {
/* 35 */     IAppearance appearance = ((StandardWidget)widget).getAppearance();
/* 36 */     if ((appearance instanceof TextEditorAppearance)) {
/* 37 */       ((TextEditorAppearance)appearance).setFont(this.m_font);
/* 38 */     } else if ((appearance instanceof LabelAppearance)) {
/* 39 */       ((LabelAppearance)appearance).setFont(this.m_font);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\util\FontSwitch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */