/*    */ package com.ankamagames.framework.graphics.aps.records.tags;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
/*    */ import com.ankamagames.framework.graphics.particlesystem.conditions.LifeCondition;
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
/*    */ public class DefineLifeCondition
/*    */   extends Tag
/*    */ {
/* 21 */   private int m_lifeMax = 0;
/* 22 */   private int m_lifeMin = 0;
/*    */ 
/*    */   
/*    */   public DefineLifeCondition() {}
/*    */   
/*    */   public DefineLifeCondition(LifeCondition condition) {
/* 28 */     this.m_code = 11;
/*    */     
/* 30 */     this.m_lifeMax = condition.getLifeMax();
/* 31 */     this.m_lifeMin = condition.getLifeMin();
/*    */   }
/*    */ 
/*    */   
/*    */   public void initializeCondition(LifeCondition condition) {
/* 36 */     condition.setLifeMax(this.m_lifeMax);
/* 37 */     condition.setLifeMin(this.m_lifeMin);
/*    */   }
/*    */   
/*    */   protected void writeData(OutputBitStream outStream) throws IOException {
/* 41 */     outStream.writeUI16(this.m_lifeMax);
/* 42 */     outStream.writeUI16(this.m_lifeMin);
/*    */   }
/*    */   
/*    */   public void setData(byte[] data, short version) throws IOException {
/* 46 */     InputBitStream inStream = new InputBitStream(data);
/* 47 */     this.m_lifeMax = inStream.readUI16();
/* 48 */     this.m_lifeMin = inStream.readUI16();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\records\tags\DefineLifeCondition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */