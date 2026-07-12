/*    */ package com.ankamagames.framework.graphics.aps.records.tags;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*    */ import com.ankamagames.framework.graphics.particlesystem.affectors.Deformer;
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
/*    */ public class DefineDeformer
/*    */   extends DefineBaseAffecor
/*    */ {
/*    */   private float m_growthX;
/*    */   private float m_growthY;
/*    */   private float m_rotate;
/*    */   
/*    */   protected DefineDeformer() {}
/*    */   
/*    */   public DefineDeformer(Deformer affector)
/*    */   {
/* 27 */     super(affector);
/*    */     
/* 29 */     this.m_code = 10;
/*    */     
/* 31 */     this.m_growthX = affector.getGrowthX();
/* 32 */     this.m_growthY = affector.getGrowthY();
/* 33 */     this.m_rotate = affector.getRotate();
/*    */   }
/*    */   
/*    */   public void initializeAffector(Deformer affector)
/*    */   {
/* 38 */     affector.setGrowthX(this.m_growthX);
/* 39 */     affector.setGrowthY(this.m_growthY);
/* 40 */     affector.setRotate(this.m_rotate);
/*    */   }
/*    */   
/*    */   protected void writeData(OutputBitStream outStream) throws IOException {
/* 44 */     outStream.writeFloat(this.m_growthX);
/* 45 */     outStream.writeFloat(this.m_growthY);
/* 46 */     outStream.writeFloat(this.m_rotate);
/*    */     
/* 48 */     super.writeData(outStream);
/*    */   }
/*    */   
/*    */   public void setData(byte[] data, short version) throws IOException {
/* 52 */     InputBitStream inStream = new InputBitStream(data);
/* 53 */     this.m_growthX = inStream.readFloat();
/* 54 */     this.m_growthY = inStream.readFloat();
/* 55 */     this.m_rotate = inStream.readFloat();
/*    */     
/* 57 */     setTagsData(inStream, version);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\records\tags\DefineDeformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */