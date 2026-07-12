/*    */ package com.ankamagames.framework.graphics.sba.records.tags;
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
/*    */ 
/*    */ public class SBATagDecoder
/*    */   implements TagDecoder
/*    */ {
/* 20 */   private static SBATagDecoder m_instance = new SBATagDecoder();
/*    */   
/*    */ 
/*    */ 
/*    */   public static SBATagDecoder getInstance()
/*    */   {
/* 26 */     return m_instance;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Tag creatTagInstanceFromCode(short code)
/*    */   {
/* 35 */     Tag tag = null;
/* 36 */     switch (code)
/*    */     {
/*    */     case 1: 
/* 39 */       tag = new ShowFrame();
/* 40 */       break;
/*    */     
/*    */     case 2: 
/* 43 */       tag = new DefineBitmap();
/* 44 */       break;
/*    */     
/*    */     case 3: 
/* 47 */       tag = new DefineBitmapSequence();
/* 48 */       break;
/*    */     
/*    */     case 4: 
/* 51 */       tag = new DefineMovieClip();
/* 52 */       break;
/*    */     
/*    */     case 5: 
/* 55 */       tag = new PlaceObject();
/* 56 */       break;
/*    */     
/*    */     case 6: 
/* 59 */       tag = new RemoveObject();
/* 60 */       break;
/*    */     
/*    */     case 7: 
/* 63 */       tag = new ActionFlag();
/* 64 */       break;
/*    */     
/*    */     case 0: 
/* 67 */       tag = EndTag.getInstance();
/*    */     }
/*    */     
/* 70 */     return tag;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\tags\SBATagDecoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */