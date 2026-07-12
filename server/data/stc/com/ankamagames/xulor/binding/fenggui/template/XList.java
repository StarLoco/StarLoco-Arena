/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IList;
/*     */ import java.io.PrintStream;
/*     */ import org.fenggui.List;
/*     */ import org.fenggui.ListItem;
/*     */ import org.fenggui.ToggableGroup;
/*     */ import org.fenggui.Widget;
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
/*  23 */   private boolean m_multiple = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML()
/*     */   {
/*  31 */     System.out.println("<list>");
/*  32 */     IElement[] arrayOfIElement; int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  33 */       c.buildXML();
/*     */     }
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
/*     */   public void buildGUI()
/*     */   {
/*  47 */     if (this.m_list == null)
/*     */     {
/*  49 */       if (this.m_multiple) {
/*  50 */         this.m_list = new List(ToggableGroup.MULTIPLE_SELECTION);
/*     */       } else {
/*  52 */         this.m_list = new List(ToggableGroup.SINGLE_SELECTION);
/*     */       }
/*     */       
/*     */ 
/*  56 */       applyAllAttributes();
/*     */       
/*  58 */       if (this.m_parent != null) this.m_parent.addWidget(this);
/*  59 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_list, this);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  62 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  63 */       c.buildGUI();
/*     */     }
/*  65 */     applyTheme();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/*  74 */     if (this.m_list == null)
/*  75 */       return;
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
/*     */   public void setMultiple(boolean m) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void addItem(ListItem item)
/*     */   {
/* 103 */     this.m_list.addItem(item);
/*     */   }
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
/*     */   public Object getSelectedItem()
/*     */   {
/* 120 */     return this.m_list.getToggableWidgetGroup().getSelectedItem();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getItems()
/*     */   {
/* 130 */     return null;
/*     */   }
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
/*     */   public Widget getWidget()
/*     */   {
/* 147 */     return this.m_list;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
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
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 168 */     XList list = new XList();
/* 169 */     copyElementData(list);
/* 170 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */