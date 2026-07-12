/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.Container;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.template.IContainer;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.ILayoutManager;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.util.Dimension;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.Container;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.LayoutManager;
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
/*     */ public class XContainer
/*     */   extends XObservableComponent
/*     */   implements IContainer
/*     */ {
/*     */   public static final String TAG = "Container";
/*  36 */   protected Dimension m_wishedSize = null; protected boolean m_visible;
/*     */   protected boolean m_visibleInit = false;
/*  38 */   protected Container m_container = null;
/*     */   
/*  40 */   protected XLayoutManager m_layoutManager = null;
/*     */   
/*     */   protected void setLayoutManager(LayoutManager lm) {
/*  43 */     ((Container)getWidget()).setLayoutManager(lm);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void displayNonBlockingAvailability() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addWidget(IElement w) {
/*  55 */     Widget widget = (Widget)w.getEncapsulatedObject();
/*  56 */     if (widget == null || getWidget() == null) {
/*     */       return;
/*     */     }
/*     */     
/*  60 */     ((Container)getWidget()).addWidget((IWidget)widget);
/*     */     
/*  62 */     if (w instanceof IComponent && widget.isInWidgetTree()) {
/*  63 */       ((IComponent)w).setAddedToWidgetTree(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addContainerListeners() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {
/*  80 */     if (this.m_container == null) {
/*  81 */       this.m_container = new Container();
/*     */       
/*  83 */       applyAllAttributes();
/*     */       
/*  85 */       if (this.m_parent != null) this.m_parent.addWidget((IElement)this); 
/*  86 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_container, (IElement)this);
/*     */     }  byte b; int i;
/*     */     IElement[] arrayOfIElement;
/*  89 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  90 */       c.buildGUI(); b++; }
/*     */     
/*  92 */     applyTheme();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {
/* 101 */     if (getWidget() == null)
/*     */       return; 
/* 103 */     applyComponentAttributes();
/* 104 */     applyObservableComponentAttributes();
/* 105 */     addObservableComponentListeners();
/* 106 */     if (getWidget() instanceof Container) {
/* 107 */       if (this.m_wishedSize != null) ((Container)getWidget()).setWishedSize(this.m_wishedSize); 
/* 108 */       if (this.m_visibleInit) ((Container)getWidget()).setVisible(this.m_visible);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyTheme() {
/* 118 */     if (this.m_themeNeedToBeApplied) {
/* 119 */       this.m_themeNeedToBeApplied = false;
/* 120 */       applyContainerTheme((Container)getWidget(), this.m_themeElement);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {
/* 130 */     IElement[] components = getChildren();
/* 131 */     System.out.println("<container>"); byte b; int i; IElement[] arrayOfIElement1;
/* 132 */     for (i = (arrayOfIElement1 = components).length, b = 0; b < i; ) { IElement c = arrayOfIElement1[b];
/* 133 */       c.buildXML(); b++; }
/*     */     
/* 135 */     System.out.println("</container>");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ILayoutManager getILayoutManager() {
/* 144 */     return this.m_layoutManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinSize() {
/* 151 */     return this.m_wishedSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinSize(Dimension wishedSize) {
/* 158 */     this.m_wishedSize = wishedSize;
/* 159 */     if (getWidget() instanceof Container) {
/* 160 */       ((Container)getWidget()).setWishedSize(wishedSize);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/* 168 */     return this.m_visible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 175 */     this.m_visibleInit = true;
/* 176 */     this.m_visible = visible;
/* 177 */     if (getWidget() instanceof Container) {
/* 178 */       ((Container)getWidget()).setVisible(visible);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Widget getWidget() {
/* 187 */     return (Widget)this.m_container;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 195 */     return "Container";
/*     */   }
/*     */   
/*     */   protected void copyElementData(XContainer container) {
/* 199 */     container.setMinSize(this.m_wishedSize);
/* 200 */     if (this.m_visibleInit) container.setVisible(this.m_visible); 
/* 201 */     copyElementData((IElement)container);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 208 */     XContainer container = new XContainer();
/* 209 */     copyElementData(container);
/* 210 */     return (IElement)container;
/*     */   }
/*     */   
/*     */   public static void applyContainerTheme(Container container, ThemeElement element) {
/* 214 */     if (container == null || element == null) {
/*     */       return;
/*     */     }
/*     */     
/* 218 */     container.getAppearance().removeAll();
/* 219 */     XComponent.applyThemeAttributes((Widget)container, element.getAttributes());
/* 220 */     XSpacingAppearance.setAppearance((StandardWidget)container, element);
/* 221 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 222 */     for (ThemeAppearance app : appearances) {
/* 223 */       if (app != null)
/* 224 */         XDecoratorAppearance.setAppearance((StandardWidget)container, app); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */