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
/*    */ public class UIExchangeInvitationAcceptRequestMessage
/*    */   extends UIMessage
/*    */ {
/* 19 */   private static final ObjectPool m_pool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<UIExchangeInvitationAcceptRequestMessage>() {
/*    */         public UIExchangeInvitationAcceptRequestMessage makeObject() {
/* 21 */           return new UIExchangeInvitationAcceptRequestMessage(null);
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
/*    */   private UIExchangeInvitationAcceptRequestMessage() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public static UIExchangeInvitationAcceptRequestMessage checkOut() {
/*    */     UIExchangeInvitationAcceptRequestMessage msg;
/*    */     try {
/* 39 */       msg = (UIExchangeInvitationAcceptRequestMessage)m_pool.borrowObject();
/* 40 */       msg.setPool(m_pool);
/* 41 */     } catch (Exception e) {
/* 42 */       msg = new UIExchangeInvitationAcceptRequestMessage();
/* 43 */       m_logger.error("Erreur lors d'un checkOut sur un message de type UIExchangeInvitationAcceptRequestMessage : " + e.getMessage());
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
/* 55 */     return 16801;
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


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\message\exchange\UIExchangeInvitationAcceptRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */