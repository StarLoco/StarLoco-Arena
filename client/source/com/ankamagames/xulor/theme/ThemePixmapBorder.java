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
/*     */ public class ThemePixmapBorder
/*     */   extends ThemeBorder
/*     */   implements IThemeElement
/*     */ {
/*     */   public static final String TAG = "PixmapBorder";
/*  19 */   private Pixmap m_northWest = null;
/*     */   
/*  21 */   private Pixmap m_north = null;
/*  22 */   private Pixmap m_northEast = null;
/*  23 */   private Pixmap m_west = null;
/*  24 */   private Pixmap m_east = null;
/*  25 */   private Pixmap m_southWest = null;
/*  26 */   private Pixmap m_south = null;
/*  27 */   private Pixmap m_southEast = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(IThemeElement elem) {
/*  34 */     if (elem instanceof ThemePixmap) {
/*  35 */       ThemePixmap tp = (ThemePixmap)elem;
/*     */       
/*  37 */       if (tp.getPosition().equals(Alignment.NORTH_WEST)) {
/*  38 */         this.m_northWest = tp.getPixmap();
/*  39 */       } else if (tp.getPosition().equals(Alignment.NORTH)) {
/*  40 */         this.m_north = tp.getPixmap();
/*  41 */       } else if (tp.getPosition().equals(Alignment.NORTH_EAST)) {
/*  42 */         this.m_northEast = tp.getPixmap();
/*  43 */       } else if (tp.getPosition().equals(Alignment.WEST)) {
/*  44 */         this.m_west = tp.getPixmap();
/*  45 */       } else if (tp.getPosition().equals(Alignment.EAST)) {
/*  46 */         this.m_east = tp.getPixmap();
/*  47 */       } else if (tp.getPosition().equals(Alignment.SOUTH_WEST)) {
/*  48 */         this.m_southWest = tp.getPixmap();
/*  49 */       } else if (tp.getPosition().equals(Alignment.SOUTH)) {
/*  50 */         this.m_south = tp.getPixmap();
/*  51 */       } else if (tp.getPosition().equals(Alignment.SOUTH_EAST)) {
/*  52 */         this.m_southEast = tp.getPixmap();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getEast() {
/*  62 */     return this.m_east;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorth() {
/*  70 */     return this.m_north;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorthEast() {
/*  78 */     return this.m_northEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorthWest() {
/*  86 */     return this.m_northWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouth() {
/*  94 */     return this.m_south;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouthEast() {
/* 102 */     return this.m_southEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouthWest() {
/* 110 */     return this.m_southWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getWest() {
/* 118 */     return this.m_west;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEast(Pixmap east) {
/* 125 */     this.m_east = east;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorth(Pixmap north) {
/* 132 */     this.m_north = north;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorthEast(Pixmap northEast) {
/* 139 */     this.m_northEast = northEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorthWest(Pixmap northWest) {
/* 146 */     this.m_northWest = northWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouth(Pixmap south) {
/* 153 */     this.m_south = south;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouthEast(Pixmap southEast) {
/* 160 */     this.m_southEast = southEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouthWest(Pixmap southWest) {
/* 167 */     this.m_southWest = southWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWest(Pixmap west) {
/* 174 */     this.m_west = west;
/*     */   }
/*     */   
/*     */   public IThemeElement cloneAppearance() {
/* 178 */     ThemePixmapBorder border = new ThemePixmapBorder();
/*     */     
/* 180 */     border.setNorthWest(this.m_northWest);
/* 181 */     border.setNorth(this.m_north);
/* 182 */     border.setNorthEast(this.m_northEast);
/* 183 */     border.setWest(this.m_west);
/* 184 */     border.setEast(this.m_east);
/* 185 */     border.setSouthWest(this.m_southWest);
/* 186 */     border.setSouth(this.m_south);
/* 187 */     border.setSouthEast(this.m_southEast);
/* 188 */     border.setAsBorderSpacing(this.m_asBorderSpacing);
/* 189 */     border.setEnabled(this.m_enabled);
/* 190 */     border.setSpacing(this.m_spacing);
/* 191 */     return border;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemePixmapBorder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */