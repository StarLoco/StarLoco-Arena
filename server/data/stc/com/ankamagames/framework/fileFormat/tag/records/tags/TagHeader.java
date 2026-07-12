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
/*    */ public final class TagHeader
/*    */ {
/*    */   private short m_code;
/*    */   private int m_length;
/*    */   
/*    */   TagHeader() {}
/*    */   
/*    */   TagHeader(InputBitStream stream)
/*    */     throws IOException
/*    */   {
/* 27 */     read(stream);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public short getCode()
/*    */   {
/* 38 */     return this.m_code;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getLength()
/*    */   {
/* 47 */     return this.m_length;
/*    */   }
/*    */   
/*    */   private void read(InputBitStream stream) throws IOException {
/* 51 */     int codeAndLength = stream.readUI16();
/* 52 */     this.m_code = ((short)(codeAndLength >> 6));
/* 53 */     this.m_length = (codeAndLength & 0x3F);
/* 54 */     if (this.m_length == 63) {
/* 55 */       this.m_length = ((int)stream.readUI32());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\tag\records\tags\TagHeader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */