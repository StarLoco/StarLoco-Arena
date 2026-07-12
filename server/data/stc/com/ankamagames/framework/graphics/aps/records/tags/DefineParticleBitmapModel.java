/*    */ package com.ankamagames.framework.graphics.aps.records.tags;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*    */ import com.ankamagames.framework.graphics.particlesystem.particles.ParticleBitmapModel;
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
/*    */ public class DefineParticleBitmapModel
/*    */   extends DefineParticleModel
/*    */ {
/*    */   private int m_bitmapId;
/*    */   
/*    */   protected DefineParticleBitmapModel() {}
/*    */   
/*    */   public DefineParticleBitmapModel(ParticleBitmapModel model)
/*    */   {
/* 25 */     super(model);
/*    */     
/* 27 */     this.m_code = 4;
/*    */     
/* 29 */     this.m_bitmapId = model.getBitmapId();
/*    */   }
/*    */   
/*    */   public void initializeParticle(ParticleBitmapModel model)
/*    */   {
/* 34 */     model.setBitmapId(this.m_bitmapId);
/*    */     
/* 36 */     super.initializeParticle(model);
/*    */   }
/*    */   
/*    */   protected void writeData(OutputBitStream outStream) throws IOException
/*    */   {
/* 41 */     outStream.writeUI16(this.m_bitmapId);
/*    */     
/* 43 */     super.writeData(outStream);
/*    */   }
/*    */   
/*    */   public void setData(byte[] data, short version) throws IOException {
/* 47 */     InputBitStream inStream = new InputBitStream(data);
/*    */     
/* 49 */     this.m_bitmapId = inStream.readUI16();
/*    */     
/* 51 */     super.setData(inStream);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\records\tags\DefineParticleBitmapModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */