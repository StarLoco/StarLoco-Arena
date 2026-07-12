/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.framework.preferences.PreferencePropertyChangeEvent;
/*     */ import com.ankamagames.framework.preferences.PreferenceStore;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.NonBlocking;
/*     */ import com.ankamagames.xulor.core.ElementMap;
/*     */ import com.ankamagames.xulor.core.impl.XElement;
/*     */ import com.ankamagames.xulor.property.Property;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.template.IContainer;
/*     */ import com.ankamagames.xulor.template.IDragNDropable;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.ILayoutData;
/*     */ import com.ankamagames.xulor.theme.ThemeAttributes;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeParser;
/*     */ import com.ankamagames.xulor.util.Dimension;
/*     */ import com.ankamagames.xulor.util.PrimitiveConverter;
/*     */ import com.ankamagames.xulor.util.Propagation;
/*     */ import com.ankamagames.xulor.util.XulorUtil;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.fenggui.IBasicContainer;
/*     */ import org.fenggui.Widget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class XComponent
/*     */   extends XElement
/*     */   implements IComponent
/*     */ {
/*  35 */   private static Logger m_logger = Logger.getLogger(XComponent.class);
/*     */   
/*  37 */   protected XLayoutData m_layoutData = null;
/*  38 */   protected boolean m_expandable = true;
/*  39 */   protected boolean m_shrinkable = true;
/*  40 */   protected Dimension m_minSize = null;
/*  41 */   protected boolean m_usedInLayout = true;
/*     */   
/*  43 */   private boolean m_nonBlocking = false;
/*  44 */   private boolean m_nonBlockingInit = false;
/*     */   
/*     */   protected int m_x;
/*     */   
/*     */   protected int m_y;
/*     */   protected int m_width;
/*     */   protected int m_height;
/*  51 */   protected boolean m_xInit = false;
/*  52 */   protected boolean m_yInit = false;
/*  53 */   protected boolean m_heightInit = false;
/*  54 */   protected boolean m_widthInit = false;
/*     */   
/*  56 */   protected ThemeElement m_themeElement = null;
/*  57 */   protected boolean m_themeNeedToBeApplied = true;
/*  58 */   protected String m_style = null;
/*  59 */   protected String m_styleToPropagate = null;
/*  60 */   protected Propagation m_stylePropagation = Propagation.PASS;
/*     */   
/*  62 */   private boolean m_expandableInit = false; private boolean m_shrinkableInit = false;
/*  63 */   private boolean m_usedInLayoutInit = false;
/*     */   
/*  65 */   private IDragNDropable m_dndParent = null;
/*  66 */   private Property m_expandableProperty = null;
/*  67 */   private Property m_shrinkableProperty = null;
/*     */   
/*  69 */   protected boolean m_addedToWidgetTree = false;
/*     */   
/*     */   public void setX(int x) {
/*  72 */     this.m_xInit = true;
/*  73 */     this.m_x = x;
/*  74 */     if (getWidget() != null) {
/*  75 */       getWidget().setX(x);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getX() {
/*  80 */     if (getWidget() != null) {
/*  81 */       return getWidget().getX();
/*     */     }
/*  83 */     return this.m_x;
/*     */   }
/*     */   
/*     */   public void setY(int y) {
/*  87 */     this.m_yInit = true;
/*  88 */     this.m_y = y;
/*  89 */     if (getWidget() != null) {
/*  90 */       getWidget().setY(y);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getY() {
/*  95 */     if (getWidget() != null) {
/*  96 */       return getWidget().getY();
/*     */     }
/*  98 */     return this.m_y;
/*     */   }
/*     */   
/*     */   public void setWidth(int width) {
/* 102 */     this.m_widthInit = true;
/* 103 */     this.m_width = width;
/* 104 */     if (getWidget() != null) {
/* 105 */       getWidget().setWidth(width);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 110 */     if (getWidget() != null) {
/* 111 */       return getWidget().getWidth();
/*     */     }
/* 113 */     return this.m_width;
/*     */   }
/*     */   
/*     */   public void setHeight(int height) {
/* 117 */     this.m_heightInit = true;
/* 118 */     this.m_height = height;
/* 119 */     if (getWidget() != null) {
/* 120 */       getWidget().setHeight(height);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 125 */     if (getWidget() != null) {
/* 126 */       return getWidget().getHeight();
/*     */     }
/* 128 */     return this.m_height;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isDisplayable()
/*     */   {
/* 137 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ILayoutData getLayoutData()
/*     */   {
/* 146 */     return this.m_layoutData;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLayoutData(ILayoutData ld)
/*     */   {
/* 155 */     this.m_layoutData = ((XLayoutData)ld);
/* 156 */     Widget widget = getWidget();
/* 157 */     if (widget != null) {
/* 158 */       widget.setLayoutData(this.m_layoutData.getLayoutData());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isNonBlocking()
/*     */   {
/* 166 */     return this.m_nonBlocking;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setNonBlocking(boolean nonBlocking)
/*     */   {
/* 173 */     this.m_nonBlocking = nonBlocking;
/* 174 */     this.m_nonBlockingInit = true;
/* 175 */     if ((getWidget() instanceof NonBlocking)) {
/* 176 */       ((NonBlocking)getWidget()).setNonBlocking(nonBlocking);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void displayNonBlockingAvailability() {
/* 181 */     m_logger.info("Le composant " + getTag() + " n'a pas la capacité 'non-bloquant'");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isExpandable()
/*     */   {
/* 190 */     return this.m_expandable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setExpandable(boolean expandable)
/*     */   {
/* 199 */     this.m_expandable = expandable;
/* 200 */     this.m_expandableInit = true;
/* 201 */     Widget widget = getWidget();
/* 202 */     if (widget != null) {
/* 203 */       widget.setExpandable(this.m_expandable);
/*     */     }
/* 205 */     if (this.m_expandableProperty != null) {
/* 206 */       this.m_expandableProperty.setValue(Boolean.valueOf(expandable));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isShrinkable()
/*     */   {
/* 216 */     return this.m_shrinkable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setShrinkable(boolean shrinkable)
/*     */   {
/* 225 */     this.m_shrinkable = shrinkable;
/* 226 */     this.m_shrinkableInit = true;
/* 227 */     Widget widget = getWidget();
/* 228 */     if (widget != null) {
/* 229 */       widget.setShrinkable(this.m_shrinkable);
/*     */     }
/* 231 */     if (this.m_shrinkableProperty != null) {
/* 232 */       this.m_shrinkableProperty.setValue(Boolean.valueOf(shrinkable));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasFocus()
/*     */   {
/* 241 */     Widget widget = getWidget();
/* 242 */     if (widget != null) {
/* 243 */       return widget.hasFocus();
/*     */     }
/* 245 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean getUsedInLayout()
/*     */   {
/* 252 */     return this.m_usedInLayout;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setUsedInLayout(boolean layouted)
/*     */   {
/* 259 */     this.m_usedInLayout = layouted;
/* 260 */     this.m_usedInLayoutInit = true;
/* 261 */     Widget widget = getWidget();
/* 262 */     if ((widget != null) && 
/* 263 */       ((widget.getParent() instanceof com.ankamagames.xulor.binding.fenggui.component.Container))) {
/* 264 */       ((com.ankamagames.xulor.binding.fenggui.component.Container)widget.getParent()).setUsedInLayout(widget, layouted);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void layout()
/*     */   {
/* 274 */     if (getWidget() != null) {
/* 275 */       getWidget().layout();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Dimension getMinSize()
/*     */   {
/* 283 */     return this.m_minSize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMinSize(Dimension minSize)
/*     */   {
/* 290 */     this.m_minSize = minSize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isAddedToWidgetTree()
/*     */   {
/* 297 */     return this.m_addedToWidgetTree;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setAddedToWidgetTree(boolean addedToWidgetTree)
/*     */   {
/* 304 */     this.m_addedToWidgetTree = addedToWidgetTree;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void applyThemeAttributes(Widget widget, ThemeAttributes themeAttributes)
/*     */   {
/* 311 */     if ((themeAttributes == null) || (widget == null)) {
/* 312 */       return;
/*     */     }
/* 314 */     int minWidth = 10;int minHeight = 10;
/* 315 */     if (themeAttributes.isExpandableInit()) widget.setExpandable(themeAttributes.isExpandable());
/* 316 */     if (themeAttributes.isShrinkableInit()) widget.setShrinkable(themeAttributes.isShrinkable());
/* 317 */     if (themeAttributes.isHeightInit()) minHeight = themeAttributes.getHeight();
/* 318 */     if (themeAttributes.isWidthInit()) { minWidth = themeAttributes.getWidth();
/*     */     }
/* 320 */     widget.setMinSize(minWidth, minHeight);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ThemeElement getThemeElement()
/*     */   {
/* 327 */     return this.m_themeElement;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setThemeElement(ThemeElement themes)
/*     */   {
/* 334 */     if (themes != null) {
/* 335 */       this.m_themeElement = ((ThemeElement)themes.cloneAppearance());
/*     */     } else {
/* 337 */       this.m_themeElement = null;
/*     */     }
/* 339 */     this.m_themeNeedToBeApplied = true;
/*     */   }
/*     */   
/*     */   public void propagateStyle(String style) {
/* 343 */     if (this.m_style == null) {
/* 344 */       changeStyle(style, false);
/*     */     }
/*     */     
/* 347 */     if ((style == null) || (this.m_stylePropagation == null) || 
/* 348 */       (Propagation.PASS.equals(this.m_stylePropagation))) {
/* 349 */       super.propagateStyle(style);
/*     */     }
/*     */   }
/*     */   
/*     */   private void changeStyle(String newStyle, boolean applyPropagation) {
/* 354 */     ThemeElement themeElement = null;
/* 355 */     if (newStyle != null) {
/* 356 */       if ((applyPropagation) && (Propagation.PROPAGATE.equals(this.m_stylePropagation))) {
/* 357 */         super.propagateStyle(newStyle);
/*     */       }
/* 359 */       String themeClass = getTag() + newStyle;
/*     */       
/* 361 */       themeElement = Xulor.getInstance().getThemeParser().getThemeElement(themeClass);
/*     */     }
/*     */     else {
/* 364 */       if (newStyle == null) {
/* 365 */         newStyle = "";
/*     */       }
/* 367 */       String themeClass = getTag() + newStyle;
/* 368 */       themeElement = Xulor.getInstance().getThemeParser().getThemeElement(themeClass);
/*     */     }
/*     */     
/*     */ 
/* 372 */     if (themeElement == null) {
/* 373 */       themeElement = Xulor.getInstance().getThemeParser().getThemeElement(getTag());
/*     */     }
/*     */     
/* 376 */     if ((applyPropagation) && (Propagation.STOP.equals(this.m_stylePropagation))) {
/* 377 */       super.propagateStyle("");
/*     */     }
/*     */     
/* 380 */     if (themeElement != null) {
/* 381 */       this.m_themeElement = ((ThemeElement)themeElement.cloneAppearance());
/* 382 */       this.m_themeNeedToBeApplied = true;
/*     */     }
/*     */     
/* 385 */     if (getWidget() != null) {
/* 386 */       applyTheme();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setStyle(String style, boolean apply)
/*     */   {
/* 395 */     if (style != this.m_style) {
/* 396 */       this.m_style = style;
/* 397 */       if (apply) {
/* 398 */         changeStyle(this.m_style, true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void setStyle(String style) {
/* 404 */     setStyle(style, true);
/*     */   }
/*     */   
/*     */   public void setStylePropagation(Propagation propagation, boolean apply) {
/* 408 */     if (propagation != this.m_stylePropagation) {
/* 409 */       this.m_stylePropagation = propagation;
/* 410 */       if ((apply) && (this.m_style != null)) {
/* 411 */         changeStyle(this.m_style, true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void setStylePropagation(Propagation stylePropagation) {
/* 417 */     setStylePropagation(stylePropagation, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isThemeNeedToBeApplied()
/*     */   {
/* 424 */     return this.m_themeNeedToBeApplied;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setThemeNeedToBeApplied(boolean themeNeedToBeApplied)
/*     */   {
/* 431 */     this.m_themeNeedToBeApplied = themeNeedToBeApplied;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void buildGUI();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void applyComponentAttributes()
/*     */   {
/* 446 */     Widget widget = getWidget();
/* 447 */     if (widget != null) {
/* 448 */       if (this.m_xInit) {
/* 449 */         widget.setX(this.m_x);
/*     */       }
/* 451 */       if (this.m_yInit) {
/* 452 */         widget.setY(this.m_y);
/*     */       }
/* 454 */       if (this.m_widthInit) {
/* 455 */         widget.setWidth(this.m_width);
/*     */       }
/* 457 */       if (this.m_heightInit) {
/* 458 */         widget.setHeight(this.m_height);
/*     */       }
/* 460 */       if (this.m_expandableInit) {
/* 461 */         widget.setExpandable(this.m_expandable);
/*     */       }
/* 463 */       if (this.m_shrinkableInit) {
/* 464 */         widget.setShrinkable(this.m_shrinkable);
/*     */       }
/* 466 */       if (this.m_minSize != null) {
/* 467 */         widget.setMinSize(this.m_minSize.getWidth(), this.m_minSize.getHeight());
/*     */       }
/* 469 */       if (this.m_layoutData != null) {
/* 470 */         widget.setLayoutData(this.m_layoutData.getLayoutData());
/*     */       }
/*     */       
/* 473 */       if ((this.m_usedInLayoutInit) && 
/* 474 */         ((widget.getParent() instanceof com.ankamagames.xulor.binding.fenggui.component.Container))) {
/* 475 */         ((com.ankamagames.xulor.binding.fenggui.component.Container)widget.getParent()).setUsedInLayout(widget, this.m_usedInLayout);
/*     */       }
/*     */       
/*     */ 
/* 479 */       if ((this.m_nonBlockingInit) && ((widget instanceof NonBlocking))) {
/* 480 */         ((NonBlocking)widget).setNonBlocking(this.m_nonBlocking);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void applyTheme();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void loadPreferences()
/*     */   {
/* 499 */     super.loadPreferences();
/* 500 */     if (!this.m_static) {
/* 501 */       PreferenceStore pref = Xulor.getInstance().getPreferenceStore();
/* 502 */       m_logger.info("loadPreferences de " + this.m_id + " : " + this);
/* 503 */       String x = XulorUtil.generatePreferenceKey(this.m_elementMap.getId(), this.m_id, "x");
/* 504 */       String y = XulorUtil.generatePreferenceKey(this.m_elementMap.getId(), this.m_id, "y");
/* 505 */       String width = XulorUtil.generatePreferenceKey(this.m_elementMap.getId(), this.m_id, "width");
/* 506 */       String height = XulorUtil.generatePreferenceKey(this.m_elementMap.getId(), this.m_id, "height");
/* 507 */       if (pref.contains(x)) setX(pref.getInt(x));
/* 508 */       if (pref.contains(y)) setY(pref.getInt(y));
/* 509 */       if (pref.contains(width)) setWidth(pref.getInt(width));
/* 510 */       if (pref.contains(height)) { setHeight(pref.getInt(height));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void storePreferences()
/*     */   {
/* 519 */     super.storePreferences();
/* 520 */     if (!this.m_static) {
/* 521 */       m_logger.info("storePreferences de " + this.m_id);
/* 522 */       String x = XulorUtil.generatePreferenceKey(this.m_elementMap.getId(), this.m_id, "x");
/* 523 */       String y = XulorUtil.generatePreferenceKey(this.m_elementMap.getId(), this.m_id, "y");
/* 524 */       String width = XulorUtil.generatePreferenceKey(this.m_elementMap.getId(), this.m_id, "width");
/* 525 */       String height = XulorUtil.generatePreferenceKey(this.m_elementMap.getId(), this.m_id, "height");
/* 526 */       PreferenceStore pref = Xulor.getInstance().getPreferenceStore();
/* 527 */       pref.setValue(x, getX());
/* 528 */       pref.setValue(y, getY());
/* 529 */       pref.setValue(width, getWidth());
/* 530 */       pref.setValue(height, getHeight());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void propertyChange(PreferencePropertyChangeEvent event)
/*     */   {
/* 539 */     super.propertyChange(event);
/* 540 */     String x = XulorUtil.generatePreferenceKey(this.m_elementMap.getId(), this.m_id, "x");
/* 541 */     String y = XulorUtil.generatePreferenceKey(this.m_elementMap.getId(), this.m_id, "y");
/* 542 */     String width = XulorUtil.generatePreferenceKey(this.m_elementMap.getId(), this.m_id, "width");
/* 543 */     String height = XulorUtil.generatePreferenceKey(this.m_elementMap.getId(), this.m_id, "height");
/* 544 */     if (event.getPropertyName().equalsIgnoreCase(x)) { setX(PrimitiveConverter.getInteger(event.getNewValue()));
/* 545 */     } else if (event.getPropertyName().equalsIgnoreCase(y)) { setY(PrimitiveConverter.getInteger(event.getNewValue()));
/* 546 */     } else if (event.getPropertyName().equalsIgnoreCase(width)) { setWidth(PrimitiveConverter.getInteger(event.getNewValue()));
/* 547 */     } else if (event.getPropertyName().equalsIgnoreCase(height)) { setHeight(PrimitiveConverter.getInteger(event.getNewValue()));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void buildXML();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract IElement cloneElementStructure();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract String getTag();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDisplayX()
/*     */   {
/* 571 */     Widget widget = getWidget();
/* 572 */     if (widget != null) {
/* 573 */       return widget.getDisplayX();
/*     */     }
/*     */     
/* 576 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDisplayY()
/*     */   {
/* 585 */     Widget widget = getWidget();
/* 586 */     if (widget != null) {
/* 587 */       return widget.getDisplayY();
/*     */     }
/*     */     
/* 590 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setExpandableProperty(Property expandableProperty)
/*     */   {
/* 600 */     this.m_expandableProperty = expandableProperty;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setShrinkableProperty(Property shrinkableProperty)
/*     */   {
/* 610 */     this.m_shrinkableProperty = shrinkableProperty;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeSelfFromParent()
/*     */   {
/* 618 */     super.removeSelfFromParent();
/*     */     
/* 620 */     Widget widget = getWidget();
/* 621 */     if (widget != null) {
/* 622 */       IBasicContainer parent = widget.getParent();
/* 623 */       if ((parent instanceof org.fenggui.Container)) {
/* 624 */         ((org.fenggui.Container)parent).removeWidget(widget);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Object getEncapsulatedObject()
/*     */   {
/* 632 */     return getWidget();
/*     */   }
/*     */   
/*     */   public abstract Widget getWidget();
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 638 */     XComponent component = (XComponent)element;
/* 639 */     if (this.m_xInit) component.setX(this.m_x);
/* 640 */     if (this.m_yInit) component.setY(this.m_y);
/* 641 */     if (this.m_widthInit) component.setWidth(this.m_width);
/* 642 */     if (this.m_heightInit) component.setHeight(this.m_height);
/* 643 */     if (this.m_expandableInit) component.setExpandable(this.m_expandable);
/* 644 */     if (this.m_shrinkableInit) component.setShrinkable(this.m_shrinkable);
/* 645 */     if (this.m_usedInLayoutInit) component.setUsedInLayout(this.m_usedInLayout);
/* 646 */     if (this.m_minSize != null) component.setMinSize(this.m_minSize);
/* 647 */     if (this.m_nonBlockingInit) component.setNonBlocking(this.m_nonBlocking);
/* 648 */     if (this.m_themeElement != null) {
/* 649 */       component.m_themeElement = ((ThemeElement)this.m_themeElement.cloneAppearance());
/*     */     }
/* 651 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void applyAllAttributes();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IDragNDropable getDragAndDropParent()
/*     */   {
/* 664 */     return this.m_dndParent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDragAndDropParent(IDragNDropable component)
/*     */   {
/* 671 */     this.m_dndParent = component;
/*     */   }
/*     */   
/*     */   public void moveTo(IElement container) {
/* 675 */     if (this.m_parent != null) {
/* 676 */       XComponent element = (XComponent)this.m_parent.getChild(this);
/* 677 */       if (element == null) {
/* 678 */         return;
/*     */       }
/* 680 */       Widget widget = element.getWidget();
/* 681 */       if (widget != null) {
/* 682 */         org.fenggui.Container cont = (org.fenggui.Container)widget.getParent();
/* 683 */         if (container != null) {
/* 684 */           cont.removeWidget(element.getWidget());
/*     */         }
/*     */       }
/* 687 */       if (element != null) {
/* 688 */         while (!(container instanceof IContainer)) {
/* 689 */           container = container.getParent();
/*     */         }
/* 691 */         container.add(element);
/*     */         
/* 693 */         if (container.getEncapsulatedObject() != null) {
/* 694 */           container.addWidget(element);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */