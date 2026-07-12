/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeBorder;
/*     */ import com.ankamagames.xulor.theme.ThemePixmapBorder;
/*     */ import com.ankamagames.xulor.util.Pixmap;
/*     */ import org.fenggui.border.Border;
/*     */ import org.fenggui.border.PixmapBorder;
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
/*     */ public class XPixmapBorder
/*     */   extends XBorder
/*     */ {
/*     */   public static final String TAG = "PixmapBorder";
/*  25 */   private PixmapBorder m_pixmapBorder = null;
/*     */ 
/*     */   
/*     */   private Pixmap m_north;
/*     */ 
/*     */   
/*     */   private Pixmap m_northWest;
/*     */ 
/*     */   
/*     */   private Pixmap m_northEast;
/*     */ 
/*     */   
/*     */   private Pixmap m_west;
/*     */ 
/*     */   
/*     */   private Pixmap m_east;
/*     */ 
/*     */   
/*     */   private Pixmap m_southWest;
/*     */   
/*     */   private Pixmap m_south;
/*     */   
/*     */   private Pixmap m_southEast;
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {}
/*     */ 
/*     */   
/*     */   public void buildGUI() {}
/*     */ 
/*     */   
/*     */   public void buildXML() {}
/*     */ 
/*     */   
/*     */   public Pixmap getEast() {
/*  60 */     return this.m_east;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEast(Pixmap east) {
/*  67 */     this.m_east = east;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorth() {
/*  74 */     return this.m_north;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorth(Pixmap north) {
/*  81 */     this.m_north = north;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorthEast() {
/*  88 */     return this.m_northEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorthEast(Pixmap northEast) {
/*  95 */     this.m_northEast = northEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getNorthWest() {
/* 102 */     return this.m_northWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNorthWest(Pixmap northWest) {
/* 109 */     this.m_northWest = northWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PixmapBorder getPixmapBorder() {
/* 116 */     return this.m_pixmapBorder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPixmapBorder(PixmapBorder pixmapBorder) {
/* 123 */     this.m_pixmapBorder = pixmapBorder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouth() {
/* 130 */     return this.m_south;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouth(Pixmap south) {
/* 137 */     this.m_south = south;
/*     */   }
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
/*     */   public void setSouthEast(Pixmap southEast) {
/* 151 */     this.m_southEast = southEast;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getSouthWest() {
/* 158 */     return this.m_southWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSouthWest(Pixmap southWest) {
/* 165 */     this.m_southWest = southWest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getWest() {
/* 172 */     return this.m_west;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWest(Pixmap west) {
/* 179 */     this.m_west = west;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 187 */     return "PixmapBorder";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 195 */     XPixmapBorder elem = (XPixmapBorder)element;
/* 196 */     elem.setNorthWest(this.m_northWest);
/* 197 */     elem.setNorth(this.m_north);
/* 198 */     elem.setNorthEast(this.m_northEast);
/* 199 */     elem.setWest(this.m_west);
/* 200 */     elem.setEast(this.m_east);
/* 201 */     elem.setSouthWest(this.m_southWest);
/* 202 */     elem.setSouth(this.m_south);
/* 203 */     elem.setSouthEast(this.m_southEast);
/* 204 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 211 */     XPixmapBorder elem = new XPixmapBorder();
/* 212 */     copyElementData((IElement)elem);
/* 213 */     return (IElement)elem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Border getBorder() {
/* 221 */     return (Border)this.m_pixmapBorder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PixmapBorder getPixmapBorder(ThemePixmapBorder theme) {
/* 230 */     if (theme == null) {
/* 231 */       return null;
/*     */     }
/*     */     
/* 234 */     Pixmap northWest = FengguiConstant.toFengguiPixmap(theme.getNorthWest());
/* 235 */     Pixmap north = FengguiConstant.toFengguiPixmap(theme.getNorth());
/* 236 */     Pixmap northEast = FengguiConstant.toFengguiPixmap(theme.getNorthEast());
/* 237 */     Pixmap east = FengguiConstant.toFengguiPixmap(theme.getEast());
/* 238 */     Pixmap southWest = FengguiConstant.toFengguiPixmap(theme.getSouthWest());
/* 239 */     Pixmap south = FengguiConstant.toFengguiPixmap(theme.getSouth());
/* 240 */     Pixmap southEast = FengguiConstant.toFengguiPixmap(theme.getSouthEast());
/* 241 */     Pixmap west = FengguiConstant.toFengguiPixmap(theme.getWest());
/*     */     
/* 243 */     if (northWest == null || north == null || northEast == null || west == null || 
/* 244 */       east == null || southWest == null || south == null || southEast == null) {
/* 245 */       return null;
/*     */     }
/*     */     
/* 248 */     PixmapBorder border = new PixmapBorder(west, east, north, south, northWest, northEast, southWest, southEast);
/* 249 */     border.setEnabled(theme.isEnabled());
/* 250 */     return border;
/*     */   }
/*     */ 
/*     */   
/*     */   public ThemeBorder toThemeBorder() {
/* 255 */     ThemePixmapBorder border = new ThemePixmapBorder();
/*     */     
/* 257 */     border.setNorthWest(this.m_northWest);
/* 258 */     border.setNorth(this.m_north);
/* 259 */     border.setNorthEast(this.m_northEast);
/* 260 */     border.setWest(this.m_west);
/* 261 */     border.setEast(this.m_east);
/* 262 */     border.setSouthWest(this.m_southWest);
/* 263 */     border.setSouth(this.m_south);
/* 264 */     border.setSouthEast(this.m_southEast);
/* 265 */     border.setEnabled(this.m_enabled);
/* 266 */     border.setSpacing(this.m_spacing);
/* 267 */     border.setAsBorderSpacing(this.m_asBorderSpacing);
/* 268 */     return (ThemeBorder)border;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XPixmapBorder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */