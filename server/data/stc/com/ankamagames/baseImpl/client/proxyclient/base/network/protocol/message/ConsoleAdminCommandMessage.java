/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message;
/*    */ 
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
/*    */ public class ConsoleAdminCommandMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/* 18 */   private String m_command = null;
/*    */   
/*    */ 
/*    */   private byte m_serverId;
/*    */   
/*    */ 
/*    */ 
/*    */   public byte[] encode()
/*    */   {
/* 27 */     byte[] str = this.m_command.getBytes();
/* 28 */     ByteBuffer bb = ByteBuffer.allocate(str.length + 1);
/* 29 */     bb.put((byte)str.length);
/* 30 */     bb.put(str);
/* 31 */     return addClientHeader(this.m_serverId, bb.array());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 40 */     return 8193;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setCommand(String command)
/*    */   {
/* 48 */     this.m_command = command;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setServerId(byte serverId)
/*    */   {
/* 55 */     this.m_serverId = serverId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\ConsoleAdminCommandMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */