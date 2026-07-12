/*     */ package com.ankamagames.framework.graphics.sba.records;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*     */ import com.ankamagames.framework.graphics.image.AlphaBitmapData;
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
/*     */ public class Bitmap
/*     */ {
/*     */   public static final float LOSSLESS_QUALITY = 1.0F;
/*     */   public static final float DEFAULT_JPEG_QUALITY = 0.7F;
/*     */   protected Point m_hotPoint;
/*     */   protected AlphaBitmapData m_bitmapData;
/*     */   private float m_quality;
/*     */   
/*     */   public Bitmap(Point hotPoint, AlphaBitmapData bitmapData, float quality) {
/*  38 */     this.m_hotPoint = hotPoint;
/*  39 */     setBitmapData(bitmapData);
/*  40 */     this.m_quality = quality;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bitmap(Point hotPoint, AlphaBitmapData bitmapData) {
/*  50 */     this(hotPoint, bitmapData, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bitmap(InputBitStream inStream, short version) throws IOException {
/*  57 */     this.m_hotPoint = new Point(inStream);
/*  58 */     readBitmapData(inStream, version);
/*     */   }
/*     */   
/*     */   public Bitmap() {
/*  62 */     this.m_hotPoint = new Point(0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getQuality() {
/*  69 */     return this.m_quality;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setQuality(float quality) {
/*  76 */     this.m_quality = quality;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  83 */     if (this.m_bitmapData == null) {
/*  84 */       return 0;
/*     */     }
/*  86 */     return this.m_bitmapData.getWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  93 */     if (this.m_bitmapData == null) {
/*  94 */       return 0;
/*     */     }
/*  96 */     return this.m_bitmapData.getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAlphaPremultiplied() {
/* 104 */     return (this.m_bitmapData != null && this.m_bitmapData.isAlphaPremultiplied());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point getHotPoint() {
/* 111 */     return this.m_hotPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHotPoint(Point hotPoint) {
/* 118 */     this.m_hotPoint = hotPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AlphaBitmapData getBitmapData() {
/* 125 */     return this.m_bitmapData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBitmapData(AlphaBitmapData bitmapData) {
/* 132 */     this.m_bitmapData = bitmapData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(OutputBitStream outStream) throws IOException {
/* 142 */     this.m_hotPoint.write(outStream);
/* 143 */     writeBitmapData(outStream);
/*     */   }
/*     */   
/*     */   protected void writeBitmapData(OutputBitStream outStream) throws IOException {
/* 147 */     if (this.m_bitmapData != null) {
/* 148 */       outStream.writeUI8((short)(int)(this.m_quality * 100.0F));
/*     */       
/* 150 */       OutputBitStream zStream = new OutputBitStream();
/* 151 */       zStream.enableCompression();
/* 152 */       this.m_bitmapData.write(zStream);
/*     */       
/* 154 */       byte[] zData = zStream.getData();
/* 155 */       outStream.writeUI16(zData.length);
/* 156 */       outStream.writeBytes(zData);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void readBitmapData(InputBitStream inStream, short sbaversion) throws IOException {
/* 161 */     switch (sbaversion) {
/*     */       case 1:
/* 163 */         read1(inStream, sbaversion);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 2:
/* 168 */         read2(inStream);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 3:
/* 173 */         read3(inStream);
/*     */         return;
/*     */     } 
/*     */     
/* 177 */     System.err.println("SBA Version inconnue:" + sbaversion + " courante:" + '\003');
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
/*     */   protected void read1(InputBitStream inStream, short sbaversion) throws IOException {
/* 191 */     int quality = inStream.readUI8();
/* 192 */     int dataLength = inStream.readUI16();
/* 193 */     if (dataLength > 0) {
/* 194 */       this.m_bitmapData = AlphaBitmapData.OldVersionReader.read1(inStream, dataLength);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void read2(InputBitStream inStream) throws IOException {
/* 205 */     this.m_quality = inStream.readUI8() / 100.0F;
/* 206 */     int dataLength = inStream.readUI16();
/* 207 */     if (dataLength > 0) {
/* 208 */       InputBitStream zStream = new InputBitStream(inStream.readBytes(dataLength));
/* 209 */       zStream.enableCompression();
/* 210 */       this.m_bitmapData = AlphaBitmapData.OldVersionReader.read2(zStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void read3(InputBitStream inStream) throws IOException {
/* 221 */     this.m_quality = inStream.readUI8() / 100.0F;
/* 222 */     int dataLength = inStream.readUI16();
/* 223 */     if (dataLength > 0) {
/* 224 */       InputBitStream zStream = new InputBitStream(inStream.readBytes(dataLength));
/* 225 */       zStream.enableCompression();
/* 226 */       this.m_bitmapData = new AlphaBitmapData(zStream);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\Bitmap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */