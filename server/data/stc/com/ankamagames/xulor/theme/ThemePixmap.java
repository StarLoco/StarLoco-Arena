/*     */ package com.ankamagames.xulor.theme;
/*     */ 
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ import com.ankamagames.xulor.util.Pixmap;
/*     */ import com.ankamagames.xulor.util.ThemeTexture;
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
/*     */ public class ThemePixmap
/*     */   implements IThemeElement, IPositionable
/*     */ {
/*     */   public static final String TAG = "Pixmap";
/*  20 */   private Pixmap m_pixmap = new Pixmap();
/*     */   
/*     */ 
/*     */ 
/*     */   private Alignment m_position;
/*     */   
/*     */ 
/*     */ 
/*     */   public void add(IThemeElement elem) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public Pixmap getPixmap()
/*     */   {
/*  34 */     return this.m_pixmap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setHeight(int height)
/*     */   {
/*  42 */     this.m_pixmap.setHeight(height);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTexture(ThemeTexture texture)
/*     */   {
/*  50 */     this.m_pixmap.setTexture(texture);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setWidth(int width)
/*     */   {
/*  58 */     this.m_pixmap.setWidth(width);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setX(int x)
/*     */   {
/*  66 */     this.m_pixmap.setX(x);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setY(int y)
/*     */   {
/*  74 */     this.m_pixmap.setY(y);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Alignment getPosition()
/*     */   {
/*  81 */     return this.m_position;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPosition(Alignment pos)
/*     */   {
/*  88 */     this.m_position = pos;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPixmap(Pixmap pixmap)
/*     */   {
/*  95 */     this.m_pixmap = pixmap;
/*     */   }
/*     */   
/*     */   public IThemeElement cloneAppearance() {
/*  99 */     ThemePixmap pixmap = new ThemePixmap();
/*     */     
/* 101 */     pixmap.setPosition(this.m_position);
/* 102 */     pixmap.setPixmap(this.m_pixmap);
/*     */     
/* 104 */     return pixmap;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemePixmap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */