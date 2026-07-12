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
/*    */   public Tag creatTagInstanceFromCode(short code)
/*    */   {
/* 28 */     Tag tag = null;
/* 29 */     switch (code)
/*    */     {
/*    */     case 2: 
/*    */     case 3: 
/*    */     case 4: 
/* 34 */       tag = new CommonDefineTag();
/* 35 */       break;
/*    */     
/*    */     case 0: 
/* 38 */       tag = EndTag.getInstance();
/*    */     }
/*    */     
/*    */     
/* 42 */     return tag;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\tags\SBADefinitionTagDecoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */