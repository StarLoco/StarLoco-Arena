/*     */ package com.ankamagames.framework.graphics.image;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import javax.imageio.ImageIO;
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
/*     */ public final class AlphaBitmapData
/*     */ {
/*     */   public static final byte CURRENT_VERSION = 1;
/*     */   
/*     */   public static class OldVersionReader
/*     */   {
/*     */     public static AlphaBitmapData read1(InputBitStream inStream, int dataLength) throws IOException {
/*  38 */       byte[] data = inStream.readBytes(dataLength);
/*  39 */       ByteArrayInputStream buffer = new ByteArrayInputStream(data);
/*  40 */       return new AlphaBitmapData(ImageIO.read(buffer), true);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static AlphaBitmapData read2(InputBitStream inStream) throws IOException {
/*  52 */       int width = inStream.readUI16();
/*  53 */       int height = inStream.readUI16();
/*  54 */       int length = (int)inStream.readUI32();
/*  55 */       byte[] datas = (byte[])null;
/*  56 */       if (length > 0) {
/*  57 */         datas = inStream.readBytes(length);
/*     */       }
/*  59 */       return new AlphaBitmapData(width, height, datas, null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean m_alphaPremultiplied = false;
/*     */ 
/*     */   
/*     */   private byte[] m_datas;
/*     */   
/*     */   private int m_width;
/*     */   
/*     */   private int m_height;
/*     */ 
/*     */   
/*     */   private AlphaBitmapData(int width, int height, byte[] data) {
/*  76 */     this.m_width = width;
/*  77 */     this.m_height = height;
/*  78 */     this.m_datas = data;
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
/*     */   public AlphaBitmapData(BufferedImage image, boolean premultAlpha) {
/*  90 */     fromBufferedImage(image, premultAlpha);
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
/*     */   public AlphaBitmapData(InputBitStream stream) throws IOException {
/* 104 */     read(stream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedImage toBufferedImage() {
/* 113 */     return ImageUtilities.toImage(this.m_width, this.m_height, this.m_datas);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fromBufferedImage(BufferedImage image, boolean premultAlpha) {
/* 124 */     if (image != null) {
/* 125 */       this.m_width = image.getWidth();
/* 126 */       this.m_height = image.getHeight();
/*     */       
/* 128 */       if (premultAlpha) {
/* 129 */         if (image.getType() != 3) {
/* 130 */           image = ImageUtilities.convertToARGB_PRE(image);
/*     */         
/*     */         }
/* 133 */         else if (image.getType() != 2) {
/* 134 */           image = ImageUtilities.convertToARGB(image);
/*     */         }
/*     */       
/*     */       }
/* 138 */       else if (image.getType() != 2) {
/* 139 */         image = ImageUtilities.convertToARGB(image);
/*     */       } 
/*     */ 
/*     */       
/* 143 */       this.m_alphaPremultiplied = image.isAlphaPremultiplied();
/*     */       
/* 145 */       DataBufferInt buffer = (DataBufferInt)image.getData().getDataBuffer();
/*     */       
/* 147 */       this.m_datas = new byte[this.m_width * this.m_height * 4];
/*     */       
/* 149 */       for (int i = 0; i < buffer.getSize(); i++) {
/* 150 */         int offset = 4 * i;
/* 151 */         int value = buffer.getElem(i);
/* 152 */         this.m_datas[offset] = (byte)(value >> 16 & 0xFF);
/* 153 */         this.m_datas[offset + 1] = (byte)(value >> 8 & 0xFF);
/* 154 */         this.m_datas[offset + 2] = (byte)(value & 0xFF);
/* 155 */         this.m_datas[offset + 3] = (byte)(value >> 24 & 0xFF);
/*     */       } 
/*     */     } else {
/*     */       
/* 159 */       this.m_width = 0;
/* 160 */       this.m_height = 0;
/* 161 */       this.m_datas = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getDatas() {
/* 172 */     return this.m_datas;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 180 */     return this.m_height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 188 */     return this.m_width;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 193 */     return "AlphaBitmapData (" + this.m_width + "x" + this.m_height + ") @" + Integer.toHexString(hashCode());
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
/*     */   public void write(OutputBitStream stream) throws IOException {
/* 205 */     stream.writeUI8((short)1);
/* 206 */     stream.writeBooleanBit(this.m_alphaPremultiplied);
/*     */     
/* 208 */     stream.writeUI16(this.m_width);
/* 209 */     stream.writeUI16(this.m_height);
/*     */     
/* 211 */     if (this.m_datas != null) {
/* 212 */       stream.writeUI32(this.m_datas.length);
/* 213 */       stream.writeBytes(this.m_datas);
/*     */     } else {
/* 215 */       stream.writeUI32(0L);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(InputBitStream inStream) throws IOException {
/* 227 */     short version = inStream.readUI8();
/*     */     
/* 229 */     if (version != 1) {
/* 230 */       (new Exception("Version incorrecte:" + version + " courante:" + '\001')).printStackTrace();
/*     */     }
/*     */     
/* 233 */     this.m_alphaPremultiplied = inStream.readBooleanBit();
/* 234 */     this.m_width = inStream.readUI16();
/* 235 */     this.m_height = inStream.readUI16();
/* 236 */     int length = (int)inStream.readUI32();
/* 237 */     if (length > 0) {
/* 238 */       this.m_datas = inStream.readBytes(length);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void premultAlpha() {
/* 247 */     if (this.m_datas != null && !this.m_alphaPremultiplied) {
/* 248 */       this.m_alphaPremultiplied = true;
/* 249 */       for (int i = 0; i < this.m_datas.length; i += 4) {
/* 250 */         byte a = this.m_datas[i + 3];
/*     */         
/* 252 */         this.m_datas[i] = (byte)(this.m_datas[i] * a / 255);
/* 253 */         this.m_datas[i + 1] = (byte)(this.m_datas[i + 1] * a / 255);
/* 254 */         this.m_datas[i + 2] = (byte)(this.m_datas[i + 2] * a / 255);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void demultiplyAlpha() {
/* 261 */     if (this.m_datas != null && this.m_alphaPremultiplied) {
/* 262 */       this.m_alphaPremultiplied = false;
/* 263 */       for (int i = 0; i < this.m_datas.length; i += 4) {
/* 264 */         byte a = this.m_datas[i + 3];
/* 265 */         if (a != 0) {
/* 266 */           this.m_datas[i] = (byte)(this.m_datas[i] * 255 / a);
/* 267 */           this.m_datas[i + 1] = (byte)(this.m_datas[i + 1] * 255 / a);
/* 268 */           this.m_datas[i + 2] = (byte)(this.m_datas[i + 2] * 255 / a);
/*     */         } else {
/* 270 */           this.m_datas[i] = -1;
/* 271 */           this.m_datas[i + 1] = -1;
/* 272 */           this.m_datas[i + 2] = -1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAlphaPremultiplied() {
/* 283 */     return this.m_alphaPremultiplied;
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
/*     */   public double getAlphaValue(int x, int y) {
/* 295 */     if (x >= this.m_width || y >= this.m_height || this.m_datas == null) {
/* 296 */       return 0.0D;
/*     */     }
/* 298 */     byte a = this.m_datas[4 * (x + y * this.m_width) + 3];
/* 299 */     return a / 255.0D;
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
/*     */   public boolean equals(Object alphaBitmapData) {
/* 311 */     if (this == alphaBitmapData) {
/* 312 */       return true;
/*     */     }
/* 314 */     if (alphaBitmapData instanceof AlphaBitmapData) {
/* 315 */       AlphaBitmapData image = (AlphaBitmapData)alphaBitmapData;
/* 316 */       if (getWidth() != image.getWidth() || getHeight() != image.getHeight()) {
/* 317 */         return false;
/*     */       }
/* 319 */       byte[] data1 = getDatas();
/* 320 */       byte[] data2 = image.getDatas();
/*     */       
/* 322 */       for (int i = 0; i < data1.length; i++) {
/*     */         
/* 324 */         if (data1[i] != data2[i]) {
/* 325 */           return false;
/*     */         }
/*     */       } 
/* 328 */       return true;
/*     */     } 
/* 330 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 341 */     assert false : "Pas d'insertion possible en tant que clef dans une HashMap/HashTable";
/* 342 */     return super.hashCode();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\image\AlphaBitmapData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */