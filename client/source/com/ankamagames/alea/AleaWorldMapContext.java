/*    */ package com.ankamagames.alea;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.document.DocumentAccessor;
/*    */ import com.ankamagames.framework.kernel.core.resource.ResourceContext;
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
/*    */ public abstract class AleaWorldMapContext
/*    */   extends ResourceContext
/*    */ {
/*    */   private String m_sourceFileName;
/*    */   private DocumentAccessor m_documentAccessor;
/*    */   private int m_x;
/*    */   private int m_y;
/*    */   private int m_width;
/*    */   private int m_height;
/*    */   
/*    */   public String getSourceFileName() {
/* 26 */     return this.m_sourceFileName;
/*    */   }
/*    */   
/*    */   public void setSourceFileName(String sourceFileName) {
/* 30 */     this.m_sourceFileName = sourceFileName;
/*    */   }
/*    */   
/*    */   public DocumentAccessor getDocumentAccessor() {
/* 34 */     return this.m_documentAccessor;
/*    */   }
/*    */   
/*    */   public void setDocumentAccessor(DocumentAccessor documentAccessor) {
/* 38 */     this.m_documentAccessor = documentAccessor;
/*    */   }
/*    */   
/*    */   public void setMapGeometry(int x, int y, int width, int height) {
/* 42 */     this.m_x = x;
/* 43 */     this.m_y = y;
/* 44 */     this.m_width = width;
/* 45 */     this.m_height = height;
/*    */   }
/*    */   
/*    */   public int getX() {
/* 49 */     return this.m_x;
/*    */   }
/*    */   
/*    */   public int getY() {
/* 53 */     return this.m_y;
/*    */   }
/*    */   
/*    */   public int getWidth() {
/* 57 */     return this.m_width;
/*    */   }
/*    */   
/*    */   public int getHeight() {
/* 61 */     return this.m_height;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onCheckIn() {
/* 67 */     this.m_sourceFileName = null;
/* 68 */     this.m_documentAccessor = null;
/* 69 */     this.m_x = Integer.MAX_VALUE;
/* 70 */     this.m_y = Integer.MAX_VALUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onCheckOut() {
/* 75 */     super.onCheckOut();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\alea\AleaWorldMapContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */