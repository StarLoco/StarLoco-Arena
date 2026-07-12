/*    */ package com.ankamagames.framework.kernel.impl.admin.messages;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*    */ import com.ankamagames.framework.kernel.impl.admin.AdminMessageCipher;
/*    */ import java.io.PrintStream;
/*    */ import java.nio.ByteBuffer;
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
/*    */ public abstract class SecureMessage
/*    */   extends Message
/*    */ {
/*    */   public byte[] crypt(byte[] datas)
/*    */   {
/* 27 */     byte[] cryptedDatas = datas;
/* 28 */     if (isSecure()) {
/* 29 */       cryptedDatas = AdminMessageCipher.crypt(datas);
/*    */     }
/* 31 */     int messageLen = cryptedDatas.length + 2 + 4 + 1;
/*    */     
/* 33 */     if ((messageLen <= 7) && (messageLen > 32767)) {
/* 34 */       System.err.println("Longueur de message incorrecte : " + messageLen);
/*    */     }
/*    */     
/* 37 */     ByteBuffer buffer = ByteBuffer.allocate(messageLen);
/* 38 */     buffer.putShort((short)messageLen);
/* 39 */     buffer.putInt(getId());
/* 40 */     buffer.put((byte)(isSecure() ? 1 : 0));
/* 41 */     buffer.put(cryptedDatas);
/*    */     
/* 43 */     return buffer.array();
/*    */   }
/*    */   
/*    */ 
/*    */   public void setId(int id) {}
/*    */   
/*    */ 
/*    */   public void onCheckOut() {}
/*    */   
/*    */   public void onCheckIn() {}
/*    */   
/*    */   public boolean isSecure()
/*    */   {
/* 56 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\impl\admin\messages\SecureMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */