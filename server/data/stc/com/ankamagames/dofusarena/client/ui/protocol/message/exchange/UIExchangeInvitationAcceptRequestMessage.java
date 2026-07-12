/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.exchange;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.log4j.Logger;
/*    */ 
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
/* 19 */   private static final ObjectPool m_pool = new MonitoredPool(new ObjectFactory() {
/*    */     public UIExchangeInvitationAcceptRequestMessage makeObject() {
/* 21 */       return new UIExchangeInvitationAcceptRequestMessage(null);
/*    */     }
/* 19 */   });
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private long m_invitationId;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static UIExchangeInvitationAcceptRequestMessage checkOut()
/*    */   {
/*    */     UIExchangeInvitationAcceptRequestMessage msg;
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */     try
/*    */     {
/* 39 */       UIExchangeInvitationAcceptRequestMessage msg = (UIExchangeInvitationAcceptRequestMessage)m_pool.borrowObject();
/* 40 */       msg.setPool(m_pool);
/*    */     } catch (Exception e) {
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
/*    */   public int getId()
/*    */   {
/* 55 */     return 16801;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getInvitationId()
/*    */   {
/* 62 */     return this.m_invitationId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setInvitationId(long exchangeId)
/*    */   {
/* 69 */     this.m_invitationId = exchangeId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\message\exchange\UIExchangeInvitationAcceptRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */