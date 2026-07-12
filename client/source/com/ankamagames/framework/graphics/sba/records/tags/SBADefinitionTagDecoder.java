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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SBADefinitionTagDecoder
/*    */   implements TagDecoder
/*    */ {
/*    */   public Tag creatTagInstanceFromCode(short code) {
/*    */     EndTag endTag;
/* 28 */     Tag tag = null;
/* 29 */     switch (code) {
/*    */       
/*    */       case 2:
/*    */       case 3:
/*    */       case 4:
/* 34 */         tag = new CommonDefineTag();
/*    */         break;
/*    */       
/*    */       case 0:
/* 38 */         endTag = EndTag.getInstance();
/*    */         break;
/*    */     } 
/*    */     
/* 42 */     return (Tag)endTag;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\tags\SBADefinitionTagDecoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */