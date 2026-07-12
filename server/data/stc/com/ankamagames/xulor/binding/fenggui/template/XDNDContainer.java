/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.DragNDropContainer;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.event.DropValidateCallBack;
/*     */ import com.ankamagames.xulor.event.IDragListener;
/*     */ import com.ankamagames.xulor.event.IDropListener;
/*     */ import com.ankamagames.xulor.event.IDropOutListener;
/*     */ import com.ankamagames.xulor.event.listener.DragOutListener;
/*     */ import com.ankamagames.xulor.event.listener.DragOverListener;
/*     */ import com.ankamagames.xulor.template.IContainer;
/*     */ import com.ankamagames.xulor.template.IDragNDropable;
/*     */ import com.ankamagames.xulor.template.IElement;
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
/*     */ public class XDNDContainer
/*     */   extends XContainer
/*     */   implements IDragNDropable, IContainer
/*     */ {
/*     */   public static final String TAG = "DNDContainer";
/*     */   public static final String SHORT_TAG = "DNDC";
/*  31 */   private DragNDropContainer m_dragNDropContainer = null;
/*     */   
/*  33 */   private DragOutListener m_dragOutListener = null;
/*  34 */   private DragOverListener m_dragOverListener = null;
/*  35 */   private IDragListener m_dragListener = null;
/*  36 */   private IDropListener m_dropListener = null;
/*  37 */   private IDropOutListener m_dropOutListener = null;
/*  38 */   private DropValidateCallBack m_dropValidate = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/*  47 */     if (this.m_dragNDropContainer != null) {
/*  48 */       if (this.m_dragListener != null) this.m_dragNDropContainer.setOnDrag(this.m_dragListener);
/*  49 */       if (this.m_dropListener != null) this.m_dragNDropContainer.setOnDrop(this.m_dropListener);
/*  50 */       if (this.m_dropOutListener != null) this.m_dragNDropContainer.setOnDropOut(this.m_dropOutListener);
/*  51 */       if (this.m_dragOutListener != null) this.m_dragNDropContainer.setOnDragOut(this.m_dragOutListener);
/*  52 */       if (this.m_dragOverListener != null) this.m_dragNDropContainer.setOnDragOver(this.m_dragOverListener);
/*  53 */       if (this.m_dropValidate != null) this.m_dragNDropContainer.setValidateDrop(this.m_dropValidate);
/*     */     }
/*  55 */     super.applyAllAttributes();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/*  63 */     if (this.m_dragNDropContainer == null) {
/*  64 */       this.m_dragNDropContainer = new DragNDropContainer();
/*  65 */       this.m_container = this.m_dragNDropContainer;
/*     */       
/*  67 */       applyAllAttributes();
/*     */       
/*  69 */       if (this.m_parent != null) this.m_parent.addWidget(this);
/*  70 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_dragNDropContainer, this);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  73 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  74 */       c.buildGUI();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isCompatible(Item item)
/*     */   {
/*  82 */     if (this.m_dragNDropContainer != null) {
/*  83 */       return this.m_dragNDropContainer.isCompatible(item);
/*     */     }
/*  85 */     return false;
/*     */   }
/*     */   
/*     */   public IItemRenderable getRenderableParent() {
/*  89 */     if (this.m_dragNDropContainer != null) {
/*  90 */       return this.m_dragNDropContainer.getRenderableParent();
/*     */     }
/*  92 */     return super.getRenderableParent();
/*     */   }
/*     */   
/*     */   public void setRenderableParent(IItemRenderable parent) {
/*  96 */     if (this.m_dragNDropContainer != null) {
/*  97 */       this.m_dragNDropContainer.setRenderableParent(parent);
/*     */     }
/*  99 */     super.setRenderableParent(parent);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 107 */     return "DNDContainer";
/*     */   }
/*     */   
/*     */   public DragNDropContainer getWidget() {
/* 111 */     return this.m_dragNDropContainer;
/*     */   }
/*     */   
/*     */   protected void copyElementData(XDNDContainer container) {
/* 115 */     container.m_renderableParent = this.m_renderableParent;
/* 116 */     if (this.m_dragListener != null) {
/* 117 */       container.setOnDrag(this.m_dragListener);
/*     */     }
/* 119 */     if (this.m_dropListener != null) {
/* 120 */       container.setOnDrop(this.m_dropListener);
/*     */     }
/* 122 */     if (this.m_dropOutListener != null) {
/* 123 */       container.setOnDropOut(this.m_dropOutListener);
/*     */     }
/* 125 */     if (this.m_dragOutListener != null) {
/* 126 */       container.setOnDragOut(this.m_dragOutListener);
/*     */     }
/* 128 */     if (this.m_dragOverListener != null) {
/* 129 */       container.setOnDragOver(this.m_dragOverListener);
/*     */     }
/* 131 */     if (this.m_dropValidate != null) {
/* 132 */       container.setValidateDrop(this.m_dropValidate.cloneListener());
/*     */     }
/* 134 */     super.copyElementData(container);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 141 */     XDNDContainer container = new XDNDContainer();
/* 142 */     copyElementData(container);
/* 143 */     return container;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setOnDrag(IDragListener l)
/*     */   {
/* 150 */     this.m_dragListener = l;
/* 151 */     if (this.m_dragNDropContainer != null) {
/* 152 */       this.m_dragNDropContainer.setOnDrag(l);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setOnDrop(IDropListener l)
/*     */   {
/* 160 */     this.m_dropListener = l;
/* 161 */     if (this.m_dragNDropContainer != null) {
/* 162 */       this.m_dragNDropContainer.setOnDrop(l);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setOnDropOut(IDropOutListener l)
/*     */   {
/* 170 */     this.m_dropOutListener = l;
/* 171 */     if (this.m_dragNDropContainer != null) {
/* 172 */       this.m_dragNDropContainer.setOnDropOut(l);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setOnDragOut(DragOutListener l)
/*     */   {
/* 180 */     this.m_dragOutListener = l;
/* 181 */     if (this.m_dragNDropContainer != null) {
/* 182 */       this.m_dragNDropContainer.setOnDragOut(l);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setOnDragOver(DragOverListener l)
/*     */   {
/* 190 */     this.m_dragOverListener = l;
/* 191 */     if (this.m_dragNDropContainer != null) {
/* 192 */       this.m_dragNDropContainer.setOnDragOver(l);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setValidateDrop(DropValidateCallBack c)
/*     */   {
/* 200 */     this.m_dropValidate = c;
/* 201 */     if (this.m_dragNDropContainer != null) {
/* 202 */       this.m_dragNDropContainer.setValidateDrop(c);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void fireDrag(Object value)
/*     */   {
/* 210 */     if (this.m_dragNDropContainer != null) {
/* 211 */       this.m_dragNDropContainer.fireDrag(value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void fireDrop(IDragNDropable source, Object value)
/*     */   {
/* 219 */     if (this.m_dragNDropContainer != null) {
/* 220 */       this.m_dragNDropContainer.fireDrop(source, value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void fireDropOut(Object value)
/*     */   {
/* 228 */     if (this.m_dragNDropContainer != null) {
/* 229 */       this.m_dragNDropContainer.fireDropOut(value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void fireDragOut(IDragNDropable out, Object value)
/*     */   {
/* 237 */     if (this.m_dragNDropContainer != null) {
/* 238 */       this.m_dragNDropContainer.fireDragOut(out, value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void fireDragOver(IDragNDropable over, Object value)
/*     */   {
/* 246 */     if (this.m_dragNDropContainer != null) {
/* 247 */       this.m_dragNDropContainer.fireDragOver(over, value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isDropValid(IDragNDropable source, Object value)
/*     */   {
/* 255 */     if (this.m_dragNDropContainer != null) {
/* 256 */       return this.m_dragNDropContainer.isDropValid(source, value);
/*     */     }
/* 258 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XDNDContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */