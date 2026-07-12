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
/*    */ public abstract class DefinitionTag
/*    */   extends Tag
/*    */ {
/*    */   protected int m_identifier;
/*    */   protected String m_linkage;
/*    */   
/*    */   public void setIdentifier(int identifier)
/*    */   {
/* 30 */     this.m_identifier = identifier;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getIdentifier()
/*    */   {
/* 39 */     return this.m_identifier;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isLinked()
/*    */   {
/* 48 */     return this.m_linkage != null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getLinkage()
/*    */   {
/* 55 */     return this.m_linkage;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setLinkage(String linkage)
/*    */   {
/* 62 */     this.m_linkage = linkage;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void writeData(OutputBitStream outStream)
/*    */     throws IOException
/*    */   {
/* 72 */     outStream.writeUI16(this.m_identifier);
/* 73 */     if (this.m_linkage != null) {
/* 74 */       outStream.writeBooleanBit(true);
/* 75 */       outStream.writeString(this.m_linkage);
/*    */     } else {
/* 77 */       outStream.writeBooleanBit(false);
/*    */     }
/* 79 */     outStream.align();
/*    */   }
/*    */   
/*    */   protected InputBitStream readDefinitionTagHeader(byte[] data) throws IOException {
/* 83 */     InputBitStream inStream = new InputBitStream(data);
/* 84 */     this.m_identifier = inStream.readUI16();
/* 85 */     boolean hasLinkage = inStream.readBooleanBit();
/* 86 */     if (hasLinkage) {
/* 87 */       this.m_linkage = inStream.readString();
/* 88 */       inStream.align();
/*    */     } else {
/* 90 */       this.m_linkage = null;
/*    */     }
/* 92 */     return inStream;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\tags\DefinitionTag.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */