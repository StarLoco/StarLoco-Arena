/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.connection.clientToServer;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.OutputOnlyProxyMessage;
/*    */ import com.ankamagames.dofusarena.common.constants.Version;
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
/*    */ public class ClientVersionMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   public byte[] encode()
/*    */   {
/* 29 */     ByteBuffer bb = ByteBuffer.allocate(4 + Version.BUILD_VERSION.length());
/* 30 */     bb.put((byte)2);
/* 31 */     bb.putShort((short)4);
/* 32 */     bb.put((byte)Version.BUILD_VERSION.length());
/* 33 */     bb.put(Version.BUILD_VERSION.getBytes());
/*    */     
/* 35 */     return addClientHeader((byte)0, bb.array());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 46 */     return 7;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\connection\clientToServer\ClientVersionMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */