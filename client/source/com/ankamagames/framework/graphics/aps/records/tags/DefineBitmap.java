/*     */ package com.ankamagames.framework.graphics.aps.records.tags;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
/*     */ import com.ankamagames.framework.graphics.image.AlphaBitmapData;
/*     */ import java.awt.image.BufferedImage;
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
/*     */ public class DefineBitmap
/*     */   extends Tag
/*     */ {
/*     */   private AlphaBitmapData m_alphaBitmapData;
/*     */   private int m_bitmapId;
/*     */   
/*     */   protected DefineBitmap() {}
/*     */   
/*     */   public DefineBitmap(int id, AlphaBitmapData image) {
/*  33 */     this.m_code = 3;
/*     */     
/*  35 */     this.m_alphaBitmapData = image;
/*  36 */     this.m_bitmapId = id;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeData(OutputBitStream outStream) throws IOException {
/*  41 */     outStream.writeUI16(this.m_bitmapId);
/*     */     
/*  43 */     if (this.m_alphaBitmapData != null) {
/*  44 */       OutputBitStream zStream = new OutputBitStream();
/*  45 */       zStream.enableCompression();
/*  46 */       this.m_alphaBitmapData.write(zStream);
/*     */       
/*  48 */       byte[] data = zStream.getData();
/*  49 */       outStream.writeUI32(data.length);
/*  50 */       outStream.writeBytes(data);
/*  51 */       zStream.close();
/*     */     } else {
/*  53 */       outStream.writeUI32(0L);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(byte[] data, short version) throws IOException {
/*     */     byte[] bitmapData;
/*     */     long length;
/*     */     ByteArrayInputStream buffer;
/*     */     BufferedImage img;
/*  64 */     InputBitStream inStream = new InputBitStream(data);
/*  65 */     this.m_bitmapId = inStream.readUI16();
/*  66 */     switch (version) {
/*     */       
/*     */       case 1:
/*  69 */         bitmapData = inStream.readBytes(data.length - 2);
/*  70 */         buffer = new ByteArrayInputStream(bitmapData);
/*  71 */         img = ImageIO.read(buffer);
/*     */         
/*  73 */         this.m_alphaBitmapData = new AlphaBitmapData(img, true);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 2:
/*  78 */         length = inStream.readUI32();
/*  79 */         if (length > 0L) {
/*  80 */           byte[] datas = inStream.readBytes((int)length);
/*  81 */           InputBitStream zStream = new InputBitStream(datas);
/*  82 */           zStream.enableCompression();
/*     */           
/*  84 */           this.m_alphaBitmapData = new AlphaBitmapData(zStream);
/*  85 */           zStream.close();
/*     */         } 
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/*  91 */     System.err.println("Particle Version inconnue: " + version + " courante: " + '\002');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBitmapId() {
/*  99 */     return this.m_bitmapId;
/*     */   }
/*     */   
/*     */   public AlphaBitmapData getAlphaBitmap() {
/* 103 */     return this.m_alphaBitmapData;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\records\tags\DefineBitmap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */