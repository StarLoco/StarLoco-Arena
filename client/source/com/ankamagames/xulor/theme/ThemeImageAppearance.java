/*     */ package com.ankamagames.xulor.theme;
/*     */ 
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ import com.ankamagames.xulor.util.Pixmap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThemeImageAppearance
/*     */   extends ThemeAppearance
/*     */   implements IThemeElement
/*     */ {
/*     */   public static final String TAG = "ImageAppearance";
/*  19 */   private Alignment m_alignment = Alignment.CENTER;
/*  20 */   private Pixmap m_pixmap = null;
/*     */   
/*     */   private boolean m_scaled = false;
/*     */   
/*     */   private boolean m_keepAspectRatio = true;
/*     */   
/*     */   private boolean m_scaledInit = false;
/*     */   
/*     */   private boolean m_keepAspectRatioInit = false;
/*     */   
/*     */   public Alignment getAlignment() {
/*  31 */     return this.m_alignment;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlignment(Alignment alignment) {
/*  37 */     this.m_alignment = alignment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getPixmap() {
/*  44 */     return this.m_pixmap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPixmap(Pixmap pixmap) {
/*  51 */     this.m_pixmap = pixmap;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isKeepAspectRatio() {
/*  57 */     return this.m_keepAspectRatio;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKeepAspectRatio(boolean keepAspectRatio) {
/*  63 */     this.m_keepAspectRatio = keepAspectRatio;
/*  64 */     this.m_keepAspectRatioInit = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isScaled() {
/*  70 */     return this.m_scaled;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScaled(boolean scaled) {
/*  76 */     this.m_scaled = scaled;
/*  77 */     this.m_scaledInit = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(IThemeElement elem) {
/*  84 */     if (elem instanceof ThemePixmap) {
/*  85 */       this.m_pixmap = ((ThemePixmap)elem).getPixmap();
/*     */     } else {
/*  87 */       super.add(elem);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void copyAttributes(ThemeImageAppearance app) {
/*  92 */     copyAttributes(app);
/*  93 */     app.setAlignment(this.m_alignment);
/*  94 */     if (this.m_pixmap != null) app.setPixmap(this.m_pixmap.clone()); 
/*  95 */     if (this.m_keepAspectRatioInit) app.setKeepAspectRatio(this.m_keepAspectRatio); 
/*  96 */     if (this.m_scaledInit) app.setScaled(this.m_scaled); 
/*     */   }
/*     */   
/*     */   public IThemeElement cloneAppearance() {
/* 100 */     ThemeImageAppearance app = new ThemeImageAppearance();
/*     */     
/* 102 */     copyAttributes(app);
/*     */     
/* 104 */     return app;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeImageAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */