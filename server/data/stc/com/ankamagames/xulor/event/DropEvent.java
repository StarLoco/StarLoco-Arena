/*     */ package com.ankamagames.xulor.event;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.template.IDragNDropable;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IItemRenderable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DropEvent
/*     */   extends Event
/*     */ {
/*     */   private IDragNDropable m_destination;
/*     */   private IDragNDropable m_source;
/*     */   private Object m_sourceValue;
/*     */   private Object m_destinationValue;
/*     */   private Object m_value;
/*     */   
/*     */   public DropEvent() {}
/*     */   
/*     */   public DropEvent(IDragNDropable destination, IDragNDropable source, Object value)
/*     */   {
/*  30 */     setDropped(source);
/*  31 */     setDroppedInto(destination);
/*  32 */     this.m_value = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IElement getElement()
/*     */   {
/*  40 */     if ((this.m_destination instanceof IElement)) {
/*  41 */       return (IElement)this.m_destination;
/*     */     }
/*  43 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IDragNDropable getDropped()
/*     */   {
/*  51 */     return this.m_source;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDropped(IDragNDropable dropped)
/*     */   {
/*  59 */     if ((dropped instanceof IElement)) {
/*  60 */       this.m_source = dropped;
/*     */     } else {
/*  62 */       IElement elem = Xulor.getInstance().getEnvironment().getElementByWidget(dropped);
/*  63 */       if ((elem instanceof IDragNDropable)) {
/*  64 */         this.m_source = ((IDragNDropable)elem);
/*     */       }
/*     */     }
/*  67 */     if (dropped != null) {
/*  68 */       IItemRenderable renderable = dropped.getRenderableParent();
/*  69 */       if (renderable != null) {
/*  70 */         this.m_sourceValue = renderable.getItemValue();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IDragNDropable getDroppedInto()
/*     */   {
/*  80 */     return this.m_destination;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDroppedInto(IDragNDropable droppedInto)
/*     */   {
/*  88 */     if ((droppedInto instanceof IElement)) {
/*  89 */       this.m_destination = droppedInto;
/*     */     } else {
/*  91 */       IElement elem = Xulor.getInstance().getEnvironment().getElementByWidget(droppedInto);
/*  92 */       if ((elem instanceof IDragNDropable)) {
/*  93 */         this.m_destination = ((IDragNDropable)elem);
/*     */       }
/*     */     }
/*  96 */     if (droppedInto != null) {
/*  97 */       IItemRenderable renderable = droppedInto.getRenderableParent();
/*  98 */       if (renderable != null) {
/*  99 */         this.m_destinationValue = renderable.getItemValue();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getDestinationValue()
/*     */   {
/* 109 */     return this.m_destinationValue;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDestinationValue(Object componentValue)
/*     */   {
/* 116 */     this.m_destinationValue = componentValue;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object getSourceValue()
/*     */   {
/* 123 */     return this.m_sourceValue;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setSourceValue(Object sourceValue)
/*     */   {
/* 130 */     this.m_sourceValue = sourceValue;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object getValue()
/*     */   {
/* 137 */     return this.m_value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setValue(Object value)
/*     */   {
/* 144 */     this.m_value = value;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\DropEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */