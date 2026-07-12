/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.exchange;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UIExchangeInvitationRejectRequestMessage
/*    */   extends UIMessage
/*    */ {
/* 19 */   private static final ObjectPool m_pool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<UIExchangeInvitationRejectRequestMessage>() {
/*    */         public UIExchangeInvitationRejectRequestMessage makeObject() {
/* 21 */           return new UIExchangeInvitationRejectRequestMessage(null);
/*    */         }
/*    */       });
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private long m_invitationId;
/*    */ 
/*    */ 
/*    */   
/*    */   private UIExchangeInvitationRejectRequestMessage() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public static UIExchangeInvitationRejectRequestMessage checkOut() {
/*    */     UIExchangeInvitationRejectRequestMessage msg;
/*    */     try {
/* 39 */       msg = (UIExchangeInvitationRejectRequestMessage)m_pool.borrowObject();
/* 40 */       msg.setPool(m_pool);
/* 41 */     } catch (Exception e) {
/* 42 */       msg = new UIExchangeInvitationRejectRequestMessage();
/* 43 */       m_logger.error("Erreur lors d'un checkOut sur un message de type UIRejectFightInvitationRequestMessage : " + e.getMessage());
/*    */     } 
/* 45 */     return msg;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 55 */     return 16802;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getInvitationId() {
/* 62 */     return this.m_invitationId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setInvitationId(long exchangeId) {
/* 69 */     this.m_invitationId = exchangeId;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\message\exchange\UIExchangeInvitationRejectRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */