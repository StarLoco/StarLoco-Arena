/*     */ package org.fenggui.render.jogl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.media.opengl.GL;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.InputOnlyStream;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.render.Binding;
/*     */ import org.fenggui.render.ITexture;
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
/*     */ public class JOGLTexture
/*     */   implements ITexture
/*     */ {
/*     */   private int target;
/*     */   private int textureID;
/*     */   private int height;
/*     */   private int width;
/*     */   private int texWidth;
/*     */   private int texHeight;
/*     */   private float widthRatio;
/*     */   private float heightRatio;
/*     */   private GL gl;
/*     */   private boolean alpha = false;
/*     */   
/*     */   protected JOGLTexture(GL gl, int target, int textureID) {
/*  53 */     this.target = target;
/*  54 */     this.textureID = textureID;
/*  55 */     this.gl = gl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JOGLTexture(InputOnlyStream stream) throws IOException, IOStreamException {
/*  66 */     process((InputOutputStream)stream);
/*     */   }
/*     */ 
/*     */   
/*     */   private void set(JOGLTexture t) {
/*  71 */     this.gl = t.gl;
/*  72 */     this.alpha = t.alpha;
/*  73 */     this.height = t.height;
/*  74 */     this.heightRatio = t.heightRatio;
/*  75 */     this.target = t.target;
/*  76 */     this.texHeight = t.texHeight;
/*  77 */     this.textureID = t.textureID;
/*  78 */     this.texWidth = t.texWidth;
/*  79 */     this.width = t.width;
/*  80 */     this.widthRatio = t.widthRatio;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind() {
/*  87 */     this.gl.glBindTexture(this.target, this.textureID);
/*     */   }
/*     */   
/*     */   public void delete() {
/*  91 */     this.gl.glDeleteTextures(1, new int[] { this.textureID }, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeight(int height) {
/* 101 */     this.height = height;
/* 102 */     setHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWidth(int width) {
/* 111 */     this.width = width;
/* 112 */     setWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getImageHeight() {
/* 121 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getImageWidth() {
/* 130 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeight() {
/* 139 */     return this.heightRatio;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth() {
/* 148 */     return this.widthRatio;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextureHeight(int texHeight) {
/* 157 */     this.texHeight = texHeight;
/* 158 */     setHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextureWidth(int texWidth) {
/* 167 */     this.texWidth = texWidth;
/* 168 */     setWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setHeight() {
/* 176 */     if (this.texHeight != 0) {
/* 177 */       this.heightRatio = this.height / this.texHeight;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setWidth() {
/* 186 */     if (this.texWidth != 0) {
/* 187 */       this.widthRatio = this.width / this.texWidth;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/* 195 */     return this.target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextureWidth() {
/* 202 */     return this.texWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextureHeight() {
/* 209 */     return this.texHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAlpha(boolean alpha) {
/* 214 */     this.alpha = alpha;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAlpha() {
/* 219 */     return this.alpha;
/*     */   }
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 224 */     if (stream.isInputStream()) {
/*     */       
/* 226 */       String filename = stream.processAttribute("filename", "filename");
/*     */       
/* 228 */       filename = String.valueOf(((InputOnlyStream)stream).getResourcePath()) + filename;
/*     */       
/* 230 */       set((JOGLTexture)Binding.getInstance().getTexture(filename));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUniqueName() {
/* 238 */     return "--generate-name--";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\render\jogl\JOGLTexture.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */