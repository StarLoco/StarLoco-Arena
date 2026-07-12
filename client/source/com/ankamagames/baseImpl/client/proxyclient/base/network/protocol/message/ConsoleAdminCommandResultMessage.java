/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.utils.StringUtils;
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
/*    */ public class ConsoleAdminCommandResultMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   public static final byte TRACE = 0;
/*    */   public static final byte LOG = 1;
/*    */   public static final byte ERROR = 2;
/*    */   private byte m_messageType;
/* 26 */   private String m_message = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 35 */     if (!checkMessageSize(rawDatas.length, 3, false)) {
/* 36 */       return false;
/*    */     }
/* 38 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/* 39 */     this.m_messageType = bb.get();
/* 40 */     Short size = Short.valueOf(bb.getShort());
/* 41 */     byte[] str = new byte[size.shortValue() & 0xFFFF];
/* 42 */     bb.get(str);
/* 43 */     this.m_message = StringUtils.fromUTF8(str);
/*    */     
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 54 */     return 8194;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getMessageType() {
/* 61 */     return this.m_messageType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 68 */     return this.m_message;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\ConsoleAdminCommandResultMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */