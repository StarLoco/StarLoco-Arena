/*    */ package com.ankamagames.framework.graphics.sba;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.tag.TagDocument;
/*    */ import com.ankamagames.framework.fileFormat.tag.TagDocumentFactory;
/*    */ import com.ankamagames.framework.fileFormat.tag.TagDocumentReader;
/*    */ import com.ankamagames.framework.fileFormat.tag.records.tags.TagDecoder;
/*    */ import com.ankamagames.framework.graphics.sba.records.tags.SBATagDecoder;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.InputStream;
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
/*    */ public class SBADocumentManager
/*    */ {
/* 25 */   private static TagDocumentFactory m_documentFactory = new TagDocumentFactory() {
/*    */       public TagDocument createDocument() {
/* 27 */         return new SBADocument();
/*    */       }
/*    */     };
/*    */   
/* 31 */   private static final SBADocumentManager m_instance = new SBADocumentManager();
/*    */ 
/*    */ 
/*    */   
/*    */   private TagDocumentReader m_reader;
/*    */ 
/*    */ 
/*    */   
/*    */   private SBADocumentManager() {
/* 40 */     this.m_reader = new TagDocumentReader(null, (TagDecoder)SBATagDecoder.getInstance(), m_documentFactory);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SBADocumentManager getInstance() {
/* 47 */     return m_instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SBADocument getDocument(String fileName) throws Exception {
/* 58 */     return getDocument(new File(fileName));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SBADocument getDocument(File file) throws Exception {
/* 69 */     SBADocument doc = getDocument(new FileInputStream(file));
/* 70 */     if (doc.getVersion() != 3) {
/* 71 */       System.out.println("Le fichier " + file.getName() + " n'est pas a jour (version: " + doc.getVersion() + " courante: " + '\002');
/*    */     }
/* 73 */     return doc;
/*    */   }
/*    */   
/*    */   public SBADocument getDocument(InputStream istream) throws Exception {
/* 77 */     this.m_reader.create(istream, (TagDecoder)SBATagDecoder.getInstance(), m_documentFactory);
/*    */     
/* 79 */     this.m_reader.read();
/*    */     
/* 81 */     return (SBADocument)this.m_reader.getDocument();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\SBADocumentManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */