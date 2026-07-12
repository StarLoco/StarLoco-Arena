/*    */ package org.fenggui.event;
/*    */ 
/*    */ import org.fenggui.IWidget;
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
/*    */ public class FocusEvent
/*    */   extends Event
/*    */ {
/*    */   private boolean focusLost = false;
/*    */   
/*    */   public FocusEvent(IWidget source, boolean focusLost) {
/* 30 */     super(source);
/* 31 */     this.focusLost = focusLost;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFocusLost() {
/* 36 */     return this.focusLost;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFocusGained() {
/* 41 */     return !this.focusLost;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\FocusEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */