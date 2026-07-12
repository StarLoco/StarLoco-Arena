/*     */ package com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.core.ProxyClientEntity;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.ConsoleAdminCommandResultMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.DefaultResultsMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InvalidClientVersionMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.QueueNotificationMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.ReconnectionTicketMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.ReconnectionTicketRequestResultMessage;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetBasicsFrame
/*     */   implements MessageFrame
/*     */ {
/*  27 */   private static Logger m_logger = Logger.getLogger(NetBasicsFrame.class);
/*     */ 
/*     */   
/*     */   private ProxyClientEntity m_proxyClientEntity;
/*     */ 
/*     */ 
/*     */   
/*     */   public NetBasicsFrame(ProxyClientEntity proxyClientEntity) {
/*  35 */     this.m_proxyClientEntity = proxyClientEntity;
/*     */   } public boolean onMessage(Message message) {
/*     */     ReconnectionTicketMessage reconnectionTicketMessage;
/*     */     ReconnectionTicketRequestResultMessage reconnectionTicketRequestResultMessage;
/*     */     InvalidClientVersionMessage invalidClientVersionMessage;
/*     */     QueueNotificationMessage queueNotificationMessage;
/*     */     ConsoleAdminCommandResultMessage consoleAdminCommandResultMessage;
/*     */     DefaultResultsMessage msg;
/*     */     int position;
/*  44 */     switch (message.getId()) {
/*     */ 
/*     */       
/*     */       case 2:
/*  48 */         reconnectionTicketMessage = (ReconnectionTicketMessage)message;
/*     */ 
/*     */         
/*  51 */         this.m_proxyClientEntity.setTicket(reconnectionTicketMessage.getTicket());
/*     */         
/*  53 */         return false;
/*     */ 
/*     */       
/*     */       case 4:
/*  57 */         reconnectionTicketRequestResultMessage = (ReconnectionTicketRequestResultMessage)message;
/*     */ 
/*     */         
/*  60 */         if (!reconnectionTicketRequestResultMessage.isSuccess()) {
/*  61 */           m_logger.error("Reco impossible");
/*     */         }
/*  63 */         return false;
/*     */ 
/*     */       
/*     */       case 8:
/*  67 */         invalidClientVersionMessage = (InvalidClientVersionMessage)message;
/*  68 */         this.m_proxyClientEntity.onInvalidClientVersion(invalidClientVersionMessage.getNeededVersion());
/*  69 */         return false;
/*     */ 
/*     */ 
/*     */       
/*     */       case 8192:
/*  74 */         queueNotificationMessage = (QueueNotificationMessage)message;
/*     */ 
/*     */         
/*  77 */         position = queueNotificationMessage.getPosition();
/*  78 */         if (position == -1) {
/*  79 */           this.m_proxyClientEntity.onQueueFinished();
/*     */         } else {
/*  81 */           this.m_proxyClientEntity.onQueuePositionUpdate(position);
/*     */         } 
/*  83 */         return false;
/*     */ 
/*     */ 
/*     */       
/*     */       case 8194:
/*  88 */         consoleAdminCommandResultMessage = (ConsoleAdminCommandResultMessage)message;
/*     */         
/*  90 */         switch (consoleAdminCommandResultMessage.getMessageType())
/*     */         { case 0:
/*  92 */             ConsoleManager.getInstance().trace(consoleAdminCommandResultMessage.getMessage());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 107 */             return false;case 1: ConsoleManager.getInstance().log(consoleAdminCommandResultMessage.getMessage()); return false;case 2: ConsoleManager.getInstance().err(consoleAdminCommandResultMessage.getMessage()); return false; }  m_logger.error("Type de message inconnu " + consoleAdminCommandResultMessage.getMessageType()); return false;
/*     */ 
/*     */ 
/*     */       
/*     */       case 8195:
/* 112 */         msg = (DefaultResultsMessage)message;
/*     */ 
/*     */         
/* 115 */         this.m_proxyClientEntity.onQueryResult(msg.getQueryResultCode());
/*     */         
/* 117 */         return false;
/*     */     } 
/*     */ 
/*     */     
/* 121 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 130 */     return 0L;
/*     */   }
/*     */   
/*     */   public void setId(long id) {}
/*     */   
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*     */   
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\frame\NetBasicsFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */