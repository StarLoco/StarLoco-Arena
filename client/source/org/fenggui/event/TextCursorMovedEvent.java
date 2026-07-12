/*    */ package org.fenggui.event;
/*    */ 
/*    */ import org.fenggui.IWidget;
/*    */ import org.fenggui.Widget;
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
/*    */ public class TextCursorMovedEvent
/*    */   extends Event
/*    */ {
/*    */   public static final int DOWN = 0;
/*    */   public static final int UP = 1;
/*    */   public static final int LEFT = 2;
/*    */   public static final int RIGHT = 3;
/*    */   private int direction;
/*    */   private int newIndex;
/*    */   private boolean select;
/*    */   
/*    */   public TextCursorMovedEvent(Widget source, int direction, int newIndex, boolean select) {
/* 40 */     super((IWidget)source);
/* 41 */     this.direction = direction;
/* 42 */     this.newIndex = newIndex;
/* 43 */     this.select = select;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDirection() {
/* 48 */     return this.direction;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getNewIndex() {
/* 53 */     return this.newIndex;
/*    */   }
/*    */   
/*    */   public boolean isSelect() {
/* 57 */     return this.select;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\TextCursorMovedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */