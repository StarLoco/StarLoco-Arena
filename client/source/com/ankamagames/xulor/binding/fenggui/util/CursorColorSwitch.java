/*    */ package com.ankamagames.xulor.binding.fenggui.util;
/*    */ 
/*    */ import org.fenggui.IAppearance;
/*    */ import org.fenggui.IWidget;
/*    */ import org.fenggui.StandardWidget;
/*    */ import org.fenggui.Switch;
/*    */ import org.fenggui.TextEditorAppearance;
/*    */ import org.fenggui.util.Color;
/*    */ 
/*    */ 
/*    */ public class CursorColorSwitch
/*    */   extends Switch
/*    */ {
/*    */   Color m_CursorColor;
/*    */   
/*    */   public CursorColorSwitch(String state, Color color) {
/* 17 */     super(state);
/* 18 */     this.m_CursorColor = color;
/*    */   }
/*    */   
/*    */   public void setup(IWidget widget) {
/* 22 */     IAppearance appearance = ((StandardWidget)widget).getAppearance();
/* 23 */     if (appearance instanceof TextEditorAppearance)
/* 24 */       ((TextEditorAppearance)appearance).getCursorPainter().setCursorColor(this.m_CursorColor); 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggu\\util\CursorColorSwitch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */