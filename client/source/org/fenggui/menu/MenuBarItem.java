/*    */ package org.fenggui.menu;
/*    */ 
/*    */ import org.fenggui.render.BufferedTextRenderer;
/*    */ import org.fenggui.render.ITextRenderer;
/*    */ 
/*    */ 
/*    */ public class MenuBarItem
/*    */ {
/*  9 */   private Menu menu = null;
/* 10 */   private ITextRenderer textRenderer = (ITextRenderer)new BufferedTextRenderer();
/* 11 */   private int width = 0;
/*    */ 
/*    */ 
/*    */   
/*    */   protected MenuBarItem(Menu menu, String name) {
/* 16 */     this.menu = menu;
/* 17 */     this.textRenderer.setText(name);
/* 18 */     this.width = this.textRenderer.getWidth();
/*    */   }
/*    */ 
/*    */   
/*    */   public Menu getMenu() {
/* 23 */     return this.menu;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 29 */     return this.textRenderer.getText();
/*    */   }
/*    */ 
/*    */   
/*    */   public ITextRenderer getTextRenderer() {
/* 34 */     return this.textRenderer;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 39 */     return this.width;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\menu\MenuBarItem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */