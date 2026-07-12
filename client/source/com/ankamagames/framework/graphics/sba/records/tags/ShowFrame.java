/*    */ package com.ankamagames.framework.graphics.sba.records.tags;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
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
/*    */ public class ShowFrame
/*    */   extends Tag
/*    */ {
/*    */   private int m_duration;
/*    */   
/*    */   public ShowFrame(int duration) {
/* 26 */     this.m_code = 1;
/* 27 */     this.m_duration = duration;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ShowFrame() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDuration() {
/* 42 */     return this.m_duration;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDuration(int duration) {
/* 51 */     this.m_duration = duration;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setData(byte[] data, short version) throws IOException {
/* 61 */     InputBitStream inStream = new InputBitStream(data);
/* 62 */     this.m_duration = inStream.readUI16() & 0xFFFF;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeData(OutputBitStream outStream) throws IOException {
/* 72 */     outStream.writeUI16(this.m_duration);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\tags\ShowFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */