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
/*     */ public class ThemePixmapBackground
/*     */   extends ThemeBackground
/*     */   implements IThemeElement
/*     */ {
/*     */   public static final String TAG = "PixmapBackground";
/*  19 */   private Pixmap m_northWest = null;
/*  20 */   private Pixmap m_north = null;
/*  21 */   private Pixmap m_northEast = null;
/*  22 */   private Pixmap m_west = null;
/*  23 */   private Pixmap m_east = null;
/*  24 */   private Pixmap m_southWest = null;
/*  25 */   private Pixmap m_south = null;
/*  26 */   private Pixmap m_southEast = null;
/*  27 */   private Pixmap m_center = null;
/*     */ 
/*     */   
/*     */   private boolean m_scaled = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(IThemeElement elem) {
/*  35 */     if (elem instanceof ThemePixmap) {
/*  36 */       ThemePixmap tp = (ThemePixmap)elem;
/*     */       
/*  38 */       if (tp.getPosition().equals(Alignment.CENTER)) {
/*  39 */         this.m_center = tp.getPixmap();
/*  40 */       } else if (tp.getPosition().equals(Alignment.NORTH_WEST)) {
/*  41 */         this.m_northWest = tp.getPixmap();
/*  42 */       } else if (tp.getPosition().equals(Alignment.NORTH)) {
/*  43 */         this.m_north = tp.getPixmap();
/*  44 */       } else if (tp.getPosition().equals(Alignment.NORTH_EAST)) {
/*  45 */         this.m_northEast = tp.getPixmap();
/*  46 */       } else if (tp.getPosition().equals(Alignment.WEST)) {
/*  47 */         this.m_west = tp.getPixmap();
/*  48 */       } else if (tp.getPosition().equals(Alignment.EAST)) {
/*  49 */         this.m_east = tp.getPixmap();
/*  50 */       } else if (tp.getPosition().equals(Alignment.SOUTH_WEST)) {
/*  51 */         this.m_southWest = tp.getPixmap();
/*  52 */       } else if (tp.getPosition().equals(Alignment.SOUTH)) {
/*  53 */         this.m_south = tp.getPixmap();
/*  54 */       } else if (tp.getPosition().equals(Alignment.SOUTH_EAST)) {
/*  55 */         this.m_southEast = tp.getPixmap();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getCenter() {
/*  65 */     return this.m_center;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getEast() {
/*  73 */     return this.m_east;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorth() {
/*  81 */     return this.m_north;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorthEast() {
/*  89 */     return this.m_northEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorthWest() {
/*  97 */     return this.m_northWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouth() {
/* 105 */     return this.m_south;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouthEast() {
/* 113 */     return this.m_southEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouthWest() {
/* 121 */     return this.m_southWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getWest() {
/* 129 */     return this.m_west;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCenter(Pixmap center) {
/* 136 */     this.m_center = center;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEast(Pixmap east) {
/* 143 */     this.m_east = east;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorth(Pixmap north) {
/* 150 */     this.m_north = north;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorthEast(Pixmap northEast) {
/* 157 */     this.m_northEast = northEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorthWest(Pixmap northWest) {
/* 164 */     this.m_northWest = northWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouth(Pixmap south) {
/* 171 */     this.m_south = south;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouthEast(Pixmap southEast) {
/* 178 */     this.m_southEast = southEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouthWest(Pixmap southWest) {
/* 185 */     this.m_southWest = southWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWest(Pixmap west) {
/* 192 */     this.m_west = west;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isScaled() {
/* 199 */     return this.m_scaled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScaled(boolean scaled) {
/* 206 */     this.m_scaled = scaled;
/*     */   }
/*     */   
/*     */   public IThemeElement cloneAppearance() {
/* 210 */     ThemePixmapBackground bg = new ThemePixmapBackground();
/*     */     
/* 212 */     bg.setNorthWest(this.m_northWest);
/* 213 */     bg.setNorth(this.m_north);
/* 214 */     bg.setNorthEast(this.m_northEast);
/* 215 */     bg.setWest(this.m_west);
/* 216 */     bg.setCenter(this.m_center);
/* 217 */     bg.setEast(this.m_east);
/* 218 */     bg.setSouthWest(this.m_southWest);
/* 219 */     bg.setSouth(this.m_south);
/* 220 */     bg.setSouthEast(this.m_southEast);
/* 221 */     bg.setScaled(this.m_scaled);
/* 222 */     bg.setEnabled(this.m_enabled);
/*     */     
/* 224 */     return bg;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemePixmapBackground.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */