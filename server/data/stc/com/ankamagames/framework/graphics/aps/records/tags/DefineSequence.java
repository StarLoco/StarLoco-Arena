/*    */ package com.ankamagames.framework.graphics.aps.records.tags;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
/*    */ import com.ankamagames.framework.graphics.sba.util.SBAVersionConverter;
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
/*    */ public class DefineSequence
/*    */   extends Tag
/*    */ {
/*    */   private byte[] m_sequenceBuffer;
/*    */   private int m_sequenceId;
/*    */   
/*    */   protected DefineSequence() {}
/*    */   
/*    */   public DefineSequence(int id, byte[] buffer)
/*    */   {
/* 28 */     this.m_code = 12;
/*    */     
/* 30 */     this.m_sequenceId = id;
/* 31 */     this.m_sequenceBuffer = buffer;
/*    */   }
/*    */   
/*    */   protected void writeData(OutputBitStream outStream) throws IOException
/*    */   {
/* 36 */     outStream.writeUI16(this.m_sequenceId);
/* 37 */     this.m_sequenceBuffer = SBAVersionConverter.convert(this.m_sequenceBuffer);
/* 38 */     outStream.writeBytes(this.m_sequenceBuffer);
/*    */   }
/*    */   
/*    */   public void setData(byte[] data, short version) throws IOException {
/* 42 */     InputBitStream inStream = new InputBitStream(data);
/*    */     
/* 44 */     this.m_sequenceId = inStream.readUI16();
/* 45 */     this.m_sequenceBuffer = inStream.readBytes(data.length - 2);
/*    */   }
/*    */   
/*    */   public byte[] getSequenceBuffer() {
/* 49 */     return this.m_sequenceBuffer;
/*    */   }
/*    */   
/*    */   public int getSequenceId() {
/* 53 */     return this.m_sequenceId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\records\tags\DefineSequence.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */