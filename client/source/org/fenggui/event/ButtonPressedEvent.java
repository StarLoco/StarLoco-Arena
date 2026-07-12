/*    */ package org.fenggui.event;
/*    */ 
/*    */ import org.fenggui.Button;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ButtonPressedEvent
/*    */   extends Event
/*    */ {
/*    */   private Button button;
/*    */   
/*    */   public ButtonPressedEvent(Button trigger) {
/* 39 */     super((IWidget)trigger);
/* 40 */     this.button = trigger;
/*    */   }
/*    */   
/*    */   public Button getTrigger() {
/* 44 */     return this.button;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\ButtonPressedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */