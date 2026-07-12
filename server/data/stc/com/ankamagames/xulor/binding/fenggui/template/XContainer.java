/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.template.IContainer;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.ILayoutManager;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.util.Dimension;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.Container.ContainerAppearance;
/*     */ import org.fenggui.LayoutManager;
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
/*     */ public class XContainer
/*     */   extends XObservableComponent
/*     */   implements IContainer
/*     */ {
/*     */   public static final String TAG = "Container";
/*  36 */   protected Dimension m_wishedSize = null;
/*  37 */   protected boolean m_visible; protected boolean m_visibleInit = false;
/*  38 */   protected com.ankamagames.xulor.binding.fenggui.component.Container m_container = null;
/*     */   
/*  40 */   protected XLayoutManager m_layoutManager = null;
/*     */   
/*     */   protected void setLayoutManager(LayoutManager lm) {
/*  43 */     ((com.ankamagames.xulor.binding.fenggui.component.Container)getWidget()).setLayoutManager(lm);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void displayNonBlockingAvailability() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void addWidget(IElement w)
/*     */   {
/*  55 */     Widget widget = (Widget)w.getEncapsulatedObject();
/*  56 */     if ((widget == null) || (getWidget() == null)) {
/*  57 */       return;
/*     */     }
/*     */     
/*  60 */     ((com.ankamagames.xulor.binding.fenggui.component.Container)getWidget()).addWidget(widget);
/*     */     
/*  62 */     if (((w instanceof IComponent)) && (widget.isInWidgetTree())) {
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
/*     */   public void buildGUI()
/*     */   {
/*  80 */     if (this.m_container == null) {
/*  81 */       this.m_container = new com.ankamagames.xulor.binding.fenggui.component.Container();
/*     */       
/*  83 */       applyAllAttributes();
/*     */       
/*  85 */       if (this.m_parent != null) this.m_parent.addWidget(this);
/*  86 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_container, this);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  89 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  90 */       c.buildGUI();
/*     */     }
/*  92 */     applyTheme();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/* 101 */     if (getWidget() == null)
/* 102 */       return;
/* 103 */     applyComponentAttributes();
/* 104 */     applyObservableComponentAttributes();
/* 105 */     addObservableComponentListeners();
/* 106 */     if ((getWidget() instanceof com.ankamagames.xulor.binding.fenggui.component.Container)) {
/* 107 */       if (this.m_wishedSize != null) ((com.ankamagames.xulor.binding.fenggui.component.Container)getWidget()).setWishedSize(this.m_wishedSize);
/* 108 */       if (this.m_visibleInit) { ((com.ankamagames.xulor.binding.fenggui.component.Container)getWidget()).setVisible(this.m_visible);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyTheme()
/*     */   {
/* 118 */     if (this.m_themeNeedToBeApplied) {
/* 119 */       this.m_themeNeedToBeApplied = false;
/* 120 */       applyContainerTheme((com.ankamagames.xulor.binding.fenggui.component.Container)getWidget(), this.m_themeElement);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML()
/*     */   {
/* 130 */     IElement[] components = getChildren();
/* 131 */     System.out.println("<container>");
/* 132 */     IElement[] arrayOfIElement1; int j = (arrayOfIElement1 = components).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement1[i];
/* 133 */       c.buildXML();
/*     */     }
/* 135 */     System.out.println("</container>");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ILayoutManager getILayoutManager()
/*     */   {
/* 144 */     return this.m_layoutManager;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Dimension getMinSize()
/*     */   {
/* 151 */     return this.m_wishedSize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMinSize(Dimension wishedSize)
/*     */   {
/* 158 */     this.m_wishedSize = wishedSize;
/* 159 */     if ((getWidget() instanceof com.ankamagames.xulor.binding.fenggui.component.Container)) {
/* 160 */       ((com.ankamagames.xulor.binding.fenggui.component.Container)getWidget()).setWishedSize(wishedSize);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isVisible()
/*     */   {
/* 168 */     return this.m_visible;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setVisible(boolean visible)
/*     */   {
/* 175 */     this.m_visibleInit = true;
/* 176 */     this.m_visible = visible;
/* 177 */     if ((getWidget() instanceof com.ankamagames.xulor.binding.fenggui.component.Container)) {
/* 178 */       ((com.ankamagames.xulor.binding.fenggui.component.Container)getWidget()).setVisible(visible);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Widget getWidget()
/*     */   {
/* 187 */     return this.m_container;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 195 */     return "Container";
/*     */   }
/*     */   
/*     */   protected void copyElementData(XContainer container) {
/* 199 */     container.setMinSize(this.m_wishedSize);
/* 200 */     if (this.m_visibleInit) container.setVisible(this.m_visible);
/* 201 */     super.copyElementData(container);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 208 */     XContainer container = new XContainer();
/* 209 */     copyElementData(container);
/* 210 */     return container;
/*     */   }
/*     */   
/*     */   public static void applyContainerTheme(org.fenggui.Container container, ThemeElement element) {
/* 214 */     if ((container == null) || (element == null)) {
/* 215 */       return;
/*     */     }
/*     */     
/* 218 */     container.getAppearance().removeAll();
/* 219 */     XComponent.applyThemeAttributes(container, element.getAttributes());
/* 220 */     XSpacingAppearance.setAppearance(container, element);
/* 221 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 222 */     for (ThemeAppearance app : appearances) {
/* 223 */       if (app != null) {
/* 224 */         XDecoratorAppearance.setAppearance(container, app);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */