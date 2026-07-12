/*     */ package com.ankamagames.xulor.core;
/*     */ 
/*     */ import com.ankamagames.graphics.isometric.text.BackgroundedText.BackgroundedTextHotPointPosition;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.Binding;
/*     */ import com.ankamagames.xulor.converter.ComponentTypeConverter;
/*     */ import com.ankamagames.xulor.converter.ListenerConverter;
/*     */ import com.ankamagames.xulor.converter.PrimitiveConverter;
/*     */ import com.ankamagames.xulor.converter.StringConverter;
/*     */ import com.ankamagames.xulor.event.IActivationListener;
/*     */ import com.ankamagames.xulor.event.IFocusListener;
/*     */ import com.ankamagames.xulor.event.IKeyPressedListener;
/*     */ import com.ankamagames.xulor.event.IKeyReleasedListener;
/*     */ import com.ankamagames.xulor.event.IMenuItemPressedListener;
/*     */ import com.ankamagames.xulor.event.IMouseEnteredListener;
/*     */ import com.ankamagames.xulor.event.IMouseExitedListener;
/*     */ import com.ankamagames.xulor.event.IMouseMovedListener;
/*     */ import com.ankamagames.xulor.event.IMouseReleasedListener;
/*     */ import com.ankamagames.xulor.event.IWindowClosedListener;
/*     */ import com.ankamagames.xulor.event.listener.ItemDoubleClickListener;
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ import com.ankamagames.xulor.util.Propagation;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public abstract class ConverterLibrary
/*     */ {
/*  30 */   private static Logger m_logger = Logger.getLogger(ConverterLibrary.class);
/*     */   
/*  32 */   private Map<Class, Converter> m_converters = new java.util.HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */   protected ConverterLibrary()
/*     */   {
/*  38 */     registerDefaultConverters();
/*  39 */     registerConverters();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static synchronized ConverterLibrary getInstance()
/*     */   {
/*  50 */     return Xulor.getInstance().getBinding().getConverterLibrary();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Map getConverters()
/*     */   {
/*  57 */     return this.m_converters;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void registerDefaultConverters()
/*     */   {
/*  67 */     ListenerConverter lc = new ListenerConverter();
/*     */     
/*  69 */     register(IActivationListener.class, lc);
/*  70 */     register(IFocusListener.class, lc);
/*  71 */     register(IKeyPressedListener.class, lc);
/*  72 */     register(IKeyReleasedListener.class, lc);
/*  73 */     register(com.ankamagames.xulor.event.IMenuClosedListener.class, lc);
/*  74 */     register(IMenuItemPressedListener.class, lc);
/*  75 */     register(com.ankamagames.xulor.event.IMouseDoubleClickListener.class, lc);
/*  76 */     register(com.ankamagames.xulor.event.IMouseClickListener.class, lc);
/*  77 */     register(com.ankamagames.xulor.event.IMouseDraggedListener.class, lc);
/*  78 */     register(IMouseEnteredListener.class, lc);
/*  79 */     register(IMouseExitedListener.class, lc);
/*  80 */     register(IMouseMovedListener.class, lc);
/*  81 */     register(com.ankamagames.xulor.event.IMousePressedListener.class, lc);
/*  82 */     register(IMouseReleasedListener.class, lc);
/*  83 */     register(com.ankamagames.xulor.event.IMouseWheelListener.class, lc);
/*  84 */     register(com.ankamagames.xulor.event.ISliderMovedListener.class, lc);
/*  85 */     register(com.ankamagames.xulor.event.listener.SelectionChangedListener.class, lc);
/*  86 */     register(IWindowClosedListener.class, lc);
/*  87 */     register(com.ankamagames.xulor.event.IDragListener.class, lc);
/*  88 */     register(com.ankamagames.xulor.event.IDropListener.class, lc);
/*  89 */     register(com.ankamagames.xulor.event.IDropOutListener.class, lc);
/*  90 */     register(com.ankamagames.xulor.event.listener.DragOutListener.class, lc);
/*  91 */     register(com.ankamagames.xulor.event.listener.DragOverListener.class, lc);
/*  92 */     register(com.ankamagames.xulor.event.listener.ItemOverListener.class, lc);
/*  93 */     register(com.ankamagames.xulor.event.listener.ItemOutListener.class, lc);
/*  94 */     register(com.ankamagames.xulor.event.listener.ItemClickListener.class, lc);
/*  95 */     register(ItemDoubleClickListener.class, lc);
/*     */     
/*  97 */     register(Alignment.class, new com.ankamagames.xulor.converter.AlignmentConverter());
/*  98 */     register(BackgroundedText.BackgroundedTextHotPointPosition.class, new com.ankamagames.xulor.converter.BackgroundedTextHotPointPositionConverter());
/*  99 */     register(com.ankamagames.xulor.util.Color.class, new com.ankamagames.xulor.converter.ColorConverter());
/* 100 */     register(com.ankamagames.xulor.util.ComponentType.class, new ComponentTypeConverter());
/* 101 */     register(com.ankamagames.xulor.util.Dimension.class, new com.ankamagames.xulor.converter.DimensionConverter());
/* 102 */     register(com.ankamagames.xulor.event.DropValidateCallBack.class, new com.ankamagames.xulor.converter.DropValidateConverter());
/* 103 */     register(com.ankamagames.xulor.core.form.FormValidateCallBack.class, new com.ankamagames.xulor.converter.FormConverter());
/* 104 */     register(com.ankamagames.xulor.util.Percentage.class, new com.ankamagames.xulor.converter.PercentageConverter());
/* 105 */     register(Propagation.class, new com.ankamagames.xulor.converter.PropagationConverter());
/* 106 */     register(com.ankamagames.xulor.util.Spacing.class, new com.ankamagames.xulor.converter.SpacingConverter());
/* 107 */     register(String.class, new StringConverter());
/* 108 */     register(com.ankamagames.xulor.util.ScrollBarBehaviour.class, new com.ankamagames.xulor.converter.ScrollBarBehaviourConverter());
/* 109 */     register(com.ankamagames.xulor.util.ThemeTexture.class, new com.ankamagames.xulor.converter.TextureConverter());
/* 110 */     register(com.ankamagames.xulor.binding.fenggui.component.MapNavigator.MapShape.class, new com.ankamagames.xulor.converter.MapShapeConverter());
/*     */     
/*     */ 
/*     */ 
/* 114 */     PrimitiveConverter primConv = new PrimitiveConverter();
/* 115 */     register(Boolean.TYPE, primConv);
/* 116 */     register(Integer.TYPE, primConv);
/* 117 */     register(Long.TYPE, primConv);
/* 118 */     register(Float.TYPE, primConv);
/* 119 */     register(Double.TYPE, primConv);
/* 120 */     register(Byte.TYPE, primConv);
/* 121 */     register(Short.TYPE, primConv);
/* 122 */     register(Boolean.class, primConv);
/* 123 */     register(Integer.class, primConv);
/* 124 */     register(Long.class, primConv);
/* 125 */     register(Float.class, primConv);
/* 126 */     register(Double.class, primConv);
/* 127 */     register(Byte.class, primConv);
/* 128 */     register(Short.class, primConv);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void registerConverters();
/*     */   
/*     */ 
/*     */ 
/*     */   public void register(Class template, Converter converter)
/*     */   {
/* 140 */     if (!this.m_converters.containsKey(template)) {
/* 141 */       this.m_converters.put(template, converter);
/*     */     } else {
/* 143 */       m_logger.error("le convertisseur (template=" + template.toString() + ") est déjà utilisé !");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasConverter(Class template)
/*     */   {
/* 153 */     boolean found = this.m_converters.keySet().contains(template);
/* 154 */     Iterator it = this.m_converters.keySet().iterator();
/* 155 */     while ((!found) && (it != null) && (it.hasNext())) {
/* 156 */       found = template.isAssignableFrom(((Converter)this.m_converters.get(it.next())).convertsTo());
/*     */     }
/* 158 */     return found;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Converter getConverter(Class template)
/*     */   {
/* 165 */     return (Converter)this.m_converters.get(template);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\ConverterLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */