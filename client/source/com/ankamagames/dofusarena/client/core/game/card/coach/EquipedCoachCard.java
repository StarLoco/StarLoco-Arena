/*    */ package com.ankamagames.dofusarena.client.core.game.card.coach;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.common.GUIDGenerator;
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
/*    */ public class EquipedCoachCard
/*    */   extends CoachCard
/*    */ {
/*    */   private long m_referenceUniqueId;
/*    */   
/*    */   public long getReferenceUniqueId() {
/* 24 */     return this.m_referenceUniqueId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] serialize() {
/* 34 */     return super.serialize();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean unserialize(ByteBuffer buf) {
/* 44 */     boolean success = super.unserialize(buf);
/* 45 */     this.m_referenceUniqueId = getUniqueId();
/* 46 */     this.m_uid = GUIDGenerator.getGUID();
/* 47 */     setQuantity((short)1);
/* 48 */     return success;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\card\coach\EquipedCoachCard.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */