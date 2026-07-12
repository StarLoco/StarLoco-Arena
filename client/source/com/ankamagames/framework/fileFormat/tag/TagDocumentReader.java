/*     */ package com.ankamagames.framework.fileFormat.tag;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.TagDecoder;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.TagHeader;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.TagReader;
/*     */ import java.io.InputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TagDocumentReader
/*     */ {
/*     */   private InputBitStream m_bitStream;
/*     */   private TagDecoder m_decoder;
/*     */   private TagDocument m_document;
/*     */   
/*     */   public TagDocumentReader(InputStream stream, TagDecoder decoder, TagDocumentFactory documentFactory) {
/*  50 */     create(stream, decoder, documentFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void create(InputStream stream, TagDecoder decoder, TagDocumentFactory documentFactory) {
/*  57 */     this.m_bitStream = new InputBitStream(stream);
/*  58 */     this.m_decoder = decoder;
/*  59 */     this.m_document = documentFactory.createDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read() throws Exception {
/*  68 */     this.m_document.getHeader().read(this.m_bitStream);
/*     */ 
/*     */     
/*  71 */     short version = this.m_document.getVersion();
/*  72 */     if (!this.m_document.isReadable(version)) {
/*  73 */       throw new Exception("La version lu est inconnue par le document : " + version);
/*     */     }
/*     */ 
/*     */     
/*  77 */     if (this.m_document.isCompressed()) {
/*  78 */       this.m_bitStream.enableCompression();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/*  85 */       TagHeader tagHeader = null;
/*  86 */       tagHeader = TagReader.readTagHeader(this.m_bitStream);
/*  87 */       if (tagHeader.getLength() < 0)
/*     */       {
/*  89 */         throw new Exception("Longueur de Tag invalide : " + tagHeader.getLength());
/*     */       }
/*     */ 
/*     */       
/*  93 */       Tag tag = null;
/*  94 */       byte[] tagData = (byte[])null;
/*     */       try {
/*  96 */         tagData = TagReader.readTagData(this.m_bitStream, tagHeader);
/*  97 */         tag = TagReader.readTag(this.m_decoder, tagHeader, tagData, version);
/*  98 */         if (tag.getCode() == 0) {
/*     */           break;
/*     */         }
/* 101 */       } catch (Exception e) {
/*     */         break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 107 */       this.m_document.addTag(tag);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 112 */     this.m_bitStream.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TagDocument getDocument() {
/* 119 */     return this.m_document;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\tag\TagDocumentReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */