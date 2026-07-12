/*    */ package com.ankamagames.xulor.binding;
/*    */ 
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import java.util.Stack;
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
/*    */ public class ModalManager
/*    */ {
/* 18 */   private static ModalManager m_modalManager = new ModalManager();
/*    */   
/* 20 */   public static short BOTTOM_MODAL_LEVEL = 1;
/* 21 */   public static short TOP_MODAL_LEVEL = 10000;
/* 22 */   public static short MSG_BOX_MODAL_LEVEL = 20000;
/* 23 */   public static short POP_UP_MODAL_LEVEL = 30000;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 28 */   private Stack<IElement> m_modals = new Stack();
/* 29 */   private short m_maxModalLevel = 0;
/*    */   
/*    */ 
/*    */ 
/*    */   public static ModalManager getInstance()
/*    */   {
/* 35 */     return m_modalManager;
/*    */   }
/*    */   
/*    */   public void addPseudoModalElement(IElement element) {
/* 39 */     if (element.getModalLevel() > this.m_maxModalLevel) {
/* 40 */       this.m_maxModalLevel = element.getModalLevel();
/*    */     }
/* 42 */     this.m_modals.push(element);
/*    */   }
/*    */   
/*    */   public void addModalElement(IElement element) {
/* 46 */     if (this.m_maxModalLevel + 1 < MSG_BOX_MODAL_LEVEL) {
/* 47 */       this.m_maxModalLevel = MSG_BOX_MODAL_LEVEL;
/*    */     } else {
/* 49 */       this.m_maxModalLevel = ((short)(this.m_maxModalLevel + 1));
/*    */     }
/*    */     
/* 52 */     element.setModalLevel(this.m_maxModalLevel);
/* 53 */     this.m_modals.push(element);
/*    */   }
/*    */   
/*    */   public void removeElement(IElement element)
/*    */   {
/* 58 */     if (element.getModalLevel() == this.m_maxModalLevel) {
/* 59 */       this.m_maxModalLevel = 0;
/* 60 */       for (IElement stackElement : this.m_modals) {
/* 61 */         if (stackElement.getModalLevel() > this.m_maxModalLevel) {
/* 62 */           this.m_maxModalLevel = stackElement.getModalLevel();
/*    */         }
/*    */       }
/*    */     }
/* 66 */     this.m_modals.remove(element);
/*    */   }
/*    */   
/*    */   public boolean sendEventToDisplay(int x, int y) {
/* 70 */     if (!this.m_modals.isEmpty()) {
/* 71 */       return Xulor.getInstance().getScene().getModalLevelAt(x, y) >= ((IElement)this.m_modals.peek()).getModalLevel();
/*    */     }
/* 73 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 77 */     return this.m_modals.isEmpty();
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\ModalManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */