/*    */ package com.ankamagames.xulor.core.messagebox;
/*    */ 
/*    */ import com.ankamagames.xulor.template.IMessageBox;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class MessageBoxControler
/*    */ {
/* 19 */   private IMessageBox m_messageBox = null;
/* 20 */   private String m_messageBoxId = null;
/*    */   
/*    */ 
/*    */   private List<IMessageBoxEventListener> m_listeners;
/*    */   
/*    */ 
/*    */ 
/*    */   public MessageBoxControler(String id, IMessageBox messageBox)
/*    */   {
/* 29 */     this.m_messageBoxId = id;
/* 30 */     this.m_messageBox = messageBox;
/* 31 */     this.m_listeners = new ArrayList();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getMessageBoxId()
/*    */   {
/* 38 */     return this.m_messageBoxId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public IMessageBox getMessageBox()
/*    */   {
/* 45 */     return this.m_messageBox;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void messageBoxClosed(int type)
/*    */   {
/* 54 */     for (IMessageBoxEventListener listener : this.m_listeners) {
/* 55 */       listener.messageBoxClosed(type);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void addEventListener(IMessageBoxEventListener listener)
/*    */   {
/* 65 */     this.m_listeners.add(listener);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void removeEventListener(IMessageBoxEventListener listener)
/*    */   {
/* 74 */     this.m_listeners.remove(listener);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\messagebox\MessageBoxControler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */