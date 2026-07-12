/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.console;
/*    */ 
/*    */ import java.util.ListIterator;
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
/*    */ public abstract class AbstractInputHistoryManager
/*    */ {
/* 30 */   private Stack<String> m_history = new Stack<String>();
/*    */ 
/*    */   
/*    */   private ListIterator<String> m_historyIterator;
/*    */ 
/*    */   
/*    */   public void clear() {
/* 37 */     this.m_history.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getHistoryUp() {
/* 44 */     if (this.m_historyIterator != null && this.m_historyIterator.hasPrevious()) {
/* 45 */       return this.m_historyIterator.previous();
/*    */     }
/* 47 */     return "";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getHistoryDown() {
/* 54 */     if (this.m_historyIterator != null && this.m_historyIterator.hasNext()) {
/* 55 */       return this.m_historyIterator.next();
/*    */     }
/* 57 */     return "";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void pushToHistory(String input) {
/* 67 */     if (!this.m_history.isEmpty()) {
/* 68 */       String lastInput = this.m_history.lastElement();
/* 69 */       if (lastInput == null || !lastInput.equals(input)) {
/* 70 */         this.m_history.push(input);
/*    */       }
/*    */     } else {
/* 73 */       this.m_history.push(input);
/*    */     } 
/* 75 */     this.m_historyIterator = this.m_history.listIterator(this.m_history.size());
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\console\AbstractInputHistoryManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */