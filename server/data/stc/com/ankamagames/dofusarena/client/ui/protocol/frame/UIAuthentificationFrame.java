/*    */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.proxy.ProxyGroup;
/*    */ import com.ankamagames.baseImpl.graphicalClient.ui.progress.ProgressMonitor;
/*    */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*    */ import com.ankamagames.dofusarena.client.core.preferences.DofusArenaGamePreferences;
/*    */ import com.ankamagames.dofusarena.client.ui.progress.DofusArenaProgressMonitorManager;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.connection.UIChangeLanguageRequestMessage;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.connection.UILogonRequestMessage;
/*    */ import com.ankamagames.framework.kernel.FrameHandler;
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*    */ import com.ankamagames.framework.kernel.events.MessageFrame;
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
/*    */ public class UIAuthentificationFrame
/*    */   implements MessageFrame
/*    */ {
/* 28 */   private static UIAuthentificationFrame m_instance = new UIAuthentificationFrame();
/*    */   
/*    */ 
/*    */ 
/*    */   public static UIAuthentificationFrame getInstance()
/*    */   {
/* 34 */     return m_instance;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean onMessage(Message message)
/*    */   {
/* 43 */     switch (message.getId())
/*    */     {
/*    */ 
/*    */     case 16385: 
/* 47 */       UILogonRequestMessage msg = (UILogonRequestMessage)message;
/* 48 */       DofusArenaGameEntity gameEntity = DofusArenaGameEntity.getInstance();
/*    */       
/*    */ 
/* 51 */       DofusArenaClientInstance.getInstance().getGamePreferences().setRememberLastLogin(msg.getRemember().booleanValue());
/* 52 */       DofusArenaClientInstance.getInstance().getGamePreferences().setLastLogin(msg.getRemember().booleanValue() ? msg.getLogin() : "");
/*    */       
/*    */ 
/* 55 */       gameEntity.setLogin(msg.getLogin());
/* 56 */       gameEntity.setPassword(msg.getPassword());
/* 57 */       gameEntity.setLogged(false);
/*    */       
/*    */ 
/* 60 */       ProxyGroup proxyGroup = msg.getProxyGroup();
/* 61 */       gameEntity.setProxyGroup(proxyGroup);
/*    */       
/*    */ 
/* 64 */       proxyGroup.clearRandomIterator();
/*    */       
/*    */ 
/* 67 */       DofusArenaProgressMonitorManager.getInstance().getProgressMonitor(true).beginTask(DofusArenaTranslator.getInstance().getString("logon.progress", new Object[0]), 0);
/*    */       
/*    */ 
/* 70 */       gameEntity.connect();
/*    */       
/* 72 */       return false;
/*    */     
/*    */ 
/*    */ 
/*    */     case 16384: 
/* 77 */       UIChangeLanguageRequestMessage msg = (UIChangeLanguageRequestMessage)message;
/*    */       
/*    */ 
/* 80 */       DofusArenaTranslator.getInstance().setLanguage(msg.getLanguage());
/*    */       
/*    */ 
/* 83 */       DofusArenaClientInstance.getInstance().cleanUp();
/*    */       
/* 85 */       return false;
/*    */     }
/*    */     
/* 88 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public long getId()
/*    */   {
/* 97 */     return 0L;
/*    */   }
/*    */   
/*    */   public void setId(long id) {}
/*    */   
/*    */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*    */   
/*    */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UIAuthentificationFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */