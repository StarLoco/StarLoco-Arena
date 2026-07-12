/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.FightActionMessage;
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
/*    */ 
/*    */ 
/*    */ public class EffectAreaActionMessage
/*    */   extends FightActionMessage
/*    */ {
/*    */   private long m_areaId;
/*    */   private long m_targetId;
/*    */   private boolean m_apply;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 30 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/* 31 */     decodeFightActionHeader(buffer);
/* 32 */     this.m_apply = (buffer.get() == 1);
/* 33 */     this.m_areaId = buffer.getLong();
/* 34 */     this.m_targetId = buffer.getLong();
/* 35 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 44 */     return 6200;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getActionId()
/*    */   {
/* 53 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public FightActionType getFightActionType()
/*    */   {
/* 62 */     return FightActionType.EFFEC_AREA_ACTION;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isApply()
/*    */   {
/* 69 */     return this.m_apply;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getAreaId()
/*    */   {
/* 76 */     return this.m_areaId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getTargetId()
/*    */   {
/* 83 */     return this.m_targetId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\fight\EffectAreaActionMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */