/*     */ package com.ankamagames.xulor.event;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.CallBack;
/*     */ import com.ankamagames.xulor.core.ElementMap;
/*     */ import com.ankamagames.xulor.template.IDragNDropable;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IItemRenderable;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DropValidateCallBack
/*     */   extends CallBack
/*     */ {
/*     */   private IDragNDropable m_source;
/*     */   private IDragNDropable m_destination;
/*     */   private Object m_sourceValue;
/*     */   private Object m_destinationValue;
/*     */   private Object m_value;
/*     */   
/*     */   public void setFunc(String func, ElementMap elementMap) {
/*  35 */     setCallBackFunc(func, elementMap);
/*     */   }
/*     */   
/*     */   public void setDragNDropables(IDragNDropable source, IDragNDropable destination, Object value) {
/*  39 */     this.m_value = value;
/*  40 */     if (source instanceof IElement) {
/*  41 */       this.m_source = source;
/*     */     } else {
/*  43 */       IElement elem = Xulor.getInstance().getEnvironment().getElementByWidget(source);
/*  44 */       if (elem instanceof IDragNDropable) {
/*  45 */         this.m_source = (IDragNDropable)elem;
/*     */       }
/*     */     } 
/*  48 */     if (destination instanceof IElement) {
/*  49 */       this.m_destination = destination;
/*     */     } else {
/*  51 */       IElement elem = Xulor.getInstance().getEnvironment().getElementByWidget(destination);
/*  52 */       if (elem instanceof IDragNDropable) {
/*  53 */         this.m_destination = (IDragNDropable)elem;
/*     */       }
/*     */     } 
/*  56 */     if (this.m_source != null) {
/*  57 */       IItemRenderable renderable = this.m_source.getRenderableParent();
/*  58 */       if (renderable != null) {
/*  59 */         this.m_sourceValue = renderable.getItemValue();
/*     */       }
/*     */     } 
/*  62 */     if (this.m_destination != null) {
/*  63 */       IItemRenderable renderable = this.m_destination.getRenderableParent();
/*  64 */       if (renderable != null) {
/*  65 */         this.m_destinationValue = renderable.getItemValue();
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
/*     */   protected void fillParameters(String[] parameters, List<Class<?>> parameterTypes, List<Object> args) {
/*  78 */     parameterTypes.add(IDragNDropable.class);
/*  79 */     parameterTypes.add(Object.class);
/*  80 */     parameterTypes.add(IDragNDropable.class);
/*  81 */     parameterTypes.add(Object.class);
/*  82 */     parameterTypes.add(Object.class);
/*  83 */     args.add(this.m_source);
/*  84 */     args.add(this.m_sourceValue);
/*  85 */     args.add(this.m_destination);
/*  86 */     args.add(this.m_destinationValue);
/*  87 */     args.add(this.m_value);
/*  88 */     super.fillParameters(parameters, parameterTypes, args);
/*     */   }
/*     */   
/*     */   public void copyCallback(DropValidateCallBack listener) {
/*  92 */     listener.setFunc(this.m_func, this.m_elementMap);
/*     */   }
/*     */   
/*     */   public DropValidateCallBack cloneListener() {
/*  96 */     DropValidateCallBack cb = new DropValidateCallBack();
/*  97 */     copyCallback(cb);
/*  98 */     return cb;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object invokeCallBack(IDragNDropable source, IDragNDropable destination, Object value) {
/* 103 */     this.m_value = value;
/* 104 */     if (source instanceof IElement) {
/* 105 */       this.m_source = source;
/*     */     } else {
/* 107 */       IElement elem = Xulor.getInstance().getEnvironment().getElementByWidget(source);
/* 108 */       if (elem instanceof IDragNDropable) {
/* 109 */         this.m_source = (IDragNDropable)elem;
/*     */       }
/*     */     } 
/* 112 */     if (destination instanceof IElement) {
/* 113 */       this.m_destination = destination;
/*     */     } else {
/* 115 */       IElement elem = Xulor.getInstance().getEnvironment().getElementByWidget(destination);
/* 116 */       if (elem instanceof IDragNDropable) {
/* 117 */         this.m_destination = (IDragNDropable)elem;
/*     */       }
/*     */     } 
/* 120 */     if (this.m_source != null) {
/* 121 */       IItemRenderable renderable = this.m_source.getRenderableParent();
/* 122 */       if (renderable != null) {
/* 123 */         this.m_sourceValue = renderable.getItemValue();
/*     */       }
/*     */     } 
/* 126 */     if (this.m_destination != null) {
/* 127 */       IItemRenderable renderable = this.m_destination.getRenderableParent();
/* 128 */       if (renderable != null) {
/* 129 */         this.m_destinationValue = renderable.getItemValue();
/*     */       }
/*     */     } 
/* 132 */     return invokeCallBack();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\DropValidateCallBack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */