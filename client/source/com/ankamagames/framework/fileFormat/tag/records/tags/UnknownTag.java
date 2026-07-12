/*    */ package com.ankamagames.framework.fileFormat.tag.records.tags;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
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
/*    */ public final class UnknownTag
/*    */   extends Tag
/*    */ {
/*    */   private byte[] inData;
/*    */   
/*    */   public UnknownTag() {}
/*    */   
/*    */   public UnknownTag(short code, byte[] data) {
/* 33 */     this.m_code = code;
/* 34 */     this.inData = data;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getData() {
/* 43 */     return this.inData;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setData(byte[] data, short version) throws IOException {
/* 53 */     this.inData = data;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeData(OutputBitStream outStream) throws IOException {
/* 64 */     outStream.writeBytes(this.inData);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\tag\records\tags\UnknownTag.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */