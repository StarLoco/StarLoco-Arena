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
/*    */ public final class EndTag
/*    */   extends Tag
/*    */ {
/* 15 */   private static final EndTag m_instance = new EndTag();
/*    */   
/* 17 */   public static EndTag getInstance() { return m_instance; }
/*    */   
/*    */   public void setData(byte[] data, short version)
/*    */     throws IOException
/*    */   {}
/*    */   
/*    */   protected void writeData(OutputBitStream outStream)
/*    */     throws IOException
/*    */   {}
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\tag\records\tags\EndTag.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */