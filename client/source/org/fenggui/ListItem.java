/*    */ package org.fenggui;
/*    */ 
/*    */ import org.fenggui.render.Pixmap;
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
/*    */ public class ListItem<E>
/*    */   extends Item
/*    */   implements IToggable<E>
/*    */ {
/* 35 */   private E value = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean isSelected = false;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ListItem(String text, Pixmap pixmap, E value) {
/* 46 */     super(text, pixmap);
/* 47 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public ListItem(String text, E value) {
/* 52 */     this(text, null, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public ListItem(String text, Pixmap pixmap) {
/* 57 */     this(text, pixmap, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public ListItem(String text) {
/* 62 */     this(text, null, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public ListItem() {
/* 67 */     this(null, null, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSelected() {
/* 72 */     return this.isSelected;
/*    */   }
/*    */ 
/*    */   
/*    */   public ListItem<E> setSelected(boolean b) {
/* 77 */     this.isSelected = b;
/* 78 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public E getValue() {
/* 83 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(E value) {
/* 88 */     this.value = value;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\ListItem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */