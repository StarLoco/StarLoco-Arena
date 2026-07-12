/*    */ package com.ankamagames.framework.graphics.aps;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.tag.TagDocument;
/*    */ import com.ankamagames.framework.fileFormat.tag.TagDocumentFactory;
/*    */ import com.ankamagames.framework.fileFormat.tag.TagDocumentReader;
/*    */ import com.ankamagames.framework.graphics.aps.records.tags.APSTagDecoder;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.InputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.net.URL;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class APSDocumentManager
/*    */ {
/* 23 */   private static TagDocumentFactory m_documentFactory = new TagDocumentFactory() {
/*    */     public TagDocument createDocument() {
/* 25 */       return new APSDocument();
/*    */     }
/*    */   };
/*    */   
/* 29 */   private static final APSDocumentManager m_instance = new APSDocumentManager();
/*    */   private TagDocumentReader m_reader;
/*    */   
/*    */   private APSDocumentManager()
/*    */   {
/* 34 */     this.m_reader = new TagDocumentReader(null, APSTagDecoder.getInstance(), m_documentFactory);
/*    */   }
/*    */   
/*    */   public static APSDocumentManager getInstance() {
/* 38 */     return m_instance;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public APSDocument getDocument(String fileName)
/*    */     throws Exception
/*    */   {
/* 49 */     InputStream stream = null;
/*    */     try
/*    */     {
/* 52 */       URL jarUrl = new URL(fileName.toLowerCase());
/* 53 */       stream = jarUrl.openStream();
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 57 */       File file = new File(fileName);
/* 58 */       stream = new FileInputStream(file);
/*    */     }
/*    */     
/* 61 */     return getDocument(stream);
/*    */   }
/*    */   
/*    */   public APSDocument getDocument(File file) throws Exception {
/* 65 */     APSDocument doc = getDocument(new FileInputStream(file));
/* 66 */     if (doc.getVersion() != 2) {
/* 67 */       System.out.println("Le fichier " + file.getName() + " n'est pas a jour (version: " + doc.getVersion() + " courante: " + 2);
/*    */     }
/* 69 */     return doc;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public APSDocument getDocument(InputStream stream)
/*    */     throws Exception
/*    */   {
/* 79 */     this.m_reader.create(stream, APSTagDecoder.getInstance(), m_documentFactory);
/*    */     
/* 81 */     this.m_reader.read();
/* 82 */     return (APSDocument)this.m_reader.getDocument();
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\APSDocumentManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */