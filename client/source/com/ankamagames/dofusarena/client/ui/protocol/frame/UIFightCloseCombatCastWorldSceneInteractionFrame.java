/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.dofusarena.client.alea.DofusArenaWorldScene;
/*     */ import com.ankamagames.dofusarena.client.alea.highlightingCells.CloseCombatDisplayZone;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.fight.CloseCombatRequestMessage;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
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
/*     */ public class UIFightCloseCombatCastWorldSceneInteractionFrame
/*     */   extends UIAbstractFightCastWorldSceneInteractionFrame
/*     */ {
/*  25 */   private static UIFightCloseCombatCastWorldSceneInteractionFrame m_instance = new UIFightCloseCombatCastWorldSceneInteractionFrame();
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
/*     */   public static UIFightCloseCombatCastWorldSceneInteractionFrame getInstance() {
/*  38 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EffectContainer getEffectContainer() {
/*  48 */     return null;
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
/*     */   protected void sendCastMessage(int castPositionX, int castPositionY, short castPositionZ) {
/*  60 */     CloseCombatRequestMessage netMessage = new CloseCombatRequestMessage();
/*  61 */     netMessage.setFighterId(this.m_fighter.getId());
/*  62 */     netMessage.setUsePosition(castPositionX, castPositionY, castPositionZ);
/*  63 */     DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getCastMouseIcon() {
/*  73 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/*  83 */     return 0L;
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
/*     */   public void setId(long id) {}
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
/*     */   public void selectRange() {
/* 114 */     super.selectRange();
/*     */ 
/*     */     
/* 117 */     if (this.m_fighter != null) {
/* 118 */       DofusArenaWorldScene worldScene = (DofusArenaWorldScene)DofusArenaClientInstance.getInstance().getWorldScene();
/* 119 */       if (worldScene != null)
/* 120 */         ((CloseCombatDisplayZone)this.m_rangeDisplayer).selectCloseCombatRange(this.m_fighter, worldScene); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\frame\UIFightCloseCombatCastWorldSceneInteractionFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */