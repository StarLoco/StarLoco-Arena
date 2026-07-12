/*     */ package com.ankamagames.framework.kernel.impl.admin;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.common.message.MessageDecoder;
/*     */ import com.ankamagames.framework.kernel.impl.admin.messages.SecureMessage;
/*     */ import com.ankamagames.framework.kernel.impl.admin.messages.clientToServer.LoginMessage;
/*     */ import com.ankamagames.framework.kernel.impl.admin.messages.clientToServer.PropertyListQueryMessage;
/*     */ import com.ankamagames.framework.kernel.impl.admin.messages.clientToServer.PropertyQueryMessage;
/*     */ import com.ankamagames.framework.kernel.impl.admin.messages.serverToClient.LoginErrorMessage;
/*     */ import com.ankamagames.framework.kernel.impl.admin.messages.serverToClient.LoginResultMessage;
/*     */ import com.ankamagames.framework.kernel.impl.admin.messages.serverToClient.PropertyItemMessage;
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
/*     */ 
/*     */ public final class AdminMessageDecoder
/*     */   implements MessageDecoder
/*     */ {
/*  27 */   public static final Logger m_logger = Logger.getLogger(AdminMessageDecoder.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AdminMessageDecoder() {
/*  34 */     AdminMessageCipher.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Message decode(ByteBuffer rawMessage) {
/*  43 */     int len = rawMessage.limit() - rawMessage.position();
/*  44 */     rawMessage.mark();
/*     */     
/*  46 */     if (len >= 7) {
/*  47 */       LoginMessage loginMessage; LoginErrorMessage loginErrorMessage; LoginResultMessage loginResultMessage; PropertyListQueryMessage propertyListQueryMessage; PropertyItemMessage propertyItemMessage; PropertyQueryMessage propertyQueryMessage; int messageLen = rawMessage.getShort() & 0xFFFF;
/*  48 */       int messageId = rawMessage.getInt();
/*  49 */       boolean isSecure = (rawMessage.get() == 1);
/*     */       
/*  51 */       if (messageLen < 7) {
/*  52 */         System.err.println("Message size < 7 bytes!!!");
/*  53 */         rawMessage.reset();
/*  54 */         return null;
/*     */       } 
/*     */       
/*  57 */       if (rawMessage.remaining() < messageLen - 7) {
/*  58 */         rawMessage.reset();
/*  59 */         return null;
/*     */       } 
/*     */       
/*  62 */       byte[] messageDatas = new byte[messageLen - 7];
/*  63 */       rawMessage.get(messageDatas);
/*     */       
/*  65 */       byte[] dmsg = messageDatas;
/*  66 */       if (isSecure) {
/*  67 */         dmsg = AdminMessageCipher.decrypt(messageDatas);
/*     */       }
/*  69 */       SecureMessage message = null;
/*     */       
/*  71 */       switch (messageId) {
/*     */         
/*     */         case 1:
/*  74 */           loginMessage = new LoginMessage();
/*     */           break;
/*     */         
/*     */         case 3:
/*  78 */           loginErrorMessage = new LoginErrorMessage();
/*     */           break;
/*     */         
/*     */         case 2:
/*  82 */           loginResultMessage = new LoginResultMessage();
/*     */           break;
/*     */         
/*     */         case 10:
/*  86 */           propertyListQueryMessage = new PropertyListQueryMessage();
/*     */           break;
/*     */         
/*     */         case 11:
/*  90 */           propertyItemMessage = new PropertyItemMessage();
/*     */           break;
/*     */         
/*     */         case 12:
/*  94 */           propertyQueryMessage = new PropertyQueryMessage();
/*     */           break;
/*     */         
/*     */         default:
/*  98 */           m_logger.error("Unknown message");
/*     */           break;
/*     */       } 
/*     */       
/* 102 */       if (propertyQueryMessage != null) {
/* 103 */         propertyQueryMessage.onCheckOut();
/* 104 */         propertyQueryMessage.decode(dmsg);
/*     */       } 
/*     */       
/* 107 */       return (Message)propertyQueryMessage;
/*     */     } 
/* 109 */     return null;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\impl\admin\AdminMessageDecoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */