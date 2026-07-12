/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeBorder;
/*     */ import com.ankamagames.xulor.theme.ThemePixmapBorder16;
/*     */ import com.ankamagames.xulor.util.Pixmap;
/*     */ import org.fenggui.border.Border;
/*     */ import org.fenggui.border.PixmapBorder16;
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
/*     */ 
/*     */ 
/*     */ public class XPixmapBorder16
/*     */   extends XBorder
/*     */ {
/*     */   public static final String TAG = "PixmapBorder16";
/*  27 */   private PixmapBorder16 m_pixmapBorder = null;
/*     */   
/*     */   private Pixmap m_north;
/*     */   private Pixmap m_northWest;
/*     */   private Pixmap m_northEast;
/*     */   private Pixmap m_west;
/*     */   private Pixmap m_east;
/*     */   private Pixmap m_southWest;
/*     */   private Pixmap m_south;
/*     */   private Pixmap m_southEast;
/*  37 */   private Pixmap m_northNorthWest = null;
/*  38 */   private Pixmap m_northNorthEast = null;
/*  39 */   private Pixmap m_southSouthWest = null;
/*  40 */   private Pixmap m_southSouthEast = null;
/*  41 */   private Pixmap m_westNorthWest = null;
/*  42 */   private Pixmap m_eastNorthEast = null;
/*  43 */   private Pixmap m_westSouthWest = null;
/*  44 */   private Pixmap m_eastSouthEast = null;
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
/*     */   public void buildGUI() {}
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
/*     */   public Pixmap getEast() {
/*  70 */     return this.m_east;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEast(Pixmap east) {
/*  77 */     this.m_east = east;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorth() {
/*  84 */     return this.m_north;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorth(Pixmap north) {
/*  91 */     this.m_north = north;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorthEast() {
/*  98 */     return this.m_northEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorthEast(Pixmap northEast) {
/* 105 */     this.m_northEast = northEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorthWest() {
/* 112 */     return this.m_northWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorthWest(Pixmap northWest) {
/* 119 */     this.m_northWest = northWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PixmapBorder16 getPixmapBorder() {
/* 126 */     return this.m_pixmapBorder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPixmapBorder(PixmapBorder16 pixmapBorder) {
/* 133 */     this.m_pixmapBorder = pixmapBorder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouth() {
/* 140 */     return this.m_south;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouth(Pixmap south) {
/* 147 */     this.m_south = south;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouthEast() {
/* 154 */     return this.m_southEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouthEast(Pixmap southEast) {
/* 161 */     this.m_southEast = southEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouthWest() {
/* 168 */     return this.m_southWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouthWest(Pixmap southWest) {
/* 175 */     this.m_southWest = southWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getWest() {
/* 182 */     return this.m_west;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWest(Pixmap west) {
/* 189 */     this.m_west = west;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getEastNorthEast() {
/* 196 */     return this.m_eastNorthEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEastNorthEast(Pixmap eastNorthEast) {
/* 203 */     this.m_eastNorthEast = eastNorthEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getEastSouthEast() {
/* 210 */     return this.m_eastSouthEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEastSouthEast(Pixmap eastSouthEast) {
/* 217 */     this.m_eastSouthEast = eastSouthEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorthNorthEast() {
/* 224 */     return this.m_northNorthEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorthNorthEast(Pixmap northNorthEast) {
/* 231 */     this.m_northNorthEast = northNorthEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorthNorthWest() {
/* 238 */     return this.m_northNorthWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorthNorthWest(Pixmap northNorthWest) {
/* 245 */     this.m_northNorthWest = northNorthWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouthSouthEast() {
/* 252 */     return this.m_southSouthEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouthSouthEast(Pixmap southSouthEast) {
/* 259 */     this.m_southSouthEast = southSouthEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouthSouthWest() {
/* 266 */     return this.m_southSouthWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouthSouthWest(Pixmap southSouthWest) {
/* 273 */     this.m_southSouthWest = southSouthWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getWestNorthWest() {
/* 280 */     return this.m_westNorthWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWestNorthWest(Pixmap westNorthWest) {
/* 287 */     this.m_westNorthWest = westNorthWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getWestSouthWest() {
/* 294 */     return this.m_westSouthWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWestSouthWest(Pixmap westSouthWest) {
/* 301 */     this.m_westSouthWest = westSouthWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 309 */     return "PixmapBorder16";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 317 */     XPixmapBorder16 elem = (XPixmapBorder16)element;
/* 318 */     elem.setNorthWest(this.m_northWest);
/* 319 */     elem.setNorth(this.m_north);
/* 320 */     elem.setNorthEast(this.m_northEast);
/* 321 */     elem.setWest(this.m_west);
/* 322 */     elem.setEast(this.m_east);
/* 323 */     elem.setSouthWest(this.m_southWest);
/* 324 */     elem.setSouth(this.m_south);
/* 325 */     elem.setSouthEast(this.m_southEast);
/* 326 */     elem.setNorthNorthWest(this.m_northNorthWest);
/* 327 */     elem.setNorthNorthEast(this.m_northNorthEast);
/* 328 */     elem.setWestNorthWest(this.m_westNorthWest);
/* 329 */     elem.setEastNorthEast(this.m_eastNorthEast);
/* 330 */     elem.setSouthSouthWest(this.m_southSouthWest);
/* 331 */     elem.setSouthSouthEast(this.m_southSouthEast);
/* 332 */     elem.setWestSouthWest(this.m_westSouthWest);
/* 333 */     elem.setEastSouthEast(this.m_eastSouthEast);
/* 334 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 341 */     XPixmapBorder16 elem = new XPixmapBorder16();
/* 342 */     copyElementData((IElement)elem);
/* 343 */     return (IElement)elem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Border getBorder() {
/* 351 */     return (Border)this.m_pixmapBorder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PixmapBorder16 getPixmapBorder16(ThemePixmapBorder16 theme) {
/* 360 */     if (theme == null) {
/* 361 */       return null;
/*     */     }
/*     */     
/* 364 */     Pixmap northWest = FengguiConstant.toFengguiPixmap(theme.getNorthWest());
/* 365 */     Pixmap north = FengguiConstant.toFengguiPixmap(theme.getNorth());
/* 366 */     Pixmap northEast = FengguiConstant.toFengguiPixmap(theme.getNorthEast());
/* 367 */     Pixmap east = FengguiConstant.toFengguiPixmap(theme.getEast());
/* 368 */     Pixmap southWest = FengguiConstant.toFengguiPixmap(theme.getSouthWest());
/* 369 */     Pixmap south = FengguiConstant.toFengguiPixmap(theme.getSouth());
/* 370 */     Pixmap southEast = FengguiConstant.toFengguiPixmap(theme.getSouthEast());
/* 371 */     Pixmap west = FengguiConstant.toFengguiPixmap(theme.getWest());
/* 372 */     Pixmap northNorthWest = FengguiConstant.toFengguiPixmap(theme.getNorthNorthWest());
/* 373 */     Pixmap northNorthEast = FengguiConstant.toFengguiPixmap(theme.getNorthNorthEast());
/* 374 */     Pixmap westNorthWest = FengguiConstant.toFengguiPixmap(theme.getWestNorthWest());
/* 375 */     Pixmap eastNorthEast = FengguiConstant.toFengguiPixmap(theme.getEastNorthEast());
/* 376 */     Pixmap southSouthWest = FengguiConstant.toFengguiPixmap(theme.getSouthSouthWest());
/* 377 */     Pixmap southSouthEast = FengguiConstant.toFengguiPixmap(theme.getSouthSouthEast());
/* 378 */     Pixmap westSouthWest = FengguiConstant.toFengguiPixmap(theme.getWestSouthWest());
/* 379 */     Pixmap eastSouthEast = FengguiConstant.toFengguiPixmap(theme.getEastSouthEast());
/*     */     
/* 381 */     if (northWest == null || north == null || northEast == null || west == null || 
/* 382 */       east == null || southWest == null || south == null || southEast == null || 
/* 383 */       northNorthWest == null || northNorthEast == null || westNorthWest == null || eastNorthEast == null || 
/* 384 */       southSouthWest == null || southSouthEast == null || westSouthWest == null || eastSouthEast == null) {
/* 385 */       return null;
/*     */     }
/*     */     
/* 388 */     PixmapBorder16 border = new PixmapBorder16(new Pixmap[] { 
/* 389 */           northWest, northNorthWest, north, northNorthEast, northEast, 
/* 390 */           westNorthWest, eastNorthEast, west, east, westSouthWest, eastSouthEast, 
/* 391 */           southWest, southSouthWest, south, southSouthEast, southEast });
/*     */     
/* 393 */     border.setEnabled(theme.isEnabled());
/* 394 */     return border;
/*     */   }
/*     */ 
/*     */   
/*     */   public ThemeBorder toThemeBorder() {
/* 399 */     ThemePixmapBorder16 border = new ThemePixmapBorder16();
/*     */     
/* 401 */     border.setNorthWest(this.m_northWest);
/* 402 */     border.setNorth(this.m_north);
/* 403 */     border.setNorthEast(this.m_northEast);
/* 404 */     border.setWest(this.m_west);
/* 405 */     border.setEast(this.m_east);
/* 406 */     border.setSouthWest(this.m_southWest);
/* 407 */     border.setSouth(this.m_south);
/* 408 */     border.setSouthEast(this.m_southEast);
/* 409 */     border.setNorthNorthWest(this.m_northNorthWest);
/* 410 */     border.setNorthNorthEast(this.m_northNorthEast);
/* 411 */     border.setWestNorthWest(this.m_westNorthWest);
/* 412 */     border.setEastNorthEast(this.m_eastNorthEast);
/* 413 */     border.setSouthSouthWest(this.m_southSouthWest);
/* 414 */     border.setSouthSouthEast(this.m_southSouthEast);
/* 415 */     border.setWestSouthWest(this.m_westSouthWest);
/* 416 */     border.setEastSouthEast(this.m_eastSouthEast);
/* 417 */     border.setEnabled(this.m_enabled);
/* 418 */     border.setSpacing(this.m_spacing);
/* 419 */     border.setAsBorderSpacing(this.m_asBorderSpacing);
/* 420 */     return (ThemeBorder)border;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XPixmapBorder16.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */