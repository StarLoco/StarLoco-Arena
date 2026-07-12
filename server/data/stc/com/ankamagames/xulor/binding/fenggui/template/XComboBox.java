/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XComboBoxAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.util.Togglable;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.event.listener.SelectionChangedListener;
/*     */ import com.ankamagames.xulor.template.IComboBox;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeComboBoxAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.ComboBox;
/*     */ import org.fenggui.ComboBoxAppearance;
/*     */ import org.fenggui.IToggable;
/*     */ import org.fenggui.List;
/*     */ import org.fenggui.ListItem;
/*     */ import org.fenggui.ToggableGroup;
/*     */ import org.fenggui.Widget;
/*     */ import org.fenggui.event.ISelectionChangedListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XComboBox
/*     */   extends XAbstractList
/*     */   implements IComboBox
/*     */ {
/*     */   public static final String TAG = "ComboBox";
/*  33 */   private ComboBox<Object> m_comboBox = null;
/*     */   
/*  35 */   private ArrayList<ListItem<Object>> m_items = null;
/*     */   
/*  37 */   private ArrayList<SelectionChangedListener> m_scl = new ArrayList();
/*     */   private ISelectionChangedListener m_selectionChangedListener;
/*     */   private XComboBox THIS;
/*     */   
/*     */   public XComboBox()
/*     */   {
/*  43 */     this.THIS = this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML()
/*     */   {
/*  53 */     System.out.println("<combobox>");
/*  54 */     IElement[] arrayOfIElement; int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  55 */       c.buildXML();
/*     */     }
/*  57 */     System.out.println("</combobox>");
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
/*  69 */     if (this.m_comboBox == null) {
/*  70 */       this.m_comboBox = new ComboBox();
/*     */       
/*     */ 
/*  73 */       applyAllAttributes();
/*     */       
/*  75 */       this.m_selectionChangedListener = new ISelectionChangedListener() {
/*     */         public void selectionChanged(org.fenggui.event.SelectionChangedEvent selectionChangedEvent) {
/*  77 */           com.ankamagames.xulor.event.SelectionChangedEvent event = new com.ankamagames.xulor.event.SelectionChangedEvent(XComboBox.this.THIS, new Togglable(selectionChangedEvent.getToggableWidget()), selectionChangedEvent.isSelected(), null);
/*  78 */           for (SelectionChangedListener l : XComboBox.this.m_scl)
/*  79 */             l.run(event);
/*     */         }
/*  81 */       };
/*  82 */       this.m_comboBox.addSelectionChangedListener(this.m_selectionChangedListener);
/*     */       
/*  84 */       if (this.m_parent != null) this.m_parent.addWidget(this);
/*  85 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_comboBox, this);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  88 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  89 */       c.buildGUI();
/*     */     }
/*     */     
/*  92 */     applyTheme();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/* 102 */     if (this.m_comboBox == null) {
/* 103 */       return;
/*     */     }
/* 105 */     if (this.m_items != null) {
/* 106 */       updateItems();
/*     */     }
/* 108 */     applyComponentAttributes();
/*     */   }
/*     */   
/*     */   public void applyTheme() {
/* 112 */     if (this.m_themeNeedToBeApplied) {
/* 113 */       this.m_themeNeedToBeApplied = false;
/* 114 */       applyComboBoxTheme(this.m_comboBox, this.m_themeElement);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void addItem(ListItem item)
/*     */   {
/* 124 */     this.m_comboBox.addItem(item);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getItems()
/*     */   {
/* 134 */     if (this.m_comboBox != null) {
/* 135 */       ArrayList<ListItem<Object>> listItems = this.m_comboBox.getList().getItems();
/* 136 */       ArrayList<Object> items = new ArrayList();
/* 137 */       for (ListItem<Object> listItem : listItems) {
/* 138 */         items.add(listItem.getValue());
/*     */       }
/* 140 */       return items;
/*     */     }
/* 142 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setItems(Object items)
/*     */   {
/* 152 */     if ((items instanceof Iterable)) {
/* 153 */       this.m_items = new ArrayList();
/* 154 */       for (Object item : (Iterable)items) {
/* 155 */         this.m_items.add(new ListItem(item.toString(), item));
/*     */       }
/* 157 */       if (this.m_comboBox != null) {
/* 158 */         updateItems();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getSelectedItem()
/*     */   {
/* 169 */     if (this.m_comboBox != null) {
/* 170 */       return this.m_comboBox.getList().getToggableWidgetGroup().getSelectedItem().getValue();
/*     */     }
/* 172 */     return null;
/*     */   }
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
/*     */   private void updateItems()
/*     */   {
/* 187 */     if ((this.m_comboBox != null) && (this.m_items != null)) {
/* 188 */       this.m_comboBox.getList().clear();
/* 189 */       for (ListItem<Object> item : this.m_items) {
/* 190 */         this.m_comboBox.addItem(item);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Widget getWidget()
/*     */   {
/* 200 */     return this.m_comboBox;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 208 */     return "ComboBox";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setOnSelectionChange(SelectionChangedListener selectionChangedListener)
/*     */   {
/* 215 */     this.m_scl.add(selectionChangedListener);
/*     */   }
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 219 */     XComboBox combobox = (XComboBox)element;
/* 220 */     if (this.m_items != null) {
/* 221 */       combobox.m_items = ((ArrayList)this.m_items.clone());
/*     */     }
/* 223 */     for (SelectionChangedListener scl : this.m_scl) {
/* 224 */       combobox.setOnSelectionChange(scl);
/*     */     }
/* 226 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 233 */     XComboBox combobox = new XComboBox();
/* 234 */     copyElementData(combobox);
/* 235 */     return combobox;
/*     */   }
/*     */   
/*     */   public static void applyComboBoxTheme(ComboBox comboBox, ThemeElement element) {
/* 239 */     if ((comboBox == null) || (element == null)) {
/* 240 */       return;
/*     */     }
/*     */     
/* 243 */     comboBox.getAppearance().removeAll();
/* 244 */     XComponent.applyThemeAttributes(comboBox, element.getAttributes());
/* 245 */     XSpacingAppearance.setAppearance(comboBox, element);
/* 246 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 247 */     for (ThemeAppearance app : appearances) {
/* 248 */       if (app != null) {
/* 249 */         XDecoratorAppearance.setAppearance(comboBox, app);
/* 250 */         if ((app instanceof ThemeComboBoxAppearance)) {
/* 251 */           XComboBoxAppearance.setAppearance(comboBox, (ThemeComboBoxAppearance)app);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XComboBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */