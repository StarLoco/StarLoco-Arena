/*      */ package com.ankamagames.dofusarena.client.core.game.fighter;
/*      */ 
/*      */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.AbstractCharacteristic;
/*      */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.CharacteristicType;
/*      */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.CharacteristicUpdateListener;
/*      */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.PropertyType;
/*      */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.PropertyUpdateListener;
/*      */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*      */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*      */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFight;
/*      */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFighter;
/*      */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.ArrayInventory;
/*      */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContentChecker;
/*      */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContentProvider;
/*      */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.StackInventory;
/*      */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryEvent;
/*      */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryItemModifiedEvent;
/*      */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryObserver;
/*      */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeUnit;
/*      */ import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
/*      */ import com.ankamagames.baseImpl.graphics.alea.mobile.movementStyle.MovementStyleManager;
/*      */ import com.ankamagames.dofusarena.client.alea.mobile.ThrowMovementStyle;
/*      */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*      */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*      */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*      */ import com.ankamagames.dofusarena.client.core.game.actor.Actor;
/*      */ import com.ankamagames.dofusarena.client.core.game.actor.ActorHolder;
/*      */ import com.ankamagames.dofusarena.client.core.game.actor.FighterActor;
/*      */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCard;
/*      */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCardInventoryChecker;
/*      */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCardManager;
/*      */ import com.ankamagames.dofusarena.client.core.game.card.fighter.UsableFighterCard;
/*      */ import com.ankamagames.dofusarena.client.core.game.coach.Coach;
/*      */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*      */ import com.ankamagames.dofusarena.client.core.game.spell.Spell;
/*      */ import com.ankamagames.dofusarena.client.core.game.spell.SpellInventoryChecker;
/*      */ import com.ankamagames.dofusarena.client.core.game.spell.SpellManager;
/*      */ import com.ankamagames.dofusarena.client.core.game.spell.UsableSpell;
/*      */ import com.ankamagames.dofusarena.client.core.game.team.EditableTeamPreset;
/*      */ import com.ankamagames.dofusarena.client.core.game.team.TeamPresetManager;
/*      */ import com.ankamagames.dofusarena.client.network.protocol.frame.NetFightActorsFrame;
/*      */ import com.ankamagames.dofusarena.common.constants.FighterCardType;
/*      */ import com.ankamagames.dofusarena.common.game.card.AbstractFighterCard;
/*      */ import com.ankamagames.dofusarena.common.game.effect.RunningEffectConstants;
/*      */ import com.ankamagames.dofusarena.common.game.effect.runningEffect.ArenaRunningEffect;
/*      */ import com.ankamagames.dofusarena.common.game.fight.AbstractFight;
/*      */ import com.ankamagames.dofusarena.common.game.fight.CloseCombatValidity;
/*      */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*      */ import com.ankamagames.dofusarena.common.game.fighter.Breed;
/*      */ import com.ankamagames.dofusarena.common.game.fighter.FighterInformation;
/*      */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristic;
/*      */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*      */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterPropertyType;
/*      */ import com.ankamagames.dofusarena.common.game.spell.CoachSpellInventory;
/*      */ import com.ankamagames.framework.kernel.core.maths.Direction;
/*      */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*      */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*      */ import com.ankamagames.xulor.Xulor;
/*      */ import com.ankamagames.xulor.property.FieldProvider;
/*      */ import gnu.trove.TIntObjectIterator;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Fighter
/*      */   extends AbstractFighter
/*      */   implements ActorHolder, FieldProvider, CharacteristicUpdateListener<FighterCharacteristic>, PropertyUpdateListener<FighterPropertyType>
/*      */ {
/*      */   public static final String NAME_FIELD = "name";
/*      */   public static final String BREED_ID_FIELD = "breedId";
/*      */   public static final String SEX_FIELD = "sex";
/*      */   public static final String SKIN_INDEX_FIELD = "skinIndex";
/*      */   public static final String ACTOR_DESCRIPTOR_LIBRARY_FIELD = "actorDescriptorLibrary";
/*      */   public static final String ACTOR_LINKAGE_FIELD = "actorLinkage";
/*      */   public static final String TIMELINE_ICON_URL_FIELD = "timelineIconUrl";
/*      */   public static final String BACKGROUND_URL_FIELD = "backgroundUrl";
/*      */   public static final String TEAM_MEMBER_FIELD = "teamMember";
/*      */   public static final String TEAM_ID_FIELD = "teamId";
/*      */   public static final String CLOSE_COMBAT_SMALL_DESCRIPTION = "closeCombatSmallDescription";
/*      */   public static final String CLOSE_COMBAT_USABLE = "closeCombatUsable";
/*      */   public static final String SPELLS_FIELD = "spells";
/*      */   public static final String USABLE_FIGHTER_CARDS_FIELD = "usableFighterCards";
/*      */   public static final String COACH_SPELLS_FIELD = "coachSpells";
/*      */   public static final String VALUE_FIELD = "value";
/*      */   public static final String HEALTH_POINTS_FIELD = "healthPoints";
/*      */   public static final String ACTION_POINTS_FIELD = "actionPoints";
/*      */   public static final String MAX_ACTION_POINTS_FIELD = "maxActionPoints";
/*      */   public static final String MOVE_POINTS_FIELD = "movePoints";
/*      */   public static final String MAX_MOVE_POINTS_FIELD = "maxMovePoints";
/*      */   public static final String INITIATIVE_POINTS_FIELD = "initiativePoints";
/*      */   public static final String RES_FIRE_PERCENT_FIELD = "resFirePercent";
/*      */   public static final String RES_WATER_PERCENT_FIELD = "resWaterPercent";
/*      */   public static final String RES_EARTH_PERCENT_FIELD = "resEarthPercent";
/*      */   public static final String RES_WIND_PERCENT_FIELD = "resWindPercent";
/*      */   public static final String DMG_FIRE_PERCENT_FIELD = "dmgFirePercent";
/*      */   public static final String DMG_WATER_PERCENT_FIELD = "dmgWaterPercent";
/*      */   public static final String DMG_EARTH_PERCENT_FIELD = "dmgEarthPercent";
/*      */   public static final String DMG_WIND_PERCENT_FIELD = "dmgWindPercent";
/*      */   public static final String RANGE_BONUS_FIELD = "rangeBonus";
/*      */   public static final String HEAL_BONUS_FIELD = "healBonus";
/*      */   public static final String SUMMONS_COUNT_FIELD = "summonsCount";
/*      */   public static final String CRITICAL_HIT_BONUS_FIELD = "criticalHitBonus";
/*      */   public static final String CRITICAL_MISS_MALUS_FIELD = "criticalMissMalus";
/*      */   public static final String WEAPON_EQUIPMENT_FIELD = "weaponEquipment";
/*      */   public static final String PET_EQUIPMENT_FIELD = "petEquipment";
/*      */   public static final String CLOAK_EQUIPMENT_FIELD = "cloakEquipment";
/*      */   public static final String HAT_EQUIPMENT_FIELD = "hatEquipment";
/*      */   public static final String DOFUS_EQUIPMENT_FIELD = "dofusEquipment";
/*      */   public static final String RUNNING_EFFECTS_FIELD = "runningEffects";
/*  126 */   public static final String[] FIELDS = new String[] { 
/*  127 */       "name", 
/*  128 */       "breedId", 
/*  129 */       "sex", 
/*  130 */       "skinIndex", 
/*  131 */       "actorDescriptorLibrary", 
/*  132 */       "actorLinkage", 
/*  133 */       "timelineIconUrl", 
/*  134 */       "backgroundUrl", 
/*      */       
/*  136 */       "teamMember", 
/*      */       
/*  138 */       "teamId",
/*      */       
/*  140 */       "closeCombatSmallDescription", 
/*  141 */       "closeCombatUsable", 
/*      */       
/*  143 */       "spells", 
/*  144 */       "usableFighterCards", 
/*  145 */       "coachSpells", 
/*      */       
/*  147 */       "value", 
/*      */       
/*  149 */       "healthPoints", 
/*  150 */       "actionPoints", 
/*  151 */       "maxActionPoints", 
/*  152 */       "movePoints", 
/*  153 */       "maxMovePoints", 
/*  154 */       "initiativePoints", 
/*      */       
/*  156 */       "resFirePercent", 
/*  157 */       "resWaterPercent", 
/*  158 */       "resEarthPercent", 
/*  159 */       "resWindPercent", 
/*      */       
/*  161 */       "dmgFirePercent", 
/*  162 */       "dmgWaterPercent", 
/*  163 */       "dmgEarthPercent", 
/*  164 */       "dmgWindPercent",
/*      */       
/*  166 */       "rangeBonus", 
/*  167 */       "healBonus", 
/*  168 */       "criticalHitBonus", 
/*  169 */       "criticalMissMalus", 
/*  170 */       "summonsCount", 
/*      */       
/*  172 */       "weaponEquipment", 
/*  173 */       "petEquipment", 
/*  174 */       "cloakEquipment", 
/*  175 */       "hatEquipment", 
/*  176 */       "dofusEquipment",
/*      */       
/*  178 */       "runningEffects" };
/*      */ 
/*      */ 
/*      */   
/*  182 */   private static final HashMap<FighterCharacteristicType, String[]> CHARACTERISTIC_FILEDS_CORRELATION = (HashMap)new HashMap<FighterCharacteristicType, String>();
/*      */   static {
/*  184 */     CHARACTERISTIC_FILEDS_CORRELATION.put(FighterCharacteristicType.HP, new String[] { "healthPoints" });
/*  185 */     CHARACTERISTIC_FILEDS_CORRELATION.put(FighterCharacteristicType.AP, new String[] { "actionPoints", "maxActionPoints", "spells", "usableFighterCards", "closeCombatSmallDescription", "closeCombatUsable" });
/*  186 */     CHARACTERISTIC_FILEDS_CORRELATION.put(FighterCharacteristicType.MP, new String[] { "movePoints", "maxMovePoints", "spells", "usableFighterCards" });
/*  187 */     CHARACTERISTIC_FILEDS_CORRELATION.put(FighterCharacteristicType.INIT, new String[] { "initiativePoints" });
/*      */     
/*  189 */     CHARACTERISTIC_FILEDS_CORRELATION.put(FighterCharacteristicType.RES_IN_PERCENT, new String[] { "resFirePercent", "resWaterPercent", "resEarthPercent", "resWindPercent" });
/*  190 */     CHARACTERISTIC_FILEDS_CORRELATION.put(FighterCharacteristicType.RES_FIRE_PERCENT, new String[] { "resFirePercent" });
/*  191 */     CHARACTERISTIC_FILEDS_CORRELATION.put(FighterCharacteristicType.RES_WATER_PERCENT, new String[] { "resWaterPercent" });
/*  192 */     CHARACTERISTIC_FILEDS_CORRELATION.put(FighterCharacteristicType.RES_EARTH_PERCENT, new String[] { "resEarthPercent" });
/*  193 */     CHARACTERISTIC_FILEDS_CORRELATION.put(FighterCharacteristicType.RES_WIND_PERCENT, new String[] { "resWindPercent" });
/*      */     
/*  195 */     CHARACTERISTIC_FILEDS_CORRELATION.put(FighterCharacteristicType.DMG_IN_PERCENT, new String[] { "dmgFirePercent", "dmgWaterPercent", "dmgEarthPercent", "dmgWindPercent" });
/*  196 */     CHARACTERISTIC_FILEDS_CORRELATION.put(FighterCharacteristicType.DMG_FIRE_PERCENT, new String[] { "dmgFirePercent" });
/*  197 */     CHARACTERISTIC_FILEDS_CORRELATION.put(FighterCharacteristicType.DMG_WATER_PERCENT, new String[] { "dmgWaterPercent" });
/*  198 */     CHARACTERISTIC_FILEDS_CORRELATION.put(FighterCharacteristicType.DMG_EARTH_PERCENT, new String[] { "dmgEarthPercent" });
/*  199 */     CHARACTERISTIC_FILEDS_CORRELATION.put(FighterCharacteristicType.DMG_WIND_PERCENT, new String[] { "dmgWindPercent" });
/*      */     
/*  201 */     CHARACTERISTIC_FILEDS_CORRELATION.put(FighterCharacteristicType.RANGE, new String[] { "rangeBonus" });
/*  202 */     CHARACTERISTIC_FILEDS_CORRELATION.put(FighterCharacteristicType.HEAL, new String[] { "healBonus" });
/*  203 */     CHARACTERISTIC_FILEDS_CORRELATION.put(FighterCharacteristicType.CRITICAL_RATE, new String[] { "criticalHitBonus" });
/*  204 */     CHARACTERISTIC_FILEDS_CORRELATION.put(FighterCharacteristicType.FUMBLE_RATE, new String[] { "criticalMissMalus" });
/*  205 */     CHARACTERISTIC_FILEDS_CORRELATION.put(FighterCharacteristicType.NB_SUMMONS, new String[] { "summonsCount", "spells" });
/*      */   }
/*  207 */   private static final String[] UPDATED_FILEDS_WITH_CARRING_STATE = new String[] { "spells", "usableFighterCards", "coachSpells" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AbstractFight<Fighter> m_currentFight;
/*      */ 
/*      */ 
/*      */   
/*      */   private final FighterActor m_actor;
/*      */ 
/*      */ 
/*      */   
/*      */   protected ArrayInventory<FighterCard> m_equipmentInventory;
/*      */ 
/*      */ 
/*      */   
/*      */   protected StackInventory<Spell> m_spellInventory;
/*      */ 
/*      */ 
/*      */   
/*  228 */   protected final ArrayList<Fighter> m_childs = new ArrayList<Fighter>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  233 */   protected Fighter m_father = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean m_actived = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  243 */   private String m_look = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Fighter() {
/*  249 */     for (TIntObjectIterator<AbstractCharacteristic> it = this.m_characteristics.iterator(); it.hasNext(); ) {
/*  250 */       it.advance();
/*  251 */       FighterCharacteristic c = (FighterCharacteristic)it.value();
/*  252 */       c.setListener(this);
/*      */     } 
/*      */     
/*  255 */     this.m_runningEffectManager.disableTriggers();
/*  256 */     this.m_spellInventory = new StackInventory((short)6, (InventoryContentProvider)SpellManager.getInstance(), (InventoryContentChecker)new SpellInventoryChecker(this), true, false, false);
/*  257 */     this.m_spellInventory.addObserver((InventoryObserver)this);
/*  258 */     this.m_equipmentInventory = new ArrayInventory((InventoryContentProvider)FighterCardManager.getInstance(), (InventoryContentChecker)new FighterCardInventoryChecker(this), (short)6, false);
/*  259 */     this.m_equipmentInventory.addObserver((InventoryObserver)this);
/*  260 */     this.m_properties.setListener(this);
/*  261 */     this.m_actor = new FighterActor(this);
/*  262 */     onCheckOut();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCheckIn() {
/*  272 */     super.onCheckIn();
/*  273 */     this.m_childs.clear();
/*  274 */     this.m_father = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FighterActor getActor() {
/*  283 */     return this.m_actor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setId(long id) {
/*  293 */     super.setId(id);
/*  294 */     this.m_actor.setId(id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSummoned() {
/*  304 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayList<Fighter> getChilds() {
/*  314 */     return this.m_childs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Fighter getFather() {
/*  324 */     return this.m_father;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Breed getBreed() {
/*  334 */     return this.m_breed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBreedFromId(byte breedId) {
/*  344 */     super.setBreedFromId(breedId);
/*  345 */     updateActorGfx();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSex(byte sex) {
/*  355 */     super.setSex(sex);
/*  356 */     updateActorGfx();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void changeLook(int lookId) {
/*  366 */     this.m_look = String.valueOf(lookId);
/*  367 */     updateActorGfx();
/*  368 */     if (getActor() != null) {
/*  369 */       getActor().applyAllEquipments();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void adaptLook(int lookId) {
/*  380 */     this.m_look = String.valueOf(getBaseGfx()) + "_" + String.valueOf(lookId);
/*  381 */     updateActorGfx();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void restoreLastLook() {
/*  391 */     ArenaRunningEffect lastLookRunningEffect = null;
/*  392 */     for (RunningEffect re : getRunningEffectManager()) {
/*  393 */       ArenaRunningEffect are = (ArenaRunningEffect)re;
/*  394 */       if (are.getId() == RunningEffectConstants.CHANGE_LOOK.getId() || are.getId() == RunningEffectConstants.ADAPT_LOOK.getId()) {
/*  395 */         if (lastLookRunningEffect != null) {
/*  396 */           if (are.getStartTime().compareTo(lastLookRunningEffect.getStartTime()) == 1)
/*  397 */             lastLookRunningEffect = are; 
/*      */           continue;
/*      */         } 
/*  400 */         lastLookRunningEffect = are;
/*      */       } 
/*      */     } 
/*      */     
/*  404 */     if (lastLookRunningEffect != null) {
/*      */       
/*  406 */       lastLookRunningEffect.execute(null, false);
/*      */     } else {
/*      */       
/*  409 */       this.m_look = null;
/*  410 */       updateActorGfx();
/*  411 */       setSkinIndex(getSkinIndex());
/*  412 */       if (getActor() != null) {
/*  413 */         getActor().applyAllEquipments();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSkinIndex(byte skinIndex) {
/*  426 */     super.setSkinIndex(skinIndex);
/*  427 */     this.m_actor.setSkinIndex(skinIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBreedAndSex(byte breedId, byte sex) {
/*  438 */     super.setBreedAndSex(breedId, sex);
/*  439 */     updateActorGfx();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPosition(int x, int y, short alt) {
/*  450 */     super.setPosition(x, y, alt);
/*  451 */     getActor().setWorldPosition(x, y, alt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositionWithoutNotifyActor(int x, int y, short alt) {
/*  462 */     super.setPosition(x, y, alt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPosition(Point3 pos) {
/*  472 */     super.setPosition(pos);
/*  473 */     getActor().setWorldPosition(pos.getX(), pos.getY(), pos.getZ());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDirection(Direction direction) {
/*  483 */     getActor().setDirection((Direction8)direction);
/*  484 */     setDirectionWithoutNotifyActor(direction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDirectionWithoutNotifyActor(Direction direction) {
/*  493 */     boolean needToUpdateProperty = !getDirection().equals(direction);
/*  494 */     if (needToUpdateProperty) {
/*  495 */       super.setDirection(direction);
/*  496 */       updateActorLinkageProperty();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateActorLinkageProperty() {
/*  505 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "actorLinkage");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AbstractFight<Fighter> getCurrentFight() {
/*  515 */     return this.m_currentFight;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOnFight() {
/*  524 */     return (this.m_currentFight != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCarriedByFighter(AbstractFighter carriedByFighter) {
/*  534 */     super.setCarriedByFighter(carriedByFighter);
/*      */ 
/*      */     
/*  537 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, UPDATED_FILEDS_WITH_CARRING_STATE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCarriedFighter(AbstractFighter carriedFighter) {
/*  547 */     super.setCarriedFighter(carriedFighter);
/*      */     
/*  549 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, UPDATED_FILEDS_WITH_CARRING_STATE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void appendFieldValue(String fieldName, Object value) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getFieldValue(String fieldName) {
/*  568 */     if (fieldName.equals("name")) {
/*  569 */       return getName();
/*      */     }
/*  571 */     if (fieldName.equals("breedId")) {
/*  572 */       return Byte.valueOf(this.m_breed.getId());
/*      */     }
/*  574 */     if (fieldName.equals("sex")) {
/*  575 */       return Byte.valueOf(getSex());
/*      */     }
/*  577 */     if (fieldName.equals("skinIndex")) {
/*  578 */       return Byte.valueOf(getSkinIndex());
/*      */     }
/*      */     
/*  581 */     if (fieldName.equals("actorDescriptorLibrary")) {
/*  582 */       return this.m_actor.getDescriptorLibrary();
/*      */     }
/*  584 */     if (fieldName.equals("actorLinkage")) {
/*  585 */       return this.m_actor.getDisplayObjectLinkage();
/*      */     }
/*  587 */     if (fieldName.equals("timelineIconUrl")) {
/*      */       try {
/*  589 */         return String.format(DofusArenaConfiguration.getInstance().getString("breedsTimelineIconPath"), new Object[] { getBaseGfx() });
/*  590 */       } catch (Exception e) {
/*  591 */         e.printStackTrace();
/*      */       } 
/*      */     }
/*  594 */     if (fieldName.equals("backgroundUrl")) {
/*      */       try {
/*  596 */         return String.format(DofusArenaConfiguration.getInstance().getString("breedsBackgroundPath"), new Object[] { Byte.valueOf(getBreed().getId()) });
/*  597 */       } catch (Exception e) {
/*  598 */         e.printStackTrace();
/*      */       } 
/*      */     }
/*      */     
/*  602 */     if (fieldName.equals("teamMember")) {
/*  603 */       EditableTeamPreset teamPreset = TeamPresetManager.getInstance().getEditableTeamPreset();
/*  604 */       if (teamPreset != null) {
/*  605 */         return Boolean.valueOf(teamPreset.contains(getId()));
/*      */       }
/*  607 */       return Boolean.valueOf(false);
/*      */     } 
/*      */     
/*  610 */     if (fieldName.equals("teamId") && 
/*  611 */       getTeamMate() != null) {
/*  612 */       return Integer.valueOf(getTeamMate().getTeam().getId());
/*      */     }
/*      */ 
/*      */     
/*  616 */     if (fieldName.equals("closeCombatSmallDescription")) {
/*  617 */       StringBuilder smallDescriptionBuilder = new StringBuilder(DofusArenaTranslator.getInstance().getString("closeCombat", new Object[0]));
/*  618 */       smallDescriptionBuilder.append(" (").append(getBreed().getCloseCombatAp()).append(' ').append(DofusArenaTranslator.getInstance().getString("AP", new Object[0])).append(")");
/*  619 */       Fight fight = DofusArenaGameEntity.getInstance().getFight();
/*  620 */       if (fight == null) {
/*  621 */         return "";
/*      */       }
/*  623 */       CloseCombatValidity castValidity = fight.getCloseCombatValidity(this, null);
/*  624 */       if (castValidity != CloseCombatValidity.OK) {
/*  625 */         smallDescriptionBuilder.append('\n').append(castValidity);
/*      */       }
/*  627 */       return smallDescriptionBuilder.toString();
/*      */     } 
/*      */     
/*  630 */     if (fieldName.equals("closeCombatUsable")) {
/*  631 */       Fight fight = DofusArenaGameEntity.getInstance().getFight();
/*  632 */       if (fight == null) {
/*  633 */         return "";
/*      */       }
/*  635 */       return (fight.getCloseCombatValidity(this, null) == CloseCombatValidity.OK) ? Boolean.valueOf(true) : Boolean.valueOf(false);
/*      */     } 
/*      */     
/*  638 */     if (fieldName.equals("spells")) {
/*  639 */       ArrayList<Spell> spells = new ArrayList<Spell>();
/*  640 */       for (Spell spell : this.m_spellInventory) {
/*  641 */         spells.add(spell);
/*      */       }
/*  643 */       return spells.toArray();
/*      */     } 
/*  645 */     if (fieldName.equals("usableFighterCards")) {
/*  646 */       ArrayList<FighterCard> usableFighterCards = new ArrayList<FighterCard>();
/*  647 */       for (FighterCard fighterCard : this.m_equipmentInventory) {
/*  648 */         if (fighterCard.isUsable()) {
/*  649 */           usableFighterCards.add(fighterCard);
/*      */         }
/*      */       } 
/*  652 */       return usableFighterCards.toArray();
/*      */     } 
/*  654 */     if (fieldName.equals("coachSpells")) {
/*  655 */       StackInventory<Spell> inventory = getTeamMateSpellInventory();
/*  656 */       if (inventory != null) {
/*  657 */         ArrayList<Spell> coachCards = new ArrayList<Spell>();
/*  658 */         for (Spell spell : inventory) {
/*  659 */           coachCards.add(spell);
/*      */         }
/*  661 */         return coachCards.toArray();
/*      */       } 
/*      */     } 
/*      */     
/*  665 */     if (fieldName.equals("value")) {
/*  666 */       return Short.valueOf(getValue());
/*      */     }
/*      */     
/*  669 */     if (fieldName.equals("healthPoints")) {
/*  670 */       return Integer.valueOf(getCharacteristicValue((CharacteristicType)FighterCharacteristicType.HP));
/*      */     }
/*  672 */     if (fieldName.equals("actionPoints")) {
/*  673 */       return Integer.valueOf(getCharacteristicValue((CharacteristicType)FighterCharacteristicType.AP));
/*      */     }
/*  675 */     if (fieldName.equals("maxActionPoints")) {
/*  676 */       return Integer.valueOf(getCharacteristic((CharacteristicType)FighterCharacteristicType.AP).max());
/*      */     }
/*  678 */     if (fieldName.equals("movePoints")) {
/*  679 */       return Integer.valueOf(getCharacteristicValue((CharacteristicType)FighterCharacteristicType.MP));
/*      */     }
/*  681 */     if (fieldName.equals("maxMovePoints")) {
/*  682 */       return Integer.valueOf(getCharacteristic((CharacteristicType)FighterCharacteristicType.MP).max());
/*      */     }
/*  684 */     if (fieldName.equals("initiativePoints")) {
/*  685 */       return Integer.valueOf(getCharacteristicValue((CharacteristicType)FighterCharacteristicType.INIT));
/*      */     }
/*      */     
/*  688 */     if (fieldName.equals("resFirePercent")) {
/*  689 */       return Integer.valueOf(getCharacteristicValue((CharacteristicType)FighterCharacteristicType.RES_FIRE_PERCENT) + getCharacteristicValue((CharacteristicType)FighterCharacteristicType.RES_IN_PERCENT));
/*      */     }
/*  691 */     if (fieldName.equals("resWaterPercent")) {
/*  692 */       return Integer.valueOf(getCharacteristicValue((CharacteristicType)FighterCharacteristicType.RES_WATER_PERCENT) + getCharacteristicValue((CharacteristicType)FighterCharacteristicType.RES_IN_PERCENT));
/*      */     }
/*  694 */     if (fieldName.equals("resEarthPercent")) {
/*  695 */       return Integer.valueOf(getCharacteristicValue((CharacteristicType)FighterCharacteristicType.RES_EARTH_PERCENT) + getCharacteristicValue((CharacteristicType)FighterCharacteristicType.RES_IN_PERCENT));
/*      */     }
/*  697 */     if (fieldName.equals("resWindPercent")) {
/*  698 */       return Integer.valueOf(getCharacteristicValue((CharacteristicType)FighterCharacteristicType.RES_WIND_PERCENT) + getCharacteristicValue((CharacteristicType)FighterCharacteristicType.RES_IN_PERCENT));
/*      */     }
/*      */     
/*  701 */     if (fieldName.equals("dmgFirePercent")) {
/*  702 */       return Integer.valueOf(getCharacteristicValue((CharacteristicType)FighterCharacteristicType.DMG_FIRE_PERCENT) + getCharacteristicValue((CharacteristicType)FighterCharacteristicType.DMG_IN_PERCENT));
/*      */     }
/*  704 */     if (fieldName.equals("dmgWaterPercent")) {
/*  705 */       return Integer.valueOf(getCharacteristicValue((CharacteristicType)FighterCharacteristicType.DMG_WATER_PERCENT) + getCharacteristicValue((CharacteristicType)FighterCharacteristicType.DMG_IN_PERCENT));
/*      */     }
/*  707 */     if (fieldName.equals("dmgEarthPercent")) {
/*  708 */       return Integer.valueOf(getCharacteristicValue((CharacteristicType)FighterCharacteristicType.DMG_EARTH_PERCENT) + getCharacteristicValue((CharacteristicType)FighterCharacteristicType.DMG_IN_PERCENT));
/*      */     }
/*  710 */     if (fieldName.equals("dmgWindPercent")) {
/*  711 */       return Integer.valueOf(getCharacteristicValue((CharacteristicType)FighterCharacteristicType.DMG_WIND_PERCENT) + getCharacteristicValue((CharacteristicType)FighterCharacteristicType.DMG_IN_PERCENT));
/*      */     }
/*      */     
/*  714 */     if (fieldName.equals("rangeBonus")) {
/*  715 */       return Integer.valueOf(getCharacteristicValue((CharacteristicType)FighterCharacteristicType.RANGE));
/*      */     }
/*  717 */     if (fieldName.equals("healBonus")) {
/*  718 */       return Integer.valueOf(getCharacteristicValue((CharacteristicType)FighterCharacteristicType.HEAL));
/*      */     }
/*  720 */     if (fieldName.equals("criticalHitBonus")) {
/*  721 */       return Integer.valueOf(getCharacteristicValue((CharacteristicType)FighterCharacteristicType.CRITICAL_RATE));
/*      */     }
/*  723 */     if (fieldName.equals("criticalMissMalus")) {
/*  724 */       return Integer.valueOf(getCharacteristicValue((CharacteristicType)FighterCharacteristicType.FUMBLE_RATE));
/*      */     }
/*  726 */     if (fieldName.equals("summonsCount")) {
/*  727 */       return Integer.valueOf(getCharacteristicValue((CharacteristicType)FighterCharacteristicType.NB_SUMMONS));
/*      */     }
/*      */     
/*  730 */     if (fieldName.equals("weaponEquipment")) {
/*  731 */       return getEquipmentInventory().getFromPosition(FighterCardType.WEAPON.getInventoryPosition());
/*      */     }
/*  733 */     if (fieldName.equals("petEquipment")) {
/*  734 */       return getEquipmentInventory().getFromPosition(FighterCardType.PET.getInventoryPosition());
/*      */     }
/*  736 */     if (fieldName.equals("cloakEquipment")) {
/*  737 */       return getEquipmentInventory().getFromPosition(FighterCardType.CLOAK.getInventoryPosition());
/*      */     }
/*  739 */     if (fieldName.equals("hatEquipment")) {
/*  740 */       return getEquipmentInventory().getFromPosition(FighterCardType.HAT.getInventoryPosition());
/*      */     }
/*  742 */     if (fieldName.equals("dofusEquipment")) {
/*  743 */       return getEquipmentInventory().getFromPosition(FighterCardType.DOFUS.getInventoryPosition());
/*      */     }
/*      */     
/*  746 */     if (fieldName.equals("runningEffects")) {
/*  747 */       for (RunningEffect runningEffect : getRunningEffectManager()) {
/*  748 */         EffectContainer effectContainer = runningEffect.getEffectContainer();
/*  749 */         if (effectContainer instanceof Spell) {
/*  750 */           Spell spell = (Spell)effectContainer;
/*  751 */           TurnBasedTimeUnit endTime = (TurnBasedTimeUnit)runningEffect.getEndTime();
/*  752 */           TurnBasedTimeUnit currentTime = (TurnBasedTimeUnit)runningEffect.getContext().getTimeline().now();
/*  753 */           int i = (endTime == null) ? Integer.MAX_VALUE : (endTime.getTableTurn() - currentTime.getTableTurn());
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  761 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getFields() {
/*  770 */     return FIELDS;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFieldSynchronisable(String fieldName) {
/*  779 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void prependFieldValue(String fieldName, Object value) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFieldValue(String fieldName, Object value) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean carry(AbstractFighter carriedFighter) {
/*  808 */     boolean result = super.carry(carriedFighter);
/*  809 */     if (result) {
/*  810 */       getActor().setMovementStyle(MovementStyleManager.WALK_CARRY_STYLE);
/*  811 */       getActor().setAnimationSuffix("Porte");
/*  812 */       getActor().setAnimation("Anim01");
/*  813 */       getActor().carry((Mobile)((Fighter)carriedFighter).getActor());
/*      */       
/*  815 */       getActor().setStaticAnimationKey("AnimStatique");
/*      */     } 
/*  817 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean uncarry(Point3 position) {
/*  829 */     getActor().setAnimationSuffix(null);
/*  830 */     if (this.m_actived) {
/*  831 */       getActor().setStaticAnimationKey("AnimStatique-02");
/*      */     } else {
/*  833 */       getActor().setStaticAnimationKey("AnimStatique");
/*      */     } 
/*  835 */     getActor().setMovementStyle(MovementStyleManager.WALK_STYLE);
/*      */     
/*  837 */     if (isCarrying()) {
/*  838 */       this.m_carriedFighter.setCarriedByFighter(null);
/*  839 */       setCarriedFighter((AbstractFighter)null);
/*      */       
/*  841 */       int deltaX = 0;
/*  842 */       int deltaY = 0;
/*  843 */       int deltaZ = 0;
/*  844 */       if (position != null) {
/*  845 */         deltaZ = Math.abs(position.getZ() - getPosition().getZ());
/*  846 */         deltaX = Math.abs(position.getX() - getPosition().getX());
/*  847 */         deltaY = Math.abs(position.getY() - getPosition().getY());
/*  848 */         if (deltaZ == 0 && deltaX + deltaY > 1) {
/*  849 */           getActor().setAnimation("Anim03Porte");
/*      */         } else {
/*  851 */           getActor().setAnimation("Anim02Porte");
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  856 */       Mobile carried = getActor().uncarry();
/*  857 */       if (carried != null) {
/*  858 */         ((FighterActor)carried).setMovementStyle("Throw");
/*  859 */         ThrowMovementStyle throwStyle = (ThrowMovementStyle)((FighterActor)carried).getMovementStyle();
/*  860 */         if (position != null) {
/*  861 */           throwStyle.setDistance(deltaX + deltaY);
/*  862 */           carried.setWorldPosition(position.getX(), position.getY(), position.getZ());
/*      */         } 
/*      */       } else {
/*  865 */         m_logger.trace("Lancer: n'avait pas de mobile porté");
/*      */       } 
/*  867 */       return true;
/*      */     } 
/*  869 */     m_logger.trace("Essaye de déposer alors qu'il ne porte personne");
/*      */     
/*  871 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void uncarry() {
/*  878 */     getActor().setAnimationSuffix(null);
/*  879 */     getActor().setMovementStyle(MovementStyleManager.WALK_STYLE);
/*  880 */     if (isCarrying()) {
/*  881 */       this.m_carriedFighter.setPosition(getPosition());
/*  882 */       this.m_carriedFighter.setCarriedByFighter(null);
/*  883 */       setCarriedFighter((AbstractFighter)null);
/*      */     } 
/*  885 */     getActor().uncarry();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getBaseGfx() {
/*  892 */     if (this.m_breed != null) {
/*  893 */       return String.valueOf(String.valueOf(this.m_breed.getId())) + String.valueOf(this.m_sex);
/*      */     }
/*  895 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateActorGfx() {
/*  902 */     if (this.m_actor != null) {
/*  903 */       String gfx = (this.m_look == null) ? getBaseGfx() : this.m_look;
/*  904 */       this.m_actor.setGfx(gfx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isActived() {
/*  912 */     return this.m_actived;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getMovementObstruction() {
/*  926 */     if (!this.m_actor.isVisible()) {
/*  927 */       return 1.0F;
/*      */     }
/*  929 */     return super.getMovementObstruction();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Fighter summonCreature(long newSummoningId, Point3 summonPos, int summoningDefinitionId) {
/*  940 */     SummoningDefinition definition = (SummoningDefinition)SummoningManager.getInstance().getSummoningDefinition(summoningDefinitionId);
/*  941 */     if (definition != null)
/*      */     {
/*  943 */       return summonFighter(newSummoningId, new SummonedFighter(this, definition), summonPos);
/*      */     }
/*      */     
/*  946 */     m_logger.error("SummoningDefinition id=" + summoningDefinitionId + " est inconnue !");
/*      */     
/*  948 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Fighter summonDouble(long newSummoningId, Point3 summonPos) {
/*  958 */     return summonFighter(newSummoningId, new DoubleFighter(this), summonPos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Fighter summonMirror(long newSummoningId, Point3 summonPos, int summoningDefinitionId) {
/*  969 */     SummoningDefinition definition = (SummoningDefinition)SummoningManager.getInstance().getSummoningDefinition(summoningDefinitionId);
/*  970 */     if (definition != null) {
/*  971 */       return summonFighter(newSummoningId, new MirrorFighter(this, definition), summonPos);
/*      */     }
/*  973 */     m_logger.error("SummoningDefinition id=" + summoningDefinitionId + " est inconnue !");
/*      */     
/*  975 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Fighter summonFighter(long newSummoningId, Fighter summoning, Point3 summonPos) {
/*  985 */     summoning.setId(newSummoningId);
/*  986 */     summoning.setPosition(summonPos);
/*      */ 
/*      */     
/*  989 */     this.m_childs.add(summoning);
/*      */ 
/*      */     
/*  992 */     if (getCurrentFight() != null) {
/*  993 */       getCurrentFight().addFighterDuringFight((BasicFighter)summoning);
/*      */     }
/*      */ 
/*      */     
/*  997 */     NetFightActorsFrame.addMobile((Mobile)summoning.getActor());
/*  998 */     summoning.getActor().showTeamParticleSystem();
/*      */     
/* 1000 */     return summoning;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StackInventory<Spell> getSpellInventory() {
/* 1011 */     return this.m_spellInventory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayInventory<FighterCard> getEquipmentInventory() {
/* 1021 */     return this.m_equipmentInventory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StackInventory<Spell> getTeamMateSpellInventory() {
/* 1031 */     if (getTeamMate() != null) {
/* 1032 */       CoachSpellInventory<Spell> inventory = ((Coach)getTeamMate()).getCoachSpellInventory();
/* 1033 */       if (inventory != null) {
/* 1034 */         return inventory.getSpellInventory();
/*      */       }
/*      */     } 
/* 1037 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeCoachSpell(Spell spell) {
/* 1048 */     if (getTeamMate() != null) {
/*      */ 
/*      */       
/* 1051 */       ((Coach)getTeamMate()).getCoachSpellInventory().removeCoachSpell(spell.getId());
/*      */ 
/*      */       
/* 1054 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "coachSpells");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void die() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onInventoryEvent(InventoryEvent event) {
/* 1074 */     if (event.getInventory().equals(getEquipmentInventory())) {
/*      */       InventoryItemModifiedEvent modifiedEvent;
/* 1076 */       switch (event.getAction()) {
/*      */         case ITEM_ADDED:
/*      */         case ITEM_ADDED_AT:
/* 1079 */           modifiedEvent = (InventoryItemModifiedEvent)event;
/* 1080 */           getActor().applyEquipment((FighterCard)modifiedEvent.getConcernedItem());
/* 1081 */           applyCardEffects((AbstractFighterCard)modifiedEvent.getConcernedItem());
/* 1082 */           if (modifiedEvent.getConcernedItem() instanceof UsableFighterCard) {
/* 1083 */             ((UsableFighterCard)modifiedEvent.getConcernedItem()).setFighter(this);
/*      */           }
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case ITEM_REMOVED:
/*      */         case ITEM_REMOVED_AT:
/* 1091 */           modifiedEvent = (InventoryItemModifiedEvent)event;
/* 1092 */           getActor().unapplyEquipment((FighterCard)modifiedEvent.getConcernedItem());
/* 1093 */           unapplyCardEffects((AbstractFighterCard)modifiedEvent.getConcernedItem());
/*      */           break;
/*      */ 
/*      */         
/*      */         case null:
/* 1098 */           getActor().unapplyAllEquipments();
/* 1099 */           initializeCharacteristics();
/*      */           break;
/*      */       } 
/*      */ 
/*      */     
/* 1104 */     } else if (event.getInventory().equals(getSpellInventory())) {
/*      */       InventoryItemModifiedEvent modifiedEvent;
/* 1106 */       switch (event.getAction()) {
/*      */         case ITEM_ADDED:
/*      */         case ITEM_ADDED_AT:
/*      */         case ITEM_REMOVED:
/*      */         case ITEM_REMOVED_AT:
/* 1111 */           modifiedEvent = (InventoryItemModifiedEvent)event;
/* 1112 */           if (modifiedEvent.getConcernedItem() instanceof UsableSpell) {
/* 1113 */             ((UsableSpell)modifiedEvent.getConcernedItem()).setFighter(this);
/*      */           }
/*      */           break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCharacteristicUpdated(FighterCharacteristic charac) {
/* 1128 */     String[] fields = CHARACTERISTIC_FILEDS_CORRELATION.get(charac.getType());
/* 1129 */     if (fields != null) {
/* 1130 */       byte b; int i; String[] arrayOfString; for (i = (arrayOfString = fields).length, b = 0; b < i; ) { String field = arrayOfString[b];
/* 1131 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, field);
/*      */         b++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPropertyUpdated(FighterPropertyType prop) {
/*      */     Fight fight;
/* 1142 */     if (prop == null)
/*      */       return; 
/* 1144 */     switch (prop) {
/*      */       case null:
/* 1146 */         fight = (Fight)getCurrentFight();
/* 1147 */         if (fight == null || this.m_actor == null) {
/*      */           return;
/*      */         }
/*      */         
/* 1151 */         if (fight.isLocalCoachFighter(this)) {
/* 1152 */           if (getProperties().isActiveProperty((PropertyType)prop)) {
/* 1153 */             this.m_actor.setAlpha(0.4F); break;
/*      */           } 
/* 1155 */           this.m_actor.setAlpha(1.0F);
/*      */           
/*      */           break;
/*      */         } 
/* 1159 */         this.m_actor.setAlpha(1.0F);
/* 1160 */         this.m_actor.setVisible(!getProperties().isActiveProperty((PropertyType)prop));
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onJoinFight(BasicFight fight) {
/* 1176 */     this.m_currentFight = (AbstractFight<Fighter>)fight;
/* 1177 */     super.onJoinFight((BasicFight)this.m_currentFight);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEffectUsed() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onRemovedFromFight() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onNowAbleToFight() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onNowUnableToFight() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onSpecialFighterEvent(int fightEventType) {
/* 1224 */     switch (fightEventType) {
/*      */ 
/*      */       
/*      */       case 101:
/* 1228 */         this.m_spellCastHistory.onNewTurn();
/*      */ 
/*      */         
/* 1231 */         getActor().showActiveParticleSystem();
/* 1232 */         if (!isCarrying()) {
/* 1233 */           getActor().setStaticAnimationKey("AnimStatique-02");
/* 1234 */           getActor().setAnimation("AnimStatique-02");
/*      */         } 
/* 1236 */         this.m_actived = true;
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 102:
/* 1242 */         getActor().hideActiveParticleSystem();
/* 1243 */         if (!isCarrying()) {
/* 1244 */           getActor().setStaticAnimationKey("AnimStatique");
/* 1245 */           getActor().setAnimation("AnimStatique");
/*      */         } 
/* 1247 */         this.m_actived = false;
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EditableFighter getEditableFighter() {
/* 1258 */     EditableFighter fighter = new EditableFighter();
/* 1259 */     fighter.unserialize(ByteBuffer.wrap(serialize()));
/* 1260 */     return fighter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FighterInformation getFighterInformation() {
/* 1267 */     FighterInformation fighterInformation = new FighterInformation();
/* 1268 */     fighterInformation.setName(getName());
/* 1269 */     fighterInformation.setBreedId(this.m_breed.getId());
/* 1270 */     fighterInformation.setSex(getSex());
/* 1271 */     fighterInformation.setSkinIndex(getSkinIndex());
/* 1272 */     fighterInformation.setSerializedSpellsInventory(getSpellInventory().serialize());
/* 1273 */     fighterInformation.setSerializedCardsInventory(getEquipmentInventory().serialize());
/* 1274 */     fighterInformation.setBudget(getValue());
/* 1275 */     return fighterInformation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1285 */     return getName();
/*      */   }
/*      */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\fighter\Fighter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */