/*     */ package com.ankamagames.framework.graphics.sba.records.tags;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*     */ import com.ankamagames.framework.graphics.image.AlphaBitmapData;
/*     */ import com.ankamagames.framework.graphics.sba.records.Bitmap;
/*     */ import com.ankamagames.framework.graphics.sba.records.Point;
/*     */ import java.io.IOException;
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
/*     */ public class DefineBitmap
/*     */   extends DefinitionTag
/*     */ {
/*     */   private Bitmap m_bitmap;
/*     */   private float m_invertScalingValue;
/*     */   
/*     */   public DefineBitmap(int identifier, Point hotPoint, AlphaBitmapData bitmapData, float quality, float invertScalingValue) {
/*  42 */     this(identifier);
/*  43 */     set(hotPoint, bitmapData, quality, invertScalingValue);
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
/*     */   public DefineBitmap(int identifier, Point hotPoint, AlphaBitmapData bitmapData, float quality) {
/*  55 */     this(identifier);
/*  56 */     set(hotPoint, bitmapData, quality, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefineBitmap(int identifier, Point hotPoint, AlphaBitmapData bitmapData) {
/*  67 */     this(identifier, hotPoint, bitmapData, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public DefineBitmap(int identifier) {
/*  71 */     this.m_code = 2;
/*  72 */     this.m_identifier = identifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DefineBitmap() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(Point hotPoint, AlphaBitmapData alphaBitmapData, float quality, float invertScaleFactor) {
/*  86 */     this.m_bitmap = new Bitmap(hotPoint, alphaBitmapData, quality);
/*  87 */     this.m_invertScalingValue = invertScaleFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getQuality() {
/*  94 */     return this.m_bitmap.getQuality();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getInvertScalingValue() {
/* 101 */     return this.m_invertScalingValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 108 */     return this.m_bitmap.getWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 115 */     return this.m_bitmap.getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point getHotPoint() {
/* 122 */     return this.m_bitmap.getHotPoint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AlphaBitmapData getBitmapData() {
/* 129 */     return this.m_bitmap.getBitmapData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(byte[] data, short version) throws IOException {
/* 139 */     InputBitStream inStream = readDefinitionTagHeader(data);
/* 140 */     this.m_invertScalingValue = inStream.readFloat16();
/* 141 */     this.m_bitmap = new Bitmap(inStream, version);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeData(OutputBitStream outStream) throws IOException {
/* 151 */     super.writeData(outStream);
/* 152 */     outStream.writeFloat16(this.m_invertScalingValue);
/* 153 */     if (this.m_bitmap != null) {
/* 154 */       this.m_bitmap.write(outStream);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean getAlphaPremultiplied() {
/* 159 */     if (this.m_bitmap != null) {
/* 160 */       return this.m_bitmap.isAlphaPremultiplied();
/*     */     }
/* 162 */     return false;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\tags\DefineBitmap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */