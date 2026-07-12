/*    */ package com.ankamagames.framework.kernel.impl.admin.messages.serverToClient;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.impl.admin.messages.SecureMessage;
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
/*    */ public class LoginResultMessage
/*    */   extends SecureMessage
/*    */ {
/*    */   private boolean m_successful;
/*    */   
/*    */   public byte[] encode() {
/* 26 */     return crypt(new byte[] { (byte)(this.m_successful ? 1 : 0) });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 35 */     this.m_successful = (rawDatas[0] == 1);
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 45 */     return 2;
/*    */   }
/*    */   
/*    */   public boolean isSuccessful() {
/* 49 */     return this.m_successful;
/*    */   }
/*    */   
/*    */   public void setSuccessful(boolean successful) {
/* 53 */     this.m_successful = successful;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\impl\admin\messages\serverToClient\LoginResultMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */