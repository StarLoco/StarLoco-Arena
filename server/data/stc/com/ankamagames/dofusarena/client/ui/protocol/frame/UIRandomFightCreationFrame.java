/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.frame.NetRandomFightFrame;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.clientToServer.OpponentSearchCancelMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.clientToServer.OpponentSearchRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*     */ import com.ankamagames.dofusarena.client.ui.actions.RandomFightSearchActions;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
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
/*     */ public class UIRandomFightCreationFrame
/*     */   implements MessageFrame
/*     */ {
/*  29 */   private static UIRandomFightCreationFrame m_instance = new UIRandomFightCreationFrame();
/*     */   
/*     */ 
/*     */ 
/*     */   public static UIRandomFightCreationFrame getInstance()
/*     */   {
/*  35 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  44 */     switch (message.getId())
/*     */     {
/*     */     case 19501: 
/*  47 */       UIMessage msg = (UIMessage)message;
/*     */       
/*     */ 
/*  50 */       OpponentSearchRequestMessage netMessage = new OpponentSearchRequestMessage();
/*  51 */       netMessage.setFightTypeId((byte)1);
/*  52 */       netMessage.setBet(msg.getIntValue());
/*  53 */       DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */       
/*     */ 
/*  56 */       DofusArenaGameEntity.getInstance().pushFrame(NetRandomFightFrame.getInstance());
/*     */       
/*  58 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 19502: 
/*  64 */       DofusArenaGameEntity.getInstance().removeFrame(this);
/*     */       
/*  66 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 19503: 
/*  72 */       OpponentSearchCancelMessage netMessage = new OpponentSearchCancelMessage();
/*  73 */       DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */       
/*  75 */       return false;
/*     */     }
/*     */     
/*     */     
/*  79 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/*  88 */     return 0L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setId(long id) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded)
/*     */   {
/* 106 */     if (!isAboutToBeAdded)
/*     */     {
/*     */ 
/* 109 */       Xulor.getInstance().load("randomFightCreationDialog", Dialogs.getDialogPath("randomFightCreationDialog"), (short)12000);
/*     */       
/*     */ 
/* 112 */       Xulor.getInstance().putActionClass("dofusarena.randomFightSearch", RandomFightSearchActions.class);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved)
/*     */   {
/* 124 */     if (!isAboutToBeRemoved)
/*     */     {
/*     */ 
/* 127 */       DofusArenaGameEntity.getInstance().removeFrame(NetRandomFightFrame.getInstance());
/*     */       
/*     */ 
/* 130 */       Xulor.getInstance().unload("randomFightCreationDialog");
/* 131 */       Xulor.getInstance().unload("randomFightSearchStatusDialog");
/*     */       
/*     */ 
/* 134 */       Xulor.getInstance().removeActionClass("dofusarena.randomFightSearch");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UIRandomFightCreationFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */