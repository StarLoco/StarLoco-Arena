/*     */ package com.ankamagames.baseImpl.client.proxyclient.base.network.protocol;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.ConsoleAdminCommandMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.ConsoleAdminCommandResultMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.DefaultResultsMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InvalidClientVersionMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.QueueNotificationMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.ReconnectionTicketMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.ReconnectionTicketRequestResultMessage;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.common.message.MessageDecoder;
/*     */ import java.nio.ByteBuffer;
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
/*     */ public abstract class AbstractClientMessageDecoder
/*     */   implements MessageDecoder
/*     */ {
/*  26 */   protected static Logger m_logger = Logger.getLogger(AbstractClientMessageDecoder.class);
/*     */   public Message decode(ByteBuffer rawMessage) {
/*     */     QueueNotificationMessage queueNotificationMessage;
/*     */     ConsoleAdminCommandMessage consoleAdminCommandMessage;
/*     */     ConsoleAdminCommandResultMessage consoleAdminCommandResultMessage;
/*     */     DefaultResultsMessage defaultResultsMessage;
/*     */     InvalidClientVersionMessage invalidClientVersionMessage;
/*     */     ReconnectionTicketMessage reconnectionTicketMessage;
/*     */     ReconnectionTicketRequestResultMessage reconnectionTicketRequestResultMessage;
/*     */     Message message1;
/*  36 */     rawMessage.mark();
/*  37 */     int availableSize = rawMessage.remaining();
/*     */ 
/*     */     
/*  40 */     if (availableSize < 4) {
/*  41 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  45 */     short msgSize = rawMessage.getShort();
/*     */ 
/*     */     
/*  48 */     if (msgSize < 4) {
/*  49 */       m_logger.error("Décodage impossible car taille trop petite (taille = " + msgSize + ", minimum = 6");
/*  50 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*  54 */     if (msgSize > availableSize) {
/*  55 */       rawMessage.reset();
/*  56 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  61 */     int msgEndPosition = rawMessage.position() + msgSize - 2;
/*     */ 
/*     */     
/*  64 */     short msgType = rawMessage.getShort();
/*     */     
/*  66 */     Message msg = null;
/*  67 */     switch (msgType) {
/*     */ 
/*     */ 
/*     */       
/*     */       case 8192:
/*  72 */         queueNotificationMessage = new QueueNotificationMessage();
/*     */         break;
/*     */       
/*     */       case 8193:
/*  76 */         consoleAdminCommandMessage = new ConsoleAdminCommandMessage();
/*     */         break;
/*     */       
/*     */       case 8194:
/*  80 */         consoleAdminCommandResultMessage = new ConsoleAdminCommandResultMessage();
/*     */         break;
/*     */       
/*     */       case 8195:
/*  84 */         defaultResultsMessage = new DefaultResultsMessage();
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 8:
/*  90 */         invalidClientVersionMessage = new InvalidClientVersionMessage();
/*     */         break;
/*     */       
/*     */       case 2:
/*  94 */         reconnectionTicketMessage = new ReconnectionTicketMessage();
/*     */         break;
/*     */       
/*     */       case 4:
/*  98 */         reconnectionTicketRequestResultMessage = new ReconnectionTicketRequestResultMessage();
/*     */         break;
/*     */       
/*     */       default:
/* 102 */         message1 = createMessageFromType(msgType);
/*     */         break;
/*     */     } 
/* 105 */     if (message1 == null) {
/* 106 */       rawMessage.position(msgEndPosition);
/* 107 */       m_logger.error("Le message type=" + msgType + " inconnu du décodeur !");
/*     */     } 
/*     */     
/* 110 */     if (message1 != null && rawMessage.remaining() != 0) {
/*     */       
/* 112 */       byte[] messageDatas = new byte[msgSize - 4];
/* 113 */       rawMessage.get(messageDatas);
/*     */       try {
/* 115 */         message1.decode(messageDatas);
/* 116 */       } catch (Throwable e) {
/* 117 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     return message1;
/*     */   }
/*     */   
/*     */   protected abstract Message createMessageFromType(int paramInt);
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\AbstractClientMessageDecoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */