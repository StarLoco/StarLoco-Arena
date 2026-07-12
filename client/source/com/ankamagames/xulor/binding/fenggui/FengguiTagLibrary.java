/*     */ package com.ankamagames.xulor.binding.fenggui;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XBorderLayout;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XBorderLayoutData;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XButton;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XCheckBox;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XComboBox;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XComboBoxPlus;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XContainer;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XDNDContainer;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XDisplayObjectViewer;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XFengguiList;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XGridLayout;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XImage;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XInstanciatedContainer;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XLabel;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XList;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XListItem;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XMapNavigator;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XMenu;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XMenuBar;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XMenuItem;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XMessageBox;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XPopup;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XPopupMenu;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XProgressBar;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XProperty;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XRadioButton;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XRadioGroup;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XRenderableContainer;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XRowLayout;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XSceneCanvas;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XScrollContainer;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XSeparator;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XSlider;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XStaticLayout;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XStaticLayoutData;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XTabItem;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XTabbedContainer;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XTextEditor;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XTextView;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XWindow;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XWindowMovePoint;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XWindowResizePoint;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XBevelBorder;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XGradientBackground;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XLabelAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XMargin;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XPadding;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XPixmapBackground;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XPlainBackground;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XPlainBorder;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XRoundedBorder;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XTitledBorder;
/*     */ import com.ankamagames.xulor.core.XulorTagLibrary;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FengguiTagLibrary
/*     */   extends XulorTagLibrary
/*     */ {
/*  69 */   private static FengguiTagLibrary INSTANCE = new FengguiTagLibrary();
/*     */   
/*     */   public static FengguiTagLibrary getInstance() {
/*  72 */     return INSTANCE;
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
/*     */   
/*     */   protected void registerTags() {
/*  87 */     registerTag("Window", XWindow.class);
/*  88 */     registerTag("windowMovePoint", XWindowMovePoint.class);
/*  89 */     registerTag("windowResizePoint", XWindowResizePoint.class);
/*  90 */     registerTag("Button", XButton.class);
/*  91 */     registerTag("Checkbox", XCheckBox.class);
/*  92 */     registerTag("ComboBox", XComboBox.class);
/*  93 */     registerTag("ComboBoxPlus", XComboBoxPlus.class);
/*  94 */     registerTag("Container", XContainer.class);
/*  95 */     registerTag("DisplayObjectViewer", XDisplayObjectViewer.class);
/*  96 */     registerTag("DNDContainer", XDNDContainer.class);
/*  97 */     registerTag("DNDC", XDNDContainer.class);
/*  98 */     registerTag("List", XFengguiList.class);
/*  99 */     registerTag("GridLayout", XGridLayout.class);
/* 100 */     registerTag("GL", XGridLayout.class);
/* 101 */     registerTag("InstanciatedContainer", XInstanciatedContainer.class);
/* 102 */     registerTag("IC", XInstanciatedContainer.class);
/* 103 */     registerTag("image", XImage.class);
/* 104 */     registerTag("Label", XLabel.class);
/* 105 */     registerTag("DeprecatedList", XList.class);
/* 106 */     registerTag("ListItem", XListItem.class);
/* 107 */     registerTag("LI", XListItem.class);
/* 108 */     registerTag("MessageBox", XMessageBox.class);
/* 109 */     registerTag("Menu", XMenu.class);
/* 110 */     registerTag("Menubar", XMenuBar.class);
/* 111 */     registerTag("MenuItem", XMenuItem.class);
/* 112 */     registerTag("popup", XPopup.class);
/* 113 */     registerTag("PopupMenu", XPopupMenu.class);
/* 114 */     registerTag("ProgressBar", XProgressBar.class);
/* 115 */     registerTag("Property", XProperty.class);
/* 116 */     registerTag("RadioButton", XRadioButton.class);
/* 117 */     registerTag("RadioGroup", XRadioGroup.class);
/* 118 */     registerTag("RenderableContainer", XRenderableContainer.class);
/* 119 */     registerTag("RC", XRenderableContainer.class);
/* 120 */     registerTag("SceneCanvas", XSceneCanvas.class);
/* 121 */     registerTag("ScrollContainer", XScrollContainer.class);
/* 122 */     registerTag("SC", XScrollContainer.class);
/* 123 */     registerTag("separator", XSeparator.class);
/* 124 */     registerTag("Slider", XSlider.class);
/* 125 */     registerTag("TabbedContainer", XTabbedContainer.class);
/* 126 */     registerTag("TabItem", XTabItem.class);
/* 127 */     registerTag("TextEditor", XTextEditor.class);
/* 128 */     registerTag("TextView", XTextView.class);
/* 129 */     registerTag("MapNavigator", XMapNavigator.class);
/*     */ 
/*     */     
/* 132 */     registerTag("BL", XBorderLayout.class);
/* 133 */     registerTag("BorderLayout", XBorderLayout.class);
/* 134 */     registerTag("BLD", XBorderLayoutData.class);
/* 135 */     registerTag("BorderLayoutData", XBorderLayoutData.class);
/* 136 */     registerTag("RL", XRowLayout.class);
/* 137 */     registerTag("RowLayout", XRowLayout.class);
/* 138 */     registerTag("StaticLayout", XStaticLayout.class);
/* 139 */     registerTag("SL", XStaticLayout.class);
/* 140 */     registerTag("StaticLayoutData", XStaticLayoutData.class);
/* 141 */     registerTag("sld", XStaticLayoutData.class);
/*     */ 
/*     */     
/* 144 */     registerTag("Appearance", XAppearance.class);
/* 145 */     registerTag("BevelBorder", XBevelBorder.class);
/* 146 */     registerTag("GradientBackground", XGradientBackground.class);
/* 147 */     registerTag("LabelAppearance", XLabelAppearance.class);
/* 148 */     registerTag("Margin", XMargin.class);
/* 149 */     registerTag("Padding", XPadding.class);
/* 150 */     registerTag("PixmapBackground", XPixmapBackground.class);
/* 151 */     registerTag("PlainBackground", XPlainBackground.class);
/* 152 */     registerTag("PlainBorder", XPlainBorder.class);
/* 153 */     registerTag("RoundedBorder", XRoundedBorder.class);
/* 154 */     registerTag("TitledBorder", XTitledBorder.class);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\FengguiTagLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */