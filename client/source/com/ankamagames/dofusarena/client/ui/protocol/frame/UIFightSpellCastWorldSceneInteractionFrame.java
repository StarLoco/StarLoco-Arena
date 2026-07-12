/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.dofusarena.client.alea.DofusArenaWorldScene;
/*     */ import com.ankamagames.dofusarena.client.alea.highlightingCells.RangeAndEffectDisplayer;
/*     */ import com.ankamagames.dofusarena.client.alea.highlightingCells.SpellDisplayZone;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.spell.Spell;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.fight.SpellCastRequestMessage;
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
/*     */ public class UIFightSpellCastWorldSceneInteractionFrame
/*     */   extends UIAbstractFightCastWorldSceneInteractionFrame
/*     */ {
/*  25 */   private static UIFightSpellCastWorldSceneInteractionFrame m_instance = new UIFightSpellCastWorldSceneInteractionFrame();
/*     */   
/*  27 */   private Spell m_selectedSpell = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private UIFightSpellCastWorldSceneInteractionFrame() {
/*  33 */     this.m_rangeDisplayer = (RangeAndEffectDisplayer)new SpellDisplayZone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UIFightSpellCastWorldSceneInteractionFrame getInstance() {
/*  40 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedSpell(Spell selectedSpell) {
/*  47 */     this.m_selectedSpell = selectedSpell;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Spell getSelectedSpell() {
/*  54 */     return this.m_selectedSpell;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EffectContainer getEffectContainer() {
/*  64 */     return (EffectContainer)this.m_selectedSpell;
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
/*  76 */     SpellCastRequestMessage netMessage = new SpellCastRequestMessage();
/*  77 */     netMessage.setFighterId(this.m_fighter.getId());
/*  78 */     netMessage.setSpellId(this.m_selectedSpell.getId());
/*  79 */     netMessage.setCastPosition(castPositionX, castPositionY, castPositionZ);
/*  80 */     DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getCastMouseIcon() {
/*  90 */     if (this.m_selectedSpell != null) {
/*  91 */       return (String)this.m_selectedSpell.getFieldValue("iconUrl");
/*     */     }
/*  93 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 103 */     return 0L;
/*     */   }
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
/*     */   public void selectRange() {
/* 122 */     super.selectRange();
/*     */ 
/*     */     
/* 125 */     if (this.m_selectedSpell != null && this.m_fighter != null) {
/* 126 */       DofusArenaWorldScene worldScene = (DofusArenaWorldScene)DofusArenaClientInstance.getInstance().getWorldScene();
/* 127 */       if (worldScene != null)
/* 128 */         ((SpellDisplayZone)this.m_rangeDisplayer).selectSpellRange(this.m_selectedSpell, this.m_fighter, worldScene); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\frame\UIFightSpellCastWorldSceneInteractionFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */