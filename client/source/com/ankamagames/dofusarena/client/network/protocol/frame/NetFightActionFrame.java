/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.PropertyType;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.StaticRunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.BasicEffectArea;
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
/*     */ import com.ankamagames.dofusarena.common.game.effectArea.AbstractEffectArea;
/*     */ import com.ankamagames.dofusarena.common.game.event.AbstractEvent;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.Breed;
/*     */ import com.ankamagames.dofusarena.common.game.spell.AbstractSpell;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.framework.script.JavaFunctionsLibrary;
/*     */ import com.ankamagames.framework.script.ScriptedActionFunctionsLibrary;
/*     */ import com.ankamagames.framework.script.action.Action;
/*     */ import com.ankamagames.framework.script.action.ActionGroup;
/*     */ import com.ankamagames.framework.script.action.QueueActionGroupManager;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class NetFightActionFrame implements MessageFrame {
/*  53 */   protected static final Logger m_logger = Logger.getLogger(NetFightActionFrame.class);
/*     */   
/*  55 */   private static NetFightActionFrame m_instance = new NetFightActionFrame();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NetFightActionFrame getInstance() {
/*  61 */     return m_instance; } public boolean onMessage(Message message) { NewTableTurnBeginMessage newTableTurnBeginMessage; FighterTurnBeginMessage fighterTurnBeginMessage; FighterTurnEndMessage fighterTurnEndMessage; FighterChangeDirectionMessage fighterChangeDirectionMessage; EffectAreaActionMessage effectAreaActionMessage; FighterTackledMessage fighterTackledMessage; FighterDiesMessage fighterDiesMessage; FighterMoveMessage fighterMoveMessage; FighterCardUseMessage fighterCardUseMessage; CloseCombatMessage closeCombatMessage; SpellCastMessage spellCastMessage; RunningEffectActionMessage msg; ActionGroup actionGroup; int k; BasicEffectArea area; int j; MoveAction action; int i; Fight fight; int actionTypeId; int newUniqueId; NewTableTurnAction newTableTurnAction; FighterTurnStartAction fighterTurnStartAction; FighterTurnEndAction fighterTurnEndAction; ChangeDirectionAction changeDirectionAction1; int m; TackleAction tackleAction; DieAction dieAction; FighterCardAction fighterCardAction; CloseCombatAction closeCombatAction; Fighter caster; StaticRunningEffect staticEffect; ChangeDirectionAction changeDirectionAction;
/*     */     EffectAreaAction effectAreaAction;
/*     */     ActionGroup group;
/*     */     RunningEffect re;
/*     */     EffectContainer container;
/*     */     AbstractEffectArea abstractEffectArea;
/*     */     AbstractEvent abstractEvent;
/*     */     EffectContainer effectContainer1;
/*     */     SpellEffectAction spellEffectAction;
/*  70 */     switch (message.getId()) {
/*     */ 
/*     */       
/*     */       case 8100:
/*  74 */         newTableTurnBeginMessage = (NewTableTurnBeginMessage)message;
/*  75 */         k = newTableTurnBeginMessage.getFightActionType().getId();
/*  76 */         newTableTurnAction = new NewTableTurnAction(newTableTurnBeginMessage.getUniqueId(), k, newTableTurnBeginMessage.getActionId());
/*  77 */         newTableTurnAction.setEventToAdd((AbstractEvent)newTableTurnBeginMessage.getEvent());
/*     */         
/*  79 */         QueueActionGroupManager.getInstance().addActionToPendingGroup((Action)newTableTurnAction);
/*  80 */         QueueActionGroupManager.getInstance().executePendingGroup();
/*  81 */         return false;
/*     */ 
/*     */ 
/*     */       
/*     */       case 8104:
/*  86 */         fighterTurnBeginMessage = (FighterTurnBeginMessage)message;
/*     */         
/*  88 */         k = fighterTurnBeginMessage.getFightActionType().getId();
/*  89 */         fighterTurnStartAction = new FighterTurnStartAction(fighterTurnBeginMessage.getUniqueId(), k, fighterTurnBeginMessage.getActionId());
/*  90 */         fighterTurnStartAction.setInstigatorId(fighterTurnBeginMessage.getFighterId());
/*     */         
/*  92 */         QueueActionGroupManager.getInstance().addActionToPendingGroup((Action)fighterTurnStartAction);
/*  93 */         QueueActionGroupManager.getInstance().executePendingGroup();
/*     */         
/*  95 */         return false;
/*     */ 
/*     */       
/*     */       case 8106:
/*  99 */         fighterTurnEndMessage = (FighterTurnEndMessage)message;
/*     */         
/* 101 */         k = fighterTurnEndMessage.getFightActionType().getId();
/* 102 */         fighterTurnEndAction = new FighterTurnEndAction(fighterTurnEndMessage.getUniqueId(), k, fighterTurnEndMessage.getActionId());
/* 103 */         fighterTurnEndAction.setInstigatorId(fighterTurnEndMessage.getFighterId());
/*     */         
/* 105 */         QueueActionGroupManager.getInstance().addActionToPendingGroup((Action)fighterTurnEndAction);
/* 106 */         QueueActionGroupManager.getInstance().executePendingGroup();
/*     */         
/* 108 */         return false;
/*     */ 
/*     */       
/*     */       case 4522:
/* 112 */         fighterChangeDirectionMessage = (FighterChangeDirectionMessage)message;
/*     */         
/* 114 */         k = fighterChangeDirectionMessage.getFightActionType().getId();
/* 115 */         changeDirectionAction1 = new ChangeDirectionAction(fighterChangeDirectionMessage.getUniqueId(), k, fighterChangeDirectionMessage.getActionId(), fighterChangeDirectionMessage.getDirection());
/* 116 */         changeDirectionAction1.setInstigatorId(fighterChangeDirectionMessage.getFighterId());
/*     */         
/* 118 */         QueueActionGroupManager.getInstance().addActionToPendingGroup((Action)changeDirectionAction1);
/* 119 */         QueueActionGroupManager.getInstance().executePendingGroup();
/*     */         
/* 121 */         return false;
/*     */ 
/*     */       
/*     */       case 6200:
/* 125 */         effectAreaActionMessage = (EffectAreaActionMessage)message;
/*     */         
/* 127 */         area = DofusArenaGameEntity.getInstance().getFight().getEffectAreaManager().getActiveEffectAreaWithId(effectAreaActionMessage.getAreaId());
/*     */         
/* 129 */         if (area == null) {
/* 130 */           m_logger.error("Impossible de lancer un effet static d'id " + effectAreaActionMessage.getAreaId());
/* 131 */           return false;
/*     */         } 
/*     */         
/* 134 */         m = effectAreaActionMessage.getFightActionType().getId();
/* 135 */         effectAreaAction = new EffectAreaAction(effectAreaActionMessage.getUniqueId(), m, effectAreaActionMessage.getActionId(), effectAreaActionMessage.isApply(), (EffectArea)area);
/* 136 */         effectAreaAction.setInstigatorId(effectAreaActionMessage.getAreaId());
/* 137 */         effectAreaAction.setTargetId(effectAreaActionMessage.getTargetId());
/*     */         
/* 139 */         QueueActionGroupManager.getInstance().addActionToPendingGroup((Action)effectAreaAction);
/*     */         
/* 141 */         return false;
/*     */ 
/*     */       
/*     */       case 4506:
/* 145 */         fighterTackledMessage = (FighterTackledMessage)message;
/*     */         
/* 147 */         j = fighterTackledMessage.getFightActionType().getId();
/* 148 */         tackleAction = new TackleAction(fighterTackledMessage.getUniqueId(), j, fighterTackledMessage.getActionId());
/* 149 */         tackleAction.setInstigatorId(fighterTackledMessage.getTacklerId());
/* 150 */         tackleAction.setTargetId(fighterTackledMessage.getTackledFighterId());
/*     */         
/* 152 */         QueueActionGroupManager.getInstance().addActionToPendingGroup((Action)tackleAction);
/* 153 */         return false;
/*     */ 
/*     */       
/*     */       case 4520:
/* 157 */         fighterDiesMessage = (FighterDiesMessage)message;
/*     */         
/* 159 */         j = fighterDiesMessage.getFightActionType().getId();
/*     */         
/* 161 */         dieAction = new DieAction(fighterDiesMessage.getUniqueId(), j, fighterDiesMessage.getActionId());
/* 162 */         dieAction.setTargetId(fighterDiesMessage.getFighterId());
/*     */ 
/*     */         
/* 165 */         QueueActionGroupManager.getInstance().addActionToPendingGroup((Action)dieAction);
/* 166 */         return false;
/*     */ 
/*     */       
/*     */       case 4524:
/* 170 */         fighterMoveMessage = (FighterMoveMessage)message;
/*     */         
/* 172 */         action = new MoveAction(fighterMoveMessage.getUniqueId(), fighterMoveMessage.getFightActionType().getId(), fighterMoveMessage.getActionId(), fighterMoveMessage.getFighterId(), fighterMoveMessage.getPathResult());
/* 173 */         action.setTargetId(fighterMoveMessage.getFighterId());
/* 174 */         QueueActionGroupManager.getInstance().addActionToPendingGroup((Action)action);
/* 175 */         return false;
/*     */ 
/*     */ 
/*     */       
/*     */       case 8108:
/* 180 */         fighterCardUseMessage = (FighterCardUseMessage)message;
/*     */         
/* 182 */         i = fighterCardUseMessage.getFightActionType().getId();
/* 183 */         fighterCardAction = new FighterCardAction(fighterCardUseMessage.getUniqueId(), i, fighterCardUseMessage.getActionId(), fighterCardUseMessage.getCard(), fighterCardUseMessage.isCriticalHit(), fighterCardUseMessage.isCriticalMiss(), 
/* 184 */             fighterCardUseMessage.getUserId(), fighterCardUseMessage.getUsePositionX(), fighterCardUseMessage.getUsePositionY(), fighterCardUseMessage.getUsePositionZ());
/*     */         
/* 186 */         group = QueueActionGroupManager.getInstance().addActionToPendingGroup((Action)fighterCardAction);
/* 187 */         fighterCardAction.addJavaFunctionsLibrary((JavaFunctionsLibrary)new ScriptedActionFunctionsLibrary(group));
/*     */         
/* 189 */         return false;
/*     */ 
/*     */       
/*     */       case 8112:
/* 193 */         closeCombatMessage = (CloseCombatMessage)message;
/* 194 */         i = closeCombatMessage.getFightActionType().getId();
/*     */         
/* 196 */         closeCombatAction = new CloseCombatAction(closeCombatMessage.getUniqueId(), i, closeCombatMessage.getActionId(), closeCombatMessage.isCriticalHit(), closeCombatMessage.isCriticalMiss(), closeCombatMessage.getUserId(), 
/* 197 */             closeCombatMessage.getUsePositionX(), closeCombatMessage.getUsePositionY(), closeCombatMessage.getUsePositionZ());
/*     */         
/* 199 */         group = QueueActionGroupManager.getInstance().addActionToPendingGroup((Action)closeCombatAction);
/* 200 */         closeCombatAction.addJavaFunctionsLibrary((JavaFunctionsLibrary)new ScriptedActionFunctionsLibrary(group));
/*     */         
/* 202 */         return false;
/*     */ 
/*     */       
/*     */       case 8110:
/* 206 */         spellCastMessage = (SpellCastMessage)message;
/*     */         
/* 208 */         fight = DofusArenaGameEntity.getInstance().getFight();
/* 209 */         caster = (Fighter)fight.getFighterById(spellCastMessage.getCasterId());
/* 210 */         if (caster != null) {
/*     */           
/* 212 */           Spell spell = (Spell)caster.getSpellInventory().getWithUniqueId(spellCastMessage.getSpellId());
/* 213 */           if (spell == null) {
/*     */ 
/*     */             
/* 216 */             spell = (Spell)caster.getTeamMateSpellInventory().getWithUniqueId(spellCastMessage.getSpellId());
/*     */             
/* 218 */             if (spell == null) {
/* 219 */               m_logger.error("Impossible de lancer un sort car le caster " + spellCastMessage.getCasterId() + " ne possede pas le sort : " + spellCastMessage.getSpellId());
/* 220 */               return false;
/*     */             } 
/*     */ 
/*     */             
/* 224 */             caster.removeCoachSpell(spell);
/*     */           } 
/*     */           
/* 227 */           caster.getSpellCastHistory().storeSpellCast((AbstractSpell)spell, fight.getTimeline().getCurrentTableturn(), 
/* 228 */               (Target)fight.getTargetOnPosition(new Point3(spellCastMessage.getCastPositionX(), spellCastMessage.getCastPositionY(), spellCastMessage.getCastPositionZ())));
/*     */           
/* 230 */           int n = spellCastMessage.getFightActionType().getId();
/*     */ 
/*     */ 
/*     */           
/* 234 */           boolean display = !(spell.getBreedId() == Breed.COACH.getId() && caster.getProperties().isActiveProperty((PropertyType)FighterPropertyType.INVISIBLE) && 
/* 235 */             caster.getTeam().getLeaderId() != DofusArenaGameEntity.getInstance().getLocalCoach().getTeamMateId());
/*     */           
/* 237 */           SpellAction spellAction = new SpellAction(spellCastMessage.getUniqueId(), n, spellCastMessage.getActionId(), spell, spellCastMessage.isCriticalHit(), spellCastMessage.isCriticalMiss(), spellCastMessage.getCasterId(), 
/* 238 */               spellCastMessage.getCastPositionX(), spellCastMessage.getCastPositionY(), spellCastMessage.getCastPositionZ(), display);
/*     */           
/* 240 */           ActionGroup actionGroup1 = QueueActionGroupManager.getInstance().addActionToPendingGroup((Action)spellAction);
/* 241 */           spellAction.addJavaFunctionsLibrary((JavaFunctionsLibrary)new ScriptedActionFunctionsLibrary(actionGroup1));
/*     */         } else {
/* 243 */           m_logger.error("Impossible de lancer un sort car le caster n'existe pas : " + spellCastMessage.getCasterId());
/* 244 */           return false;
/*     */         } 
/*     */         
/* 247 */         return false;
/*     */ 
/*     */       
/*     */       case 8120:
/* 251 */         msg = (RunningEffectActionMessage)message;
/*     */         
/* 253 */         actionTypeId = msg.getFightActionType().getId();
/*     */         
/* 255 */         staticEffect = (StaticRunningEffect)RunningEffectConstants.getInstance().getObjectFromId(msg.getRunningEffectId());
/* 256 */         if (staticEffect == null) {
/* 257 */           m_logger.error("Impossible d'instancier un runningEffect :" + msg.getRunningEffectId() + " inconnu");
/* 258 */           return false;
/*     */         } 
/*     */         
/* 261 */         re = staticEffect.newUnserializedInstance(ByteBuffer.wrap(msg.getSerializedRunningEffect()), DofusArenaGameEntity.getInstance().getFight().getContext(), 
/* 262 */             (AbstractEffectManager)EffectManager.getInstance());
/*     */ 
/*     */ 
/*     */         
/* 266 */         container = null;
/* 267 */         switch (msg.getEffectContainerType()) {
/*     */           case 13:
/* 269 */             container = (EffectContainer)SpellManager.getInstance().getSpell(msg.getEffectContainerId());
/*     */             break;
/*     */           case 3:
/* 272 */             abstractEffectArea = StaticEffectAreaManager.getInstance().getEffectArea(msg.getEffectContainerId());
/*     */             break;
/*     */           case 14:
/* 275 */             abstractEvent = AbstractEventManager.getInstance().getAbstractEventFromId(msg.getEffectContainerId());
/*     */             break;
/*     */           case 12:
/* 278 */             effectContainer1 = (EffectContainer)FighterCardManager.getInstance().get(msg.getEffectContainerId());
/*     */             break;
/*     */         } 
/* 281 */         re.setEffectContainer(effectContainer1);
/*     */ 
/*     */ 
/*     */         
/* 285 */         re.disableValueComputation();
/*     */         
/* 287 */         spellEffectAction = new SpellEffectAction(msg.getUniqueId(), actionTypeId, msg.getActionId(), re, msg.isTriggered());
/* 288 */         spellEffectAction.setTriggerActionUniqueId(msg.getTriggeringActionUniqueId());
/*     */ 
/*     */         
/* 291 */         if (re.getCaster() != null) {
/* 292 */           spellEffectAction.setInstigatorId(re.getCaster().getId());
/*     */         } else {
/* 294 */           spellEffectAction.setInstigatorId(0L);
/*     */         } 
/*     */ 
/*     */         
/* 298 */         if (re.getTarget() != null) {
/* 299 */           spellEffectAction.setTargetId(re.getTarget().getId());
/*     */         } else {
/* 301 */           spellEffectAction.setTargetId(0L);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 308 */         if (msg.mustBeExecutedNow()) {
/* 309 */           spellEffectAction.run();
/*     */         } else {
/* 311 */           QueueActionGroupManager.getInstance().addActionToPendingGroup((Action)spellEffectAction);
/*     */         } 
/* 313 */         return false;
/*     */ 
/*     */ 
/*     */       
/*     */       case 8200:
/* 318 */         actionGroup = QueueActionGroupManager.getInstance().getPendingActionGroup();
/* 319 */         if (actionGroup == null) {
/* 320 */           return false;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 325 */         newUniqueId = 0;
/* 326 */         changeDirectionAction = null;
/* 327 */         for (Action action1 : actionGroup.getActions()) {
/* 328 */           if (action1 instanceof AbstractFightCastAction) {
/* 329 */             long fighterId = action1.getInstigatorId();
/* 330 */             Fighter fighter = (Fighter)DofusArenaGameEntity.getInstance().getFight().getFighterById(fighterId);
/*     */             
/* 332 */             if (fighter != null) {
/* 333 */               Direction8 direction; AbstractFightCastAction fightCastAction = (AbstractFightCastAction)action1;
/*     */               
/* 335 */               double vx = (fightCastAction.getX() - fighter.getPosition().getX());
/* 336 */               double vy = (fightCastAction.getY() - fighter.getPosition().getY());
/*     */ 
/*     */               
/* 339 */               if (vx == 0.0D && vy == 0.0D) {
/* 340 */                 direction = fighter.getDirection();
/*     */               } else {
/* 342 */                 direction = Vector3i.getDirection4FromVector(vx, vy);
/*     */               } 
/*     */               
/* 345 */               changeDirectionAction = new ChangeDirectionAction(0, FightActionType.CHANGE_DIRECTION.getId(), 0, direction);
/* 346 */               changeDirectionAction.setInstigatorId(action1.getInstigatorId());
/*     */             } 
/*     */           } 
/*     */           
/* 350 */           if (action1.getUniqueId() >= newUniqueId) {
/* 351 */             newUniqueId = action1.getUniqueId() + 1;
/*     */           }
/*     */         } 
/*     */         
/* 355 */         if (changeDirectionAction != null) {
/* 356 */           changeDirectionAction.setUniqueId(newUniqueId);
/* 357 */           actionGroup.addAction((Action)changeDirectionAction);
/*     */         } 
/*     */         
/* 360 */         QueueActionGroupManager.getInstance().executePendingGroup();
/*     */         
/* 362 */         return false;
/*     */     } 
/*     */ 
/*     */     
/* 366 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 375 */     return 0L;
/*     */   }
/*     */   
/*     */   public void setId(long id) {}
/*     */   
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*     */   
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetFightActionFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */