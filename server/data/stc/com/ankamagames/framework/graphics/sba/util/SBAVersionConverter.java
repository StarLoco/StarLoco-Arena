/*    */ package com.ankamagames.framework.graphics.sba.util;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.tag.TagDocumentWriter;
/*    */ import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
/*    */ import com.ankamagames.framework.graphics.sba.SBADocument;
/*    */ import com.ankamagames.framework.graphics.sba.SBADocumentManager;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.InputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SBAVersionConverter
/*    */ {
/*    */   public static byte[] convert(byte[] oldData)
/*    */   {
/* 23 */     byte[] data = (byte[])null;
/*    */     
/* 25 */     InputStream istream = new ByteArrayInputStream(oldData);
/*    */     
/*    */     try
/*    */     {
/* 29 */       SBADocument oldDoc = SBADocumentManager.getInstance().getDocument(istream);
/* 30 */       short oldVersion = oldDoc.getVersion();
/*    */       
/* 32 */       if (oldDoc.isReadable(oldVersion))
/*    */       {
/* 34 */         if (oldVersion == 3) {
/* 35 */           System.out.println("Pas de conversion necessaire");
/* 36 */           data = oldData;
/*    */         }
/*    */         else {
/* 39 */           SBADocument newDoc = new SBADocument();
/* 40 */           newDoc.setCompressed(oldDoc.isCompressed());
/*    */           
/* 42 */           List<Tag> tags = oldDoc.getTags();
/* 43 */           for (Tag tag : tags) {
/* 44 */             newDoc.addTag(tag);
/*    */           }
/*    */           
/* 47 */           ByteArrayOutputStream ostream = new ByteArrayOutputStream(oldData.length);
/* 48 */           TagDocumentWriter writer = new TagDocumentWriter(newDoc, ostream);
/* 49 */           writer.write();
/*    */           
/* 51 */           data = ostream.toByteArray();
/*    */         }
/*    */       }
/*    */       else {
/* 55 */         System.err.println("Le numéro de version est incorrect " + getError(oldVersion));
/*    */       }
/*    */     }
/*    */     catch (Exception ex) {
/* 59 */       ex.printStackTrace();
/*    */     }
/*    */     
/* 62 */     return data;
/*    */   }
/*    */   
/*    */   private static String getError(short version)
/*    */   {
/* 67 */     return version + "(courante= " + 3 + ")";
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\util\SBAVersionConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */