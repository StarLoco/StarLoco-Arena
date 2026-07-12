/*    */ package com.ankamagames.xulor.binding.fenggui.util;
/*    */ 
/*    */ import org.fenggui.IAppearance;
/*    */ import org.fenggui.IWidget;
/*    */ import org.fenggui.LabelAppearance;
/*    */ import org.fenggui.StandardWidget;
/*    */ import org.fenggui.Switch;
/*    */ import org.fenggui.layout.Alignment;
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
/*    */ public class AlignmentSwitch
/*    */   extends Switch
/*    */ {
/*    */   private Alignment m_alignment;
/*    */   
/*    */   public AlignmentSwitch(String state, Alignment alignment) {
/* 27 */     super(state);
/* 28 */     this.m_alignment = alignment;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setup(IWidget widget) {
/* 36 */     IAppearance appearance = ((StandardWidget)widget).getAppearance();
/* 37 */     if (appearance instanceof LabelAppearance)
/* 38 */       ((LabelAppearance)appearance).setAlignment(this.m_alignment); 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggu\\util\AlignmentSwitch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */