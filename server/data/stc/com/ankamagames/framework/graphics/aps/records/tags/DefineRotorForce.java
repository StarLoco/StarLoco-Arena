/*    */ package com.ankamagames.framework.graphics.aps.records.tags;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*    */ import com.ankamagames.framework.graphics.particlesystem.affectors.RotorForce;
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
/*    */ public class DefineRotorForce
/*    */   extends DefineBaseAffecor
/*    */ {
/*    */   public float m_intensity;
/*    */   
/*    */   protected DefineRotorForce() {}
/*    */   
/*    */   public DefineRotorForce(RotorForce affector)
/*    */   {
/* 25 */     super(affector);
/*    */     
/* 27 */     this.m_code = 8;
/*    */     
/* 29 */     this.m_intensity = affector.getIntensity();
/*    */   }
/*    */   
/*    */   public void initializeAffector(RotorForce affector)
/*    */   {
/* 34 */     affector.setIntensity(this.m_intensity);
/*    */   }
/*    */   
/*    */   protected void writeData(OutputBitStream outStream) throws IOException {
/* 38 */     outStream.writeFloat(this.m_intensity);
/*    */     
/* 40 */     super.writeData(outStream);
/*    */   }
/*    */   
/*    */   public void setData(byte[] data, short version) throws IOException {
/* 44 */     InputBitStream inStream = new InputBitStream(data);
/* 45 */     this.m_intensity = inStream.readFloat();
/*    */     
/* 47 */     setTagsData(inStream, version);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\records\tags\DefineRotorForce.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */