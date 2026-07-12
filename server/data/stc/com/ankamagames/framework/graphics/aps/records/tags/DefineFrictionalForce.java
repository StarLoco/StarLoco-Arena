/*    */ package com.ankamagames.framework.graphics.aps.records.tags;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*    */ import com.ankamagames.framework.graphics.particlesystem.affectors.FrictionalForce;
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
/*    */ public class DefineFrictionalForce
/*    */   extends DefineBaseAffecor
/*    */ {
/*    */   public float m_friction;
/*    */   
/*    */   protected DefineFrictionalForce() {}
/*    */   
/*    */   public DefineFrictionalForce(FrictionalForce affector)
/*    */   {
/* 25 */     super(affector);
/*    */     
/* 27 */     this.m_code = 9;
/*    */     
/* 29 */     this.m_friction = affector.getFriction();
/*    */   }
/*    */   
/*    */   public void initializeAffector(FrictionalForce affector)
/*    */   {
/* 34 */     affector.setFriction(this.m_friction);
/*    */   }
/*    */   
/*    */   protected void writeData(OutputBitStream outStream) throws IOException {
/* 38 */     outStream.writeFloat(this.m_friction);
/*    */     
/* 40 */     super.writeData(outStream);
/*    */   }
/*    */   
/*    */   public void setData(byte[] data, short version) throws IOException {
/* 44 */     InputBitStream inStream = new InputBitStream(data);
/* 45 */     this.m_friction = inStream.readFloat();
/*    */     
/* 47 */     setTagsData(inStream, version);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\records\tags\DefineFrictionalForce.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */