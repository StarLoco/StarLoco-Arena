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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RemoveObject
/*    */   extends Tag
/*    */ {
/*    */   private int m_depth;
/*    */   
/*    */   public RemoveObject(int depth) {
/* 30 */     this.m_code = 6;
/* 31 */     this.m_depth = depth;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public RemoveObject() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDepth() {
/* 41 */     return this.m_depth;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDepth(int depth) {
/* 49 */     this.m_depth = depth;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setData(byte[] data, short version) throws IOException {
/* 57 */     InputBitStream inStream = new InputBitStream(data);
/* 58 */     this.m_depth = inStream.readUI16();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeData(OutputBitStream outStream) throws IOException {
/* 66 */     outStream.writeUI16(this.m_depth);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\tags\RemoveObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */