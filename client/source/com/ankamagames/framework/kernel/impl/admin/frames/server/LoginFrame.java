/*     */ package com.ankamagames.framework.kernel.impl.admin.frames.server;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.framework.kernel.impl.admin.entity.AdminEntity;
/*     */ import com.ankamagames.framework.kernel.impl.admin.messages.clientToServer.LoginMessage;
/*     */ import com.ankamagames.framework.kernel.impl.admin.messages.serverToClient.LoginErrorMessage;
/*     */ import com.ankamagames.framework.kernel.impl.admin.messages.serverToClient.LoginResultMessage;
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
/*     */ 
/*     */ 
/*     */ public class LoginFrame
/*     */   implements MessageFrame
/*     */ {
/*  26 */   private static final Logger m_logger = Logger.getLogger(LoginFrame.class);
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
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMessage(Message message) {
/*     */     LoginMessage msg;
/*  59 */     boolean bForward = true;
/*  60 */     AdminEntity entity = (AdminEntity)message.getHandler();
/*     */     
/*  62 */     switch (message.getId())
/*     */     
/*     */     { case 1:
/*  65 */         msg = (LoginMessage)message;
/*     */         
/*  67 */         m_logger.info("User auth : l=" + msg.getLogin() + " / p=" + msg.getPassword());
/*     */         
/*  69 */         if (msg.getLogin().equals("seb") && msg.getPassword().equals("pass")) {
/*  70 */           LoginResultMessage results = new LoginResultMessage();
/*  71 */           results.setSuccessful(true);
/*  72 */           entity.sendMessage((Message)results);
/*     */           
/*  74 */           entity.removeAllFrames();
/*  75 */           entity.pushFrame(new AdminFrame());
/*     */         } else {
/*     */           
/*  78 */           LoginErrorMessage error = new LoginErrorMessage();
/*  79 */           error.setErrorCode((byte)1);
/*  80 */           entity.sendMessage((Message)error);
/*     */         } 
/*     */ 
/*     */         
/*  84 */         bForward = false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 103 */         return bForward; }  if (!entity.isAuthenticationRequested()) { entity.setAuthenticationRequested(true); LoginErrorMessage error = new LoginErrorMessage(); error.setErrorCode((byte)2); entity.sendMessage((Message)error); }  bForward = false; return bForward;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 112 */     return 1L;
/*     */   }
/*     */   
/*     */   public void setId(long id) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\impl\admin\frames\server\LoginFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */