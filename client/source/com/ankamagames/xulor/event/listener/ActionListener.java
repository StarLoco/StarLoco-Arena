/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.event.IMouseClickListener;
/*    */ import com.ankamagames.xulor.event.MouseButtons;
/*    */ import com.ankamagames.xulor.event.MouseClickEvent;
/*    */ import com.ankamagames.xulor.shortcuts.ShortcutTrigger;
/*    */ import com.ankamagames.xulor.template.IComponent;
/*    */ import com.ankamagames.xulor.template.IObservable;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class ActionListener
/*    */   implements ShortcutTrigger
/*    */ {
/* 23 */   private static Logger m_logger = Logger.getLogger(ActionListener.class);
/*    */   
/* 25 */   private String m_actionTrigger = null;
/* 26 */   private IObservable m_element = null;
/*    */   
/*    */   public ActionListener(IObservable element, String actionTrigger) {
/* 29 */     this.m_actionTrigger = actionTrigger;
/* 30 */     this.m_element = element;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getActionTrigger() {
/* 38 */     return this.m_actionTrigger;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 46 */     if (this.m_actionTrigger == null) {
/* 47 */       m_logger.error("actionTrigger est nul."); return;
/*    */     } 
/* 49 */     if (this.m_actionTrigger.equalsIgnoreCase("click") && this.m_element.isEnabled()) {
/* 50 */       MouseClickEvent event = new MouseClickEvent((IComponent)this.m_element, 0, 0, 1, MouseButtons.BUTTON1);
/* 51 */       for (IMouseClickListener mcl : this.m_element.getOnClick()) {
/* 52 */         mcl.run(event);
/*    */       }
/*    */     } else {
/* 55 */       m_logger.error("Le type d'action \"" + this.m_actionTrigger + "\" n'est pas encore géré");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\ActionListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */