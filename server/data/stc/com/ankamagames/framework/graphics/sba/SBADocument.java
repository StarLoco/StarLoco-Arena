/*    */ package com.ankamagames.framework.graphics.sba;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.tag.TagDocument;
/*    */ import com.ankamagames.framework.graphics.sba.records.SBAHeader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SBADocument
/*    */   extends TagDocument
/*    */ {
/* 17 */   private int m_currentIdentifier = 0;
/*    */   
/* 19 */   public static final short[] READABLE_VERSION = { 1, 2, 3 };
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
/*    */   protected void createHeader()
/*    */   {
/* 35 */     this.m_header = new SBAHeader();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getNextIdentifier()
/*    */   {
/* 44 */     return ++this.m_currentIdentifier;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void clear()
/*    */   {
/* 54 */     super.clear();
/* 55 */     this.m_currentIdentifier = 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isReadable(short version)
/*    */   {
/* 67 */     for (int i = READABLE_VERSION.length - 1; i >= 0; i--) {
/* 68 */       if (READABLE_VERSION[i] == version) {
/* 69 */         return true;
/*    */       }
/*    */     }
/* 72 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\SBADocument.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */