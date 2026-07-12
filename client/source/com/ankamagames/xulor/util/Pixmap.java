/*     */ package com.ankamagames.xulor.util;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Pixmap
/*     */ {
/*  22 */   private Object m_instanciatedPixmap = null;
/*     */   
/*     */   private ThemeTexture m_texture;
/*     */   
/*     */   private int m_width;
/*     */   private int m_height;
/*     */   private int m_x;
/*     */   private int m_y;
/*     */   private boolean m_needReinstanciation = false;
/*     */   
/*     */   public Pixmap() {
/*  33 */     this(null, -1, -1, 0, 0);
/*     */   }
/*     */   
/*     */   public Pixmap(ThemeTexture texture) {
/*  37 */     if (texture != null) {
/*  38 */       this.m_width = texture.getWidth();
/*  39 */       this.m_height = texture.getHeight();
/*     */     } 
/*  41 */     this.m_x = 0;
/*  42 */     this.m_y = 0;
/*  43 */     this.m_texture = texture;
/*  44 */     this.m_needReinstanciation = true;
/*     */   }
/*     */   
/*     */   public Pixmap(ThemeTexture texture, int width, int height, int x, int y) {
/*  48 */     this.m_texture = texture;
/*  49 */     this.m_x = x;
/*  50 */     this.m_y = y;
/*  51 */     this.m_width = width;
/*  52 */     this.m_height = height;
/*  53 */     this.m_needReinstanciation = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  62 */     return this.m_height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeight(int height) {
/*  72 */     if (height != this.m_height) {
/*  73 */       this.m_height = height;
/*  74 */       this.m_needReinstanciation = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThemeTexture getTexture() {
/*  84 */     return this.m_texture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTexture(ThemeTexture texture) {
/*  94 */     if (texture != this.m_texture) {
/*  95 */       this.m_texture = texture;
/*  96 */       if (this.m_texture != null) {
/*  97 */         if (this.m_width == -1) {
/*  98 */           this.m_width = texture.getWidth();
/*  99 */           this.m_x = 0;
/*     */         } 
/* 101 */         if (this.m_height == -1) {
/* 102 */           this.m_height = texture.getHeight();
/* 103 */           this.m_y = 0;
/*     */         } 
/*     */       } 
/* 106 */       this.m_needReinstanciation = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 116 */     return this.m_width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWidth(int width) {
/* 126 */     if (width != this.m_width) {
/* 127 */       this.m_width = width;
/* 128 */       this.m_needReinstanciation = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/* 139 */     return this.m_x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(int x) {
/* 149 */     if (x != this.m_x) {
/* 150 */       this.m_x = x;
/* 151 */       this.m_needReinstanciation = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY() {
/* 162 */     return this.m_y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(int y) {
/* 172 */     if (y != this.m_y) {
/* 173 */       this.m_y = y;
/* 174 */       this.m_needReinstanciation = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getInstanciatedPixmap() {
/* 184 */     return this.m_instanciatedPixmap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInstanciatedPixmap(Object instanciatedPixmap) {
/* 194 */     this.m_instanciatedPixmap = instanciatedPixmap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needReinstanciation() {
/* 201 */     return this.m_needReinstanciation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNeedReinstanciation(boolean needReinstanciation) {
/* 209 */     this.m_needReinstanciation = needReinstanciation;
/*     */   }
/*     */   
/*     */   public Pixmap clone() {
/* 213 */     Pixmap pixmap = new Pixmap(this.m_texture, this.m_width, this.m_height, this.m_x, this.m_y);
/* 214 */     pixmap.setInstanciatedPixmap(this.m_instanciatedPixmap);
/* 215 */     pixmap.setNeedReinstanciation(this.m_needReinstanciation);
/* 216 */     return pixmap;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulo\\util\Pixmap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */