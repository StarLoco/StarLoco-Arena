/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
/*    */ import com.ankamagames.dofusarena.common.game.fight.FightActionType;
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
/*    */ public abstract class FightActionMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   protected static final int FIGHT_ACTION_HEADER_SIZE = 8;
/*    */   private int m_uniqueId;
/* 24 */   private int m_triggeringActionUniqueId = -1;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract int getActionId();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract FightActionType getFightActionType();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void decodeFightActionHeader(ByteBuffer buff)
/*    */   {
/* 43 */     this.m_uniqueId = buff.getInt();
/* 44 */     this.m_triggeringActionUniqueId = buff.getInt();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getUniqueId()
/*    */   {
/* 53 */     return this.m_uniqueId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getTriggeringActionUniqueId()
/*    */   {
/* 60 */     return this.m_triggeringActionUniqueId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\action\FightActionMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */