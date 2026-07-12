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
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Message decode(ByteBuffer rawMessage)
/*     */   {
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
/*  67 */     switch (msgType)
/*     */     {
/*     */ 
/*     */ 
/*     */     case 8192: 
/*  72 */       msg = new QueueNotificationMessage();
/*  73 */       break;
/*     */     
/*     */     case 8193: 
/*  76 */       msg = new ConsoleAdminCommandMessage();
/*  77 */       break;
/*     */     
/*     */     case 8194: 
/*  80 */       msg = new ConsoleAdminCommandResultMessage();
/*  81 */       break;
/*     */     
/*     */     case 8195: 
/*  84 */       msg = new DefaultResultsMessage();
/*  85 */       break;
/*     */     
/*     */ 
/*     */ 
/*     */     case 8: 
/*  90 */       msg = new InvalidClientVersionMessage();
/*  91 */       break;
/*     */     
/*     */     case 2: 
/*  94 */       msg = new ReconnectionTicketMessage();
/*  95 */       break;
/*     */     
/*     */     case 4: 
/*  98 */       msg = new ReconnectionTicketRequestResultMessage();
/*  99 */       break;
/*     */     
/*     */     default: 
/* 102 */       msg = createMessageFromType(msgType);
/*     */     }
/*     */     
/* 105 */     if (msg == null) {
/* 106 */       rawMessage.position(msgEndPosition);
/* 107 */       m_logger.error("Le message type=" + msgType + " inconnu du décodeur !");
/*     */     }
/*     */     
/* 110 */     if ((msg != null) && (rawMessage.remaining() != 0))
/*     */     {
/* 112 */       byte[] messageDatas = new byte[msgSize - 4];
/* 113 */       rawMessage.get(messageDatas);
/*     */       try {
/* 115 */         msg.decode(messageDatas);
/*     */       } catch (Throwable e) {
/* 117 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/* 121 */     return msg;
/*     */   }
/*     */   
/*     */   protected abstract Message createMessageFromType(int paramInt);
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\AbstractClientMessageDecoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */