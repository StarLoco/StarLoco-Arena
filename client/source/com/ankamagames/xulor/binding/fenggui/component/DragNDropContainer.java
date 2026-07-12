/*     */ package com.ankamagames.xulor.binding.fenggui.component;
/*     */ 
/*     */ import com.ankamagames.xulor.event.DragEvent;
/*     */ import com.ankamagames.xulor.event.DragOutEvent;
/*     */ import com.ankamagames.xulor.event.DragOverEvent;
/*     */ import com.ankamagames.xulor.event.DropEvent;
/*     */ import com.ankamagames.xulor.event.DropOutEvent;
/*     */ import com.ankamagames.xulor.event.DropValidateCallBack;
/*     */ import com.ankamagames.xulor.event.IDragListener;
/*     */ import com.ankamagames.xulor.event.IDropListener;
/*     */ import com.ankamagames.xulor.event.IDropOutListener;
/*     */ import com.ankamagames.xulor.event.listener.DragListener;
/*     */ import com.ankamagames.xulor.event.listener.DragOutListener;
/*     */ import com.ankamagames.xulor.event.listener.DragOverListener;
/*     */ import com.ankamagames.xulor.event.listener.DropListener;
/*     */ import com.ankamagames.xulor.event.listener.DropOutListener;
/*     */ import com.ankamagames.xulor.template.IDragNDropable;
/*     */ import com.ankamagames.xulor.template.IItemRenderable;
/*     */ import com.ankamagames.xulor.util.Item;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DragNDropContainer
/*     */   extends Container
/*     */   implements IDragNDropable
/*     */ {
/*     */   private IItemRenderable m_renderableParent;
/*  34 */   private DragOutListener m_dragOutListener = null;
/*  35 */   private DragOverListener m_dragOverListener = null;
/*  36 */   private DragListener m_dragListener = null;
/*  37 */   private DropListener m_dropListener = null;
/*  38 */   private DropOutListener m_dropOutListener = null;
/*  39 */   private DropValidateCallBack m_dropValidateCallBack = null;
/*     */   
/*     */   public DragNDropContainer() {
/*  42 */     setNonBlocking(true);
/*     */   }
/*     */   
/*     */   public IItemRenderable getRenderableParent() {
/*  46 */     return this.m_renderableParent;
/*     */   }
/*     */   
/*     */   public void setRenderableParent(IItemRenderable renderable) {
/*  50 */     this.m_renderableParent = renderable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCompatible(Item item) {
/*  59 */     if (this.m_renderableParent != null && this.m_renderableParent.getRenderer() != null) {
/*  60 */       return this.m_renderableParent.getRenderer().isCompatible(item);
/*     */     }
/*  62 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnDrag(IDragListener l) {
/*  71 */     this.m_dragListener = (DragListener)l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnDrop(IDropListener l) {
/*  80 */     this.m_dropListener = (DropListener)l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnDropOut(IDropOutListener l) {
/*  89 */     this.m_dropOutListener = (DropOutListener)l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnDragOut(DragOutListener l) {
/*  96 */     this.m_dragOutListener = l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnDragOver(DragOverListener l) {
/* 103 */     this.m_dragOverListener = l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValidateDrop(DropValidateCallBack c) {
/* 112 */     this.m_dropValidateCallBack = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireDrag(Object value) {
/* 121 */     if (this.m_dragListener != null) {
/* 122 */       DragEvent event = new DragEvent(this, value);
/* 123 */       this.m_dragListener.run(event);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireDrop(IDragNDropable source, Object value) {
/* 133 */     if (this.m_dropListener != null) {
/* 134 */       DropEvent event = new DropEvent(this, source, value);
/* 135 */       this.m_dropListener.run(event);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireDropOut(Object value) {
/* 145 */     if (this.m_dropOutListener != null) {
/* 146 */       DropOutEvent event = new DropOutEvent(this, value);
/* 147 */       this.m_dropOutListener.run(event);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireDragOut(IDragNDropable out, Object value) {
/* 155 */     if (this.m_dragOutListener != null) {
/* 156 */       DragOutEvent event = new DragOutEvent(this, out, value);
/* 157 */       this.m_dragOutListener.run(event);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireDragOver(IDragNDropable over, Object value) {
/* 165 */     if (this.m_dragOverListener != null) {
/* 166 */       DragOverEvent event = new DragOverEvent(this, over, value);
/* 167 */       this.m_dragOverListener.run(event);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDropValid(IDragNDropable source, Object value) {
/* 177 */     if (this.m_dropValidateCallBack != null) {
/* 178 */       Object result = this.m_dropValidateCallBack.invokeCallBack(source, this, value);
/* 179 */       if (result != null) {
/* 180 */         return ((Boolean)result).booleanValue();
/*     */       }
/*     */     } 
/* 183 */     return true;
/*     */   }
/*     */   
/*     */   public void removedFromWidgetTree() {
/* 187 */     super.removedFromWidgetTree();
/* 188 */     this.m_renderableParent = null;
/* 189 */     this.m_dragListener = null;
/* 190 */     this.m_dragOutListener = null;
/* 191 */     this.m_dragOverListener = null;
/* 192 */     this.m_dropListener = null;
/* 193 */     this.m_dropOutListener = null;
/* 194 */     this.m_dropValidateCallBack = null;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\DragNDropContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */