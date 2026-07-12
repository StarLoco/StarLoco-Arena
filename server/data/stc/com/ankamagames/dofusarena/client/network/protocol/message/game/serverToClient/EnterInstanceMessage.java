/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
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
/*    */ public class EnterInstanceMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private float m_worldX;
/*    */   private float m_worldY;
/*    */   private short m_altitude;
/*    */   private short m_instanceID;
/*    */   private boolean m_dynamic;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 32 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 34 */     this.m_worldX = buffer.getFloat();
/* 35 */     this.m_worldY = buffer.getFloat();
/* 36 */     this.m_altitude = buffer.getShort();
/* 37 */     this.m_instanceID = buffer.getShort();
/* 38 */     this.m_dynamic = (buffer.get() == 1);
/*    */     
/* 40 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 49 */     return 4600;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public short getAltitude()
/*    */   {
/* 56 */     return this.m_altitude;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public short getInstanceID()
/*    */   {
/* 63 */     return this.m_instanceID;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public float getWorldX()
/*    */   {
/* 70 */     return this.m_worldX;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public float getWorldY()
/*    */   {
/* 77 */     return this.m_worldY;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isDynamic()
/*    */   {
/* 85 */     return this.m_dynamic;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\EnterInstanceMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */