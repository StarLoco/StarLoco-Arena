/*     */ package com.ankamagames.xulor.event;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
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
/*     */ 
/*     */ public class DragOutEvent
/*     */   extends Event
/*     */ {
/*  20 */   private IDragNDropable m_source = null;
/*  21 */   private Object m_sourceValue = null;
/*  22 */   private Object m_value = null;
/*  23 */   private IDragNDropable m_out = null;
/*  24 */   private Object m_outValue = null;
/*     */ 
/*     */   
/*     */   public DragOutEvent() {}
/*     */   
/*     */   public DragOutEvent(IDragNDropable source, IDragNDropable out, Object value) {
/*  30 */     setSource(source);
/*  31 */     setOut(out);
/*  32 */     this.m_value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement getElement() {
/*  40 */     if (this.m_source instanceof IElement) {
/*  41 */       return (IElement)this.m_source;
/*     */     }
/*  43 */     return null;
/*     */   }
/*     */   
/*     */   public IDragNDropable getSource() {
/*  47 */     return this.m_source;
/*     */   }
/*     */   
/*     */   public void setSource(IDragNDropable dnd) {
/*  51 */     if (dnd instanceof IElement) {
/*  52 */       this.m_source = dnd;
/*     */     } else {
/*  54 */       IElement elem = Xulor.getInstance().getEnvironment().getElementByWidget(dnd);
/*  55 */       if (elem instanceof IDragNDropable) {
/*  56 */         this.m_source = (IDragNDropable)elem;
/*     */       }
/*     */     } 
/*  59 */     if (dnd != null) {
/*  60 */       IItemRenderable renderable = dnd.getRenderableParent();
/*  61 */       if (renderable != null) {
/*  62 */         this.m_sourceValue = renderable.getItemValue();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public IDragNDropable getOut() {
/*  68 */     return this.m_out;
/*     */   }
/*     */   
/*     */   public void setOut(IDragNDropable dnd) {
/*  72 */     if (dnd instanceof IElement) {
/*  73 */       this.m_out = dnd;
/*     */     } else {
/*  75 */       IElement elem = Xulor.getInstance().getEnvironment().getElementByWidget(dnd);
/*  76 */       if (elem instanceof IDragNDropable) {
/*  77 */         this.m_out = (IDragNDropable)elem;
/*     */       }
/*     */     } 
/*  80 */     if (dnd != null) {
/*  81 */       IItemRenderable renderable = dnd.getRenderableParent();
/*  82 */       if (renderable != null) {
/*  83 */         this.m_outValue = renderable.getItemValue();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getSourceValue() {
/*  92 */     return this.m_sourceValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getOutValue() {
/* 106 */     return this.m_outValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue() {
/* 120 */     return this.m_value;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\DragOutEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */