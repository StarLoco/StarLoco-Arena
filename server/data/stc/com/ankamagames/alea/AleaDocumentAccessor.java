/*     */ package com.ankamagames.alea;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentAccessor;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainer;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
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
/*     */ public abstract class AleaDocumentAccessor
/*     */   implements DocumentAccessor
/*     */ {
/*     */   protected ByteBuffer m_streamBuffer;
/*  30 */   protected static final int[] m_typesSize = {
/*     */   
/*  32 */     0, 1, 
/*  33 */     1, 
/*  34 */     1, 
/*  35 */     2, 
/*  36 */     2, 
/*  37 */     4, 
/*  38 */     4, 
/*  39 */     8, 
/*  40 */     4 };
/*     */   
/*     */   private String m_basePath;
/*     */   
/*     */   private String m_documentExtension;
/*     */   
/*     */   private byte m_aleaDocumentTypeCode;
/*     */   
/*     */   private byte m_aleaDocumentVersion;
/*     */   
/*     */   public String getBasePath()
/*     */   {
/*  52 */     return this.m_basePath;
/*     */   }
/*     */   
/*     */   public void setBasePath(String basePath) {
/*  56 */     this.m_basePath = basePath;
/*     */   }
/*     */   
/*     */   public String getDocumentExtension() {
/*  60 */     return this.m_documentExtension;
/*     */   }
/*     */   
/*     */   public void setDocumentExtension(String documentExtension) {
/*  64 */     this.m_documentExtension = documentExtension;
/*     */   }
/*     */   
/*     */   public byte getAleaDocumentTypeCode() {
/*  68 */     return this.m_aleaDocumentTypeCode;
/*     */   }
/*     */   
/*     */   public void setAleaDocumentTypeCode(byte aleaDocumentTypeCode) {
/*  72 */     this.m_aleaDocumentTypeCode = aleaDocumentTypeCode;
/*     */   }
/*     */   
/*     */   public byte getAleaDocumentVersion() {
/*  76 */     return this.m_aleaDocumentVersion;
/*     */   }
/*     */   
/*     */   public void setAleaDocumentVersion(byte aleaDocumentVersion) {
/*  80 */     this.m_aleaDocumentVersion = aleaDocumentVersion;
/*     */   }
/*     */   
/*     */   public void allocateBuffer(int bufferSize)
/*     */   {
/*  85 */     this.m_streamBuffer = ByteBuffer.allocate(2 + bufferSize);
/*     */   }
/*     */   
/*     */   public void open(String fileName) throws Exception {
/*  89 */     InputStream stream = null;
/*     */     try
/*     */     {
/*  92 */       URL jarUrl = new URL(fileName);
/*  93 */       stream = jarUrl.openStream();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  97 */       File file = new File(fileName);
/*  98 */       stream = new FileInputStream(file);
/*     */     }
/*     */     
/* 101 */     open(stream);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void open(InputStream stream)
/*     */     throws Exception
/*     */   {
/* 111 */     if (stream == null) { return;
/*     */     }
/* 113 */     int fileLength = stream.available();
/*     */     
/* 115 */     if (fileLength == 0) {
/* 116 */       stream.close();
/* 117 */       return;
/*     */     }
/*     */     
/* 120 */     byte[] streamBuffer = new byte[fileLength];
/*     */     
/* 122 */     int bytesRead = stream.read(streamBuffer);
/* 123 */     if (bytesRead != fileLength) {
/* 124 */       stream.close();
/* 125 */       return;
/*     */     }
/*     */     
/* 128 */     this.m_streamBuffer = ByteBuffer.wrap(streamBuffer);
/* 129 */     this.m_streamBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 130 */     this.m_streamBuffer.rewind();
/* 131 */     stream.close();
/* 132 */     close();
/*     */   }
/*     */   
/*     */   public boolean create(String fileName) throws Exception
/*     */   {
/* 137 */     return new File(fileName).createNewFile();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws Exception
/*     */   {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void read(DocumentContainer container)
/*     */   {
/* 156 */     readHeader(container);
/*     */   }
/*     */   
/*     */   public void readHeader(DocumentContainer container) {
/* 160 */     if (container == null) {
/* 161 */       return;
/*     */     }
/* 163 */     if (this.m_streamBuffer == null) {
/* 164 */       container.notifyOnLoadError("Unable to read ALEA file : streaBuffer is null");
/*     */     }
/*     */     else
/*     */     {
/* 168 */       container.notifyOnLoadBegin();
/*     */       
/* 170 */       this.m_streamBuffer.rewind();
/*     */       
/* 172 */       if (this.m_streamBuffer.get() != getAleaDocumentTypeCode()) {
/* 173 */         container.notifyOnLoadError("Bad ALEA [type:" + getAleaDocumentTypeCode() + "] file format");
/* 174 */       } else if (this.m_streamBuffer.get() != getAleaDocumentVersion()) {
/* 175 */         container.notifyOnLoadError("Bad ALEA [type:" + getAleaDocumentTypeCode() + "] file version");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(DocumentContainer container)
/*     */   {
/* 188 */     writeHeader(container);
/*     */   }
/*     */   
/*     */   public void writeHeader(DocumentContainer container) {
/* 192 */     if (container == null) {
/* 193 */       return;
/*     */     }
/* 195 */     if (this.m_streamBuffer == null) {
/* 196 */       container.notifyOnSaveError("streamBuffer not allocated");
/*     */     }
/*     */     
/* 199 */     container.notifyOnSaveBegin();
/* 200 */     if (this.m_streamBuffer.capacity() >= 2) {
/* 201 */       this.m_streamBuffer.put(getAleaDocumentTypeCode());
/* 202 */       this.m_streamBuffer.put(getAleaDocumentVersion());
/*     */     } else {
/* 204 */       container.notifyOnSaveError("streamBuffer not allocated");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\alea\AleaDocumentAccessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */