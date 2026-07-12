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
/*    */ public class ActionFlag
/*    */   extends Tag
/*    */ {
/*    */   protected String m_action;
/*    */   
/*    */   public ActionFlag(String content)
/*    */   {
/* 29 */     this.m_code = 7;
/* 30 */     this.m_action = content;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public ActionFlag() {}
/*    */   
/*    */ 
/*    */   public String getAction()
/*    */   {
/* 40 */     return this.m_action;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setAction(String action)
/*    */   {
/* 48 */     this.m_action = action;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setData(byte[] data, short version)
/*    */     throws IOException
/*    */   {
/* 57 */     InputBitStream inStream = new InputBitStream(data);
/* 58 */     this.m_action = inStream.readString();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void writeData(OutputBitStream outStream)
/*    */     throws IOException
/*    */   {
/* 67 */     outStream.writeString(this.m_action);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\tags\ActionFlag.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */