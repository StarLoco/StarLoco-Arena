/*     */ package org.fenggui.render.jogl;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ComponentColorModel;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.media.opengl.GL;
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
/*     */ class JOGLTextureLoader
/*     */ {
/*  41 */   private HashMap table = new HashMap<Object, Object>();
/*     */ 
/*     */   
/*     */   public GL gl;
/*     */ 
/*     */   
/*     */   private ColorModel glAlphaColorModel;
/*     */ 
/*     */   
/*     */   private ColorModel glColorModel;
/*     */ 
/*     */   
/*     */   public GL getGL() {
/*  54 */     return this.gl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JOGLTextureLoader(GL gl) {
/*  65 */     this.gl = gl;
/*     */     
/*  67 */     this.glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(1000), 
/*  68 */         new int[] { 8, 8, 8, 8 }, true, false, 3, 0);
/*     */     
/*  70 */     this.glColorModel = new ComponentColorModel(ColorSpace.getInstance(1000), new int[] { 8, 8, 8
/*  71 */         }, false, false, 1, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int createTextureID() {
/*  81 */     int[] tmp = new int[1];
/*  82 */     this.gl.glGenTextures(1, tmp, 0);
/*  83 */     return tmp[0];
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
/*     */ 
/*     */   
/*     */   public JOGLTexture getTexture(String resourceName) throws IOException {
/*  98 */     JOGLTexture tex = (JOGLTexture)this.table.get(resourceName);
/*     */     
/* 100 */     if (tex != null)
/*     */     {
/* 102 */       return tex;
/*     */     }
/*     */     
/* 105 */     tex = getTexture(resourceName, 3553, 
/* 106 */         6408, 
/* 107 */         9729, 
/* 108 */         9729);
/*     */     
/* 110 */     this.table.put(resourceName, tex);
/*     */     
/* 112 */     return tex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JOGLTexture getTexture(InputStream is) throws IOException {
/* 123 */     return getTexture(is, 
/* 124 */         3553, 
/*     */         
/* 126 */         6408, 
/*     */         
/* 128 */         9729, 
/*     */         
/* 130 */         9729);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private JOGLTexture getTexture(InputStream is, int target, int dstPixelFormat, int minFilter, int magFilter) throws IOException {
/* 136 */     int srcPixelFormat = 0;
/*     */ 
/*     */     
/* 139 */     int textureID = createTextureID();
/* 140 */     JOGLTexture texture = new JOGLTexture(this.gl, target, textureID);
/*     */ 
/*     */     
/* 143 */     this.gl.glBindTexture(target, textureID);
/*     */     
/* 145 */     BufferedImage bufferedImage = copyImage(loadImage(is));
/* 146 */     texture.setWidth(bufferedImage.getWidth());
/* 147 */     texture.setHeight(bufferedImage.getHeight());
/*     */     
/* 149 */     if (bufferedImage.getColorModel().hasAlpha()) {
/*     */       
/* 151 */       srcPixelFormat = 6408;
/* 152 */       texture.setAlpha(true);
/*     */     }
/*     */     else {
/*     */       
/* 156 */       srcPixelFormat = 6407;
/*     */     } 
/*     */ 
/*     */     
/* 160 */     ByteBuffer textureBuffer = convertImageData(bufferedImage, texture);
/*     */     
/* 162 */     if (target == 3553) {
/*     */       
/* 164 */       this.gl.glTexParameteri(target, 10241, minFilter);
/* 165 */       this.gl.glTexParameteri(target, 10240, magFilter);
/* 166 */       this.gl.glTexParameteri(3553, 10242, 10497);
/* 167 */       this.gl.glTexParameteri(3553, 10243, 10497);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 172 */     this.gl.glTexImage2D(target, 0, dstPixelFormat, get2Fold(bufferedImage.getWidth()), 
/* 173 */         get2Fold(bufferedImage.getHeight()), 0, srcPixelFormat, 5121, textureBuffer);
/*     */     
/* 175 */     return texture;
/*     */   }
/*     */ 
/*     */   
/*     */   public JOGLTexture getTexture(BufferedImage bi) {
/* 180 */     char c1 = '෡';
/* 181 */     char c2 = 'ᤈ';
/* 182 */     char c3 = '☁';
/* 183 */     char c4 = '☁';
/* 184 */     int srcPixelFormat = 0;
/*     */ 
/*     */     
/* 187 */     int textureID = createTextureID();
/* 188 */     JOGLTexture texture = new JOGLTexture(this.gl, 3553, textureID);
/*     */ 
/*     */     
/* 191 */     this.gl.glBindTexture(3553, textureID);
/*     */     
/* 193 */     BufferedImage bufferedImage = bi;
/* 194 */     texture.setWidth(bufferedImage.getWidth());
/* 195 */     texture.setHeight(bufferedImage.getHeight());
/*     */     
/* 197 */     if (bufferedImage.getColorModel().hasAlpha()) {
/*     */       
/* 199 */       srcPixelFormat = 6408;
/* 200 */       texture.setAlpha(true);
/*     */     }
/*     */     else {
/*     */       
/* 204 */       srcPixelFormat = 6407;
/*     */     } 
/*     */ 
/*     */     
/* 208 */     ByteBuffer textureBuffer = convertImageData(bufferedImage, texture);
/*     */ 
/*     */ 
/*     */     
/* 212 */     this.gl.glTexParameteri(3553, 10241, 9729);
/* 213 */     this.gl.glTexParameteri(3553, 10240, 9729);
/*     */ 
/*     */ 
/*     */     
/* 217 */     this.gl.glTexImage2D(3553, 0, 6408, get2Fold(bufferedImage.getWidth()), 
/* 218 */         get2Fold(bufferedImage.getHeight()), 0, srcPixelFormat, 5121, textureBuffer);
/*     */     
/* 220 */     return texture;
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
/*     */   public JOGLTexture getTexture(String resourceName, int target, int dstPixelFormat, int minFilter, int magFilter) throws IOException {
/* 244 */     return getTexture(new FileInputStream(resourceName), 
/* 245 */         target, 
/* 246 */         dstPixelFormat, 
/* 247 */         minFilter, 
/* 248 */         magFilter);
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
/*     */   private int get2Fold(int fold) {
/* 260 */     int ret = 2;
/* 261 */     while (ret < fold)
/*     */     {
/* 263 */       ret *= 2;
/*     */     }
/* 265 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BufferedImage copyImage(BufferedImage src) {
/* 276 */     return src;
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
/*     */   private ByteBuffer convertImageData(BufferedImage bufferedImage, JOGLTexture texture) {
/*     */     BufferedImage texImage;
/* 321 */     ByteBuffer imageBuffer = null;
/*     */ 
/*     */ 
/*     */     
/* 325 */     int texWidth = 2;
/* 326 */     int texHeight = 2;
/*     */ 
/*     */ 
/*     */     
/* 330 */     while (texWidth < bufferedImage.getWidth())
/*     */     {
/* 332 */       texWidth *= 2;
/*     */     }
/* 334 */     while (texHeight < bufferedImage.getHeight())
/*     */     {
/* 336 */       texHeight *= 2;
/*     */     }
/*     */     
/* 339 */     texture.setTextureHeight(texHeight);
/* 340 */     texture.setTextureWidth(texWidth);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 345 */     if (bufferedImage.getColorModel().hasAlpha()) {
/*     */       
/* 347 */       WritableRaster raster = Raster.createInterleavedRaster(0, texWidth, texHeight, 4, null);
/* 348 */       texImage = new BufferedImage(this.glAlphaColorModel, raster, false, new Hashtable<Object, Object>());
/*     */     }
/*     */     else {
/*     */       
/* 352 */       WritableRaster raster = Raster.createInterleavedRaster(0, texWidth, texHeight, 3, null);
/* 353 */       texImage = new BufferedImage(this.glColorModel, raster, false, new Hashtable<Object, Object>());
/*     */     } 
/*     */ 
/*     */     
/* 357 */     Graphics g = texImage.getGraphics();
/* 358 */     g.setColor(new Color(0.0F, 0.0F, 0.0F, 0.0F));
/* 359 */     g.fillRect(0, 0, texWidth, texHeight);
/* 360 */     g.drawImage(bufferedImage, 0, 0, null);
/*     */ 
/*     */ 
/*     */     
/* 364 */     byte[] data = ((DataBufferByte)texImage.getRaster().getDataBuffer()).getData();
/*     */     
/* 366 */     imageBuffer = ByteBuffer.allocateDirect(data.length);
/* 367 */     imageBuffer.order(ByteOrder.nativeOrder());
/* 368 */     imageBuffer.put(data, 0, data.length);
/* 369 */     imageBuffer.rewind();
/* 370 */     return imageBuffer;
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
/*     */ 
/*     */   
/*     */   private BufferedImage loadImage(InputStream is) throws IOException {
/* 385 */     BufferedImage bi = ImageIO.read(is);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 390 */     return bi;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\render\jogl\JOGLTextureLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */