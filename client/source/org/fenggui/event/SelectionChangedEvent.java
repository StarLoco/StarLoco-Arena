/*    */ package org.fenggui.event;
/*    */ 
/*    */ import org.fenggui.IToggable;
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
/*    */ public class SelectionChangedEvent
/*    */   extends Event
/*    */ {
/* 27 */   private IToggable w = null;
/*    */   
/*    */   private boolean st;
/*    */   
/*    */   public SelectionChangedEvent(IWidget source, IToggable wi, boolean newState) {
/* 32 */     super(source);
/* 33 */     this.w = wi;
/* 34 */     this.st = newState;
/*    */   }
/*    */   
/*    */   public boolean isSelected() {
/* 38 */     return this.st;
/*    */   }
/*    */   
/*    */   public IToggable getToggableWidget() {
/* 42 */     return this.w;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\SelectionChangedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */