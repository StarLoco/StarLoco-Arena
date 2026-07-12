/*     */ package com.ankamagames.xulor.core;
/*     */ import com.ankamagames.graphics.isometric.text.BackgroundedText;
/*     */ import com.ankamagames.xulor.converter.ComponentTypeConverter;
/*     */ import com.ankamagames.xulor.converter.FormConverter;
/*     */ import com.ankamagames.xulor.converter.ListenerConverter;
/*     */ import com.ankamagames.xulor.converter.PrimitiveConverter;
/*     */ import com.ankamagames.xulor.converter.StringConverter;
/*     */ import com.ankamagames.xulor.event.IActivationListener;
/*     */ import com.ankamagames.xulor.event.IDragListener;
/*     */ import com.ankamagames.xulor.event.IFocusListener;
/*     */ import com.ankamagames.xulor.event.IKeyPressedListener;
/*     */ import com.ankamagames.xulor.event.IKeyReleasedListener;
/*     */ import com.ankamagames.xulor.event.IMenuItemPressedListener;
/*     */ import com.ankamagames.xulor.event.IMouseEnteredListener;
/*     */ import com.ankamagames.xulor.event.IMouseExitedListener;
/*     */ import com.ankamagames.xulor.event.IMouseMovedListener;
/*     */ import com.ankamagames.xulor.event.IMouseReleasedListener;
/*     */ import com.ankamagames.xulor.event.IWindowClosedListener;
/*     */ import com.ankamagames.xulor.event.listener.DragOverListener;
/*     */ import com.ankamagames.xulor.event.listener.ItemDoubleClickListener;
/*     */ import com.ankamagames.xulor.event.listener.ItemOverListener;
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ import com.ankamagames.xulor.util.Propagation;
/*     */ import com.ankamagames.xulor.util.Spacing;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public abstract class ConverterLibrary {
/*  30 */   private static Logger m_logger = Logger.getLogger(ConverterLibrary.class);
/*     */   
/*  32 */   private Map<Class, Converter> m_converters = (Map)new HashMap<Class<?>, Converter>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ConverterLibrary() {
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
/*     */   
/*     */   public static synchronized ConverterLibrary getInstance() {
/*  50 */     return Xulor.getInstance().getBinding().getConverterLibrary();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map getConverters() {
/*  57 */     return this.m_converters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerDefaultConverters() {
/*  67 */     ListenerConverter lc = new ListenerConverter();
/*     */     
/*  69 */     register(IActivationListener.class, (Converter)lc);
/*  70 */     register(IFocusListener.class, (Converter)lc);
/*  71 */     register(IKeyPressedListener.class, (Converter)lc);
/*  72 */     register(IKeyReleasedListener.class, (Converter)lc);
/*  73 */     register(IMenuClosedListener.class, (Converter)lc);
/*  74 */     register(IMenuItemPressedListener.class, (Converter)lc);
/*  75 */     register(IMouseDoubleClickListener.class, (Converter)lc);
/*  76 */     register(IMouseClickListener.class, (Converter)lc);
/*  77 */     register(IMouseDraggedListener.class, (Converter)lc);
/*  78 */     register(IMouseEnteredListener.class, (Converter)lc);
/*  79 */     register(IMouseExitedListener.class, (Converter)lc);
/*  80 */     register(IMouseMovedListener.class, (Converter)lc);
/*  81 */     register(IMousePressedListener.class, (Converter)lc);
/*  82 */     register(IMouseReleasedListener.class, (Converter)lc);
/*  83 */     register(IMouseWheelListener.class, (Converter)lc);
/*  84 */     register(ISliderMovedListener.class, (Converter)lc);
/*  85 */     register(SelectionChangedListener.class, (Converter)lc);
/*  86 */     register(IWindowClosedListener.class, (Converter)lc);
/*  87 */     register(IDragListener.class, (Converter)lc);
/*  88 */     register(IDropListener.class, (Converter)lc);
/*  89 */     register(IDropOutListener.class, (Converter)lc);
/*  90 */     register(DragOutListener.class, (Converter)lc);
/*  91 */     register(DragOverListener.class, (Converter)lc);
/*  92 */     register(ItemOverListener.class, (Converter)lc);
/*  93 */     register(ItemOutListener.class, (Converter)lc);
/*  94 */     register(ItemClickListener.class, (Converter)lc);
/*  95 */     register(ItemDoubleClickListener.class, (Converter)lc);
/*     */     
/*  97 */     register(Alignment.class, (Converter)new AlignmentConverter());
/*  98 */     register(BackgroundedText.BackgroundedTextHotPointPosition.class, (Converter)new BackgroundedTextHotPointPositionConverter());
/*  99 */     register(Color.class, (Converter)new ColorConverter());
/* 100 */     register(ComponentType.class, (Converter)new ComponentTypeConverter());
/* 101 */     register(Dimension.class, (Converter)new DimensionConverter());
/* 102 */     register(DropValidateCallBack.class, (Converter)new DropValidateConverter());
/* 103 */     register(FormValidateCallBack.class, (Converter)new FormConverter());
/* 104 */     register(Percentage.class, (Converter)new PercentageConverter());
/* 105 */     register(Propagation.class, (Converter)new PropagationConverter());
/* 106 */     register(Spacing.class, (Converter)new SpacingConverter());
/* 107 */     register(String.class, (Converter)new StringConverter());
/* 108 */     register(ScrollBarBehaviour.class, (Converter)new ScrollBarBehaviourConverter());
/* 109 */     register(ThemeTexture.class, (Converter)new TextureConverter());
/* 110 */     register(MapNavigator.MapShape.class, (Converter)new MapShapeConverter());
/*     */ 
/*     */ 
/*     */     
/* 114 */     PrimitiveConverter primConv = new PrimitiveConverter();
/* 115 */     register(boolean.class, (Converter)primConv);
/* 116 */     register(int.class, (Converter)primConv);
/* 117 */     register(long.class, (Converter)primConv);
/* 118 */     register(float.class, (Converter)primConv);
/* 119 */     register(double.class, (Converter)primConv);
/* 120 */     register(byte.class, (Converter)primConv);
/* 121 */     register(short.class, (Converter)primConv);
/* 122 */     register(Boolean.class, (Converter)primConv);
/* 123 */     register(Integer.class, (Converter)primConv);
/* 124 */     register(Long.class, (Converter)primConv);
/* 125 */     register(Float.class, (Converter)primConv);
/* 126 */     register(Double.class, (Converter)primConv);
/* 127 */     register(Byte.class, (Converter)primConv);
/* 128 */     register(Short.class, (Converter)primConv);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void registerConverters();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(Class template, Converter converter) {
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
/*     */   
/*     */   public boolean hasConverter(Class template) {
/* 153 */     boolean found = this.m_converters.keySet().contains(template);
/* 154 */     Iterator it = this.m_converters.keySet().iterator();
/* 155 */     while (!found && it != null && it.hasNext()) {
/* 156 */       found = template.isAssignableFrom(((Converter)this.m_converters.get(it.next())).convertsTo());
/*     */     }
/* 158 */     return found;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Converter getConverter(Class template) {
/* 165 */     return this.m_converters.get(template);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\ConverterLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */