/*     */ package org.fenggui.render;
/*     */ 
/*     */ import org.fenggui.util.CharacterPixmap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DirectTextRenderer
/*     */   implements ITextRenderer
/*     */ {
/*  34 */   private String text = null;
/*  35 */   private Font font = Font.getDefaultFont();
/*  36 */   private int height = -1, width = -1;
/*     */ 
/*     */   
/*     */   public String getText() {
/*  40 */     return this.text;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(int x, int y, Graphics g, IOpenGL gl) {
/*  45 */     if (this.text == null || this.text.length() == 0)
/*     */       return; 
/*  47 */     int localX = x + g.getTranslation().getX();
/*  48 */     int localY = y + g.getTranslation().getY() + getHeight() - this.font.getHeight();
/*     */     
/*  50 */     gl.enableTexture2D(true);
/*     */     
/*  52 */     CharacterPixmap pixmap = null;
/*     */     
/*  54 */     boolean init = true;
/*     */     
/*  56 */     for (int i = 0; i < this.text.length(); i++) {
/*     */       
/*  58 */       char c = this.text.charAt(i);
/*  59 */       if (c != '\r' && c != '\f' && c != '\t')
/*  60 */         if (c == '\n') {
/*     */           
/*  62 */           localY -= this.font.getHeight();
/*  63 */           localX = x + g.getTranslation().getX();
/*     */         } else {
/*     */           
/*  66 */           pixmap = getFont().getCharPixMap(c);
/*     */           
/*  68 */           if (init) {
/*     */             
/*  70 */             ITexture tex = pixmap.getTexture();
/*     */             
/*  72 */             if (tex.hasAlpha())
/*     */             {
/*  74 */               gl.setTexEnvModeModulate();
/*     */             }
/*     */             
/*  77 */             tex.bind();
/*  78 */             gl.startQuads();
/*  79 */             init = false;
/*     */           } 
/*     */           
/*  82 */           int imgWidth = pixmap.getWidth();
/*  83 */           int imgHeight = pixmap.getHeight();
/*     */           
/*  85 */           float endY = pixmap.getEndY();
/*  86 */           float endX = pixmap.getEndX();
/*     */           
/*  88 */           float startX = pixmap.getStartX();
/*  89 */           float startY = pixmap.getStartY();
/*     */           
/*  91 */           gl.texCoord(startX, endY);
/*  92 */           gl.vertex(localX, localY);
/*     */           
/*  94 */           gl.texCoord(startX, startY);
/*  95 */           gl.vertex(localX, (imgHeight + localY));
/*     */           
/*  97 */           gl.texCoord(endX, startY);
/*  98 */           gl.vertex((imgWidth + localX), (imgHeight + localY));
/*     */           
/* 100 */           gl.texCoord(endX, endY);
/* 101 */           gl.vertex((imgWidth + localX), localY);
/*     */           
/* 103 */           localX += pixmap.getCharWidth();
/*     */         }  
/*     */     } 
/* 106 */     gl.end();
/* 107 */     gl.enableTexture2D(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/* 112 */     this.text = text;
/*     */     
/* 114 */     this.width = -1;
/* 115 */     this.height = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public Font getFont() {
/* 120 */     return this.font;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/* 125 */     this.width = -1;
/* 126 */     this.height = -1;
/*     */     
/* 128 */     this.font = font;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 133 */     if (this.text == null || this.text.length() == 0) return 0;
/*     */     
/* 135 */     if (this.height == -1) {
/*     */       
/* 137 */       String[] split = this.text.split("\n");
/* 138 */       this.height = split.length * this.font.getHeight();
/*     */     } 
/*     */     
/* 141 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 146 */     if (this.text == null || this.text.length() == 0) return 0;
/*     */     
/* 148 */     if (this.width == -1) {
/*     */       
/* 150 */       String[] split = this.text.split("\n");
/* 151 */       for (int i = 0; i < split.length; i++)
/*     */       {
/* 153 */         this.width = Math.max(this.width, this.font.getWidth(split[i]));
/*     */       }
/*     */     } 
/*     */     
/* 157 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderCarret(int x, int y, int charIndex, ICarretRenderer carret, Graphics g, IOpenGL gl) {
/* 162 */     if (this.text == null || this.text.length() == 0)
/*     */       return; 
/* 164 */     if (charIndex < 0 || carret == null || charIndex > this.text.length() + 1)
/*     */       return; 
/* 166 */     int localX = x + g.getTranslation().getX();
/* 167 */     int localY = y + g.getTranslation().getY() + getHeight() - this.font.getHeight();
/*     */     
/* 169 */     CharacterPixmap pixmap = null;
/*     */     
/* 171 */     for (int i = 0; i <= charIndex; i++) {
/*     */       
/* 173 */       if (i >= this.text.length())
/*     */         break; 
/* 175 */       char c = this.text.charAt(i);
/* 176 */       if (c != '\r' && c != '\f' && c != '\t')
/* 177 */         if (c == '\n') {
/*     */           
/* 179 */           localY -= this.font.getHeight();
/* 180 */           localX = x + g.getTranslation().getX();
/*     */         } else {
/*     */           
/* 183 */           pixmap = getFont().getCharPixMap(c);
/*     */           
/* 185 */           localX += pixmap.getCharWidth();
/*     */         }  
/*     */     } 
/* 188 */     carret.render(localX - g.getTranslation().getX(), localY - g.getTranslation().getY(), g);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\render\DirectTextRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */