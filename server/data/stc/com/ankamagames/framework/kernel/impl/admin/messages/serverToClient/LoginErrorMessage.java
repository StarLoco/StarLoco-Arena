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
/*    */ public class LoginErrorMessage
/*    */   extends SecureMessage
/*    */ {
/*    */   private byte m_errorCode;
/*    */   
/*    */   public byte[] encode()
/*    */   {
/* 26 */     return crypt(new byte[] { this.m_errorCode });
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 35 */     this.m_errorCode = rawDatas[0];
/* 36 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 45 */     return 3;
/*    */   }
/*    */   
/*    */   public byte getErrorCode() {
/* 49 */     return this.m_errorCode;
/*    */   }
/*    */   
/*    */   public void setErrorCode(byte errorCode) {
/* 53 */     this.m_errorCode = errorCode;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\impl\admin\messages\serverToClient\LoginErrorMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */