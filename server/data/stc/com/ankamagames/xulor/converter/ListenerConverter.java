/*     */ package com.ankamagames.xulor.converter;
/*     */ 
/*     */ import com.ankamagames.xulor.event.listener.ActivationListener;
/*     */ import com.ankamagames.xulor.event.listener.DragOutListener;
/*     */ import com.ankamagames.xulor.event.listener.DragOverListener;
/*     */ import com.ankamagames.xulor.event.listener.ItemClickListener;
/*     */ import com.ankamagames.xulor.event.listener.ItemDoubleClickListener;
/*     */ import com.ankamagames.xulor.event.listener.ItemOutListener;
/*     */ import com.ankamagames.xulor.event.listener.ItemOverListener;
/*     */ import com.ankamagames.xulor.event.listener.KeyPressedListener;
/*     */ import com.ankamagames.xulor.event.listener.MouseDoubleClickListener;
/*     */ import com.ankamagames.xulor.event.listener.MouseExitedListener;
/*     */ import com.ankamagames.xulor.event.listener.MouseMovedListener;
/*     */ import com.ankamagames.xulor.event.listener.MouseWheelListener;
/*     */ import com.ankamagames.xulor.event.listener.SelectionChangedListener;
/*     */ import com.ankamagames.xulor.event.listener.SliderMovedListener;
/*     */ 
/*     */ public class ListenerConverter implements com.ankamagames.xulor.core.Converter
/*     */ {
/*  20 */   private Class TEMPLATE = com.ankamagames.xulor.event.IListener.class;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object convert(Class type, String func)
/*     */   {
/*  29 */     if (func == null) {
/*  30 */       return null;
/*     */     }
/*  32 */     com.ankamagames.xulor.core.ElementMap currentElementMap = com.ankamagames.xulor.Xulor.getInstance().getEnvironment().getCurrentElementMap();
/*     */     
/*  34 */     if (type.equals(com.ankamagames.xulor.event.IWindowClosedListener.class)) {
/*  35 */       com.ankamagames.xulor.event.listener.WindowClosedListener wcl = new com.ankamagames.xulor.event.listener.WindowClosedListener();
/*  36 */       wcl.setWindowClosedFunc(func, currentElementMap);
/*  37 */       return wcl; }
/*  38 */     if (type.equals(com.ankamagames.xulor.event.IActivationListener.class)) {
/*  39 */       ActivationListener al = new ActivationListener();
/*  40 */       al.setActivatedFunc(func, currentElementMap);
/*  41 */       return al; }
/*  42 */     if (type.equals(com.ankamagames.xulor.event.IFocusListener.class)) {
/*  43 */       com.ankamagames.xulor.event.listener.FocusListener fl = new com.ankamagames.xulor.event.listener.FocusListener();
/*  44 */       fl.setFocusFunc(func, currentElementMap);
/*  45 */       return fl; }
/*  46 */     if (type.equals(com.ankamagames.xulor.event.IKeyPressedListener.class)) {
/*  47 */       KeyPressedListener kpl = new KeyPressedListener();
/*  48 */       kpl.setKeyPressedFunc(func, currentElementMap);
/*  49 */       return kpl; }
/*  50 */     if (type.equals(com.ankamagames.xulor.event.IKeyReleasedListener.class)) {
/*  51 */       com.ankamagames.xulor.event.listener.KeyReleasedListener krl = new com.ankamagames.xulor.event.listener.KeyReleasedListener();
/*  52 */       krl.setKeyReleasedFunc(func, currentElementMap);
/*  53 */       return krl; }
/*  54 */     if (type.equals(com.ankamagames.xulor.event.IMenuClosedListener.class)) {
/*  55 */       com.ankamagames.xulor.event.listener.MenuClosedListener mcl = new com.ankamagames.xulor.event.listener.MenuClosedListener();
/*  56 */       mcl.setMenuClosedFunc(func, currentElementMap);
/*  57 */       return mcl; }
/*  58 */     if (type.equals(com.ankamagames.xulor.event.IMenuItemPressedListener.class)) {
/*  59 */       com.ankamagames.xulor.event.listener.MenuItemPressedListener mipl = new com.ankamagames.xulor.event.listener.MenuItemPressedListener();
/*  60 */       mipl.setMenuItemPressedFunc(func, currentElementMap);
/*  61 */       return mipl; }
/*  62 */     if (type.equals(com.ankamagames.xulor.event.IMouseDraggedListener.class)) {
/*  63 */       com.ankamagames.xulor.event.listener.MouseDraggedListener mdl = new com.ankamagames.xulor.event.listener.MouseDraggedListener();
/*  64 */       mdl.setMouseDraggedFunc(func, currentElementMap);
/*  65 */       return mdl; }
/*  66 */     if (type.equals(com.ankamagames.xulor.event.IMouseEnteredListener.class)) {
/*  67 */       com.ankamagames.xulor.event.listener.MouseEnteredListener mel = new com.ankamagames.xulor.event.listener.MouseEnteredListener();
/*  68 */       mel.setMouseEnteredFunc(func, currentElementMap);
/*  69 */       return mel; }
/*  70 */     if (type.equals(com.ankamagames.xulor.event.IMouseExitedListener.class)) {
/*  71 */       MouseExitedListener mel = new MouseExitedListener();
/*  72 */       mel.setMouseExitedFunc(func, currentElementMap);
/*  73 */       return mel; }
/*  74 */     if (type.equals(com.ankamagames.xulor.event.IMouseMovedListener.class)) {
/*  75 */       MouseMovedListener mml = new MouseMovedListener();
/*  76 */       mml.setMouseMovedFunc(func, currentElementMap);
/*  77 */       return mml; }
/*  78 */     if (type.equals(com.ankamagames.xulor.event.IMousePressedListener.class)) {
/*  79 */       com.ankamagames.xulor.event.listener.MousePressedListener mml = new com.ankamagames.xulor.event.listener.MousePressedListener();
/*  80 */       mml.setMousePressedFunc(func, currentElementMap);
/*  81 */       return mml; }
/*  82 */     if (type.equals(com.ankamagames.xulor.event.IMouseReleasedListener.class)) {
/*  83 */       com.ankamagames.xulor.event.listener.MouseReleasedListener mml = new com.ankamagames.xulor.event.listener.MouseReleasedListener();
/*  84 */       mml.setMouseReleasedFunc(func, currentElementMap);
/*  85 */       return mml; }
/*  86 */     if (type.equals(com.ankamagames.xulor.event.IMouseWheelListener.class)) {
/*  87 */       MouseWheelListener mml = new MouseWheelListener();
/*  88 */       mml.setMouseWheelFunc(func, currentElementMap);
/*  89 */       return mml; }
/*  90 */     if (type.equals(com.ankamagames.xulor.event.ISliderMovedListener.class)) {
/*  91 */       SliderMovedListener mml = new SliderMovedListener();
/*  92 */       mml.setSliderMovedFunc(func, currentElementMap);
/*  93 */       return mml; }
/*  94 */     if (type.equals(SelectionChangedListener.class)) {
/*  95 */       SelectionChangedListener scl = new SelectionChangedListener();
/*  96 */       scl.setSelectionChangedFunc(func, currentElementMap);
/*  97 */       return scl; }
/*  98 */     if (type.equals(com.ankamagames.xulor.event.IDragListener.class)) {
/*  99 */       com.ankamagames.xulor.event.listener.DragListener dl = new com.ankamagames.xulor.event.listener.DragListener();
/* 100 */       dl.setDraggedFunc(func, currentElementMap);
/* 101 */       return dl; }
/* 102 */     if (type.equals(com.ankamagames.xulor.event.IDropListener.class)) {
/* 103 */       com.ankamagames.xulor.event.listener.DropListener dl = new com.ankamagames.xulor.event.listener.DropListener();
/* 104 */       dl.setDroppedFunc(func, currentElementMap);
/* 105 */       return dl; }
/* 106 */     if (type.equals(com.ankamagames.xulor.event.IDropOutListener.class)) {
/* 107 */       com.ankamagames.xulor.event.listener.DropOutListener dol = new com.ankamagames.xulor.event.listener.DropOutListener();
/* 108 */       dol.setDroppedOutFunc(func, currentElementMap);
/* 109 */       return dol; }
/* 110 */     if (type.equals(DragOutListener.class)) {
/* 111 */       DragOutListener dol = new DragOutListener();
/* 112 */       dol.setDraggedOutFunc(func, currentElementMap);
/* 113 */       return dol; }
/* 114 */     if (type.equals(DragOverListener.class)) {
/* 115 */       DragOverListener dol = new DragOverListener();
/* 116 */       dol.setDraggedOverFunc(func, currentElementMap);
/* 117 */       return dol; }
/* 118 */     if (type.equals(ItemOverListener.class)) {
/* 119 */       ItemOverListener iol = new ItemOverListener();
/* 120 */       iol.setItemOverFunc(func, currentElementMap);
/* 121 */       return iol; }
/* 122 */     if (type.equals(ItemOutListener.class)) {
/* 123 */       ItemOutListener iol = new ItemOutListener();
/* 124 */       iol.setItemOutFunc(func, currentElementMap);
/* 125 */       return iol; }
/* 126 */     if (type.equals(com.ankamagames.xulor.event.IMouseDoubleClickListener.class)) {
/* 127 */       MouseDoubleClickListener mdcl = new MouseDoubleClickListener();
/* 128 */       mdcl.setMouseDoubleClickFunc(func, currentElementMap);
/* 129 */       return mdcl; }
/* 130 */     if (type.equals(com.ankamagames.xulor.event.IMouseClickListener.class)) {
/* 131 */       com.ankamagames.xulor.event.listener.MouseClickListener mcl = new com.ankamagames.xulor.event.listener.MouseClickListener();
/* 132 */       mcl.setMouseClickFunc(func, currentElementMap);
/* 133 */       return mcl; }
/* 134 */     if (type.equals(ItemClickListener.class)) {
/* 135 */       ItemClickListener icl = new ItemClickListener();
/* 136 */       icl.setItemClickFunc(func, currentElementMap);
/* 137 */       return icl; }
/* 138 */     if (type.equals(ItemDoubleClickListener.class)) {
/* 139 */       ItemDoubleClickListener icl = new ItemDoubleClickListener();
/* 140 */       icl.setItemDoubleClickFunc(func, currentElementMap);
/* 141 */       return icl;
/*     */     }
/* 143 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class convertsTo()
/*     */   {
/* 152 */     return this.TEMPLATE;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\converter\ListenerConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */