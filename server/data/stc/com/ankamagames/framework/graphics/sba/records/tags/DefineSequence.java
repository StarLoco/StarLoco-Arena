/*    */ package com.ankamagames.framework.graphics.sba.records.tags;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
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
/*    */ public abstract class DefineSequence
/*    */   extends DefinitionTag
/*    */ {
/*    */   private short m_loopCount;
/*    */   
/*    */   public short getLoopCount()
/*    */   {
/* 27 */     return this.m_loopCount;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setLoopCount(short loopCount)
/*    */   {
/* 35 */     this.m_loopCount = loopCount;
/*    */   }
/*    */   
/*    */ 
/*    */   public abstract int getFrameCount();
/*    */   
/*    */   protected InputBitStream readDefinitionSequenceTagHeader(byte[] data)
/*    */     throws IOException
/*    */   {
/* 44 */     InputBitStream inStream = readDefinitionTagHeader(data);
/* 45 */     this.m_loopCount = inStream.readUI8();
/* 46 */     return inStream;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void writeData(OutputBitStream outStream)
/*    */     throws IOException
/*    */   {
/* 56 */     super.writeData(outStream);
/* 57 */     outStream.writeUI8(this.m_loopCount);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\tags\DefineSequence.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */