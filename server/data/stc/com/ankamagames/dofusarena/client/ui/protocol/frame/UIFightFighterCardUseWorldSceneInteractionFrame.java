/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.dofusarena.client.alea.DofusArenaWorldScene;
/*     */ import com.ankamagames.dofusarena.client.alea.highlightingCells.FighterCardUseDisplayZone;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.fight.FighterCardUseRequestMessage;
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
/*     */ public class UIFightFighterCardUseWorldSceneInteractionFrame
/*     */   extends UIAbstractFightCastWorldSceneInteractionFrame
/*     */ {
/*  25 */   private static UIFightFighterCardUseWorldSceneInteractionFrame m_instance = new UIFightFighterCardUseWorldSceneInteractionFrame();
/*     */   
/*  27 */   private FighterCard m_selectedFighterCard = null;
/*     */   
/*     */ 
/*     */ 
/*     */   private UIFightFighterCardUseWorldSceneInteractionFrame()
/*     */   {
/*  33 */     this.m_rangeDisplayer = new FighterCardUseDisplayZone();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static UIFightFighterCardUseWorldSceneInteractionFrame getInstance()
/*     */   {
/*  40 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setSelectedFighterCard(FighterCard selectedFighterCard)
/*     */   {
/*  47 */     this.m_selectedFighterCard = selectedFighterCard;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public FighterCard getSelectedFighterCard()
/*     */   {
/*  54 */     return this.m_selectedFighterCard;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected EffectContainer getEffectContainer()
/*     */   {
/*  64 */     return this.m_selectedFighterCard;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void sendCastMessage(int castPositionX, int castPositionY, short castPositionZ)
/*     */   {
/*  76 */     FighterCardUseRequestMessage netMessage = new FighterCardUseRequestMessage();
/*  77 */     netMessage.setFighterId(this.m_fighter.getId());
/*  78 */     netMessage.setCardId(this.m_selectedFighterCard.getId());
/*  79 */     netMessage.setUsePosition(castPositionX, castPositionY, castPositionZ);
/*  80 */     DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getCastMouseIcon()
/*     */   {
/*  90 */     if (this.m_selectedFighterCard != null) {
/*  91 */       return (String)this.m_selectedFighterCard.getFieldValue("iconUrl");
/*     */     }
/*  93 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 103 */     return 0L;
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
/*     */ 
/*     */   public void selectRange()
/*     */   {
/* 122 */     super.selectRange();
/*     */     
/*     */ 
/* 125 */     if ((this.m_selectedFighterCard != null) && (this.m_fighter != null)) {
/* 126 */       DofusArenaWorldScene worldScene = (DofusArenaWorldScene)DofusArenaClientInstance.getInstance().getWorldScene();
/* 127 */       if (worldScene != null) {
/* 128 */         ((FighterCardUseDisplayZone)this.m_rangeDisplayer).selectCardUseRange(this.m_selectedFighterCard, this.m_fighter, worldScene);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UIFightFighterCardUseWorldSceneInteractionFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */