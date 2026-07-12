/*    */ package com.ankamagames.framework.sounds;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.java.games.sound3d.Buffer;
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
/*    */ class SoundBankItem
/*    */ {
/*    */   private String m_referenceName;
/*    */   private ArrayList<Buffer> m_buffers;
/* 21 */   private int m_userReferenceCount = 0;
/*    */   
/*    */   public SoundBankItem(String referenceName, ArrayList<Buffer> buffers) {
/* 24 */     this.m_referenceName = referenceName;
/* 25 */     this.m_buffers = buffers;
/*    */   }
/*    */   
/*    */   public String getReferenceName() {
/* 29 */     return this.m_referenceName;
/*    */   }
/*    */   
/*    */   public void setReferenceName(String referenceName) {
/* 33 */     this.m_referenceName = referenceName;
/*    */   }
/*    */   
/*    */   public ArrayList<Buffer> getBuffers() {
/* 37 */     return this.m_buffers;
/*    */   }
/*    */   
/*    */   public void setBuffers(ArrayList<Buffer> buffers) {
/* 41 */     this.m_buffers = buffers;
/*    */   }
/*    */   
/*    */   public synchronized void addUserReference() {
/* 45 */     this.m_userReferenceCount += 1;
/*    */   }
/*    */   
/*    */ 
/*    */   public synchronized void release()
/*    */   {
/* 51 */     if (this.m_userReferenceCount > 0) {
/* 52 */       this.m_userReferenceCount -= 1;
/*    */     }
/* 54 */     if ((this.m_userReferenceCount == 0) && 
/* 55 */       (this.m_buffers != null)) {
/* 56 */       for (Buffer buffer : this.m_buffers)
/* 57 */         SoundManager.getInstance().releaseBuffer(buffer);
/* 58 */       this.m_buffers.clear();
/*    */     }
/*    */   }
/*    */   
/*    */   public synchronized int getUserReferenceCount()
/*    */   {
/* 64 */     return this.m_userReferenceCount;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\sounds\SoundBankItem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */