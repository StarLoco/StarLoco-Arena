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
/*    */ public class ActivationEvent
/*    */   extends Event
/*    */ {
/*    */   private boolean enabled = true;
/*    */   
/*    */   public ActivationEvent(IWidget source, boolean enabled) {
/* 30 */     super(source);
/* 31 */     this.enabled = enabled;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnabled() {
/* 36 */     return this.enabled;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\ActivationEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */