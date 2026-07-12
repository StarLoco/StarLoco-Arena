/*     */ package com.ankamagames.dofusarena.client.core;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.DisconnectionNotificationMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.proxy.ProxyGroup;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.FightingTeam;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.TeamMate;
/*     */ import com.ankamagames.baseImpl.graphicalClient.core.GameEntity;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldManager;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.Coach;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.FighterManager;
/*     */ import com.ankamagames.dofusarena.client.core.game.team.TeamPresetManager;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.connection.clientToServer.ClientAuthenticationMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.connection.clientToServer.ClientVersionMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.progress.DofusArenaProgressMonitorManager;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIBasicsFrame;
/*     */ import com.ankamagames.dofusarena.common.constants.Version;
/*     */ import com.ankamagames.framework.ai.dataProvider.CellInformationProvider;
/*     */ import com.ankamagames.framework.fileFormat.properties.PropertyException;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.messagebox.IMessageBoxEventListener;
/*     */ import com.ankamagames.xulor.core.messagebox.MessageBoxControler;
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
/*     */ public class DofusArenaGameEntity
/*     */   extends GameEntity
/*     */ {
/*  44 */   private static DofusArenaGameEntity m_instance = new DofusArenaGameEntity();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   private LocalCoach m_localCoach = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private Fight m_fight = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean m_randomFightSearchInProgress = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DofusArenaGameEntity getInstance() {
/*  65 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DofusArenaGameEntity() {
/*     */     try {
/*  73 */       int maxConnectionRetries = DofusArenaConfiguration.getInstance().getInteger("connectionRetryCount");
/*  74 */       int connectionRetryDelay = DofusArenaConfiguration.getInstance().getInteger("connectionRetryDelay");
/*  75 */       setConnectionRetryParameters(maxConnectionRetries, connectionRetryDelay);
/*  76 */     } catch (PropertyException e) {
/*  77 */       m_logger.error("Impossible de définit les paramètres du système de déco/reco");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LocalCoach getLocalCoach() {
/*  85 */     return this.m_localCoach;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocalCoach(LocalCoach localCoach) {
/*  92 */     this.m_localCoach = localCoach;
/*     */ 
/*     */     
/*  95 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("localCoach", this.m_localCoach);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fight getFight() {
/* 102 */     return this.m_fight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFight(Fight fight) {
/* 109 */     this.m_fight = fight;
/* 110 */     if (this.m_fight != null) {
/* 111 */       this.m_fight.setCellInformationProvider((CellInformationProvider)WorldManager.getInstance());
/*     */     }
/*     */ 
/*     */     
/* 115 */     if (this.m_localCoach != null) {
/* 116 */       if (this.m_fight != null) {
/* 117 */         Iterable<FightingTeam<Fighter>> teams = this.m_fight.getTeams();
/* 118 */         for (FightingTeam<Fighter> team : teams) {
/* 119 */           TeamMate<Fighter> localCoachTeamMate = team.getTeamMateById(this.m_localCoach.getId());
/* 120 */           if (localCoachTeamMate != null) {
/* 121 */             this.m_localCoach.setFightingCoach((Coach)localCoachTeamMate);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 126 */         this.m_localCoach.setFightingCoach(null);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRandomFightSearchInProgress() {
/* 136 */     return this.m_randomFightSearchInProgress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRandomFightSearchInProgress(boolean randomFightSearchInProgress) {
/* 143 */     this.m_randomFightSearchInProgress = randomFightSearchInProgress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProxyGroup(ProxyGroup proxyGroup) {
/* 153 */     super.setProxyGroup(proxyGroup);
/*     */ 
/*     */     
/* 156 */     DofusArenaConfiguration.getInstance().setInteger("lastProxyGroupIndex", proxyGroup.getIndex());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanUp() {
/* 166 */     super.cleanUp();
/*     */ 
/*     */     
/* 169 */     pushFrame((MessageFrame)UIBasicsFrame.getInstance());
/*     */ 
/*     */     
/* 172 */     FighterManager.getInstance().clear();
/* 173 */     TeamPresetManager.getInstance().clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean connect() {
/* 185 */     return connect(DofusArenaClientInstance.getInstance().getProxy());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onQueuePositionUpdate(int position) {
/* 195 */     DofusArenaProgressMonitorManager.getInstance().getProgressMonitor(true).worked(position);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onQueueFinished() {
/* 205 */     DofusArenaProgressMonitorManager.getInstance().done();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onQueryResult(int queryResultCode) {
/* 215 */     m_logger.info("queryResultCode : " + queryResultCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInvalidClientVersion(byte[] neededVersion) {
/* 225 */     Xulor.getInstance().msgBox(
/* 226 */         DofusArenaTranslator.getInstance().getString("logon.invalidClientVersion", new Object[] { Version.format(Version.INTERNAL_VERSION), Version.format(neededVersion)
/* 227 */           }), 66);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onConnectionToProxyFaild() {
/* 238 */     DofusArenaProgressMonitorManager.getInstance().done();
/*     */ 
/*     */     
/* 241 */     Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("logon.noProxyAvailable", new Object[0]), 67);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onLogonRequest() {
/* 252 */     DofusArenaProgressMonitorManager.getInstance().getProgressMonitor(true).beginTask(DofusArenaTranslator.getInstance().getString("logon.progress", new Object[0]), 0);
/*     */ 
/*     */     
/* 255 */     ClientVersionMessage versionMsg = new ClientVersionMessage();
/* 256 */     getNetworkEntity().sendMessage((Message)versionMsg);
/*     */ 
/*     */     
/* 259 */     ClientAuthenticationMessage msg = new ClientAuthenticationMessage();
/* 260 */     msg.setLogin(getLogin());
/* 261 */     msg.setPassword(getPassword());
/* 262 */     getNetworkEntity().sendMessage((Message)msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onLogoffRequest() {
/* 272 */     MessageBoxControler messageBoxControler = Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("question.disconnect", new Object[0]), 
/* 273 */         152);
/* 274 */     messageBoxControler.addEventListener(new IMessageBoxEventListener() {
/*     */           public void messageBoxClosed(int type) {
/* 276 */             if (type == 8) {
/*     */ 
/*     */               
/* 279 */               DisconnectionNotificationMessage message = new DisconnectionNotificationMessage();
/* 280 */               DofusArenaGameEntity.this.getNetworkEntity().sendMessage((Message)message);
/*     */ 
/*     */               
/* 283 */               DofusArenaGameEntity.this.getNetworkEntity().flushAndCloseConnection();
/*     */ 
/*     */               
/* 286 */               DofusArenaClientInstance.getInstance().start();
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onQuitRequest() {
/* 300 */     MessageBoxControler messageBoxControler = Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("question.quit", new Object[0]), 
/* 301 */         152);
/* 302 */     messageBoxControler.addEventListener(new IMessageBoxEventListener() {
/*     */           public void messageBoxClosed(int type) {
/* 304 */             if (type == 8)
/*     */             {
/*     */               
/* 307 */               System.exit(0);
/*     */             }
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\DofusArenaGameEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */