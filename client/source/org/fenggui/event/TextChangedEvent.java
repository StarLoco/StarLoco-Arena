/*    */ package org.fenggui.event;
/*    */ 
/*    */ import org.fenggui.ITextWidget;
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
/*    */ 
/*    */ public class TextChangedEvent
/*    */   extends Event
/*    */ {
/*    */   private ITextWidget trigger;
/*    */   private String text;
/*    */   
/*    */   public TextChangedEvent(ITextWidget trigger, String text) {
/* 41 */     super((IWidget)trigger);
/* 42 */     this.trigger = trigger;
/* 43 */     this.text = text;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ITextWidget getTrigger() {
/* 50 */     return this.trigger;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getText() {
/* 58 */     return this.text;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\TextChangedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */