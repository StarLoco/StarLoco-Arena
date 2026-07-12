/*    */ package org.fenggui.event;
/*    */ 
/*    */ import org.fenggui.IWidget;
/*    */ import org.fenggui.menu.Menu;
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
/*    */ public class MenuClosedEvent
/*    */   extends Event
/*    */ {
/*    */   private Menu menu;
/*    */   
/*    */   public MenuClosedEvent(Menu m) {
/* 35 */     super((IWidget)m);
/* 36 */     this.menu = m;
/*    */   }
/*    */ 
/*    */   
/*    */   public Menu getMenu() {
/* 41 */     return this.menu;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\MenuClosedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */