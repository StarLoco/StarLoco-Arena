/*     */ package com.ankamagames.baseImpl.graphicalClient.core.contentLoader;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentAccessor;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainer;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainerEventsHandler;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentEntry;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URL;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public abstract class ContentDocumentLoader
/*     */   implements DocumentAccessor, DocumentContainer, ContentInitializer
/*     */ {
/*  29 */   protected static final Logger m_logger = Logger.getLogger(ContentDocumentLoader.class);
/*     */   
/*     */   private String m_contentDocumentExtension;
/*     */   private ByteBuffer m_streamBuffer;
/*     */   
/*     */   public void open(String fileName) throws Exception {
/*  35 */     InputStream stream = null;
/*     */     
/*     */     try {
/*  38 */       URL jarUrl = new URL(fileName);
/*  39 */       stream = jarUrl.openStream();
/*  40 */     } catch (Exception e) {
/*     */ 
/*     */       
/*  43 */       File file = new File(fileName);
/*  44 */       stream = new FileInputStream(file);
/*     */     } 
/*     */     
/*  47 */     open(stream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void open(InputStream stream) throws Exception {
/*  57 */     int fileLength = stream.available();
/*     */     
/*  59 */     if (fileLength == 0) {
/*  60 */       stream.close();
/*     */       
/*     */       return;
/*     */     } 
/*  64 */     byte[] streamBuffer = new byte[fileLength];
/*     */     
/*  66 */     int bytesRead = stream.read(streamBuffer);
/*  67 */     if (bytesRead != fileLength) {
/*  68 */       stream.close();
/*     */       
/*     */       return;
/*     */     } 
/*  72 */     this.m_streamBuffer = ByteBuffer.wrap(streamBuffer);
/*  73 */     this.m_streamBuffer.order(ByteOrder.BIG_ENDIAN);
/*  74 */     this.m_streamBuffer.rewind();
/*     */     
/*  76 */     read(this);
/*     */     
/*  78 */     this.m_streamBuffer.clear();
/*  79 */     stream.close();
/*  80 */     close();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean readBoolean() {
/*  85 */     return (this.m_streamBuffer.get() != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readInteger() {
/*  90 */     return this.m_streamBuffer.getInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public float readFloat() {
/*  95 */     return this.m_streamBuffer.getFloat();
/*     */   }
/*     */ 
/*     */   
/*     */   public short readShort() {
/* 100 */     return this.m_streamBuffer.getShort();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte readByte() {
/* 105 */     return this.m_streamBuffer.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public String readString() {
/* 110 */     int stringLength = readInteger();
/* 111 */     byte[] stringBytes = new byte[stringLength];
/* 112 */     this.m_streamBuffer.get(stringBytes);
/*     */     
/*     */     try {
/* 115 */       return new String(stringBytes, "UTF-8");
/* 116 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*     */ 
/*     */       
/* 119 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int[] readIntegerArray() {
/* 124 */     int array[], arrayLength = readInteger();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 129 */       array = new int[arrayLength];
/* 130 */     } catch (OutOfMemoryError e) {
/* 131 */       m_logger.fatal("Taille du tableau alloué : " + arrayLength);
/* 132 */       throw e;
/*     */     } 
/*     */     
/* 135 */     for (int i = 0; i < arrayLength; i++) {
/* 136 */       array[i] = readInteger();
/*     */     }
/* 138 */     return array;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] readFloatArray() {
/* 143 */     int arrayLength = readInteger();
/*     */     
/* 145 */     float[] array = new float[arrayLength];
/* 146 */     for (int i = 0; i < arrayLength; i++) {
/* 147 */       array[i] = readFloat();
/*     */     }
/* 149 */     return array;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws Exception {}
/*     */   
/*     */   public String getContentDocumentExtension() {
/* 156 */     return this.m_contentDocumentExtension;
/*     */   }
/*     */   
/*     */   public void setContentDocumentExtension(String contentDocumentExtension) {
/* 160 */     this.m_contentDocumentExtension = contentDocumentExtension;
/*     */   }
/*     */   
/*     */   public DocumentContainer getNewDocumentContainer() {
/* 164 */     return null;
/*     */   }
/*     */   
/*     */   public DocumentEntry getEntryByName(String name) {
/* 168 */     return null;
/*     */   }
/*     */   
/*     */   public ArrayList<DocumentEntry> getEntriesByName(String name) {
/* 172 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyOnLoadBegin() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEventsHandler(DocumentContainerEventsHandler handler) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyOnLoadError(String errorMessage) {}
/*     */ 
/*     */   
/*     */   public void notifyOnSaveBegin() {}
/*     */ 
/*     */   
/*     */   public void notifyOnSaveComplete() {}
/*     */ 
/*     */   
/*     */   public void notifyOnSaveError(String errorMessage) {}
/*     */ 
/*     */   
/*     */   public boolean create(String fileName) throws Exception {
/* 198 */     return false;
/*     */   }
/*     */   
/*     */   public void write(DocumentContainer container) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\core\contentLoader\ContentDocumentLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */