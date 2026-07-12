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
/*    */ public class KeyEvent
/*    */   extends Event
/*    */ {
/*    */   private char key;
/*    */   private Key keyClass;
/*    */   
/*    */   public KeyEvent(IWidget source, char key, Key keyClass) {
/* 31 */     super(source);
/* 32 */     this.key = key;
/* 33 */     this.keyClass = keyClass;
/*    */   }
/*    */ 
/*    */   
/*    */   public char getKey() {
/* 38 */     return this.key;
/*    */   }
/*    */ 
/*    */   
/*    */   public Key getKeyClass() {
/* 43 */     return this.keyClass;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\KeyEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */