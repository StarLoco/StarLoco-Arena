/*    */ package com.ankamagames.framework.graphics.sba.records;
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
/*    */ public class SBAHeader
/*    */   extends TagDocumentHeader
/*    */ {
/*    */   public static final boolean DEFAULT_COMPRESS = true;
/*    */   public static final String SBA_SIGNATURE = "sba";
/*    */   
/*    */   public SBAHeader()
/*    */   {
/* 31 */     setSignature("sba");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void reset()
/*    */   {
/* 39 */     super.reset();
/* 40 */     setVersion((short)3);
/* 41 */     setCompressed(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\SBAHeader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */