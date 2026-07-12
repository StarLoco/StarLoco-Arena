/*     */ package com.ankamagames.dofusarena.client.core.game.coach;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFight;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFighter;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.FightingTeam;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.TeamMate;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContentProvider;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.ItemExchanger;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.ItemExchangerUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.StackInventory;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryItemModifiedEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryObserver;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.ItemExchangerEvent;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.actor.Actor;
/*     */ import com.ankamagames.dofusarena.client.core.game.actor.ActorHolder;
/*     */ import com.ankamagames.dofusarena.client.core.game.actor.CoachEquipmentType;
/*     */ import com.ankamagames.dofusarena.client.core.game.actor.FighterActorEquipmentType;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.BetCoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCardProvider;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.core.game.miniMap.MiniMapManager;
/*     */ import com.ankamagames.dofusarena.client.core.game.spell.Spell;
/*     */ import com.ankamagames.dofusarena.client.core.game.spell.SpellManager;
/*     */ import com.ankamagames.dofusarena.common.constants.DofusArenaConstants;
/*     */ import com.ankamagames.dofusarena.common.game.card.CoachCardInventories;
/*     */ import com.ankamagames.dofusarena.common.game.coach.CoachHairColor;
/*     */ import com.ankamagames.dofusarena.common.game.coach.CoachSkinColor;
/*     */ import com.ankamagames.dofusarena.common.game.spell.AbstractSpellManager;
/*     */ import com.ankamagames.dofusarena.common.game.spell.CoachSpellInventory;
/*     */ import com.ankamagames.dofusarena.common.game.statistics.PlayerStatisticsReport;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindResult;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.BaseDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.DescriptorLibraryManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.property.FieldProvider;
/*     */ import java.nio.BufferUnderflowException;
/*     */ import java.nio.ByteBuffer;
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
/*     */ public class Coach
/*     */   extends Actor
/*     */   implements TeamMate<Fighter>, ItemExchangerUser<CoachCard>, ActorHolder, FieldProvider, InventoryObserver
/*     */ {
/*  60 */   protected static Logger m_logger = Logger.getLogger(Coach.class);
/*     */   
/*     */   public static final String NAME_FIELD = "name";
/*     */   
/*     */   public static final String SEX_FIELD = "sex";
/*     */   
/*     */   public static final String HAIR_INDEX_FIELD = "hairIndex";
/*     */   
/*     */   public static final String HAIR_COLOR_INDEX_FIELD = "hairColor";
/*     */   
/*     */   public static final String SKIN_COLOR_INDEX_FIELD = "skinColor";
/*     */   public static final String ACTOR_DESCRIPTOR_LIBRARY_FIELD = "actorDescriptorLibrary";
/*     */   public static final String ACTOR_LINKAGE_FIELD = "actorLinkage";
/*     */   public static final String LEVEL_FIELD = "level";
/*     */   public static final String PREVIOUS_LEVEL_FIELD = "previousLevel";
/*     */   public static final String RANK_FIELD = "rank";
/*     */   public static final String PREVIOUS_RANK_FIELD = "previousRank";
/*     */   public static final String RANK_ICON_URL_FIELD = "rankIconUrl";
/*     */   public static final String BET_CARDS_FIELD = "betCards";
/*     */   public static final String COACH_SPELLS_FIELD = "coachSpells";
/*     */   public static final String STATISTICS_TOTAL_FIGHTS_FIELD = "statisticsTotalFights";
/*     */   public static final String STATISTICS_TOTAL_FIGHTS_WON_FIELD = "statisticsTotalFightsWon";
/*     */   public static final String STATISTICS_TOTAL_FIGHTS_LOST_FIELD = "statisticsTotalFightsLost";
/*     */   public static final String STATISTICS_CONSECUTIVE_WINS_FIELD = "statisticsConsecutiveWins";
/*     */   public static final String STATISTICS_CONSECUTIVE_LOSSES_FIELD = "statisticsConsecutiveLosses";
/*     */   public static final String STATISTICS_TOTAL_FIGHTS_TIME_FIELD = "statisticsTotalFightsTime";
/*     */   public static final String STATISTICS_TOTAL_PLAY_TIME_FIELD = "statisticsTotalPlayTime";
/*  87 */   public static final String[] FIELDS = new String[] { 
/*  88 */       "name", 
/*  89 */       "sex", 
/*  90 */       "hairIndex", 
/*  91 */       "hairColor", 
/*  92 */       "skinColor", 
/*  93 */       "actorDescriptorLibrary", 
/*  94 */       "actorLinkage", 
/*     */       
/*  96 */       "level", 
/*  97 */       "rank", 
/*  98 */       "rankIconUrl",
/*     */       
/* 100 */       "betCards", 
/* 101 */       "coachSpells", 
/*     */       
/* 103 */       "statisticsTotalFights", 
/* 104 */       "statisticsTotalFightsWon", 
/* 105 */       "statisticsTotalFightsLost", 
/* 106 */       "statisticsConsecutiveWins", 
/* 107 */       "statisticsConsecutiveLosses", 
/* 108 */       "statisticsTotalFightsTime", 
/* 109 */       "statisticsTotalPlayTime" };
/*     */ 
/*     */   
/*     */   public static final int UNSERIALIZE_OPTION_NONE = 0;
/*     */ 
/*     */   
/*     */   public static final int UNSERIALIZE_OPTION_POSITION = 1;
/*     */ 
/*     */   
/*     */   public static final int UNSERIALIZE_OPTION_EQUIPMENT = 2;
/*     */   
/*     */   public static final int UNSERIALIZE_OPTION_CARD_INVENTORY = 4;
/*     */   
/*     */   public static final int UNSERIALIZE_LADDERS_STRENGTHS = 8;
/*     */   
/* 124 */   public static final String[] ACTOR_LINKAGES = new String[] {
/* 125 */       Mobile.createLinkage(Direction8.EAST.getIndex(), "AnimStatique"), 
/* 126 */       Mobile.createLinkage(Direction8.SOUTH_EAST.getIndex(), "AnimStatique"), 
/* 127 */       Mobile.createLinkage(Direction8.SOUTH.getIndex(), "AnimStatique"), 
/* 128 */       Mobile.createLinkage(Direction8.SOUTH_WEST.getIndex(), "AnimStatique"), 
/* 129 */       Mobile.createLinkage(Direction8.WEST.getIndex(), "AnimStatique"), 
/* 130 */       Mobile.createLinkage(Direction8.NORTH_WEST.getIndex(), "AnimStatique"), 
/* 131 */       Mobile.createLinkage(Direction8.NORTH.getIndex(), "AnimStatique"), 
/* 132 */       Mobile.createLinkage(Direction8.NORTH_EAST.getIndex(), "AnimStatique")
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   private static final String[] HAIR_COLORED_PARTS = new String[] { "Cheveux", "Natte" };
/* 139 */   private static final String[] SKIN_COLORED_PARTS = new String[] { "Peau", "Main", "Crane", "Oreille" };
/*     */   
/* 141 */   private String m_name = null;
/* 142 */   private byte m_sex = 0;
/*     */   
/*     */   private byte m_hairColorIndex;
/*     */   
/*     */   private byte m_skinColorIndex;
/* 147 */   private Direction8 m_actorLinkageDirection = Direction8.SOUTH_EAST;
/* 148 */   private String m_actorLinkage = ACTOR_LINKAGES[1];
/*     */ 
/*     */ 
/*     */   
/*     */   private final CoachCardInventories<CoachCard> m_coachCardInventories;
/*     */ 
/*     */ 
/*     */   
/*     */   private CoachSpellInventory<Spell> m_coachSpellInventory;
/*     */ 
/*     */ 
/*     */   
/*     */   private ItemExchanger<CoachCard> m_currentExchanger;
/*     */ 
/*     */ 
/*     */   
/* 164 */   private FightingTeam<Fighter> m_team = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private final HashMap<Long, Fighter> m_fighters;
/*     */ 
/*     */ 
/*     */   
/*     */   private BasicFight<Fighter> m_currentFight;
/*     */ 
/*     */   
/*     */   private final HashMap<Byte, Short> m_previousLaddersStrength;
/*     */ 
/*     */   
/*     */   private final HashMap<Byte, Short> m_laddersStrength;
/*     */ 
/*     */   
/* 181 */   private PlayerStatisticsReport m_statisticsReport = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 186 */   private ArrayList<BetCoachCard> m_betCards = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Coach() {
/* 192 */     super(-1L);
/* 193 */     this.m_coachCardInventories = new CoachCardInventories((InventoryContentProvider)CoachCardProvider.getInstance());
/* 194 */     this.m_coachCardInventories.addEquipmentObserver(this);
/* 195 */     this.m_fighters = new HashMap<Long, Fighter>();
/* 196 */     this.m_previousLaddersStrength = new HashMap<Byte, Short>();
/* 197 */     this.m_laddersStrength = new HashMap<Byte, Short>();
/* 198 */     setDirection(Direction8.SOUTH_EAST);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 205 */     return this.m_name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 212 */     this.m_name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getSex() {
/* 219 */     return this.m_sex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSex(byte sex) {
/* 226 */     this.m_sex = sex;
/* 227 */     setGfx(String.format("700%d", new Object[] { Byte.valueOf(sex) }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHairColorIndex(byte hairColorIndex) {
/* 234 */     this.m_hairColorIndex = hairColorIndex;
/* 235 */     CoachHairColor hairColor = CoachHairColor.getHairColor(this.m_hairColorIndex);
/* 236 */     Material material = (hairColor != null) ? hairColor.getMaterial() : null;
/* 237 */     setCustomMaterialToLinkages(HAIR_COLORED_PARTS, material);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getHairColorIndex() {
/* 244 */     return this.m_hairColorIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSkinColorIndex(byte skinColorIndex) {
/* 251 */     this.m_skinColorIndex = skinColorIndex;
/* 252 */     CoachSkinColor skinColor = CoachSkinColor.getSkinColor(this.m_skinColorIndex);
/* 253 */     Material material = (skinColor != null) ? skinColor.getMaterial() : null;
/* 254 */     setCustomMaterialToLinkages(SKIN_COLORED_PARTS, material);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getSkinColorIndex() {
/* 261 */     return this.m_skinColorIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActorLinkage(int index) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getActorLinkage() {
/* 274 */     return this.m_actorLinkage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Direction8 getActorLinkageDirection() {
/* 281 */     return this.m_actorLinkageDirection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActorLinkageDirection(Direction8 actorLinkageDirection) {
/* 288 */     this.m_actorLinkageDirection = actorLinkageDirection;
/* 289 */     this.m_actorLinkage = ACTOR_LINKAGES[Math.max(0, Math.min(this.m_actorLinkageDirection.getIndex(), ACTOR_LINKAGES.length - 1))];
/*     */ 
/*     */     
/* 292 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "actorLinkage");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLevel() {
/* 299 */     return DofusArenaConstants.strengthToLevel(getLadderStrength((byte)1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPreviousLevel() {
/* 306 */     return DofusArenaConstants.strengthToLevel(getPreviousLadderStrength((byte)1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getRank() {
/* 313 */     return DofusArenaConstants.levelToRank(getLevel());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getPreviousRank() {
/* 320 */     return DofusArenaConstants.levelToRank(getPreviousLevel());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBetCoachCard(BetCoachCard betCoachCard) {
/* 329 */     if (this.m_betCards == null) {
/* 330 */       this.m_betCards = new ArrayList<BetCoachCard>();
/*     */     }
/* 332 */     this.m_betCards.add(betCoachCard);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<BetCoachCard> getBetCards() {
/* 339 */     return this.m_betCards;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPath(PathFindResult node, boolean recompute) {
/* 349 */     super.setPath(node, recompute);
/*     */     
/* 351 */     int[] lastStep = node.getLastStep();
/* 352 */     if (lastStep != null) {
/* 353 */       MiniMapManager.getInstance().setXCenter(lastStep[0]);
/* 354 */       MiniMapManager.getInstance().setYCenter(lastStep[1]);
/* 355 */       MiniMapManager.getInstance().addPoint(getId(), lastStep[0], lastStep[1], "Moi", "\\\\games\\dofus-arena\\Version2\\SourcesGraphiques\\Interfaces\\source_découpage\\testMap.tga", new float[] { 0.1F, 1.0F, 0.1F, 0.8F });
/* 356 */       MiniMapManager.getInstance().addPoint(getId() + 1L, 340.0D, 50.0D, "1er pt", "\\\\games\\dofus-arena\\Version2\\SourcesGraphiques\\Interfaces\\source_découpage\\testMap.tga", new float[] { 1.0F, 0.1F, 0.1F, 0.8F });
/* 357 */       MiniMapManager.getInstance().addPoint(getId() + 2L, 330.0D, 55.0D, "2eme pt", "\\\\games\\dofus-arena\\Version2\\SourcesGraphiques\\Interfaces\\source_découpage\\testMap.tga", new float[] { 0.1F, 0.1F, 0.5F, 0.8F });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CoachCardInventories<CoachCard> getCardInventories() {
/* 365 */     return this.m_coachCardInventories;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CoachSpellInventory<Spell> getCoachSpellInventory() {
/* 372 */     return this.m_coachSpellInventory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeCoachSpellInventory(byte[] serializedCoachSpellInventory) {
/* 379 */     this.m_coachSpellInventory = new CoachSpellInventory((AbstractSpellManager)SpellManager.getInstance(), (short)4);
/* 380 */     this.m_coachSpellInventory.unserialize(serializedCoachSpellInventory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomizeLook() {
/* 387 */     setSkinColorIndex((byte)(int)(Math.random() * (CoachHairColor.values()).length));
/* 388 */     setHairColorIndex((byte)(int)(Math.random() * (CoachSkinColor.values()).length));
/* 389 */     setSex((byte)(int)(Math.random() * 2.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyEquipment(CoachCard equipment, short position) {
/*     */     try {
/* 401 */       String equipmentFileName = DofusArenaConfiguration.getInstance().getString("coachEquipmentPath");
/* 402 */       equipmentFileName = String.format(equipmentFileName, new Object[] { Integer.valueOf(equipment.getReferenceId()) });
/*     */ 
/*     */       
/* 405 */       BaseDescriptorLibrary library = DescriptorLibraryManager.getInstance().getDescriptorLibrary(equipmentFileName);
/*     */ 
/*     */       
/* 408 */       CoachEquipmentType equipmentType = CoachEquipmentType.getActorEquipmentTypeFromPosition(position);
/* 409 */       if (equipmentType != null) {
/* 410 */         setPartDescriptor(library, equipmentType.getLinkageNames());
/*     */       }
/*     */     }
/* 413 */     catch (Exception e) {
/* 414 */       m_logger.error("Erreur au chargement de l'équipment : " + equipment.getReferenceId(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unapplyEquipment(CoachCard equipment, short position) {
/* 424 */     CoachEquipmentType equipmentType = CoachEquipmentType.getActorEquipmentTypeFromPosition(position);
/* 425 */     if (equipmentType != null) {
/* 426 */       unsetPartDescriptor(equipmentType.getLinkageNames());
/*     */     }
/*     */   }
/*     */   
/*     */   public void unapplyAllEquipments() {
/*     */     byte b;
/*     */     int i;
/*     */     FighterActorEquipmentType[] arrayOfFighterActorEquipmentType;
/* 434 */     for (i = (arrayOfFighterActorEquipmentType = FighterActorEquipmentType.values()).length, b = 0; b < i; ) { FighterActorEquipmentType equipmentType = arrayOfFighterActorEquipmentType[b];
/* 435 */       unsetPartDescriptor(equipmentType.getLinkageNames());
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] serialize() {
/* 445 */     return new byte[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unserialize(byte[] serializedTeamMate) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unserialize(ByteBuffer buffer, int options) {
/*     */     try {
/* 465 */       unserializeIdAndName(buffer);
/* 466 */       if ((options & 0x1) == 1) {
/* 467 */         unserializePosition(buffer);
/*     */       }
/* 469 */       unserializeLook(buffer);
/* 470 */       if ((options & 0x2) == 2) {
/* 471 */         unserializeEquipment(buffer);
/*     */       }
/* 473 */       if ((options & 0x4) == 4) {
/* 474 */         unserializeCardInventory(buffer);
/*     */       }
/* 476 */       if ((options & 0x8) == 8) {
/* 477 */         unserializeLaddersStrength(buffer);
/*     */       }
/*     */     }
/* 480 */     catch (BufferUnderflowException e) {
/* 481 */       m_logger.error("pas assez de données pour completer la création d'un Coach");
/* 482 */       return false;
/*     */     } 
/* 484 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void unserializeCardInventory(ByteBuffer buffer) {
/* 491 */     short length = buffer.getShort();
/* 492 */     byte[] data = new byte[length];
/* 493 */     buffer.get(data);
/* 494 */     this.m_coachCardInventories.unserializeInventory(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void unserializeEquipment(ByteBuffer buffer) {
/* 501 */     short length = buffer.getShort();
/* 502 */     byte[] data = new byte[length];
/* 503 */     buffer.get(data);
/* 504 */     this.m_coachCardInventories.unserializeEquipment(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void unserializeLook(ByteBuffer buffer) {
/* 511 */     setSkinColorIndex(buffer.get());
/* 512 */     setHairColorIndex(buffer.get());
/* 513 */     setSex(buffer.get());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void unserializePosition(ByteBuffer buffer) {
/* 520 */     setWorldX(buffer.getInt());
/* 521 */     setWorldY(buffer.getInt());
/* 522 */     setAltitude(buffer.getShort());
/* 523 */     setDirection(Direction8.getDirectionFromIndex(buffer.get()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void unserializeIdAndName(ByteBuffer buffer) {
/* 530 */     setId(buffer.getLong());
/*     */     
/* 532 */     byte[] nameBuffer = new byte[buffer.get()];
/* 533 */     buffer.get(nameBuffer);
/* 534 */     setName(new String(nameBuffer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void unserializeLaddersStrength(ByteBuffer buffer) {
/* 544 */     int count = buffer.get();
/* 545 */     for (int i = 0; i < count; i++) {
/* 546 */       byte ladderId = buffer.get();
/* 547 */       short strength = buffer.getShort();
/*     */ 
/*     */       
/* 550 */       this.m_laddersStrength.put(Byte.valueOf(ladderId), Short.valueOf(strength));
/* 551 */       this.m_previousLaddersStrength.put(Byte.valueOf(ladderId), Short.valueOf(strength));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Actor getActor() {
/* 561 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicFight<Fighter> getCurrentFight() {
/* 570 */     return this.m_currentFight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addFighter(Fighter fighter) {
/* 579 */     if (!this.m_fighters.containsKey(Long.valueOf(fighter.getId()))) {
/* 580 */       this.m_fighters.put(Long.valueOf(fighter.getId()), fighter);
/* 581 */       fighter.setTeamMate(this);
/* 582 */       return true;
/*     */     } 
/* 584 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeFighter(Fighter fighter) {
/* 593 */     if (this.m_fighters.containsKey(Long.valueOf(fighter.getId()))) {
/* 594 */       this.m_fighters.remove(Long.valueOf(fighter.getId()));
/* 595 */       fighter.setTeamMate(null);
/* 596 */       return true;
/*     */     } 
/* 598 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterable<Fighter> getFighters() {
/* 607 */     return this.m_fighters.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLadderStrength(byte ladderId, short strength) {
/* 617 */     short previousStrength = getLadderStrength(ladderId);
/* 618 */     this.m_laddersStrength.put(Byte.valueOf(ladderId), Short.valueOf(strength));
/* 619 */     this.m_previousLaddersStrength.put(Byte.valueOf(ladderId), Short.valueOf(previousStrength));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getLadderStrength(byte ladderId) {
/* 627 */     if (this.m_laddersStrength.containsKey(Byte.valueOf(ladderId))) {
/* 628 */       return ((Short)this.m_laddersStrength.get(Byte.valueOf(ladderId))).shortValue();
/*     */     }
/* 630 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getPreviousLadderStrength(byte ladderId) {
/* 638 */     if (this.m_previousLaddersStrength.containsKey(Byte.valueOf(ladderId))) {
/* 639 */       return ((Short)this.m_previousLaddersStrength.get(Byte.valueOf(ladderId))).shortValue();
/*     */     }
/* 641 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerStatisticsReport getStatisticsReport() {
/* 648 */     return this.m_statisticsReport;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatisticsReport(PlayerStatisticsReport statisticsReport) {
/* 655 */     this.m_statisticsReport = statisticsReport;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFightersCount() {
/* 664 */     return this.m_fighters.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTeamMateId() {
/* 673 */     return getId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FightingTeam<Fighter> getTeam() {
/* 682 */     return this.m_team;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTeam(FightingTeam<Fighter> team) {
/* 691 */     this.m_team = team;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentItemExchanger(ItemExchanger<CoachCard> itemExchanger) {
/* 700 */     this.m_currentExchanger = itemExchanger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemExchanger<CoachCard> getCurrentExchanger() {
/* 707 */     return this.m_currentExchanger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getBudget() {
/* 716 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canStartNewExchange() {
/* 725 */     return (this.m_currentExchanger == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendFieldValue(String fieldName, Object value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getFields() {
/* 743 */     return FIELDS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getFieldValue(String fieldName) {
/* 752 */     if (fieldName.equals("name")) {
/* 753 */       return getName();
/*     */     }
/* 755 */     if (fieldName.equals("sex")) {
/* 756 */       return Byte.valueOf(getSex());
/*     */     }
/* 758 */     if (fieldName.equals("actorDescriptorLibrary")) {
/* 759 */       return getDescriptorLibrary();
/*     */     }
/* 761 */     if (fieldName.equals("hairColor")) {
/* 762 */       return Byte.valueOf(getHairColorIndex());
/*     */     }
/* 764 */     if (fieldName.equals("skinColor")) {
/* 765 */       return Byte.valueOf(getSkinColorIndex());
/*     */     }
/* 767 */     if (fieldName.equals("actorLinkage")) {
/* 768 */       return getActorLinkage();
/*     */     }
/*     */     
/* 771 */     if (fieldName.equals("level")) {
/* 772 */       int level = getLevel();
/* 773 */       if (level <= 0) {
/* 774 */         return "-";
/*     */       }
/* 776 */       return Integer.valueOf(level);
/*     */     } 
/* 778 */     if (fieldName.equals("previousLevel")) {
/* 779 */       int level = getPreviousLevel();
/* 780 */       if (level <= 0) {
/* 781 */         return "-";
/*     */       }
/* 783 */       return Integer.valueOf(level);
/*     */     } 
/* 785 */     if (fieldName.equals("rank")) {
/* 786 */       return Short.valueOf(getRank());
/*     */     }
/* 788 */     if (fieldName.equals("previousRank")) {
/* 789 */       return Short.valueOf(getPreviousRank());
/*     */     }
/* 791 */     if (fieldName.equals("rankIconUrl")) {
/*     */       try {
/* 793 */         return String.format(DofusArenaConfiguration.getInstance().getString("coachRankIconsPath"), new Object[] { Short.valueOf(getRank()) });
/* 794 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */     
/* 798 */     if (fieldName.equals("betCards") && 
/* 799 */       this.m_betCards != null) {
/* 800 */       return this.m_betCards.toArray();
/*     */     }
/*     */     
/* 803 */     if (fieldName.equals("coachSpells") && 
/* 804 */       getCoachSpellInventory() != null) {
/* 805 */       StackInventory<Spell> inventory = getCoachSpellInventory().getSpellInventory();
/* 806 */       if (inventory != null) {
/* 807 */         ArrayList<Spell> coachCards = new ArrayList<Spell>();
/* 808 */         for (Spell spell : inventory) {
/* 809 */           coachCards.add(spell);
/*     */         }
/* 811 */         return coachCards.toArray();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 816 */     if (getStatisticsReport() != null) {
/* 817 */       if (fieldName.equals("statisticsTotalFights")) {
/* 818 */         return Integer.valueOf(getStatisticsReport().getTotalFights());
/*     */       }
/* 820 */       if (fieldName.equals("statisticsTotalFightsWon")) {
/* 821 */         return Integer.valueOf(getStatisticsReport().getTotalFightsWon());
/*     */       }
/* 823 */       if (fieldName.equals("statisticsTotalFightsLost")) {
/* 824 */         return Integer.valueOf(getStatisticsReport().getTotalFightsLost());
/*     */       }
/* 826 */       if (fieldName.equals("statisticsConsecutiveWins")) {
/* 827 */         return Integer.valueOf(getStatisticsReport().getConsecutiveWins());
/*     */       }
/* 829 */       if (fieldName.equals("statisticsConsecutiveLosses")) {
/* 830 */         return Integer.valueOf(getStatisticsReport().getConsecutiveLosses());
/*     */       }
/* 832 */       if (fieldName.equals("statisticsTotalFightsTime"))
/*     */       {
/* 834 */         return DofusArenaTranslator.getInstance().formatDateWithDay(getStatisticsReport().getTotalFightsTime() * 1000L);
/*     */       }
/* 836 */       if (fieldName.equals("statisticsTotalPlayTime"))
/*     */       {
/* 838 */         return DofusArenaTranslator.getInstance().formatDateWithDay(getStatisticsReport().getTotalPlayTime() * 1000L);
/*     */       }
/*     */     } 
/* 841 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFieldSynchronisable(String fieldName) {
/* 850 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prependFieldValue(String fieldName, Object value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFieldValue(String fieldName, Object value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPooled(boolean pooled) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInventoryEvent(InventoryEvent event) {
/*     */     InventoryItemModifiedEvent modifiedEvent;
/* 886 */     switch (event.getAction()) {
/*     */       case ITEM_ADDED:
/*     */       case ITEM_ADDED_AT:
/* 889 */         modifiedEvent = (InventoryItemModifiedEvent)event;
/* 890 */         applyEquipment((CoachCard)modifiedEvent.getConcernedItem(), modifiedEvent.getPosition());
/*     */         break;
/*     */ 
/*     */       
/*     */       case ITEM_REMOVED:
/*     */       case ITEM_REMOVED_AT:
/* 896 */         modifiedEvent = (InventoryItemModifiedEvent)event;
/* 897 */         unapplyEquipment((CoachCard)modifiedEvent.getConcernedItem(), modifiedEvent.getPosition());
/*     */         break;
/*     */ 
/*     */       
/*     */       case null:
/* 902 */         unapplyAllEquipments();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onItemExchangerEvent(ItemExchangerEvent event) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFightEnd() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTeamMateJoinFight(BasicFight<Fighter> basicFight) {
/* 931 */     this.m_currentFight = basicFight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckIn() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckOut() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 965 */     return getName();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\coach\Coach.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */