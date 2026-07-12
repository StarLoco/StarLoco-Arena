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
/*    */   
/*    */   public static SBATagDecoder getInstance() {
/* 26 */     return m_instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Tag creatTagInstanceFromCode(short code) {
/*    */     EndTag endTag;
/* 35 */     Tag tag = null;
/* 36 */     switch (code) {
/*    */       
/*    */       case 1:
/* 39 */         tag = new ShowFrame();
/*    */         break;
/*    */       
/*    */       case 2:
/* 43 */         tag = new DefineBitmap();
/*    */         break;
/*    */       
/*    */       case 3:
/* 47 */         tag = new DefineBitmapSequence();
/*    */         break;
/*    */       
/*    */       case 4:
/* 51 */         tag = new DefineMovieClip();
/*    */         break;
/*    */       
/*    */       case 5:
/* 55 */         tag = new PlaceObject();
/*    */         break;
/*    */       
/*    */       case 6:
/* 59 */         tag = new RemoveObject();
/*    */         break;
/*    */       
/*    */       case 7:
/* 63 */         tag = new ActionFlag();
/*    */         break;
/*    */       
/*    */       case 0:
/* 67 */         endTag = EndTag.getInstance();
/*    */         break;
/*    */     } 
/* 70 */     return (Tag)endTag;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\tags\SBATagDecoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */