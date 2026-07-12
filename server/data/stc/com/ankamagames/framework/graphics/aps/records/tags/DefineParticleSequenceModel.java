/*    */ package com.ankamagames.framework.graphics.aps.records.tags;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*    */ import com.ankamagames.framework.graphics.particlesystem.particles.ParticleSequenceModel;
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
/*    */ public class DefineParticleSequenceModel
/*    */   extends DefineParticleModel
/*    */ {
/*    */   private int m_sequenceId;
/*    */   private String m_sequenceLinkage;
/*    */   
/*    */   protected DefineParticleSequenceModel() {}
/*    */   
/*    */   public DefineParticleSequenceModel(ParticleSequenceModel model)
/*    */   {
/* 27 */     super(model);
/*    */     
/* 29 */     this.m_code = 13;
/*    */     
/* 31 */     this.m_sequenceLinkage = model.getLinkage();
/* 32 */     this.m_sequenceId = model.getSequenceId();
/*    */   }
/*    */   
/*    */   public void initializeParticle(ParticleSequenceModel model)
/*    */   {
/* 37 */     model.setLinkage(this.m_sequenceLinkage);
/* 38 */     model.setSequenceId(this.m_sequenceId);
/*    */     
/* 40 */     super.initializeParticle(model);
/*    */   }
/*    */   
/*    */   protected void writeData(OutputBitStream outStream) throws IOException
/*    */   {
/* 45 */     outStream.writeString(this.m_sequenceLinkage);
/* 46 */     outStream.writeUI16(this.m_sequenceId);
/*    */     
/* 48 */     super.writeData(outStream);
/*    */   }
/*    */   
/*    */   public void setData(byte[] data, short version) throws IOException {
/* 52 */     InputBitStream inStream = new InputBitStream(data);
/*    */     
/* 54 */     this.m_sequenceLinkage = inStream.readString();
/* 55 */     this.m_sequenceId = inStream.readUI16();
/*    */     
/* 57 */     super.setData(inStream);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\records\tags\DefineParticleSequenceModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */