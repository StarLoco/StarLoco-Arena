/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeBackground;
/*     */ import com.ankamagames.xulor.theme.ThemePixmapBackground;
/*     */ import com.ankamagames.xulor.util.Pixmap;
/*     */ import org.fenggui.background.Background;
/*     */ import org.fenggui.background.PixmapBackground;
/*     */ import org.fenggui.render.Pixmap;
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
/*     */ public class XPixmapBackground
/*     */   extends XBackground
/*     */ {
/*  24 */   private PixmapBackground m_pixmapBackground = null;
/*     */   
/*     */   public static final String TAG = "PixmapBackground";
/*     */   
/*     */   private Pixmap m_center;
/*     */   
/*     */   private Pixmap m_north;
/*     */   
/*     */   private Pixmap m_northWest;
/*     */   
/*     */   private Pixmap m_northEast;
/*     */   
/*     */   private Pixmap m_west;
/*     */   
/*     */   private Pixmap m_east;
/*     */   
/*     */   private Pixmap m_southWest;
/*     */   
/*     */   private Pixmap m_south;
/*     */   
/*     */   private Pixmap m_southEast;
/*     */   
/*     */   private boolean m_scale = false;
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {}
/*     */   
/*     */   public void buildGUI() {
/*  52 */     if (!(this.m_parent instanceof XDecoratorAppearance)) {
/*     */       return;
/*     */     }
/*  55 */     if (this.m_pixmapBackground == null) {
/*  56 */       this.m_pixmapBackground = new PixmapBackground(FengguiConstant.toFengguiPixmap(this.m_center), this.m_scale);
/*  57 */       ((XDecoratorAppearance)this.m_parent).addBackground(this);
/*     */     } 
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
/*     */   public boolean isScale() {
/*  73 */     return this.m_scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScale(boolean scale) {
/*  80 */     this.m_scale = scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getCenter() {
/*  87 */     return this.m_center;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCenter(Pixmap center) {
/*  94 */     this.m_center = center;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getEast() {
/* 101 */     return this.m_east;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEast(Pixmap east) {
/* 108 */     this.m_east = east;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorth() {
/* 115 */     return this.m_north;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorth(Pixmap north) {
/* 122 */     this.m_north = north;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorthEast() {
/* 129 */     return this.m_northEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorthEast(Pixmap northEast) {
/* 136 */     this.m_northEast = northEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorthWest() {
/* 143 */     return this.m_northWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorthWest(Pixmap northWest) {
/* 150 */     this.m_northWest = northWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PixmapBackground getPixmapBackground() {
/* 157 */     return this.m_pixmapBackground;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPixmapBackground(PixmapBackground pixmapBackground) {
/* 164 */     this.m_pixmapBackground = pixmapBackground;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouth() {
/* 171 */     return this.m_south;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouth(Pixmap south) {
/* 178 */     this.m_south = south;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouthEast() {
/* 185 */     return this.m_southEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouthEast(Pixmap southEast) {
/* 192 */     this.m_southEast = southEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouthWest() {
/* 199 */     return this.m_southWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouthWest(Pixmap southWest) {
/* 206 */     this.m_southWest = southWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getWest() {
/* 213 */     return this.m_west;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWest(Pixmap west) {
/* 220 */     this.m_west = west;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Background getBackground() {
/* 228 */     return (Background)this.m_pixmapBackground;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 236 */     return "PixmapBackground";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 244 */     XPixmapBackground elem = (XPixmapBackground)element;
/* 245 */     elem.setCenter(this.m_center);
/* 246 */     elem.setNorthWest(this.m_northWest);
/* 247 */     elem.setNorth(this.m_north);
/* 248 */     elem.setNorthEast(this.m_northEast);
/* 249 */     elem.setWest(this.m_west);
/* 250 */     elem.setEast(this.m_east);
/* 251 */     elem.setSouthWest(this.m_southWest);
/* 252 */     elem.setSouth(this.m_south);
/* 253 */     elem.setSouthEast(this.m_southEast);
/* 254 */     elem.setScale(this.m_scale);
/* 255 */     elem.setEnabled(this.m_enabled);
/* 256 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 263 */     XPixmapBackground elem = new XPixmapBackground();
/* 264 */     copyElementData((IElement)elem);
/* 265 */     return (IElement)elem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PixmapBackground getPixmapBackground(ThemePixmapBackground theme) {
/*     */     PixmapBackground background;
/* 274 */     if (theme == null) {
/* 275 */       return null;
/*     */     }
/*     */     
/* 278 */     Pixmap center = FengguiConstant.toFengguiPixmap(theme.getCenter());
/* 279 */     Pixmap northWest = FengguiConstant.toFengguiPixmap(theme.getNorthWest());
/* 280 */     Pixmap north = FengguiConstant.toFengguiPixmap(theme.getNorth());
/* 281 */     Pixmap northEast = FengguiConstant.toFengguiPixmap(theme.getNorthEast());
/* 282 */     Pixmap east = FengguiConstant.toFengguiPixmap(theme.getEast());
/* 283 */     Pixmap southWest = FengguiConstant.toFengguiPixmap(theme.getSouthWest());
/* 284 */     Pixmap south = FengguiConstant.toFengguiPixmap(theme.getSouth());
/* 285 */     Pixmap southEast = FengguiConstant.toFengguiPixmap(theme.getSouthEast());
/* 286 */     Pixmap west = FengguiConstant.toFengguiPixmap(theme.getWest());
/*     */     
/* 288 */     if (center == null) {
/* 289 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 294 */     if (northWest == null || north == null || northEast == null || west == null || 
/* 295 */       east == null || southWest == null || south == null || southEast == null) {
/* 296 */       background = new PixmapBackground(center, theme.isScaled());
/*     */     } else {
/* 298 */       background = new PixmapBackground(center, northWest, north, northEast, east, southEast, 
/* 299 */           south, southWest, west, theme.isScaled());
/*     */     } 
/*     */     
/* 302 */     background.setEnabled(theme.isEnabled());
/*     */     
/* 304 */     return background;
/*     */   }
/*     */ 
/*     */   
/*     */   public ThemeBackground toThemeBackground() {
/* 309 */     ThemePixmapBackground bg = new ThemePixmapBackground();
/*     */     
/* 311 */     bg.setNorthWest(this.m_northWest);
/* 312 */     bg.setNorth(this.m_north);
/* 313 */     bg.setNorthEast(this.m_northEast);
/* 314 */     bg.setWest(this.m_west);
/* 315 */     bg.setCenter(this.m_center);
/* 316 */     bg.setEast(this.m_east);
/* 317 */     bg.setSouthWest(this.m_southWest);
/* 318 */     bg.setSouth(this.m_south);
/* 319 */     bg.setSouthEast(this.m_southEast);
/* 320 */     bg.setScaled(this.m_scale);
/* 321 */     bg.setEnabled(this.m_enabled);
/*     */     
/* 323 */     return (ThemeBackground)bg;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XPixmapBackground.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */