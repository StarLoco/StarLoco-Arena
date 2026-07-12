/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.ModalManager;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.ComboBox;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.List;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.RenderableContainer;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XComboBoxAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.event.listener.SelectionChangedListener;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeComboBoxAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.util.Item;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.Button;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.Widget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XComboBoxPlus
/*     */   extends XObservableComponent
/*     */ {
/*     */   public static final String TAG = "ComboBoxPlus";
/*  32 */   private ComboBox m_comboBox = null;
/*     */   
/*  34 */   private ArrayList<SelectionChangedListener> m_scl = new ArrayList<SelectionChangedListener>();
/*     */   private Item[] m_content;
/*  36 */   private XFengguiList m_list = null;
/*  37 */   private XRenderableContainer m_renderable = null;
/*  38 */   private XButton m_button = null;
/*     */   private Object m_selectedValue;
/*  40 */   private int m_maxRows = -1;
/*     */   
/*     */   public void add(IElement element) {
/*  43 */     if (element instanceof XFengguiList) {
/*  44 */       this.m_list = (XFengguiList)element;
/*  45 */       element.setModalLevel(ModalManager.POP_UP_MODAL_LEVEL);
/*  46 */     } else if (element instanceof XRenderableContainer) {
/*  47 */       this.m_renderable = (XRenderableContainer)element;
/*  48 */     } else if (element instanceof XButton) {
/*  49 */       this.m_button = (XButton)element;
/*     */     } else {
/*  51 */       super.add(element);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {
/*  62 */     System.out.println("<combobox>"); byte b; int i; IElement[] arrayOfIElement;
/*  63 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  64 */       c.buildXML(); b++; }
/*     */     
/*  66 */     System.out.println("</combobox>");
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
/*  78 */     if (this.m_comboBox == null) {
/*  79 */       this.m_comboBox = new ComboBox();
/*     */       
/*  81 */       applyAllAttributes();
/*     */       
/*  83 */       if (this.m_parent != null) this.m_parent.addWidget((IElement)this); 
/*  84 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_comboBox, (IElement)this);
/*     */     }  byte b; int i;
/*     */     IElement[] arrayOfIElement;
/*  87 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  88 */       c.buildGUI();
/*     */       b++; }
/*     */     
/*  91 */     applyTheme();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {
/* 101 */     if (this.m_comboBox == null) {
/*     */       return;
/*     */     }
/*     */     
/* 105 */     for (SelectionChangedListener scl : this.m_scl) {
/* 106 */       this.m_comboBox.addSelectionChangedListener(scl);
/*     */     }
/*     */     
/* 109 */     if (this.m_list != null) {
/* 110 */       this.m_list.buildGUI();
/* 111 */       this.m_comboBox.setList((List)this.m_list.getWidget());
/*     */     } 
/*     */     
/* 114 */     if (this.m_renderable != null) {
/* 115 */       this.m_renderable.buildGUI();
/* 116 */       this.m_comboBox.setRenderable((RenderableContainer)this.m_renderable.getWidget());
/*     */     } 
/*     */     
/* 119 */     if (this.m_button != null) {
/* 120 */       this.m_button.buildGUI();
/* 121 */       this.m_comboBox.setButton((Button)this.m_button.getWidget());
/*     */     } 
/*     */     
/* 124 */     this.m_comboBox.setItems(this.m_content);
/* 125 */     if (this.m_selectedValue != null) this.m_comboBox.setSelectedValue(this.m_selectedValue); 
/* 126 */     this.m_comboBox.setMaxRows(this.m_maxRows);
/*     */     
/* 128 */     applyComponentAttributes();
/*     */   }
/*     */   
/*     */   public void applyTheme() {
/* 132 */     if (this.m_themeNeedToBeApplied) {
/* 133 */       this.m_themeNeedToBeApplied = false;
/* 134 */       applyComboBoxTheme(this.m_comboBox, this.m_themeElement);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContent(Object[] content) {
/*     */     Item[] items;
/* 143 */     if (content != null) {
/* 144 */       items = new Item[content.length];
/* 145 */       for (int i = 0; i < content.length; i++) {
/* 146 */         Object value = content[i];
/* 147 */         if (value instanceof Item) {
/* 148 */           items[i] = (Item)value;
/*     */         } else {
/* 150 */           items[i] = new Item(value);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 154 */       items = (Item[])null;
/*     */     } 
/*     */     
/* 157 */     this.m_content = items;
/* 158 */     if (this.m_comboBox != null) {
/* 159 */       this.m_comboBox.setItems(this.m_content);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Widget getWidget() {
/* 168 */     return (Widget)this.m_comboBox;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 176 */     return "ComboBoxPlus";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnSelectionChange(SelectionChangedListener selectionChangedListener) {
/* 183 */     this.m_scl.add(selectionChangedListener);
/*     */   }
/*     */   
/*     */   public void setSelectedValue(Object value) {
/* 187 */     this.m_selectedValue = value;
/* 188 */     if (this.m_comboBox != null) {
/* 189 */       this.m_comboBox.setSelectedValue(this.m_selectedValue);
/*     */     }
/*     */   }
/*     */   
/*     */   public Object getSelectedValue() {
/* 194 */     if (this.m_comboBox != null) {
/* 195 */       return this.m_comboBox.getSelectedValue();
/*     */     }
/* 197 */     return this.m_selectedValue;
/*     */   }
/*     */   
/*     */   public int getMaxRows() {
/* 201 */     return this.m_maxRows;
/*     */   }
/*     */   
/*     */   public void setMaxRows(int maxRows) {
/* 205 */     this.m_maxRows = maxRows;
/* 206 */     if (this.m_comboBox != null) {
/* 207 */       this.m_comboBox.setMaxRows(maxRows);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 212 */     XComboBoxPlus combobox = (XComboBoxPlus)element;
/*     */     
/* 214 */     for (SelectionChangedListener scl : this.m_scl) {
/* 215 */       combobox.setOnSelectionChange(scl);
/*     */     }
/*     */     
/* 218 */     combobox.setSelectedValue(this.m_selectedValue);
/* 219 */     combobox.setMaxRows(this.m_maxRows);
/*     */     
/* 221 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 228 */     XComboBoxPlus combobox = new XComboBoxPlus();
/* 229 */     copyElementData((IElement)combobox);
/* 230 */     return (IElement)combobox;
/*     */   }
/*     */   
/*     */   public static void applyComboBoxTheme(ComboBox comboBox, ThemeElement element) {
/* 234 */     if (comboBox == null || element == null) {
/*     */       return;
/*     */     }
/*     */     
/* 238 */     comboBox.getAppearance().removeAll();
/* 239 */     XComponent.applyThemeAttributes((Widget)comboBox, element.getAttributes());
/* 240 */     XSpacingAppearance.setAppearance((StandardWidget)comboBox, element);
/* 241 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 242 */     for (ThemeAppearance app : appearances) {
/* 243 */       if (app != null) {
/* 244 */         XDecoratorAppearance.setAppearance((StandardWidget)comboBox, app);
/* 245 */         if (app instanceof ThemeComboBoxAppearance)
/* 246 */           XComboBoxAppearance.setAppearance(comboBox, (ThemeComboBoxAppearance)app); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XComboBoxPlus.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */