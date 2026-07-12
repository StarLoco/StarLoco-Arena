/*     */ package com.ankamagames.framework.fileFormat.tag.records.tags;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
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
/*     */ public abstract class Tag
/*     */ {
/*     */   protected boolean m_forceLongHeader;
/*     */   private byte[] m_outData;
/*     */   protected short m_code;
/*     */   protected int m_length;
/*     */   
/*     */   public int getCode()
/*     */   {
/*  29 */     return this.m_code;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setCode(short code)
/*     */   {
/*  38 */     this.m_code = code;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getLength()
/*     */   {
/*  47 */     return this.m_length;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setLength(int length)
/*     */   {
/*  53 */     this.m_length = length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/*  62 */     return getClass().getSimpleName();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void writeData(OutputBitStream paramOutputBitStream)
/*     */     throws IOException;
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract void setData(byte[] paramArrayOfByte, short paramShort)
/*     */     throws IOException;
/*     */   
/*     */ 
/*     */ 
/*     */   void write(OutputBitStream stream)
/*     */     throws IOException
/*     */   {
/*  81 */     initData(stream);
/*  82 */     stream.writeBytes(getHeaderData());
/*  83 */     stream.writeBytes(this.m_outData);
/*     */   }
/*     */   
/*     */   private byte[] getHeaderData() throws IOException {
/*  87 */     OutputBitStream headerStream = new OutputBitStream();
/*  88 */     int typeAndLength = this.m_code << 6;
/*  89 */     this.m_length = this.m_outData.length;
/*     */     
/*  91 */     if ((this.m_forceLongHeader) || (this.m_length >= 63))
/*     */     {
/*  93 */       typeAndLength |= 0x3F;
/*  94 */       headerStream.writeUI16(typeAndLength);
/*  95 */       headerStream.writeUI32(this.m_length);
/*     */     }
/*     */     else {
/*  98 */       typeAndLength |= this.m_length;
/*  99 */       headerStream.writeUI16(typeAndLength);
/*     */     }
/* 101 */     return headerStream.getData();
/*     */   }
/*     */   
/*     */   private void initData(OutputBitStream parentStream) throws IOException {
/* 105 */     OutputBitStream outStream = new OutputBitStream();
/* 106 */     writeData(outStream);
/* 107 */     this.m_outData = outStream.getData();
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\tag\records\tags\Tag.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */