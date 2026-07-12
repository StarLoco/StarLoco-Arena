/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XComboBoxAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.util.Togglable;
/*     */ import com.ankamagames.xulor.event.SelectionChangedEvent;
/*     */ import com.ankamagames.xulor.event.listener.SelectionChangedListener;
/*     */ import com.ankamagames.xulor.template.IComboBox;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.ISelection;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeComboBoxAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.ComboBox;
/*     */ import org.fenggui.ListItem;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.Widget;
/*     */ import org.fenggui.event.ISelectionChangedListener;
/*     */ import org.fenggui.event.SelectionChangedEvent;
/*     */ 
/*     */ 
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
/*  37 */   private ArrayList<SelectionChangedListener> m_scl = new ArrayList<SelectionChangedListener>();
/*     */   
/*     */   private ISelectionChangedListener m_selectionChangedListener;
/*     */   private XComboBox THIS;
/*     */   
/*     */   public XComboBox() {
/*  43 */     this.THIS = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {
/*  53 */     System.out.println("<combobox>"); byte b; int i; IElement[] arrayOfIElement;
/*  54 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  55 */       c.buildXML(); b++; }
/*     */     
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
/*     */   
/*     */   public void buildGUI() {
/*  69 */     if (this.m_comboBox == null) {
/*  70 */       this.m_comboBox = new ComboBox();
/*     */ 
/*     */       
/*  73 */       applyAllAttributes();
/*     */       
/*  75 */       this.m_selectionChangedListener = new ISelectionChangedListener() {
/*     */           public void selectionChanged(SelectionChangedEvent selectionChangedEvent) {
/*  77 */             SelectionChangedEvent event = new SelectionChangedEvent((IElement)XComboBox.this.THIS, (ISelection)new Togglable(selectionChangedEvent.getToggableWidget()), selectionChangedEvent.isSelected(), null);
/*  78 */             for (SelectionChangedListener l : XComboBox.this.m_scl)
/*  79 */               l.run(event); 
/*     */           }
/*     */         };
/*  82 */       this.m_comboBox.addSelectionChangedListener(this.m_selectionChangedListener);
/*     */       
/*  84 */       if (this.m_parent != null) this.m_parent.addWidget((IElement)this); 
/*  85 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_comboBox, (IElement)this);
/*     */     }  byte b; int i;
/*     */     IElement[] arrayOfIElement;
/*  88 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  89 */       c.buildGUI();
/*     */       b++; }
/*     */     
/*  92 */     applyTheme();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {
/* 102 */     if (this.m_comboBox == null) {
/*     */       return;
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
/*     */   
/*     */   protected void addItem(ListItem item) {
/* 124 */     this.m_comboBox.addItem(item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getItems() {
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
/*     */   
/*     */   public void setItems(Object items) {
/* 152 */     if (items instanceof Iterable) {
/* 153 */       this.m_items = new ArrayList<ListItem<Object>>();
/* 154 */       for (Object item : items) {
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
/*     */   
/*     */   public Object getSelectedItem() {
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
/*     */   
/*     */   public void setSelectedItem(Object item) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateItems() {
/* 187 */     if (this.m_comboBox != null && this.m_items != null) {
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
/*     */   
/*     */   public Widget getWidget() {
/* 200 */     return (Widget)this.m_comboBox;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 208 */     return "ComboBox";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnSelectionChange(SelectionChangedListener selectionChangedListener) {
/* 215 */     this.m_scl.add(selectionChangedListener);
/*     */   }
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 219 */     XComboBox combobox = (XComboBox)element;
/* 220 */     if (this.m_items != null) {
/* 221 */       combobox.m_items = (ArrayList<ListItem<Object>>)this.m_items.clone();
/*     */     }
/* 223 */     for (SelectionChangedListener scl : this.m_scl) {
/* 224 */       combobox.setOnSelectionChange(scl);
/*     */     }
/* 226 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 233 */     XComboBox combobox = new XComboBox();
/* 234 */     copyElementData((IElement)combobox);
/* 235 */     return (IElement)combobox;
/*     */   }
/*     */   
/*     */   public static void applyComboBoxTheme(ComboBox comboBox, ThemeElement element) {
/* 239 */     if (comboBox == null || element == null) {
/*     */       return;
/*     */     }
/*     */     
/* 243 */     comboBox.getAppearance().removeAll();
/* 244 */     XComponent.applyThemeAttributes((Widget)comboBox, element.getAttributes());
/* 245 */     XSpacingAppearance.setAppearance((StandardWidget)comboBox, element);
/* 246 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 247 */     for (ThemeAppearance app : appearances) {
/* 248 */       if (app != null) {
/* 249 */         XDecoratorAppearance.setAppearance((StandardWidget)comboBox, app);
/* 250 */         if (app instanceof ThemeComboBoxAppearance)
/* 251 */           XComboBoxAppearance.setAppearance(comboBox, (ThemeComboBoxAppearance)app); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XComboBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */