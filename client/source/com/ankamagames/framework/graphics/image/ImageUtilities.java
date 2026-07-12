/*     */ package com.ankamagames.framework.graphics.image;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.sba.records.Rect;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ComponentColorModel;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
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
/*     */ public class ImageUtilities
/*     */ {
/*     */   public static Rect getClipPixelRectFromAlpha(BufferedImage srcImage, int alphaMin) {
/*  33 */     int width = srcImage.getWidth();
/*  34 */     int height = srcImage.getHeight();
/*  35 */     int top = 0;
/*  36 */     int left = width;
/*  37 */     int right = 0;
/*  38 */     int bottom = height;
/*     */     
/*     */     int y;
/*  41 */     for (y = 0; y < bottom; y++) {
/*  42 */       for (int x = 0; x < left; x++) {
/*  43 */         int alpha = (srcImage.getRGB(x, y) & 0xFF000000) >> 24 & 0xFF;
/*  44 */         if (alpha > alphaMin) {
/*  45 */           left = x;
/*  46 */           if (top == 0) {
/*  47 */             top = y;
/*     */           }
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  55 */     for (y = height - 1; y > top; y--) {
/*  56 */       for (int x = width - 1; x > right; x--) {
/*  57 */         int alpha = (srcImage.getRGB(x, y) & 0xFF000000) >> 24 & 0xFF;
/*  58 */         if (alpha > alphaMin) {
/*  59 */           right = x;
/*  60 */           if (bottom == height) {
/*  61 */             bottom = y;
/*     */           }
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*  68 */     if (left == width || top == height)
/*     */     {
/*  70 */       return new Rect(0, 0, 0, 0); } 
/*  71 */     if (left >= right) {
/*  72 */       return new Rect(0, 0, width, height);
/*     */     }
/*  74 */     return new Rect(left, top, right, bottom);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage addAlphaImageBorder(BufferedImage srcImage, int borderSize) {
/*  85 */     BufferedImage newImage = new BufferedImage(srcImage.getWidth() + borderSize * 2, srcImage.getHeight() + borderSize * 2, 2);
/*  86 */     Graphics bg = newImage.getGraphics();
/*  87 */     bg.drawImage(srcImage, borderSize, borderSize, null);
/*  88 */     bg.dispose();
/*  89 */     return newImage;
/*     */   }
/*     */   
/*     */   public static BufferedImage convertToARGB(BufferedImage srcImage) {
/*  93 */     BufferedImage newImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), 2);
/*  94 */     Graphics bg = newImage.getGraphics();
/*  95 */     bg.drawImage(srcImage, 0, 0, null);
/*  96 */     bg.dispose();
/*  97 */     return newImage;
/*     */   }
/*     */   
/*     */   public static BufferedImage convertToARGB_PRE(BufferedImage srcImage) {
/* 101 */     BufferedImage newImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), 3);
/* 102 */     Graphics bg = newImage.getGraphics();
/* 103 */     bg.drawImage(srcImage, 0, 0, null);
/* 104 */     bg.dispose();
/* 105 */     return newImage;
/*     */   }
/*     */   
/*     */   public static BufferedImage toImage(int w, int h, byte[] data) {
/* 109 */     if (w == 0 || h == 0) {
/* 110 */       return null;
/*     */     }
/* 112 */     DataBuffer buffer = new DataBufferByte(data, w * h);
/*     */     
/* 114 */     int pixelStride = 4;
/* 115 */     int scanlineStride = 4 * w;
/* 116 */     int[] bandOffsets = { 0, 1, 2, 3 };
/* 117 */     WritableRaster raster = Raster.createInterleavedRaster(buffer, w, h, scanlineStride, pixelStride, bandOffsets, (Point)null);
/*     */     
/* 119 */     ColorSpace colorSpace = ColorSpace.getInstance(1000);
/* 120 */     boolean hasAlpha = true;
/* 121 */     boolean isAlphaPremultiplied = false;
/* 122 */     int transparency = 3;
/* 123 */     int transferType = 0;
/* 124 */     ColorModel colorModel = new ComponentColorModel(colorSpace, hasAlpha, isAlphaPremultiplied, transparency, transferType);
/*     */     
/* 126 */     return new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\image\ImageUtilities.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */