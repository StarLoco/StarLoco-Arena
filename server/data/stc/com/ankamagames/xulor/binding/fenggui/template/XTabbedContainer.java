/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XTabbedContainerAppearance;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.ITabbedContainer;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeTabbedContainerAppearance;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.TabContainer;
/*     */ import org.fenggui.TabContainer.TabContainerAppearance;
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
/*     */ public class XTabbedContainer
/*     */   extends XComponent
/*     */   implements ITabbedContainer
/*     */ {
/*     */   public static final String TAG = "TabbedContainer";
/*  35 */   private TabContainer m_tabbedContainer = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/*  44 */     if (this.m_tabbedContainer == null) {
/*  45 */       this.m_tabbedContainer = new TabContainer();
/*     */       
/*  47 */       if (this.m_parent != null) { this.m_parent.addWidget(this);
/*     */       }
/*  49 */       applyAllAttributes();
/*     */       
/*  51 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_tabbedContainer, this);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  54 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  55 */       c.buildGUI();
/*     */     }
/*     */     
/*  58 */     applyTheme();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML()
/*     */   {
/*  67 */     System.out.println("<tabbedcontainer>");
/*  68 */     IElement[] arrayOfIElement; int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  69 */       c.buildXML();
/*     */     }
/*  71 */     System.out.println("</tabbedcontainer>");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/*  80 */     if (this.m_tabbedContainer == null) {
/*  81 */       return;
/*     */     }
/*  83 */     applyComponentAttributes();
/*     */   }
/*     */   
/*     */   public void applyTheme() {
/*  87 */     if (this.m_themeNeedToBeApplied) {
/*  88 */       this.m_themeNeedToBeApplied = false;
/*  89 */       applyTabbedContainerTheme(this.m_tabbedContainer, this.m_themeElement);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void addTab(String text, String icon, IWidget widget)
/*     */   {
/* 101 */     this.m_tabbedContainer.addTab(text, null, widget);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addWidget(IElement w)
/*     */   {
/* 110 */     Widget widget = (Widget)w.getEncapsulatedObject();
/* 111 */     if ((widget == null) || (this.m_tabbedContainer == null)) {
/* 112 */       return;
/*     */     }
/*     */     
/* 115 */     this.m_tabbedContainer.addWidget(widget);
/*     */     
/* 117 */     if (((w instanceof IComponent)) && (widget.isInWidgetTree())) {
/* 118 */       ((IComponent)w).setAddedToWidgetTree(true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Widget getWidget()
/*     */   {
/* 127 */     return this.m_tabbedContainer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 135 */     return "TabbedContainer";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 142 */     XTabbedContainer elem = new XTabbedContainer();
/* 143 */     copyElementData(elem);
/* 144 */     return elem;
/*     */   }
/*     */   
/*     */   public static void applyTabbedContainerTheme(TabContainer container, ThemeElement element) {
/* 148 */     if ((container == null) || (element == null)) {
/* 149 */       return;
/*     */     }
/*     */     
/* 152 */     container.getAppearance().removeAll();
/* 153 */     XComponent.applyThemeAttributes(container, element.getAttributes());
/* 154 */     XSpacingAppearance.setAppearance(container, element);
/* 155 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 156 */     for (ThemeAppearance app : appearances) {
/* 157 */       if (app != null) {
/* 158 */         XDecoratorAppearance.setAppearance(container, app);
/* 159 */         if ((app instanceof ThemeTabbedContainerAppearance)) {
/* 160 */           XTabbedContainerAppearance.setAppearance(container, (ThemeTabbedContainerAppearance)app);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XTabbedContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */