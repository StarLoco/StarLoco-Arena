/*    */ package com.ankamagames.framework.graphics.aps.records;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.tag.records.TagDocumentHeader;
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
/*    */ 
/*    */ 
/*    */ public class APSHeader
/*    */   extends TagDocumentHeader
/*    */ {
/*    */   public static final boolean DEFAULT_COMPRESS = true;
/*    */   public static final String APS_SIGNATURE = "aps";
/*    */   
/*    */   public APSHeader() {
/* 30 */     setSignature("aps");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void reset() {
/* 38 */     super.reset();
/* 39 */     setVersion((short)2);
/* 40 */     setCompressed(true);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\records\APSHeader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */