/*    */ package com.ankamagames.xulor.event;
/*    */ 
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.template.IDragNDropable;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import com.ankamagames.xulor.template.IItemRenderable;
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
/*    */ public class DragEvent
/*    */   extends Event
/*    */ {
/* 20 */   private IDragNDropable m_component = null;
/* 21 */   private Object m_sourceValue = null;
/* 22 */   private Object m_value = null;
/*    */ 
/*    */   
/*    */   public DragEvent() {}
/*    */   
/*    */   public DragEvent(IDragNDropable component, Object value) {
/* 28 */     setDragNDropable(component);
/* 29 */     this.m_value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IElement getElement() {
/* 37 */     if (this.m_component instanceof IElement) {
/* 38 */       return (IElement)this.m_component;
/*    */     }
/* 40 */     return null;
/*    */   }
/*    */   
/*    */   public IDragNDropable getDragNDropable() {
/* 44 */     return this.m_component;
/*    */   }
/*    */   
/*    */   public void setDragNDropable(IDragNDropable dnd) {
/* 48 */     if (dnd instanceof IElement) {
/* 49 */       this.m_component = dnd;
/*    */     } else {
/* 51 */       IElement elem = Xulor.getInstance().getEnvironment().getElementByWidget(dnd);
/* 52 */       if (elem instanceof IDragNDropable) {
/* 53 */         this.m_component = (IDragNDropable)elem;
/*    */       }
/*    */     } 
/* 56 */     if (dnd != null) {
/* 57 */       IItemRenderable renderable = dnd.getRenderableParent();
/* 58 */       if (renderable != null) {
/* 59 */         this.m_sourceValue = renderable.getItemValue();
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getSourceValue() {
/* 68 */     return this.m_sourceValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSourceValue(Object value) {
/* 75 */     this.m_sourceValue = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getValue() {
/* 82 */     return this.m_value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setValue(Object value) {
/* 89 */     this.m_value = value;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\DragEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */