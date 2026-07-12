/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.core.impl.XElement;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import java.io.PrintStream;
/*     */ import org.fenggui.TabContainer;
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
/*     */ public class XTabItem
/*     */   extends XElement
/*     */ {
/*     */   public static final String TAG = "TabItem";
/*  25 */   private String m_text = null;
/*     */   
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/*     */     IElement[] arrayOfIElement;
/*     */     
/*  33 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  34 */       c.buildGUI();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML()
/*     */   {
/*  44 */     System.out.println("<tabbedcontainer>");
/*  45 */     IElement[] arrayOfIElement; int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  46 */       c.buildXML();
/*     */     }
/*  48 */     System.out.println("</tabbedcontainer>");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setText(String text)
/*     */   {
/*  64 */     this.m_text = text;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addWidget(IElement w)
/*     */   {
/*  74 */     Widget widget = (Widget)w.getEncapsulatedObject();
/*  75 */     if ((!(this.m_parent instanceof XTabbedContainer)) || (widget == null) || (this.m_parent.getEncapsulatedObject() == null)) {
/*  76 */       return;
/*     */     }
/*     */     
/*  79 */     ((TabContainer)this.m_parent.getEncapsulatedObject()).addTab(this.m_text, null, widget);
/*     */     
/*  81 */     if (((w instanceof IComponent)) && (widget.isInWidgetTree())) {
/*  82 */       ((IComponent)w).setAddedToWidgetTree(true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getEncapsulatedObject()
/*     */   {
/*  92 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 100 */     return "TabItem";
/*     */   }
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 104 */     XTabItem tabItem = (XTabItem)element;
/* 105 */     tabItem.m_text = this.m_text;
/* 106 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 113 */     XTabItem tabItem = new XTabItem();
/* 114 */     copyElementData(tabItem);
/* 115 */     return tabItem;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XTabItem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */