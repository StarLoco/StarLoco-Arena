/*    */ package com.ankamagames.framework.graphics.sba.records.tags;
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
/*    */ 
/*    */ 
/*    */ public class CommonDefineTag
/*    */   extends DefinitionTag
/*    */ {
/*    */   public void setData(byte[] data, short version) throws IOException {
/* 31 */     readDefinitionTagHeader(data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeData(OutputBitStream outStream) throws IOException {
/* 41 */     super.writeData(outStream);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\tags\CommonDefineTag.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */