/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XComponent;
/*     */ import com.ankamagames.xulor.binding.fenggui.util.AlignmentSwitch;
/*     */ import com.ankamagames.xulor.binding.fenggui.util.FontSwitch;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeLabelAppearance;
/*     */ import org.fenggui.IAppearance;
/*     */ import org.fenggui.ILabel;
/*     */ import org.fenggui.LabelAppearance;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.switches.SetPixmapSwitch;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XLabelAppearance
/*     */   extends XDecoratorAppearance
/*     */ {
/*  37 */   private LabelAppearance m_labelAppearance = null;
/*     */   
/*  39 */   private int m_gap = 0;
/*  40 */   private boolean m_gapInit = false;
/*  41 */   private com.ankamagames.xulor.util.Color m_textColor = null;
/*  42 */   private com.ankamagames.xulor.util.Font m_font = null;
/*  43 */   private com.ankamagames.xulor.util.Alignment m_alignment = null;
/*  44 */   private com.ankamagames.xulor.util.Pixmap m_pixmap = null;
/*     */   
/*     */ 
/*     */   public static final String TAG = "LabelAppearance";
/*     */   
/*     */   public static final String SHORT_TAG = "LabelApp";
/*     */   
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/*  54 */     if ((this.m_parent instanceof XComponent)) {
/*  55 */       ThemeElement element = ((XComponent)this.m_parent).getThemeElement();
/*  56 */       if (element == null) {
/*  57 */         return;
/*     */       }
/*  59 */       ThemeAppearance theme = element.getThemeAppearance(this.m_state);
/*     */       
/*  61 */       if (theme == null) {
/*  62 */         theme = new ThemeLabelAppearance();
/*  63 */         theme.setState(this.m_state);
/*  64 */         element.addThemeAppearance(theme);
/*     */       }
/*     */       
/*  67 */       applySpacingAttributes();
/*  68 */       applyDecoratorAttributes();
/*     */       
/*     */ 
/*  71 */       if ((theme instanceof ThemeLabelAppearance)) {
/*  72 */         ThemeLabelAppearance app = (ThemeLabelAppearance)theme;
/*  73 */         if (this.m_alignment != null) app.setAlignment(this.m_alignment);
/*  74 */         if (this.m_font != null) app.setFont(this.m_font);
/*  75 */         if (this.m_gapInit) app.setGap(this.m_gap);
/*  76 */         if (this.m_textColor != null) app.setTextColor(this.m_textColor);
/*  77 */         if ((this.m_pixmap != null) && 
/*  78 */           ((this.m_parent.getEncapsulatedObject() instanceof ILabel))) {
/*  79 */           ((ILabel)this.m_parent.getEncapsulatedObject()).setPixmap(FengguiConstant.toFengguiPixmap(this.m_pixmap));
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*  84 */       ((IComponent)this.m_parent).applyTheme();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/* 104 */     for (IElement c : this.m_children) {
/* 105 */       c.buildGUI();
/*     */     }
/*     */     
/* 108 */     applyAllAttributes();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getGap()
/*     */   {
/* 124 */     return this.m_gap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGap(int gap)
/*     */   {
/* 132 */     this.m_gapInit = true;
/* 133 */     this.m_gap = gap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public com.ankamagames.xulor.util.Color getTextColor()
/*     */   {
/* 141 */     return this.m_textColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTextColor(com.ankamagames.xulor.util.Color textColor)
/*     */   {
/* 149 */     this.m_textColor = textColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public com.ankamagames.xulor.util.Alignment getAlignment()
/*     */   {
/* 157 */     return this.m_alignment;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAlignment(com.ankamagames.xulor.util.Alignment alignment)
/*     */   {
/* 165 */     this.m_alignment = alignment;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public com.ankamagames.xulor.util.Pixmap getPixmap()
/*     */   {
/* 172 */     return this.m_pixmap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPixmap(com.ankamagames.xulor.util.Pixmap pixmap)
/*     */   {
/* 179 */     this.m_pixmap = pixmap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IAppearance getAppearance()
/*     */   {
/* 187 */     return this.m_labelAppearance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 195 */     return "LabelAppearance";
/*     */   }
/*     */   
/*     */   public static void setAppearance(StandardWidget widget, ThemeLabelAppearance theme) {
/* 199 */     if (!(widget instanceof ILabel)) {
/* 200 */       return;
/*     */     }
/* 202 */     LabelAppearance app = (LabelAppearance)widget.getAppearance();
/* 203 */     org.fenggui.layout.Alignment alignment = FengguiConstant.toFengguiAlignment(theme.getAlignment());
/* 204 */     if (alignment != null) {
/* 205 */       app.add(new AlignmentSwitch(theme.getState(), alignment));
/*     */     }
/* 207 */     app.setGap(theme.getGap());
/* 208 */     org.fenggui.util.Color textColor = FengguiConstant.toFengguiColor(theme.getTextColor());
/* 209 */     if (textColor != null) {
/* 210 */       app.add(new SetTextColorSwitch(theme.getState(), textColor));
/*     */     }
/* 212 */     org.fenggui.render.Font font = FengguiConstant.toFengguiFont(theme.getFont());
/* 213 */     if (font != null) {
/* 214 */       app.add(new FontSwitch(theme.getState(), font));
/*     */     }
/*     */     
/* 217 */     org.fenggui.render.Pixmap pixmap = FengguiConstant.toFengguiPixmap(theme.getPixmap());
/* 218 */     if (pixmap != null) {
/* 219 */       app.add(new SetPixmapSwitch(theme.getState(), pixmap));
/*     */     }
/*     */     
/* 222 */     if (theme.getState().equals("default")) {
/* 223 */       app.setEnabled("default", true);
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
/*     */   protected void copyElementData(IElement element)
/*     */   {
/* 236 */     XLabelAppearance elem = (XLabelAppearance)element;
/* 237 */     elem.setAlignment(this.m_alignment);
/* 238 */     elem.setGap(this.m_gap);
/* 239 */     elem.setTextColor(this.m_textColor);
/* 240 */     elem.m_font = this.m_font;
/* 241 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 248 */     XLabelAppearance elem = new XLabelAppearance();
/* 249 */     copyElementData(elem);
/* 250 */     return elem;
/*     */   }
/*     */   
/*     */   protected void copyThemeAppearanceAttributes(ThemeLabelAppearance app) {
/* 254 */     super.copyThemeAppearanceAttributes(app);
/* 255 */     app.setAlignment(this.m_alignment);
/* 256 */     app.setFont(this.m_font);
/* 257 */     app.setGap(this.m_gap);
/* 258 */     if (this.m_pixmap != null) app.setPixmap(this.m_pixmap.clone());
/* 259 */     app.setTextColor(this.m_textColor);
/*     */   }
/*     */   
/*     */   public ThemeAppearance toThemeAppearance() {
/* 263 */     ThemeLabelAppearance app = new ThemeLabelAppearance();
/* 264 */     copyThemeAppearanceAttributes(app);
/* 265 */     return app;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XLabelAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */