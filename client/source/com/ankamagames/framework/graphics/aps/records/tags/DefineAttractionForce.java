/*    */ package com.ankamagames.framework.graphics.aps.records.tags;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*    */ import com.ankamagames.framework.graphics.particlesystem.affectors.AttractionForce;
/*    */ import com.ankamagames.framework.graphics.particlesystem.affectors.BaseAffector;
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
/*    */ public class DefineAttractionForce
/*    */   extends DefineBaseAffecor
/*    */ {
/*    */   public float m_intensity;
/*    */   
/*    */   protected DefineAttractionForce() {}
/*    */   
/*    */   public DefineAttractionForce(AttractionForce affector) {
/* 25 */     super((BaseAffector)affector);
/*    */     
/* 27 */     this.m_code = 6;
/* 28 */     this.m_intensity = affector.getIntensity();
/*    */   }
/*    */ 
/*    */   
/*    */   public void initializeAffector(AttractionForce attractionForce) {
/* 33 */     attractionForce.setIntensity(this.m_intensity);
/*    */   }
/*    */   
/*    */   protected void writeData(OutputBitStream outStream) throws IOException {
/* 37 */     outStream.writeFloat(this.m_intensity);
/*    */     
/* 39 */     super.writeData(outStream);
/*    */   }
/*    */   
/*    */   public void setData(byte[] data, short version) throws IOException {
/* 43 */     InputBitStream inStream = new InputBitStream(data);
/* 44 */     this.m_intensity = inStream.readFloat();
/*    */     
/* 46 */     setTagsData(inStream, version);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\records\tags\DefineAttractionForce.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */