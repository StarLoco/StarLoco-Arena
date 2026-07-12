/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeBackground;
/*     */ import com.ankamagames.xulor.theme.ThemePixmapBackground;
/*     */ import org.fenggui.background.Background;
/*     */ import org.fenggui.background.PixmapBackground;
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
/*     */ public class XPixmapBackground
/*     */   extends XBackground
/*     */ {
/*  24 */   private PixmapBackground m_pixmapBackground = null;
/*     */   
/*     */   public static final String TAG = "PixmapBackground";
/*     */   
/*     */   private com.ankamagames.xulor.util.Pixmap m_center;
/*     */   private com.ankamagames.xulor.util.Pixmap m_north;
/*     */   private com.ankamagames.xulor.util.Pixmap m_northWest;
/*     */   private com.ankamagames.xulor.util.Pixmap m_northEast;
/*     */   private com.ankamagames.xulor.util.Pixmap m_west;
/*     */   private com.ankamagames.xulor.util.Pixmap m_east;
/*     */   private com.ankamagames.xulor.util.Pixmap m_southWest;
/*     */   private com.ankamagames.xulor.util.Pixmap m_south;
/*     */   private com.ankamagames.xulor.util.Pixmap m_southEast;
/*  37 */   private boolean m_scale = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/*  52 */     if (!(this.m_parent instanceof XDecoratorAppearance)) {
/*  53 */       return;
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
/*     */   public void buildXML() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isScale()
/*     */   {
/*  73 */     return this.m_scale;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setScale(boolean scale)
/*     */   {
/*  80 */     this.m_scale = scale;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public com.ankamagames.xulor.util.Pixmap getCenter()
/*     */   {
/*  87 */     return this.m_center;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setCenter(com.ankamagames.xulor.util.Pixmap center)
/*     */   {
/*  94 */     this.m_center = center;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public com.ankamagames.xulor.util.Pixmap getEast()
/*     */   {
/* 101 */     return this.m_east;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setEast(com.ankamagames.xulor.util.Pixmap east)
/*     */   {
/* 108 */     this.m_east = east;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public com.ankamagames.xulor.util.Pixmap getNorth()
/*     */   {
/* 115 */     return this.m_north;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setNorth(com.ankamagames.xulor.util.Pixmap north)
/*     */   {
/* 122 */     this.m_north = north;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public com.ankamagames.xulor.util.Pixmap getNorthEast()
/*     */   {
/* 129 */     return this.m_northEast;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setNorthEast(com.ankamagames.xulor.util.Pixmap northEast)
/*     */   {
/* 136 */     this.m_northEast = northEast;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public com.ankamagames.xulor.util.Pixmap getNorthWest()
/*     */   {
/* 143 */     return this.m_northWest;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setNorthWest(com.ankamagames.xulor.util.Pixmap northWest)
/*     */   {
/* 150 */     this.m_northWest = northWest;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public PixmapBackground getPixmapBackground()
/*     */   {
/* 157 */     return this.m_pixmapBackground;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPixmapBackground(PixmapBackground pixmapBackground)
/*     */   {
/* 164 */     this.m_pixmapBackground = pixmapBackground;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public com.ankamagames.xulor.util.Pixmap getSouth()
/*     */   {
/* 171 */     return this.m_south;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setSouth(com.ankamagames.xulor.util.Pixmap south)
/*     */   {
/* 178 */     this.m_south = south;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public com.ankamagames.xulor.util.Pixmap getSouthEast()
/*     */   {
/* 185 */     return this.m_southEast;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setSouthEast(com.ankamagames.xulor.util.Pixmap southEast)
/*     */   {
/* 192 */     this.m_southEast = southEast;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public com.ankamagames.xulor.util.Pixmap getSouthWest()
/*     */   {
/* 199 */     return this.m_southWest;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setSouthWest(com.ankamagames.xulor.util.Pixmap southWest)
/*     */   {
/* 206 */     this.m_southWest = southWest;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public com.ankamagames.xulor.util.Pixmap getWest()
/*     */   {
/* 213 */     return this.m_west;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setWest(com.ankamagames.xulor.util.Pixmap west)
/*     */   {
/* 220 */     this.m_west = west;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Background getBackground()
/*     */   {
/* 228 */     return this.m_pixmapBackground;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 236 */     return "PixmapBackground";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyElementData(IElement element)
/*     */   {
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
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 263 */     XPixmapBackground elem = new XPixmapBackground();
/* 264 */     copyElementData(elem);
/* 265 */     return elem;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PixmapBackground getPixmapBackground(ThemePixmapBackground theme)
/*     */   {
/* 274 */     if (theme == null) {
/* 275 */       return null;
/*     */     }
/*     */     
/* 278 */     org.fenggui.render.Pixmap center = FengguiConstant.toFengguiPixmap(theme.getCenter());
/* 279 */     org.fenggui.render.Pixmap northWest = FengguiConstant.toFengguiPixmap(theme.getNorthWest());
/* 280 */     org.fenggui.render.Pixmap north = FengguiConstant.toFengguiPixmap(theme.getNorth());
/* 281 */     org.fenggui.render.Pixmap northEast = FengguiConstant.toFengguiPixmap(theme.getNorthEast());
/* 282 */     org.fenggui.render.Pixmap east = FengguiConstant.toFengguiPixmap(theme.getEast());
/* 283 */     org.fenggui.render.Pixmap southWest = FengguiConstant.toFengguiPixmap(theme.getSouthWest());
/* 284 */     org.fenggui.render.Pixmap south = FengguiConstant.toFengguiPixmap(theme.getSouth());
/* 285 */     org.fenggui.render.Pixmap southEast = FengguiConstant.toFengguiPixmap(theme.getSouthEast());
/* 286 */     org.fenggui.render.Pixmap west = FengguiConstant.toFengguiPixmap(theme.getWest());
/*     */     
/* 288 */     if (center == null) {
/* 289 */       return null;
/*     */     }
/*     */     
/*     */     PixmapBackground background;
/*     */     PixmapBackground background;
/* 294 */     if ((northWest == null) || (north == null) || (northEast == null) || (west == null) || 
/* 295 */       (east == null) || (southWest == null) || (south == null) || (southEast == null)) {
/* 296 */       background = new PixmapBackground(center, theme.isScaled());
/*     */     } else {
/* 298 */       background = new PixmapBackground(center, northWest, north, northEast, east, southEast, 
/* 299 */         south, southWest, west, theme.isScaled());
/*     */     }
/*     */     
/* 302 */     background.setEnabled(theme.isEnabled());
/*     */     
/* 304 */     return background;
/*     */   }
/*     */   
/*     */   public ThemeBackground toThemeBackground()
/*     */   {
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
/* 323 */     return bg;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XPixmapBackground.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */