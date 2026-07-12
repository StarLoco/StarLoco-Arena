/*    */ package com.ankamagames.xulor.binding.fenggui.util;
/*    */ 
/*    */ import com.ankamagames.xulor.template.ITogglable;
/*    */ import org.fenggui.IToggable;
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
/*    */ public class Togglable
/*    */   implements ITogglable
/*    */ {
/*    */   private IToggable m_toggable;
/*    */   
/*    */   public Togglable(IToggable toggable) {
/* 21 */     this.m_toggable = toggable;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getText() {
/* 28 */     return this.m_toggable.getText();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getValue() {
/* 35 */     return this.m_toggable.getValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getSelected() {
/* 42 */     return this.m_toggable.isSelected();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ITogglable setSelected(boolean b) {
/* 49 */     this.m_toggable.setSelected(b);
/* 50 */     return this;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggu\\util\Togglable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */