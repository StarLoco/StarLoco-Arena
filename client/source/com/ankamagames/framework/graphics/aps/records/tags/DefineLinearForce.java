/*    */ package com.ankamagames.framework.graphics.aps.records.tags;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*    */ import com.ankamagames.framework.graphics.particlesystem.affectors.BaseAffector;
/*    */ import com.ankamagames.framework.graphics.particlesystem.affectors.LinearForce;
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
/*    */ public class DefineLinearForce
/*    */   extends DefineBaseAffecor
/*    */ {
/*    */   public float m_forceX;
/*    */   public float m_forceY;
/*    */   public float m_forceZ;
/*    */   
/*    */   protected DefineLinearForce() {}
/*    */   
/*    */   public DefineLinearForce(LinearForce affector) {
/* 27 */     super((BaseAffector)affector);
/*    */     
/* 29 */     this.m_code = 7;
/*    */     
/* 31 */     this.m_forceX = affector.getForceX();
/* 32 */     this.m_forceY = affector.getForceY();
/* 33 */     this.m_forceZ = affector.getForceZ();
/*    */   }
/*    */ 
/*    */   
/*    */   public void initializeAffector(LinearForce affector) {
/* 38 */     affector.setForceX(this.m_forceX);
/* 39 */     affector.setForceY(this.m_forceY);
/* 40 */     affector.setForceZ(this.m_forceZ);
/*    */   }
/*    */   
/*    */   protected void writeData(OutputBitStream outStream) throws IOException {
/* 44 */     outStream.writeFloat(this.m_forceX);
/* 45 */     outStream.writeFloat(this.m_forceY);
/* 46 */     outStream.writeFloat(this.m_forceZ);
/*    */     
/* 48 */     super.writeData(outStream);
/*    */   }
/*    */   
/*    */   public void setData(byte[] data, short version) throws IOException {
/* 52 */     InputBitStream inStream = new InputBitStream(data);
/* 53 */     this.m_forceX = inStream.readFloat();
/* 54 */     this.m_forceY = inStream.readFloat();
/* 55 */     this.m_forceZ = inStream.readFloat();
/*    */     
/* 57 */     setTagsData(inStream, version);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\records\tags\DefineLinearForce.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */