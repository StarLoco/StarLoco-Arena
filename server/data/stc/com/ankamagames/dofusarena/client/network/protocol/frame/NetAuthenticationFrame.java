/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.connection.serverToClient.ClientAuthenticationResultsMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIAuthentificationFrame;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.property.PropertiesProvider;
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
/*     */ public class NetAuthenticationFrame
/*     */   implements MessageFrame
/*     */ {
/*  30 */   protected static final Logger m_logger = Logger.getLogger(NetAuthenticationFrame.class);
/*     */   
/*  32 */   private static NetAuthenticationFrame m_instance = new NetAuthenticationFrame();
/*     */   
/*     */ 
/*     */ 
/*     */   public static NetAuthenticationFrame getInstance()
/*     */   {
/*  38 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  47 */     switch (message.getId())
/*     */     {
/*     */     case 1024: 
/*  50 */       ClientAuthenticationResultsMessage msg = (ClientAuthenticationResultsMessage)message;
/*  51 */       if (msg.isSuccessfull())
/*     */       {
/*     */ 
/*  54 */         DofusArenaGameEntity.getInstance().setLogged(true);
/*     */         
/*     */ 
/*  57 */         DofusArenaGameEntity.getInstance().removeFrame(this);
/*  58 */         DofusArenaGameEntity.getInstance().removeFrame(UIAuthentificationFrame.getInstance());
/*     */         
/*     */ 
/*  61 */         DofusArenaGameEntity.getInstance().pushFrame(NetInstanceFrame.getInstance());
/*  62 */         DofusArenaGameEntity.getInstance().pushFrame(NetCoachFrame.getInstance());
/*  63 */         DofusArenaGameEntity.getInstance().pushFrame(NetChatFrame.getInstance());
/*     */         
/*     */ 
/*  66 */         Xulor.getInstance().unload("logonDialog");
/*     */         
/*     */ 
/*  69 */         PropertiesProvider propertiesProvider = Xulor.getInstance().getEnvironment().getPropertiesProvider();
/*  70 */         propertiesProvider.removeProperty("account.name");
/*  71 */         propertiesProvider.removeProperty("account.password");
/*  72 */         propertiesProvider.removeProperty("account.remember");
/*  73 */         propertiesProvider.removeProperty("proxy.list");
/*  74 */         propertiesProvider.removeProperty("proxy.selected");
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*  79 */         DofusArenaGameEntity.getInstance().setLogin(null);
/*  80 */         DofusArenaGameEntity.getInstance().setPassword(null);
/*  81 */         DofusArenaGameEntity.getInstance().setLogged(false);
/*     */         String errorString;
/*     */         String errorString;
/*     */         String errorString;
/*  85 */         String errorString; String errorString; switch (msg.getErrorCode() & 0xFF) {
/*     */         case 2: 
/*  87 */           errorString = "error.connection.invalidLogin";
/*  88 */           break;
/*     */         case 3: 
/*  90 */           errorString = "error.connection.alreadyConnected";
/*  91 */           break;
/*     */         case 4: 
/*  93 */           errorString = "error.connection.saveInProgress";
/*  94 */           break;
/*     */         case 127: 
/*  96 */           errorString = "error.connection.closedBeta";
/*  97 */           break;
/*     */         default: 
/*  99 */           errorString = "error.connection.invalidLogin";
/*     */         }
/* 101 */         Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString(errorString, new Object[0]), 67);
/*     */         
/*     */ 
/* 104 */         DofusArenaGameEntity.getInstance().getNetworkEntity().closeConnection();
/*     */       }
/*     */       
/* 107 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 1026: 
/* 113 */       DofusArenaGameEntity.getInstance().setLogin(null);
/* 114 */       DofusArenaGameEntity.getInstance().setPassword(null);
/* 115 */       DofusArenaGameEntity.getInstance().setLogged(false);
/*     */       
/*     */ 
/* 118 */       Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("error.connection.worldLoading", new Object[0]), 67);
/*     */       
/*     */ 
/* 121 */       DofusArenaGameEntity.getInstance().getNetworkEntity().closeConnection();
/*     */       
/* 123 */       return false;
/*     */     }
/*     */     
/*     */     
/* 127 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 136 */     return 0L;
/*     */   }
/*     */   
/*     */   public void setId(long id) {}
/*     */   
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*     */   
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetAuthenticationFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */