/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeBorder;
/*     */ import com.ankamagames.xulor.theme.ThemeTitledBorder;
/*     */ import com.ankamagames.xulor.util.Color;
/*     */ import org.fenggui.border.Border;
/*     */ import org.fenggui.border.TitledBorder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XTitledBorder
/*     */   extends XBorder
/*     */ {
/*  21 */   private TitledBorder m_titledBorder = null;
/*     */   
/*     */   public static final String TAG = "TitledBorder";
/*     */   
/*  25 */   private Color m_color = Color.GRAY; private Color m_textColor = Color.BLACK;
/*  26 */   private com.ankamagames.xulor.util.Font m_font = null;
/*  27 */   private String m_title = null;
/*     */   
/*     */   public XTitledBorder() {
/*  30 */     this(Color.BLACK, new com.ankamagames.xulor.util.Font());
/*     */   }
/*     */   
/*     */   public XTitledBorder(String title, Color c) {
/*  34 */     this(c, new com.ankamagames.xulor.util.Font());
/*     */   }
/*     */   
/*     */   public XTitledBorder(Color c, com.ankamagames.xulor.util.Font font) {
/*  38 */     this.m_font = font;
/*  39 */     this.m_textColor = c;
/*  40 */     this.m_color = new Color();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/*  48 */     if (this.m_titledBorder != null) {
/*  49 */       if (this.m_color != null) this.m_titledBorder.setColor(FengguiConstant.toFengguiColor(this.m_color));
/*  50 */       if (this.m_textColor != null) { this.m_titledBorder.setTextColor(FengguiConstant.toFengguiColor(this.m_textColor));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/*  59 */     if (!(this.m_parent instanceof XDecoratorAppearance)) {
/*  60 */       return;
/*     */     }
/*  62 */     if (this.m_titledBorder == null) {
/*  63 */       this.m_titledBorder = new TitledBorder(FengguiConstant.toFengguiFont(this.m_font), this.m_title);
/*     */       
/*  65 */       applyAllAttributes();
/*     */       
/*  67 */       ((XDecoratorAppearance)this.m_parent).addBorder(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML()
/*     */   {
/*  78 */     IElement[] components = getChildren();
/*     */     IElement[] arrayOfIElement1;
/*  80 */     int j = (arrayOfIElement1 = components).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement1[i];
/*  81 */       c.buildXML();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Color getColor()
/*     */   {
/*  89 */     return this.m_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setColor(Color color)
/*     */   {
/*  96 */     this.m_color = color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Color getTextColor()
/*     */   {
/* 103 */     return this.m_textColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setTextColor(Color textColor)
/*     */   {
/* 110 */     this.m_textColor = textColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 117 */     return this.m_title;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 124 */     this.m_title = title;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Border getBorder()
/*     */   {
/* 132 */     return this.m_titledBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 140 */     return "TitledBorder";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyElementData(IElement element)
/*     */   {
/* 148 */     XTitledBorder elem = (XTitledBorder)element;
/* 149 */     elem.m_color = this.m_color;
/* 150 */     elem.m_title = this.m_title;
/* 151 */     elem.m_font = this.m_font;
/* 152 */     elem.m_textColor = this.m_textColor;
/* 153 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 160 */     XTitledBorder elem = new XTitledBorder();
/* 161 */     copyElementData(elem);
/* 162 */     return elem;
/*     */   }
/*     */   
/*     */   public static TitledBorder getTitledBorder(ThemeTitledBorder theme) {
/* 166 */     if (theme == null) {
/* 167 */       return null;
/*     */     }
/*     */     
/* 170 */     TitledBorder border = new TitledBorder(org.fenggui.render.Font.getDefaultFont(), 
/* 171 */       theme.getTitle(), 
/* 172 */       FengguiConstant.toFengguiColor(theme.getTextColor()));
/* 173 */     border.setColor(FengguiConstant.toFengguiColor(theme.getColor()));
/* 174 */     border.setEnabled(theme.isEnabled());
/* 175 */     return border;
/*     */   }
/*     */   
/*     */   public ThemeBorder toThemeBorder() {
/* 179 */     ThemeTitledBorder border = new ThemeTitledBorder();
/*     */     
/* 181 */     border.setAsBorderSpacing(this.m_asBorderSpacing);
/* 182 */     border.setColor(this.m_color);
/* 183 */     border.setTextColor(this.m_textColor);
/* 184 */     border.setFont(this.m_font);
/* 185 */     border.setSpacing(this.m_spacing);
/* 186 */     border.setEnabled(this.m_enabled);
/* 187 */     return border;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XTitledBorder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */