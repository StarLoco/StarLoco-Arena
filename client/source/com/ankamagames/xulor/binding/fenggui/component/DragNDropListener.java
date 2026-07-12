/*     */ package com.ankamagames.xulor.binding.fenggui.component;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.template.IContainer;
/*     */ import com.ankamagames.xulor.template.IDragNDropable;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IItemRenderable;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.fenggui.Display;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.Widget;
/*     */ import org.fenggui.event.IDragAndDropListener;
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
/*     */ public class DragNDropListener
/*     */   implements IDragAndDropListener
/*     */ {
/*  28 */   private static Logger m_logger = Logger.getLogger(DragNDropListener.class);
/*     */   
/*  30 */   private IDragNDropable m_sourceDND = null;
/*  31 */   private IWidget m_over = null;
/*     */   private IItemRenderable m_sourceRenderable;
/*     */   private IComponent m_copyDND;
/*     */   private boolean m_dragged;
/*  35 */   private Object m_value = null;
/*     */   
/*     */   public DragNDropListener(IItemRenderable source) {
/*  38 */     this.m_sourceRenderable = source;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drag(int displayX, int displayY) {
/*  45 */     Widget widget = null;
/*     */     
/*  47 */     if (!this.m_dragged) {
/*  48 */       widget = (Widget)this.m_sourceRenderable;
/*  49 */       if (displayX < widget.getDisplayX() || displayX > widget.getDisplayX() + widget.getWidth() || 
/*  50 */         displayY < widget.getDisplayY() || displayY > widget.getDisplayY() + widget.getHeight()) {
/*     */         
/*  52 */         IComponent dnd = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(this.m_sourceDND);
/*  53 */         if (dnd == null) {
/*     */           return;
/*     */         }
/*     */         
/*  57 */         this.m_copyDND = (IComponent)dnd.cloneElementStructure();
/*  58 */         this.m_copyDND.buildGUI();
/*     */         
/*  60 */         Widget sourceWidget = (Widget)this.m_sourceDND;
/*  61 */         widget = (Widget)this.m_copyDND.getEncapsulatedObject();
/*  62 */         widget.setSize(sourceWidget.getSize());
/*  63 */         widget.layout();
/*     */         
/*  65 */         this.m_sourceDND.fireDrag(this.m_value);
/*     */         
/*  67 */         IElement top = Xulor.getInstance().getTopRootContainer();
/*     */         
/*  69 */         if (widget instanceof Container) {
/*  70 */           ((Container)widget).setNonBlocking(true);
/*     */         }
/*     */         
/*  73 */         if (top.getEncapsulatedObject() != null) {
/*  74 */           top.add((IElement)this.m_copyDND);
/*  75 */           ((IContainer)top).addWidget((IElement)this.m_copyDND);
/*     */         } 
/*     */         
/*  78 */         this.m_dragged = true;
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     if (this.m_copyDND != null) {
/*  83 */       widget = (Widget)this.m_copyDND.getEncapsulatedObject();
/*     */     }
/*     */     
/*  86 */     if (this.m_dragged && widget != null) {
/*  87 */       widget.setX(displayX - widget.getWidth() / 2);
/*  88 */       widget.setY(displayY - widget.getHeight() / 2);
/*  89 */       Display display = widget.getDisplay();
/*     */       
/*  91 */       if (display == null) {
/*     */         return;
/*     */       }
/*  94 */       IWidget draggedOver = display.getWidget(displayX, displayY);
/*  95 */       if (draggedOver != this.m_over) {
/*  96 */         if (this.m_over != null) {
/*  97 */           this.m_sourceDND.fireDragOut(((IItemRenderable)this.m_over).getDragNDropable(), this.m_value);
/*  98 */           this.m_over = null;
/*     */         } 
/* 100 */         if (this.m_over == null && draggedOver instanceof IItemRenderable) {
/* 101 */           this.m_over = draggedOver;
/* 102 */           this.m_sourceDND.fireDragOver(((IItemRenderable)this.m_over).getDragNDropable(), this.m_value);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drop(int displayX, int displayY, IWidget droppedOn) {
/* 116 */     if (this.m_dragged) {
/*     */ 
/*     */ 
/*     */       
/* 120 */       if (droppedOn instanceof IItemRenderable) {
/* 121 */         IDragNDropable dnd = ((IItemRenderable)droppedOn).getDragNDropable();
/*     */         
/* 123 */         if (dnd != null && dnd.isDropValid(this.m_sourceDND, this.m_value)) {
/* 124 */           dnd.fireDrop(this.m_sourceDND, this.m_value);
/* 125 */         } else if (dnd != this.m_sourceDND) {
/*     */ 
/*     */           
/* 128 */           this.m_sourceDND.fireDropOut(this.m_value);
/*     */         } 
/*     */       } else {
/* 131 */         this.m_sourceDND.fireDropOut(this.m_value);
/*     */       } 
/*     */       
/* 134 */       IElement top = Xulor.getInstance().getTopRootContainer();
/* 135 */       top.removeChild((IElement)this.m_copyDND);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDndWidget(IWidget w, int displayX, int displayY) {
/* 144 */     if (w instanceof IItemRenderable) {
/* 145 */       IItemRenderable renderable = (IItemRenderable)w;
/*     */       
/* 147 */       if (renderable == this.m_sourceRenderable && renderable.getItem() != null) {
/* 148 */         this.m_value = renderable.getItemValue();
/* 149 */         this.m_sourceDND = renderable.getDragNDropable();
/* 150 */         this.m_dragged = false;
/* 151 */         this.m_over = null;
/* 152 */         return true;
/*     */       } 
/*     */     } 
/* 155 */     return false;
/*     */   }
/*     */   
/*     */   public void select(int displayX, int displayY) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\DragNDropListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */