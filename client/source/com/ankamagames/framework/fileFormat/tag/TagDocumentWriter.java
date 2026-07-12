/*    */ package com.ankamagames.framework.fileFormat.tag;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.tag.records.TagDocumentHeader;
/*    */ import com.ankamagames.framework.fileFormat.tag.records.tags.TagWriter;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TagDocumentWriter
/*    */ {
/*    */   private OutputBitStream m_bitStream;
/*    */   private TagDocument m_document;
/*    */   
/*    */   public TagDocumentWriter(TagDocument document, OutputStream stream) {
/* 34 */     this.m_bitStream = new OutputBitStream(stream);
/* 35 */     this.m_document = document;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write() throws IOException {
/*    */     try {
/* 45 */       byte[] tagsBuffer = TagWriter.writeTags(this.m_document.getTags());
/* 46 */       long fileLength = (8 + tagsBuffer.length);
/*    */ 
/*    */       
/* 49 */       writeHeader(fileLength);
/* 50 */       if (this.m_document.isCompressed()) {
/* 51 */         this.m_bitStream.enableCompression();
/*    */       }
/*    */ 
/*    */       
/* 55 */       this.m_bitStream.writeBytes(tagsBuffer);
/*    */     } finally {
/*    */       
/*    */       try {
/* 59 */         this.m_bitStream.close();
/* 60 */       } catch (Exception exception) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void writeHeader(long fileLength) throws IOException {
/* 66 */     TagDocumentHeader header = this.m_document.getHeader();
/* 67 */     header.setFileLength(fileLength);
/* 68 */     header.write(this.m_bitStream);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\tag\TagDocumentWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */