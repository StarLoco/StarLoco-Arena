/*    */ package com.ankamagames.baseImpl.common.clientAndServer.protocol.sql;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
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
/*    */ public abstract class ResultMessage
/*    */   extends Message
/*    */ {
/*    */   private byte m_result;
/*    */   private String m_errorMessage;
/*    */   public static final byte RESULT_NOT_SET = 0;
/*    */   public static final byte RESULT_LOAD_SUCCESS = 1;
/*    */   public static final byte RESULT_SAVE_SUCCESS = 2;
/*    */   public static final byte RESULT_LOAD_ERROR = 3;
/*    */   public static final byte RESULT_SAVE_ERROR = 4;
/*    */   public static final int ID = 1024;
/*    */   
/*    */   public byte[] encode()
/*    */   {
/* 35 */     return new byte[0];
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 44 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 53 */     return 1024;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setId(int id) {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onCheckOut()
/*    */   {
/* 66 */     this.m_result = 0;
/* 67 */     this.m_errorMessage = "";
/*    */   }
/*    */   
/*    */   public void onCheckIn() {}
/*    */   
/*    */   public byte getResult()
/*    */   {
/* 74 */     return this.m_result;
/*    */   }
/*    */   
/*    */   public void setResult(byte result) {
/* 78 */     this.m_result = result;
/*    */   }
/*    */   
/*    */   public String getErrorMessage() {
/* 82 */     return this.m_errorMessage;
/*    */   }
/*    */   
/*    */   public void setErrorMessage(String errorMessage) {
/* 86 */     this.m_errorMessage = errorMessage;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\protocol\sql\ResultMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */