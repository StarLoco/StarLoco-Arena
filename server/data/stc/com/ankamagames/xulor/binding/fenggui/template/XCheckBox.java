/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XLabelAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.event.listener.SelectionChangedListener;
/*     */ import com.ankamagames.xulor.property.Property;
/*     */ import com.ankamagames.xulor.template.ICheckBox;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.ITogglable;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeLabelAppearance;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.CheckBox;
/*     */ import org.fenggui.LabelAppearance;
/*     */ import org.fenggui.Widget;
/*     */ import org.fenggui.event.ISelectionChangedListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XCheckBox
/*     */   extends XObservableLabelComponent
/*     */   implements ICheckBox, ITogglable
/*     */ {
/*     */   public static final String TAG = "Checkbox";
/*  33 */   private CheckBox m_checkbox = null;
/*     */   
/*  35 */   private boolean m_selected = false;
/*  36 */   private boolean m_selectedInit = false;
/*  37 */   private String m_value = null;
/*     */   
/*  39 */   private Property m_selectedProperty = null;
/*  40 */   private Property m_valueProperty = null;
/*     */   
/*  42 */   private ArrayList<SelectionChangedListener> m_scl = new ArrayList();
/*     */   private ISelectionChangedListener m_selectionChangedListener;
/*     */   private XCheckBox THIS;
/*     */   
/*     */   public XCheckBox()
/*     */   {
/*  48 */     this.THIS = this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML()
/*     */   {
/*  57 */     IElement[] components = getChildren();
/*  58 */     System.out.println("<checkbox text=\"" + this.m_text + "\"selected=\"" + this.m_selected + "\">");
/*  59 */     IElement[] arrayOfIElement1; int j = (arrayOfIElement1 = components).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement1[i];
/*  60 */       c.buildXML();
/*     */     }
/*  62 */     System.out.println("</checkbox>");
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
/*  73 */     if (this.m_checkbox == null) {
/*  74 */       this.m_checkbox = new CheckBox(this.m_text);
/*     */       
/*     */ 
/*  77 */       applyAllAttributes();
/*     */       
/*  79 */       addObservableComponentListeners();
/*     */       
/*  81 */       this.m_selectionChangedListener = new ISelectionChangedListener() {
/*     */         public void selectionChanged(org.fenggui.event.SelectionChangedEvent selectionChangedEvent) {
/*  83 */           com.ankamagames.xulor.event.SelectionChangedEvent event = new com.ankamagames.xulor.event.SelectionChangedEvent(XCheckBox.this.THIS, XCheckBox.this.THIS, selectionChangedEvent.isSelected(), null);
/*  84 */           for (SelectionChangedListener l : XCheckBox.this.m_scl)
/*  85 */             l.run(event);
/*     */         }
/*  87 */       };
/*  88 */       this.m_checkbox.addSelectionChangedListener(this.m_selectionChangedListener);
/*     */       
/*     */ 
/*  91 */       if (this.m_parent != null) this.m_parent.addWidget(this);
/*  92 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_checkbox, this);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  95 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  96 */       c.buildGUI();
/*     */     }
/*     */     
/*  99 */     applyTheme();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/* 108 */     if (this.m_checkbox == null)
/* 109 */       return;
/* 110 */     if (this.m_selectedInit)
/* 111 */       this.m_checkbox.setSelected(this.m_selected);
/* 112 */     if (this.m_value != null)
/* 113 */       this.m_checkbox.setValue(this.m_value);
/* 114 */     applyComponentAttributes();
/* 115 */     applyObservableComponentAttributes();
/* 116 */     applyObservableLabelComponentAttributes();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyTheme()
/*     */   {
/* 124 */     if (this.m_themeNeedToBeApplied) {
/* 125 */       this.m_themeNeedToBeApplied = false;
/* 126 */       applyCheckBoxTheme(this.m_checkbox, this.m_themeElement);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ITogglable setSelected(boolean selected)
/*     */   {
/* 136 */     this.m_selected = selected;
/* 137 */     this.m_selectedInit = true;
/* 138 */     if (this.m_checkbox != null) {
/* 139 */       this.m_checkbox.setSelected(selected);
/*     */     }
/* 141 */     if (this.m_selectedProperty != null) {
/* 142 */       this.m_selectedProperty.setValue(Boolean.valueOf(selected));
/*     */     }
/* 144 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getSelected()
/*     */   {
/* 153 */     if (this.m_checkbox != null) {
/* 154 */       return this.m_checkbox.isSelected();
/*     */     }
/* 156 */     return this.m_selected;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValue(String value)
/*     */   {
/* 165 */     this.m_value = value;
/* 166 */     if (this.m_checkbox != null) {
/* 167 */       this.m_checkbox.setValue(this.m_value);
/*     */     }
/* 169 */     if (this.m_valueProperty != null) {
/* 170 */       this.m_valueProperty.setValue(value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getValue()
/*     */   {
/* 180 */     if (this.m_checkbox != null) {
/* 181 */       return (String)this.m_checkbox.getValue();
/*     */     }
/*     */     
/* 184 */     return this.m_value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSelectedProperty(Property selectedProperty)
/*     */   {
/* 194 */     this.m_selectedProperty = selectedProperty;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValueProperty(Property valueProperty)
/*     */   {
/* 203 */     this.m_valueProperty = valueProperty;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyElementData(IElement element)
/*     */   {
/* 211 */     XCheckBox elem = (XCheckBox)element;
/* 212 */     elem.setSelected(this.m_selected);
/* 213 */     elem.setValue(this.m_value);
/* 214 */     for (SelectionChangedListener scl : this.m_scl) {
/* 215 */       elem.setOnSelectionChange(scl);
/*     */     }
/* 217 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 224 */     XCheckBox elem = new XCheckBox();
/* 225 */     copyElementData(elem);
/* 226 */     return elem;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Widget getWidget()
/*     */   {
/* 234 */     return this.m_checkbox;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOnSelectionChange(SelectionChangedListener selectionChangedListener)
/*     */   {
/* 242 */     this.m_scl.add(selectionChangedListener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 250 */     return "Checkbox";
/*     */   }
/*     */   
/*     */   public static void applyCheckBoxTheme(CheckBox checkbox, ThemeElement element) {
/* 254 */     if ((checkbox == null) || (element == null)) {
/* 255 */       return;
/*     */     }
/*     */     
/* 258 */     checkbox.getAppearance().removeAll();
/* 259 */     XComponent.applyThemeAttributes(checkbox, element.getAttributes());
/* 260 */     XSpacingAppearance.setAppearance(checkbox, element);
/* 261 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 262 */     for (ThemeAppearance app : appearances) {
/* 263 */       if (app != null) {
/* 264 */         XDecoratorAppearance.setAppearance(checkbox, app);
/* 265 */         if ((app instanceof ThemeLabelAppearance)) {
/* 266 */           XLabelAppearance.setAppearance(checkbox, (ThemeLabelAppearance)app);
/*     */         }
/*     */       }
/*     */     }
/* 270 */     checkbox.setSelected(checkbox.isSelected());
/* 271 */     XObservableComponent.setAppearance(checkbox);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XCheckBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */