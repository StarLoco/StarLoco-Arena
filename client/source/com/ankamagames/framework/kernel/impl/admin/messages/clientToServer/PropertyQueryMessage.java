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
/*    */ public class PropertyQueryMessage
/*    */   extends SecureMessage
/*    */ {
/*    */   private String m_propertyName;
/*    */   
/*    */   public byte[] encode() {
/* 29 */     byte[] propertyName = this.m_propertyName.getBytes();
/*    */     
/* 31 */     ByteBuffer buffer = ByteBuffer.allocate(1 + propertyName.length);
/*    */     
/* 33 */     buffer.put((byte)propertyName.length);
/* 34 */     buffer.put(propertyName);
/*    */     
/* 36 */     return crypt(buffer.array());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 45 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/* 46 */     byte[] propertyName = new byte[buffer.get() & 0xFF]; buffer.get(propertyName);
/* 47 */     this.m_propertyName = new String(propertyName);
/* 48 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 57 */     return 12;
/*    */   }
/*    */   
/*    */   public String getPropertyName() {
/* 61 */     return this.m_propertyName;
/*    */   }
/*    */   
/*    */   public void setPropertyName(String propertyName) {
/* 65 */     this.m_propertyName = propertyName;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\impl\admin\messages\clientToServer\PropertyQueryMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */