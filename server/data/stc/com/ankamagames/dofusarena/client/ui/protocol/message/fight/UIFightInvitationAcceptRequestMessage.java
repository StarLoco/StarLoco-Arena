/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.fight;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UIFightInvitationAcceptRequestMessage
/*    */   extends UIMessage
/*    */ {
/* 23 */   private static final ObjectPool m_pool = new MonitoredPool(new ObjectFactory() {
/*    */     public UIFightInvitationAcceptRequestMessage makeObject() {
/* 25 */       return new UIFightInvitationAcceptRequestMessage(null);
/*    */     }
/* 23 */   });
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
/*    */   public static UIFightInvitationAcceptRequestMessage checkOut()
/*    */   {
/*    */     UIFightInvitationAcceptRequestMessage msg;
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */     try
/*    */     {
/* 43 */       UIFightInvitationAcceptRequestMessage msg = (UIFightInvitationAcceptRequestMessage)m_pool.borrowObject();
/* 44 */       msg.setPool(m_pool);
/*    */     } catch (Exception e) {
/* 46 */       msg = new UIFightInvitationAcceptRequestMessage();
/* 47 */       m_logger.error("Erreur lors d'un checkOut sur un message de type UIAcceptFightInvitationRequestMessage : " + e.getMessage());
/*    */     }
/* 49 */     return msg;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 59 */     return 16500;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getInvitationId()
/*    */   {
/* 66 */     return this.m_invitationId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setInvitationId(long battleId)
/*    */   {
/* 73 */     this.m_invitationId = battleId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\message\fight\UIFightInvitationAcceptRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */