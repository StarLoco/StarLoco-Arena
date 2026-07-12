/*    */ package com.ankamagames.framework.graphics.aps.records.tags;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*    */ import com.ankamagames.framework.graphics.particlesystem.affectors.BaseAffector;
/*    */ import com.ankamagames.framework.graphics.particlesystem.affectors.DirectionFollower;
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
/*    */ public class DefineDirectionFollower
/*    */   extends DefineBaseAffecor
/*    */ {
/*    */   protected DefineDirectionFollower() {}
/*    */   
/*    */   public DefineDirectionFollower(DirectionFollower affector) {
/* 24 */     super((BaseAffector)affector);
/*    */     
/* 26 */     this.m_code = 14;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void initializeAffector(DirectionFollower affector) {}
/*    */ 
/*    */   
/*    */   protected void writeData(OutputBitStream outStream) throws IOException {
/* 35 */     super.writeData(outStream);
/*    */   }
/*    */   
/*    */   public void setData(byte[] data, short version) throws IOException {
/* 39 */     InputBitStream inStream = new InputBitStream(data);
/*    */     
/* 41 */     setTagsData(inStream, version);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\records\tags\DefineDirectionFollower.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */