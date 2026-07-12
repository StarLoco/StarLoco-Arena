/*    */ package com.ankamagames.framework.fileFormat.tag.records.tags;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class TagWriter
/*    */ {
/*    */   public static void writeTag(OutputBitStream stream, Tag tag)
/*    */     throws IOException
/*    */   {
/* 36 */     tag.write(stream);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static byte[] writeTag(Tag tag)
/*    */     throws IOException
/*    */   {
/* 49 */     OutputBitStream stream = new OutputBitStream();
/* 50 */     writeTag(stream, tag);
/* 51 */     return stream.getData();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static byte[] writeTags(ArrayList<Tag> tags)
/*    */     throws IOException
/*    */   {
/* 65 */     OutputBitStream stream = new OutputBitStream();
/* 66 */     writeTags(stream, tags);
/* 67 */     return stream.getData();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void writeTags(OutputBitStream stream, ArrayList<Tag> tags)
/*    */     throws IOException
/*    */   {
/* 81 */     for (Tag tag : tags) {
/* 82 */       writeTag(stream, tag);
/*    */     }
/*    */     
/*    */ 
/* 86 */     stream.writeUI16(0);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\tag\records\tags\TagWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */