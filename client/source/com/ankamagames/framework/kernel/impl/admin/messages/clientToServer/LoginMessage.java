/*    */ package com.ankamagames.framework.kernel.impl.admin.messages.clientToServer;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.impl.admin.messages.SecureMessage;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LoginMessage
/*    */   extends SecureMessage
/*    */ {
/*    */   private String m_login;
/*    */   private String m_password;
/*    */   
/*    */   public byte[] encode() {
/* 30 */     byte[] login = this.m_login.getBytes();
/* 31 */     byte[] password = this.m_password.getBytes();
/*    */     
/* 33 */     ByteBuffer buffer = ByteBuffer.allocate(1 + login.length + 1 + password.length);
/*    */     
/* 35 */     buffer.put((byte)login.length);
/* 36 */     buffer.put(login);
/*    */     
/* 38 */     buffer.put((byte)password.length);
/* 39 */     buffer.put(password);
/*    */     
/* 41 */     return crypt(buffer.array());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 51 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 53 */     byte[] login = new byte[buffer.get()];
/* 54 */     buffer.get(login);
/* 55 */     this.m_login = new String(login);
/*    */     
/* 57 */     byte[] password = new byte[buffer.get()];
/* 58 */     buffer.get(password);
/* 59 */     this.m_password = new String(password);
/*    */     
/* 61 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 70 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLogin() {
/* 75 */     return this.m_login;
/*    */   }
/*    */   
/*    */   public void setLogin(String login) {
/* 79 */     this.m_login = login;
/*    */   }
/*    */   
/*    */   public String getPassword() {
/* 83 */     return this.m_password;
/*    */   }
/*    */   
/*    */   public void setPassword(String password) {
/* 87 */     this.m_password = password;
/*    */   }
/*    */   
/*    */   public boolean isSecure() {
/* 91 */     return true;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\impl\admin\messages\clientToServer\LoginMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */