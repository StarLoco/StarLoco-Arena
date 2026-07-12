/*    */ package com.ankamagames.framework.graphics.aps.records.tags;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.tag.records.tags.EndTag;
/*    */ import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
/*    */ import com.ankamagames.framework.fileFormat.tag.records.tags.TagDecoder;
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
/*    */ public class APSTagDecoder
/*    */   implements TagDecoder
/*    */ {
/* 19 */   private static APSTagDecoder m_instance = new APSTagDecoder();
/*    */   
/*    */ 
/*    */ 
/*    */   public static APSTagDecoder getInstance()
/*    */   {
/* 25 */     return m_instance;
/*    */   }
/*    */   
/*    */   public Tag creatTagInstanceFromCode(short code) {
/* 29 */     Tag tag = null;
/* 30 */     switch (code)
/*    */     {
/*    */     case 2: 
/* 33 */       tag = new DefineEmitter();
/* 34 */       break;
/*    */     case 4: 
/* 36 */       tag = new DefineParticleBitmapModel();
/* 37 */       break;
/*    */     case 13: 
/* 39 */       tag = new DefineParticleSequenceModel();
/* 40 */       break;
/*    */     case 1: 
/* 42 */       tag = new DefineParticleSystem();
/* 43 */       break;
/*    */     case 3: 
/* 45 */       tag = new DefineBitmap();
/* 46 */       break;
/*    */     case 12: 
/* 48 */       tag = new DefineSequence();
/* 49 */       break;
/*    */     case 5: 
/* 51 */       tag = new DefineColorFader();
/* 52 */       break;
/*    */     case 10: 
/* 54 */       tag = new DefineDeformer();
/* 55 */       break;
/*    */     case 14: 
/* 57 */       tag = new DefineDirectionFollower();
/* 58 */       break;
/*    */     case 9: 
/* 60 */       tag = new DefineFrictionalForce();
/* 61 */       break;
/*    */     case 7: 
/* 63 */       tag = new DefineLinearForce();
/* 64 */       break;
/*    */     case 8: 
/* 66 */       tag = new DefineRotorForce();
/* 67 */       break;
/*    */     case 6: 
/* 69 */       tag = new DefineAttractionForce();
/* 70 */       break;
/*    */     case 11: 
/* 72 */       tag = new DefineLifeCondition();
/* 73 */       break;
/*    */     case 0: 
/* 75 */       tag = EndTag.getInstance();
/*    */     }
/*    */     
/* 78 */     return tag;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\records\tags\APSTagDecoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */