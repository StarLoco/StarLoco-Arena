/*    */ package com.ankamagames.framework.graphics.aps;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.tag.TagDocument;
/*    */ import com.ankamagames.framework.fileFormat.tag.records.TagDocumentHeader;
/*    */ import com.ankamagames.framework.graphics.aps.records.APSHeader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class APSDocument
/*    */   extends TagDocument
/*    */ {
/*    */   protected void createHeader() {
/* 17 */     this.m_header = (TagDocumentHeader)new APSHeader();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\APSDocument.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */