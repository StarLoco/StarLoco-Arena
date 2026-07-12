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
/*    */   
/*    */   public static APSTagDecoder getInstance() {
/* 25 */     return m_instance;
/*    */   }
/*    */   public Tag creatTagInstanceFromCode(short code) {
/*    */     EndTag endTag;
/* 29 */     Tag tag = null;
/* 30 */     switch (code) {
/*    */       
/*    */       case 2:
/* 33 */         tag = new DefineEmitter();
/*    */         break;
/*    */       case 4:
/* 36 */         tag = new DefineParticleBitmapModel();
/*    */         break;
/*    */       case 13:
/* 39 */         tag = new DefineParticleSequenceModel();
/*    */         break;
/*    */       case 1:
/* 42 */         tag = new DefineParticleSystem();
/*    */         break;
/*    */       case 3:
/* 45 */         tag = new DefineBitmap();
/*    */         break;
/*    */       case 12:
/* 48 */         tag = new DefineSequence();
/*    */         break;
/*    */       case 5:
/* 51 */         tag = new DefineColorFader();
/*    */         break;
/*    */       case 10:
/* 54 */         tag = new DefineDeformer();
/*    */         break;
/*    */       case 14:
/* 57 */         tag = new DefineDirectionFollower();
/*    */         break;
/*    */       case 9:
/* 60 */         tag = new DefineFrictionalForce();
/*    */         break;
/*    */       case 7:
/* 63 */         tag = new DefineLinearForce();
/*    */         break;
/*    */       case 8:
/* 66 */         tag = new DefineRotorForce();
/*    */         break;
/*    */       case 6:
/* 69 */         tag = new DefineAttractionForce();
/*    */         break;
/*    */       case 11:
/* 72 */         tag = new DefineLifeCondition();
/*    */         break;
/*    */       case 0:
/* 75 */         endTag = EndTag.getInstance();
/*    */         break;
/*    */     } 
/* 78 */     return (Tag)endTag;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\records\tags\APSTagDecoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */