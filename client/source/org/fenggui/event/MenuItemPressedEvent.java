/*    */ package org.fenggui.event;
/*    */ 
/*    */ import org.fenggui.IWidget;
/*    */ import org.fenggui.menu.Menu;
/*    */ import org.fenggui.menu.MenuItem;
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
/*    */ public class MenuItemPressedEvent
/*    */   extends Event
/*    */ {
/* 32 */   private MenuItem item = null;
/*    */ 
/*    */   
/*    */   public MenuItemPressedEvent(Menu source, MenuItem i) {
/* 36 */     super((IWidget)source);
/* 37 */     this.item = i;
/*    */   }
/*    */ 
/*    */   
/*    */   public MenuItem getItem() {
/* 42 */     return this.item;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\MenuItemPressedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */