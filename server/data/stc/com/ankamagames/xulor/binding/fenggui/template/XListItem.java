/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.core.impl.XElement;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.ITogglable;
/*     */ import java.io.PrintStream;
/*     */ import org.fenggui.ListItem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XListItem
/*     */   extends XElement
/*     */   implements ITogglable
/*     */ {
/*     */   public static final String TAG = "ListItem";
/*     */   public static final String SHORT_TAG = "LI";
/*  20 */   private ListItem m_listItem = null;
/*     */   
/*  22 */   private String m_text = null;
/*  23 */   private String m_value = null;
/*  24 */   private boolean m_selected = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML()
/*     */   {
/*  32 */     System.out.println("<listitem m_text=\"" + this.m_text + "\" value=\"" + this.m_value + "\"selected=\"" + this.m_selected + "\">");
/*  33 */     IElement[] arrayOfIElement; int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  34 */       c.buildXML();
/*     */     }
/*  36 */     System.out.println("</listitem>");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/*  47 */     if (this.m_listItem == null)
/*     */     {
/*  49 */       ListItem<Object> i = new ListItem(this.m_text, this.m_value);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  56 */       applyAllAttributes();
/*     */       
/*  58 */       if ((this.m_parent instanceof XAbstractList)) {
/*  59 */         ((XAbstractList)this.m_parent).addItem(i);
/*     */       }
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  63 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  64 */       c.buildGUI();
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
/*     */   public void applyAllAttributes()
/*     */   {
/*  77 */     if (this.m_listItem == null)
/*  78 */       return;
/*  79 */     if (this.m_text != null) {
/*  80 */       this.m_listItem.setText(this.m_text);
/*     */     }
/*  82 */     if (this.m_value != null) {
/*  83 */       this.m_listItem.setValue(this.m_value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setText(String text)
/*     */   {
/*  94 */     this.m_text = text;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setValue(String value)
/*     */   {
/* 101 */     this.m_value = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ITogglable setSelected(boolean selected)
/*     */   {
/* 108 */     this.m_selected = selected;
/* 109 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getEncapsulatedObject()
/*     */   {
/* 120 */     return this.m_listItem;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 128 */     return "ListItem";
/*     */   }
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 132 */     XListItem item = (XListItem)element;
/* 133 */     item.m_selected = this.m_selected;
/* 134 */     item.m_text = this.m_text;
/* 135 */     item.m_value = this.m_value;
/* 136 */     super.copyElementData(item);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 143 */     XListItem item = new XListItem();
/* 144 */     copyElementData(item);
/* 145 */     return item;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getText()
/*     */   {
/* 152 */     return this.m_text;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object getValue()
/*     */   {
/* 159 */     return this.m_value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean getSelected()
/*     */   {
/* 166 */     if (this.m_listItem != null) {
/* 167 */       return this.m_listItem.isSelected();
/*     */     }
/* 169 */     return this.m_selected;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XListItem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */