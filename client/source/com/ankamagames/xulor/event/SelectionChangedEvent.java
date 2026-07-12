/*    */ package com.ankamagames.xulor.event;
/*    */ 
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import com.ankamagames.xulor.template.ISelection;
/*    */ import com.ankamagames.xulor.util.Item;
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
/*    */   private ISelection m_selection;
/*    */   private boolean m_selected;
/*    */   private IElement m_element;
/*    */   private Item m_item;
/*    */   
/*    */   public SelectionChangedEvent(IElement element) {
/* 24 */     this.m_element = element;
/*    */   }
/*    */   
/*    */   public SelectionChangedEvent(IElement element, ISelection togglable, boolean selected, Item value) {
/* 28 */     this.m_element = element;
/* 29 */     this.m_selection = togglable;
/* 30 */     this.m_selected = selected;
/* 31 */     this.m_item = value;
/*    */   }
/*    */   
/*    */   public void setItem(Item value) {
/* 35 */     this.m_item = value;
/*    */   }
/*    */   
/*    */   public Item getItem() {
/* 39 */     return this.m_item;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSelected() {
/* 46 */     return this.m_selected;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSelected(boolean selected) {
/* 53 */     this.m_selected = selected;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ISelection getSelection() {
/* 60 */     return this.m_selection;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSelection(ISelection togglable) {
/* 67 */     this.m_selection = togglable;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IElement getElement() {
/* 76 */     return this.m_element;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\SelectionChangedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */