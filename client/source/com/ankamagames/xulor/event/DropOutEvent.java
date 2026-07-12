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
/*    */ 
/*    */ public class DropOutEvent
/*    */   extends Event
/*    */ {
/*    */   IDragNDropable m_component;
/*    */   Object m_sourceValue;
/*    */   Object m_value;
/*    */   
/*    */   public DropOutEvent() {}
/*    */   
/*    */   public DropOutEvent(IDragNDropable component, Object value) {
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
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IDragNDropable getDragNDropable() {
/* 48 */     return this.m_component;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDragNDropable(IDragNDropable dnd) {
/* 56 */     if (dnd instanceof IElement) {
/* 57 */       this.m_component = dnd;
/*    */     } else {
/* 59 */       IElement elem = Xulor.getInstance().getEnvironment().getElementByWidget(dnd);
/* 60 */       if (elem instanceof IDragNDropable) {
/* 61 */         this.m_component = (IDragNDropable)elem;
/*    */       }
/*    */     } 
/* 64 */     if (dnd != null) {
/* 65 */       IItemRenderable renderable = dnd.getRenderableParent();
/* 66 */       if (renderable != null) {
/* 67 */         this.m_sourceValue = renderable.getItemValue();
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getSourceValue() {
/* 76 */     return this.m_sourceValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSourceValue(Object value) {
/* 83 */     this.m_sourceValue = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getValue() {
/* 90 */     return this.m_value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setValue(Object value) {
/* 97 */     this.m_value = value;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\DropOutEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */