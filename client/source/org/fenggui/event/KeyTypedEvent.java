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
/*    */ public class KeyTypedEvent
/*    */   extends KeyEvent
/*    */ {
/*    */   public KeyTypedEvent(IWidget source, char key) {
/* 27 */     super(source, key, Key.UNDEFINED);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\KeyTypedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */