/*     */ package com.ankamagames.framework.graphics.opengl.base;
/*     */ 
/*     */ import com.sun.opengl.util.texture.TextureCoords;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.Raster;
/*     */ import java.nio.ByteBuffer;
/*     */ import javax.media.opengl.GL;
/*     */ import javax.media.opengl.glu.GLU;
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
/*     */ public class Texture
/*     */ {
/*     */   private int m_target;
/*     */   private int m_textureID;
/*     */   private int m_width;
/*     */   private int m_height;
/*     */   private int m_imageWidth;
/*     */   private int m_imageHeight;
/*     */   private TextureCoords m_texCoords;
/*     */   private byte[] m_alphaDatas;
/*     */   
/*     */   public Texture(int target, int width, int height, ByteBuffer datas, int storedFormat, boolean bFlipVerticaly, int magFilter, int minFilter, int wrapS, int wrapT)
/*     */   {
/*  51 */     initialize(target, width, height, datas, storedFormat, bFlipVerticaly, magFilter, minFilter, wrapS, wrapT);
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
/*     */   public Texture(int target, int width, int height, ByteBuffer datas, int storedFormat, boolean bFlipVerticaly)
/*     */   {
/*  65 */     this(target, width, height, datas, storedFormat, bFlipVerticaly, 9729, 9728, 10496, 10496);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Texture(int target, BufferedImage bufferedImage, int storedFormat, boolean bFlipVerticaly)
/*     */   {
/*     */     DataBufferByte dbuf;
/*     */     
/*     */ 
/*     */ 
/*     */     DataBufferByte dbuf;
/*     */     
/*     */ 
/*  80 */     if (bufferedImage.getType() != 7) {
/*  81 */       BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), 7);
/*  82 */       newBufferedImage.getGraphics().drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
/*     */       
/*  84 */       dbuf = (DataBufferByte)newBufferedImage.getData().getDataBuffer();
/*     */     } else {
/*  86 */       dbuf = (DataBufferByte)bufferedImage.getData().getDataBuffer();
/*     */     }
/*  88 */     initialize(target, bufferedImage.getWidth(), bufferedImage.getHeight(), ByteBuffer.wrap(dbuf.getData()), storedFormat, bFlipVerticaly, 9728, 9728, 10496, 10496);
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
/*     */   private void initialize(int target, int width, int height, ByteBuffer datas, int storedFormat, boolean bFlipVerticaly, int magFilter, int minFilter, int wrapS, int wrapT)
/*     */   {
/* 107 */     GL gl = GLU.getCurrentGL();
/*     */     
/*     */ 
/* 110 */     this.m_target = target;
/* 111 */     this.m_imageWidth = width;
/* 112 */     this.m_imageHeight = height;
/* 113 */     this.m_width = nearestPowOfTwo(width);
/* 114 */     this.m_height = nearestPowOfTwo(height);
/*     */     
/* 116 */     float rw = width / this.m_width;
/* 117 */     float rh = height / this.m_height;
/*     */     
/*     */ 
/* 120 */     int[] id = new int[1];
/* 121 */     gl.glGenTextures(1, id, 0);
/* 122 */     this.m_textureID = id[0];
/*     */     
/*     */ 
/* 125 */     int lineSize = 4 * this.m_width;
/*     */     
/* 127 */     ByteBuffer buffer = ByteBuffer.allocate(lineSize * this.m_height);
/* 128 */     ByteBuffer input = datas;
/* 129 */     byte[] line = new byte[width * 4];
/* 130 */     byte[] linef = new byte[(this.m_width - width) * 4];
/*     */     
/*     */ 
/* 133 */     if (bFlipVerticaly) {
/* 134 */       int offset = lineSize * height;
/* 135 */       for (int y = 0; y < height; y++) {
/* 136 */         offset -= lineSize;
/* 137 */         input.get(line);
/* 138 */         buffer.position(offset);
/* 139 */         buffer.put(line);
/*     */       }
/*     */     }
/*     */     else {
/* 143 */       for (int y = 0; y < height; y++) {
/* 144 */         input.get(line);
/* 145 */         buffer.put(line);
/* 146 */         buffer.put(linef);
/*     */       }
/*     */     }
/*     */     
/* 150 */     buffer.rewind();
/*     */     
/* 152 */     int offset = 0;
/* 153 */     switch (storedFormat) {
/*     */     case 6408: 
/*     */     case 32993: 
/* 156 */       offset = 3;
/* 157 */       break;
/*     */     
/*     */     case 32768: 
/* 160 */       offset = 0;
/*     */     }
/*     */     
/*     */     
/* 164 */     int size = this.m_height * this.m_width;
/* 165 */     this.m_alphaDatas = new byte[size];
/* 166 */     for (int i = 0; i < size; i++) {
/* 167 */       this.m_alphaDatas[i] = buffer.get(i * 4 + offset);
/*     */     }
/*     */     
/* 170 */     buffer.rewind();
/*     */     
/* 172 */     bind();
/*     */     
/* 174 */     gl.glTexImage2D(this.m_target, 0, 4, this.m_width, this.m_height, 0, storedFormat, 5121, buffer);
/*     */     
/* 176 */     gl.glTexParameterf(3553, 10242, wrapS);
/* 177 */     gl.glTexParameterf(3553, 10243, wrapT);
/* 178 */     gl.glTexParameterf(3553, 10240, magFilter);
/* 179 */     gl.glTexParameterf(3553, 10241, minFilter);
/*     */     
/* 181 */     this.m_texCoords = new TextureCoords(0.0F, rh, rw, 0.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int nearestPowOfTwo(int value)
/*     */   {
/* 190 */     value--;
/* 191 */     value |= value >> 1;
/* 192 */     value |= value >> 2;
/* 193 */     value |= value >> 4;
/* 194 */     value |= value >> 8;
/* 195 */     value |= value >> 16;
/* 196 */     value++;return value;
/*     */   }
/*     */   
/*     */   public void bind() {
/* 200 */     GLU.getCurrentGL().glBindTexture(this.m_target, this.m_textureID);
/*     */   }
/*     */   
/*     */   public void enable() {
/* 204 */     GLU.getCurrentGL().glEnable(this.m_target);
/*     */   }
/*     */   
/*     */   public void disable() {
/* 208 */     GLU.getCurrentGL().glDisable(this.m_target);
/*     */   }
/*     */   
/*     */   public TextureCoords getImageTexCoords() {
/* 212 */     return this.m_texCoords;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 216 */     return this.m_width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 220 */     return this.m_height;
/*     */   }
/*     */   
/*     */   public int getImageWidth() {
/* 224 */     return this.m_imageWidth;
/*     */   }
/*     */   
/*     */   public int getImageHeight() {
/* 228 */     return this.m_imageHeight;
/*     */   }
/*     */   
/*     */   public int getTarget() {
/* 232 */     return this.m_target;
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 236 */     GLU.getCurrentGL().glDeleteTextures(1, new int[] { this.m_textureID }, 0);
/* 237 */     this.m_target = 0;
/* 238 */     this.m_textureID = 0;
/* 239 */     this.m_width = 0;
/* 240 */     this.m_height = 0;
/* 241 */     this.m_imageWidth = 0;
/* 242 */     this.m_imageHeight = 0;
/*     */     
/* 244 */     this.m_texCoords = null;
/* 245 */     this.m_alphaDatas = null;
/*     */   }
/*     */   
/*     */   public int getTextureObject() {
/* 249 */     return this.m_textureID;
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
/*     */   public byte getAlpha(int x, int y)
/*     */   {
/* 262 */     if (this.m_alphaDatas != null) {
/* 263 */       int pos = y * this.m_width + x;
/* 264 */       if ((pos >= 0) && (pos < this.m_alphaDatas.length))
/* 265 */         return this.m_alphaDatas[pos];
/*     */     }
/* 267 */     return 0;
/*     */   }
/*     */   
/*     */   public long getImageDataSize() {
/* 271 */     return this.m_alphaDatas.length;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\Texture.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */