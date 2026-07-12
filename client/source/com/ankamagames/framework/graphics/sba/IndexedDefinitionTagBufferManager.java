/*     */ package com.ankamagames.framework.graphics.sba;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.collections.ByteArray;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IndexedDefinitionTagBufferManager
/*     */ {
/*     */   public static final int BUFFER_SIZE = 2097152;
/*  26 */   protected static final Logger m_logger = Logger.getLogger(IndexedDefinitionTagBufferManager.class);
/*     */   
/*  28 */   private static final IndexedDefinitionTagBufferManager m_instance = new IndexedDefinitionTagBufferManager();
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
/*     */   public static IndexedDefinitionTagBufferManager getInstance() {
/*  40 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IndexedDefinitionTagBuffer getIndexedBuffer(String fileName) {
/*  51 */     InputStream stream = null;
/*     */     
/*     */     try {
/*  54 */       URL jarUrl = new URL(fileName);
/*  55 */       stream = jarUrl.openStream();
/*  56 */     } catch (Exception e) {
/*     */ 
/*     */       
/*  59 */       File file = new File(fileName);
/*     */       try {
/*  61 */         stream = new FileInputStream(file);
/*  62 */       } catch (FileNotFoundException e1) {
/*  63 */         m_logger.error("Fichier " + fileName + " introuvable ou illisible");
/*  64 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/*  69 */       return getIndexedBuffer(stream);
/*  70 */     } catch (Exception e) {
/*  71 */       System.err.println("Erreur avec le fichier " + fileName);
/*  72 */       e.printStackTrace();
/*     */ 
/*     */       
/*  75 */       return null;
/*     */     } 
/*     */   }
/*     */   public IndexedDefinitionTagBuffer getIndexedBuffer(File file) throws Exception {
/*  79 */     return getIndexedBuffer(new FileInputStream(file));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IndexedDefinitionTagBuffer getIndexedBuffer(InputStream stream) throws Exception {
/*  88 */     ByteArray array = new ByteArray(2097152, 2097152);
/*     */     
/*  90 */     byte[] buffer = new byte[2097152];
/*  91 */     int bytesRead = 0;
/*     */     
/*  93 */     while (bytesRead != -1) {
/*  94 */       bytesRead = stream.read(buffer, 0, buffer.length);
/*  95 */       if (bytesRead > 0) {
/*  96 */         array.put(buffer, bytesRead);
/*     */       }
/*     */     } 
/*  99 */     return getIndexedBuffer(array.internalArray());
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
/*     */   public IndexedDefinitionTagBuffer getIndexedBuffer(byte[] buffer) throws Exception {
/* 111 */     IndexedDefinitionTagBuffer indexedBuffer = new IndexedDefinitionTagBuffer(buffer);
/* 112 */     return indexedBuffer;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\IndexedDefinitionTagBufferManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */