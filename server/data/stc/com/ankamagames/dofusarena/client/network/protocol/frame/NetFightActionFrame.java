/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.StaticRunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.StackInventory;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.action.AbstractFightCastAction;
/*     */ import com.ankamagames.dofusarena.client.core.action.ChangeDirectionAction;
/*     */ import com.ankamagames.dofusarena.client.core.action.CloseCombatAction;
/*     */ import com.ankamagames.dofusarena.client.core.action.DieAction;
/*     */ import com.ankamagames.dofusarena.client.core.action.EffectAreaAction;
/*     */ import com.ankamagames.dofusarena.client.core.action.FighterCardAction;
/*     */ import com.ankamagames.dofusarena.client.core.action.FighterTurnEndAction;
/*     */ import com.ankamagames.dofusarena.client.core.action.FighterTurnStartAction;
/*     */ import com.ankamagames.dofusarena.client.core.action.MoveAction;
/*     */ import com.ankamagames.dofusarena.client.core.action.NewTableTurnAction;
/*     */ import com.ankamagames.dofusarena.client.core.action.SpellAction;
/*     */ import com.ankamagames.dofusarena.client.core.action.SpellEffectAction;
/*     */ import com.ankamagames.dofusarena.client.core.action.TackleAction;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCardManager;
/*     */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.core.game.spell.Spell;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.CloseCombatMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.FighterCardUseMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.FighterDiesMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.FighterMoveMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.FighterTackledMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.RunningEffectActionMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.SpellCastMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.EffectAreaActionMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.FighterChangeDirectionMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.FighterTurnBeginMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.FighterTurnEndMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.NewTableTurnBeginMessage;
/*     */ import com.ankamagames.dofusarena.common.game.effect.RunningEffectConstants;
/*     */ import com.ankamagames.dofusarena.common.game.event.AbstractEventManager;
/*     */ import com.ankamagames.dofusarena.common.game.fight.FightActionType;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.Breed;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import com.ankamagames.framework.script.ScriptedActionFunctionsLibrary;
/*     */ import com.ankamagames.framework.script.action.Action;
/*     */ import com.ankamagames.framework.script.action.ActionGroup;
/*     */ import com.ankamagames.framework.script.action.QueueActionGroupManager;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class NetFightActionFrame implements com.ankamagames.framework.kernel.events.MessageFrame
/*     */ {
/*  53 */   protected static final Logger m_logger = Logger.getLogger(NetFightActionFrame.class);
/*     */   
/*  55 */   private static NetFightActionFrame m_instance = new NetFightActionFrame();
/*     */   
/*     */ 
/*     */ 
/*     */   public static NetFightActionFrame getInstance()
/*     */   {
/*  61 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*     */     EffectContainer container;
/*     */     
/*  70 */     switch (message.getId())
/*     */     {
/*     */ 
/*     */     case 8100: 
/*  74 */       NewTableTurnBeginMessage msg = (NewTableTurnBeginMessage)message;
/*  75 */       int actionTypeId = msg.getFightActionType().getId();
/*  76 */       NewTableTurnAction action = new NewTableTurnAction(msg.getUniqueId(), actionTypeId, msg.getActionId());
/*  77 */       action.setEventToAdd(msg.getEvent());
/*     */       
/*  79 */       QueueActionGroupManager.getInstance().addActionToPendingGroup(action);
/*  80 */       QueueActionGroupManager.getInstance().executePendingGroup();
/*  81 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */     case 8104: 
/*  86 */       FighterTurnBeginMessage msg = (FighterTurnBeginMessage)message;
/*     */       
/*  88 */       int actionTypeId = msg.getFightActionType().getId();
/*  89 */       FighterTurnStartAction action = new FighterTurnStartAction(msg.getUniqueId(), actionTypeId, msg.getActionId());
/*  90 */       action.setInstigatorId(msg.getFighterId());
/*     */       
/*  92 */       QueueActionGroupManager.getInstance().addActionToPendingGroup(action);
/*  93 */       QueueActionGroupManager.getInstance().executePendingGroup();
/*     */       
/*  95 */       return false;
/*     */     
/*     */ 
/*     */     case 8106: 
/*  99 */       FighterTurnEndMessage msg = (FighterTurnEndMessage)message;
/*     */       
/* 101 */       int actionTypeId = msg.getFightActionType().getId();
/* 102 */       FighterTurnEndAction action = new FighterTurnEndAction(msg.getUniqueId(), actionTypeId, msg.getActionId());
/* 103 */       action.setInstigatorId(msg.getFighterId());
/*     */       
/* 105 */       QueueActionGroupManager.getInstance().addActionToPendingGroup(action);
/* 106 */       QueueActionGroupManager.getInstance().executePendingGroup();
/*     */       
/* 108 */       return false;
/*     */     
/*     */ 
/*     */     case 4522: 
/* 112 */       FighterChangeDirectionMessage msg = (FighterChangeDirectionMessage)message;
/*     */       
/* 114 */       int actionTypeId = msg.getFightActionType().getId();
/* 115 */       ChangeDirectionAction action = new ChangeDirectionAction(msg.getUniqueId(), actionTypeId, msg.getActionId(), msg.getDirection());
/* 116 */       action.setInstigatorId(msg.getFighterId());
/*     */       
/* 118 */       QueueActionGroupManager.getInstance().addActionToPendingGroup(action);
/* 119 */       QueueActionGroupManager.getInstance().executePendingGroup();
/*     */       
/* 121 */       return false;
/*     */     
/*     */ 
/*     */     case 6200: 
/* 125 */       EffectAreaActionMessage msg = (EffectAreaActionMessage)message;
/*     */       
/* 127 */       com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.BasicEffectArea area = DofusArenaGameEntity.getInstance().getFight().getEffectAreaManager().getActiveEffectAreaWithId(msg.getAreaId());
/*     */       
/* 129 */       if (area == null) {
/* 130 */         m_logger.error("Impossible de lancer un effet static d'id " + msg.getAreaId());
/* 131 */         return false;
/*     */       }
/*     */       
/* 134 */       int actionTypeId = msg.getFightActionType().getId();
/* 135 */       EffectAreaAction action = new EffectAreaAction(msg.getUniqueId(), actionTypeId, msg.getActionId(), msg.isApply(), (com.ankamagames.dofusarena.client.core.game.effectArea.EffectArea)area);
/* 136 */       action.setInstigatorId(msg.getAreaId());
/* 137 */       action.setTargetId(msg.getTargetId());
/*     */       
/* 139 */       QueueActionGroupManager.getInstance().addActionToPendingGroup(action);
/*     */       
/* 141 */       return false;
/*     */     
/*     */ 
/*     */     case 4506: 
/* 145 */       FighterTackledMessage msg = (FighterTackledMessage)message;
/*     */       
/* 147 */       int actionTypeId = msg.getFightActionType().getId();
/* 148 */       TackleAction action = new TackleAction(msg.getUniqueId(), actionTypeId, msg.getActionId());
/* 149 */       action.setInstigatorId(msg.getTacklerId());
/* 150 */       action.setTargetId(msg.getTackledFighterId());
/*     */       
/* 152 */       QueueActionGroupManager.getInstance().addActionToPendingGroup(action);
/* 153 */       return false;
/*     */     
/*     */ 
/*     */     case 4520: 
/* 157 */       FighterDiesMessage msg = (FighterDiesMessage)message;
/*     */       
/* 159 */       int actionTypeId = msg.getFightActionType().getId();
/*     */       
/* 161 */       DieAction action = new DieAction(msg.getUniqueId(), actionTypeId, msg.getActionId());
/* 162 */       action.setTargetId(msg.getFighterId());
/*     */       
/*     */ 
/* 165 */       QueueActionGroupManager.getInstance().addActionToPendingGroup(action);
/* 166 */       return false;
/*     */     
/*     */ 
/*     */     case 4524: 
/* 170 */       FighterMoveMessage msg = (FighterMoveMessage)message;
/*     */       
/* 172 */       MoveAction action = new MoveAction(msg.getUniqueId(), msg.getFightActionType().getId(), msg.getActionId(), msg.getFighterId(), msg.getPathResult());
/* 173 */       action.setTargetId(msg.getFighterId());
/* 174 */       QueueActionGroupManager.getInstance().addActionToPendingGroup(action);
/* 175 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */     case 8108: 
/* 180 */       FighterCardUseMessage msg = (FighterCardUseMessage)message;
/*     */       
/* 182 */       int actionTypeId = msg.getFightActionType().getId();
/* 183 */       FighterCardAction fighterCardAction = new FighterCardAction(msg.getUniqueId(), actionTypeId, msg.getActionId(), msg.getCard(), msg.isCriticalHit(), msg.isCriticalMiss(), 
/* 184 */         msg.getUserId(), msg.getUsePositionX(), msg.getUsePositionY(), msg.getUsePositionZ());
/*     */       
/* 186 */       ActionGroup group = QueueActionGroupManager.getInstance().addActionToPendingGroup(fighterCardAction);
/* 187 */       fighterCardAction.addJavaFunctionsLibrary(new ScriptedActionFunctionsLibrary(group));
/*     */       
/* 189 */       return false;
/*     */     
/*     */ 
/*     */     case 8112: 
/* 193 */       CloseCombatMessage msg = (CloseCombatMessage)message;
/* 194 */       int actionTypeId = msg.getFightActionType().getId();
/*     */       
/* 196 */       CloseCombatAction closeCombatAction = new CloseCombatAction(msg.getUniqueId(), actionTypeId, msg.getActionId(), msg.isCriticalHit(), msg.isCriticalMiss(), msg.getUserId(), 
/* 197 */         msg.getUsePositionX(), msg.getUsePositionY(), msg.getUsePositionZ());
/*     */       
/* 199 */       ActionGroup group = QueueActionGroupManager.getInstance().addActionToPendingGroup(closeCombatAction);
/* 200 */       closeCombatAction.addJavaFunctionsLibrary(new ScriptedActionFunctionsLibrary(group));
/*     */       
/* 202 */       return false;
/*     */     
/*     */ 
/*     */     case 8110: 
/* 206 */       SpellCastMessage msg = (SpellCastMessage)message;
/*     */       
/* 208 */       Fight fight = DofusArenaGameEntity.getInstance().getFight();
/* 209 */       Fighter caster = (Fighter)fight.getFighterById(msg.getCasterId());
/* 210 */       if (caster != null)
/*     */       {
/* 212 */         Spell spell = (Spell)caster.getSpellInventory().getWithUniqueId(msg.getSpellId());
/* 213 */         if (spell == null)
/*     */         {
/*     */ 
/* 216 */           spell = (Spell)caster.getTeamMateSpellInventory().getWithUniqueId(msg.getSpellId());
/*     */           
/* 218 */           if (spell == null) {
/* 219 */             m_logger.error("Impossible de lancer un sort car le caster " + msg.getCasterId() + " ne possede pas le sort : " + msg.getSpellId());
/* 220 */             return false;
/*     */           }
/*     */           
/*     */ 
/* 224 */           caster.removeCoachSpell(spell);
/*     */         }
/*     */         
/* 227 */         caster.getSpellCastHistory().storeSpellCast(spell, fight.getTimeline().getCurrentTableturn(), 
/* 228 */           (com.ankamagames.framework.ai.targetfinder.Target)fight.getTargetOnPosition(new Point3(msg.getCastPositionX(), msg.getCastPositionY(), msg.getCastPositionZ())));
/*     */         
/* 230 */         int actionTypeId = msg.getFightActionType().getId();
/*     */         
/*     */ 
/*     */ 
/* 234 */         boolean display = (spell.getBreedId() != Breed.COACH.getId()) || (!caster.getProperties().isActiveProperty(com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterPropertyType.INVISIBLE)) || 
/* 235 */           (caster.getTeam().getLeaderId() == DofusArenaGameEntity.getInstance().getLocalCoach().getTeamMateId());
/*     */         
/* 237 */         SpellAction spellAction = new SpellAction(msg.getUniqueId(), actionTypeId, msg.getActionId(), spell, msg.isCriticalHit(), msg.isCriticalMiss(), msg.getCasterId(), 
/* 238 */           msg.getCastPositionX(), msg.getCastPositionY(), msg.getCastPositionZ(), display);
/*     */         
/* 240 */         ActionGroup group = QueueActionGroupManager.getInstance().addActionToPendingGroup(spellAction);
/* 241 */         spellAction.addJavaFunctionsLibrary(new ScriptedActionFunctionsLibrary(group));
/*     */       } else {
/* 243 */         m_logger.error("Impossible de lancer un sort car le caster n'existe pas : " + msg.getCasterId());
/* 244 */         return false;
/*     */       }
/*     */       
/* 247 */       return false;
/*     */     
/*     */ 
/*     */     case 8120: 
/* 251 */       RunningEffectActionMessage msg = (RunningEffectActionMessage)message;
/*     */       
/* 253 */       int actionTypeId = msg.getFightActionType().getId();
/*     */       
/* 255 */       StaticRunningEffect staticEffect = (StaticRunningEffect)RunningEffectConstants.getInstance().getObjectFromId(msg.getRunningEffectId());
/* 256 */       if (staticEffect == null) {
/* 257 */         m_logger.error("Impossible d'instancier un runningEffect :" + msg.getRunningEffectId() + " inconnu");
/* 258 */         return false;
/*     */       }
/*     */       
/* 261 */       RunningEffect re = staticEffect.newUnserializedInstance(java.nio.ByteBuffer.wrap(msg.getSerializedRunningEffect()), DofusArenaGameEntity.getInstance().getFight().getContext(), 
/* 262 */         com.ankamagames.dofusarena.client.core.game.effect.EffectManager.getInstance());
/*     */       
/*     */ 
/*     */ 
/* 266 */       container = null;
/* 267 */       switch (msg.getEffectContainerType()) {
/*     */       case 13: 
/* 269 */         container = (EffectContainer)com.ankamagames.dofusarena.client.core.game.spell.SpellManager.getInstance().getSpell(msg.getEffectContainerId());
/* 270 */         break;
/*     */       case 3: 
/* 272 */         container = com.ankamagames.dofusarena.common.game.effectArea.StaticEffectAreaManager.getInstance().getEffectArea(msg.getEffectContainerId());
/* 273 */         break;
/*     */       case 14: 
/* 275 */         container = AbstractEventManager.getInstance().getAbstractEventFromId(msg.getEffectContainerId());
/* 276 */         break;
/*     */       case 12: 
/* 278 */         container = (EffectContainer)FighterCardManager.getInstance().get(msg.getEffectContainerId());
/*     */       }
/*     */       
/* 281 */       re.setEffectContainer(container);
/*     */       
/*     */ 
/*     */ 
/* 285 */       re.disableValueComputation();
/*     */       
/* 287 */       SpellEffectAction spellEffectAction = new SpellEffectAction(msg.getUniqueId(), actionTypeId, msg.getActionId(), re, msg.isTriggered());
/* 288 */       spellEffectAction.setTriggerActionUniqueId(msg.getTriggeringActionUniqueId());
/*     */       
/*     */ 
/* 291 */       if (re.getCaster() != null) {
/* 292 */         spellEffectAction.setInstigatorId(re.getCaster().getId());
/*     */       } else {
/* 294 */         spellEffectAction.setInstigatorId(0L);
/*     */       }
/*     */       
/*     */ 
/* 298 */       if (re.getTarget() != null) {
/* 299 */         spellEffectAction.setTargetId(re.getTarget().getId());
/*     */       } else {
/* 301 */         spellEffectAction.setTargetId(0L);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 308 */       if (msg.mustBeExecutedNow()) {
/* 309 */         spellEffectAction.run();
/*     */       } else {
/* 311 */         QueueActionGroupManager.getInstance().addActionToPendingGroup(spellEffectAction);
/*     */       }
/* 313 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */     case 8200: 
/* 318 */       ActionGroup actionGroup = QueueActionGroupManager.getInstance().getPendingActionGroup();
/* 319 */       if (actionGroup == null) {
/* 320 */         return false;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 325 */       int newUniqueId = 0;
/* 326 */       ChangeDirectionAction changeDirectionAction = null;
/* 327 */       for (Action action : actionGroup.getActions()) {
/* 328 */         if ((action instanceof AbstractFightCastAction)) {
/* 329 */           long fighterId = action.getInstigatorId();
/* 330 */           Fighter fighter = (Fighter)DofusArenaGameEntity.getInstance().getFight().getFighterById(fighterId);
/*     */           
/* 332 */           if (fighter != null) {
/* 333 */             AbstractFightCastAction fightCastAction = (AbstractFightCastAction)action;
/*     */             
/* 335 */             double vx = fightCastAction.getX() - fighter.getPosition().getX();
/* 336 */             double vy = fightCastAction.getY() - fighter.getPosition().getY();
/*     */             Direction8 direction;
/*     */             Direction8 direction;
/* 339 */             if ((vx == 0.0D) && (vy == 0.0D)) {
/* 340 */               direction = fighter.getDirection();
/*     */             } else {
/* 342 */               direction = com.ankamagames.framework.kernel.core.maths.Vector3i.getDirection4FromVector(vx, vy);
/*     */             }
/*     */             
/* 345 */             changeDirectionAction = new ChangeDirectionAction(0, FightActionType.CHANGE_DIRECTION.getId(), 0, direction);
/* 346 */             changeDirectionAction.setInstigatorId(action.getInstigatorId());
/*     */           }
/*     */         }
/*     */         
/* 350 */         if (action.getUniqueId() >= newUniqueId) {
/* 351 */           newUniqueId = action.getUniqueId() + 1;
/*     */         }
/*     */       }
/*     */       
/* 355 */       if (changeDirectionAction != null) {
/* 356 */         changeDirectionAction.setUniqueId(newUniqueId);
/* 357 */         actionGroup.addAction(changeDirectionAction);
/*     */       }
/*     */       
/* 360 */       QueueActionGroupManager.getInstance().executePendingGroup();
/*     */       
/* 362 */       return false;
/*     */     }
/*     */     
/*     */     
/* 366 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 375 */     return 0L;
/*     */   }
/*     */   
/*     */   public void setId(long id) {}
/*     */   
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*     */   
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetFightActionFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */