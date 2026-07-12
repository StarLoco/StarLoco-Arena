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
/*     */ public class ThemePixmapBorder16
/*     */   extends ThemeBorder
/*     */   implements IThemeElement
/*     */ {
/*     */   public static final String TAG = "PixmapBorder16";
/*  18 */   private Pixmap m_northWest = null;
/*     */   
/*  20 */   private Pixmap m_north = null;
/*  21 */   private Pixmap m_northEast = null;
/*  22 */   private Pixmap m_west = null;
/*  23 */   private Pixmap m_east = null;
/*  24 */   private Pixmap m_southWest = null;
/*  25 */   private Pixmap m_south = null;
/*  26 */   private Pixmap m_southEast = null;
/*  27 */   private Pixmap m_northNorthWest = null;
/*  28 */   private Pixmap m_northNorthEast = null;
/*  29 */   private Pixmap m_southSouthWest = null;
/*  30 */   private Pixmap m_southSouthEast = null;
/*  31 */   private Pixmap m_westNorthWest = null;
/*  32 */   private Pixmap m_eastNorthEast = null;
/*  33 */   private Pixmap m_westSouthWest = null;
/*  34 */   private Pixmap m_eastSouthEast = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(IThemeElement elem) {
/*  41 */     if (elem instanceof ThemePixmap) {
/*  42 */       ThemePixmap tp = (ThemePixmap)elem;
/*     */       
/*  44 */       switch (tp.getPosition()) {
/*     */         case NORTH_WEST:
/*  46 */           this.m_northWest = tp.getPixmap();
/*     */           break;
/*     */         case NORTH:
/*  49 */           this.m_north = tp.getPixmap();
/*     */           break;
/*     */         case NORTH_EAST:
/*  52 */           this.m_northEast = tp.getPixmap();
/*     */           break;
/*     */         case WEST:
/*  55 */           this.m_west = tp.getPixmap();
/*     */           break;
/*     */         case EAST:
/*  58 */           this.m_east = tp.getPixmap();
/*     */           break;
/*     */         case SOUTH_WEST:
/*  61 */           this.m_southWest = tp.getPixmap();
/*     */           break;
/*     */         case SOUTH:
/*  64 */           this.m_south = tp.getPixmap();
/*     */           break;
/*     */         case SOUTH_EAST:
/*  67 */           this.m_southEast = tp.getPixmap();
/*     */           break;
/*     */         case NORTH_NORTH_WEST:
/*  70 */           this.m_northNorthWest = tp.getPixmap();
/*     */           break;
/*     */         case NORTH_NORTH_EAST:
/*  73 */           this.m_northNorthEast = tp.getPixmap();
/*     */           break;
/*     */         case WEST_NORTH_WEST:
/*  76 */           this.m_westNorthWest = tp.getPixmap();
/*     */           break;
/*     */         case EAST_NORTH_EAST:
/*  79 */           this.m_eastNorthEast = tp.getPixmap();
/*     */           break;
/*     */         case WEST_SOUTH_WEST:
/*  82 */           this.m_westSouthWest = tp.getPixmap();
/*     */           break;
/*     */         case EAST_SOUTH_EAST:
/*  85 */           this.m_eastSouthEast = tp.getPixmap();
/*     */           break;
/*     */         case SOUTH_SOUTH_WEST:
/*  88 */           this.m_southSouthWest = tp.getPixmap();
/*     */           break;
/*     */         case SOUTH_SOUTH_EAST:
/*  91 */           this.m_southSouthEast = tp.getPixmap();
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getEast() {
/* 104 */     return this.m_east;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorth() {
/* 112 */     return this.m_north;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorthEast() {
/* 120 */     return this.m_northEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorthWest() {
/* 128 */     return this.m_northWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouth() {
/* 136 */     return this.m_south;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouthEast() {
/* 144 */     return this.m_southEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouthWest() {
/* 152 */     return this.m_southWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getWest() {
/* 160 */     return this.m_west;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEast(Pixmap east) {
/* 167 */     this.m_east = east;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorth(Pixmap north) {
/* 174 */     this.m_north = north;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorthEast(Pixmap northEast) {
/* 181 */     this.m_northEast = northEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorthWest(Pixmap northWest) {
/* 188 */     this.m_northWest = northWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouth(Pixmap south) {
/* 195 */     this.m_south = south;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouthEast(Pixmap southEast) {
/* 202 */     this.m_southEast = southEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouthWest(Pixmap southWest) {
/* 209 */     this.m_southWest = southWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWest(Pixmap west) {
/* 216 */     this.m_west = west;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getEastNorthEast() {
/* 223 */     return this.m_eastNorthEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEastNorthEast(Pixmap east_north_east) {
/* 230 */     this.m_eastNorthEast = east_north_east;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getEastSouthEast() {
/* 237 */     return this.m_eastSouthEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEastSouthEast(Pixmap east_south_east) {
/* 244 */     this.m_eastSouthEast = east_south_east;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorthNorthEast() {
/* 251 */     return this.m_northNorthEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorthNorthEast(Pixmap north_north_east) {
/* 258 */     this.m_northNorthEast = north_north_east;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorthNorthWest() {
/* 265 */     return this.m_northNorthWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorthNorthWest(Pixmap north_north_west) {
/* 272 */     this.m_northNorthWest = north_north_west;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouthSouthEast() {
/* 279 */     return this.m_southSouthEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouthSouthEast(Pixmap south_south_east) {
/* 286 */     this.m_southSouthEast = south_south_east;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouthSouthWest() {
/* 293 */     return this.m_southSouthWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouthSouthWest(Pixmap south_south_west) {
/* 300 */     this.m_southSouthWest = south_south_west;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getWestNorthWest() {
/* 307 */     return this.m_westNorthWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWestNorthWest(Pixmap west_north_west) {
/* 314 */     this.m_westNorthWest = west_north_west;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getWestSouthWest() {
/* 321 */     return this.m_westSouthWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWestSouthWest(Pixmap west_south_west) {
/* 328 */     this.m_westSouthWest = west_south_west;
/*     */   }
/*     */   
/*     */   public IThemeElement cloneAppearance() {
/* 332 */     ThemePixmapBorder16 border = new ThemePixmapBorder16();
/*     */     
/* 334 */     border.setNorthWest(this.m_northWest);
/* 335 */     border.setNorth(this.m_north);
/* 336 */     border.setNorthEast(this.m_northEast);
/* 337 */     border.setWest(this.m_west);
/* 338 */     border.setEast(this.m_east);
/* 339 */     border.setSouthWest(this.m_southWest);
/* 340 */     border.setSouth(this.m_south);
/* 341 */     border.setSouthEast(this.m_southEast);
/* 342 */     border.setNorthNorthWest(this.m_northNorthWest);
/* 343 */     border.setNorthNorthEast(this.m_northNorthEast);
/* 344 */     border.setWestNorthWest(this.m_westNorthWest);
/* 345 */     border.setEastNorthEast(this.m_eastNorthEast);
/* 346 */     border.setSouthSouthWest(this.m_southSouthWest);
/* 347 */     border.setSouthSouthEast(this.m_southSouthEast);
/* 348 */     border.setWestSouthWest(this.m_westSouthWest);
/* 349 */     border.setEastSouthEast(this.m_eastSouthEast);
/* 350 */     border.setAsBorderSpacing(this.m_asBorderSpacing);
/* 351 */     border.setEnabled(this.m_enabled);
/* 352 */     border.setSpacing(this.m_spacing);
/* 353 */     return border;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemePixmapBorder16.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */