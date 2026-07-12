/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XLabelAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.util.Togglable;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.event.listener.SelectionChangedListener;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IRadioButton;
/*     */ import com.ankamagames.xulor.template.IRadioGroup;
/*     */ import com.ankamagames.xulor.template.ITogglable;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeLabelAppearance;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.LabelAppearance;
/*     */ import org.fenggui.RadioButton;
/*     */ import org.fenggui.ToggableGroup;
/*     */ import org.fenggui.Widget;
/*     */ import org.fenggui.event.ISelectionChangedListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XRadioButton
/*     */   extends XObservableLabelComponent
/*     */   implements IRadioButton, ITogglable
/*     */ {
/*     */   public static final String TAG = "RadioButton";
/*  35 */   private RadioButton m_radioButton = null;
/*     */   
/*  37 */   private String m_value = null;
/*  38 */   private String m_groupId = null;
/*  39 */   private boolean m_selected = false;
/*     */   
/*  41 */   private boolean m_selectedInit = false;
/*     */   
/*  43 */   private ArrayList<SelectionChangedListener> m_scl = new ArrayList();
/*     */   private ISelectionChangedListener m_selectionChangedListener;
/*     */   private XRadioButton THIS;
/*     */   
/*     */   public XRadioButton()
/*     */   {
/*  49 */     this.THIS = this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML()
/*     */   {
/*  58 */     System.out.println("<radiobutton text=\"" + this.m_text + "\" value=\"" + this.m_value + "\" radioId=\"" + this.m_groupId + "\">");
/*  59 */     IElement[] arrayOfIElement; int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  60 */       c.buildXML();
/*     */     }
/*  62 */     System.out.println("</radiobutton>");
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
/*  73 */     if (this.m_radioButton == null) {
/*  74 */       this.m_radioButton = new RadioButton();
/*     */       
/*  76 */       applyAllAttributes();
/*     */       
/*  78 */       addObservableComponentListeners();
/*     */       
/*  80 */       this.m_selectionChangedListener = new ISelectionChangedListener() {
/*     */         public void selectionChanged(org.fenggui.event.SelectionChangedEvent selectionChangedEvent) {
/*  82 */           com.ankamagames.xulor.event.SelectionChangedEvent event = new com.ankamagames.xulor.event.SelectionChangedEvent(XRadioButton.this.THIS, new Togglable(selectionChangedEvent.getToggableWidget()), selectionChangedEvent.isSelected(), null);
/*  83 */           for (SelectionChangedListener l : XRadioButton.this.m_scl)
/*  84 */             l.run(event);
/*     */         }
/*  86 */       };
/*  87 */       this.m_radioButton.addSelectionChangedListener(this.m_selectionChangedListener);
/*     */       
/*  89 */       if (this.m_parent != null) { this.m_parent.addWidget(this);
/*     */       }
/*  91 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_radioButton, this);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  94 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  95 */       c.buildGUI();
/*     */     }
/*     */     
/*  98 */     applyTheme();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/* 108 */     if (this.m_radioButton == null) {
/* 109 */       return;
/*     */     }
/* 111 */     if (this.m_value != null) {
/* 112 */       this.m_radioButton.setValue(this.m_value);
/*     */     }
/* 114 */     if ((this.m_groupId != null) && 
/* 115 */       (Xulor.getInstance().getEnvironment().radioGroupExists(this.m_groupId))) {
/* 116 */       Xulor.getInstance().getEnvironment().getRadioGroup(this.m_groupId).addRadioButton(this);
/*     */     }
/*     */     
/* 119 */     if (this.m_selectedInit)
/* 120 */       this.m_radioButton.setSelected(this.m_selected);
/* 121 */     applyComponentAttributes();
/* 122 */     applyObservableComponentAttributes();
/* 123 */     applyObservableLabelComponentAttributes();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyTheme()
/*     */   {
/* 131 */     if (this.m_themeNeedToBeApplied) {
/* 132 */       this.m_themeNeedToBeApplied = false;
/* 133 */       applyRadioButtonTheme(this.m_radioButton, this.m_themeElement);
/*     */     }
/*     */   }
/*     */   
/*     */   public ITogglable setSelected(boolean selected) {
/* 138 */     this.m_selected = selected;
/* 139 */     this.m_selectedInit = true;
/* 140 */     if (this.m_radioButton != null) {
/* 141 */       this.m_radioButton.setSelected(selected);
/*     */     }
/* 143 */     return this;
/*     */   }
/*     */   
/*     */   public void setValue(String value)
/*     */   {
/* 148 */     this.m_value = value;
/* 149 */     if (this.m_radioButton != null) {
/* 150 */       this.m_radioButton.setValue(value);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setGroupId(String groupId)
/*     */   {
/* 156 */     this.m_groupId = groupId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getGroupId()
/*     */   {
/* 168 */     return this.m_groupId;
/*     */   }
/*     */   
/*     */   private ToggableGroup getRadioGroup(String radioId) {
/* 172 */     if (Xulor.getInstance().getEnvironment().radioGroupExists(this.m_groupId)) {
/* 173 */       IRadioGroup rg = Xulor.getInstance().getEnvironment().getRadioGroup(radioId);
/* 174 */       return (ToggableGroup)rg.getEncapsulatedObject();
/*     */     }
/* 176 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   private ToggableGroup addRadioGroup(String radioId)
/*     */   {
/* 182 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Widget getWidget()
/*     */   {
/* 190 */     return this.m_radioButton;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 198 */     return "RadioButton";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyElementData(IElement element)
/*     */   {
/* 206 */     XRadioButton elem = (XRadioButton)element;
/* 207 */     elem.setGroupId(this.m_groupId);
/* 208 */     elem.setSelected(this.m_selected);
/* 209 */     elem.setValue(this.m_value);
/* 210 */     for (SelectionChangedListener scl : this.m_scl) {
/* 211 */       elem.setOnSelectionChange(scl);
/*     */     }
/* 213 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 220 */     XRadioButton elem = new XRadioButton();
/* 221 */     copyElementData(elem);
/* 222 */     return elem;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getValue()
/*     */   {
/* 229 */     return this.m_value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean getSelected()
/*     */   {
/* 236 */     if (this.m_radioButton != null) {
/* 237 */       return this.m_radioButton.isSelected();
/*     */     }
/* 239 */     return this.m_selected;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setOnSelectionChange(SelectionChangedListener selectionChangedListener)
/*     */   {
/* 246 */     this.m_scl.add(selectionChangedListener);
/*     */   }
/*     */   
/*     */   public static void applyRadioButtonTheme(RadioButton radioButton, ThemeElement element) {
/* 250 */     if ((radioButton == null) || (element == null)) {
/* 251 */       return;
/*     */     }
/*     */     
/* 254 */     radioButton.getAppearance().removeAll();
/* 255 */     XComponent.applyThemeAttributes(radioButton, element.getAttributes());
/* 256 */     XSpacingAppearance.setAppearance(radioButton, element);
/* 257 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 258 */     for (ThemeAppearance app : appearances) {
/* 259 */       if (app != null) {
/* 260 */         XDecoratorAppearance.setAppearance(radioButton, app);
/* 261 */         if ((app instanceof ThemeLabelAppearance)) {
/* 262 */           XLabelAppearance.setAppearance(radioButton, (ThemeLabelAppearance)app);
/*     */         }
/*     */       }
/*     */     }
/* 266 */     radioButton.setSelected(radioButton.isSelected());
/* 267 */     XObservableComponent.setAppearance(radioButton);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XRadioButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */