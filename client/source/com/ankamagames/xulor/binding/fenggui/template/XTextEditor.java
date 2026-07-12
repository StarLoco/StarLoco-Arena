/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XTextEditorAppearance;
/*     */ import com.ankamagames.xulor.event.FocusManager;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.ITextEditor;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeTextEditorAppearance;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.ObservableWidget;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.TextEditor;
/*     */ import org.fenggui.Widget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XTextEditor
/*     */   extends XObservableComponent
/*     */   implements ITextEditor
/*     */ {
/*     */   public static final String TAG = "TextEditor";
/*  28 */   private TextEditor m_textEditor = null;
/*     */   
/*  30 */   private String m_text = "";
/*     */   private boolean m_multiline = false;
/*     */   private boolean m_password = false;
/*  33 */   private String m_restrict = null;
/*  34 */   private Boolean m_unicodeRestrict = Boolean.valueOf(true);
/*  35 */   private Boolean m_selectOnFocus = Boolean.valueOf(false);
/*     */ 
/*     */   
/*     */   private int m_maxChars;
/*     */ 
/*     */   
/*     */   private boolean m_maxCharactersInit = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {
/*  46 */     System.out.println("<texteditor text=\"" + this.m_text + "\" multiline=\"" + this.m_multiline + "\" password=\"" + this.m_password + "\">"); byte b; int i; IElement[] arrayOfIElement;
/*  47 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  48 */       c.buildXML(); b++; }
/*     */     
/*  50 */     System.out.println("</texteditor>");
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
/*  62 */     if (this.m_textEditor == null) {
/*  63 */       this.m_textEditor = new TextEditor(this.m_multiline);
/*     */       
/*  65 */       applyAllAttributes();
/*     */       
/*  67 */       addObservableComponentListeners();
/*     */       
/*  69 */       if (this.m_parent != null) this.m_parent.addWidget((IElement)this);
/*     */       
/*  71 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_textEditor, (IElement)this);
/*     */     }  byte b; int i;
/*     */     IElement[] arrayOfIElement;
/*  74 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  75 */       c.buildGUI();
/*     */       b++; }
/*     */     
/*  78 */     applyTheme();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {
/*  87 */     if (this.m_textEditor != null) {
/*  88 */       applyComponentAttributes();
/*  89 */       applyObservableComponentAttributes();
/*     */       
/*  91 */       this.m_textEditor.setText(this.m_text);
/*  92 */       this.m_textEditor.setMultiline(this.m_multiline);
/*  93 */       this.m_textEditor.setPasswordField(this.m_password);
/*  94 */       if (this.m_maxCharactersInit) this.m_textEditor.setMaxCharacters(this.m_maxChars); 
/*  95 */       if (this.m_restrict != null) this.m_textEditor.setRestrict(this.m_restrict); 
/*  96 */       if (this.m_unicodeRestrict != null) this.m_textEditor.setUnicodeRestrict(this.m_unicodeRestrict.booleanValue()); 
/*  97 */       if (this.m_selectOnFocus != null) this.m_textEditor.setSelectOnFocus(this.m_selectOnFocus.booleanValue());
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyTheme() {
/* 105 */     if (this.m_themeNeedToBeApplied) {
/* 106 */       this.m_themeNeedToBeApplied = false;
/* 107 */       applyTextEditorTheme(this.m_textEditor, this.m_themeElement);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMultiline(boolean m) {
/* 117 */     this.m_multiline = m;
/* 118 */     if (this.m_textEditor != null) {
/* 119 */       this.m_textEditor.setMultiline(this.m_multiline);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxChars() {
/* 127 */     return this.m_maxChars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxChars(int maxCharacters) {
/* 134 */     this.m_maxCharactersInit = true;
/* 135 */     this.m_maxChars = maxCharacters;
/* 136 */     if (this.m_textEditor != null) {
/* 137 */       this.m_textEditor.setMaxCharacters(maxCharacters);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRestrict() {
/* 145 */     return this.m_restrict;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRestrict(String validCharacters) {
/* 152 */     this.m_restrict = validCharacters;
/* 153 */     if (this.m_textEditor != null) {
/* 154 */       this.m_textEditor.setRestrict(validCharacters);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean getUnicodeRestrict() {
/* 162 */     if (this.m_unicodeRestrict != null) {
/* 163 */       return this.m_unicodeRestrict;
/*     */     }
/* 165 */     return Boolean.valueOf(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUnicodeRestrict(Boolean unicodeRestrict) {
/* 173 */     this.m_unicodeRestrict = unicodeRestrict;
/* 174 */     if (this.m_textEditor != null) {
/* 175 */       this.m_textEditor.setUnicodeRestrict(unicodeRestrict.booleanValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getSelectOnFocus() {
/* 184 */     if (this.m_selectOnFocus != null) {
/* 185 */       return this.m_selectOnFocus.booleanValue();
/*     */     }
/* 187 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectOnFocus(boolean selectOnFocus) {
/* 196 */     this.m_selectOnFocus = Boolean.valueOf(selectOnFocus);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPassword(boolean p) {
/* 205 */     this.m_password = p;
/* 206 */     if (this.m_textEditor != null) {
/* 207 */       this.m_textEditor.setPasswordField(this.m_password);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFocused(boolean f) {
/* 217 */     if (f) {
/* 218 */       FocusManager.getInstance().gainFocus((IElement)this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void appendText(String text) {
/* 223 */     if (this.m_textEditor != null) {
/* 224 */       this.m_text = this.m_textEditor.getText();
/*     */     }
/* 226 */     this.m_text = (this.m_text == null) ? text : (String.valueOf(this.m_text) + text);
/* 227 */     if (this.m_textEditor != null) {
/* 228 */       this.m_textEditor.setText(this.m_text);
/*     */     }
/*     */   }
/*     */   
/*     */   public void prependText(String text) {
/* 233 */     if (this.m_textEditor != null) {
/* 234 */       this.m_text = this.m_textEditor.getText();
/*     */     }
/* 236 */     this.m_text = (this.m_text == null) ? text : (String.valueOf(text) + this.m_text);
/* 237 */     if (this.m_textEditor != null) {
/* 238 */       this.m_textEditor.setText(this.m_text);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/* 248 */     this.m_text = text;
/* 249 */     if (this.m_textEditor != null) {
/* 250 */       this.m_textEditor.setText(this.m_text);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 260 */     if (this.m_textEditor != null) {
/* 261 */       return this.m_textEditor.getText();
/*     */     }
/* 263 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Widget getWidget() {
/* 271 */     return (Widget)this.m_textEditor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 279 */     XTextEditor elem = (XTextEditor)element;
/* 280 */     elem.m_multiline = this.m_multiline;
/* 281 */     elem.m_password = this.m_password;
/* 282 */     elem.m_text = this.m_text;
/* 283 */     elem.setRestrict(this.m_restrict);
/* 284 */     if (this.m_unicodeRestrict != null) elem.setUnicodeRestrict(this.m_unicodeRestrict); 
/* 285 */     if (this.m_selectOnFocus != null) elem.setSelectOnFocus(this.m_selectOnFocus.booleanValue()); 
/* 286 */     if (this.m_maxCharactersInit) elem.setMaxChars(this.m_maxChars); 
/* 287 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 294 */     XTextEditor elem = new XTextEditor();
/* 295 */     copyElementData((IElement)elem);
/* 296 */     return (IElement)elem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 304 */     return "TextEditor";
/*     */   }
/*     */   
/*     */   public static void applyTextEditorTheme(TextEditor textEditor, ThemeElement element) {
/* 308 */     if (textEditor == null || element == null) {
/*     */       return;
/*     */     }
/*     */     
/* 312 */     textEditor.getAppearance().removeAll();
/* 313 */     XComponent.applyThemeAttributes((Widget)textEditor, element.getAttributes());
/* 314 */     XSpacingAppearance.setAppearance((StandardWidget)textEditor, element);
/* 315 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 316 */     for (ThemeAppearance app : appearances) {
/* 317 */       if (app != null) {
/* 318 */         XDecoratorAppearance.setAppearance((StandardWidget)textEditor, app);
/* 319 */         if (app instanceof ThemeTextEditorAppearance) {
/* 320 */           XTextEditorAppearance.setAppearance((StandardWidget)textEditor, (ThemeTextEditorAppearance)app);
/*     */         }
/*     */       } 
/*     */     } 
/* 324 */     XObservableComponent.setAppearance((ObservableWidget)textEditor);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XTextEditor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */