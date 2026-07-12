/*    */ package com.ankamagames.framework.fileFormat.tag.records.tags;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*    */ import java.io.IOException;
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
/*    */ public final class TagReader
/*    */ {
/*    */   public static Tag readTag(TagDecoder decoder, TagHeader header, byte[] tagData, short version) throws IOException {
/* 28 */     Tag tag = decoder.creatTagInstanceFromCode(header.getCode());
/* 29 */     if (tag == null) {
/* 30 */       tag = new UnknownTag();
/*    */     }
/* 32 */     tag.setCode(header.getCode());
/* 33 */     tag.setData(tagData, version);
/* 34 */     tag.setLength(tagData.length);
/* 35 */     return tag;
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
/*    */   
/*    */   public static byte[] readTagData(InputBitStream stream, TagHeader header) throws IOException {
/* 48 */     return stream.readBytes(header.getLength());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static TagHeader readTagHeader(InputBitStream stream) throws IOException {
/* 59 */     return new TagHeader(stream);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Tag readTag(TagDecoder decoder, InputBitStream stream, short version) throws IOException {
/* 66 */     TagHeader header = readTagHeader(stream);
/* 67 */     byte[] tagData = stream.readBytes(header.getLength());
/* 68 */     return readTag(decoder, header, tagData, version);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\tag\records\tags\TagReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */