/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.dofusarena.client.alea.DofusArenaWorldScene;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.fighter.UsableFighterCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*     */ import com.ankamagames.dofusarena.client.core.game.fight.Timeline;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.core.game.spell.Spell;
/*     */ import com.ankamagames.dofusarena.client.core.game.spell.UsableSpell;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.fight.FighterActorDirectionChangeRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.fight.FighterEndTurnRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterCardMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterSpellMessage;
/*     */ import com.ankamagames.dofusarena.common.game.fight.AbstractFight;
/*     */ import com.ankamagames.dofusarena.common.game.fight.CardUseValidity;
/*     */ import com.ankamagames.dofusarena.common.game.fight.CloseCombatValidity;
/*     */ import com.ankamagames.dofusarena.common.game.fight.SpellCastValidity;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.property.PropertiesProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UIFighterFrame
/*     */   implements MessageFrame
/*     */ {
/*  39 */   private static UIFighterFrame m_instance = new UIFighterFrame();
/*     */   
/*     */ 
/*     */ 
/*     */   public static UIFighterFrame getInstance()
/*     */   {
/*  45 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  54 */     switch (message.getId())
/*     */     {
/*     */     case 18001: 
/*  57 */       UIFighterMessage msg = (UIFighterMessage)message;
/*     */       
/*  59 */       Fighter fighter = msg.getFighter();
/*  60 */       if (fighter != null) {
/*  61 */         long fighterId = fighter.getId();
/*  62 */         Fighter currentFighter = (Fighter)DofusArenaGameEntity.getInstance().getFight().getTimeline().getCurrentFighter();
/*  63 */         if ((currentFighter != null) && (currentFighter.getId() == fighterId))
/*     */         {
/*     */ 
/*  66 */           FighterEndTurnRequestMessage netMessage = new FighterEndTurnRequestMessage();
/*  67 */           netMessage.setFighterId(fighterId);
/*  68 */           DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*  73 */       return false;
/*     */     
/*     */ 
/*     */     case 18003: 
/*  77 */       UIFighterMessage msg = (UIFighterMessage)message;
/*     */       
/*     */ 
/*  80 */       Fighter fighter = msg.getFighter();
/*  81 */       if (fighter != null) {
/*  82 */         FighterActorDirectionChangeRequestMessage netMessage = new FighterActorDirectionChangeRequestMessage();
/*  83 */         netMessage.setFighterId(fighter.getId());
/*  84 */         netMessage.setDirection8(Direction8.SOUTH_WEST);
/*  85 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */       }
/*     */       
/*  88 */       return false;
/*     */     
/*     */ 
/*     */     case 18002: 
/*  92 */       UIFighterMessage msg = (UIFighterMessage)message;
/*     */       
/*     */ 
/*  95 */       Fighter fighter = msg.getFighter();
/*  96 */       if (fighter != null) {
/*  97 */         FighterActorDirectionChangeRequestMessage netMessage = new FighterActorDirectionChangeRequestMessage();
/*  98 */         netMessage.setFighterId(fighter.getId());
/*  99 */         netMessage.setDirection8(Direction8.SOUTH_EAST);
/* 100 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */       }
/*     */       
/* 103 */       return false;
/*     */     
/*     */ 
/*     */     case 18004: 
/* 107 */       UIFighterMessage msg = (UIFighterMessage)message;
/*     */       
/*     */ 
/* 110 */       Fighter fighter = msg.getFighter();
/* 111 */       if (fighter != null) {
/* 112 */         FighterActorDirectionChangeRequestMessage netMessage = new FighterActorDirectionChangeRequestMessage();
/* 113 */         netMessage.setFighterId(fighter.getId());
/* 114 */         netMessage.setDirection8(Direction8.NORTH_WEST);
/* 115 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */       }
/*     */       
/* 118 */       return false;
/*     */     
/*     */ 
/*     */     case 18005: 
/* 122 */       UIFighterMessage msg = (UIFighterMessage)message;
/*     */       
/*     */ 
/* 125 */       Fighter fighter = msg.getFighter();
/* 126 */       if (fighter != null) {
/* 127 */         FighterActorDirectionChangeRequestMessage netMessage = new FighterActorDirectionChangeRequestMessage();
/* 128 */         netMessage.setFighterId(fighter.getId());
/* 129 */         netMessage.setDirection8(Direction8.NORTH_EAST);
/* 130 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */       }
/*     */       
/* 133 */       return false;
/*     */     
/*     */ 
/*     */     case 18006: 
/* 137 */       UIFighterSpellMessage msg = (UIFighterSpellMessage)message;
/*     */       
/* 139 */       Fighter fighter = msg.getFighter();
/* 140 */       Spell spell = msg.getSpell();
/* 141 */       if ((fighter != null) && (spell != null)) {
/* 142 */         boolean isValid = true;
/* 143 */         if ((spell instanceof UsableSpell)) {
/* 144 */           UsableSpell usableSpell = (UsableSpell)spell;
/* 145 */           isValid = usableSpell.getCastValidity().isValid();
/*     */         }
/* 147 */         if (isValid)
/*     */         {
/* 149 */           DofusArenaGameEntity.getInstance().removeFrame(UIFightCloseCombatCastWorldSceneInteractionFrame.getInstance());
/* 150 */           DofusArenaGameEntity.getInstance().removeFrame(UIFightFighterCardUseWorldSceneInteractionFrame.getInstance());
/*     */           
/* 152 */           Spell selectedSpell = UIFightSpellCastWorldSceneInteractionFrame.getInstance().getSelectedSpell();
/* 153 */           if ((DofusArenaGameEntity.getInstance().hasFrame(UIFightSpellCastWorldSceneInteractionFrame.getInstance())) && (selectedSpell != null) && (selectedSpell.equals(spell))) {
/* 154 */             DofusArenaGameEntity.getInstance().removeFrame(UIFightSpellCastWorldSceneInteractionFrame.getInstance());
/* 155 */             Xulor.getInstance().hideMouseImage();
/*     */           }
/*     */           else
/*     */           {
/* 159 */             UIFightSpellCastWorldSceneInteractionFrame.getInstance().setSelectedSpell(spell);
/* 160 */             UIFightSpellCastWorldSceneInteractionFrame.getInstance().setFighter(fighter);
/* 161 */             UIFightSpellCastWorldSceneInteractionFrame.getInstance().selectRange();
/*     */             
/* 163 */             UIFightSpellCastWorldSceneInteractionFrame.getInstance().refreshRangeDisplay();
/*     */             
/*     */ 
/* 166 */             DofusArenaGameEntity.getInstance().pushFrame(UIFightSpellCastWorldSceneInteractionFrame.getInstance());
/*     */           }
/*     */         }
/*     */         else {
/* 170 */           unselectAction();
/*     */         }
/*     */       }
/*     */       
/* 174 */       return false;
/*     */     
/*     */ 
/*     */     case 18007: 
/* 178 */       UIFighterCardMessage msg = (UIFighterCardMessage)message;
/*     */       
/* 180 */       Fighter fighter = msg.getFighter();
/* 181 */       FighterCard fighterCard = msg.getFighterCard();
/* 182 */       if ((fighter != null) && (fighterCard != null)) {
/* 183 */         boolean isValid = true;
/* 184 */         if ((fighterCard instanceof UsableFighterCard)) {
/* 185 */           UsableFighterCard usableSpell = (UsableFighterCard)fighterCard;
/* 186 */           isValid = usableSpell.getCastValidity().isValid();
/*     */         }
/* 188 */         if (isValid)
/*     */         {
/* 190 */           DofusArenaGameEntity.getInstance().removeFrame(UIFightCloseCombatCastWorldSceneInteractionFrame.getInstance());
/* 191 */           DofusArenaGameEntity.getInstance().removeFrame(UIFightSpellCastWorldSceneInteractionFrame.getInstance());
/*     */           
/* 193 */           FighterCard selectedFighterCard = UIFightFighterCardUseWorldSceneInteractionFrame.getInstance().getSelectedFighterCard();
/* 194 */           if ((DofusArenaGameEntity.getInstance().hasFrame(UIFightFighterCardUseWorldSceneInteractionFrame.getInstance())) && (selectedFighterCard != null) && (selectedFighterCard.equals(fighterCard))) {
/* 195 */             DofusArenaGameEntity.getInstance().removeFrame(UIFightFighterCardUseWorldSceneInteractionFrame.getInstance());
/* 196 */             Xulor.getInstance().hideMouseImage();
/*     */           }
/*     */           else
/*     */           {
/* 200 */             UIFightFighterCardUseWorldSceneInteractionFrame.getInstance().setSelectedFighterCard(fighterCard);
/* 201 */             UIFightFighterCardUseWorldSceneInteractionFrame.getInstance().setFighter(fighter);
/* 202 */             UIFightFighterCardUseWorldSceneInteractionFrame.getInstance().selectRange();
/*     */             
/*     */ 
/* 205 */             DofusArenaGameEntity.getInstance().pushFrame(UIFightFighterCardUseWorldSceneInteractionFrame.getInstance());
/*     */           }
/*     */         }
/*     */         else {
/* 209 */           unselectAction();
/*     */         }
/*     */       }
/*     */       
/* 213 */       return false;
/*     */     
/*     */ 
/*     */     case 18008: 
/* 217 */       UIFighterMessage msg = (UIFighterMessage)message;
/*     */       
/* 219 */       Fighter fighter = msg.getFighter();
/* 220 */       if (fighter != null) {
/* 221 */         if (fighter.getCurrentFight().getCloseCombatValidity(fighter, null).isValid())
/*     */         {
/* 223 */           DofusArenaGameEntity.getInstance().removeFrame(UIFightFighterCardUseWorldSceneInteractionFrame.getInstance());
/* 224 */           DofusArenaGameEntity.getInstance().removeFrame(UIFightSpellCastWorldSceneInteractionFrame.getInstance());
/*     */           
/* 226 */           if (DofusArenaGameEntity.getInstance().hasFrame(UIFightCloseCombatCastWorldSceneInteractionFrame.getInstance())) {
/* 227 */             DofusArenaGameEntity.getInstance().removeFrame(UIFightCloseCombatCastWorldSceneInteractionFrame.getInstance());
/* 228 */             Xulor.getInstance().hideMouseImage();
/*     */           }
/*     */           else
/*     */           {
/* 232 */             UIFightCloseCombatCastWorldSceneInteractionFrame.getInstance().setFighter(fighter);
/* 233 */             UIFightCloseCombatCastWorldSceneInteractionFrame.getInstance().selectRange();
/*     */             
/*     */ 
/* 236 */             DofusArenaGameEntity.getInstance().pushFrame(UIFightCloseCombatCastWorldSceneInteractionFrame.getInstance());
/*     */           }
/*     */         }
/*     */         else {
/* 240 */           unselectAction();
/*     */         }
/*     */       }
/*     */       
/* 244 */       return false;
/*     */     
/*     */ 
/*     */     case 18013: 
/* 248 */       UIFighterSpellMessage msg = (UIFighterSpellMessage)message;
/*     */       
/*     */ 
/* 251 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("singleCardData", msg.getSpell());
/*     */       
/*     */ 
/* 254 */       Xulor.getInstance().unload("fighterInformationsDialog");
/*     */       
/*     */ 
/* 257 */       Xulor.getInstance().load("singleCardDialog", Dialogs.getDialogPath("singleCardDialog"), 1L, (short)10100);
/*     */       
/* 259 */       return false;
/*     */     
/*     */ 
/*     */     case 18014: 
/* 263 */       UIFighterCardMessage msg = (UIFighterCardMessage)message;
/*     */       
/*     */ 
/* 266 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("singleCardData", msg.getFighterCard());
/*     */       
/*     */ 
/* 269 */       Xulor.getInstance().unload("fighterInformationsDialog");
/*     */       
/*     */ 
/* 272 */       Xulor.getInstance().load("singleCardDialog", Dialogs.getDialogPath("singleCardDialog"), 1L, (short)10100);
/*     */       
/* 274 */       return false;
/*     */     }
/*     */     
/*     */     
/* 278 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 287 */     return 0L;
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
/* 305 */     if (!isAboutToBeAdded)
/*     */     {
/*     */ 
/* 308 */       ((DofusArenaWorldScene)DofusArenaClientInstance.getInstance().getWorldScene()).setDispatchMouseMovedMessage(true);
/*     */       
/*     */ 
/* 311 */       Xulor.getInstance().load("fighterControlsDialog", Dialogs.getDialogPath("fighterControlsDialog"), (short)10000);
/*     */       
/*     */ 
/* 314 */       DofusArenaGameEntity.getInstance().pushFrame(UIFightMoveWorldSceneInteractionFrame.getInstance());
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
/* 326 */     if (!isAboutToBeRemoved)
/*     */     {
/*     */ 
/* 329 */       ((DofusArenaWorldScene)DofusArenaClientInstance.getInstance().getWorldScene()).setDispatchMouseMovedMessage(false);
/*     */       
/*     */ 
/* 332 */       Xulor.getInstance().unload("fighterControlsDialog");
/* 333 */       Xulor.getInstance().unload("singleCardDialog");
/*     */       
/*     */ 
/* 336 */       Xulor.getInstance().hideTooltip();
/*     */       
/*     */ 
/* 339 */       Xulor.getInstance().hideMouseImage();
/*     */       
/*     */ 
/* 342 */       DofusArenaGameEntity.getInstance().removeFrame(UIFightMoveWorldSceneInteractionFrame.getInstance());
/* 343 */       DofusArenaGameEntity.getInstance().removeFrame(UIFightSpellCastWorldSceneInteractionFrame.getInstance());
/* 344 */       DofusArenaGameEntity.getInstance().removeFrame(UIFightCloseCombatCastWorldSceneInteractionFrame.getInstance());
/* 345 */       DofusArenaGameEntity.getInstance().removeFrame(UIFightFighterCardUseWorldSceneInteractionFrame.getInstance());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void unselectAction()
/*     */   {
/* 354 */     if (DofusArenaGameEntity.getInstance().hasFrame(UIFightCloseCombatCastWorldSceneInteractionFrame.getInstance())) {
/* 355 */       DofusArenaGameEntity.getInstance().removeFrame(UIFightCloseCombatCastWorldSceneInteractionFrame.getInstance());
/*     */     }
/* 357 */     if (DofusArenaGameEntity.getInstance().hasFrame(UIFightFighterCardUseWorldSceneInteractionFrame.getInstance())) {
/* 358 */       DofusArenaGameEntity.getInstance().removeFrame(UIFightFighterCardUseWorldSceneInteractionFrame.getInstance());
/*     */     }
/* 360 */     if (DofusArenaGameEntity.getInstance().hasFrame(UIFightSpellCastWorldSceneInteractionFrame.getInstance())) {
/* 361 */       DofusArenaGameEntity.getInstance().removeFrame(UIFightSpellCastWorldSceneInteractionFrame.getInstance());
/*     */     }
/* 363 */     Xulor.getInstance().hideMouseImage();
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UIFighterFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */