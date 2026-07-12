/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.Label;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XLabelAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.ILabel;
/*     */ import com.ankamagames.xulor.template.IPixmapable;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeLabelAppearance;
/*     */ import com.ankamagames.xulor.util.Pixmap;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.IBasicContainer;
/*     */ import org.fenggui.Label;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.Widget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XLabel
/*     */   extends XComponent
/*     */   implements ILabel, IPixmapable
/*     */ {
/*     */   public static final String TAG = "Label";
/*  31 */   private Label m_label = null;
/*     */   
/*  33 */   protected String m_text = null;
/*  34 */   protected Pixmap m_pixmap = null;
/*  35 */   private int m_maxChar = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void displayNonBlockingAvailability() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {
/*  47 */     IElement[] components = getChildren();
/*  48 */     System.out.println("<label text=\"" + this.m_text + "\" image=\"" + this.m_pixmap + "\">"); byte b; int i; IElement[] arrayOfIElement1;
/*  49 */     for (i = (arrayOfIElement1 = components).length, b = 0; b < i; ) { IElement c = arrayOfIElement1[b];
/*  50 */       c.buildXML(); b++; }
/*     */     
/*  52 */     System.out.println("</label>");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {
/*  63 */     if (this.m_label == null) {
/*  64 */       this.m_label = new Label();
/*     */       
/*  66 */       if (this.m_parent != null) this.m_parent.addWidget((IElement)this);
/*     */       
/*  68 */       applyAllAttributes();
/*     */       
/*  70 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_label, (IElement)this);
/*     */     }  byte b; int i;
/*     */     IElement[] arrayOfIElement;
/*  73 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  74 */       c.buildGUI(); b++; }
/*     */     
/*  76 */     applyTheme();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {
/*  85 */     if (this.m_label == null) {
/*     */       return;
/*     */     }
/*  88 */     this.m_label.setText(limitedText());
/*  89 */     this.m_label.setPixmap(FengguiConstant.toFengguiPixmap(this.m_pixmap));
/*     */     
/*  91 */     applyComponentAttributes();
/*     */   }
/*     */   
/*     */   public void applyTheme() {
/*  95 */     if (this.m_themeNeedToBeApplied) {
/*  96 */       this.m_themeNeedToBeApplied = false;
/*  97 */       applyLabelTheme((Label)this.m_label, this.m_themeElement);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getPixmap() {
/* 105 */     return this.m_pixmap;
/*     */   }
/*     */   
/*     */   public String getText() {
/* 109 */     if (this.m_label != null) {
/* 110 */       return this.m_label.getText();
/*     */     }
/* 112 */     return this.m_text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String limitedText() {
/* 120 */     if (this.m_text != null) {
/* 121 */       String text = new String(this.m_text);
/* 122 */       if (this.m_maxChar != -1 && this.m_text.length() > this.m_maxChar + 2)
/* 123 */         text = String.valueOf(this.m_text.substring(0, this.m_maxChar)) + "..."; 
/* 124 */       return text;
/*     */     } 
/* 126 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/* 134 */     this.m_text = text;
/* 135 */     if (this.m_label != null) {
/* 136 */       this.m_label.setText(limitedText());
/* 137 */       IBasicContainer parent = this.m_label.getParent();
/* 138 */       if (parent != null) {
/* 139 */         parent.layout();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPixmap(Pixmap image) {
/* 150 */     if (this.m_label != null) {
/* 151 */       this.m_label.setPixmap(FengguiConstant.toFengguiPixmap(image));
/*     */     }
/* 153 */     this.m_pixmap = image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Widget getWidget() {
/* 161 */     return (Widget)this.m_label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 169 */     return "Label";
/*     */   }
/*     */   
/*     */   protected void copyElementData(XLabel label) {
/* 173 */     copyElementData((IElement)label);
/* 174 */     label.setText(getText());
/* 175 */     label.setMaxChar(getMaxChar());
/* 176 */     if (this.m_pixmap != null) label.setPixmap(getPixmap().clone());
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 183 */     XLabel elem = new XLabel();
/* 184 */     copyElementData(elem);
/* 185 */     return (IElement)elem;
/*     */   }
/*     */   
/*     */   public static void applyLabelTheme(Label label, ThemeElement element) {
/* 189 */     if (label == null || element == null) {
/*     */       return;
/*     */     }
/*     */     
/* 193 */     label.getAppearance().removeAll();
/* 194 */     XComponent.applyThemeAttributes((Widget)label, element.getAttributes());
/* 195 */     XSpacingAppearance.setAppearance((StandardWidget)label, element);
/* 196 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 197 */     for (ThemeAppearance app : appearances) {
/* 198 */       if (app != null) {
/* 199 */         XDecoratorAppearance.setAppearance((StandardWidget)label, app);
/* 200 */         if (app instanceof ThemeLabelAppearance) {
/* 201 */           XLabelAppearance.setAppearance((StandardWidget)label, (ThemeLabelAppearance)app);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxChar() {
/* 212 */     return this.m_maxChar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxChar(int maxChar) {
/* 220 */     this.m_maxChar = maxChar;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XLabel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */