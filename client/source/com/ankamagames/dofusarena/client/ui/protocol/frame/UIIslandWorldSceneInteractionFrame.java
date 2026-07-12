/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.clientToServer.AddFriendMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.clientToServer.AddIgnoreMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.clientToServer.RemoveFriendMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.clientToServer.RemoveIgnoreMessage;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.PathMobile;
/*     */ import com.ankamagames.baseImpl.graphics.alea.utils.WorldSceneInteractionUtils;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.dofusarena.client.chat.DofusArenaUser;
/*     */ import com.ankamagames.dofusarena.client.chat.DofusArenaUserGroupManager;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.Coach;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.CoachActorMovementRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.FightInvitationRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.exchange.ItemExchangeInvitationRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.worldScene.UIWorldSceneMouseReleasedMessage;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindParameters;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindResult;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.event.IMouseClickListener;
/*     */ import com.ankamagames.xulor.event.MouseClickEvent;
/*     */ import com.ankamagames.xulor.event.listener.MouseClickListener;
/*     */ import com.ankamagames.xulor.template.IPopupMenu;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ public class UIIslandWorldSceneInteractionFrame
/*     */   implements MessageFrame
/*     */ {
/*  50 */   private static final Logger m_logger = Logger.getLogger(UIIslandWorldSceneInteractionFrame.class);
/*     */   
/*  52 */   private static UIIslandWorldSceneInteractionFrame m_instance = new UIIslandWorldSceneInteractionFrame();
/*     */   
/*  54 */   private static float PATH_SEARCH_ITERATION_PER_TIME = 0.05F;
/*     */ 
/*     */   
/*     */   private long m_lastMovementTime;
/*     */ 
/*     */   
/*     */   public static UIIslandWorldSceneInteractionFrame getInstance() {
/*  61 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMessage(Message message) {
/*     */     UIWorldSceneMouseReleasedMessage msg;
/*  70 */     switch (message.getId()) {
/*     */       
/*     */       case 30000:
/*  73 */         msg = (UIWorldSceneMouseReleasedMessage)message;
/*     */         
/*  75 */         if (msg.getMouseButton() == 1) {
/*     */ 
/*     */           
/*  78 */           LocalCoach localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/*  79 */           if (localCoach != null) {
/*     */             
/*  81 */             int searchMaxIteration = (int)Math.min(500.0F, (float)(System.currentTimeMillis() - this.m_lastMovementTime) * PATH_SEARCH_ITERATION_PER_TIME);
/*     */             
/*  83 */             PathFindParameters defaultParameters = new PathFindParameters();
/*  84 */             defaultParameters.m_searchLimit = searchMaxIteration;
/*  85 */             defaultParameters.m_includeStartCell = false;
/*     */ 
/*     */             
/*  88 */             PathFindResult pathResult = WorldSceneInteractionUtils.getPathSolutionFromMouseCoordinates(DofusArenaClientInstance.getInstance().getWorldScene(), (PathMobile)localCoach, msg.getMouseX(), 
/*  89 */                 msg.getMouseY(), WorldSceneInteractionUtils.PathSolutionCriterions.NEAREST_UP_FACE, defaultParameters);
/*     */             
/*  91 */             if (pathResult != null && pathResult.getPathLength() > 0) {
/*     */ 
/*     */               
/*  94 */               CoachActorMovementRequestMessage netMessage = new CoachActorMovementRequestMessage();
/*  95 */               netMessage.setPathResult(pathResult);
/*  96 */               DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*     */               
/*  98 */               this.m_lastMovementTime = System.currentTimeMillis();
/*     */             } else {
/* 100 */               m_logger.error("Aucun chemin trouvé");
/*     */             }
/*     */           
/*     */           } 
/* 104 */         } else if (msg.getMouseButton() == 3) {
/*     */ 
/*     */           
/* 107 */           ArrayList<Mobile> hitMobiles = DofusArenaClientInstance.getInstance().getWorldScene().getMobilesUnderMousePoint(msg.getMouseX(), msg.getMouseY());
/* 108 */           if (hitMobiles.size() != 0) {
/*     */             
/* 110 */             final Mobile mobile = hitMobiles.get(0);
/*     */ 
/*     */             
/* 113 */             if (mobile.getId() != DofusArenaGameEntity.getInstance().getLocalCoach().getId()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 119 */               IPopupMenu popupMenu = Xulor.getInstance().popupMenu();
/* 120 */               popupMenu.addLabel(((Coach)mobile).getName(), null);
/*     */               
/* 122 */               if (mobile instanceof Coach) {
/* 123 */                 final Coach coach = (Coach)mobile;
/*     */ 
/*     */                 
/* 126 */                 HashMap<String, DofusArenaUser> friendGroup = DofusArenaUserGroupManager.getInstance().getFriendGroup();
/* 127 */                 if (friendGroup != null) {
/* 128 */                   if (!friendGroup.containsKey(((Coach)mobile).getName().toLowerCase())) {
/* 129 */                     popupMenu.addButton(DofusArenaTranslator.getInstance().getString("chat.addToFriendList", new Object[0]), null, (IMouseClickListener)new MouseClickListener() {
/*     */                           public void run(MouseClickEvent event) {
/* 131 */                             AddFriendMessage netMessage = new AddFriendMessage();
/* 132 */                             netMessage.setFriendName(coach.getName());
/* 133 */                             DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*     */                           }
/* 135 */                         },  true);
/*     */                   } else {
/* 137 */                     popupMenu.addButton(DofusArenaTranslator.getInstance().getString("chat.removeFromFriendList", new Object[0]), null, (IMouseClickListener)new MouseClickListener() {
/*     */                           public void run(MouseClickEvent event) {
/* 139 */                             RemoveFriendMessage netMessage = new RemoveFriendMessage();
/* 140 */                             netMessage.setFriendName(coach.getName());
/* 141 */                             DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*     */                           }
/* 143 */                         },  true);
/*     */                   } 
/*     */                 }
/*     */ 
/*     */                 
/* 148 */                 HashMap<String, DofusArenaUser> ignoreGroup = DofusArenaUserGroupManager.getInstance().getIgnoreGroup();
/* 149 */                 if (ignoreGroup != null) {
/* 150 */                   if (!ignoreGroup.containsKey(((Coach)mobile).getName().toLowerCase())) {
/* 151 */                     popupMenu.addButton(DofusArenaTranslator.getInstance().getString("chat.addToIgnoreList", new Object[0]), null, (IMouseClickListener)new MouseClickListener() {
/*     */                           public void run(MouseClickEvent event) {
/* 153 */                             AddIgnoreMessage netMessage = new AddIgnoreMessage();
/* 154 */                             netMessage.setIgnoreName(coach.getName());
/* 155 */                             DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*     */                           }
/* 157 */                         },  true);
/*     */                   } else {
/* 159 */                     popupMenu.addButton(DofusArenaTranslator.getInstance().getString("chat.removeFromIgnoreList", new Object[0]), null, (IMouseClickListener)new MouseClickListener() {
/*     */                           public void run(MouseClickEvent event) {
/* 161 */                             RemoveIgnoreMessage netMessage = new RemoveIgnoreMessage();
/* 162 */                             netMessage.setIgnoreName(coach.getName());
/* 163 */                             DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*     */                           }
/* 165 */                         },  true);
/*     */                   } 
/*     */                 }
/*     */ 
/*     */                 
/* 170 */                 popupMenu.addSeparator();
/* 171 */                 popupMenu.addButton(DofusArenaTranslator.getInstance().getString("fightInvitation.training", new Object[0]), null, (IMouseClickListener)new MouseClickListener() {
/*     */                       public void run(MouseClickEvent event) {
/* 173 */                         FightInvitationRequestMessage netMessage = new FightInvitationRequestMessage();
/* 174 */                         netMessage.setTargetCoachId(mobile.getId());
/* 175 */                         netMessage.setFightTypeId((byte)4);
/* 176 */                         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*     */                       }
/* 178 */                     },  true);
/*     */ 
/*     */                 
/* 181 */                 popupMenu.addButton(DofusArenaTranslator.getInstance().getString("fightInvitation.trainingWithBet", new Object[0]), null, (IMouseClickListener)new MouseClickListener() {
/*     */                       public void run(MouseClickEvent event) {
/* 183 */                         FightInvitationRequestMessage netMessage = new FightInvitationRequestMessage();
/* 184 */                         netMessage.setTargetCoachId(mobile.getId());
/* 185 */                         netMessage.setFightTypeId((byte)4);
/* 186 */                         netMessage.setBet(1);
/* 187 */                         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*     */                       }
/* 189 */                     },  true);
/*     */ 
/*     */                 
/* 192 */                 popupMenu.addSeparator();
/* 193 */                 popupMenu.addButton(DofusArenaTranslator.getInstance().getString("exchange.invitation", new Object[0]), null, (IMouseClickListener)new MouseClickListener() {
/*     */                       public void run(MouseClickEvent event) {
/* 195 */                         ItemExchangeInvitationRequestMessage netMessage = new ItemExchangeInvitationRequestMessage();
/* 196 */                         netMessage.setOtherUserId(coach.getId());
/* 197 */                         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*     */                       }
/* 199 */                     },  true);
/*     */               } 
/*     */ 
/*     */               
/* 203 */               Xulor.getInstance().showPopupMenu(popupMenu);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 211 */         return false;
/*     */     } 
/*     */     
/* 214 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 223 */     return 0L;
/*     */   }
/*     */   
/*     */   public void setId(long id) {}
/*     */   
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*     */   
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\frame\UIIslandWorldSceneInteractionFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */