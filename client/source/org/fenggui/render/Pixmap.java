/*     */ package org.fenggui.render;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.FengGUI;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOnlyStream;
/*     */ import org.fenggui.io.InputOutputStream;
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
/*     */   implements IOStreamSaveable
/*     */ {
/*  53 */   private ITexture texture = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private int x;
/*     */ 
/*     */ 
/*     */   
/*     */   private int y;
/*     */ 
/*     */   
/*     */   private int width;
/*     */ 
/*     */   
/*     */   private int height;
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap(ITexture tex) {
/*  72 */     this(tex, 0, 0, tex.getImageWidth(), tex.getImageHeight());
/*     */   }
/*     */ 
/*     */   
/*     */   public Pixmap(InputOnlyStream stream) throws IOException, IOStreamException {
/*  77 */     process((InputOutputStream)stream);
/*     */   }
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
/*     */   public Pixmap(ITexture texture, int x, int y, int width, int height) {
/*  90 */     this.texture = texture;
/*  91 */     this.x = x;
/*  92 */     this.y = y;
/*  93 */     this.width = width;
/*  94 */     this.height = height;
/*  95 */     setTexture(texture);
/*     */   }
/*     */ 
/*     */   
/*     */   public ITexture getTexture() {
/* 100 */     return this.texture;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getX() {
/* 105 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getY() {
/* 110 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 121 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 130 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 135 */     return "Pixmap pos: " + this.x + ", " + this.y + " size: " + this.width + ", " + this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setTexture(ITexture texture) {
/* 140 */     this.texture = texture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getEndX() {
/* 150 */     return (this.width + this.x) / this.texture.getTextureWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getEndY() {
/* 160 */     return (this.height + this.y) / this.texture.getTextureHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getStartX() {
/* 169 */     return this.x / this.texture.getTextureWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getStartY() {
/* 178 */     return this.y / this.texture.getTextureHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 188 */     if (stream.isInputStream()) {
/* 189 */       this.texture = (ITexture)stream.processChild(this.texture, FengGUI.TYPE_REGISTRY);
/*     */     }
/* 191 */     this.x = stream.processAttribute("x", this.x);
/* 192 */     this.y = stream.processAttribute("y", this.y);
/* 193 */     this.width = stream.processAttribute("width", this.width);
/* 194 */     this.height = stream.processAttribute("height", this.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUniqueName() {
/* 201 */     return "--generate-name--";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\render\Pixmap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */