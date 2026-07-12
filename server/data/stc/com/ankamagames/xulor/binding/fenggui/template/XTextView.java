/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.TextView;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XTextViewAppearance;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.ITextView;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeTextViewAppearance;
/*     */ import com.ankamagames.xulor.util.Font;
/*     */ import com.ankamagames.xulor.util.StyledTextParser;
/*     */ import com.ankamagames.xulor.util.StyledTextParserHandler;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.Widget;
/*     */ import org.fenggui.text.TextStyle;
/*     */ import org.fenggui.text.TextView.TextViewAppearance;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XTextView
/*     */   extends XObservableComponent
/*     */   implements ITextView
/*     */ {
/*     */   public static final String TAG = "TextView";
/*  34 */   private StyledTextParserHandler m_styledTextParserHandler = new StyledTextParserHandler() {
/*  35 */     private Font m_defaultFont = new Font();
/*     */     
/*     */     public void setFont(Font font) {
/*  38 */       this.m_defaultFont = font;
/*     */     }
/*     */     
/*     */     public Font getFont() {
/*  42 */       return this.m_defaultFont;
/*     */     }
/*     */     
/*     */     public void append(String text, Font font, java.awt.Color color) {
/*  46 */       if (XTextView.this.m_textView != null) {
/*  47 */         if (color != null) {
/*  48 */           TextStyle textStyle = new TextStyle(FengguiConstant.toFengguiFont(font), new org.fenggui.util.Color(color.getRed(), color.getGreen(), color.getBlue()));
/*  49 */           XTextView.this.m_textView.appendText(text, textStyle);
/*     */         } else {
/*  51 */           TextStyle textStyle = new TextStyle(FengguiConstant.toFengguiFont(font), XTextView.this.m_textView.getDefaulStyle().getColor());
/*  52 */           XTextView.this.m_textView.appendText(text, textStyle);
/*     */         }
/*     */       }
/*     */     }
/*     */   };
/*     */   
/*  58 */   protected TextView m_textView = null;
/*  59 */   private Integer m_minWidth = null;
/*  60 */   private String m_text = "";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML()
/*     */   {
/*  68 */     System.out.println("<textview text=\"" + this.m_text + "\">");
/*  69 */     IElement[] arrayOfIElement; int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  70 */       c.buildXML();
/*     */     }
/*  72 */     System.out.println("</textview>");
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
/*  84 */     if (this.m_textView == null) {
/*  85 */       this.m_textView = new TextView();
/*  86 */       this.m_textView.setTraversable(true);
/*     */       
/*  88 */       applyAllAttributes();
/*     */       
/*  90 */       addObservableComponentListeners();
/*     */       
/*  92 */       if (this.m_parent != null) { this.m_parent.addWidget(this);
/*     */       }
/*  94 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_textView, this);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  97 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  98 */       c.buildGUI();
/*     */     }
/*     */     
/* 101 */     applyTheme();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/* 110 */     if (this.m_textView != null) {
/* 111 */       applyComponentAttributes();
/* 112 */       applyObservableComponentAttributes();
/* 113 */       this.m_textView.setText("");
/* 114 */       if (this.m_minWidth != null) { this.m_textView.setMinWidth(this.m_minWidth.intValue());
/*     */       }
/* 116 */       if (!this.m_themeNeedToBeApplied) StyledTextParser.parse(this.m_text, this.m_styledTextParserHandler);
/*     */     }
/*     */   }
/*     */   
/*     */   public void applyTheme() {
/* 121 */     if (this.m_themeNeedToBeApplied) {
/* 122 */       this.m_themeNeedToBeApplied = false;
/* 123 */       applyTextViewTheme(this.m_textView, this.m_themeElement);
/* 124 */       if (this.m_textView != null)
/*     */       {
/* 126 */         this.m_textView.setText("");
/* 127 */         StyledTextParser.parse(this.m_text, this.m_styledTextParserHandler);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void setDefaultFont(Font font) {
/* 133 */     this.m_styledTextParserHandler.setFont(font);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setText(String text)
/*     */   {
/* 142 */     this.m_text = (text == null ? "" : text);
/* 143 */     if ((this.m_textView != null) && (!this.m_themeNeedToBeApplied))
/*     */     {
/* 145 */       this.m_textView.setText("");
/* 146 */       StyledTextParser.parse(this.m_text, this.m_styledTextParserHandler);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getMinWidth()
/*     */   {
/* 154 */     if (this.m_minWidth != null) {
/* 155 */       return this.m_minWidth.intValue();
/*     */     }
/* 157 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMinWidth(int minWidth)
/*     */   {
/* 165 */     this.m_minWidth = Integer.valueOf(minWidth);
/* 166 */     if (this.m_textView != null) {
/* 167 */       this.m_textView.setMinWidth(minWidth);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void appendText(String text)
/*     */   {
/* 177 */     if (this.m_textView != null) {
/* 178 */       StyledTextParser.parse(text == null ? "" : text, this.m_styledTextParserHandler);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyElementData(IElement element)
/*     */   {
/* 187 */     XTextView elem = (XTextView)element;
/* 188 */     elem.m_text = this.m_text;
/* 189 */     if (this.m_minWidth != null) elem.setMinWidth(this.m_minWidth.intValue());
/* 190 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 197 */     XTextView elem = new XTextView();
/* 198 */     copyElementData(elem);
/* 199 */     return elem;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Widget getWidget()
/*     */   {
/* 209 */     return this.m_textView;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 217 */     return "TextView";
/*     */   }
/*     */   
/*     */   public static void applyTextViewTheme(TextView textView, ThemeElement element) {
/* 221 */     if ((textView == null) || (element == null)) {
/* 222 */       return;
/*     */     }
/*     */     
/* 225 */     XTextView xtv = (XTextView)Xulor.getInstance().getEnvironment().getElementByWidget(textView);
/*     */     
/* 227 */     textView.getAppearance().removeAll();
/* 228 */     XComponent.applyThemeAttributes(textView, element.getAttributes());
/* 229 */     XSpacingAppearance.setAppearance(textView, element);
/* 230 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 231 */     for (ThemeAppearance app : appearances) {
/* 232 */       if (app != null) {
/* 233 */         XDecoratorAppearance.setAppearance(textView, app);
/* 234 */         if ((app instanceof ThemeTextViewAppearance)) {
/* 235 */           if (xtv != null) {
/* 236 */             Font font = ((ThemeTextViewAppearance)app).getFont();
/* 237 */             if (font == null) {
/* 238 */               xtv.setDefaultFont(new Font());
/*     */             } else {
/* 240 */               xtv.setDefaultFont(font);
/*     */             }
/*     */           }
/* 243 */           XTextViewAppearance.setAppearance(textView, (ThemeTextViewAppearance)app);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 248 */     XObservableComponent.setAppearance(textView);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XTextView.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */