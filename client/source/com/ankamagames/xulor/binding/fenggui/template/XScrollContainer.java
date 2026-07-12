/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XScrollContainerAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IScrollContainer;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeScrollContainerAppearance;
/*     */ import com.ankamagames.xulor.util.ScrollBarBehaviour;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.ScrollContainer;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.Widget;
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
/*     */ public class XScrollContainer
/*     */   extends XComponent
/*     */   implements IScrollContainer
/*     */ {
/*     */   public static final String TAG = "ScrollContainer";
/*     */   public static final String SHORT_TAG = "SC";
/*  38 */   private ScrollContainer m_scrollContainer = null;
/*     */ 
/*     */   
/*     */   private ScrollBarBehaviour m_displayHorizontalScrollBar;
/*     */ 
/*     */   
/*     */   private ScrollBarBehaviour m_displayVerticalScrollBar;
/*     */ 
/*     */   
/*     */   public void addWidget(IElement w) {
/*  48 */     Widget widget = (Widget)w.getEncapsulatedObject();
/*  49 */     if (widget == null || this.m_scrollContainer == null) {
/*     */       return;
/*     */     }
/*     */     
/*  53 */     this.m_scrollContainer.setInnerWidget((IWidget)widget);
/*     */     
/*  55 */     if (w instanceof IComponent && widget.isInWidgetTree()) {
/*  56 */       ((IComponent)w).setAddedToWidgetTree(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {
/*  67 */     if (this.m_scrollContainer == null) {
/*  68 */       this.m_scrollContainer = new ScrollContainer();
/*     */       
/*  70 */       applyAllAttributes();
/*     */       
/*  72 */       if (this.m_parent != null) this.m_parent.addWidget((IElement)this);
/*     */       
/*  74 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_scrollContainer, (IElement)this);
/*     */     }  byte b; int i;
/*     */     IElement[] arrayOfIElement;
/*  77 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  78 */       c.buildGUI();
/*     */       b++; }
/*     */     
/*  81 */     applyTheme();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {
/*  90 */     System.out.println("<scrollcontainer>"); byte b; int i; IElement[] arrayOfIElement;
/*  91 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  92 */       c.buildXML(); b++; }
/*     */     
/*  94 */     System.out.println("</scrollcontainer>");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {
/* 103 */     if (this.m_scrollContainer == null)
/*     */       return; 
/* 105 */     applyComponentAttributes();
/* 106 */     if (this.m_displayHorizontalScrollBar != null) {
/* 107 */       this.m_scrollContainer.setHorizontalScrollBarBehaviour(FengguiConstant.toFengguiScrollBarBehaviour(this.m_displayHorizontalScrollBar));
/*     */     }
/* 109 */     if (this.m_displayVerticalScrollBar != null) {
/* 110 */       this.m_scrollContainer.setVerticalScrollBarBehaviour(FengguiConstant.toFengguiScrollBarBehaviour(this.m_displayVerticalScrollBar));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyTheme() {
/* 116 */     if (this.m_themeNeedToBeApplied) {
/* 117 */       this.m_themeNeedToBeApplied = false;
/* 118 */       applyScrollContainerTheme(this.m_scrollContainer, this.m_themeElement);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScrollBarBehaviour getDisplayHorizontalScrollBar() {
/* 127 */     return this.m_displayHorizontalScrollBar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplayHorizontalScrollBar(ScrollBarBehaviour displayHorizontalScrollBar) {
/* 134 */     this.m_displayHorizontalScrollBar = displayHorizontalScrollBar;
/* 135 */     if (this.m_scrollContainer != null) {
/* 136 */       this.m_scrollContainer.setHorizontalScrollBarBehaviour(FengguiConstant.toFengguiScrollBarBehaviour(this.m_displayHorizontalScrollBar));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScrollBarBehaviour getDisplayVerticalScrollBar() {
/* 144 */     return this.m_displayVerticalScrollBar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplayVerticalScrollBar(ScrollBarBehaviour displayVerticalScrollBar) {
/* 151 */     this.m_displayVerticalScrollBar = displayVerticalScrollBar;
/* 152 */     if (this.m_scrollContainer != null) {
/* 153 */       this.m_scrollContainer.setVerticalScrollBarBehaviour(FengguiConstant.toFengguiScrollBarBehaviour(this.m_displayVerticalScrollBar));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Widget getWidget() {
/* 162 */     return (Widget)this.m_scrollContainer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 170 */     return "ScrollContainer";
/*     */   }
/*     */ 
/*     */   
/*     */   public void copyElementData(XScrollContainer sc) {
/* 175 */     copyElementData((IElement)sc);
/* 176 */     if (this.m_displayHorizontalScrollBar != null) sc.setDisplayHorizontalScrollBar(this.m_displayHorizontalScrollBar); 
/* 177 */     if (this.m_displayVerticalScrollBar != null) sc.setDisplayVerticalScrollBar(this.m_displayVerticalScrollBar);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 184 */     XScrollContainer elem = new XScrollContainer();
/* 185 */     copyElementData(elem);
/* 186 */     return (IElement)elem;
/*     */   }
/*     */   
/*     */   public static void applyScrollContainerTheme(ScrollContainer container, ThemeElement element) {
/* 190 */     if (container == null || element == null) {
/*     */       return;
/*     */     }
/*     */     
/* 194 */     container.getAppearance().removeAll();
/* 195 */     XComponent.applyThemeAttributes((Widget)container, element.getAttributes());
/* 196 */     XSpacingAppearance.setAppearance((StandardWidget)container, element);
/* 197 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 198 */     for (ThemeAppearance app : appearances) {
/* 199 */       if (app != null) {
/* 200 */         XDecoratorAppearance.setAppearance((StandardWidget)container, app);
/* 201 */         if (app instanceof ThemeScrollContainerAppearance)
/* 202 */           XScrollContainerAppearance.setAppearance(container, (ThemeScrollContainerAppearance)app); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XScrollContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */