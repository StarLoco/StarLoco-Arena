/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IList;
/*     */ import org.fenggui.List;
/*     */ import org.fenggui.ListItem;
/*     */ import org.fenggui.ToggableGroup;
/*     */ import org.fenggui.Widget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XList
/*     */   extends XAbstractList
/*     */   implements IList
/*     */ {
/*     */   public static final String TAG = "DeprecatedList";
/*  21 */   public List m_list = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean m_multiple = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {
/*  31 */     System.out.println("<list>"); byte b; int i; IElement[] arrayOfIElement;
/*  32 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  33 */       c.buildXML(); b++; }
/*     */     
/*  35 */     System.out.println("</list>");
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
/*     */   public void buildGUI() {
/*  47 */     if (this.m_list == null) {
/*     */       
/*  49 */       if (this.m_multiple) {
/*  50 */         this.m_list = new List(ToggableGroup.MULTIPLE_SELECTION);
/*     */       } else {
/*  52 */         this.m_list = new List(ToggableGroup.SINGLE_SELECTION);
/*     */       } 
/*     */ 
/*     */       
/*  56 */       applyAllAttributes();
/*     */       
/*  58 */       if (this.m_parent != null) this.m_parent.addWidget((IElement)this); 
/*  59 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_list, (IElement)this);
/*     */     }  byte b; int i;
/*     */     IElement[] arrayOfIElement;
/*  62 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  63 */       c.buildGUI(); b++; }
/*     */     
/*  65 */     applyTheme();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {
/*  74 */     if (this.m_list == null)
/*     */       return; 
/*  76 */     applyComponentAttributes();
/*     */   }
/*     */   
/*     */   public void applyTheme() {
/*  80 */     if (this.m_themeNeedToBeApplied) {
/*  81 */       this.m_themeNeedToBeApplied = false;
/*     */     }
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
/*     */   public void setMultiple(boolean m) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addItem(ListItem item) {
/* 103 */     this.m_list.addItem(item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedItem(Object item) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getSelectedItem() {
/* 120 */     return this.m_list.getToggableWidgetGroup().getSelectedItem();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getItems() {
/* 130 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItems(Object items) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Widget getWidget() {
/* 147 */     return (Widget)this.m_list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 155 */     return "DeprecatedList";
/*     */   }
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 159 */     XList list = (XList)element;
/* 160 */     list.m_multiple = this.m_multiple;
/* 161 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 168 */     XList list = new XList();
/* 169 */     copyElementData((IElement)list);
/* 170 */     return (IElement)list;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */