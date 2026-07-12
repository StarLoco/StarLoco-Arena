/*    */ package com.ankamagames.xulor.binding.fenggui.component;
/*    */ 
/*    */ import com.ankamagames.xulor.template.IItemRenderable;
/*    */ import com.ankamagames.xulor.util.Item;
/*    */ import org.fenggui.event.Event;
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
/*    */ public class ListSelectionChangedEvent
/*    */   extends Event
/*    */ {
/* 19 */   private IItemRenderable m_renderable = null;
/* 20 */   private Item m_value = null;
/*    */   private boolean m_selected;
/*    */   
/*    */   public ListSelectionChangedEvent(List list, IItemRenderable renderable, Item value, boolean selected) {
/* 24 */     super(list);
/* 25 */     this.m_renderable = renderable;
/* 26 */     this.m_value = value;
/* 27 */     this.m_selected = selected;
/*    */   }
/*    */   
/*    */   public ListSelectionChangedEvent(List list) {
/* 31 */     super(list);
/*    */   }
/*    */   
/*    */   public void setItemRenderable(IItemRenderable renderable)
/*    */   {
/* 36 */     this.m_renderable = renderable;
/*    */   }
/*    */   
/*    */   public IItemRenderable getItemRenderable() {
/* 40 */     return this.m_renderable;
/*    */   }
/*    */   
/*    */   public void setSelected(boolean selected) {
/* 44 */     this.m_selected = selected;
/*    */   }
/*    */   
/*    */   public boolean getSelected() {
/* 48 */     return this.m_selected;
/*    */   }
/*    */   
/*    */   public void setValue(Item value) {
/* 52 */     this.m_value = value;
/*    */   }
/*    */   
/*    */   public Item getValue() {
/* 56 */     return this.m_value;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\ListSelectionChangedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */