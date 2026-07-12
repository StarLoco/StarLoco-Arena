/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XComponent;
/*     */ import com.ankamagames.xulor.binding.fenggui.util.CursorColorSwitch;
/*     */ import com.ankamagames.xulor.binding.fenggui.util.FontSwitch;
/*     */ import com.ankamagames.xulor.binding.fenggui.util.SelectionColorSwitch;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeTextEditorAppearance;
/*     */ import org.fenggui.IAppearance;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.TextEditor;
/*     */ import org.fenggui.TextEditorAppearance;
/*     */ import org.fenggui.switches.SetTextColorSwitch;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XTextEditorAppearance
/*     */   extends XDecoratorAppearance
/*     */ {
/*     */   public static final String TAG = "TextEditorAppearance";
/*     */   public static final String SHORT_TAG = "TEA";
/*  34 */   private TextEditorAppearance m_textEditorAppearance = null;
/*     */   
/*     */   private com.ankamagames.xulor.util.Color m_textColor;
/*     */   
/*     */   private com.ankamagames.xulor.util.Color m_selectionColor;
/*     */   
/*     */   private com.ankamagames.xulor.util.Color m_cursorColor;
/*     */   
/*     */   private com.ankamagames.xulor.util.Font m_font;
/*     */   
/*     */   public IAppearance getAppearance()
/*     */   {
/*  46 */     return this.m_textEditorAppearance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/*  54 */     ThemeElement element = ((XComponent)this.m_parent).getThemeElement();
/*  55 */     if (element == null) {
/*  56 */       return;
/*     */     }
/*  58 */     ThemeAppearance theme = element.getThemeAppearance(this.m_state);
/*     */     
/*  60 */     if (theme == null) {
/*  61 */       theme = new ThemeTextEditorAppearance();
/*  62 */       theme.setState(this.m_state);
/*  63 */       element.addThemeAppearance(theme);
/*     */     }
/*     */     
/*  66 */     applySpacingAttributes();
/*  67 */     applyDecoratorAttributes();
/*     */     
/*  69 */     if ((theme instanceof ThemeTextEditorAppearance)) {
/*  70 */       ThemeTextEditorAppearance app = (ThemeTextEditorAppearance)theme;
/*  71 */       app.setFont(this.m_font);
/*  72 */       app.setSelectionColor(this.m_selectionColor);
/*  73 */       app.setTextColor(this.m_textColor);
/*  74 */       app.setCursorColor(this.m_cursorColor);
/*     */     }
/*     */     
/*  77 */     ((IComponent)this.m_parent).applyTheme();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/*  94 */     for (IElement c : this.m_children) {
/*  95 */       c.buildGUI();
/*     */     }
/*     */     
/*  98 */     applyAllAttributes();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public com.ankamagames.xulor.util.Font getFont()
/*     */   {
/* 111 */     return this.m_font;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setFont(com.ankamagames.xulor.util.Font font)
/*     */   {
/* 118 */     this.m_font = font;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public com.ankamagames.xulor.util.Color getSelectionColor()
/*     */   {
/* 125 */     return this.m_selectionColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setSelectionColor(com.ankamagames.xulor.util.Color selectionColor)
/*     */   {
/* 132 */     this.m_selectionColor = selectionColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public com.ankamagames.xulor.util.Color getTextColor()
/*     */   {
/* 139 */     return this.m_textColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setTextColor(com.ankamagames.xulor.util.Color textColor)
/*     */   {
/* 146 */     this.m_textColor = textColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public com.ankamagames.xulor.util.Color getCursorColor()
/*     */   {
/* 153 */     return this.m_cursorColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setCursorColor(com.ankamagames.xulor.util.Color cursorColor)
/*     */   {
/* 160 */     this.m_cursorColor = cursorColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 168 */     return "TextEditorAppearance";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyElementData(IElement element)
/*     */   {
/* 176 */     XTextEditorAppearance elem = (XTextEditorAppearance)element;
/* 177 */     elem.setTextColor(this.m_textColor);
/* 178 */     elem.setFont(this.m_font);
/* 179 */     elem.setSelectionColor(this.m_selectionColor);
/* 180 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 187 */     XTextEditorAppearance elem = new XTextEditorAppearance();
/* 188 */     copyElementData(elem);
/* 189 */     return elem;
/*     */   }
/*     */   
/*     */   public static void setAppearance(StandardWidget widget, ThemeTextEditorAppearance theme) {
/* 193 */     if (!(widget instanceof TextEditor)) {
/* 194 */       return;
/*     */     }
/* 196 */     TextEditorAppearance app = (TextEditorAppearance)widget.getAppearance();
/* 197 */     org.fenggui.util.Color textColor = FengguiConstant.toFengguiColor(theme.getTextColor());
/* 198 */     if (textColor != null) {
/* 199 */       app.add(new SetTextColorSwitch(theme.getState(), textColor));
/*     */     }
/*     */     
/* 202 */     org.fenggui.util.Color selectionColor = FengguiConstant.toFengguiColor(theme.getSelectionColor());
/* 203 */     if (selectionColor != null) {
/* 204 */       app.add(new SelectionColorSwitch(theme.getState(), selectionColor));
/*     */     }
/*     */     
/* 207 */     org.fenggui.util.Color cursorColor = FengguiConstant.toFengguiColor(theme.getCursorColor());
/* 208 */     if (cursorColor != null) {
/* 209 */       app.add(new CursorColorSwitch(theme.getState(), cursorColor));
/*     */     }
/*     */     
/* 212 */     org.fenggui.render.Font font = FengguiConstant.toFengguiFont(theme.getFont());
/* 213 */     if (font != null) {
/* 214 */       app.add(new FontSwitch(theme.getState(), font));
/*     */     }
/*     */     
/* 217 */     if (theme.getState().equals("default")) {
/* 218 */       app.setEnabled("default", true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyThemeAppearanceAttributes(ThemeTextEditorAppearance app)
/*     */   {
/* 227 */     super.copyThemeAppearanceAttributes(app);
/* 228 */     app.setSelectionColor(this.m_selectionColor);
/* 229 */     app.setFont(this.m_font);
/* 230 */     app.setTextColor(this.m_textColor);
/*     */   }
/*     */   
/*     */   public ThemeAppearance toThemeAppearance() {
/* 234 */     ThemeTextEditorAppearance app = new ThemeTextEditorAppearance();
/* 235 */     copyThemeAppearanceAttributes(app);
/* 236 */     return app;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XTextEditorAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */